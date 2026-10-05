import { ApplicationRef, Injectable } from "@angular/core";
import { NavigationStart, Router } from "@angular/router";
import { SwUpdate } from "@angular/service-worker";
import { filter, first, interval, switchMap } from "rxjs";

const UPDATE_CHECK_INTERVAL_MS = 30 * 60 * 1000;

/**
 * Switches users to the latest deployed version without them having to
 * hard-refresh the page.
 *
 * The service worker downloads new versions in the background, but keeps
 * serving the cached one to the open tab. Once a new version is ready, the
 * next in-app navigation is turned into a full page load, so the user never
 * loses what they're doing on the current page.
 */
@Injectable({
  providedIn: 'root'
})
export class AppUpdateService {

  private updateReady = false;

  constructor(
    private swUpdate: SwUpdate,
    private router: Router,
    private appRef: ApplicationRef,
  ) { }

  init(): void {
    if (!this.swUpdate.isEnabled) {
      return;
    }

    this.swUpdate.versionUpdates.pipe(
      filter(event => event.type === "VERSION_READY")
    ).subscribe(() => this.updateReady = true);

    this.router.events.pipe(
      filter((event): event is NavigationStart => event instanceof NavigationStart),
      filter(() => this.updateReady)
    ).subscribe(event => document.location.href = event.url);

    // The cached version is broken and can't be recovered, so start fresh.
    this.swUpdate.unrecoverable.subscribe(() => document.location.reload());

    // The service worker only checks for updates when the app is loaded, so
    // tabs left open would never find out about new versions. The checks only
    // start once the app is stable, otherwise they would keep it unstable and
    // delay the service worker registration.
    this.appRef.isStable.pipe(
      first(isStable => isStable),
      switchMap(() => interval(UPDATE_CHECK_INTERVAL_MS))
    ).subscribe(() => this.swUpdate.checkForUpdate().catch(() => { }));
  }
}

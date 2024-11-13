import {Component, EventEmitter, Input, Output} from '@angular/core';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {NgClass} from "@angular/common";

/**
 * A popup dialog to confirm certain actions to do with caution.
 */
@Component({
  selector: 'app-confirmation-modal',
  standalone: true,
  imports: [
    NgClass
  ],
  templateUrl: './confirmation-modal.component.html',
})
export class ConfirmationModalComponent {
  @Input()
  title: string = "";

  @Input()
  message: string = "";

  /**
   * Semantic is important based on the nature of the task.
   */
  @Input()
  type: "primary" | "danger" = "primary";

  @Output()
  confirmed = new EventEmitter<void>();

  constructor(public activeModal: NgbActiveModal) {}

  confirm() {
    this.confirmed.emit();
    this.activeModal.close();
  }

  cancel() {
    this.activeModal.dismiss();
  }
}

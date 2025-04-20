import { Directive, ElementRef, HostListener } from '@angular/core';

/**
 * Useful for text areas containing long amount of text.<br>
 * The height automatically changes based on the content.
 * TODO: does not work for text already in the input element when initializing it.
 */
@Directive({
  selector: '[appAutoResize]',
  standalone: true
})
export class AutoResizeDirective {

  constructor(private element: ElementRef) { }

  @HostListener("input") onInput(): void {
    this.resizeTextArea();
  }

  resizeTextArea(): void {
    const element = this.element.nativeElement;
    element.style.height = 'auto';
    element.style.height = `${element.scrollHeight}px`;
  }
}

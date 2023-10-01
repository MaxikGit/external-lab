import {Component, inject} from '@angular/core';
import {DOCUMENT, ViewportScroller} from "@angular/common";
import {fromEvent, map, Observable} from "rxjs";

@Component({
  selector: 'scroll-to-top',
  templateUrl: './scroll-to-top.component.html',
  styleUrls: ['./scroll-to-top.component.css']
})
export class ScrollToTopComponent {

  private readonly activationDistance = 500;
  private readonly document = inject(DOCUMENT);
  private readonly viewport = inject(ViewportScroller);

  readonly showScroll$: Observable<boolean> = fromEvent(this.document, 'scroll').pipe(
    //getScrollPosition:  [0] - X position, [1] - Y position
    map(() => this.viewport.getScrollPosition()?.[1] > this.activationDistance)
  );

  scrollToTop(): void {
    this.viewport.scrollToPosition([0, 0]);
  }
}

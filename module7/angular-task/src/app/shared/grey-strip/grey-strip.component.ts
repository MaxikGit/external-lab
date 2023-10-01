import {Component, Input} from '@angular/core';

@Component({
  selector: 'grey-title-strip',
  templateUrl: './grey-strip.component.html',
  styleUrls: ['./grey-strip.component.css']
})
export class GreyStripComponent {

  @Input() pageTitle!: string;

}

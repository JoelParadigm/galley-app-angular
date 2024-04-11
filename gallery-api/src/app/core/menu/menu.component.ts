import { Component } from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent {
  // Define functionality for the buttons
  log_temp = 'log';
  handleClickButton1() {
    console.log('Button 1 clicked');
    this.log_temp = 'btn1';
  }

  handleClickButton2() {
    console.log('Button 2 clicked');
    this.log_temp = 'btn2';
  }
}

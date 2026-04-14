import { Component } from '@angular/core';
import { ToastService, ToastMessage } from '../services/toast.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-toast',
  imports: [CommonModule],
  template: `
    <div *ngIf="toast" class="toast" [ngClass]="toast.type">
      {{ toast.message }}
    </div>
  `,
  styles: [`
    .toast { 
      position: fixed; 
      top: 20px; 
      right: 20px; 
      padding: 10px 20px; 
      background: #333; 
      color: #fff; 
      border-radius: 4px; 
      z-index: 9999;
    }
    .error { background: #e74c3c; }
    .success { background: #2ecc71; }
    .info { background: #3498db; }
  `]
})

export class ToastComponent {
  toast: ToastMessage | null = null;
  
  constructor(private toastService: ToastService) {
    this.toastService.toast$.subscribe(t => this.toast = t);
  }

}

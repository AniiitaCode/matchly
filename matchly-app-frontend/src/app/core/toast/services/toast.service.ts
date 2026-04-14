import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

export interface ToastMessage {
  message: string;
  type?: 'error' | 'success' | 'info';
}

@Injectable({
  providedIn: 'root',
})

export class ToastService {
  private _toast = new BehaviorSubject<ToastMessage | null>(null);
  toast$ = this._toast.asObservable();

  show(message: string, type: 'error' | 'success' | 'info' = 'info') {
    this._toast.next({ message, type });
    setTimeout(() => this.clear(), 4000);
  }

  clear() {
    this._toast.next(null);
  }
}

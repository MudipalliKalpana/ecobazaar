// src/app/services/theme.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ThemeService {
  private readonly darkSubject = new BehaviorSubject<boolean>(false);
  readonly isDark$ = this.darkSubject.asObservable();

  constructor() {
    const saved = localStorage.getItem('theme');
    const initial = saved ? saved === 'dark' : false;
    this.darkSubject.next(initial);
    this.applyTheme(initial);
  }

  toggleTheme(): void {
    const next = !this.darkSubject.value;
    this.darkSubject.next(next);
    localStorage.setItem('theme', next ? 'dark' : 'light');
    this.applyTheme(next);
  }

  isDark(): boolean {
    return this.darkSubject.value;
  }

  private applyTheme(dark: boolean): void {
    const html = document.documentElement;
    if (dark) {
      html.classList.add('dark');
    } else {
      html.classList.remove('dark');
    }
  }
}

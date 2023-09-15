import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SseService {
  private eventSource: EventSource = new EventSource('');

  constructor() {}

  connect(url: string): Observable<MessageEvent> {
    this.eventSource = new EventSource(url);
    return new Observable<MessageEvent>((observer) => {
      this.eventSource.addEventListener('message', (event) => {
        observer.next(event);
      });
    });
  }

  disconnect() {
    if (this.eventSource) {
      this.eventSource.close();
    }
  }
}

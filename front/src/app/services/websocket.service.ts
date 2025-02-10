import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { WebSocketSubject } from 'rxjs/internal/observable/dom/WebSocketSubject';
import { webSocket } from 'rxjs/webSocket';
import { environment } from '../../environments/environment';
import { SocketInterface } from './socket.interface';

export class WebsocketService implements SocketInterface {
  private readonly socket: WebSocketSubject<any>;
  private readonly subscribedEvents = new Map<string, Subject<any>>();
  constructor() {
    this.socket = webSocket(environment.baseUrl.replace('http', 'ws'));
    this.socket.asObservable().subscribe((data) => {
      console.log(`received event ${data.name} with data ${JSON.stringify(data.data)}`);
      const subject = this.subscribedEvents.get(data.name);
      if (subject) {
        subject.next(data.data);
      }
    })
  }

  public sendEvent<T>(name: string, data?: any): void {
    console.log(`sending event ${name} with data ${JSON.stringify(data)}`);
    this.socket.next({name, data});
  }

  public listenToEvent<T>(name: string): Observable<T> {
    console.log(`listening to event ${name}`);
    const subject =new Subject<T>()
    this.subscribedEvents.set(name, subject );
    return subject.asObservable();
  }
}

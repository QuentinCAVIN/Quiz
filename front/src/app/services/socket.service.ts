import { inject, Injectable } from '@angular/core';
import { SocketIoService } from './socket-io.service';
import { Socket } from 'ngx-socket-io';
import { SocketInterface } from './socket.interface';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { WebsocketService } from './websocket.service';

@Injectable({
  providedIn: 'root'
})
export class SocketService implements SocketInterface {
  private readonly socketImplementation: SocketInterface;

  constructor() {
    if (environment.useSocketIo) {
      this.socketImplementation = new SocketIoService(inject(Socket));
    } else {
      this.socketImplementation = new WebsocketService();
    }
  }

  sendEvent<T>(name: string, data?: T | undefined): void {
    this.socketImplementation.sendEvent(name, data);
  }

  listenToEvent<T>(name: string): Observable<T> {
    return this.socketImplementation.listenToEvent(name);
  }

}

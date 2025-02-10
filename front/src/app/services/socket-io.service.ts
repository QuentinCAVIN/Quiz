import { Socket } from 'ngx-socket-io';
import { Observable } from 'rxjs';
import { SocketInterface } from './socket.interface';

export class SocketIoService implements SocketInterface {
  constructor(private socket: Socket) {}

  public sendEvent<T>(name: string, data?: any, callback?: (response: T) => void): void {
    console.log(`sending event ${name} with data ${JSON.stringify(data)}`);

    // We are never going to use the data, because we need compatibility with websocket for
    // those poor Java developers who cannot use socket.io
    if (!callback){
      callback = () => {};
    }
    this.socket.emit(name, data, callback);
  }

  public listenToEvent<T>(name: string): Observable<T> {
    console.log(`listening to event ${name}`);
    return this.socket.fromEvent<T>(name);
  }
}

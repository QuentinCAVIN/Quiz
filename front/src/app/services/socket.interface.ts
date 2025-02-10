import { Observable } from 'rxjs';

export interface SocketInterface {
  sendEvent<T>(name: string, data?: T): void;

  listenToEvent<T>(name: string): Observable<T>;
}

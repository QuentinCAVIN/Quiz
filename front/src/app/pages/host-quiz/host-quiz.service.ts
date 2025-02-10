import { inject, Injectable } from '@angular/core';
import { tap } from 'rxjs';
import { Quiz } from '../../model/quiz';
import { SocketService } from 'src/app/services/socket.service';

export interface StatusEvent {
  status: 'waiting';
  participants: number;
}

export interface HostDetailsEvent {
  quiz: Quiz;
}

export interface HostJoinEvent {
  executionId: string;
}

@Injectable({ providedIn: 'root' })
export class HostQuizService {
  private readonly socketService = inject(SocketService);

  status$ = this.socketService.listenToEvent<StatusEvent>('status').pipe(tap(
      status => console.log('status', status)
    )
  );
  hostDetails$ = this.socketService.listenToEvent<HostDetailsEvent>('hostDetails')
    .pipe(tap(
      details => console.log('host details', details)
    ));
  connect(executionId: string) {
    this.socketService.sendEvent<HostJoinEvent>('host', { executionId });
  }

  nextQuestion(executionId: string) {
    this.socketService.sendEvent('nextQuestion', { executionId });
  }
}

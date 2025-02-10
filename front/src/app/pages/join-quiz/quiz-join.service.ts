import { inject, Injectable } from '@angular/core';
import { SocketIoService } from '../../services/socket-io.service';
import { SocketService } from '../../services/socket.service';

export interface StatusEvent {
  status: 'waiting';
  participants: number;
}

export interface ClientJoinDetails {
  quizTitle: string;
}

export interface ClientJoinEvent {
  executionId: string;
}

export interface QuestionEvent {
  question: string;
  answers: string[];
}

@Injectable({ providedIn: 'root' })
export class QuizJoinService {
  private readonly socketService = inject(SocketService);
  status$ = this.socketService.listenToEvent<StatusEvent>('status');
  question$ = this.socketService.listenToEvent<QuestionEvent>('newQuestion');
  joinDetails$ = this.socketService.listenToEvent<ClientJoinDetails>('joinDetails')

  joinQuiz(id: string){
      this.socketService.sendEvent<ClientJoinEvent>('join', { executionId: id });
  }
}

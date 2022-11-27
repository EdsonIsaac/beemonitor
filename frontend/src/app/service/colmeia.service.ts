import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Colmeia } from '../model/colmeia.model';

@Injectable({
  providedIn: 'root'
})
export class ColmeiaService {

  constructor(private http: HttpClient) { }

  delete (id: number): Observable<Colmeia> {
    return this.http.delete<Colmeia>(environment.serverURL + '/colmeias/' + id);
  }

  findAll (): Observable<Array<Colmeia>> {
    return this.http.get<Array<Colmeia>>(environment.serverURL + '/colmeias');
  }

  findAllWithOneMedicao(): Observable<Array<Colmeia>> {
    return this.http.get<Array<Colmeia>>(environment.serverURL + '/colmeias?oneMedicao=true');
  }

  findById (id: number): Observable<Colmeia> {
    return this.http.get<Colmeia>(environment.serverURL + '/colmeias/' + id);
  }
  
  save (colmeia: Colmeia): Observable<Colmeia> {
    return this.http.post<Colmeia>(environment.serverURL + '/colmeias', colmeia);
  }

  update (colmeia: Colmeia): Observable<Colmeia> {
    return this.http.put<Colmeia>(environment.serverURL + '/colmeias/' + colmeia.id, colmeia);
  }
}
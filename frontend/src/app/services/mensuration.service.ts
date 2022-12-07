import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Mensuration } from '../entities/mensuration';

@Injectable({
  providedIn: 'root'
})
export class MensurationService {

  constructor(
    private http: HttpClient
  ) { }

  getMensurations(hiveId: string, size: number) {
    return this.http.get<Array<Mensuration>>(environment.apiURL + '/mensurations/search?hiveId=' + hiveId + '&size=' + size);
  }
}

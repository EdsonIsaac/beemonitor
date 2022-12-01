import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Hive } from '../entities/hive';

@Injectable({
  providedIn: 'root'
})
export class HiveService {

  constructor(
    private http: HttpClient
  ) { }

  findAll() {
    return this.http.get<Array<Hive>>(environment.apiURL + '/hives');
  }
}
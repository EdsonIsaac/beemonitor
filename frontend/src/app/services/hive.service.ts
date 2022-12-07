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

  /**
   * 
   * @param hive 
   * @returns 
   */
  delete(hive: Hive) {
    return this.http.delete(environment.apiURL + '/hives/' + hive.id);
  }

  /**
   * 
   * @returns 
   */
  findAll() {
    return this.http.get<Array<Hive>>(environment.apiURL + '/hives');
  }

  /**
   * 
   * @param id 
   * @returns 
   */
  findById(id: string) {
    return this.http.get<Hive>(environment.apiURL + '/hives/' + id);
  }

  /**
   * 
   * @param hive 
   * @returns 
   */
  save(hive: Hive) {
    return this.http.post<Hive>(environment.apiURL + '/hives', hive);
  }

  /**
   * 
   * @param hive 
   * @returns 
   */
   update(hive: Hive) {
    return this.http.put<Hive>(environment.apiURL + '/hives/' + hive.id, hive);
  }
}
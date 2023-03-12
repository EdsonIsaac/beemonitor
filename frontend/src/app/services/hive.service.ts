import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

import { Hive } from '../entities/hive';
import { Page } from '../entities/page';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HiveService {

  private _baseURL = environment.apiURL + '/hives';
  private _subject = new BehaviorSubject<Hive | null>(null);

  constructor(
    private http: HttpClient
  ) { }

  /**
   * 
   * @param hive 
   * @returns 
   */
  delete(hive: Hive) {
    return this.http.delete(this._baseURL + '/' + hive.id);
  }

  /**
   * 
   * @param page 
   * @param size 
   * @param sort 
   * @param direction 
   * @returns 
   */
  findAll(page: number, size: number, sort: string, direction: string) {
    
    return this.http.get<Page<Hive>>(this._baseURL, {  
      params: {
        page: page,
        size: size, 
        sort: sort,
        direction: direction
      }
    });
  }

  /**
   * 
   * @param id 
   * @returns 
   */
  findById(id: string) {
    return this.http.get<Hive>(this._baseURL + '/' + id);
  }
  
  /**
   * 
   * @returns 
   */
  get() {
    return this._subject.asObservable();
  }

  /**
   * 
   * @param hive 
   * @returns 
   */
  save(hive: Hive) {
    return this.http.post<Hive>(this._baseURL, hive);
  }

  /**
   * 
   * @param value 
   * @param page 
   * @param size 
   * @param sort 
   * @param direction 
   * @returns 
   */
  search(value: string, page: number, size: number, sort: string, direction: string) {
    
    return this.http.get<Page<Hive>>(this._baseURL + '/search', {
      
      params: {
        value: value,
        page: page,
        size: size, 
        sort: sort,
        direction: direction
      }
    });
  }

  /**
   * 
   * @param hive 
   */
  set(hive: Hive) {
    this._subject.next(hive);
  }

  /**
   * 
   * @param hive 
   * @returns 
   */
   update(hive: Hive) {
    return this.http.put<Hive>(this._baseURL + '/' + hive.id, hive);
  }
}
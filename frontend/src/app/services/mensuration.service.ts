import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

import { Mensuration } from '../entities/mensuration';
import { Page } from '../entities/page';

@Injectable({
  providedIn: 'root'
})
export class MensurationService {

  private _baseURL = environment.apiURL + '/mensurations';

  constructor(
    private http: HttpClient
  ) { }

  /**
   * 
   * @param hiveId 
   * @param page 
   * @param size 
   * @param sort 
   * @param direction 
   * @returns 
   */
  findAll(hiveId: string, page: number, size: number, sort: string, direction: string) {

    return this.http.get<Page<Mensuration>>(this._baseURL, {
      
      params: {
        hiveId: hiveId,
        page: page,
        size: size, 
        sort: sort,
        direction: direction
      }
    });
  }

  /**
   * 
   * @param hiveId 
   * @param date 
   * @param page 
   * @param size 
   * @param sort 
   * @param direction 
   * @returns 
   */
  search(hiveId: string, date: string, page: number, size: number, sort: string, direction: string) {

    return this.http.get<Page<Mensuration>>(this._baseURL + '/search', {
      
      params: {
        hiveId: hiveId,
        date: date,
        page: page,
        size: size, 
        sort: sort,
        direction: direction
      }
    });
  }
}
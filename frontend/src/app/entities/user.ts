import { Authority } from '../enums/authority';
import { AbstractEntity } from './abstract-entity';
import { Image } from './image';

export interface User extends AbstractEntity {
  name: string;
  username: string;
  password: string;
  enabled: boolean;
  authorities: Array<Authority>;
  photo: Image;
}

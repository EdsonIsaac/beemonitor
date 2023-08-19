import { Department } from '../enums/department';
import { AbstractEntity } from './abstract-entity';
import { Image } from './image';

export interface User extends AbstractEntity {
  name: string;
  username: string;
  password: string;
  enabled: boolean;
  department: Department;
  photo: Image;
}

import { Department } from '../enums/department';
import { AbstractModel } from './abstract-model';
import { Image } from './image';

export interface User extends AbstractModel {
	name: string;
	username: string;
	password: string;
	enabled: boolean;
	department: Department;
	photo: Image;
}
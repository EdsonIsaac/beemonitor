import { AbstractModel } from "./abstract-model";

export interface Image extends AbstractModel {
    name: string;
    path: string;
}
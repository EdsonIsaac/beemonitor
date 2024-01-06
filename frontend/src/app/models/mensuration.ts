import { AbstractModel } from "./abstract-model";

export interface Mensuration extends AbstractModel {
    temperature: number;
    humidity: number;
    weight: number;
}
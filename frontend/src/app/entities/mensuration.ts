import { AbstractEntity } from "./abstract-entity";

export interface Mensuration extends AbstractEntity {
    temperature: number;
    humidity: number;
    weight: number;
}
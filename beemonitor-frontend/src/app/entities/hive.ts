import { AbstractEntity } from "./abstract-entity";
import { Mensuration } from "./mensuration";

export interface Hive extends AbstractEntity {
    code: string;
    mensuration: Array<Mensuration>;
}
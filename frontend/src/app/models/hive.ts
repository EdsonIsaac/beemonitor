import { AbstractModel } from "./abstract-model";
import { Mensuration } from "./mensuration";

export interface Hive extends AbstractModel {
    code: string;
    mensurations: Array<Mensuration>;
}
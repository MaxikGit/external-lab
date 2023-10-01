import {Employee} from "../EuropeCompany/Employee";

export interface ILocation {
    addPerson(person: any): void;
    getPerson(index: number): Employee;
    getCount(): number;
}

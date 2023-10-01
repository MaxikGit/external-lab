import { ILocation } from "./ILocation";
import {Employee} from "../EuropeCompany/Employee";

export class CompanyLocationArray implements ILocation {
    private people: Employee[];

    constructor() {
        this.people = [];
    }

    addPerson(person: Employee): void {
        this.people.push(person);
    }

    getPerson(index: number): Employee {
        return this.people[index];
    }

    getCount(): number {
        return this.people.length;
    }
}
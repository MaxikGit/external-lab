import {ILocation} from "./ILocation";
import {Employee} from "../EuropeCompany/Employee";

export class CompanyLocationLocalStorage implements ILocation {
    private readonly storageKey: string;
    private readonly employees: Employee[];

    constructor(storageKey: string) {
        this.storageKey = storageKey;
        const storedPeople = localStorage.getItem(this.storageKey);
        this.employees = storedPeople ? this.parseEmployees(JSON.parse(storedPeople)) : [];
    }

    private parseEmployees(personDataArr: string[]): Employee[]{
        return personDataArr.map((personData: any) =>
            new Employee(personData.name, personData.currentProject));
    }

    addPerson(person: Employee): void {
        this.employees.push(person);
        localStorage.setItem(this.storageKey, JSON.stringify(this.employees));
    }



    getPerson(index: number): Employee {
        return this.employees[index];

    }

    getCount(): number {
        return this.employees.length;
    }
}
import { ILocation } from "./ILocation";
import {Employee} from "../EuropeCompany/Employee";

export class Company {
    private location: ILocation;

    constructor(location: ILocation) {
        this.location = location;
    }

    addEmployee(employee: any): void {
        this.location.addPerson(employee);
    }

    getProjectList(): string[] {
        const employees = this.getAllEmployees();
        return employees.map((employee: Employee) => employee.getCurrentProject());
    }

    getNameList(): string[] {
        const employees = this.getAllEmployees();
        return employees.map((employee: Employee) => employee.getName());
    }

    private getAllEmployees(): Employee[] {
        const employeeCount = this.location.getCount();
        const employees: Employee[] = [];
        for (let i = 0; i < employeeCount; i++) {
            const employee = this.location.getPerson(i);
            employees.push(employee);
        }
        return employees;
    }
}
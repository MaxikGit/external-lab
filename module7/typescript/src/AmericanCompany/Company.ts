import { IEmployee } from "./IEmployee";

export class Company {
    private employees: IEmployee[];

    constructor() {
        this.employees = [];
    }

    add(employee: IEmployee): void {
        this.employees.push(employee);
    }

    getProjectList(): string[] {
        return this.employees.map((employee) => employee.getCurrentProject());
    }

    getNameList(): string[] {
        return this.employees.map((employee) => employee.getName());
    }
}
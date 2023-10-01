import {Employee} from "./Employee"

export class Company {
    employees: Employee[];

    constructor() {
        this.employees = [];
    }

    add(employee: Employee): void{
        this.employees.push(employee);
    }

    getProjectList(){
        return this.employees.map((employee) => employee.getCurrentProject());
    }

    getNameList(){
        return this.employees.map((employee) => employee.getName());
    }
}
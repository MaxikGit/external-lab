import { Employee } from "./Employee";

export class Frontend extends Employee {
    constructor(name: string, currentProject: string) {
        super(name, currentProject);
    }
}
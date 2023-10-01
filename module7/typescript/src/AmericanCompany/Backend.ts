import { IEmployee } from "./IEmployee";

export class Backend implements IEmployee {
    private readonly name: string;
    private readonly currentProject: string;

    constructor(name: string, currentProject: string) {
        this.name = name;
        this.currentProject = currentProject;
    }

    getCurrentProject(): string {
        return this.currentProject;
    }

    getName(): string {
        return this.name;
    }
}
export class Employee {

    private readonly name: string;
    private readonly currentProject: string;

    constructor(name = "employee", currentProject = "secret") {
        this.name = name;
        this.currentProject = currentProject;
    }

    getCurrentProject() {
        return this.currentProject;
    }

    getName(){
        return this.name;
    }
}
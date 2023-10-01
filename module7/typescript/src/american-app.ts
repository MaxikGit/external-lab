import {Frontend} from "./AmericanCompany/Frontend";
import {Backend} from "./AmericanCompany/Backend";
import {Company} from "./AmericanCompany/Company";

const americanCompany = new Company();

const frontendEmployee1 = new Frontend("John Doe", "Frontend Project A");
const frontendEmployee2 = new Frontend("Jane Smith", "Frontend Project B");
const backendEmployee1 = new Backend("Bob Johnson", "Backend Project A");
const backendEmployee2 = new Backend("Alice Brown", "Backend Project B");

americanCompany.add(frontendEmployee1);
americanCompany.add(frontendEmployee2);
americanCompany.add(backendEmployee1);
americanCompany.add(backendEmployee2);

console.log("American company")
console.log("Project List:", americanCompany.getProjectList());
console.log("Name List:", americanCompany.getNameList());
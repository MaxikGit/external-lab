import { Company } from "./BritishCompany/Company";
import { CompanyLocationArray } from "./BritishCompany/CompanyLocationArray";
import { CompanyLocationLocalStorage } from "./BritishCompany/CompanyLocationLocalStorage";
import { Employee } from "./EuropeCompany/Employee";

const company1 = new Company(new CompanyLocationArray());
const company2 = new Company(new CompanyLocationLocalStorage("company2_employees"));

const employee1 = new Employee("John Doe", "Project A");
const employee2 = new Employee("Jane Smith", "Project B");
const employee3 = new Employee("Bob Johnson", "Project A");
const employee4 = new Employee("Alice Brown", "Project B");
const employee5 = new Employee("Alik Yells", "Project C");

company1.addEmployee(employee1);
company1.addEmployee(employee2);

company2.addEmployee(employee3);
company2.addEmployee(employee4);
company2.addEmployee(employee5);

console.log("british app");
console.log("Company 1 Project List:", company1.getProjectList());
console.log("Company 1 Name List:", company1.getNameList());

console.log("Company 2 Project List:", company2.getProjectList());
console.log("Company 2 Name List:", company2.getNameList());

//clean local storage
localStorage.removeItem("company2_employees");

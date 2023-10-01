import {Frontend} from "./EuropeCompany/Frontend";
import {Backend} from "./EuropeCompany/Backend";
import {Company} from "./EuropeCompany/Company";

const europeCompany = new Company();

const frontendEmployee1 = new Frontend("John Doe", "Frontend Project A");
const frontendEmployee2 = new Frontend("Jane Smith", "Frontend Project B");
const backendEmployee1 = new Backend("Bob Johnson", "Backend Project A");
const backendEmployee2 = new Backend("Alice Brown", "Backend Project B");

europeCompany.add(frontendEmployee1);
europeCompany.add(frontendEmployee2);
europeCompany.add(backendEmployee1);
europeCompany.add(backendEmployee2);

console.log("European company")
console.log("Project List:", europeCompany.getProjectList());
console.log("Name List:", europeCompany.getNameList());
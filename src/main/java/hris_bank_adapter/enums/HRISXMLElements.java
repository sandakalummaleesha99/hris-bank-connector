package hris_bank_adapter.enums;

import lombok.Getter;

@Getter
public enum HRISXMLElements {

    HEADER("Header"),
    EMPLOYEE_INFO("EmployeeInfo"),
    FOOTER("Footer"),
    COMPANY_REG_NAME("CompanyRegName"),
    COMPANY_REG_NO("CompanyRegNo"),
    EPF_NO("EpfNo"),
    FIRST_NAME("FirstName"),
    LAST_NAME("LastName"),
    MIDDLE_NAME("MiddleName"),
    NIC("NIC"),
    BANK_INFO("BankInfo"),
    BANK_NAME("BankName"),
    BANK_ACCOUNT_NO("BankAccount"),
    BANK_CODE("BankCode"),
    BANK_BRANCH_CODE("BranchCode"),
    SALARY("Salary"),
    CTRL_SUM("CtrlSum");

    HRISXMLElements(String element) {
        this.element = element;
    }

    private final String element;
}

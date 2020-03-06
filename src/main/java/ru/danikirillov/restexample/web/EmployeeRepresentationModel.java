package ru.danikirillov.restexample.web;

import org.springframework.hateoas.RepresentationModel;
import ru.danikirillov.restexample.payroll.Employee;

public class EmployeeRepresentationModel extends RepresentationModel<EmployeeRepresentationModel> {
    private final String name;
    private final String role;

    public EmployeeRepresentationModel(Employee employee) {
        this.name = employee.getName();
        this.role = employee.getRole();
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}

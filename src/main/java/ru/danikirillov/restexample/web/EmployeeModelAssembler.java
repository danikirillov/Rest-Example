package ru.danikirillov.restexample.web;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;
import ru.danikirillov.restexample.payroll.Employee;

@Component
public class EmployeeModelAssembler extends RepresentationModelAssemblerSupport<Employee, EmployeeRepresentationModel> {

    public EmployeeModelAssembler() {
        super(EmployeeController.class, EmployeeRepresentationModel.class);
    }

    @Override
    public EmployeeRepresentationModel toModel(Employee employee) {
        return createModelWithId(employee.getId(), employee);
    }

    @Override
    protected EmployeeRepresentationModel instantiateModel(Employee employee) {
        return new EmployeeRepresentationModel(employee);
    }
}

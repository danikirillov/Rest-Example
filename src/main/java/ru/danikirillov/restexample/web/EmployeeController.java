package ru.danikirillov.restexample.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.danikirillov.restexample.data.EmployeeRepository;
import ru.danikirillov.restexample.exceptions.EmployeeNotFoundException;
import ru.danikirillov.restexample.payroll.Employee;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/employees", produces = "application/hal+json")
public class EmployeeController {
    private final EmployeeRepository repository;
    private final EmployeeModelAssembler employeeModelAssembler;


    public EmployeeController(EmployeeRepository repository, EmployeeModelAssembler employeeModelAssembler) {
        this.repository = repository;
        this.employeeModelAssembler = employeeModelAssembler;
    }

    @GetMapping
    public ResponseEntity<CollectionModel<EmployeeRepresentationModel>> all() {
        CollectionModel<EmployeeRepresentationModel> allEmployees = employeeModelAssembler.toCollectionModel(repository.findAll());
        allEmployees.add(linkTo(methodOn(EmployeeController.class).all()).withRel("all"));
        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<EmployeeRepresentationModel> newEmployee(@RequestBody Employee employee) {
        EmployeeRepresentationModel newEmployee = employeeModelAssembler.toModel(repository.save(employee));
        newEmployee.add(linkTo(methodOn(EmployeeController.class).newEmployee(employee)).withRel("newEmployee"));
        return new ResponseEntity<>(newEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<EmployeeRepresentationModel> one(@PathVariable Long id) {
        EmployeeRepresentationModel one = employeeModelAssembler
                .toModel(repository.findById(id)
                        .orElseThrow(() -> new EmployeeNotFoundException(id)));
        one.add(linkTo(methodOn(EmployeeController.class).one(id)).withRel("one"));
        return new ResponseEntity<>(one, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    ResponseEntity<EmployeeRepresentationModel> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        EmployeeRepresentationModel replacedEmployee = employeeModelAssembler.toModel(repository.findById(id)
                .map(employee -> {
                    employee.setName(newEmployee.getName());
                    employee.setRole(newEmployee.getRole());
                    return repository.save(employee);
                })
                .orElseGet(() -> {
                    newEmployee.setId(id);
                    return repository.save(newEmployee);
                }));
        replacedEmployee.add(linkTo(methodOn(EmployeeController.class).replaceEmployee(newEmployee, id)).withRel("replaceEmployee"));
        return new ResponseEntity<>(replacedEmployee, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteEmployee(@PathVariable Long id) {
        if (repository.existsById(id))
            repository.deleteById(id);
        else
            throw new EmployeeNotFoundException(id);
        return ResponseEntity.noContent().build();
    }
}

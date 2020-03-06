package ru.danikirillov.restexample.data;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.danikirillov.restexample.payroll.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}

package ru.danikirillov.restexample.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.danikirillov.restexample.exceptions.EmployeeNotFoundException;

@ControllerAdvice
//аннотация для обработки ошибок
public class EmployeeNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(EmployeeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(EmployeeNotFoundException e) {
        return e.getMessage();
    }
}

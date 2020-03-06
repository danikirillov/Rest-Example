package ru.danikirillov.restexample.data;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.danikirillov.restexample.payroll.Employee;

@Configuration
public class LoadDb {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(LoadDb.class);

    @Bean
    //Spring выполнит все CommandLineRunner методы при загрузке приложения
    CommandLineRunner initDb(EmployeeRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Employee("Danzello", "crook")));
            log.info("Preloading " + repository.save(new Employee("Dimon", "druid")));
            log.info("Preloading " + repository.save(new Employee("Sashket", "mage")));
            log.info("Preloading " + repository.save(new Employee("Nekit", "barbarian")));
            log.info("Preloading " + repository.save(new Employee("Sergeo", "gm")));
        };
    }
}

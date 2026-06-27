package com.backend.backend;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.backend.backend.service.CsvImportService;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    CommandLineRunner run(CsvImportService csvImportService) {
        return args -> {
            // csvImportService.importPlayers();
            // csvImportService.importPlayerPerformance();
            // csvImportService.importNationalPerformance();
            // csvImportService.importMatches();
            // csvImportService.importLeagueTables();
            // csvImportService.importGoalLeaders();
            // csvImportService.importAssistLeaders();
            csvImportService.importTeamDiscipline();
        };
    }
}
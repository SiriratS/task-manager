package com.example.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main application class for the Task Manager.
 */
@SpringBootApplication
public class TaskManagerApplication {

    /**
     * Main method to start the application.
     * Loads environment variables from .env file if present.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Load .env file if it exists
        io.github.cdimascio.dotenv.Dotenv dotenv = io.github.cdimascio.dotenv.Dotenv
                .configure()
                .ignoreIfMissing()
                .load();

        // Set Spring properties from .env
        dotenv.entries().forEach(entry -> {
            String key = entry.getKey();
            String value = entry.getValue();
            // Convert to Spring property format
            String springKey = key.toLowerCase().replace("_", ".");
            System.setProperty(springKey, value);
        });

        SpringApplication.run(TaskManagerApplication.class, args);
    }

}

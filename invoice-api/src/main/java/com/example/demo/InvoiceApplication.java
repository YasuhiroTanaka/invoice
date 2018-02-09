package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InvoiceApplication {
    /** CLASS. */
    private static final String CLASS = InvoiceApplication.class.getSimpleName();

    public static void main(String[] args) {
        DebugLog.enter(CLASS + ".main");
        SpringApplication.run(InvoiceApplication.class, args);
        DebugLog.exit(CLASS + ".main");
    }
}

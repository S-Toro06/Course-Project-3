package com.example.pricing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PricingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PricingServiceApplication.class, args);

        System.out.println("""
            
            
                                  PRICING SERVICE STARTED                                                      
                                                                           
              Available at: http://localhost:8082/api/pricing               
      
            """);
    }
}

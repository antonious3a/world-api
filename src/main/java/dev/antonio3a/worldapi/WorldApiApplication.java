package dev.antonio3a.worldapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class WorldApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorldApiApplication.class, args);
    }

}

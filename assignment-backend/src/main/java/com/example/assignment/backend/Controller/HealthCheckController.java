package com.example.assignment.backend.Controller;



import lombok.RequiredArgsConstructor;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/health")
@RequiredArgsConstructor
public class HealthCheckController {



    private final Neo4jClient neo4jClient;

     @GetMapping
    public String checkConnection() {
        return neo4jClient.query("RETURN 'Connected to Neo4j' AS result")
                .fetchAs(String.class)
                .one()
                .orElse("Not connected");
    }
}




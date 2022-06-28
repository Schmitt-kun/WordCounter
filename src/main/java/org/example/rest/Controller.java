package org.example.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

@RestController
public class Controller {
    private Logger logger = LoggerFactory.getLogger(Controller.class);

    @GetMapping
    public String testController() {
        return String.format("Now is: %s", new Date().toString());
    }

    @GetMapping("/")
    public ResponseEntity<String> get() {
        logger.info("Homepage");
        try {
            File f = new ClassPathResource("Front/Home.html").getFile();
            return ResponseEntity.ok(new String(Files.readAllBytes(f.toPath())));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getResource(@PathVariable String filename) {
        logger.info("Get file {}", filename);
        try {
            File f = new ClassPathResource("Front/" + filename).getFile();
            return ResponseEntity.ok(Files.readAllBytes(f.toPath()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

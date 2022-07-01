package ru.schmitt.wordcounter.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.schmitt.wordcounter.counter.Counter;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.Map;

@RestController
public class Controller {
    private final Logger logger = LoggerFactory.getLogger(Controller.class);

    @GetMapping
    public String testController() {
        return String.format("Now is: %s", new Date().toString());
    }

    @GetMapping("/")
    public ResponseEntity<String> get() {
        logger.info("Homepage");
        try {
            File f = new ClassPathResource("front/index.html").getFile();
            return ResponseEntity.ok(new String(Files.readAllBytes(f.toPath())));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{filename}")
    public ResponseEntity<byte[]> getResource(@PathVariable String filename) {
        logger.info("Get file '{}'", filename);
        if (filename.isEmpty()) {
            filename = "index.html";
        }
        try {
            File f = new ClassPathResource("front/" + filename).getFile();
            return ResponseEntity.ok(Files.readAllBytes(f.toPath()));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadText(@RequestParam String sourceText) {
        logger.info("Received text [{} chars]: {}", sourceText.length(), sourceText.substring(0, Math.min(sourceText.length(), 100)));
        Map<String, Integer> res = new Counter().count(sourceText);
        return  ResponseEntity.ok("{\"Different words\": " + res.size() + "}");
    }
}

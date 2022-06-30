package ru.schmitt.services;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CountTest {
    private final Logger logger = LoggerFactory.getLogger(CountTest.class);

    @Test
    public void test() throws Exception {
        logger.info("test start");
        File lotr = new File(this.getClass().getClassLoader().getResource("JRRT_-_The_Lord_of_the_Rings.txt").getFile());
        logger.info("{} {}", lotr.getName(), lotr.exists() ? "exists" : "not exists");
        Map<String, Integer> words = new HashMap<>();
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(lotr))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty())
                    continue;

                String[] wordsInLine = line.replaceAll("[.,?!()\"]","").toLowerCase().split(" ");
                for (String word : wordsInLine) {
                    if(!word.trim().isEmpty()) {
                        if (words.containsKey(word)) {
                            words.put(word, words.get(word) + 1);
                        } else {
                            words.put(word, 1);
                        }
                    }
                }
                i++;
                if (i==2000)
                    break;
            }
        }

        Map<String, Integer> result = words.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            logger.info("{} {}", entry.getKey(), entry.getValue());
        }
        logger.info("test finished");
    }
}

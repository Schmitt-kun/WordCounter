package ru.schmitt.wordcounter.counter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Counter {
    private final Logger logger = LoggerFactory.getLogger(Counter.class);
    public Map<String, Integer> count(String text) {
        Map<String, Integer> words = new HashMap<>();

        String[] wordsInLine = text.replaceAll("[.,?!()\"]","").toLowerCase().split(" ");
        for (String word : wordsInLine) {
            if(!word.trim().isEmpty()) {
                if (words.containsKey(word)) {
                    words.put(word, words.get(word) + 1);
                } else {
                    words.put(word, 1);
                }
            }
        }

        Map<String, Integer> result = words.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        for (Map.Entry<String, Integer> entry : result.entrySet()) {
            logger.info("{} {}", entry.getKey(), entry.getValue());
        }

        return result;
    }
}

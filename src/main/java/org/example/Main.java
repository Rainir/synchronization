package org.example;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Main {
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();
    public static void main(String[] args) throws InterruptedException {
        Runnable runnable = () -> {
            synchronized (sizeToFreq) {
                int countR = StringUtils.countMatches(generateRoute("RLRFR", 100), 'R');
                int value;
                if (sizeToFreq.get(countR) == null) {
                    sizeToFreq.put(countR, 1);
                } else {
                    value = sizeToFreq.get(countR);
                    value++;
                    sizeToFreq.replace(countR, value);
                }
            }
        };

        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(runnable);
            thread.start();
            thread.join();
        }
        int maxValue = Collections.max(sizeToFreq.values());
        int maxKey = 0;
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getValue() == maxValue) {
                maxKey = entry.getKey();
            }
        }
        int entres = maxValue;
        System.out.println("Самое частое количество повторений: " + maxKey + " (встретилось " + maxValue + " раз)");
        System.out.println("Другие размеры:");
        for (Map.Entry<Integer, Integer> entry : sizeToFreq.entrySet()) {
            if (entry.getKey() == maxKey) {
                continue;
            } else {
                System.out.println("- " + entry.getKey() + ("(") + entry.getValue() + " раз)");
            }
            entres += entry.getValue();
        }
        System.out.println(entres);
    }

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }
}
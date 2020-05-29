package com.kyriba.log.reducer;

import com.kyriba.log.reducer.config.ConsoleMenu;
import com.kyriba.log.reducer.processor.FileProcessor;

public class Main {

    public static void main(String[] args) {
        try {
            ConsoleMenu menu = new ConsoleMenu();
            menu.startMenu();
            FileProcessor processor = new FileProcessor(menu.getConfig());
            long start = System.currentTimeMillis();
            processor.process();
            System.out.println("Processing ended with duration: " + (System.currentTimeMillis() - start));
        } catch (Throwable ex) {
            System.out.println("Oops,smth went Wrong: " + ex.getMessage());
        }
    }
}

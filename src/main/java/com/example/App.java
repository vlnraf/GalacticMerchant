package com.example;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import com.example.customexceptions.MaxRepeatingException;
import com.example.customexceptions.NonRepeatingException;
import com.example.customexceptions.UnknownWordException;

public final class App {
    private App() {}

    public static void main(String[] args) {
        QueryDispatcher queryDispatcher = new QueryDispatcher();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("inputFile")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                try {
                    queryDispatcher.parseQuery(line);
                } catch (UnknownWordException | MaxRepeatingException | NonRepeatingException e) {
                    System.out.println("Error processing line: \"" + line + "\"");
                    System.out.println("Reason: " + e.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Input file not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}

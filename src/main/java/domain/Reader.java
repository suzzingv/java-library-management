package domain;

import exception.FileReadException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Reader {
    public static Scanner sc = new Scanner(System.in);
    public static Scanner fc;

    static {
        try {
            fc = new Scanner(new File("src/main/resources/library.csv"));
        } catch (FileNotFoundException e) {
            throw new FileReadException();
        }
    }
}
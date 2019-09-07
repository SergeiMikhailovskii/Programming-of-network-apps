package com.mikhailovskii.lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Menu {

    static int showStartMenu() {
        System.out.println("1 - add a book with default info");
        System.out.println("2 - add a book and input info by self");
        System.out.println("3 - get total cost of all created books");
        System.out.println("4 - get total amount of books with images");
        System.out.println("5 - show data from file");
        System.out.println("0 - exit");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int result = -1;   //-1 by default is value, which is not in list, so user can't choose it

        try {
            result = Integer.parseInt(in.readLine());
        } catch (IOException e) {
            System.out.println("Wrong input!");
        }

        return result;
    }

    static boolean isBookSavedToFileMenu() {
        System.out.println("Do you want to save book info to file?");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        boolean result = false;

        try {
            result = in.readLine().equalsIgnoreCase("yes");
        } catch (IOException e) {
            System.out.println("Wrong input!");
        }

        return result;
    }

}

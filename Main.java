package com.mikhailovskii.lab1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        int menuChoose;
        do {
            menuChoose = Menu.showStartMenu();
            switch (menuChoose) {
                case 1:
                    Book defaultBook = new Book();
                    System.out.println("Default book is added!");
                    if (Menu.isBookSavedToFileMenu()) {
                        defaultBook.saveBookToFile();
                    }
                    break;
                case 2:
                    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        System.out.println("Enter book name: ");
                        String bookName = in.readLine();
                        System.out.println("Enter book price: ");
                        int bookPrice = Integer.parseInt(in.readLine());
                        System.out.println("Is book with images: ");
                        boolean isBookWithImages = in.readLine().equalsIgnoreCase("yes");
                        Book customBook = new Book(bookName, bookPrice, isBookWithImages);
                        if (Menu.isBookSavedToFileMenu()) {
                            customBook.saveBookToFile();
                        }
                    } catch (IOException e) {
                        System.out.println("Wrong input!");
                    }
                    System.out.println("Enter the name");
                    break;
                case 3:
                    System.out.println("Total cost = " + Book.getTotalCost());
                    break;
                case 4:
                    System.out.println("Total amount of books with images = " + Book.getBooksWithImagesAmount());
                    break;
                case 5:
                    Book.getBooksFromFile();
                    break;
                default:
                    break;
            }

        } while (menuChoose != 0);
    }
}

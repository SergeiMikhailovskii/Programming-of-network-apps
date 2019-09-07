package com.mikhailovskii.lab1;

import java.io.*;

public class Book {

    private static double sTotalCost;
    private static int sBooksWithImagesAmount;

    private String mBookName;
    private double mBookPrice;
    private boolean mIsBookWithImages;

    static double getTotalCost() {
        return sTotalCost;
    }

    static int getBooksWithImagesAmount() {
        return sBooksWithImagesAmount;
    }

    static void getBooksFromFile() {
        File file = new File("output.txt");
        try {
            FileReader fileReader = new FileReader(file);
            int numb;   //for check if file is end
            char[] buffer = new char[1];    //Creating 1-symbol array, as method read in line 30 needs it as parameter.

            do {
                numb = fileReader.read(buffer);
                System.out.print(buffer[0]);
            } while (numb != -1);

        } catch (FileNotFoundException e) {
            System.out.println("Error while working with file!");
        } catch (IOException e) {
            System.out.println("Error while reading data from file!");
        }
    }

    Book() {
        this.mBookName = "Steve Jobs";
        this.mBookPrice = 15;
        this.mIsBookWithImages = true;

        sTotalCost += mBookPrice;
        sBooksWithImagesAmount++;
    }

    Book(String mBookName, double mBookPrice, boolean mIsBookWithImages) {
        this.mBookName = mBookName;
        this.mBookPrice = mBookPrice;
        this.mIsBookWithImages = mIsBookWithImages;

        sTotalCost += mBookPrice;

        if (this.mIsBookWithImages) {
            sBooksWithImagesAmount++;
        }
    }

    public String getBookName() {
        return mBookName;
    }

    public void setBookName(String mBookName) {
        this.mBookName = mBookName;
    }

    public double getBookPrice() {
        return mBookPrice;
    }

    public void setBookPrice(double bookPrice) {
        this.mBookPrice = bookPrice;
    }

    public boolean isIsBookWithImages() {
        return mIsBookWithImages;
    }

    public void setIsBookWithImages(boolean mIsBookWithImages) {
        this.mIsBookWithImages = mIsBookWithImages;
    }

    void saveBookToFile() {
        File file = new File("output.txt");
        file.deleteOnExit();

        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.append("Name = ")
                    .append(this.mBookName)
                    .append(", Price = ")
                    .append(String.valueOf(this.mBookPrice))
                    .append(", isWithImages = ")
                    .append(String.valueOf(this.mIsBookWithImages))
                    .append("\n");
            fileWriter.flush();
            fileWriter.close();
            System.out.println("Info saved!");
        } catch (IOException e) {
            System.out.println("Error while working with file!");
        }
    }

}

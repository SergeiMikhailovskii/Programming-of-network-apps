package com.mikhailovskii.lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static int timeRefuelEachCar;
    private static int timeOfWork;
    private static int timeOfWorkToBreak;

    private static int carsTotal = 0;

    public static void main(String[] args) {

        //time of break on each gas station
        final int timeOfBreak1 = 10;
        final int timeOfBreak2 = 15;
        final int timeOfBreak3 = 5;
        final int timeOfBreak4 = 13;

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {

            System.out.println("Enter the time of refueling each car: ");
            timeRefuelEachCar = Integer.parseInt(bufferedReader.readLine());

            System.out.println("Enter time of work of gas station: ");
            timeOfWork = Integer.parseInt(bufferedReader.readLine());

            System.out.println("Enter time station work to break: ");
            timeOfWorkToBreak = Integer.parseInt(bufferedReader.readLine());

            Thread firstStation = new Thread(() -> System.out.println(calculateCarsOnStation(timeOfBreak1)));

            Thread secondStation = new Thread(() -> System.out.println(calculateCarsOnStation(timeOfBreak2)));

            Thread thirdStation = new Thread(() -> System.out.println(calculateCarsOnStation(timeOfBreak3)));

            Thread forthStation = new Thread(() -> System.out.println(calculateCarsOnStation(timeOfBreak4)));

            firstStation.start();
            secondStation.start();
            thirdStation.start();
            forthStation.start();

            firstStation.join();
            secondStation.join();
            thirdStation.join();
            forthStation.join();

            System.out.println("Total: " + carsTotal);

        } catch (IOException e) {
            System.out.println("Wrong input!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private static int calculateCarsOnStation(int timeOfBreakI) {
        int carAmountPerIteration = timeOfWorkToBreak / timeRefuelEachCar;
        int timeIteration = timeRefuelEachCar * carAmountPerIteration + timeOfBreakI;
        int iterationsAmount = timeOfWork / timeIteration;
        int leftTime = timeOfWork - timeIteration * iterationsAmount;
        int leftCars = leftTime / timeRefuelEachCar;
        int carsOnStation = carAmountPerIteration * iterationsAmount + leftCars;
        carsTotal+=carsOnStation;
        return carsOnStation;
    }

}

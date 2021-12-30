/**********************************************
Workshop #8
Course:JAC444 - Semester 2020
Last Name: Moss
First Name: Tanner
ID: 017896150
Section: NDD
This assignment represents my own work in accordance with Seneca Academic Policy.
Tanner Douglas Moss
Date: 11/24/2020
**********************************************/
package ca.task1;

public class App {
    static final int ARRAY_SIZES = 2000;

    public static void main(String[] args) {

        double[][] a = load(ARRAY_SIZES);
        double[][] b = load(ARRAY_SIZES);

        System.out.println("\nParallel addition beginning...");
        long pStart = System.nanoTime();
        double[][] x = parallelAddMatrix(a, b);
        long pEnd = System.nanoTime();
        System.out.println("Time taken in nanoTime: " + (pEnd - pStart));

        System.out.println("\nParallel addition Results:\n");
        System.out.println("A array index 0 first 5 values:");
        for (int i = 0; i < 5; i++)
            System.out.printf("%.2f ", a[0][i]);
        System.out.printf("- Final index final entry: %.2f", a[ARRAY_SIZES - 1][ARRAY_SIZES - 1]);
        System.out.println("\n");

        System.out.println("B array index 0 first 5 values:");
        for (int i = 0; i < 5; i++)
            System.out.printf("%.2f ", b[0][i]);
        System.out.printf("- Final index final entry: %.2f", b[ARRAY_SIZES - 1][ARRAY_SIZES - 1]);
        System.out.println("\n");

        System.out.println("Sum array index 0 first 5 values:");
        for (int i = 0; i < 5; i++)
            System.out.printf("%.2f ", x[0][i]);
        System.out.printf("- Final index final entry: %.2f", x[ARRAY_SIZES - 1][ARRAY_SIZES - 1]);
        System.out.println("\n");

        // ---------------------------------//

        System.out.println("\nSequential addition beginning...");
        long start = System.nanoTime();
        double[][] y = sequentialAddMatrix(a, b);
        long end = System.nanoTime();
        System.out.println("Time taken in nanoTime: " + (end - start));

        System.out.println("\nSequential addition Results:\n");
        System.out.println("A array index 0 first 5 values:");
        for (int i = 0; i < 5; i++)
            System.out.printf("%.2f ", a[0][i]);
        System.out.printf("- Final index final entry: %.2f", a[ARRAY_SIZES - 1][ARRAY_SIZES - 1]);
        System.out.println("\n");

        System.out.println("B array index 0 first 5 values:");
        for (int i = 0; i < 5; i++)
            System.out.printf("%.2f ", b[0][i]);
        System.out.printf("- Final index final entry: %.2f", b[ARRAY_SIZES - 1][ARRAY_SIZES - 1]);
        System.out.println("\n");

        System.out.println("Sum array index 0 first 5 values:");
        for (int i = 0; i < 5; i++)
            System.out.printf("%.2f ", y[0][i]);
        System.out.printf("- Final index final entry: %.2f", y[ARRAY_SIZES - 1][ARRAY_SIZES - 1]);
        System.out.println("\n");
    }

    public static double[][] parallelAddMatrix(double[][] a, double[][] b) {
        double[][] temp = new double[ARRAY_SIZES][ARRAY_SIZES];
        int quarters = ARRAY_SIZES / 4; // 500

        Thread t1 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < quarters; i++) {
                    int index = 0;
                    for (double x : a[i]) {
                        temp[i][index] = x + b[i][index];
                        index++;
                    }
                }
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                for (int i = quarters; i < quarters * 2; i++) {
                    int index = 0;
                    for (double x : a[i]) {
                        temp[i][index] = x + b[i][index];
                        index++;
                    }
                }
            }
        };

        Thread t3 = new Thread() {
            @Override
            public void run() {
                for (int i = quarters * 2; i < quarters * 3; i++) {
                    int index = 0;
                    for (double x : a[i]) {
                        temp[i][index] = x + b[i][index];
                        index++;
                    }
                }
            }
        };

        Thread t4 = new Thread() {
            @Override
            public void run() {
                for (int i = quarters * 3; i < quarters * 4; i++) {
                    int index = 0;
                    for (double x : a[i]) {
                        temp[i][index] = x + b[i][index];
                        index++;
                    }
                }
            }
        };

        t1.start(); t2.start();
        t3.start(); t4.start();
        try {
            t1.join(); t2.join();
            t3.join(); t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return temp;
    }

    public static double[][] sequentialAddMatrix(double[][] c, double[][] d){
        double[][] temp = new double[ARRAY_SIZES][ARRAY_SIZES];

        for(int i = 0; i < ARRAY_SIZES; i++){
            int index = 0;
            for(double x: c[i]){
                temp[i][index] = x + d[i][index];
                index++;
            }
        }
        
        return temp;
    }

    public static double[][] load(int size){
        double[][] temp = new double[size][size];

        for(double[] x: temp)
            for(int i = 0; i < x.length; i++)
                x[i] = Math.random()*20;

        return temp;
    }

}

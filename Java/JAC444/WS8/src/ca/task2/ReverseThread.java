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
package ca.task2;

public class ReverseThread implements Runnable {
    int count = 0;
    int THREADS;

    public static void main(String[] args) {
        Thread t = new Thread(new ReverseThread(5));
        t.start();
    }

    ReverseThread(int threads){
        this.THREADS = threads;
    }

    @Override
    public void run() {
        this.count++;
        createThread();
        System.out.println("Hello from " + Thread.currentThread().getName());
    }

    public void createThread() {

        if (count < THREADS) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    count++;
                    createThread();
                    System.out.println("Hello from " + Thread.currentThread().getName());
                }
            };

            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
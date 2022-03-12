package com.company.juc.c_000;

public class HowToCreateThread {

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("hello MyThread!");
        }
    }

    static class MyRun implements Runnable {
        @Override
        public void run() {
            System.out.println("hello MyRun!");
        }
    }

    public static void main(String[] args) {
        new MyThread().start();
        new Thread(new MyRun()).start();
        new Thread(() -> {
            System.out.println("hello Lambda!");
        }).start();
    }

}


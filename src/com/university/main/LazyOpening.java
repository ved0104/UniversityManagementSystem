package com.university.main;

public class LazyOpening {
    public void open() throws InterruptedException{
        int i = 5;
        while (i > 0) {
            System.out.print(".");
            Thread.sleep(500);
            i--;
        }
    }
}

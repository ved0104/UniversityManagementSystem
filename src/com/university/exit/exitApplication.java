package com.university.exit;

public class exitApplication {
    public void exit() throws InterruptedException {
        System.out.print("Exiting the Application ");
        int i = 5;
        while (i > 0) {
            System.out.print(".");
            Thread.sleep(500);
            i--;
        }
    }
}

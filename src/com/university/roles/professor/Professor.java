package com.university.roles.professor;
import com.university.main.User;
import java.sql.Connection;
import java.util.Scanner;
//p23ai001@coed.svnit.ac.in
public class Professor extends User {
    Scanner sc=new Scanner(System.in);
    public Professor() {
        super();
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public boolean authenticate(String password) {
        return super.authenticate(password);
    }

    @Override
    public void displayMenu(Connection connection,Scanner sc) {
        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Exit");
            System.out.print("\nEnter your choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                        LoginProfessor lp = new LoginProfessor();
                        lp.login(connection,sc);
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Enter valid choice");
                    break;
            }
        }
    }
}

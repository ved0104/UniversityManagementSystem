package com.university.main;

import com.university.db.databaseConnection;
import com.university.exceptions.InvalidInputException;
import com.university.exit.exitApplication;
import com.university.roles.admin.Admin;
import com.university.roles.professor.Professor;
import com.university.roles.student.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws InterruptedException, SQLException {
        Scanner sc = new Scanner(System.in);
        Connection connection = null;
        try {
            databaseConnection dB = new databaseConnection();
            connection = dB.getConnection();

            System.out.println("Welcome to University Management System.");
            System.out.println("========================================");
            while (true) {
                System.out.println("========================================");
                System.out.print("Please Wait");
                LazyOpening lo = new LazyOpening();
                lo.open();

                System.out.println("\nEnter as: ");
                System.out.println("1. Student");
                System.out.println("2. Professor");
                System.out.println("3. Administrator");
                System.out.println("4. Exit");
                System.out.println();

                System.out.print("Enter Your Choice: ");
                try {
                    int choice = sc.nextInt();
                    sc.nextLine();

                    switch (choice) {
                        case 1:
                            //Login as student
                            System.out.println("As Student");
                            Student std = new Student();
                            std.displayMenu(connection,sc);
                            break;

                        case 2:
                            //Login as Professor
                            System.out.println("As Professor");
                            Professor pf = new Professor();
                            pf.displayMenu(connection,sc);
                            break;

                        case 3:
                            //Login as Admin
                            System.out.println("As Admin");
                            Admin adm = new Admin();
                            adm.displayMenu(connection,sc);
                            break;
                        case 4:
                            //Exit the system
                            exitApplication ext = new exitApplication();
                            ext.exit();
                            System.exit(0);
                        default:
                            System.out.println("Bad Choice! Please choose good choice this time.");
                            break;
                    }
                } catch (InputMismatchException ex) {
                    //Create Exception when user enter anything other than a number
                    throw new InvalidInputException("Invalid input! Please enter a valid number.");
                }
            }
        } catch (SQLException ex) {
            System.out.println("Database connection failed" + ex.getMessage());
        }catch (InvalidInputException ex){
            System.out.println(ex.getMessage());
            sc.nextLine(); //to clear the invalid input from the scanner
        }
        finally { //Executed whatsoever
            if (connection != null && !connection.isClosed()) {
                connection.close(); //Closing the connection after use
                System.out.println("Connection closed");
                sc.close();
            }
        }
    }
}
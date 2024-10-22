package com.university.roles.professor;


import com.university.exceptions.EmailValidator;
import com.university.exceptions.InvalidEmailException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginProfessor {
    public void login(Connection connection,Scanner sc) {
        System.out.println("Enter your college email only: ");
        System.out.print("(May cause exception)   ");
        String pEmail = sc.next();
        sc.nextLine();

        EmailValidator emailValidator = new EmailValidator();
        try {
            emailValidator.validateEmail(pEmail);

        } catch (InvalidEmailException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("Enter your password: ");
        String sPassword = sc.next();
        sc.nextLine();

//        System.out.println("Login as Professor");

        String sql = "SELECT * FROM Professor WHERE pEmail = ? AND password = ?";

        try {
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setString(1, pEmail);
            psmt.setString(2, sPassword);

            ResultSet resultSet = psmt.executeQuery();

            // Check if user credentials are valid
            if (resultSet.next()) {
                System.out.println("Login successful");
                while (true) {
                    System.out.println("\n========================================");
                    System.out.println("You can do the following things: ");
                    System.out.println("1. Manage Courses, Assigning Grades, View Feedback");
                    System.out.println("2. View Enrolled Students");
                    System.out.println("3. Change Credentials");
                    System.out.println("4. Exit");

                    System.out.print("\nEnter your choice: ");
                    int choice = sc.nextInt();

                    switch (choice) {
                        case 1:
                            try {
                                ManageCourses mc = new ManageCourses();
                                mc.manageAvailableCourses(connection,sc ,pEmail);
                            } catch (SQLException | InterruptedException ex) {
                                System.out.println(ex.getMessage());
                            }
                            break;

                        case 2:
                            ViewEnrolledStudents v = new ViewEnrolledStudents();
                            v.view(connection, pEmail);
                            break;

                        case 3:
                            System.out.print("Enter new Password: ");
                            String newPassword = sc.next();
                            sc.nextLine();
                            System.out.print("Confirm Password: ");
                            String newpass = sc.next();
                            sc.nextLine();

                            if (newPassword.equalsIgnoreCase(newpass)) {
                                String UpdateSql = "UPDATE Professor SET Password=? WHERE pEmail= ?";
                                PreparedStatement psmtUpdate = connection.prepareStatement(UpdateSql);
                                psmtUpdate.setString(1, newPassword);
                                psmtUpdate.setString(2, pEmail);

                                int affectedRows = psmtUpdate.executeUpdate();
                                if (affectedRows > 0) {
                                    System.out.println("updated successfully");
                                } else {
                                    System.out.println("Failed to update");
                                }
                            } else {
                                System.out.println("Password didn't match. Please try again");
                                return;
                            }
                        case 4:
                            return;
                        default:
                            System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                            break;
                    }
                }
            } else {
                System.out.println("Login failed: Not found email or password");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

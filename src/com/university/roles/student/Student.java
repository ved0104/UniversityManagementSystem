package com.university.roles.student;

import com.university.exceptions.CourseNotFoundException;
import com.university.exceptions.EmailValidator;
import com.university.exceptions.InvalidEmailException;
import com.university.main.*;

import java.sql.*;
import java.util.Scanner;

public class Student extends User {
    Scanner sc = new Scanner(System.in);

    public Student() {
        super();
    }

    @Override
    public void displayMenu(Connection connection, Scanner sc) {
        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Exit");
            System.out.print("\nEnter your choice: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    try {
                        login(connection);

                    } catch (InvalidEmailException | SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Enter valid choice");
                    break;
            }
        }
    }

    public void login(Connection connection) throws SQLException, InvalidEmailException {
        System.out.print("Enter your college email only: ");
        System.out.print("(May cause exception)   ");
        String sEmail = sc.next();
        sc.nextLine();

        EmailValidator emailValidator = new EmailValidator();
        try {
            emailValidator.validateEmail(sEmail);

        } catch (InvalidEmailException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.print("Enter your password: ");
        String sPassword = sc.next();
        sc.nextLine();

//        System.out.println("Login as Student");

        // Query to check if the user exists in the Student table
        String sql = "SELECT * FROM Student WHERE sEmail = ? AND sPassword = ?";
        try {
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setString(1, sEmail);
            psmt.setString(2, sPassword);

            ResultSet resultSet = psmt.executeQuery();

            // Check if user credentials are valid
            if (resultSet.next()) {
                System.out.println("Login successful");
                while (true) {
                    System.out.println("\n========================================");
                    System.out.println("You can do the following things: ");
                    System.out.println("1. View Available Courses");
                    System.out.println("2. Register for Courses");
                    System.out.println("3. View Schedule");
                    System.out.println("4. Track Academic Progress");
                    System.out.println("5. Drop Courses");
                    System.out.println("6. Submit Complaints");
                    System.out.println("7. Change credentials");
                    System.out.println("8. Give Feedbacks *NEW*");
                    System.out.println("9. Exit");

                    System.out.print("\nEnter your choice: ");
                    int choice = sc.nextInt();

                    switch (choice) {
                        case 1:
                            // Logic to view available courses
                            AvailableCourses ac = new AvailableCourses();
                            ac.viewAvailableCourses(connection, sEmail);
                            break;

                        case 2:
                            // Logic to register for courses
                            CourseRegistration cr = new CourseRegistration(connection);
                            cr.registerForCourse(sEmail, sc);
//                        registerForCourses();
                            break;

                        case 3:
                            // Logic to view the student's schedule
                            ViewSchedule v = new ViewSchedule();
                            v.view(sEmail, connection);
                            break;

                        case 4:
                            // Logic to track academic progress
                            TrackAcademicProgress tap = new TrackAcademicProgress();
                            tap.track(connection, sEmail);
                            break;

                        case 5:
                            // Logic to drop courses
                            DropCourse dc = new DropCourse();
                            dc.drop(sEmail, connection, sc);
                            break;

                        case 6:
                            // Logic to submit complaints
                            SubmitComplaints sbmt = new SubmitComplaints();
                            sbmt.submit(sEmail, connection, sc);
                            break;

                        case 7:
                            System.out.print("Enter new Password: ");
                            String newPassword = sc.next();
                            sc.nextLine();
                            System.out.print("Confirm Password: ");
                            String newpass = sc.next();
                            sc.nextLine();

                            if (newPassword.equalsIgnoreCase(newpass)) {
                                String UpdateSql = "UPDATE Student SET sPassword=? WHERE sEmail= ?";
                                PreparedStatement psmtUpdate = connection.prepareStatement(UpdateSql);
                                psmtUpdate.setString(1, newPassword);
                                psmtUpdate.setString(2, sEmail);

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
                            return;
                        case 8:
                            StudentFeedback feedback = new StudentFeedback();
                            try {
                                feedback.feedback(sEmail, connection, sc);
                            } catch (CourseNotFoundException ex) {
                                System.out.println(ex.getMessage());
                            }
                        case 9:
                            return;

                        default:
                            System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                            break;
                    }
                }
            } else {
                System.out.println("Login failed: Invalid email or password");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
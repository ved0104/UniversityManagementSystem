package com.university.roles.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ManageCourseCatalog {

    public void manage(Connection connection,Scanner sc) {
        try {
            String sql = "SELECT * FROM courseCatalog;";
            PreparedStatement psmt = connection.prepareStatement(sql);
            ResultSet resultSet = psmt.executeQuery();
            System.out.printf("+------------+----------------------------------+--------------------------+---------+---------------+--------------------------------------------------------+---------------------+%n");
            System.out.printf("| courseCode | courseTitle                      | pEmail                   | credits | prerequisites | description                                            | classTiming         |%n");
            System.out.printf("+------------+----------------------------------+--------------------------+---------+---------------+--------------------------------------------------------+---------------------+%n");

            // Print the rows
            while (resultSet.next()) {
                String courseCode = resultSet.getString("courseCode");
                String courseTitle = resultSet.getString("courseTitle");
                String pEmail = resultSet.getString("pEmail");
                int credits = resultSet.getInt("credits");
                String prerequisites = resultSet.getString("prerequisites");
                String description = resultSet.getString("description");
                String classTiming = resultSet.getString("classTiming");

                // Format and print each row
                System.out.printf("| %-10s | %-32s | %-24s | %-7d | %-13s | %-54s | %-19s |%n",
                        courseCode, courseTitle, pEmail, credits, prerequisites, description, classTiming);
            }

            // Print the footer
            System.out.printf("+------------+----------------------------------+--------------------------+---------+---------------+--------------------------------------------------------+---------------------+%n");

            // Menu for adding or deleting courses
            System.out.println("\nDo you want to add or delete any courses?");
            System.out.println("1. Add");
            System.out.println("2. Delete");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addCourse(connection,sc);
                    break;
                case 2:
                    deleteCourse(connection,sc);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void addCourse(Connection connection,Scanner sc) {
        try {
            // Prompting user for course details
            System.out.print("Enter course code: ");
            String courseCode = sc.nextLine();
            System.out.print("Enter course title: ");
            String courseTitle = sc.nextLine();
            System.out.print("Enter professor email: ");
            String pEmail = sc.nextLine();
            System.out.print("Enter credits: ");
            int credits = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter prerequisites: ");
            String prerequisites = sc.nextLine();
            System.out.print("Enter description: ");
            String description = sc.nextLine();
            System.out.print("Enter class timing: ");
            String classTiming = sc.nextLine();

            String insertSql = "INSERT INTO courseCatalog (courseCode, courseTitle, pEmail, credits, prerequisites, description, classTiming) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement insertPsmt = connection.prepareStatement(insertSql);
            insertPsmt.setString(1, courseCode);
            insertPsmt.setString(2, courseTitle);
            insertPsmt.setString(3, pEmail);
            insertPsmt.setInt(4, credits);
            insertPsmt.setString(5, prerequisites);
            insertPsmt.setString(6, description);
            insertPsmt.setString(7, classTiming);

            int rowsAffected = insertPsmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Course added successfully.");
            } else {
                System.out.println("Failed to add the course.");
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    private void deleteCourse(Connection connection,Scanner sc) {
        try {
            System.out.print("Enter the course code of the course you want to delete: ");
            String courseCode = sc.nextLine();

            String deleteSql = "DELETE FROM courseCatalog WHERE courseCode = ?";
            PreparedStatement deletePsmt = connection.prepareStatement(deleteSql);
            deletePsmt.setString(1, courseCode);

            int rowsAffected = deletePsmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Course deleted successfully.");
            } else {
                System.out.println("Course not found.");
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}

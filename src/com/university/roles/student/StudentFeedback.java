package com.university.roles.student;

import com.university.exceptions.CourseNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class StudentFeedback {

    public void feedback(String sEmail, Connection connection, Scanner sc) throws CourseNotFoundException {
        System.out.println("You can give feedback for your registered courses.");

        String viewSql = "SELECT courseCode FROM studentregistrations WHERE sEmail = ?;";
        Set<String> registeredCourses = new HashSet<>(); //Used Collections to handle course-not-found exceptions

        try {
            PreparedStatement psmt = connection.prepareStatement(viewSql);
            psmt.setString(1, sEmail);
            ResultSet rs = psmt.executeQuery();

            // Display the list of registered courses and store them in a set
            System.out.println("Your registered courses:");
            while (rs.next()) {
                String courseCode = rs.getString("courseCode");
                registeredCourses.add(courseCode);
                System.out.println(courseCode);
            }

            System.out.print("Enter the course code for the course you want to give feedback on: ");
            String courseCode = sc.next();
            sc.nextLine();

            // Check if the student is registered for the course
            if (!registeredCourses.contains(courseCode)) {
                throw new CourseNotFoundException("You are not registered for this course: " + courseCode);
            }

            // Ask for feedback and rating
            System.out.print("Enter your feedback: ");
            String feedbackText = sc.nextLine();

            System.out.print("Enter your rating (1-5): ");
            int rating = sc.nextInt();
            sc.nextLine();

            // SQL to insert feedback into the feedback table
            String insertSql = "INSERT INTO feedback (sEmail, courseCode, feedbackText, rating) " +
                    "VALUES (?, ?, ?, ?);";

            PreparedStatement insertPsmt = connection.prepareStatement(insertSql);
            insertPsmt.setString(1, sEmail);
            insertPsmt.setString(2, courseCode);
            insertPsmt.setString(3, feedbackText);
            insertPsmt.setInt(4, rating);

            int rowsAffected = insertPsmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Feedback submitted successfully.");
            } else {
                System.out.println("Failed to submit feedback.");
            }

        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}

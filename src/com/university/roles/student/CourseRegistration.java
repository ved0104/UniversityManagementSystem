package com.university.roles.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CourseRegistration {
    private final Connection connection;

    public CourseRegistration(Connection connection) {
        this.connection = connection;
    }

    public void registerForCourse(String sEmail, Scanner sc) throws SQLException {
        try {
            // Display the number of courses the student has already registered for
            String registeredCoursesSql = "SELECT COUNT(*) AS registeredCourses FROM StudentRegistrations WHERE sEmail = ?";
            PreparedStatement registeredCoursesStmt = connection.prepareStatement(registeredCoursesSql);
            registeredCoursesStmt.setString(1, sEmail);
            ResultSet registeredCoursesResult = registeredCoursesStmt.executeQuery();

            registeredCoursesResult.next();
            int registeredCourses = registeredCoursesResult.getInt("registeredCourses");
            int maxCoursesAllowed = 6;  // Set a maximum number of courses a student can register for
            int remainingCourses = maxCoursesAllowed - registeredCourses;

            if (remainingCourses > 0) {
                System.out.println("You have registered for " + registeredCourses + " course(s).");
                System.out.println("You can register for " + remainingCourses + " more course(s).");
            } else {
                System.out.println("You have already registered for the maximum number of courses.");
                return;  // Exit if the student has no more courses left to register
            }

            // Take sem input
            System.out.print("Enter semester: ");
            String sem = sc.next();
            sc.nextLine();
            // Take CourseCode input
            System.out.print("Enter Course code to add: ");
            String courseCode = sc.next();
            sc.nextLine();

            // Check if the course is available for the current semester
            String checkCourseSql = "SELECT * FROM AvailableCourses WHERE courseCode = ? AND sem = ?";
            PreparedStatement checkCourseStmt = connection.prepareStatement(checkCourseSql);
            checkCourseStmt.setString(1, courseCode);
            checkCourseStmt.setString(2, sem);

            ResultSet courseResult = checkCourseStmt.executeQuery();

            if (courseResult.next()) {
                // Check prerequisites
                String prerequisites = courseResult.getString("prerequisites");
                if (prerequisites != null && !prerequisites.isEmpty()) {
                    // Ensure all prerequisites are met
                    String[] requiredCourses = prerequisites.split(","); // Split the prerequisites into an array

                    for (String reqCourse : requiredCourses) {
                        String checkPrereqSql = "SELECT * FROM StudentRegistrations WHERE sEmail = ? AND courseCode = ?";
                        PreparedStatement checkPrereqStmt = connection.prepareStatement(checkPrereqSql);

                        checkPrereqStmt.setString(1, sEmail);
                        checkPrereqStmt.setString(2, reqCourse.trim());
                        ResultSet prereqResult = checkPrereqStmt.executeQuery();

                        // If the prerequisite is not found, inform the user and exit the method
                        if (!prereqResult.next()) {
                            System.out.println("You must complete the prerequisite course: " + reqCourse);
                            return; // Exit if prerequisites are not met
                        }
                    }
                }

                // Calculate total credits
                String totalCreditsSql = "SELECT SUM(credits) AS totalCredits FROM StudentRegistrations WHERE sEmail = ?";
                PreparedStatement totalCreditsStmt = connection.prepareStatement(totalCreditsSql);
                totalCreditsStmt.setString(1, sEmail);
                ResultSet totalCreditsResult = totalCreditsStmt.executeQuery();
                totalCreditsResult.next();
                int totalCredits = totalCreditsResult.getInt("totalCredits");
                int newCourseCredits = courseResult.getInt("credits");

                if (totalCredits + newCourseCredits <= 20) {
                    // Proceed to register
                    String registerSql = "INSERT INTO StudentRegistrations (sEmail, courseCode, semester, credits) VALUES (?, ?, ?, ?)";
                    PreparedStatement registerStmt = connection.prepareStatement(registerSql);
                    registerStmt.setString(1, sEmail);
                    registerStmt.setString(2, courseCode);
                    registerStmt.setString(3, sem);
                    registerStmt.setInt(4, newCourseCredits);

                    int affectedRows = registerStmt.executeUpdate();
                    if (affectedRows > 0) {
                        System.out.println("Registration successful for course: " + courseCode);
                    }
                } else {
                    System.out.println("Credit limit exceeded. You can only register for up to 20 credits.");
                }
            } else {
                System.out.println("Course not available for the selected semester.");
            }
        } catch (SQLException e) {
            System.out.println("Error during registration: " + e.getMessage());
        }
    }
}

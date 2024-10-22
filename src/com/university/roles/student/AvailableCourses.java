package com.university.roles.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AvailableCourses {
    private static final int NAME_WIDTH = 40;

    // Method to wrap text into specified width
    private String[] wrapText(String text, int width) {
        return text.split("(?<=\\G.{" + width + "})");  // Split text into chunks of specified width
    }

    public void viewAvailableCourses(Connection connection, String sEmail) throws SQLException {
        try {
            // There will already be some courses for sEmail in AvailableCourses table
            String coursesSql = "SELECT * FROM AvailableCourses WHERE sEmail = ?"; // to view those courses
            PreparedStatement coursesStmt = connection.prepareStatement(coursesSql);
            coursesStmt.setString(1, sEmail);
            ResultSet coursesResultSet = coursesStmt.executeQuery();

            System.out.println("Available Courses:");
            System.out.println("+---------------------------+------------------------------------------+-----+------------+---------------------+---------------------+---------+---------------+--------------+");
            System.out.println("| sEmail                    | name                                     | sem | courseCode | courseTitle         | professor           | credits | prerequisites | timings      |");
            System.out.println("+---------------------------+------------------------------------------+-----+------------+---------------------+---------------------+---------+---------------+--------------+");

            while (coursesResultSet.next()) {
                String email = coursesResultSet.getString("sEmail");
                String name = coursesResultSet.getString("name");
                String sem = coursesResultSet.getString("sem");
                String courseCode = coursesResultSet.getString("courseCode");
                String courseTitle = coursesResultSet.getString("courseTitle");
                String professor = coursesResultSet.getString("professor");
                int credits = coursesResultSet.getInt("credits");
                String prerequisites = coursesResultSet.getString("prerequisites");
                String timings = coursesResultSet.getString("timings");

                // Wrap the name field if it exceeds the specified width
                String[] wrappedName = wrapText(name, NAME_WIDTH);

                // Print the first line of course details (with the first line of wrapped name)
                System.out.printf("| %-25s | %-40s | %-3s | %-10s | %-19s | %-20s | %-7d | %-13s | %-12s |\n",
                        email, wrappedName[0], sem, courseCode, courseTitle, professor, credits, prerequisites, timings);

                // Print additional lines for the wrapped name if necessary
                for (int i = 1; i < wrappedName.length; i++) {
                    System.out.printf("| %-25s | %-40s | %-3s | %-10s | %-19s | %-20s | %-7s | %-13s | %-12s |\n",
                            "", wrappedName[i], "", "", "", "", "", "", "");
                }
            }

            System.out.println("+---------------------------+------------------------------------------+-----+------------+---------------------+---------------------+---------+---------------+--------------+");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

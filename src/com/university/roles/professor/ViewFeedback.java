package com.university.roles.professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewFeedback {
    public void view(Connection connection, String courseCode) {
        System.out.println("Feedback for Course Code: " + courseCode);

        String feedbackSql = "SELECT sEmail, feedbackText, feedbackDate, rating FROM feedback WHERE courseCode = ?";

        try {
            PreparedStatement feedbackStmt = connection.prepareStatement(feedbackSql);
            feedbackStmt.setString(1, courseCode);
            ResultSet feedbackResultSet = feedbackStmt.executeQuery();

            System.out.println("+---------------------------+--------------------------+---------------------+--------+");
            System.out.println("| sEmail                    | feedbackText             | feedbackDate        | rating |");
            System.out.println("+---------------------------+--------------------------+---------------------+--------+");

            if (!feedbackResultSet.isBeforeFirst()) { // Checks if ResultSet is empty
                System.out.println("| No feedback available for this course.                |                    |                     |        |");
            } else {

                while (feedbackResultSet.next()) {
                    String sEmail = feedbackResultSet.getString("sEmail");
                    String feedbackText = feedbackResultSet.getString("feedbackText");
                    String feedbackDate = feedbackResultSet.getString("feedbackDate");
                    int rating = feedbackResultSet.getInt("rating");

                    System.out.printf("| %-25s | %-24s | %-19s | %-6d |\n",
                            sEmail, feedbackText, feedbackDate, rating);
                }
            }

            System.out.println("+---------------------------+--------------------+---------------------+--------+");

        } catch (SQLException ex) {
            System.out.println("Error retrieving feedback: " + ex.getMessage());
        }
    }
}

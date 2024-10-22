package com.university.roles.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HandleComplaints {
    private static final int DESCRIPTION_WIDTH = 48;  // Width limit for description column

    public void handle(Connection connection, Scanner sc) {
        String view = "SELECT complaint_id, description, complaint_date, status FROM complaint";

        try {
            PreparedStatement psmt = connection.prepareStatement(view);
            ResultSet rs = psmt.executeQuery();

            System.out.println("+--------------+--------------------------------------------------+----------------+--------------+");
            System.out.println("| Complaint ID | Description                                      | Complaint Date | Status       |");
            System.out.println("+--------------+--------------------------------------------------+----------------+--------------+");

            while (rs.next()) {
                int complaintId = rs.getInt("complaint_id");
                String description = rs.getString("description");
                String complaintDate = rs.getString("complaint_date");
                String status = rs.getString("status");

                // Wrap the description text if it exceeds the defined width
                String[] wrappedDescription = wrapText(description, DESCRIPTION_WIDTH);

                System.out.printf("| %-12d | %-48s | %-14s | %-12s |\n", complaintId, wrappedDescription[0], complaintDate, status);

                // Print remaining lines for the wrapped description
                for (int i = 1; i < wrappedDescription.length; i++) {
                    System.out.printf("| %-12s | %-48s | %-14s | %-12s |\n", "", wrappedDescription[i], "", "");
                }
            }

            System.out.println("+--------------+--------------------------------------------------+----------------+--------------+");

            System.out.print("Enter Complaint ID to resolve: ");
            String complaintIdInput = sc.next();

            String updateComplaint = "UPDATE Complaint SET status = 'resolved' WHERE complaint_id = ?";
            PreparedStatement psmt2 = connection.prepareStatement(updateComplaint);
            psmt2.setString(1, complaintIdInput);

            System.out.print("\nResolving");
            for (int i = 0; i < 5; i++) {
                System.out.print(".");
                Thread.sleep(500);
            }

            int affectedRows = psmt2.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("\nComplaint Resolved");
            } else {
                System.out.println("\nComplaint unable to be resolved (ID may not exist).");
            }
        } catch (SQLException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Method to wrap text to a specified width
    private String[] wrapText(String text, int width) {
        return text.split("(?<=\\G.{" + width + "})");  // Split text based on the specified width
    }
}

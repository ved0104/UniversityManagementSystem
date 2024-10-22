package com.university.roles.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class SubmitComplaints {
    public void submit(String sEmail, Connection connection,Scanner sc) {
        System.out.println("What is the complaint about: ");
        sc.nextLine();
        String description = sc.nextLine();
        String status = "Pending";

        String insertSql = "INSERT INTO Complaint (complaint_id, sEmail, description, status, complaint_date) VALUES (NULL, ?, ?, ?, CURDATE());";
        try {
            PreparedStatement psmt = connection.prepareStatement(insertSql);
            psmt.setString(1, sEmail);
            psmt.setString(2, description);
            psmt.setString(3, status);

            int affected_rows = psmt.executeUpdate();

            if (affected_rows > 0) {
                System.out.println("Complaint Registered");

                String selectSql = "SELECT complaint_id, status FROM Complaint WHERE sEmail = ? ORDER BY complaint_id DESC LIMIT 1;";
                psmt = connection.prepareStatement(selectSql);
                psmt.setString(1, sEmail);

                ResultSet rs = psmt.executeQuery();

                System.out.println("+--------------+-----------+");
                System.out.println("| Complaint ID |   Status   |");
                System.out.println("+--------------+-----------+");

                while (rs.next()) {
                    long complaintId = rs.getLong("complaint_id");
                    String complaintStatus = rs.getString("status");

                    System.out.printf("| %-12d | %-9s |\n", complaintId, complaintStatus);
                }

                System.out.println("+--------------+-----------+");
            } else {
                System.out.println("Complaint submission failed");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

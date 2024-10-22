package com.university.roles.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AssignProfessors {
    public void assign(Connection connection, Scanner sc) {
        String sql = "SELECT name, sem, courseCode, courseTitle, professor, credits, prerequisites, timings FROM courses";
        try {
            PreparedStatement psmt = connection.prepareStatement(sql);
            ResultSet resultSet = psmt.executeQuery();

            System.out.println("+----------------------+-----+------------+-----------------------------------+---------------------------+---------+-----------------+--------------------+");
            System.out.println("| Name                 | Sem | CourseCode | Course Title                      | Professor                 | Credits | Prerequisites   | Timings            |");
            System.out.println("+----------------------+-----+------------+-----------------------------------+---------------------------+---------+-----------------+--------------------+");

            // Loop through the result set and print each row with the proper formatting
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int sem = resultSet.getInt("sem");
                String courseCode = resultSet.getString("courseCode");
                String courseTitle = resultSet.getString("courseTitle");
                String professor = resultSet.getString("professor");
                int credits = resultSet.getInt("credits");
                String prerequisites = resultSet.getString("prerequisites");
                String timings = resultSet.getString("timings");

                // Print each row, formatted to look like an SQL table
                System.out.printf("| %-20s | %-3d | %-10s | %-33s | %-25s | %-7d | %-15s | %-18s |%n",
                        name, sem, courseCode, courseTitle, professor, credits, prerequisites, timings);
            }

            System.out.println("+----------------------+-----+------------+-----------------------------------+---------------------------+---------+-----------------+--------------------+");
            System.out.print("Do you wish to add or remove and professor for a particular course?");
            String str = sc.next();
            sc.nextLine();
            if (str.equalsIgnoreCase("yes")) {
                System.out.print("Enter courseCode for which you want to update: ");
                String courseCode = sc.next();
                sc.nextLine();

                System.out.println("1. Add");
                System.out.println("2. Delete");
                System.out.print("Enter your choice");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:

                }
            }
        } catch (SQLException ex) {
            System.out.println("SQL Exception: " + ex.getMessage());
        }
    }
}

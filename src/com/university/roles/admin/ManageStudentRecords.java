package com.university.roles.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManageStudentRecords {
    public void manageRecords(Connection connection) {
        String sql = "SELECT \n" +
                "    ss.sEmail, \n" +
                "    ss.name, \n" +
                "    ss.degree, \n" +
                "    ss.branch, \n" +
                "    ss.sem, \n" +
                "    ss.mob_number, \n" +
                "    asg.courseCode, \n" +
                "    asg.grade, \n" +
                "    asg.academicStanding\n" +
                "FROM \n" +
                "    student ss\n" +
                "LEFT JOIN \n" +
                "    academicStandings asg \n" +
                "ON \n" +
                "    ss.sEmail = asg.sEmail\n" +
                "ORDER BY \n" +
                "    ss.sEmail;\n";

        try {
            PreparedStatement psmt = connection.prepareStatement(sql);
            ResultSet resultSet = psmt.executeQuery();

            // Print table headers
            System.out.println("+---------------------------+---------------+--------+--------+-----+------------+------------+-------+------------------+");
            System.out.println("| sEmail                    | name          | degree | branch | sem | mob_number | courseCode | grade | academicStanding |");
            System.out.println("+---------------------------+---------------+--------+--------+-----+------------+------------+-------+------------------+");

            // Loop through the result set and print each row
            while (resultSet.next()) {
                String sEmail = resultSet.getString("sEmail");
                String name = resultSet.getString("name");
                String degree = resultSet.getString("degree");
                String branch = resultSet.getString("branch");
                int sem = resultSet.getInt("sem");
                String mob_number = resultSet.getString("mob_number");
                String courseCode = resultSet.getString("courseCode");
                String grade = resultSet.getString("grade");
                String academicStanding = resultSet.getString("academicStanding");

                // Format the output to fit the table format
                System.out.printf("| %-25s | %-13s | %-6s | %-6s | %-3d | %-10s | %-10s | %-5s | %-16s |\n",
                        sEmail, name, degree, branch, sem, mob_number, courseCode, grade, academicStanding);
            }

            System.out.println("+---------------------------+---------------+--------+--------+-----+------------+------------+----------+-------+------------------+");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

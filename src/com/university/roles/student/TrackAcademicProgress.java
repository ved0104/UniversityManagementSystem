package com.university.roles.student;

import com.university.roles.professor.ManageCourses;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackAcademicProgress {
    public void track(Connection connection,String sEmail) {

            String result = "SELECT \n" +
                    "    SUM(\n" +
                    "        CASE\n" +
                    "            WHEN grade = 'A' THEN 100\n" +
                    "            WHEN grade = 'A-' THEN 90\n" +
                    "            WHEN grade = 'B+' THEN 85\n" +
                    "            WHEN grade = 'B' THEN 90\n" +
                    "            WHEN grade = 'C' THEN 75\n" +
                    "            WHEN grade = 'D' THEN 60\n" +
                    "            WHEN grade = 'E' THEN 55\n" +
                    "            WHEN grade = 'F' THEN 0\n" +
                    "            ELSE 0\n" +
                    "        END\n" +
                    "    ) AS total_points,\n" +
                    "    COUNT(courseCode) AS total_courses,\n" +
                    "    AVG(\n" +
                    "        CASE\n" +
                    "            WHEN grade = 'A' THEN 100\n" +
                    "            WHEN grade = 'A-' THEN 90\n" +
                    "            WHEN grade = 'B+' THEN 85\n" +
                    "            WHEN grade = 'B' THEN 90\n" +
                    "            WHEN grade = 'C' THEN 75\n" +
                    "            WHEN grade = 'D' THEN 60\n" +
                    "            WHEN grade = 'E' THEN 55\n" +
                    "            WHEN grade = 'F' THEN 0\n" +
                    "            ELSE 0\n" +
                    "        END\n" +
                    "    ) AS cgpa\n" +
                    "FROM \n" +
                    "    academicStandings\n" +
                    "WHERE sEmail = ?";
            try {
                PreparedStatement psmt = connection.prepareStatement(result);
                psmt.setString(1,sEmail);
                ResultSet rs = psmt.executeQuery();

                // Displaying the result in table format
                System.out.println("+-------------+--------------+------------+");
                System.out.println(" Total Points | Total Courses|   CGPA     |");
                System.out.println("+-------------+--------------+------------+");

                while (rs.next()) {
                    double cgpa = rs.getDouble("cgpa");
                    if(cgpa==0){
                        System.out.println("Grades are not assigned by the respected professor yet.");
                        break;
                    }
                    int totalPoints = rs.getInt("total_points");
                    int totalCourses = rs.getInt("total_courses");

                    // Print each row in a formatted table format
                    System.out.printf("| %-12d | %-12d | %-10.2f |\n", totalPoints, totalCourses, cgpa);
                }

                // Print table footer
                System.out.println("+------------+--------------+-------------+");

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
    }
}

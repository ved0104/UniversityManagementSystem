package com.university.roles.professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewEnrolledStudents {
    public void view(Connection connection, String pEmail){
        String viewSql="SELECT s.name, s.sEmail, s.mob_number, s.degree, s.branch, ac.courseCode, ac.sem, ac.credits, cs.academicStanding\n" +
                "FROM student s\n" +
                "JOIN AvailableCourses ac ON s.sEmail = ac.sEmail\n" +
                "JOIN courseDetails cd ON ac.courseCode = cd.courseCode\n" +
                "LEFT JOIN academicStandings cs ON s.sEmail = cs.sEmail AND ac.courseCode = cs.courseCode\n" +
                "WHERE cd.pEmail = ?;\n";
        try {
            PreparedStatement psmt=connection.prepareStatement(viewSql);
            psmt.setString(1,pEmail);

            ResultSet resultSet=psmt.executeQuery();

            System.out.printf("+---------------+---------------------------+------------+--------+--------+------------+---------+----------+-------------------+%n");
            System.out.printf("| %-13s | %-25s | %-10s | %-6s | %-6s | %-10s | %-7s | %-8s | %-17s |%n",
                    "Name", "Email", "Mobile", "Degree", "Branch", "CourseCode", "Semester", "Credits", "AcademicStanding");
            System.out.printf("+---------------+---------------------------+------------+--------+--------+------------+---------+----------+-------------------+%n");

            // Print data rows
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("sEmail");
                String mobile = resultSet.getString("mob_number");
                String degree = resultSet.getString("degree");
                String branch = resultSet.getString("branch");
                String courseCode = resultSet.getString("courseCode");
                int sem = resultSet.getInt("sem");
                int credits = resultSet.getInt("credits");
                String academicStanding = resultSet.getString("academicStanding");

                // Display formatted row
                System.out.printf("| %-13s | %-25s | %-10s | %-6s | %-6s | %-10s | %-7d | %-8d | %-17s |%n",
                        name, email, mobile, degree, branch, courseCode, sem, credits,
                        academicStanding != null ? academicStanding : "N/A");
            }
            // End of table
            System.out.printf("+---------------+---------------------------+------------+--------+--------+------------+---------+----------+-------------------+%n");
        }catch (SQLException ex) {
            System.out.println("Error: "+ex.getMessage());
        }
    }
}

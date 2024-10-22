package com.university.roles.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewSchedule {
    private static final int DESCRIPTION_WIDTH = 40;  // Adjusted width for syllabus

    public void view(String sEmail, Connection connection) {
        String sql = "SELECT s.courseCode, c.syllabus, c.classTiming, c.credits, crs.professor " +
                "FROM studentregistrations s " +
                "JOIN courseDetails c ON s.courseCode = c.courseCode " +
                "JOIN student st ON s.sEmail = st.sEmail " +
                "JOIN courses crs ON c.courseCode = crs.courseCode " +
                "WHERE s.sEmail = ?;";

        try {
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setString(1, sEmail);

            ResultSet rs = psmt.executeQuery();
            System.out.println("+------------+------------------------------------------+------------------+---------+-------------------+");
            System.out.println("| courseCode | syllabus                                 | classTiming      | credits |     professor     |");
            System.out.println("+------------+------------------------------------------+------------------+---------+-------------------+");

            // Process and print each row in the result set
            while (rs.next()) {
                String courseCode = rs.getString("courseCode");
                String syllabus = rs.getString("syllabus");
                String classTiming = rs.getString("classTiming");
                String professor = rs.getString("professor");
                int credits = rs.getInt("credits");

                // Format and print the first line
                String[] wrappedDescription = wrapText(syllabus, DESCRIPTION_WIDTH);

                System.out.printf("| %-10s | %-40s | %-16s | %-7d | %-17s |\n",
                        courseCode, wrappedDescription[0], classTiming, credits, professor);

                // Print wrapped lines (if any)
                for (int i = 1; i < wrappedDescription.length; i++) {
                    System.out.printf("| %-10s | %-40s | %-16s | %-7s | %-17s |\n", "", wrappedDescription[i], "", "", "");
                }
            }

            // Print table footer
            System.out.println("+------------+------------------------------------------+------------------+---------+-------------------+");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private String[] wrapText(String text, int width) {
        return text.split("(?<=\\G.{" + width + "})");  // Split text into chunks of specified width
    }
}

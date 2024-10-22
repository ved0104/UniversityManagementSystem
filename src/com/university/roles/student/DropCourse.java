package com.university.roles.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class DropCourse {
    public void drop(String sEmail, Connection connection, Scanner sc){
        System.out.println("Enter Course-code you want to drop out of: ");
        String coursecode=sc.next();
        sc.nextLine();
        String sql="DELETE FROM StudentRegistrations WHERE sEmail= ? AND courseCode= ?";
        try {
            PreparedStatement psmt=connection.prepareStatement(sql);
            psmt.setString(1,sEmail);
            psmt.setString(2,coursecode);

            int affectedRows= psmt.executeUpdate();
            if (affectedRows>0){
                System.out.println("Successfully updated");
            }else{
                System.out.println("failed to update");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

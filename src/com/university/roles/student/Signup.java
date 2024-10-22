package com.university.roles.student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class Signup{
    public void signup (Connection connection,Scanner sc){
        System.out.print("Enter college email only: ");
        String sEmail = sc.next();
        sc.nextLine();

        System.out.print("Create a strong password (max 20): ");
        String sPassword = sc.next();
        sc.nextLine();

        System.out.print("Enter name: ");
        String name = sc.next();
        sc.nextLine();

        System.out.print("Enter Enrollment number/ SID: ");
        String sid = sc.next();
        sc.nextLine();

        System.out.print("Enter degree: ");
        String degree = sc.next();
        sc.nextLine();

        System.out.print("Enter branch: ");
        String branch = sc.next();
        sc.nextLine();

        System.out.print("Enter sem: ");
        String sem = sc.next();
        sc.nextLine();

        System.out.print("Enter mobile_number: ");
        String mob_number = sc.next();
        sc.nextLine();

        String sql = "INSERT INTO Student (sEmail,sPassword,name,sid,degree,branch,sem,mob_number) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement psmt = connection.prepareStatement(sql);
            psmt.setString(1, sEmail);
            psmt.setString(2, sPassword);
            psmt.setString(3, name);
            psmt.setString(4, sid);
            psmt.setString(5, degree);
            psmt.setString(6, branch);
            psmt.setString(7, sem);
            psmt.setString(8, mob_number);

            int affected_rows = psmt.executeUpdate();
            if (affected_rows > 0) {
                System.out.println("Sign up successful");
            } else {
                System.out.println("signup failed");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

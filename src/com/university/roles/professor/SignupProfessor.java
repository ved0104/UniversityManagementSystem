package com.university.roles.professor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class SignupProfessor {
    public void signup(Connection connection,Scanner sc){
        System.out.print("Enter college email only ");
        System.out.print("(May cause exception): ");
        String pEmail = sc.next();
        sc.nextLine();

        System.out.print("Create a strong password (max 20): ");
        String password = sc.next();
        sc.nextLine();

        System.out.print("Enter name: ");
        String name = sc.next();
        sc.nextLine();

        System.out.print("Enter ID number/ PID: ");
        String pid = sc.next();
        sc.nextLine();

        System.out.print("Enter course (you can enter multiple courses but you need to signup again with same email): ");
        String course = sc.next();
        sc.nextLine();

        System.out.print("Enter mobile_number: ");
        String mob_number = sc.next();
        sc.nextLine();

        System.out.print("Enter Department: ");
        String department = sc.next();
        sc.nextLine();

        String insertSql= "INSERT INTO professor(pEmail,password,name,pid,course,mob_number,department) VALUES (?,?,?,?,?,?,?)";
        try{
            PreparedStatement psmt=connection.prepareStatement(insertSql);
            psmt.setString(1,pEmail);
            psmt.setString(2,password);
            psmt.setString(3,name);
            psmt.setString(4,pid);
            psmt.setString(5,course);
            psmt.setString(6,mob_number);
            psmt.setString(7,department);

            int affected_rows = psmt.executeUpdate();
            if (affected_rows > 0) {
                System.out.println("Sign up successful");
                System.out.println("You may login now");
            } else {
                System.out.println("signup failed");
            }
        }catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}

package com.university.roles.admin;

import com.university.main.LazyOpening;
import com.university.main.User;
import com.university.roles.professor.SignupProfessor;
import com.university.roles.student.Signup;

import java.sql.Connection;
import java.util.Objects;
import java.util.Scanner;

public class Admin extends User {
    private static final String admpass = "admin";

    public Admin() {
        super();
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public boolean authenticate(String password) {
        return super.authenticate(password);
    }

    @Override
    public void displayMenu(Connection connection, Scanner sc) {
        System.out.print("Enter Email: ");
        String email = sc.next();
        sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.next();
        sc.nextLine();
        System.out.print("Enter secret key: ");
        String key = sc.next();
        sc.nextLine();
        if (Objects.equals(email, "admin@coed.svnit.ac.in") && Objects.equals(pass, admpass) && Objects.equals(key, "0000")) {
            System.out.print("Success");
            try {

                LazyOpening lo = new LazyOpening();
                lo.open();
                while (true) {
                    System.out.println("\n1. Manage Course Catalog");
                    System.out.println("2. Manage Student Records");
                    System.out.println("3. Assign Professors to Courses");
                    System.out.println("4. Handle Complaints");
                    System.out.println("5. Exit");
                    System.out.print("\nEnter your choice: ");
                    int choice = sc.nextInt();
                    switch (choice) {
                        case 1:
                            ManageCourseCatalog mcc = new ManageCourseCatalog();
                            mcc.manage(connection,sc);
                            break;
                        case 2:
                            System.out.print("Do you wish to enter details of new student?: ");
                            String str = sc.next();
                            sc.nextLine();
                            if (str.equalsIgnoreCase("yes")) {
                                Signup st = new Signup();
                                st.signup(connection,sc);
                            } else {
                                ManageStudentRecords msr = new ManageStudentRecords();
                                msr.manageRecords(connection);
                                break;
                            }
                        case 3:
                            System.out.print("Do you wish to enter details of new Professor?: ");
                            String str1 = sc.next();
                            sc.nextLine();
                            if (str1.equalsIgnoreCase("yes")) {
                                SignupProfessor sp = new SignupProfessor();
                                sp.signup(connection,sc);
                            } else {
                                AssignProfessors ap = new AssignProfessors();
                                ap.assign(connection,sc);
                                break;
                            }
                        case 4:
                            HandleComplaints hc = new HandleComplaints();
                            hc.handle(connection, sc);
                            break;
                        case 5:
                            return;
                        default:
                            System.out.println("Enter valid choice");
                            break;
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Wrong credentials. Please try again after sometime.");
        }
    }
}

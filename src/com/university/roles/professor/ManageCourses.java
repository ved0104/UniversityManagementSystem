package com.university.roles.professor;

import com.university.main.LazyOpening;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ManageCourses {
    public boolean gradeAssigned;
    public void manageAvailableCourses(Connection connection,Scanner sc, String pEmail) throws SQLException, InterruptedException {

        try {
            String coursesSql = "SELECT * FROM CourseDetails WHERE pEmail = ?";
            PreparedStatement coursesStmt = connection.prepareStatement(coursesSql);
            coursesStmt.setString(1, pEmail);
            ResultSet coursesResultSet = coursesStmt.executeQuery();

            System.out.println("Your Courses:");
            System.out.println("+------------+-----------------------+-----------------+---------+---------------+-------------+-----------------+---------------------------+");
            System.out.println("| courseCode | syllabus              | classTiming     | credits | prerequisites | enrollLimit | officeHours     | pEmail                    |");
            System.out.println("+------------+-----------------------+-----------------+---------+---------------+-------------+-----------------+---------------------------+");

            while (coursesResultSet.next()) {
                String courseCode = coursesResultSet.getString("courseCode");
                String syllabus = coursesResultSet.getString("syllabus");
                String classTiming = coursesResultSet.getString("classTiming");
                int credits = coursesResultSet.getInt("credits");
                String prerequisites = coursesResultSet.getString("prerequisites");
                int enrollLimit = coursesResultSet.getInt("enrollLimit");
                String officeHours = coursesResultSet.getString("officeHours");
                String email = coursesResultSet.getString("pEmail");

                // Print the course details in a properly formatted manner
                System.out.printf("| %-10s | %-21s | %-15s | %-7d | %-13s | %-11d | %-15s | %-25s |\n",
                        courseCode, syllabus, classTiming, credits, prerequisites, enrollLimit, officeHours, email);
            }
            System.out.println("+------------+-----------------------+-----------------+---------+---------------+-------------+-----------------+---------------------------+");
            System.out.print("\n\n");
            System.out.print("Do you wish to update anything?  ");

            String str = sc.next();
            if (str.equalsIgnoreCase("yes")) {

                System.out.print("Enter courseCode for which you want to update: ");
                String courseCode=sc.next();
                sc.nextLine();

                System.out.println("What do you want to update?");
                System.out.println("1. syllabus");
                System.out.println("2. classTiming");
                System.out.println("3. credits");
                System.out.println("4. prerequisites");
                System.out.println("5. enrollLimit");
                System.out.println("6. officeHours");


                System.out.print("Enter your choice (1-6): ");
                int choice = sc.nextInt();
                sc.nextLine();
                // Switch statement to handle user choice
                switch (choice) {
                    case 1:
                        // Update syllabus
                        System.out.print("Enter new syllabus: ");
                        String newSyllabus = sc.nextLine();
                        String updateSyllabus = "UPDATE courseDetails SET syllabus = ? WHERE courseCode = ?;";
                        PreparedStatement psmt1 = connection.prepareStatement(updateSyllabus);
                        psmt1.setString(1, newSyllabus);
                        psmt1.setString(2, courseCode);
                        int affectedRows1=psmt1.executeUpdate();

                        if (affectedRows1>0){
                            System.out.println("Successfully updated");
                        }else{
                            System.out.println("failed to update");
                        }
                        break;
                    case 2:
                        // Update classTiming
                        System.out.print("Enter new classTiming: ");
                        String newClassTiming = sc.nextLine();
                        String updateClassTimingSql = "UPDATE courseDetails SET classTiming = ? WHERE courseCode = ?;";
                        PreparedStatement psmt2 = connection.prepareStatement(updateClassTimingSql);
                        psmt2.setString(1, newClassTiming);
                        psmt2.setString(2, courseCode);

                        int affectedRows2=psmt2.executeUpdate();
                        if (affectedRows2>0){
                            System.out.println("Successfully updated");
                        }else{
                            System.out.println("failed to update");
                        }
                        break;
                    case 3:
                        // Update credits
                        System.out.print("Enter new credits: ");
                        int newCredits = sc.nextInt();
                        String updateCredits = "UPDATE courseDetails SET credits = ? WHERE courseCode = ?;";
                        PreparedStatement psmt3 = connection.prepareStatement(updateCredits);
                        psmt3.setInt(1, newCredits);
                        psmt3.setString(2, courseCode);
                        int affectedRows3=psmt3.executeUpdate();
                        if (affectedRows3>0){
                            System.out.println("Successfully updated");
                        }else{
                            System.out.println("failed to update");
                        }
                        break;
                    case 4:
                        // Update prerequisites
                        System.out.print("Enter new prereq: ");
                        String newprereq = sc.nextLine();
                        String updatePrereq = "UPDATE courseDetails SET prerequisites = ? WHERE courseCode = ?;";
                        PreparedStatement psmt4 = connection.prepareStatement(updatePrereq);
                        psmt4.setString(1, newprereq);
                        psmt4.setString(2, courseCode);

                        int affectedRows4=psmt4.executeUpdate();
                        if (affectedRows4>0){
                            System.out.println("Successfully updated");
                        }else{
                            System.out.println("failed to update");
                        }
                        break;
                    case 5:
                        // Update enrollLimit
                        System.out.print("Enter new enrollLimit: ");
                        int newEnrollLimit = sc.nextInt();
                        String updateEnrollLimit = "UPDATE courseDetails SET enrollLimit = ? WHERE courseCode = ?;";
                        PreparedStatement psmt5 = connection.prepareStatement(updateEnrollLimit);
                        psmt5.setInt(1, newEnrollLimit);
                        psmt5.setString(2, courseCode);

                        int affectedRows5=psmt5.executeUpdate();
                        if (affectedRows5>0){
                            System.out.println("Successfully updated");
                        }else{
                            System.out.println("failed to update");
                        }
                        break;
                    case 6:
                        // Update officeHours
                        System.out.print("Enter new office hours: ");
                        String newOfficeHours = sc.nextLine();
                        String updateOfficeHours = "UPDATE courseDetails SET officeHours = ? WHERE courseCode = ?;";
                        PreparedStatement psmt6 = connection.prepareStatement(updateOfficeHours);
                        int affectedRows6=psmt6.executeUpdate();
                        psmt6.setString(1, newOfficeHours);
                        psmt6.setString(2, courseCode);
                        if (affectedRows6>0){
                            System.out.println("Successfully updated");
                        }else{
                            System.out.println("failed to update");
                        }
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

            System.out.print("Do you wish to assign grades to the students?  ");
            String string=sc.next();
            sc.nextLine();
            if (string.equalsIgnoreCase("yes")){
                System.out.print("Enter Course Code: ");
                String courseCode=sc.next();
                sc.nextLine();
                System.out.print("Enter sid of student: ");
                String sid=sc.next();
                sc.nextLine();
                System.out.print("Enter grade: ");
                String grade=sc.next();
                sc.nextLine();
                String sql = "UPDATE academicStandings SET grade = ? WHERE sEmail = ? AND courseCode = ?;";

                try{
                    PreparedStatement psmt=connection.prepareStatement(sql);
                    psmt.setString(1,grade);
                    psmt.setString(2,sid+"@coed.svnit.ac.in");
                    psmt.setString(3,courseCode);
                    int affectedRows= psmt.executeUpdate();
                    if (affectedRows>0){
                        System.out.println("Successfully updated");
                        gradeAssigned=true;
                    }else{
                        System.out.println("failed to update");
                    }
                }catch (SQLException ex){
                    System.out.println("error: "+ex.getMessage());
                }
            }

        System.out.print("Do you wish to view Feedback for your course? ");
        String str=sc.next();
        sc.nextLine();
        if (str.equalsIgnoreCase("yes")) {
            System.out.print("Enter course for which you want to see the feedback for: ");
            String courseCode=sc.next();
            sc.nextLine();
            ViewFeedback vf=new ViewFeedback();
            vf.view(connection,courseCode);
        }else {
            System.out.print("Returning");
            LazyOpening lo=new LazyOpening();
            lo.open();
        }
    }
}

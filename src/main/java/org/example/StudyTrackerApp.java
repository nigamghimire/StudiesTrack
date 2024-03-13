package org.example;

import org.example.model.StudyRecord;
import org.example.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class StudyTrackerApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Study Tracker!");

        System.out.print("Enter the subject you studied: ");
        String subject = scanner.nextLine();

        System.out.print("Enter what you studied: ");
        String content = scanner.nextLine();

        StudyRecord studyRecord = new StudyRecord(subject, content);


        try (Connection connection = DatabaseUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO study_records (subject, content) VALUES (?, ?)")) {

            preparedStatement.setString(1, studyRecord.getSubject());
            preparedStatement.setString(2, studyRecord.getContent());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Study record added successfully!");
            } else {
                System.out.println("Failed to add study record.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            scanner.close();
        }
    }
}

package teszt;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MavenTeszt {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/dog_shelter?createDatabaseIfNotExist=true";
    public static final String USER = "root";
    public static final String PASSWORD = "Test123!";
//    static String[] oszlopok;

    public static void main(String[] args) {
//        createDB();
//        createTable(columnNames().get(0)[0], columnNames().get(0)[1], columnNames().get(0)[2], columnNames().get(0)[3]);
//        createDogs(columnNames());
//        friendlyDogs(true);
//        idDog(1);
//        allDog();
//        updateDog(10, 1);
//        allDogLimit(3,2);
    }


    private static List<String[]> columnNames() {
        List<String[]> oszlopok = new ArrayList<>();
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get("src/main/resources/data2.txt"))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(";");
                oszlopok.add(data);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return oszlopok;
    }

    public static void createDB() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
//            statement.execute("CREATE DATABASE dog_shelter");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTable(String breed, String name, String age, String friendly) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String createTable = "CREATE TABLE IF NOT EXISTS dogs (" + "id INT PRIMARY KEY AUTO_INCREMENT, " +
                    breed + " VARCHAR(100), " +
                    name + " VARCHAR(100), " +
                    age + " INT, " +
                    friendly + " BOOLEAN); ";
            PreparedStatement preparedStatement = connection.prepareStatement(createTable); //Kocsint/Serpa
            preparedStatement.execute(createTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void createDogs(List<String[]> dogList) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            for (int i = 1; i < dogList.size(); i++) {
                String dogBreed = dogList.get(i)[0];
                String dogName = dogList.get(i)[1];
                int dogAge = Integer.parseInt(dogList.get(i)[2]);
                boolean friendly = Boolean.parseBoolean(dogList.get(i)[3]);
                String createCustomer = "INSERT INTO dogs (dog_breed, dog_name, dog_age, friendly) VALUES (?,?,?,?)"; // Query
                PreparedStatement preparedStatement = connection.prepareStatement(createCustomer); //Kocsint/Serpa
                preparedStatement.setString(1, dogBreed);
                preparedStatement.setString(2, dogName);
                preparedStatement.setInt(3, dogAge);
                preparedStatement.setBoolean(4, friendly);

                int affectedRows = preparedStatement.executeUpdate(); //executeQuery
                System.out.println("Affected rows: " + affectedRows);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void friendlyDogs(boolean b) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String userInfoQuery = "SELECT * FROM dogs WHERE friendly = ?"; // Query
            PreparedStatement preparedStatement = connection.prepareStatement(userInfoQuery); //Kocsint/Serpa
            preparedStatement.setBoolean(1, b);
            ResultSet resultSet = preparedStatement.executeQuery(); //executeQuery

            while (resultSet.next()) {
                System.out.println(resultSet.getInt("ID") + " " + resultSet.getString("dog_name")+ " " + resultSet.getBoolean("friendly"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void idDog(int id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String userInfoQuery = "SELECT * FROM dogs WHERE ID = ?"; // Query
            PreparedStatement preparedStatement = connection.prepareStatement(userInfoQuery); //Kocsint/Serpa
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery(); //executeQuery

            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2)+ " " + resultSet.getString(3 )+ " " + resultSet.getBoolean(4));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void allDog() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String userInfoQuery = "SELECT * FROM dogs"; // Query
            PreparedStatement preparedStatement = connection.prepareStatement(userInfoQuery); //Kocsint/Serpa
//            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery(); //executeQuery

            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2)+ " " + resultSet.getString(3 )+ " " + resultSet.getBoolean(4));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void updateDog(int age, int id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String updateCustomer = "UPDATE dogs SET dog_age =? WHERE id =?"; // Query
            PreparedStatement preparedStatement = connection.prepareStatement(updateCustomer); //Kocsint/Serpa

            preparedStatement.setInt(1, age); //Ertekadas a ?nek
            preparedStatement.setInt(2, id); //Ertekadas a ?nek

            int affectedRows = preparedStatement.executeUpdate(); //executeQuery
            System.out.println("Affected rows: " + affectedRows);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private static void allDogLimit(int limit, int offset) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            String userInfoQuery = "SELECT * FROM dogs LIMIT ? OFFSET ?"; // Query
            PreparedStatement preparedStatement = connection.prepareStatement(userInfoQuery); //Kocsint/Serpa
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            ResultSet resultSet = preparedStatement.executeQuery(); //executeQuery

            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2)+ " " + resultSet.getString(3 )+ " " + resultSet.getBoolean(4));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
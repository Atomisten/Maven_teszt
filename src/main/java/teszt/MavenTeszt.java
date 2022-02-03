package teszt;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class MavenTeszt {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/country_app?useSSL=false&serverTimezone=UTC";
    public static final String USER = "root";
    public static final String PASSWORD = "Test123!";
    static String[] oszlopok;

    public static void main(String[] args) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get("src/main/resources/data.txt"))) {
            String line;
            String oszlopnevek;
            oszlopnevek = bufferedReader.readLine();
            oszlopok = oszlopnevek.split(";");
            while ((line = bufferedReader.readLine()) != null) {
                String[] fields = line.split(";");
                String country = fields[0];
                String city = fields[1];
                int population = Integer.parseInt(fields[2]);
//                createCountry(country, city, population);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

//        createTable();
//        updateCountry(8);
//        giveInfoFromCountry(8);
        giveAllInfo();
    }

    public static void createTable() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement(); // Serpa/Kocsi üres

            String createTable = "CREATE TABLE IF NOT EXISTS countries " +
                    "(ID INT AUTO_INCREMENT PRIMARY KEY, " +
                    oszlopok[0] + " VARCHAR(100), " +
                    oszlopok[1] + " VARCHAR(255), " +
                    oszlopok[2] + " INT); ";
            statement.execute(createTable); // sql parancsot rárakjuk a kocsira

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static void createCountry(String country, String city, int population) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement(); // Serpa/Kocsi üres

            String createCountry =
                    "INSERT INTO countries (country, city, population) " +
                            "VALUES ('" + country + "', '" + city + "', " + population + ")";
            statement.execute(createCountry); // sql parancsot rárakjuk a kocsira

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public static void updateCountry(int id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement(); // Serpa/Kocsi üres

            String updateCountry =
                    "UPDATE countries SET population = 4000000 WHERE id =" + id;
            statement.execute("SET sql_safe_updates = 1");  // Safe update be es kikapcsolasa ezzel a paranccsal
            int affectedRows = statement.executeUpdate(updateCountry); // sql parancsot rárakjuk a kocsira
            System.out.println("Rows: " + affectedRows);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void giveInfoFromCountry(int id) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement(); // Serpa/Kocsi üres

            String giveInfo = "SELECT country, city, population from countries WHERE ID = " + id;
            ResultSet resultSet = statement.executeQuery(giveInfo); // sql parancsot rárakjuk a kocsira
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " | " + resultSet.getString(2) + " | " +
                        resultSet.getInt(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private static void giveAllInfo() {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement(); // Serpa/Kocsi üres

            String giveInfo = "SELECT * from countries";
            ResultSet resultSet = statement.executeQuery(giveInfo); // sql parancsot rárakjuk a kocsira
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1) + "" +
                        " | " + resultSet.getString(2) + " | " +
                        "" + resultSet.getString(3) + " | " +
                        "" + resultSet.getInt(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
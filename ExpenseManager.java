import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;


import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ExpenseManager {
    private static ObservableList<Expense> expenses = FXCollections.observableArrayList();
    private static int nextId = 1;

    public static void loadUsers(Map<String, String> users) {
        File file = new File("users.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        String username = parts[0];
                        String email = parts[1];
                        String password = parts[2];
                        users.put(username, email + "," + password);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean authenticateUser(String usernameOrEmail, String password, Map<String, String> users) {
        for (Map.Entry<String, String> entry : users.entrySet()) {
            String username = entry.getKey();
            String[] userData = entry.getValue().split(",");
            String email = userData[0];
            String userPassword = userData[1];

            if ((username.equals(usernameOrEmail) || email.equals(usernameOrEmail)) && userPassword.equals(password)) {
                return true;
            }
        }
        return false;
    }

    public static String getUserEmail(String usernameOrEmail, Map<String, String> users) {
        for (Map.Entry<String, String> entry : users.entrySet()) {
            String[] userData = entry.getValue().split(",");
            String email = userData[0];

            if (entry.getKey().equals(usernameOrEmail) || email.equals(usernameOrEmail)) {
                return email;
            }
        }
        return "";
    }

    public static boolean registerUser(String username, String email, String password, String confirmPassword, Map<String, String> users) {
        if (!password.equals(confirmPassword)) {
            UI.showAlert(Alert.AlertType.ERROR, "Registration Failed", "Passwords do not match.");
            return false;
        }
        if (users.containsKey(username) || users.values().stream().anyMatch(value -> value.startsWith(email + ","))) {
            UI.showAlert(Alert.AlertType.ERROR, "Registration Failed", "Username or email already exists.");
            return false;
        }
        users.put(username, email + "," + password);
        saveUsers(users);
        return true;
    }

    public static void saveUsers(Map<String, String> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("users.txt"))) {
            for (Map.Entry<String, String> entry : users.entrySet()) {
                String username = entry.getKey();
                String[] userData = entry.getValue().split(",");
                String email = userData[0];
                String password = userData[1];
                writer.printf("%s,%s,%s%n", username, email, password);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showMainApp(Stage primaryStage, String currentUserEmail) {
        primaryStage.setTitle("Expense Manager");
        BorderPane root = new BorderPane();
        HBox centerHBox = new HBox(10);
        TableView<Expense> expenseTable = new TableView<>();
        PieChart pieChart = new PieChart();

        root.setCenter(centerHBox);

        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }
}

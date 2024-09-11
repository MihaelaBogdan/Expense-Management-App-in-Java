import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.HashMap;
import java.util.Map;

public class LoginManager {
    private static String currentUserEmail = "";
    private static Map<String, String> users = new HashMap<>();

    public static void showLogin(Stage primaryStage) {
        Stage loginStage = new Stage();
        loginStage.initStyle(StageStyle.UTILITY);
        loginStage.setTitle("Login");

        VBox vbox = new VBox(10);
        TextField usernameOrEmailField = new TextField();
        usernameOrEmailField.setPromptText("Username or Email");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button loginButton = new Button("Login");
        Button registerButton = new Button("Register");

        vbox.getChildren().addAll(usernameOrEmailField, passwordField, loginButton, registerButton);
        Scene scene = new Scene(vbox, 300, 200);
        scene.getStylesheets().add("styles.css");
        loginStage.setScene(scene);

        loginButton.setOnAction(e -> handleLogin(usernameOrEmailField.getText(), passwordField.getText(), loginStage));
        registerButton.setOnAction(e -> showRegisterScreen(primaryStage));

        loginStage.show();
    }

    private static void handleLogin(String usernameOrEmail, String password, Stage loginStage) {
        ExpenseManager.loadUsers(users);
        if (ExpenseManager.authenticateUser(usernameOrEmail, password, users)) {
            currentUserEmail = ExpenseManager.getUserEmail(usernameOrEmail, users);
            loginStage.close();
            ExpenseManager.showMainApp(new Stage(), currentUserEmail);
        } else {
            UI.showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username, email, or password.");
        }
    }

    private static void showRegisterScreen(Stage primaryStage) {
        Stage registerStage = new Stage();
        registerStage.initStyle(StageStyle.UTILITY);
        registerStage.setTitle("Register");

        VBox vbox = new VBox(10);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        Button registerButton = new Button("Register");

        vbox.getChildren().addAll(usernameField, emailField, passwordField, confirmPasswordField, registerButton);
        Scene scene = new Scene(vbox, 300, 250);
        scene.getStylesheets().add("styles.css");
        registerStage.setScene(scene);

        registerButton.setOnAction(e -> handleRegister(usernameField.getText(), emailField.getText(), passwordField.getText(), confirmPasswordField.getText(), registerStage));

        registerStage.show();
    }

    private static void handleRegister(String username, String email, String password, String confirmPassword, Stage registerStage) {
        ExpenseManager.loadUsers(users);
        if (ExpenseManager.registerUser(username, email, password, confirmPassword, users)) {
            UI.showAlert(Alert.AlertType.INFORMATION, "Registration Successful", "You can now log in.");
            registerStage.close();
        }
    }
}

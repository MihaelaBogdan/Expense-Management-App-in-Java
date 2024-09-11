import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExpenseManagerApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginManager.showLogin(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}

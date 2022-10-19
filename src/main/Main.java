package main;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


/** Main class begins the application and loads the JDBC driver. */
public class Main extends Application {

    /**
     *the start() method loads the LoginPage to begin the application.
     * @param stage
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("../view/LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
    }

    /**
     *The main() method loads the JDBC driver.
     */
    public static void main(String[] args){

        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }
}

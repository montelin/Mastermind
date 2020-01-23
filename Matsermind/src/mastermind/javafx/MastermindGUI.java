package mastermind.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MastermindGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        //Ladda FXML och ställ in kontrollenheten
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layout.fxml"));
        loader.setController(new Controller());
        Parent root = loader.load();

        //Skapa fönstret och sätt huvudscenen
        primaryStage.setTitle("Mastermind av Mats Montelin 2018");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

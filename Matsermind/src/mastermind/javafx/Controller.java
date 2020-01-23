package mastermind.javafx;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.effect.InnerShadow; 
import javafx.scene.text.Text;
import mastermind.Mastermind;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.image.ImageView;

public class Controller implements Initializable {

    @FXML private FlowPane pinPanel;
    @FXML private FlowPane currentGuessPanel;
    @FXML private VBox previousGuessPanel;
    @FXML private Button submitButton;
    @FXML private Button clearButton;
    

    //Privat mastermind-variabler
    private Mastermind mastermind;
    private int maxTurns;
    private int pinNumber;
    private Color[] pinColors;
    private char[] pinColorLetters;
    private String currentGuessString;
   
    
    public Controller() {
        pinColorLetters = new char[]{'R', 'G', 'B', 'O', 'Y', 'P'};
        pinColors = new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.YELLOW, Color.PURPLE};
        maxTurns = 10;
        pinNumber = 4;
        currentGuessString = "";
        mastermind = new Mastermind(maxTurns, pinNumber, pinColorLetters);
        
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Lägg till några cirklar till pennpanelen
        for (int i = 0; i < pinColors.length; i++) {
           Circle circle = new Circle();
            circle.setRadius(20);
            circle.setFill(pinColors[i]);
           circle.setOnMouseClicked(new PinMouseClickListener(pinColorLetters[i]));
            pinPanel.getChildren().add(circle);
            
             //Instantiating the InnerShadow class  
      InnerShadow innerShadow = new InnerShadow(); 
      
      //Setting the offset values of the inner shadow 
      innerShadow.setOffsetX(2); 
      innerShadow.setOffsetY(2); 
      
      //Setting the color of the inner shadow 
       innerShadow.setColor(Color.BLACK);   
      //Applying inner shadow effect to the circle 
      circle.setEffect(innerShadow); 
        }
        
        //Ställ in knapparnas storlek till full bredd 
        //Lägg till onclick-lyssnarna på de 3 knapparna
        submitButton.setMaxWidth(Double.MAX_VALUE);
        clearButton.setMaxWidth(Double.MAX_VALUE);
        
        submitButton.setOnAction(event -> {
            if (mastermind.getCurrentTurn() < maxTurns && !mastermind.isWon() && currentGuessString.length() == pinNumber) {

                //Skicka nuvarande gissträng till mastermind
                mastermind.takeTurn(currentGuessString.toCharArray());
                addPreviousGuess(currentGuessString, mastermind.getCorrectlyColored(), mastermind.getCorrectlyPlaced());
                resetGuess();
                winConditionCheck();
                
            }
            else
                winConditionCheck();
        });
        clearButton.setOnAction(event -> {
            resetGuess();
        });
    }

    //Detta uppdaterar nuvarande gisspanel så att den visar spelaren vad de gör
    //För närvarande har som ett gissning
    public void updateGuessPanel() {

        char[] guessLetters = currentGuessString.toCharArray();
        Circle[] pins = new Circle[guessLetters.length];
        for (int i = 0; i < guessLetters.length; i++) {
            for (int j = 0; j < pinColorLetters.length; j++) {
                if (guessLetters[i] == pinColorLetters[j]) {

                    //Skapa pinnen
                    pins[i] = new Circle();
                    pins[i].setRadius(10);
                    pins[i].setFill(pinColors[j]);
                  
                    break;
                    
                }
            }
        }
        currentGuessPanel.getChildren().clear();
        currentGuessPanel.getChildren().addAll(pins);
        
    }

    //Detta ställer nuvarande gisssträngen för att tömma och
    // uppdatera den aktuella gissningsskärmen så att det inte finns något som
    // en aktuell gissning
    public void resetGuess() {

        currentGuessString = "";
        updateGuessPanel();
    }

    //Den här funktionen lägger till ett inlagt gissning till föregående
    // gissa panel så att spelaren vet vad de redan har
    // gissade tidigare. Vissa text och stift läggs till på skärmen
    public void addPreviousGuess(String guessString, int correctColor, int correctPlaced) {

        Text correctString = new Text("RF: " + correctColor + " PL: " + correctPlaced);
        char[] guessLetters = guessString.toCharArray();
        Circle[] pins = new Circle[guessLetters.length];
        for (int i = 0; i < guessLetters.length; i++) {
            for (int j = 0; j < pinColorLetters.length; j++) {
                if (guessLetters[i] == pinColorLetters[j]) {

                    //Skapa pinnen
                    pins[i] = new Circle();
                    pins[i].setRadius(15);
                    pins[i].setFill(pinColors[j]);
                    
                    break;
                    
                }
            }
        }
        FlowPane flow = new FlowPane();
        flow.setHgap(5);
        flow.setVgap(5);
        flow.getChildren().addAll(pins);
        flow.getChildren().add(correctString);
        previousGuessPanel.getChildren().add(flow);
        
    }

    // Den här funktionen skriver ut ett meddelande på previousGuessPanel
    // Så att användaren kan se textinteraktionerna med spelet
    public void displayMessage(String message) {

        Text text = new Text(message);
        previousGuessPanel.getChildren().add(text);
    }

    // Den här funktionen kontrollerar om spelaren antingen har vunnit eller förlorat
    // Funktionen kommer också att skriva ut antalet varv som spelaren
    // har lämnat för att vinna spelet
    public void winConditionCheck() {
        if (mastermind.getCurrentTurn() < maxTurns) {
            if (mastermind.isWon())
                displayMessage("Grattis! du har vunnit!");
            else
                displayMessage(maxTurns - mastermind.getCurrentTurn() + " gissningar kvar!");
        }
        else {
            if (mastermind.isWon())
                displayMessage("Grattis! du har vunnit!");
            else
                displayMessage("Tyvärr har du förlorat spelet");
        }
    }

    // Den här klassen läggs till gissnålarna så att när de är
    // klickade på stiftet uppdaterar gisssträngen och uppdaterar
    // nuvarande gisspanel så att spelaren kan se deras nuvarande
    // gissar
    class PinMouseClickListener implements EventHandler<MouseEvent> {

        private char letter;

        PinMouseClickListener(char letter) {
            this.letter = letter;
        }

        @Override
        public void handle(MouseEvent event) {

            if (currentGuessString.length() < pinNumber) {
                currentGuessString += letter;
                updateGuessPanel();
            }
        }
    }
}

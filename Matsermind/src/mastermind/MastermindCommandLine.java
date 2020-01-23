package mastermind;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MastermindCommandLine {
    
    Mastermind mastermind;
    BufferedReader br;    
    
    //Få användarna gissa som en sträng
    public char[] getGuess() {
        
        String guess = new String();
        
        //Öppna en buffertläsare för terminalen
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            guess = br.readLine();
        }
        catch(IOException e) {
            System.out.println("Det fanns en IOException");
        }
        
        return mastermind.convertPegString(guess);
    }

    //Starta kommandoradsspelet
    public void start() {
        
        this.mastermind = new Mastermind();
        System.out.println("Huvudkod genererad");
        
        int turnNumber = 0;
        int numCorrectlyPlaced = 0;
        int numCorrectColor = 0;
        
        do {            
            System.out.println("Antal Gissningar: " + (mastermind.getMaxTurns() - turnNumber));
            System.out.print("Skriv kod: ");
            
            //Få användarna gissa
            char[] guess = getGuess();
            
            //Avsluta om q trycks ned
            if ((new String(guess).toLowerCase().equals("q"))) {
                System.out.println("Slutar...");
                return;
            }
            
            //Om de kom in i en felaktig gissning gör dem att göra det igen
            if (!mastermind.correctFormat(guess)) {
                do {
                    System.out.println("Gissa format felaktigt! Försök igen, eller tryck på \"q\" för att avsluta: ");
                    System.out.print("Skriv kod: ");
                    guess = getGuess();
                    
                    if ((new String(guess).toLowerCase().equals("q"))) {
                        System.out.println("Slutar...");
                        return;
                    }
                }
                while(!mastermind.correctFormat(guess));
            }
                        
            numCorrectColor = mastermind.amountCorrectlyColored(guess);
            numCorrectlyPlaced = mastermind.amountCorrectlyPlaced(guess);
            
            System.out.println("RF:" + numCorrectColor + " PL:" + numCorrectlyPlaced);
            if (numCorrectlyPlaced == mastermind.getNumOfPegs()) {
                System.out.println("Grattis! Du har vunnit!");
                return;
            }
            
            turnNumber++;
            
        }
        while(turnNumber < mastermind.getMaxTurns());
        
        System.out.println("Tyvärr har du förlorat!");
    }
    
    public static void main(String[] args) {
        MastermindCommandLine mastermindCommandLine = new MastermindCommandLine();
        mastermindCommandLine.start();
    }
}

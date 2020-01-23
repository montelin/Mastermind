package mastermind;

import java.util.ArrayList;

public class Mastermind {

    //mastermind.Mastermind variabler
    final private char[] colors;
    final private int maxTurns;
    final private int numOfPegs;
    
    //Detta är de aktuella instansvariablerna
    private char[] correctPegs;
    private int currentTurn = 0;
    private int correctlyPlaced = 0;
    private int correctlyColored = 0;
    private boolean won = false;

    //konstruktörer
    public Mastermind() {
        this(10, 4, new char[]{'R', 'G', 'B', 'O', 'Y'});
    }
    public Mastermind(int maxTurns, int numOfPegs, char[] colors) {
        this.maxTurns = maxTurns;
        this.numOfPegs = numOfPegs;
        this.colors = colors;

        //Generera rätt pegs
        correctPegs = new char[numOfPegs];
        for (int i = 0; i < numOfPegs; i++) {
            correctPegs[i] = colors[(int) (Math.floor(Math.random() * colors.length))];
        }

        //skriv ut Pegs(rättPegs);
    }    
    
    //Gameplay funktioner
    //Detta gör att användarna vänder sig med de angivna variablerna
    public void takeTurn(char[] guessPegs) {
        
        //Kontrollera gissningar
        correctlyPlaced = amountCorrectlyPlaced(guessPegs);
        correctlyColored = amountCorrectlyColored(guessPegs);
        
        //Öka nuvarande svängnummer
        currentTurn++;
        
        //Kontrollera om de har vunnit
        if (correctlyPlaced == numOfPegs) {
            won = true;
        }
    }
    
    //Hämta numret på rätt satt pegs
    public int amountCorrectlyPlaced(char[] pegs) {

        int counter = 0;
        for (int i = 0; i < correctPegs.length; i++) {
            if (correctPegs[i] == pegs[i]) {
                counter++;
            }
        }

        return counter;
    }

    //Få antalet korrekta färger
    public int amountCorrectlyColored(char[] pegs) {

        int counter = 0;

        //Array av de färger som inte kontrolleras för tillfället
        ArrayList<Character> colorsLeft = new ArrayList<>(colors.length);
        for (int i = 0; i < pegs.length; i++) {
            colorsLeft.add(new Character(correctPegs[i]));
        }

        //Loop genom de inmatade pinnarna och kontrollera om de korrekta
        //innehålla den färgen
        for (int i = 0; i < pegs.length; i++) {
            for (int j = 0; j < colorsLeft.size(); j++) {
                if (pegs[i] == colorsLeft.get(j)) {
                    counter++;
                    colorsLeft.remove(j);
                    break;
                }
            }
        }

        return counter;
    }

    //Returnera en char array från en angiven sträng
    public char[] convertPegString(String pegs) {

        char[] pegArray = pegs.toCharArray();
        for (int i = 0; i < pegArray.length; i++) {
            pegArray[i] = Character.toUpperCase(pegArray[i]);
        }

        return pegArray;
    }

    //Den här funktionen kommer att se till att gissningen inmatas är i rätt format
    public boolean correctFormat(char[] guess) {

        //Kontrollera för null
        if (guess == null) {
            return false;
        }

        //Kontrollera att den är av rätt längd
        if (guess.length != getNumOfPegs()) {
            return false;
        }

        //Kontrollera om pinnarna har en missformat formaterad peg
        char[] colors = getColors();
        for (int i = 0; i < guess.length; i++) {
            boolean contains = false;
            for (int j = 0; j < colors.length; j++) {
                if (Character.toUpperCase(guess[i]) == colors[j]) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                return false;
            }
        }

        return true;
    }

    //Skriv ut peg array
    public void printPegs(char[] pegs) {
        for (char peg : pegs) {
            System.out.print(peg + " ");
        }
    }

    //Getters för gameplay
    public char[] getColors() {
        return colors;
    }
    public int getMaxTurns() {
        return maxTurns;
    }
    public int getNumOfPegs() {
        return numOfPegs;
    }
    public char[] getCorrectPegs() {
        return correctPegs;
    }
    public int getCurrentTurn() {
        return currentTurn;
    }
    public int getCorrectlyPlaced() {
        return correctlyPlaced;
    }
    public int getCorrectlyColored() {
        return correctlyColored;
    }
    public boolean isWon() {
        return won;
    }    

    //MastermindGUI inmatningsmetod
    public static void main(String[] args) {
        Mastermind mastermind = new Mastermind();
        char[] guess = {'R', 'G', 'Y', 'B'};

        System.out.println("");
        System.out.println(mastermind.amountCorrectlyPlaced(guess));
        System.out.println(mastermind.amountCorrectlyColored(guess));

    }
}

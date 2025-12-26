package game;

// Unlike Zuul, I will be keeping everything together in this one file.

/*
 * Some key takeaways:
 * -- made the text of the prompts more appealing to read
 * -- added some quality-of-life info in said prompts
 * -- added a "help" command that better explains the commands
 */

import java.io.IOException;
import java.util.*;

enum Commands {
    HELP, QUIT, INFO, GIVEUP, GUESS
}

class Command {
    private Commands command;
    private String guess;

    public Command(Commands word) {
        this.command = word;
    }
    public Command(Commands word, String guess) {
        this.command = word;
        this.guess = guess;
    }

    public Commands getCommand() { return command; }
    public String getGuess() { return guess; }
}

class Parser {
    private Scanner reader;
    private HashMap<String, Commands> validCommands;

    public Parser() {
        reader = new Scanner(System.in);
        validCommands = new HashMap<String, Commands>();

        validCommands.put("h", Commands.HELP);
        validCommands.put("q", Commands.QUIT);
        validCommands.put("!", Commands.GIVEUP);
        validCommands.put("?", Commands.INFO);
        validCommands.put("help", Commands.HELP);
        validCommands.put("quit", Commands.QUIT);
        validCommands.put("giveup", Commands.GIVEUP);
        validCommands.put("info", Commands.INFO);

    }

    public Command getCommand() {
        String inputLine;
        String command = null;

        System.out.print("> ");     // print prompt

        inputLine = reader.nextLine();

        Scanner tokenizer = new Scanner(inputLine);

        if(tokenizer.hasNext()) {
            command = tokenizer.next();
        }

        if (validCommands.get(command) == null)
            return new Command(Commands.GUESS, command);
        else {
            return new Command(validCommands.get(command));
        }
    }
}

public class TextTwist {

    private class MuddledStringGenerator {
        // Generate some valid 6-length string
        public Word generate() {
            String validCharacters = "abcdefghijklmnopqrstuvwxyz";
            StringBuilder string = new StringBuilder();
            Random random = new Random();

            boolean isValid = false;

            while (!isValid) {
                string = new StringBuilder();
                for (int i = 0; i < 6; i++) {
                    string.append(validCharacters.charAt(random.nextInt(validCharacters.length())));
                }
                // Check if the string is valid
                Word word = new Word(string.toString());
                // Get subword(s) that can be formed using all letters of word
                List<Word> subWords = allWords.getAllSubWords(word, 6);
                if (!subWords.isEmpty()) {
                    isValid = true;
                }
            }

            return new Word(string.toString());
        }
    }

    private Parser parser;
    private MuddledStringGenerator generator;
    private int score;
    private int highestScore;
    private long startTime;

    private boolean isRevealed;

    public static WordDatabase allWords;
    public Word muddledString;
    public List<Word> allPossibleSubwords;
    public List<Word> correctGuesses;

    public TextTwist() throws IOException {
        parser = new Parser();
        generator = new MuddledStringGenerator();
        allWords = new WordDatabase("linuxwords.txt");

        muddledString = generator.generate();
        allPossibleSubwords = allWords.getAllSubWords(muddledString, 1);
        score = 0;
        highestScore = allPossibleSubwords.size();
        correctGuesses = new ArrayList<Word>();
        isRevealed = false;
        startTime = System.currentTimeMillis();
    }

    private void printWelcome() {
        System.out.println("***");
        System.out.println("Welcome to TextTwist!");
        System.out.println("A pretty primitive guess-the-words game.");
        System.out.println("Type 'help' if you need help. Otherwise, enjoy!");
        System.out.println("***");
    }

    private void printHelp() {
        System.out.println("***");
        System.out.println("Here are all of the available commands:");
        System.out.println("'h, help' -> brings out this text.");
        System.out.println("'?, info' -> gives current information about the game, including what you've revealed and what you've yet to reveal.");
        System.out.println("'!, giveup' -> reveals all possible words and then starts a new game.");
        System.out.println("'q, quit' -> exit the game.");
        System.out.println("Anything else you say will be considered a guess!");
        System.out.println("***");
    }

    private void printNecessaryInfo() {
        System.out.println();
        System.out.println("[" + muddledString.getWord() + "]");
        System.out.println("Score: " + score + "/" + highestScore);
        System.out.println("Elapsed time: ~" + ((System.currentTimeMillis() - startTime) / 1000) + " seconds");
        System.out.println();
    }

    private void giveInfo() {
        for (int i = 1; i <= 6; i++) {
            StringBuilder string = new StringBuilder();
            for (Word word: allPossibleSubwords) {
                if (word.getHistogram().getTotalCount() == i) {
                    if (!isRevealed) {
                        if (correctGuesses.contains(word)) {
                            string.append(word.getWord());
                            string.append(" ");
                        } else {
                            string.append("?".repeat(i));
                            string.append(" ");
                        }
                    } else {
                        string.append(word.getWord());
                        string.append(" ");
                    }
                }
            }
            if (!string.isEmpty())
                System.out.println(string);
        }
    }

    private void reveal() {
        giveInfo();

        System.out.println("\nStarting a new game below...");
        System.out.println("Disclaimer: generating a valid 6-letter combination can take a bit of time.");

        isRevealed = false;
        muddledString = generator.generate();
        allPossibleSubwords.clear();
        allPossibleSubwords = allWords.getAllSubWords(muddledString, 1);
        score = 0;
        highestScore = allPossibleSubwords.size();
        correctGuesses.clear();
        startTime = System.currentTimeMillis();
    }

    private void doGuess(String guess) {
        for (Word word: allPossibleSubwords) {
            if (guess.equals(word.getWord())) {
                score++;
                System.out.println(word.getWord() + " is a correct guess! " + (highestScore - score) + " words remaining.");
                correctGuesses.add(word);
            }
        }
        if (score == highestScore) {
            System.out.println("\nWow! You guessed everything!");
            reveal();
        }
    }

    private boolean processCommand(Command command) {
        boolean wantToQuit = false;
        switch(command.getCommand()) {
            case QUIT:
                wantToQuit = true;
                break;
            case HELP:
                printHelp();
                break;
            case GIVEUP:
                isRevealed = true;
                reveal();
                break;
            case INFO:
                giveInfo();
                break;
            case GUESS:
                doGuess(command.getGuess());
                break;
        }

        return wantToQuit;
    }
    public void play()
    {
        printWelcome();

        boolean finished = false;
        while (!finished) {
            printNecessaryInfo();
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thanks for playing!");
    }

    public static void main(String[] args) throws IOException {
        TextTwist Game = new TextTwist();

        Game.play();

    }
}
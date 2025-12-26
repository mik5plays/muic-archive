/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Random;

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private List<Room> allRooms;
    private Stack<Room> roomsVisited;

    private Room outside; // Default room to handle when stack is empty
    private Room magic;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        roomsVisited = new Stack<Room>();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, theater, pub, lab, office, coworking, library, magic;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        // additional rooms
        coworking = new Room("in the coworking space");
        library = new Room("in the library");
        magic = new Room("in a magic room. wooooo...");
        
        // initialise room exits
        outside.setExit("south", theater);
        outside.setExit("east", lab);
        outside.setExit("west", pub);
        theater.setExit("north", outside);
        pub.setExit("east", outside);
        lab.setExit("west", outside);
        lab.setExit("south", office);
        lab.setExit("north", coworking);
        office.setExit("north", lab);

        coworking.setExit("south", lab);
        coworking.setExit("up", library);

        library.setExit("down", coworking);
        library.setExit("west", magic);

        // These rooms are special.
        this.outside = outside;
        this.magic = magic;

        allRooms = new ArrayList<Room>();
        allRooms.add(outside);
        allRooms.add(theater);
        allRooms.add(pub);
        allRooms.add(lab);
        allRooms.add(office);
        allRooms.add(coworking);
        allRooms.add(library);
        // To prevent being stuck in a loop, I won't consider magic room as a "room"
        // so we don't end up in it more than once.

        currentRoom = outside; // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    private String getLocations() {
        StringBuilder result = new StringBuilder();
        result.append("You are ").append(currentRoom.getDescription());
        result.append('\n');
        result.append("Exits: ");
        result.append(currentRoom.getExits());

        return result.toString();
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();

        System.out.println(getLocations());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */

    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();
        switch(commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;
            case LOOK:
                userLook();
                break;
            case BACK:
                userGoBackStack();
                break;
            case HELP:
                printHelp();
                break;
            case GO:
                goRoom(command);
                break;
            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Prints out where the user currently is.
     * This probably doesn't need its own method as-is, but in case
     * I want to add any extra information, I will consider it as
     * its own standalone method for now.
     */

    private void userLook() {
        System.out.println(getLocations());
    }

    /**
     * Go back to where the user once was.
     * Uses a stack to keep track of what rooms have been entered
     * as per Ajarn's comments
     */
    private void userGoBackStack() {
        if (roomsVisited.empty()) {
            System.out.println("You are yet to go inside!");
            return;
        }
        roomsVisited.pop();
        currentRoom = roomsVisited.empty() ? outside : roomsVisited.peek(); // Defaulting to outside if stack is empty
        System.out.println(getLocations());
    }

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println("   go quit help look back");
    }

    /** 
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        if (parser.getValidDirections().get(command.getSecondWord()) == null) {
            System.out.println("That's not a direction!");
            return;
        }
        Direction direction = Parser.getDirection(command.getSecondWord());

        Room nextRoom = null;

        switch(direction) {
            case NORTH:
                nextRoom = currentRoom.getNorthExit();
                break;
            case SOUTH:
                nextRoom = currentRoom.getSouthExit();
                break;
            case EAST:
                nextRoom = currentRoom.getEastExit();
                break;
            case WEST:
                nextRoom = currentRoom.getWestExit();
                break;
            case UP:
                nextRoom = currentRoom.getUpExit();
                break;
            case DOWN:
                nextRoom = currentRoom.getDownExit();
                break;
        }

        // Try to leave current room.

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            if (nextRoom != magic) {
                currentRoom = nextRoom;
            } else {
                /**
                 * We just stepped into the magic room!
                 * The magic room takes us to some random place in the university.
                 * When using back, it will return to the room BEFORE the magic room.
                 * In this case, that room is the library.
                 *
                 * Note that I have included the possibility to go outside (since
                 * it is technically a Room class) as well as back to the library.
                 * The task asked for any room, so I adhered to that.
                 */
                System.out.println("You just stepped in the magic room.. Whoosh~~\n");
                Random random = new Random();
                currentRoom = allRooms.get(random.nextInt(allRooms.size()));
            }
            roomsVisited.push(currentRoom);
            System.out.println(getLocations());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}

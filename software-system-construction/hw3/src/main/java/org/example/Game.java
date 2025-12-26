package org.example;

import commands.*;
import dictionary.CentralDictionary;
import enums.Direction;
import objects.Item;

import java.io.Serializable;
import java.util.*;
import java.lang.StringBuilder;

public class Game implements Serializable {
    /*
    Some key variables of game
     */
    private static final long serialVersionUID = 1L;
    public final CentralDictionary dictionary = new CentralDictionary();

    private int turn;
    private Player player;
    private transient Parser parser;
    public Remote remote;

    public boolean finished;
    public List<Room> map;
    public String asciiMap;

    public Game() {
        turn = 0;
        finished = false;
        map = new ArrayList<>();
        asciiMap = "";
        parser = new Parser(this);
        player = new Player(null);
        remote = new Remote(new CommandFactory());
    }

    public int getTurn() { return turn; }
    public void incrementTurn() { turn++; }

    public void displayRoomInfo() {
        /*
        A turn is when the player does a combat-related action:
        go, attack,
         */
        Room position = player.getPosition();
        System.out.printf("You are in %s.\n", position.name);
        if (position.monster == null) { System.out.println("There are no monsters in this room."); }
        else { System.out.printf("There is one monster - %s.\n", position.monster.name); }
        StringBuilder adjacentRooms = new StringBuilder("Neighbours: ");
        for (Map.Entry<Direction, Room> neighbour: position.neighbours.entrySet()) {
            adjacentRooms.append(neighbour.getKey().name().toLowerCase());
            adjacentRooms.append(": ");
            if (neighbour.getValue() == null) { adjacentRooms.append("none"); }
            else { adjacentRooms.append(neighbour.getValue().name); }
            adjacentRooms.append(" | ");
        }
        StringBuilder itemsInRoom = new StringBuilder();
        if (position.items.isEmpty()) { itemsInRoom.append("There are no items in this room."); }
        else {
            itemsInRoom.append("Items: ");
            for (Item item: position.items) {
                itemsInRoom.append(item.name);
                itemsInRoom.append(" | ");
            }
        }
        System.out.println(adjacentRooms);
        System.out.println(itemsInRoom);
    }

    public void displayStartupPrompt() {
        System.out.println("Welcome to Zork.");
        System.out.println("-- To start a new game, do play <map>.");
        System.out.println("-- To continue an existing game, do load <save_file>");
    }

    public void onFinish() { // A finish that isn't administered by user commands
        System.out.printf("You survived %d turns.\n", turn);
        System.out.println("-- Start afresh, or start a new map by doing play <map>");

        player.reset();
        turn = 0;
    }

    public void play() {
        displayStartupPrompt();
        while (!finished) {
            CommandInput query = parser.get();
            finished = remote.process(this, player, query);
        }
    }
}

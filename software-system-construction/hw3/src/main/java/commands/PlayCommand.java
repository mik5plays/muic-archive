package commands;

import enums.Direction;
import enums.Items;
import enums.PlayerProperty;
import items.*;
import monsters.*;
import org.example.*;
import weapons.*;
import objects.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class PlayCommand implements Command {
    private Scanner mapReader; // map reader
    private final String description = "Start a new game from a given map file. Usage: play <map_file_name>";

    private Room findInMap(Game game, String name) {
        for (Room room: game.map) {
            if (room.name.equalsIgnoreCase(name)) { return room; }
        }
        return null;
    }

    @Override
    public String description() { return description; }

    @Override
    public void execute(Game game, Player player, String arg) {

        try {
            // start a new map and run here entirely.
            game.map.clear();

            mapReader = new Scanner(new File(arg));
            while (mapReader.hasNextLine()) {
                String line = mapReader.nextLine();

                if (line.startsWith("create:")) { // create a new room, if room does not exist.
                    String substring = line.substring("create:".length());
                    String[] args = substring.split(",");
                    String name = args[0];
                    String monster = args[1];
                    if (args.length != 2) {
                        System.out.println("Error: Invalid number of arguments. Must be 2.\n");
                        continue;
                    }
                    if (findInMap(game, name) == null) {
                        game.map.add(new Room(name));
                    }
                    Monster monsterToAdd = switch (game.dictionary.monsterDictionary.lookup(monster)) {
                        case ZOMBIE -> new Zombie();
                        case VAGRANT -> new Vagrant();
                        case OGRE -> new Ogre();
                        case DUKE -> new Duke();
                        default -> null;
                    };
                    if (monsterToAdd != null) { findInMap(game, name).addMonster(monsterToAdd); }
                } else if (line.startsWith("neighbours:")) {
                    String substring = line.substring("neighbours:".length());
                    String[] args = substring.split(",");
                    if (args.length != 5) {
                        System.out.println("Error: Invalid number of arguments. Must be 5.\n");
                        continue;
                    }
                    String name = args[0];
                    String neighbourNorth = args[1];
                    String neighbourSouth = args[2];
                    String neighbourEast = args[3];
                    String neighbourWest = args[4];

                    if (findInMap(game, name) == null) {
                        System.out.printf("Error: %s is not a room. Run create:%s first.\n", name, name);
                        continue;
                    }
                    Room room = findInMap(game, name);
                    assert room != null;
                    if (!"none".equals(neighbourNorth)) {
                        if (findInMap(game, neighbourNorth) == null) {
                            System.out.printf("Error: %s is not a room. Run create:%s first.\n", neighbourNorth, neighbourNorth);
                            continue;
                        } else {
                            room.addNeighbour(Direction.NORTH, findInMap(game, neighbourNorth));
                        }
                    }
                    if (!"none".equals(neighbourSouth)) {
                        if (findInMap(game, neighbourSouth) == null) {
                            System.out.printf("Error: %s is not a room. Run create:%s first.\n", neighbourSouth, neighbourSouth);
                            continue;
                        } else {
                            room.addNeighbour(Direction.SOUTH, findInMap(game, neighbourSouth));
                        }
                    }
                    if (!"none".equals(neighbourEast)) {
                        if (findInMap(game, neighbourEast) == null) {
                            System.out.printf("Error: %s is not a room. Run create:%s first.\n", neighbourEast, neighbourEast);
                            continue;
                        } else {
                            room.addNeighbour(Direction.EAST, findInMap(game, neighbourEast));
                        }
                    }
                    if (!"none".equals(neighbourWest)) {
                        if (findInMap(game, neighbourWest) == null) {
                            System.out.printf("Error: %s is not a room. Run create:%s first.\n", neighbourWest, neighbourWest);
                            continue;
                        } else {
                            room.addNeighbour(Direction.WEST, findInMap(game, neighbourWest));
                        }
                    }
                } else if (line.startsWith("items:")) {
                    String substring = line.substring("items:".length());
                    String[] args = substring.split(",");

                    String name = args[0];
                    if (findInMap(game, name) == null) {
                        System.out.printf("Error: %s is not a room. Run create:%s first.\n", name, name);
                        continue;
                    }

                    for (int i = 1; i < args.length; i++) {
                        if (game.dictionary.itemDictionary.lookup(args[i]) == Items.UNKNOWN) {
                            System.out.printf("%s: Invalid item %s.\n", name, args[i]);
                        } else {
                            Item itemToAdd = switch (game.dictionary.itemDictionary.lookup(args[i])) {
                                case CHICKEN -> new Chicken();
                                case HEALINGPOTION -> new HealingPotion();
                                case MAGICSTEW -> new MagicStew();
                                case STEAKDINNER -> new SteakDinner();
                                case STRENGTHPOTION -> new StrengthPotion();
                                case FISTS -> new Fist();
                                case WOODENSWORD -> new WoodenSword();
                                case ENCHANTEDSWORD -> new EnchantedSword();
                                case SPACEPOTION -> new SpacePotion();
                                case UNKNOWN -> null;
                            };
                            findInMap(game, name).addItem(itemToAdd);
                        }
                    }
                } else if (line.startsWith("map:")) {
                    game.asciiMap = line.substring("map:".length()).replace("\\n", "\n");;
                } else {
                    System.out.println("Invalid starting prefix. Must be create,items,neighbours");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.printf("Invalid map file name %s, try again.\n", arg);
            return;
        }

        player.reset();
        if (game.map.isEmpty()) { System.out.println("Empty map? Something is wrong."); return; }
        player.inventory.add(new Fist());
        player.position = game.map.get(0);
        if (!player.position.hasVisited) { player.changeStat(PlayerProperty.MAXHP, 10); player.position.hasVisited = true; }
        System.out.printf("Loaded %s. %s has %d rooms.\n", arg, arg, game.map.size());
        game.displayRoomInfo();
    }
}

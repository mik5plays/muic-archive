import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    public String description;

    private HashMap<String, Room> neighbours;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */

    public Room(String description) 
    {
        this.description = description;
        neighbours = new HashMap<String, Room>();
        neighbours.put("north", null);
        neighbours.put("south", null);
        neighbours.put("east", null);
        neighbours.put("west", null);
        neighbours.put("up", null);
        neighbours.put("down", null);
    }

    // Some get functions for our private variables

    public Room getNorthExit() { return neighbours.get("north"); }
    public Room getSouthExit() { return neighbours.get("south"); }
    public Room getEastExit() { return neighbours.get("east"); }
    public Room getWestExit() { return neighbours.get("west"); }
    public Room getUpExit() { return neighbours.get("up"); }
    public Room getDownExit() { return neighbours.get("down"); }

    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param direction Direction we want to go to.
     * @param neighbour What room is in this direction
     */

    public void setExit(String direction, Room neighbour)
    {
        switch(direction) {
            case "north":
                neighbours.put("north", neighbour);
                break;
            case "south":
                neighbours.put("south", neighbour);;
                break;
            case "east":
                neighbours.put("east", neighbour);
                break;
            case "west":
                neighbours.put("west", neighbour);
                break;
            case "up":
                neighbours.put("up", neighbour);
                break;
            case "down":
                neighbours.put("down", neighbour);
                break;
        }
    }

    /**
     * Returns a string containing all the possible, non-null exits
     * of a room. Used for the print functions.
     * @return a string of possible exits
     */
    public String getExits() {
        StringBuilder x = new StringBuilder();
        if (neighbours.get("north") != null) {
            x.append("north ");
        }
        if (neighbours.get("south") != null) {
            x.append("south ");
        }
        if (neighbours.get("east") != null) {
            x.append("east ");
        }
        if (neighbours.get("west") != null) {
            x.append("west ");
        }
        if (neighbours.get("up") != null) {
            x.append("up ");
        }
        if (neighbours.get("down") != null) {
            x.append("down ");
        }
        return x.toString();

    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

}

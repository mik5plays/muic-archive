package org.example;
import enums.Direction;
import objects.*;

import java.io.Serializable;
import java.util.*;


public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
    Some variables that all Rooms should have
     */
    public String name;
    public List<Item> items;
    public Monster monster; // the monster (singular) that inhabits the room
    public boolean hasVisited; // if player has visited, don't increment health
    public Map<Direction, Room> neighbours; // all adjacent neighbours (if applicable)

    public Room(String name) {
        this.name = name;
        items = new ArrayList<>();
        hasVisited = false;
        neighbours = new HashMap<Direction, Room>();
        neighbours.put(Direction.NORTH, null);
        neighbours.put(Direction.SOUTH, null);
        neighbours.put(Direction.EAST, null);
        neighbours.put(Direction.WEST, null);
    }

    public void addNeighbour(Direction dir, Room room) { neighbours.put(dir, room); }

    public void addItem(Item item) { items.add(item); }

    public void addMonster(Monster monster) { this.monster = monster; }
    public void removeMonster() { this.monster = null; }

    public void playerTakeItem(Player player, Item itemTaken) {
        Iterator<Item> itemsIter = this.items.iterator();
        while (itemsIter.hasNext()) {
            Item item = itemsIter.next();
            if (item.equals(itemTaken)) {
                itemsIter.remove();
                break;
            }
        }
        player.inventory.add(itemTaken);
    }

}

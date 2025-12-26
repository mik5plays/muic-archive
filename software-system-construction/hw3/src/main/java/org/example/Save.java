package org.example;

import objects.Item;

import java.io.Serializable;
import java.util.*;

public class Save implements Serializable {

    private static final long serialVersionUID = 1L;

    public Room position;
    public double health;
    public double maxHealth;
    public double attackPower;
    public double critRate;
    public double critDamage;
    public double inventorySlots;

    public List<Item> inventory;
    public List<Room> map;
    public String asciiMap;

    public Save(String asciiMap, List<Room> map, double health, double maxHealth, double attackPower, double critRate, double critDamage,
                List<Item> inventory, double inventorySlots, Room position) {
        this.asciiMap = asciiMap;
        this.map = map;
        this.health = health;
        this.maxHealth = maxHealth;
        this.attackPower = attackPower;
        this.critRate = critRate;
        this.critDamage = critDamage;
        this.inventory = new ArrayList<>();
        this.inventory.addAll(inventory);
        this.inventorySlots = inventorySlots;
        this.position = position;
    }
}

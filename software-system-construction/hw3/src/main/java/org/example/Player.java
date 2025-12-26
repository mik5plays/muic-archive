package org.example;

import enums.PlayerProperty;
import objects.*;

import java.io.Serializable;
import java.util.*;

public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
    A player has these values;
     */

    public Room position;

    private double health;
    private double maxHealth;
    private double attackPower;
    private double critRate;
    private double critDamage;
    private double inventorySlots;

    public List<Item> inventory;

    public Player(Room position) {
        // some default stats that the player has:
        this.position = position;
        this.inventory = new ArrayList<>();

        this.health = 100;
        this.maxHealth = 90; // being in the starting room increments health by 10 anyway
        this.attackPower = 1;
        this.critRate = 0.2; // 20% crit rate base
        this.critDamage = 2; // 200% crit damage base (so 10 dmg does 20)
        this.inventorySlots = 5;
    }

    public double getHealth() { return this.health; }
    public double getMaxHealth() { return this.maxHealth; }
    public double getAttackPower() { return this.attackPower; }
    public double getCritRate() { return this.critRate; }
    public double getCritDamage() { return this.critDamage; }

    public List<Item> getInventory() { return this.inventory; }
    public double getInventorySlots() { return this.inventorySlots; }
    public double getInventorySize() { return this.inventory.size(); }
    public Room getPosition() { return this.position; }

    // forcibly set values, only used when loading from a save file
    public void setValuesFromLoad(double health, double maxHealth, double attackPower, double critRate, double critDamage,
                                  List<Item> inventory, double inventorySlots, Room position) {
        this.health = health;
        this.maxHealth = maxHealth;
        this.attackPower = attackPower;
        this.critRate = critRate;
        this.critDamage = critDamage;
        this.inventory.clear();
        this.inventory.addAll(inventory);
        this.inventorySlots = inventorySlots;
        this.position = position;
    }
    public void changeStat(PlayerProperty property, double factor) {
        switch (property) {
            case HP:
                if (this.health + factor > this.maxHealth) { this.health = this.maxHealth; } // cannot heal past full
                else { this.health += factor; }
                break;
            case MAXHP:
                this.maxHealth += factor;
                break;
            case ATTACKPOWER:
                this.attackPower += factor;
                break;
            case CRITRATE:
                this.critRate += factor;
                break;
            case CRITDMG:
                this.critDamage += factor;
                break;
            case INVENTORYSLOTS:
                this.inventorySlots += factor;
                break;
        }
    }


    public void reset() {
        this.position = null;
        this.inventory.clear();

        this.health = 100;
        this.maxHealth = 90;
        this.attackPower = 1;
        this.critRate = 0.2; // 20% crit rate base
        this.critDamage = 2; // 200% crit damage base (so 10 dmg does 20)
        this.inventorySlots = 5;

    }

    public void onDead(Game game, Monster monster) {
        System.out.printf("You died! You were killed by %s.\n", monster.name);
        game.onFinish();
    }
}

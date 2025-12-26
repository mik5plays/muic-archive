package objects;

import enums.MonsterType;
import enums.PlayerProperty;
import org.example.*;

import java.io.Serializable;
import java.util.Random;

abstract public class Monster implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Random random = new Random(); // random generator

    public String name;
    public String description;
    public MonsterType type;
    public Item drop;
    public double dropChance;

    protected double health;
    protected double maxHealth;
    protected double baseAttack;
    protected double attackPower;
    protected double critRate;
    protected double critDamage;

    public Monster(String name, String description, MonsterType type, Item drop, double dropChance,
                   double health, double maxHealth, double baseAttack, double attackPower, double critRate, double critDamage) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.drop = drop;
        this.dropChance = dropChance;

        this.health = health;
        this.maxHealth = maxHealth;
        this.baseAttack = baseAttack;
        this.attackPower = attackPower;
        this.critRate = critRate;
        this.critDamage = critDamage;
    }

    public void attack(Game game, Player player) {
        double damage = this.baseAttack * this.attackPower;
        if (random.nextDouble() <= this.critRate) {
            damage *= this.critDamage;
        }

        player.changeStat(PlayerProperty.HP, -damage);

        if (player.getHealth() <= 0) { player.onDead(game, this); }
        else { System.out.printf("%s hit you and dealt %.1f damage! You have %.1f health remaining.\n", this.name, damage, player.getHealth()); }
    }

    public void attacked(Game game, Player player, Weapon weapon) {
        double damage = weapon.factor * player.getAttackPower();
        if (random.nextDouble() <= player.getCritRate()) {
            damage *= player.getCritDamage();
        }
        this.health -= damage;

        if (this.health <= 0) { this.onKill(game, player); }
        else { System.out.printf("You hit %s and dealt %.1f damage! %s has %.1f health remaining.\n", this.name, damage, this.name, this.health); attack(game, player); }
    }

    public void onKill(Game game, Player player) {
        Room room = player.getPosition();
        player.changeStat(PlayerProperty.ATTACKPOWER, 0.1); // attack power increases after every kill.
        if (random.nextDouble() <= this.dropChance) { // see if it drops an item
            System.out.printf("You killed %s! It dropped %s.\n", this.name, this.drop.name);
            room.removeMonster();
            room.addItem(this.drop);
        } else {
            System.out.printf("You killed %s! It dropped nothing.\n", this.name);
            room.removeMonster();
        }
    }
}

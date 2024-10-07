package com.itbd.protisthan.others.game;

public abstract class GameCharacter {
    private String name;
    private int health;

    public GameCharacter(String name) {
        this.name = name;
        this.health = 100;
    }

    public String sayName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public abstract void damageCharacter(GameCharacter character);

    public abstract boolean successfulDefence();

    public abstract String sayDamage();

    public abstract String sayDefence();
}
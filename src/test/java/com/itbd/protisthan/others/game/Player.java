package com.itbd.protisthan.others.game;

import java.util.Random;

public class Player extends GameCharacter {
    private Random random;

    public Player(String name) {
        super(name);
        this.random = new Random();
    }

    public void damageCharacter(GameCharacter character) {
        if (!character.successfulDefence()) {
            character.setHealth(character.getHealth() - 50);
        }
    }

    public boolean successfulDefence() {
        return random.nextInt(10) < 3; // 30% chance
    }

    public String sayDamage() {
        return "Player damaged!";
    }

    public String sayDefence() {
        return "Player defended!";
    }
}
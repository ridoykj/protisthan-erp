package com.itbd.protisthan.others.game;


import java.util.Random;

public class Robot extends GameCharacter {
    private Random random;

    public Robot(String name) {
        super(name);
        this.random = new Random();
    }

    public void damageCharacter(GameCharacter character) {
        if (!character.successfulDefence()) {
            character.setHealth(character.getHealth() - 20);
        }
    }

    public boolean successfulDefence() {
        return random.nextInt(2) == 0; // 50% chance
    }

    public String sayDamage() {
        return "Robot damaged!";
    }

    public String sayDefence() {
        return "Robot defended!";
    }
}

package org.snakeLadder.models;

import java.util.Random;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Dice {
    private final int diceCount ;
    private final Random random ;
    
    public int rollDice(){
        return random.nextInt(diceCount) + 1 ;
    }
}

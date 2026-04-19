package com.sc2002.arena.engine;
/**
 * Used by BattleEngine.checkVictoryConditions() and runBattle() to drive
 * the main battle loop and determine which end screen to display.
 *
 * ONGOING – neither side has won yet; the battle loop continues
 * VICTORY – all enemies defeated (including backup wave if applicable)
 * DEFEAT  – the player's HP has reached 0
 */
public enum BattleOutcome {
    ONGOING,
    VICTORY,
    DEFEAT
}

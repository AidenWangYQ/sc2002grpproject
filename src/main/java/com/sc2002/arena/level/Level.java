package com.sc2002.arena.level;

/**
 * Describes one difficulty level: its initial enemy wave and an optional
 * backup wave that spawns after the initial wave is fully defeated.
 *
 * SRP  – holds level configuration data only; no spawn logic.
 *         SpawnManager reads this and decides when to trigger backup.
 * OCP  – new levels are added via LevelFactory without touching this class.
 */
public class Level {
 
    public enum Difficulty { EASY, MEDIUM, HARD, BOSS } // Used to represent the different difficulties of the game, which will in the end determine the different enemies the user will face in the battle.
 
    private final Difficulty difficulty;
    private final int levelNumber;
    private final Wave initialWave;
    private final Wave backupWave;
 
    public Level(int levelNumber, Difficulty difficulty, //Used to initialise the level with the different parameters such as the level number, difficulty, initial wave and backup wave.
                 Wave initialWave, Wave backupWave) {
        this.levelNumber  = levelNumber;
        this.difficulty   = difficulty;
        this.initialWave  = initialWave;
        this.backupWave   = backupWave;
    }
 
    public int        getLevelNumber()  { return levelNumber; }  //used to show the user which level number they are on
    public Difficulty getDifficulty()   { return difficulty; }  //used to show the user which difficulty they have chosen
    public Wave       getInitialWave()  { return initialWave; } //used to get the initial wave of enemies that the user will face at the start 
    public Wave       getBackupWave()   { return backupWave; }  //used to get the backup wave of enemies 
    public boolean    hasBackupWave()   { return !backupWave.isEmpty(); } //used to check if there is a backup wave of enemies 
 
    @Override
    public String toString() { //used to return the string representation of the level number and difficulty for the user to see when they are choosing the difficulty of the game.
        return "Level " + levelNumber + " (" + difficulty + ")";
    }
}

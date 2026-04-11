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
 
    public enum Difficulty { EASY, MEDIUM, HARD }
 
    private final Difficulty difficulty;
    private final int levelNumber;
    private final Wave initialWave;
    private final Wave backupWave;
 
    public Level(int levelNumber, Difficulty difficulty,
                 Wave initialWave, Wave backupWave) {
        this.levelNumber  = levelNumber;
        this.difficulty   = difficulty;
        this.initialWave  = initialWave;
        this.backupWave   = backupWave;
    }
 
    public int        getLevelNumber()  { return levelNumber; }
    public Difficulty getDifficulty()   { return difficulty; }
    public Wave       getInitialWave()  { return initialWave; }
    public Wave       getBackupWave()   { return backupWave; }
    public boolean    hasBackupWave()   { return !backupWave.isEmpty(); }
 
    @Override
    public String toString() {
        return "Level " + levelNumber + " (" + difficulty + ")";
    }
}
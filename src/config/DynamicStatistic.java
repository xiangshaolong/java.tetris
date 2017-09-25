package config;

import java.awt.*;

public class DynamicStatistic {

    /**
     * game status
     * pause or play
     */
    private boolean isGamePause = true;

    public boolean isGamePause() {
        return isGamePause;
    }

    public void setGamePause(boolean gamePause) {
        isGamePause = gamePause;
    }

    /**
     * game over
     */
    private boolean isGameOVer = false;

    public boolean isGameOVer() {
        return isGameOVer;
    }

    public void setGameOVer(boolean isGameOver) {
        this.isGameOVer = isGameOver;
    }

    /**
     * current level
     */
    private int currentLevel = 0;

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    /**
     * current score
     */
    private int currentScore = 0;

    public int getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }


    /**
     * current removed lines
     */
    private int currentRemovedLines = 0;

    public int getCurrentRemovedLines() {
        return currentRemovedLines;
    }

    public void setCurrentRemovedLines(int currentRemovedLines) {
        this.currentRemovedLines = currentRemovedLines;
    }

    /**
     * block type
     */
    private int currentBlockType;

    public int getCurrentBlockType() {
        return currentBlockType;
    }

    public void setCurrentBlockType(int currentBlockType) {
        this.currentBlockType = currentBlockType;
    }

    /**
     * next block type
     */
    private int nextBlockTYpe;

    public int getNextBlockTYpe() {
        return nextBlockTYpe;
    }

    public void setNextBlockTYpe(int nextBlockTYpe) {
        this.nextBlockTYpe = nextBlockTYpe;
    }

    /**
     * current block array
     */

    private Point[] currentBlockArray;

    public Point[] getCurrentBlockArray() {
        return currentBlockArray;
    }

    public void setCurrentBlockArray(Point[] currentBlockArray) {
        this.currentBlockArray = currentBlockArray;
    }

    /**
     * game map
     */
    private boolean[][] gameMap = new boolean[StaticConfig.GAME_MAP_ROWS][StaticConfig.GAME_MAP_COLUMNS];

    public boolean[][] getGameMap() {
        return gameMap;
    }

    public void setGameMap() {
        this.gameMap = new boolean[StaticConfig.GAME_MAP_ROWS][StaticConfig.GAME_MAP_COLUMNS];
    }

    /**
     * game speed
     */
    private int gameCurrentSpeedInterval;

    public int getGameCurrentSpeedInterval() {
        return gameCurrentSpeedInterval;
    }

    public void setGameCurrentSpeedInterval(int gameCurrentSpeedInterval) {
        this.gameCurrentSpeedInterval = gameCurrentSpeedInterval;
    }

    /**
     * game local highest score
     */
    private int localHighestScore = 0;

    public int getLocalHighestScore() {
        return localHighestScore;
    }

    public void setLocalHighestScore(int localHighestScore) {
        this.localHighestScore = localHighestScore;
    }

    /**
     * game world highest score
     */
    private int worldHighestScore = 0;

    public int getWorldHighestScore() {
        return worldHighestScore;
    }

    public void setWorldHighestScore(int worldHighestScore) {
        this.worldHighestScore = worldHighestScore;
    }
}

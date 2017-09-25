package controller;

import config.BlockPointsArray;
import config.StaticConfig;
import config.DynamicStatistic;
import database.ConnectionInfo;
import database.GetSetWorldRecord;
import localFile.GetSetLocalRecord;
import localFile.LocalRecordDataStructure;
import ui.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

public class GameFunction {
    private DynamicStatistic dynamicStatistic;
    private Random random = new Random();
    private GetSetLocalRecord getSetLocalRecord;
    private GetSetWorldRecord getSetWorldRecord;
    private GamePanel gamePanel;

    // constructor
    public GameFunction(
            DynamicStatistic dynamicStatistic,
            GetSetLocalRecord getSetLocalRecord,
            GetSetWorldRecord getSetWorldRecord,
            GamePanel gamePanel
    ) {
        this.gamePanel = gamePanel;
        this.dynamicStatistic = dynamicStatistic;
        // get local record from local file
        this.getSetLocalRecord = getSetLocalRecord;
        // get local record and write to dynamicStatistic
        LocalRecordDataStructure localRecordDataStructure = null;
        try {
            localRecordDataStructure = this.getSetLocalRecord.readRecord();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        assert localRecordDataStructure != null;
        this.dynamicStatistic.setLocalHighestScore(localRecordDataStructure.getHighestScore());

        if (ConnectionInfo.IS_DATABASE_CONNECTION) {
            // get world record from mongodb
            this.getSetWorldRecord = getSetWorldRecord;
            // then write to dynamicStatistic
            this.dynamicStatistic.setWorldHighestScore(this.getSetWorldRecord.getDocumentValue());
        }
        // set initial game speed interval
        this.dynamicStatistic.setGameCurrentSpeedInterval(StaticConfig.INITIAL_GAME_SPEED_INTERVAL);
    }

    /**
     * can move down block
     */
    private boolean canMoveDownBlock() {
        Point[] currentBlockArray = this.dynamicStatistic.getCurrentBlockArray();
        boolean[][] gameMap = this.dynamicStatistic.getGameMap();
        for (Point point : currentBlockArray) {
            int newY = point.y + 1;
            if (newY > StaticConfig.GAME_MAP_Y_MAX) {
                return false;
            } else if (gameMap[newY][point.x]) {
                return false;
            }
        }
        return true;
    }

    /**
     * move block
     */
    private boolean canMove(int x, int y, Point[] blockArray, boolean[][] gameMap) {
        // return is out of range
        for (Point point : blockArray) {
            int newX = point.x + x;
            int newY = point.y + y;
            if (newX > StaticConfig.GAME_MAP_X_MAX || newX < StaticConfig.GAME_MAP_X_MIN || newY > StaticConfig.GAME_MAP_Y_MAX || gameMap[newY][newX]) {
                return true;
            }
        }
        return false;
    }

    private Point[] moveBlock(int x, int y) {
        Point[] blockArray = this.dynamicStatistic.getCurrentBlockArray();
        boolean[][] gameMap = this.dynamicStatistic.getGameMap();
        if (!canMove(x, y, blockArray, gameMap)) {
            for (Point point : blockArray) {
                point.x += x;
                point.y += y;
            }
        }
        return blockArray;
    }

    /**
     * remove lines
     */
    private boolean isCanRemove(int row, boolean[][] gameMap) {
        for (boolean point : gameMap[row]) {
            if (!point) {
                return false;
            }
        }
        return true;
    }

    private int removeLines() {
        boolean[][] gameMap = this.dynamicStatistic.getGameMap();
        int removedLines = 0;
        for (int row = 0; row < gameMap.length; row++) {
            if (isCanRemove(row, gameMap)) {
                removedLines++;
                // from the top 'removed line', move the top line to next line
                for (int removedRow = row; removedRow > 0; removedRow--) {
                    System.arraycopy(gameMap[removedRow - 1], 0, gameMap[removedRow], 0, gameMap[row].length);
                }
                // fill the first row to all false
                for (int i = 0; i < StaticConfig.GAME_MAP_COLUMNS; i++) {
                    gameMap[0][i] = false;
                }
            }
        }
        return removedLines;
    }

    /**
     * can rotate
     */
    private static boolean canRotate(int blockType, Point blockArray[], boolean[][] gameMap) {
        // i=0 is the center point, do not rotate
        for (int i = 1; i < blockArray.length; i++) {
            Point currentPoint = blockArray[i];
            Point centerPoint = blockArray[0];
            int newX = centerPoint.y + centerPoint.x - currentPoint.y;
            int newY = centerPoint.y - centerPoint.x + currentPoint.x;
            if (newX > StaticConfig.GAME_MAP_X_MAX
                    || newX < StaticConfig.GAME_MAP_X_MIN
                    || newY > StaticConfig.GAME_MAP_Y_MAX
                    || newY < StaticConfig.GAME_MAP_Y_MIN
                    || gameMap[newY][newX]
                    || blockType == StaticConfig.DO_NOT_ROTATE_BLOCK_TYPE) {
                return false;
            }
        }
        return true;
    }

    /**
     * clockwise
     * B.x = o.y + o.x - A.y
     * B.y = o.y - o.x - A.x
     */
    private Point[] rotateBlockArray() {
        int blockType = this.dynamicStatistic.getCurrentBlockType();
        Point blockArray[] = this.dynamicStatistic.getCurrentBlockArray();
        boolean[][] gameMap = this.dynamicStatistic.getGameMap();
        if (canRotate(blockType, blockArray, gameMap)) {
            // i=0 is the center point, do not rotate
            for (int i = 1; i < blockArray.length; i++) {
                Point currentPoint = blockArray[i];
                Point centerPoint = blockArray[0];
                int newX = centerPoint.y + centerPoint.x - currentPoint.y;
                int newY = centerPoint.y - centerPoint.x + currentPoint.x;
                blockArray[i] = new Point(newX, newY);
            }
        }
        return blockArray;
    }

    /**
     * update game map
     */
    private void updateGameMap() {
        boolean[][] currentGameMap = this.dynamicStatistic.getGameMap();
        for (Point point : this.dynamicStatistic.getCurrentBlockArray()) {
            currentGameMap[point.y][point.x] = true;
        }
    }

    /**
     * move block left
     * accessed by reflection
     */
    void moveBlockLeft() {
        this.dynamicStatistic.setCurrentBlockArray(this.moveBlock(-1, 0));
    }

    /**
     * move block right
     * accessed by reflection
     */
    void moveBlockRight() {
        this.dynamicStatistic.setCurrentBlockArray(this.moveBlock(1, 0));
    }

    /**
     * move block down
     */
    private void moveBlockDown() {
        this.dynamicStatistic.setCurrentBlockArray(this.moveBlock(0, 1));
    }

    /**
     * rotate block
     * accessed by reflection
     */
    void rotateBlock() {
        this.dynamicStatistic.setCurrentBlockArray(this.rotateBlockArray());
    }

    /**
     * update local record if new record created
     */
    private void updateLocalRecord() {
        int currentScore = this.dynamicStatistic.getCurrentScore();
        if (currentScore > this.dynamicStatistic.getLocalHighestScore()) {
            // write to dynamicStatistic
            this.dynamicStatistic.setLocalHighestScore(currentScore);
            // write to local file via "GetSetLocalRecord"
            // we should initial a "LocalRecordDataStructure" first to write
            LocalRecordDataStructure localRecordDataStructure = new LocalRecordDataStructure();
            localRecordDataStructure.setHighestScore(currentScore);
            try {
                this.getSetLocalRecord.updateRecord(localRecordDataStructure);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * update world record if new record created
     */
    private void updateWorldRecord() {
        int currentScore = this.dynamicStatistic.getCurrentScore();
        int currentWorldHighestScore = this.dynamicStatistic.getWorldHighestScore();
        if (currentScore > currentWorldHighestScore && ConnectionInfo.IS_DATABASE_CONNECTION) {
            // write to mongodb via "GetSetWorldRecord"
            this.getSetWorldRecord.updateDocument(currentWorldHighestScore, currentScore);
            // write to dynamicStatistic
            this.dynamicStatistic.setWorldHighestScore(currentScore);
        }
    }

    /**
     * generate current block
     * and judge is game over here
     */
    void setCurrentBlock(int blockType) {
        /* generate a new random block base on a new block type */
        // set new block type
        this.dynamicStatistic.setCurrentBlockType(blockType);
        /**
         * set current block array
         * generate new  BlockPointsArray every time
         * not clone the points array
         * */
        BlockPointsArray blockPointsArray = new BlockPointsArray();
        Point[] newCurrentBlockArray = blockPointsArray.BLOCK_TYPE_POINTS_ARRAY[this.dynamicStatistic.getCurrentBlockType()];
        // judge if game over
        boolean[][] currentGameMap = this.dynamicStatistic.getGameMap();
        for (Point point : newCurrentBlockArray) {
            if (currentGameMap[point.y][point.x]) {
                // set game over flag
                this.dynamicStatistic.setGameOVer(true);
                // update local record if new record created
                this.updateLocalRecord();
                // update world record if new record created
                this.updateWorldRecord();
                return;
            }
        }
        // if not game over then update block points array
        this.dynamicStatistic.setCurrentBlockArray(newCurrentBlockArray);
    }

    /**
     * generate next block type
     */
    void generateNextBlockType() {
        // set next block type
        this.dynamicStatistic.setNextBlockTYpe(this.getRandomBlocKType());
    }

    /**
     * get next block type
     */
    private int getNextBlockType() {
        return this.dynamicStatistic.getNextBlockTYpe();
    }

    int getRandomBlocKType() {
        return this.random.nextInt(StaticConfig.MAX_BLOCK_TYPE_NUMBER + 1);
    }

    // exit game
    void exitGame() {
        System.exit(WindowConstants.EXIT_ON_CLOSE);
    }

    // get is game pause flag
    public boolean isGamePause() {
        return this.dynamicStatistic.isGamePause();
    }

    // get is game over
    public boolean isGameOVer() {
        return this.dynamicStatistic.isGameOVer();
    }

    // set game over flag
    void setGameOver(boolean isGameOver) {
        this.dynamicStatistic.setGameOVer(isGameOver);
    }

    // play or pause game
    void setGamePauseOrPlay() {
        this.dynamicStatistic.setGamePause(!this.dynamicStatistic.isGamePause());
    }

    /**
     * update game statistic data like level score speed removedLines
     */
    // update current moved lines
    private void updateCurrentRemovedLines(int removedLines) {
        // update current moved lines
        this.dynamicStatistic.setCurrentRemovedLines(this.dynamicStatistic.getCurrentRemovedLines() + removedLines);

    }

    // update level
    private void updateLevel() {
        this.dynamicStatistic.setCurrentLevel(this.dynamicStatistic.getCurrentRemovedLines() / StaticConfig.LEVEL_UPDATE_REMOVED_LINES);
    }

    // update score
    private void updateScore(int removedLines) {
        int increasedScore = (int) (StaticConfig.ONE_LINE_SCORE * Math.pow(2, removedLines - 1));
        this.dynamicStatistic.setCurrentScore(this.dynamicStatistic.getCurrentScore() + increasedScore);
    }

    // get current game speed interval
    public int getGameCurrentSpeedInterval() {
        return this.dynamicStatistic.getGameCurrentSpeedInterval();
    }

    // update game speed
    private void updateGameSpeed() {
        int currentLevel = this.dynamicStatistic.getCurrentLevel();
        int newSpeedInterval = (int) (StaticConfig.INITIAL_GAME_SPEED_INTERVAL * Math.pow(StaticConfig.GAME_SPEED_FACTOR, currentLevel));
        if (newSpeedInterval < StaticConfig.MIN_GAME_SPEED_INTERVAL) {
            newSpeedInterval = StaticConfig.MIN_GAME_SPEED_INTERVAL;
        }
        this.dynamicStatistic.setGameCurrentSpeedInterval(newSpeedInterval);
    }

    private void updateGameStatistics(int removedLines) {
        // update current moved lines
        this.updateCurrentRemovedLines(removedLines);
        // update level
        this.updateLevel();
        // update game speed
        this.updateGameSpeed();
        // update score
        this.updateScore(removedLines);
    }

    /**
     * restart game
     * clear game map, level, score, removed lines
     */
    void restartGame() {
        // game map
        this.dynamicStatistic.setGameMap();
        // level
        this.dynamicStatistic.setCurrentLevel(0);
        // score
        this.dynamicStatistic.setCurrentScore(0);
        // removed lines
        this.dynamicStatistic.setCurrentRemovedLines(0);
    }

    // process block down event
    public void processBlockDownEvent() {
        /* if can't move down any more, then push the points into game map */
        if (!this.canMoveDownBlock()) {
            // update game map with new block
            this.updateGameMap();

            // pass next block type to current
            this.setCurrentBlock(this.getNextBlockType());
            // then generate next block
            this.generateNextBlockType();

            // remove lines if map has removed lines
            // return removed lines to calculate player's score and level
            int removedLines = this.removeLines();

            // update game statistic data like level score speed removedLines
            if (removedLines > 0) {
                this.updateGameStatistics(removedLines);
            }
        } else {
            // else move down normally
            this.moveBlockDown();
        }
        // for block down thread
        this.gamePanel.repaint();
    }
}

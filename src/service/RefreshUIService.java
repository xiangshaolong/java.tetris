package service;

import config.StaticConfig;
import config.DynamicStatistic;

import java.awt.*;

public class RefreshUIService {

    private DynamicStatistic dynamicStatistic;

    // constructor
    public RefreshUIService(DynamicStatistic dynamicStatistic) {
        this.dynamicStatistic = dynamicStatistic;
    }

    /**
     * draw panel back ground
     */
    // draw panel background method
    private void drawPanelBackground(Graphics g) {
        // background image
        g.drawImage(StaticConfig.getPanelBgImg(), 0, 0, null);
    }

    /**
     * draw one number with img
     */
    private void drawOneNumberWithImage(int x, int y, int number, Graphics g, Image img) {
        int width = img.getWidth(null) / 10;
        int height = img.getHeight(null);
        g.drawImage(img,
                x,
                y,
                x + width,
                y + height,
                width * number,
                0,
                width * (number + 1),
                height,
                null
        );
    }

    /**
     * draw multi-number
     * align right
     */
    private void drawMultiNumberWithImage(int x, int y, int number, Graphics g, Image img) {
        String strNumber = Integer.toString(number);
        int width = img.getWidth(null) / 10;
        for (int index = 0; index < strNumber.length(); index++) {
            this.drawOneNumberWithImage(
                    x - width * (strNumber.length() - index),
                    y,
                    Integer.parseInt(strNumber.substring(index, index + 1)),
                    g,
                    img);
        }
    }


    /**
     * refresh current block
     */
    private void displayOneBlockService(Graphics g) {
        if (!this.dynamicStatistic.isGameOVer()) {
            // generate random block type with random background
            int blockType = this.dynamicStatistic.getCurrentBlockType();
            Point[] currentBlockArray = this.dynamicStatistic.getCurrentBlockArray();
            // display block
            displayOneBlock(blockType, currentBlockArray, g);
        }
    }

    /**
     * pure function
     */
    private int[] getMinMaxX(Point[] pointsArray) {
        int[] minMaxArray = new int[2];
        int min = pointsArray[0].x;
        int max = pointsArray[0].x;
        for (Point aPointsArray : pointsArray) {
            if (aPointsArray.x > max) {
                max = aPointsArray.x;
            }
            if (aPointsArray.x < min) {
                min = aPointsArray.x;
            }
        }
        minMaxArray[0] = min;
        minMaxArray[1] = max;
        return minMaxArray;
    }

    private void displayOneBlock(int blockType, Point[] pointArray, Graphics g) {
        // draw a certain block
        int blockSize = StaticConfig.BLOCK_SIZE;
        int startX = StaticConfig.GAME_MAP_START_X;
        int startY = StaticConfig.GAME_MAP_START_Y;
        // draw block background
        int[] minMaxX = this.getMinMaxX(pointArray);
        int minX = minMaxX[0];
        int maxX = minMaxX[1];
        g.drawImage(StaticConfig.getBlockAssistantBgImg(),
                startX + blockSize * minX,
                startY,
                startX + blockSize * (maxX + 1),
                startY + StaticConfig.GAME_MAP_HEIGHT,
                0, 0, 32, 32, null);
        for (Point point : pointArray) {
            g.drawImage(StaticConfig.getBlockImg(),
                    startX + blockSize * point.x,
                    startY + blockSize * point.y,
                    startX + blockSize * point.x + blockSize,
                    startY + blockSize * point.y + blockSize,
                    blockSize * blockType,
                    0,
                    blockSize * (blockType + 1),
                    blockSize,
                    null);
        }
    }

    /**
     * refresh next block using image
     */
    private void displayNextBlock(Graphics g) {
        int nextBlockTYpe = this.dynamicStatistic.getNextBlockTYpe();
        g.drawImage(
                StaticConfig.getNextBlockImg(nextBlockTYpe),
                StaticConfig.NEXT_BLOCK_START_X,
                StaticConfig.NEXT_BLOCK_START_Y,
                null);

    }

    /**
     * refresh game map
     */
    private boolean isGameMapEmpty(boolean[][] gameMap) {
        for (boolean row[] : gameMap) {
            for (boolean column : row) {
                if (column) {
                    return false;
                }
            }
        }
        return true;
    }

    private void refreshGameMap(Graphics g) {
        boolean gameMap[][] = this.dynamicStatistic.getGameMap();
        if (!isGameMapEmpty(gameMap)) {
            for (int row = 0; row < gameMap.length; row++) { // 20 rows
                for (int column = 0; column < gameMap[row].length; column++) { // 12 columns each row
                    // draw map where point is true
                    if (gameMap[row][column]) {
                        g.drawImage(StaticConfig.getBlockImg(),
                                StaticConfig.GAME_MAP_START_X + column * StaticConfig.BLOCK_SIZE,
                                StaticConfig.GAME_MAP_START_Y + row * StaticConfig.BLOCK_SIZE,
                                StaticConfig.GAME_MAP_START_X + (column + 1) * StaticConfig.BLOCK_SIZE,
                                StaticConfig.GAME_MAP_START_Y + (row + 1) * StaticConfig.BLOCK_SIZE,
                                StaticConfig.HEAPED_BLOCK_BG_START_X,
                                0,
                                StaticConfig.HEAPED_BLOCK_BG_START_X + StaticConfig.BLOCK_SIZE,
                                StaticConfig.BLOCK_SIZE,
                                null
                        );
                    }
                }
            }
        }
    }

    /**
     * refresh level
     */
    private void refreshLevel(Graphics g) {
        int currentLevel = this.dynamicStatistic.getCurrentLevel();
        drawMultiNumberWithImage(
                StaticConfig.LEVEL_START_X,
                StaticConfig.LEVEL_START_Y,
                currentLevel,
                g,
                StaticConfig.getNumberImg());
    }

    /**
     * refresh removed lines
     */
    private void refreshRemovedLines(Graphics g) {
        int removedLines = this.dynamicStatistic.getCurrentRemovedLines();
        drawMultiNumberWithImage(
                StaticConfig.REMOVED_LINES_START_X,
                StaticConfig.REMOVED_LINES_START_Y,
                removedLines,
                g,
                StaticConfig.getNumberImg());
    }

    /**
     * refresh score
     */
    private void refreshScore(Graphics g) {
        int currentScore = this.dynamicStatistic.getCurrentScore();
        drawMultiNumberWithImage(
                StaticConfig.SCORE_START_X,
                StaticConfig.SCORE_START_Y,
                currentScore,
                g,
                StaticConfig.getNumberImg());
    }

    /**
     * refresh next level progress
     */
    private void refreshNextLevelProgress(Graphics g) {
        g.setColor(Color.YELLOW);
        g.drawRect(
                StaticConfig.NEXT_LEVEL_PROGRESS_RECT_START_X,
                StaticConfig.NEXT_LEVEL_PROGRESS_RECT_START_Y,
                StaticConfig.NEXT_LEVEL_PROGRESS_RECT_WIDTH,
                StaticConfig.NEXT_LEVEL_PROGRESS_RECT_HEIGHT);
        // draw percent
        int currentRemovedLines = this.dynamicStatistic.getCurrentRemovedLines();
        float percent = (currentRemovedLines % StaticConfig.LEVEL_UPDATE_REMOVED_LINES) / (float) StaticConfig.LEVEL_UPDATE_REMOVED_LINES;
        g.drawImage(
                StaticConfig.getLevelBloodImg(),
                StaticConfig.NEXT_LEVEL_PROGRESS_RECT_START_X + 1,
                StaticConfig.NEXT_LEVEL_PROGRESS_RECT_START_Y + 1,
                (int) (StaticConfig.NEXT_LEVEL_PROGRESS_RECT_START_X + 1 + StaticConfig.NEXT_LEVEL_PROGRESS_RECT_WIDTH * percent),
                StaticConfig.NEXT_LEVEL_PROGRESS_RECT_START_Y + 1 + StaticConfig.NEXT_LEVEL_PROGRESS_RECT_HEIGHT,
                0,
                0,
                (int) (StaticConfig.NEXT_LEVEL_PROGRESS_RECT_WIDTH * percent),
                StaticConfig.NEXT_LEVEL_PROGRESS_RECT_HEIGHT,
                null
        );
    }

    /**
     * display pause or play tip
     */
    private void displayPauseTip(Graphics g) {
        if (this.dynamicStatistic.isGamePause()) {
            g.drawImage(StaticConfig.getPauseImg(), 0, 0, null);
        }
    }

    /**
     * display game over tip
     */
    private void displayGameOVerTip(Graphics g) {
        if (this.dynamicStatistic.isGameOVer()) {
            g.drawImage(StaticConfig.getGameOverImg(), 0, 0, null);
        }
    }

    /**
     * Q 键退出
     * 空格开始
     */
    private void displayQuitAndStart(Graphics g) {
        g.setColor(Color.ORANGE);
        g.setFont(new Font("Verdana", Font.ITALIC, 24));
        g.drawString("Q键退出", StaticConfig.GAME_QUIT_TIP_X, StaticConfig.GAME_QUIT_TIP_Y);
        g.drawString("空格开始", StaticConfig.GAME_START_TIP_X, StaticConfig.GAME_START_TIP_Y);
    }

    /**
     * display local record
     */
    private void displayLocalRecord(Graphics g) {
        // get highest score
        int highestScore = this.dynamicStatistic.getLocalHighestScore();
        // draw score
        this.drawMultiNumberWithImage(
                StaticConfig.LOCAL_RECORD_START_VALUE_X,
                StaticConfig.LOCAL_RECORD_START_VALUE_Y,
                highestScore,
                g,
                StaticConfig.getLargeNumberImg()
        );
    }

    /**
     * display world record
     */
    private void displayWorldRecord(Graphics g) {
        // get highest score
        int highestScore = this.dynamicStatistic.getWorldHighestScore();
        // draw score
        this.drawMultiNumberWithImage(
                StaticConfig.WORLD_RECORD_START_VALUE_X,
                StaticConfig.WORLD_RECORD_START_VALUE_Y,
                highestScore,
                g,
                StaticConfig.getLargeNumberImg()
        );
    }

    /**
     * refresh UI
     */
    public void refreshUI(Graphics g) {
        // draw panel background
        this.drawPanelBackground(g);
        // refresh level
        this.refreshLevel(g);
        // refresh removed lines
        this.refreshRemovedLines(g);
        // refresh score
        this.refreshScore(g);
        // refresh game map
        this.refreshGameMap(g);
        // display one block
        this.displayOneBlockService(g);
        // display next block
        this.displayNextBlock(g);
        // display next level progress
        this.refreshNextLevelProgress(g);
        // display pause tip
        this.displayPauseTip(g);
        // display game over tip
        this.displayGameOVerTip(g);
        // quit and start tip
        this.displayQuitAndStart(g);
        // display local record
        this.displayLocalRecord(g);
        // display world record
        this.displayWorldRecord(g);
        /**
         * repaint panel
         * we should not repaint here
         * otherwise will cause high cpu occupation
         * */
        // gamePanel.repaint();
    }
}

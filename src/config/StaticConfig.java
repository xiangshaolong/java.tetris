package config;

import javax.swing.*;
import java.awt.*;

public class StaticConfig {
    /**
     * frame
     */
    // frame size
    public static final int WINDOW_WIDTH = 1366;
    public static final int WINDOW_HEIGHT = 768;
    public static final int WINDOW_Y_OFFSET = -132;

    // frame title
    public static final String FRAME_TITLE = "java tetris demo";


    /**
     * panel
     */
    // panel background
    private static final String BG_IMAGE_PATH = "./images/background/bg101.png";

    public static Image getPanelBgImg() {
        return new ImageIcon(BG_IMAGE_PATH).getImage();
    }

    /**
     * block
     */
    // block image
    private static final String BLOCK_IMAGE_PATH = "./images/block/blockArray32.png";

    public static Image getBlockImg() {
        return new ImageIcon(BLOCK_IMAGE_PATH).getImage();
    }

    // block assistant background image
    private static final String BLOCK_ASSISTANT_BG_IMAGE_PATH = "./images/block/blockAssistantBg.png";

    public static Image getBlockAssistantBgImg() {
        return new ImageIcon(BLOCK_ASSISTANT_BG_IMAGE_PATH).getImage();
    }

    // block type number
    public static final int MIN_BLOCK_TYPE_NUMBER = 0;
    public static final int MAX_BLOCK_TYPE_NUMBER = 6;

    public static final int BLOCK_SIZE = 32;

    public static final int DO_NOT_ROTATE_BLOCK_TYPE = 3;

    private static final String[] NEXT_BLOCK_IMAGE_PATH = {
            "./images/block/next/0.png",
            "./images/block/next/1.png",
            "./images/block/next/2.png",
            "./images/block/next/3.png",
            "./images/block/next/4.png",
            "./images/block/next/5.png",
            "./images/block/next/6.png",
    };

    public static Image getNextBlockImg(int blockType) {
        return new ImageIcon(NEXT_BLOCK_IMAGE_PATH[blockType]).getImage();
    }

    public static final int NEXT_BLOCK_START_X = 1090;
    public static final int NEXT_BLOCK_START_Y = 280;

    private static final String LEVEL_BLOOD_IMG_PATH = "./images/level/levelBlood.png";

    public static Image getLevelBloodImg() {
        return new ImageIcon(LEVEL_BLOOD_IMG_PATH).getImage();
    }


    /**
     * statistic data display position
     */
    public static final int NEXT_LEVEL_PROGRESS_RECT_START_X = 1014;
    public static final int NEXT_LEVEL_PROGRESS_RECT_START_Y = 528;
    public static final int NEXT_LEVEL_PROGRESS_RECT_WIDTH = 270;
    public static final int NEXT_LEVEL_PROGRESS_RECT_HEIGHT = 40;

    public static final int LEVEL_START_X = 1050;
    public static final int LEVEL_START_Y = 120;

    public static final int REMOVED_LINES_START_X = 1260;
    public static final int REMOVED_LINES_START_Y = 484;

    public static final int SCORE_START_X = 1260;
    public static final int SCORE_START_Y = 444;

    /**
     * number image
     */
    private static final String NUMBER_IMG_PATH = "./images/number/numberArray32.png";

    public static Image getNumberImg() {
        return new ImageIcon(NUMBER_IMG_PATH).getImage();
    }

    /**
     * large number image
     */
    private static final String LARGE_NUMBER_IMG_PATH = "./images/number/numberArray48.png";

    public static Image getLargeNumberImg() {
        return new ImageIcon(LARGE_NUMBER_IMG_PATH).getImage();
    }

    /**
     * game map
     */
    public static final int HEAPED_BLOCK_BG_START_X = BLOCK_SIZE * 7;

    public static final int GAME_MAP_START_X = 448;
    public static final int GAME_MAP_START_Y = 48;

    public static final int GAME_MAP_X_MIN = 0;
    public static final int GAME_MAP_X_MAX = 11;

    public static final int GAME_MAP_Y_MIN = 0;
    public static final int GAME_MAP_Y_MAX = 19;

    static final int GAME_MAP_ROWS = 20; // default access privilege for same package
    public static final int GAME_MAP_COLUMNS = 12;

    public static final int GAME_MAP_WIDTH = 384;
    public static final int GAME_MAP_HEIGHT = 640;

    /**
     * game rule data
     */
    // level update with removed lines
    public static final int LEVEL_UPDATE_REMOVED_LINES = 6;
    // one line score
    public static final int ONE_LINE_SCORE = 10;

    // initial game speed interval
    public static final int INITIAL_GAME_SPEED_INTERVAL = 400;
    // define minimal game speed interval
    public static final int MIN_GAME_SPEED_INTERVAL = 50;
    // game speed factor
    public static final float GAME_SPEED_FACTOR = (float) 0.8;

    /**
     * local record display position parameters
     */
    public static final int LOCAL_RECORD_START_VALUE_X = 270;
    public static final int LOCAL_RECORD_START_VALUE_Y = 580;

    /**
     * world record display position parameters
     */
    public static final int WORLD_RECORD_START_VALUE_X = 270;
    public static final int WORLD_RECORD_START_VALUE_Y = 180;


    /**
     * game tip
     */
    // game tip position
    public static final int GAME_QUIT_TIP_X = 1226;
    public static final int GAME_QUIT_TIP_Y = 100;

    public static final int GAME_START_TIP_X = 1226;
    public static final int GAME_START_TIP_Y = 150;

    // game pause image
    private static final String PAUSE_IMG_PATH = "./images/game/pause.png";

    public static Image getPauseImg() {
        return new ImageIcon(PAUSE_IMG_PATH).getImage();
    }

    // game over image
    private static final String GAME_OVER_IMG_PATH = "./images/game/gameover.png";

    public static Image getGameOverImg() {
        return new ImageIcon(GAME_OVER_IMG_PATH).getImage();
    }

    // query game status interval(ms)
    public static final int QUERY_GAME_STATUS_INTERVAL = 500;

}

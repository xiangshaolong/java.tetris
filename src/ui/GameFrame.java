package ui;

import config.StaticConfig;
import util.UtilFunction;

import javax.swing.*;

public class GameFrame extends JFrame {

    // constructor
    public GameFrame() {
        // basic config
        setResizable(false);
        setTitle(StaticConfig.FRAME_TITLE);
        setSize(StaticConfig.WINDOW_WIDTH, StaticConfig.WINDOW_HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        UtilFunction.setGameFrameWindowInScreenCenter(this,
                StaticConfig.WINDOW_WIDTH,
                StaticConfig.WINDOW_HEIGHT,
                StaticConfig.WINDOW_Y_OFFSET);
        // install game panel
        setContentPane(new GamePanel());
        // we should set visible last
        setVisible(true);
    }


}

package controller;

import thread.BlockDownThread;
import ui.GamePanel;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

public class GameController extends KeyAdapter {
    private GameFunction gameFunction;
    private GamePanel gamePanel;
    // we use synchronize map here
    private Hashtable<Integer, String> methodTable = new Hashtable<>();

    // constructor
    public GameController(GameFunction gameFunction, GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.gameFunction = gameFunction;
        // initial current block
        this.gameFunction.setCurrentBlock(this.gameFunction.getRandomBlocKType());
        // initial next block
        this.gameFunction.generateNextBlockType();
        // start blockDownThread
        BlockDownThread blockDownThread = new BlockDownThread(gameFunction);
        blockDownThread.start();
        // initial hashTable
        this.methodTable.put(KeyEvent.VK_SPACE, "setGamePauseOrPlay");
        this.methodTable.put(KeyEvent.VK_LEFT, "moveBlockLeft");
        this.methodTable.put(KeyEvent.VK_DOWN, "processBlockDownEvent");
        this.methodTable.put(KeyEvent.VK_RIGHT, "moveBlockRight");
        this.methodTable.put(KeyEvent.VK_UP, "rotateBlock");
    }

    private String getMethodNameByKeyCode(int keyCode) {
        return this.methodTable.get(keyCode);
    }

    private boolean isValidKeyPress(int keyCode){
        return this.methodTable.containsKey(keyCode);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // if quit
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            this.gameFunction.exitGame();
        }
        /* normal state */
        boolean isGamePause = this.gameFunction.isGamePause();
        boolean isGameOver = this.gameFunction.isGameOVer();

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (isGameOver) { // restart game
                // clear game over flag
                this.gameFunction.setGameOver(false);
                // restart game to clear game map, score, level and removed lines
                this.gameFunction.restartGame();
            } else {
                // play or pause game
                this.gameFunction.setGamePauseOrPlay();
            }
        } else if (!isGamePause && !isGameOver && isValidKeyPress(e.getKeyCode())) {
            /**
             * use reflection here(for 装逼)
             * */
            try {
                this.gameFunction.getClass().getDeclaredMethod(getMethodNameByKeyCode(e.getKeyCode())).invoke(this.gameFunction);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e1) {
                e1.printStackTrace();
            }
            /**
             * use enum here
             * */
            /*
            switch (e.getKeyCode()) {
                case KeyEvent.VK_SPACE:
                    // play or pause game
                    this.gameFunction.setGamePauseOrPlay();
                    break;
                case KeyEvent.VK_LEFT:
                    // move block left
                    this.gameFunction.moveBlockLeft();
                    break;
                case KeyEvent.VK_DOWN:
                    this.gameFunction.processBlockDownEvent();
                    break;
                case KeyEvent.VK_RIGHT:
                    this.gameFunction.moveBlockRight();
                    break;
                case KeyEvent.VK_UP:
                    // rotate block
                    this.gameFunction.rotateBlock();
                    break;
                default:
                    break;
            }
            */
        }
        // repaint panel
        this.gamePanel.repaint();
    }
}

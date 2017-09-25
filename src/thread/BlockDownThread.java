package thread;

import config.StaticConfig;
import controller.GameFunction;

public class BlockDownThread extends Thread {
    private Thread thread;
    private GameFunction gameFunction;

    public BlockDownThread(GameFunction gameFunction) {
        this.gameFunction = gameFunction;
    }

    @Override
    public void run() {
        while (true) {
            // if game pause or game is over
            if (this.gameFunction.isGamePause() || this.gameFunction.isGameOVer()) {
                try {
                    /**
                     * query game status every 100ms
                     * we do not use wait/notify for simplify
                     * */
                    Thread.sleep(StaticConfig.QUERY_GAME_STATUS_INTERVAL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    this.gameFunction.processBlockDownEvent();
                    Thread.sleep(this.gameFunction.getGameCurrentSpeedInterval());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void start() {
        if (thread == null) {
            String threadName = "blockDownThread";
            thread = new Thread(this, threadName);
            thread.start(); // this will call this.run() method
        }
    }
}

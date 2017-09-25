package ui;

import config.DynamicStatistic;
import controller.GameController;
import database.GetSetWorldRecord;
import localFile.GetSetLocalRecord;
import service.RefreshUIService;
import controller.GameFunction;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private RefreshUIService refreshUIService;

    // constructor
    GamePanel() {
        // create a  dynamic statistic data instance
        DynamicStatistic dynamicStatistic = new DynamicStatistic();
        // initial local record
        GetSetLocalRecord getSetLocalRecord = new GetSetLocalRecord();
        // initial world record
        GetSetWorldRecord getSetWorldRecord = new GetSetWorldRecord();
        // install keyboard listener(game controller) on panel
        this.addKeyListener(
                new GameController(
                        new GameFunction(
                                dynamicStatistic,
                                getSetLocalRecord,
                                getSetWorldRecord,
                                this),
                        this));
        // install refresh ui service
        this.refreshUIService = new RefreshUIService(dynamicStatistic);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // call super class method
        super.paintComponent(g);
        // refresh ui
        this.refreshUIService.refreshUI(g);
        // request focus for keyboard listener
        requestFocus();
    }
}

package util;

import javax.swing.*;
import java.awt.*;

public class UtilFunction {
    public static void setGameFrameWindowInScreenCenter(JFrame jFrame, int width, int height, int offset) {
        // set window in the center of desktop's center
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int screenWidth = toolkit.getScreenSize().width;
        int screenHeight = toolkit.getScreenSize().height;
        jFrame.setLocation(
                (screenWidth - width) / 2,
                (screenHeight - height) / 2 + offset);
    }
}

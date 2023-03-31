package com.plugins.mybaitslog.gui.compone;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import com.intellij.openapi.util.SystemInfo;

public class MyColorButton extends JButton {

    private Color myColor = Color.WHITE;

    public MyColorButton() {
        setMargin(new Insets(0, 0, 0, 0));
        setFocusable(false);
        setDefaultCapable(false);
        setFocusable(false);
        if (SystemInfo.isMac) {
            putClientProperty("JButton.buttonType", "square");
        }
    }

    public MyColorButton( Color myColor) {
        super();
        this.myColor = myColor;
    }


    @Override
    public void paint(Graphics g) {
        final Color color = g.getColor();
        int width = getWidth();
        int height = getHeight();
        if (myColor != null) {
            g.setColor(myColor);
            g.fillRect(0, 0, width, height);
            g.setColor(color);
        }
    }

    public void setColor(Color myColor) {
        this.myColor = myColor;
    }


    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(12, 12);
    }
}
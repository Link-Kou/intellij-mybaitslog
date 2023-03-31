package com.plugins.mybaitslog.gui;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import com.plugins.mybaitslog.Config;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 过滤设置 窗口
 *
 * @author lk
 * @version 1.0
 * @date 2020/8/23 17:14
 */
public class FilterSetting extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField preparingTextField;
    private JCheckBox startupCheckBox;

    /**
     * 窗口初始化
     *
     * @param project 项目
     */
    public FilterSetting(Project project) {
        //设置标题
        this.setTitle("Filter Setting");
        this.preparingTextField.setText(Config.Idea.getParameters());
        int startup = PropertiesComponent.getInstance(project).getInt(Config.Idea.DB_STARTUP_KEY, 1);
        startupCheckBox.setSelected(startup == 1);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(e -> onOK(project));
        buttonCancel.addActionListener(e -> onCancel());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * 点击确认按钮处理
     *
     * @param project 项目
     */
    private void onOK(Project project) {
        String preparing = this.preparingTextField.getText();
        Config.Idea.setParameters(preparing, Config.Idea.PARAMETERS);
        Config.Idea.setStartup(startupCheckBox.isSelected() ? 1 : 0);
        this.setVisible(false);
    }

    private void onCancel() {
        this.setVisible(false);
    }
}

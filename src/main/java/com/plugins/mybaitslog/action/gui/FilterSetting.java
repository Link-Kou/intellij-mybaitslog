package com.plugins.mybaitslog.action.gui;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import com.plugins.mybaitslog.util.ConfigUtil;
import com.plugins.mybaitslog.util.StringConst;
import org.apache.commons.lang.StringUtils;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * filter setting
 *
 * @author ob
 */
public class FilterSetting extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea textArea;
    private JTextField preparingTextField;
    private JTextField parametersTextField;
    private JCheckBox startupCheckBox;

    public FilterSetting(Project project) {
        //设置标题
        this.setTitle("Filter Setting");
        //读取过滤字符
        String[] filters = PropertiesComponent.getInstance(project).getValues(StringConst.FILTER_KEY);
        if (filters != null && filters.length > 0) {
            this.textArea.setText(StringUtils.join(filters, "\n"));
        }
        this.preparingTextField.setText(ConfigUtil.getPreparing(project));
        this.parametersTextField.setText(ConfigUtil.getParameters(project));
        int startup = PropertiesComponent.getInstance(project).getInt(StringConst.STARTUP_KEY, 1);
        startupCheckBox.setSelected(startup == 1);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        buttonOK.addActionListener(e -> onOK(project));
        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK(Project project) {
        final PropertiesComponent component = PropertiesComponent.getInstance(project);
        //保存配置字符
        if (textArea != null && StringUtils.isNotBlank(textArea.getText())) {
            String[] filters = textArea.getText().split("\n");
            component.setValues(StringConst.FILTER_KEY, filters);
        } else {
            component.setValues(StringConst.FILTER_KEY, null);
        }
        String preparingText = StringConst.PREPARING;
        String parametersText = StringConst.PARAMETERS;
        if (StringUtils.isNotBlank(preparingTextField.getText())) {
            preparingText = preparingTextField.getText().trim();
            if (!StringUtils.endsWith(preparingText, ":")) {
                preparingText += ":";
            }
        }
        if (StringUtils.isNotBlank(parametersTextField.getText())) {
            parametersText = parametersTextField.getText().trim();
            if (!StringUtils.endsWith(parametersText, ":")) {
                parametersText += ":";
            }
        }
        ConfigUtil.setPreparing(project, preparingText);
        ConfigUtil.setParameters(project, parametersText);
        ConfigUtil.setStartup(project, startupCheckBox.isSelected() ? 1 : 0);
        this.setVisible(false);
    }

    private void onCancel() {
        this.setVisible(false);
    }
}

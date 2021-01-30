package com.hleofxquotes.tt.entertext;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

public class EnterTextMainFrame extends JFrame {

    private JTextField titleTextField;

    private JTextField textTextField;

    public EnterTextMainFrame(String title) throws HeadlessException {
        super(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//		setPreferredSize(new Dimension(600, 400));

        initView(getContentPane());
    }

    private void initView(Container contentPane) {
        JPanel view = new JPanel();
        view.setLayout(new BorderLayout());

        view.add(createTextFieldsView(), BorderLayout.CENTER);

        view.add(createActionsView(), BorderLayout.SOUTH);

        contentPane.add(view);

    }

    private JPanel createActionsView() {
        JPanel view = new JPanel();
        view.setLayout(new BoxLayout(view, BoxLayout.LINE_AXIS));
        view.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

        view.add(Box.createHorizontalGlue());

        JButton button = new JButton(new AbstractAction("Enter text") {

            @Override
            public void actionPerformed(ActionEvent e) {
                EnterTextMain enterTextMain = new EnterTextMain();

                String title = titleTextField.getText();
                String text = textTextField.getText();

                System.out.println("title=" + title);
                System.out.println("text=" + text);

                enterTextMain.enterText(title, text);
            }
        });
        view.add(button);

        view.add(Box.createHorizontalGlue());

        return view;
    }

    private JPanel createTextFieldsView() {
        String titleLabel = "Window Title: ";
        String textLabel = "Text: ";
        String[] labels = { titleLabel, textLabel };
        int numPairs = labels.length;

        // Create and populate the panel.
        JPanel view = new JPanel(new SpringLayout());
        for (int i = 0; i < numPairs; i++) {
            String label = labels[i];
            JLabel l = new JLabel(label, JLabel.TRAILING);
            view.add(l);
            JTextField textField = new JTextField(32);
            l.setLabelFor(textField);
            view.add(textField);

            if (label.compareToIgnoreCase(titleLabel) == 0) {
                textField.setText("turbotax");
                this.titleTextField = textField;
            } else if (label.compareToIgnoreCase(textLabel) == 0) {
                this.textTextField = textField;

            }
        }

        // Lay out the panel.
        SpringUtilities.makeCompactGrid(view, numPairs, 2, // rows, cols
                6, 6, // initX, initY
                6, 6); // xPad, yPad
        return view;
    }

    public static void main(String[] args) {
        final EnterTextMainFrame enterTextMainFrame = new EnterTextMainFrame("Keyboard simulator");
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                enterTextMainFrame.showMainFrame();
            }
        });
    }

    protected void showMainFrame() {
        pack();
        setLocation(100, 100);
        setVisible(true);
    }

}

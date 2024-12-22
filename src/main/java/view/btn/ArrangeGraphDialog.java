package view.btn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class ArrangeGraphDialog extends JDialog {
    private JRadioButton circularLayoutRadio;
    private JRadioButton topologicalLayoutRadio;
    private JRadioButton mstLayoutRadio;
    private JButton arrangeButton;
    private JButton cancelButton;
    private String selectedLayout;

    public ArrangeGraphDialog(JFrame parent) {
        super(parent, "Выберите тип укладки графа", true);
        setLayout(new BorderLayout());

        JPanel radioPanel = new JPanel(new GridLayout(3, 1));
        circularLayoutRadio = new JRadioButton("Круговая укладка");
        topologicalLayoutRadio = new JRadioButton("Топологическая укладка");
        mstLayoutRadio = new JRadioButton("Укладка по минимальному остовному дереву");
        ButtonGroup group = new ButtonGroup();
        group.add(circularLayoutRadio);
        group.add(topologicalLayoutRadio);
        group.add(mstLayoutRadio);
        circularLayoutRadio.setSelected(true);

        radioPanel.add(circularLayoutRadio);
        radioPanel.add(topologicalLayoutRadio);
        radioPanel.add(mstLayoutRadio);
        radioPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(radioPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        arrangeButton = new JButton("Укладка");
        cancelButton = new JButton("Отмена");
        buttonPanel.add(arrangeButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        arrangeButton.addActionListener(e -> {
            if (circularLayoutRadio.isSelected()) {
                selectedLayout = "CIRCULAR";
            } else if (topologicalLayoutRadio.isSelected()) {
                selectedLayout = "TOPOLOGICAL";
            } else if (mstLayoutRadio.isSelected()) {
                selectedLayout = "MST";
            }
            dispose();
        });

        cancelButton.addActionListener(e -> {
            selectedLayout = null;
            dispose();
        });

        pack();
        setLocationRelativeTo(parent);
    }

    public String getSelectedLayout() {
        return selectedLayout;
    }
}


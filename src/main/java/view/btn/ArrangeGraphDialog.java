package view.btn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Диалоговое окно для выбора типа укладки графа.
 */
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

        // Панель с радиокнопками
        JPanel radioPanel = new JPanel(new GridLayout(3, 1));
        circularLayoutRadio = new JRadioButton("Круговая укладка");
        topologicalLayoutRadio = new JRadioButton("Топологическая укладка");
        mstLayoutRadio = new JRadioButton("Укладка по минимальному остовному дереву");
        ButtonGroup group = new ButtonGroup();
        group.add(circularLayoutRadio);
        group.add(topologicalLayoutRadio);
        group.add(mstLayoutRadio);
        circularLayoutRadio.setSelected(true); // По умолчанию выбран круговой режим

        radioPanel.add(circularLayoutRadio);
        radioPanel.add(topologicalLayoutRadio);
        radioPanel.add(mstLayoutRadio);
        radioPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(radioPanel, BorderLayout.CENTER);

        // Панель кнопок
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        arrangeButton = new JButton("Укладка");
        cancelButton = new JButton("Отмена");
        buttonPanel.add(arrangeButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Обработчики кнопок
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

    /**
     * Возвращает выбранный тип укладки или null, если операция отменена.
     *
     * @return Тип укладки ("CIRCULAR", "TOPOLOGICAL", "MST") или null.
     */
    public String getSelectedLayout() {
        return selectedLayout;
    }
}


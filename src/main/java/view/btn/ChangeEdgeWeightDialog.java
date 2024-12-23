package view.btn;


import model.Edge;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class ChangeEdgeWeightDialog extends JDialog {
    private JComboBox<Edge> edgeComboBox;
    private JTextField weightField;
    private JButton changeButton;
    private JButton cancelButton;
    private Edge selectedEdge;
    private double newWeight;

    public ChangeEdgeWeightDialog(JFrame parent, List<Edge> edges) {
        super(parent, "Изменить вес ребра", true);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Выберите ребро:"));
        edgeComboBox = new JComboBox<>(edges.toArray(new Edge[0]));
        inputPanel.add(edgeComboBox);

        inputPanel.add(new JLabel("Новый вес:"));
        weightField = new JTextField();
        inputPanel.add(weightField);

        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        changeButton = new JButton("Изменить");
        cancelButton = new JButton("Отмена");
        buttonPanel.add(changeButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        changeButton.addActionListener(e -> {
            selectedEdge = (Edge) edgeComboBox.getSelectedItem();
            String weightStr = weightField.getText().trim();
            if (selectedEdge == null) {
                JOptionPane.showMessageDialog(this, "Выберите ребро для изменения.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (weightStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Вес не может быть пустым.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                newWeight = Double.parseDouble(weightStr);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Введите корректное числовое значение веса.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> {
            selectedEdge = null;
            dispose();
        });

        pack();
        setLocationRelativeTo(parent);
    }

    public Edge getSelectedEdge() {
        return selectedEdge;
    }

    public double getNewWeight() {
        return newWeight;
    }
}

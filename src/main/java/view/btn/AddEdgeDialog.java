package view.btn;

import model.Edge;
import model.Vertex;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class AddEdgeDialog extends JDialog {
    private JComboBox<Vertex> fromComboBox;
    private JComboBox<Vertex> toComboBox;
    private JTextField weightField;
    private JButton addButton;
    private JButton cancelButton;
    private Edge edge;

    public AddEdgeDialog(JFrame parent, List<Vertex> vertices) {
        super(parent, "Добавить ребро", true);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Исходная вершина:"));
        fromComboBox = new JComboBox<>(vertices.toArray(new Vertex[0]));
        inputPanel.add(fromComboBox);

        inputPanel.add(new JLabel("Целевая вершина:"));
        toComboBox = new JComboBox<>(vertices.toArray(new Vertex[0]));
        inputPanel.add(toComboBox);

        inputPanel.add(new JLabel("Вес ребра:"));
        weightField = new JTextField();
        inputPanel.add(weightField);

        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Добавить");
        cancelButton = new JButton("Отмена");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            Vertex from = (Vertex) fromComboBox.getSelectedItem();
            Vertex to = (Vertex) toComboBox.getSelectedItem();
            String weightStr = weightField.getText().trim();
            double weight = 0.0;
            if (!weightStr.isEmpty()) {
                try {
                    weight = Double.parseDouble(weightStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Неверный формат веса. Установлено значение 0.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    weight = 0.0;
                }
            }

            if (from.equals(to)) {
                JOptionPane.showMessageDialog(this, "Исходная и целевая вершины не могут совпадать.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            edge = new Edge(from, to, weight);
            dispose();
        });

        cancelButton.addActionListener(e -> {
            edge = null;
            dispose();
        });

        pack();
        setLocationRelativeTo(parent);
    }

    public Edge getEdge() {
        return edge;
    }
}

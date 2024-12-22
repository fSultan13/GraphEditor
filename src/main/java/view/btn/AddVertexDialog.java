package view.btn;

import model.Vertex;

import javax.swing.*;
import java.awt.*;


public class AddVertexDialog extends JDialog {
    private JTextField labelField;
    private JButton addButton;
    private JButton cancelButton;
    private Vertex vertex;

    public AddVertexDialog(JFrame parent) {
        super(parent, "Добавить вершину", true);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Метка вершины:"));
        labelField = new JTextField(15);
        inputPanel.add(labelField);
        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        addButton = new JButton("Добавить");
        cancelButton = new JButton("Отмена");
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String label = labelField.getText().trim();
            if (!label.isEmpty()) {
                vertex = new Vertex(label);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Метка вершины не может быть пустой.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> {
            vertex = null;
            dispose();
        });

        pack();
        setLocationRelativeTo(parent);
    }

    public Vertex getVertex() {
        return vertex;
    }
}

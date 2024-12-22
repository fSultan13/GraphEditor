package view.btn;

import model.Vertex;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class SelectStartVertexDialog extends JDialog {
    private JComboBox<Vertex> vertexComboBox;
    private JButton selectButton;
    private JButton cancelButton;
    private Vertex selectedVertex;

    public SelectStartVertexDialog(JFrame parent, List<Vertex> vertices, String title) {
        super(parent, title, true);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Начальная вершина:"));
        vertexComboBox = new JComboBox<>(vertices.toArray(new Vertex[0]));
        inputPanel.add(vertexComboBox);
        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        selectButton = new JButton("Выбрать");
        cancelButton = new JButton("Отмена");
        buttonPanel.add(selectButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        selectButton.addActionListener(e -> {
            selectedVertex = (Vertex) vertexComboBox.getSelectedItem();
            dispose();
        });

        cancelButton.addActionListener(e -> {
            selectedVertex = null;
            dispose();
        });

        pack();
        setLocationRelativeTo(parent);
    }

    public Vertex getSelectedVertex() {
        return selectedVertex;
    }
}

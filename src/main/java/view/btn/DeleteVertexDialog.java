package view.btn;


import model.Vertex;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Диалоговое окно для удаления вершины.
 */
public class DeleteVertexDialog extends JDialog {
    private JComboBox<Vertex> vertexComboBox;
    private JButton deleteButton;
    private JButton cancelButton;
    private Vertex selectedVertex;

    public DeleteVertexDialog(JFrame parent, List<Vertex> vertices) {
        super(parent, "Удалить вершину", true);
        setLayout(new BorderLayout());

        // Панель выбора вершины
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Выберите вершину для удаления:"));
        vertexComboBox = new JComboBox<>(vertices.toArray(new Vertex[0]));
        inputPanel.add(vertexComboBox);
        add(inputPanel, BorderLayout.CENTER);

        // Панель кнопок
        JPanel buttonPanel = new JPanel(new FlowLayout());
        deleteButton = new JButton("Удалить");
        cancelButton = new JButton("Отмена");
        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Обработчики кнопок
        deleteButton.addActionListener(e -> {
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

    /**
     * Возвращает выбранную вершину для удаления или null, если операция отменена.
     *
     * @return Вершина или null.
     */
    public Vertex getSelectedVertex() {
        return selectedVertex;
    }
}

package view.btn;


import model.Edge;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Диалоговое окно для удаления ребра.
 */
public class DeleteEdgeDialog extends JDialog {
    private JComboBox<Edge> edgeComboBox;
    private JButton deleteButton;
    private JButton cancelButton;
    private Edge selectedEdge;

    public DeleteEdgeDialog(JFrame parent, List<Edge> edges) {
        super(parent, "Удалить ребро", true);
        setLayout(new BorderLayout());

        // Панель выбора ребра
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Выберите ребро для удаления:"));
        edgeComboBox = new JComboBox<>(edges.toArray(new Edge[0]));
        inputPanel.add(edgeComboBox);
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
            selectedEdge = (Edge) edgeComboBox.getSelectedItem();
            dispose();
        });

        cancelButton.addActionListener(e -> {
            selectedEdge = null;
            dispose();
        });

        pack();
        setLocationRelativeTo(parent);
    }

    /**
     * Возвращает выбранное ребро для удаления или null, если операция отменена.
     *
     * @return Ребро или null.
     */
    public Edge getSelectedEdge() {
        return selectedEdge;
    }
}


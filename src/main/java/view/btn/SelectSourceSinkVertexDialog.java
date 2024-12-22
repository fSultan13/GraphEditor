package view.btn;

import model.Vertex;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Диалоговое окно для выбора исходной и конечной вершин для алгоритма Эдмондса-Карпа.
 */
public class SelectSourceSinkVertexDialog extends JDialog {
    private JComboBox<Vertex> sourceComboBox;
    private JComboBox<Vertex> sinkComboBox;
    private JButton selectButton;
    private JButton cancelButton;
    private Vertex selectedSource;
    private Vertex selectedSink;

    public SelectSourceSinkVertexDialog(JFrame parent, List<Vertex> vertices) {
        super(parent, "Выберите исходную и конечную вершины для Edmonds-Karp", true);
        setLayout(new BorderLayout());

        // Панель выбора вершин
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Исходная вершина (Source):"));
        sourceComboBox = new JComboBox<>(vertices.toArray(new Vertex[0]));
        inputPanel.add(sourceComboBox);

        inputPanel.add(new JLabel("Конечная вершина (Sink):"));
        sinkComboBox = new JComboBox<>(vertices.toArray(new Vertex[0]));
        inputPanel.add(sinkComboBox);

        add(inputPanel, BorderLayout.CENTER);

        // Панель кнопок
        JPanel buttonPanel = new JPanel(new FlowLayout());
        selectButton = new JButton("Выбрать");
        cancelButton = new JButton("Отмена");
        buttonPanel.add(selectButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Обработчики кнопок
        selectButton.addActionListener(e -> {
            selectedSource = (Vertex) sourceComboBox.getSelectedItem();
            selectedSink = (Vertex) sinkComboBox.getSelectedItem();
            if (selectedSource.equals(selectedSink)) {
                JOptionPane.showMessageDialog(this, "Исходная и конечная вершины не могут быть одинаковыми.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }
            dispose();
        });

        cancelButton.addActionListener(e -> {
            selectedSource = null;
            selectedSink = null;
            dispose();
        });

        pack();
        setLocationRelativeTo(parent);
    }

    /**
     * Возвращает выбранную исходную вершину или null, если операция отменена.
     *
     * @return Исходная вершина или null.
     */
    public Vertex getSelectedSource() {
        return selectedSource;
    }

    /**
     * Возвращает выбранную конечную вершину или null, если операция отменена.
     *
     * @return Конечная вершина или null.
     */
    public Vertex getSelectedSink() {
        return selectedSink;
    }
}

package view.btn;

import model.Vertex;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class TopologicalSortDialog extends JDialog {
    private JTextArea sortOrderArea;
    private JButton closeButton;

    public TopologicalSortDialog(JFrame parent, List<Vertex> sortedList) {
        super(parent, "Результат Топологической сортировки", true);
        setLayout(new BorderLayout());

        sortOrderArea = new JTextArea();
        sortOrderArea.setEditable(false);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sortedList.size(); i++) {
            sb.append((i + 1)).append(". ").append(sortedList.get(i).getLabel()).append("\n");
        }
        sortOrderArea.setText(sb.toString());
        add(new JScrollPane(sortOrderArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closeButton = new JButton("Закрыть");
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        closeButton.addActionListener(e -> dispose());

        setSize(300, 400);
        setLocationRelativeTo(parent);
    }
}

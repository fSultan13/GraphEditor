package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import io.GraphReader;
import model.Graph;


public class StartPanel extends JPanel {
    private JButton newGraphButton;
    private JButton loadGraphButton;
    private MainFrame mainFrame;

    public StartPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        newGraphButton = new JButton("Новый граф");
        newGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"Ориентированный", "Неориентированный"};
                int choice = JOptionPane.showOptionDialog(
                        StartPanel.this,
                        "Выберите тип графа:",
                        "Новый граф",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[0]
                );

                if (choice == 0) {
                    mainFrame.showGraphEditPanel(new model.graphs.DirectedGraph());
                } else if (choice == 1) {
                    mainFrame.showGraphEditPanel(new model.graphs.UndirectedGraph());
                }
            }
        });

        loadGraphButton = new JButton("Загрузить граф");
        loadGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(StartPanel.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    java.io.File file = fileChooser.getSelectedFile();
                    try {
                        GraphReader reader = new GraphReader();
                        Graph g = reader.loadGraph(file.getAbsolutePath());
                        mainFrame.showGraphEditPanel(g);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(StartPanel.this,
                                "Ошибка при загрузке графа: " + ex.getMessage(),
                                "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        add(newGraphButton, gbc);
        gbc.gridy++;
        add(loadGraphButton, gbc);
    }
}

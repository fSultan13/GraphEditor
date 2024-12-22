package view;

import javax.swing.*;
import java.awt.*;

import model.Graph;

public class MainFrame extends JFrame {
    private StartPanel startPanel;
    private GraphEditPanel graphEditPanel;

    public MainFrame() {
        setTitle("Графический редактор");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 800);
        setLocationRelativeTo(null);

        startPanel = new StartPanel(this);
        setContentPane(startPanel);
    }

    public void showStartPanel() {
        setContentPane(startPanel);
        revalidate();
        repaint();
    }

    public void showGraphEditPanel(Graph graph) {
        if (graphEditPanel == null) {
            graphEditPanel = new GraphEditPanel();
        }
        graphEditPanel.setGraph(graph);

        JScrollPane scrollPane = new JScrollPane(graphEditPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        setContentPane(scrollPane);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}

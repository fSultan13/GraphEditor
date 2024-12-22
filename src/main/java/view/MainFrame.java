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
        setSize(1000, 800); // Увеличим начальный размер окна для лучшего отображения
        setLocationRelativeTo(null);

        startPanel = new StartPanel(this);
        setContentPane(startPanel);
    }

    /**
     * Показывает стартовую панель.
     */
    public void showStartPanel() {
        setContentPane(startPanel);
        revalidate();
        repaint();
    }

    /**
     * Показывает панель редактирования графа.
     *
     * @param graph Граф для редактирования.
     */
    public void showGraphEditPanel(Graph graph) {
        if (graphEditPanel == null) {
            graphEditPanel = new GraphEditPanel();
        }
        graphEditPanel.setGraph(graph);

        // Обернём GraphEditPanel в JScrollPane
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

package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Панель инструментов с кнопками для управления графом.
 */
public class ToolPanel extends JPanel {
    private JButton backButton; // Кнопка "Назад"
    private JButton addVertexButton;
    private JButton addEdgeButton;
    private JButton deleteVertexButton;
    private JButton deleteEdgeButton;
    private JButton changeEdgeWeightButton; // Кнопка "Изменить вес ребра"
    private JButton saveGraphButton; // Кнопка "Сохранить граф"
    private JButton runDFSButton; // Кнопка "Запустить DFS"
    private JButton runBFSButton; // Кнопка "Запустить BFS"
    private JButton runDijkstraButton; // Кнопка "Запустить Dijkstra"
    private JButton runEdmondsKarpButton; // Кнопка "Запустить Edmonds-Karp"
    private JButton runTopologicalSortButton; // Кнопка "Запустить Топологическую сортировку"
    private JButton arrangeGraphButton; // Новая кнопка "Укладка графа"

    public ToolPanel() {
        // Используем WrapLayout вместо FlowLayout
        setLayout(new WrapLayout(FlowLayout.LEFT, 5, 5));
        setBackground(Color.LIGHT_GRAY);

        // Инициализация кнопок
        backButton = new JButton("Назад");
        addVertexButton = new JButton("Добавить вершину");
        addEdgeButton = new JButton("Добавить ребро");
        deleteVertexButton = new JButton("Удалить вершину");
        deleteEdgeButton = new JButton("Удалить ребро");
        changeEdgeWeightButton = new JButton("Изменить вес ребра");
        saveGraphButton = new JButton("Сохранить граф");
        runDFSButton = new JButton("Запустить DFS");
        runBFSButton = new JButton("Запустить BFS");
        runDijkstraButton = new JButton("Запустить Dijkstra");
        runEdmondsKarpButton = new JButton("Запустить Edmonds-Karp");
        runTopologicalSortButton = new JButton("Запустить Топологическую сортировку");
        arrangeGraphButton = new JButton("Укладка графа"); // Инициализация новой кнопки

        // Добавление кнопок на панель
        add(backButton);
        add(addVertexButton);
        add(addEdgeButton);
        add(deleteVertexButton);
        add(deleteEdgeButton);
        add(changeEdgeWeightButton);
        add(saveGraphButton);
        add(runDFSButton);
        add(runBFSButton);
        add(runDijkstraButton);
        add(runEdmondsKarpButton);
        add(runTopologicalSortButton);
        add(arrangeGraphButton); // Добавление новой кнопки на панель
    }

    // Методы для установки обработчиков событий для каждой кнопки

    public void setBackAction(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void setAddVertexAction(ActionListener listener) {
        addVertexButton.addActionListener(listener);
    }

    public void setAddEdgeAction(ActionListener listener) {
        addEdgeButton.addActionListener(listener);
    }

    public void setDeleteVertexAction(ActionListener listener) {
        deleteVertexButton.addActionListener(listener);
    }

    public void setDeleteEdgeAction(ActionListener listener) {
        deleteEdgeButton.addActionListener(listener);
    }

    public void setChangeEdgeWeightAction(ActionListener listener) {
        changeEdgeWeightButton.addActionListener(listener);
    }

    public void setSaveGraphAction(ActionListener listener) {
        saveGraphButton.addActionListener(listener);
    }

    public void setRunDFSAction(ActionListener listener) {
        runDFSButton.addActionListener(listener);
    }

    public void setRunBFSAction(ActionListener listener) {
        runBFSButton.addActionListener(listener);
    }

    public void setRunDijkstraAction(ActionListener listener) {
        runDijkstraButton.addActionListener(listener);
    }

    public void setRunEdmondsKarpAction(ActionListener listener) {
        runEdmondsKarpButton.addActionListener(listener);
    }

    public void setRunTopologicalSortAction(ActionListener listener) {
        runTopologicalSortButton.addActionListener(listener);
    }

    /**
     * Устанавливает обработчик события для кнопки укладки графа.
     *
     * @param listener Обработчик события.
     */
    public void setArrangeGraphAction(ActionListener listener) {
        arrangeGraphButton.addActionListener(listener);
    }
}

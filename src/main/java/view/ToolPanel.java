package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class ToolPanel extends JPanel {
    private JButton backButton;
    private JButton saveGraphButton;
    private JButton addVertexButton;
    private JButton addEdgeButton;
    private JButton deleteVertexButton;
    private JButton deleteEdgeButton;
    private JButton changeEdgeWeightButton;
    private JButton runDFSButton;
    private JButton runBFSButton;
    private JButton runDijkstraButton;
    private JButton runEdmondsKarpButton;
    private JButton runTopologicalSortButton;
    private JButton arrangeGraphButton;

    public ToolPanel() {

        setLayout(new WrapLayout(FlowLayout.LEFT, 5, 5));
        setBackground(Color.LIGHT_GRAY);


        backButton = new JButton("Назад");
        saveGraphButton = new JButton("Сохранить граф");
        addVertexButton = new JButton("Добавить вершину");
        addEdgeButton = new JButton("Добавить ребро");
        deleteVertexButton = new JButton("Удалить вершину");
        deleteEdgeButton = new JButton("Удалить ребро");
        changeEdgeWeightButton = new JButton("Изменить вес ребра");
        runDFSButton = new JButton("Запустить DFS");
        runBFSButton = new JButton("Запустить BFS");
        runDijkstraButton = new JButton("Запустить Dijkstra");
        runEdmondsKarpButton = new JButton("Запустить Edmonds-Karp");
        runTopologicalSortButton = new JButton("Запустить Топологическую сортировку");
        arrangeGraphButton = new JButton("Укладка графа");


        add(backButton);
        add(saveGraphButton);
        add(addVertexButton);
        add(addEdgeButton);
        add(deleteVertexButton);
        add(deleteEdgeButton);
        add(changeEdgeWeightButton);
        add(runDFSButton);
        add(runBFSButton);
        add(runDijkstraButton);
        add(runEdmondsKarpButton);
        add(runTopologicalSortButton);
        add(arrangeGraphButton);
    }


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

    public void setArrangeGraphAction(ActionListener listener) {
        arrangeGraphButton.addActionListener(listener);
    }
}

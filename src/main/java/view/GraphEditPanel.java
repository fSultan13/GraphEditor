package view;

import io.GraphWriter;
import model.*;
import model.algorithms.*;
import view.btn.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
/**
 * Панель редактирования графа.
 */
public class GraphEditPanel extends JPanel {
    private Graph graph;

    private static final int VERTEX_RADIUS = 20;

    private List<Point> vertexPositions = new ArrayList<>();

    // Верхняя панель инструментов
    private ToolPanel toolPanel;

    // Набор посещённых вершин после выполнения алгоритма (DFS, BFS, Dijkstra, Edmonds-Karp, TopologicalSort)
    private Set<Vertex> visitedVertices;

    // Расстояния от начальной вершины после выполнения алгоритма Дейкстры
    private Map<Vertex, Double> shortestDistances;

    // Максимальный поток после выполнения алгоритма Эдмондса-Карпа
    private double maxFlow;

    // Список вершин после выполнения топологической сортировки
    private List<Vertex> topologicalOrder;

    // Тип последнего выполненного алгоритма (для возможного расширения)
    private String lastAlgorithm = "";

    // Метка для отображения максимального потока
    private JLabel maxFlowLabel;

    public GraphEditPanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // Инициализируем и добавляем панель инструментов
        toolPanel = new ToolPanel();
        add(toolPanel, BorderLayout.NORTH);

        // Инициализируем и добавляем панель состояния
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        maxFlowLabel = new JLabel("Максимальный поток: 0.0");
        statusPanel.add(maxFlowLabel);
        add(statusPanel, BorderLayout.SOUTH);

        // Устанавливаем обработчики действий для кнопок
        toolPanel.setBackAction(e -> backToStart());
        toolPanel.setAddVertexAction(e -> addVertex());
        toolPanel.setAddEdgeAction(e -> addEdge());
        toolPanel.setDeleteVertexAction(e -> deleteVertex());
        toolPanel.setDeleteEdgeAction(e -> deleteEdge());
        toolPanel.setChangeEdgeWeightAction(e -> changeEdgeWeight());
        toolPanel.setSaveGraphAction(e -> saveGraph());
        toolPanel.setRunDFSAction(e -> runDFS());
        toolPanel.setRunBFSAction(e -> runBFS());
        toolPanel.setRunDijkstraAction(e -> runDijkstra());
        toolPanel.setRunEdmondsKarpAction(e -> runEdmondsKarp());
        toolPanel.setRunTopologicalSortAction(e -> runTopologicalSort());
        toolPanel.setArrangeGraphAction(e -> arrangeGraph());
    }

    /**
     * Переход обратно на стартовую панель.
     */
    private void backToStart() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (parentFrame instanceof MainFrame) {
            ((MainFrame) parentFrame).showStartPanel();
        }
    }

    /**
     * Устанавливает граф для отображения.
     *
     * @param graph Граф.
     */
    public void setGraph(Graph graph) {
        this.graph = graph;
        this.visitedVertices = null; // Сброс предыдущих результатов алгоритма
        this.shortestDistances = null; // Сброс результатов алгоритма Дейкстры
        this.maxFlow = 0.0; // Сброс результата алгоритма Эдмондса-Карпа
        this.topologicalOrder = null; // Сброс результата топологической сортировки
        maxFlowLabel.setText("Максимальный поток: 0.0"); // Сброс метки
        layoutVertices();
        repaint();
    }

    /**
     * Расставляет вершины по окружности для наглядности.
     */
    private void layoutVertices() {
        vertexPositions.clear();
        if (graph == null || graph.getVertices().isEmpty()) {
            return;
        }

        int width = getWidth();
        int height = getHeight() - toolPanel.getHeight(); // Учёт высоты панели инструментов
        int centerX = width / 2;
        int centerY = height / 2 + toolPanel.getHeight();

        int n = graph.getVertices().size();
        int radius = Math.min(width, height) / 3;

        List<Vertex> vertexList = new ArrayList<>(graph.getVertices());

        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            vertexPositions.add(new Point(x, y));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (graph == null || graph.getVertices().isEmpty()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawEdges(g2);
        drawVertices(g2);
    }

    /**
     * Отрисовка вершин.
     *
     * @param g2 Графический контекст.
     */
    private void drawVertices(Graphics2D g2) {
        List<Vertex> vertexList = new ArrayList<>(graph.getVertices());

        for (int i = 0; i < vertexList.size(); i++) {
            Vertex v = vertexList.get(i);
            Point p = vertexPositions.get(i);

            // Определение цвета вершины
            if (lastAlgorithm.equals("DFS") || lastAlgorithm.equals("BFS") || lastAlgorithm.equals("Dijkstra") || lastAlgorithm.equals("EdmondsKarp") || lastAlgorithm.equals("TopologicalSort") || lastAlgorithm.equals("ARRANGE")) {
                switch (lastAlgorithm) {
                    case "DFS":
                        if (visitedVertices != null && visitedVertices.contains(v)) {
                            g2.setColor(Color.GREEN); // DFS: зелёный
                        } else {
                            g2.setColor(Color.LIGHT_GRAY);
                        }
                        break;
                    case "BFS":
                        if (visitedVertices != null && visitedVertices.contains(v)) {
                            g2.setColor(Color.CYAN); // BFS: голубой
                        } else {
                            g2.setColor(Color.LIGHT_GRAY);
                        }
                        break;
                    case "Dijkstra":
                        if (shortestDistances != null) {
                            g2.setColor(Color.ORANGE); // Dijkstra: оранжевый
                        } else {
                            g2.setColor(Color.LIGHT_GRAY);
                        }
                        break;
                    case "EdmondsKarp":
                        if (visitedVertices != null && visitedVertices.contains(v)) {
                            g2.setColor(Color.MAGENTA); // Edmonds-Karp: пурпурный
                        } else {
                            g2.setColor(Color.LIGHT_GRAY);
                        }
                        break;
                    case "TopologicalSort":
                        if (topologicalOrder != null && topologicalOrder.contains(v)) {
                            g2.setColor(Color.BLUE); // TopologicalSort: синий
                        } else {
                            g2.setColor(Color.LIGHT_GRAY);
                        }
                        break;
                    case "ARRANGE":
                        // Можно добавить цветовое выделение для различных укладок, если необходимо
                        g2.setColor(Color.LIGHT_GRAY);
                        break;
                    default:
                        g2.setColor(Color.LIGHT_GRAY);
                }
            } else {
                g2.setColor(Color.LIGHT_GRAY);
            }

            g2.fillOval(p.x - VERTEX_RADIUS, p.y - VERTEX_RADIUS, 2 * VERTEX_RADIUS, 2 * VERTEX_RADIUS);

            g2.setColor(Color.BLACK);
            g2.drawOval(p.x - VERTEX_RADIUS, p.y - VERTEX_RADIUS, 2 * VERTEX_RADIUS, 2 * VERTEX_RADIUS);

            String label = v.getLabel();
            if (lastAlgorithm.equals("Dijkstra") && shortestDistances != null) {
                Double distance = shortestDistances.get(v);
                if (distance != null && distance != Double.POSITIVE_INFINITY) {
                    label += " (" + distance + ")";
                } else if (distance != null && distance == Double.POSITIVE_INFINITY) {
                    label += " (∞)";
                }
            }

            // Если был выполнен алгоритм топологической сортировки, отображаем номер порядка
            if (lastAlgorithm.equals("TopologicalSort") && topologicalOrder != null) {
                int order = topologicalOrder.indexOf(v) + 1;
                label += " [" + order + "]";
            }

            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(label);
            int textHeight = fm.getAscent();
            g2.drawString(label, p.x - textWidth / 2, p.y + textHeight / 4);
        }
    }

    /**
     * Отрисовка рёбер.
     *
     * @param g2 Графический контекст.
     */
    private void drawEdges(Graphics2D g2) {
        if (graph.getEdges().isEmpty()) return;

        List<Vertex> vertexList = new ArrayList<>(graph.getVertices());
        Set<Edge> mstEdges = null;

        if (lastAlgorithm.equals("MST")) {
            // Получаем MST, если был выполнен алгоритм MST
            mstEdges = MinimumSpanningTree.kruskalMST(graph);
        }

        for (Edge e : graph.getEdges()) {
            Vertex from = e.getFrom();
            Vertex to = e.getTo();

            int fromIndex = vertexList.indexOf(from);
            int toIndex = vertexList.indexOf(to);

            if (fromIndex == -1 || toIndex == -1) {
                continue;
            }

            Point p1 = vertexPositions.get(fromIndex);
            Point p2 = vertexPositions.get(toIndex);

            // Определение цвета ребра
            if (lastAlgorithm.equals("DFS") || lastAlgorithm.equals("BFS") || lastAlgorithm.equals("Dijkstra") || lastAlgorithm.equals("EdmondsKarp") || lastAlgorithm.equals("TopologicalSort") || lastAlgorithm.equals("ARRANGE")) {
                switch (lastAlgorithm) {
                    case "DFS":
                        if (visitedVertices != null && visitedVertices.contains(from) && visitedVertices.contains(to)) {
                            g2.setColor(Color.GREEN); // DFS: зелёный
                        } else {
                            g2.setColor(Color.BLACK);
                        }
                        break;
                    case "BFS":
                        if (visitedVertices != null && visitedVertices.contains(from) && visitedVertices.contains(to)) {
                            g2.setColor(Color.MAGENTA); // BFS: пурпурный
                        } else {
                            g2.setColor(Color.BLACK);
                        }
                        break;
                    case "Dijkstra":
                        if (shortestDistances != null && visitedVertices != null && visitedVertices.contains(from) && visitedVertices.contains(to)) {
                            g2.setColor(Color.RED); // Dijkstra: красный
                        } else {
                            g2.setColor(Color.BLACK);
                        }
                        break;
                    case "EdmondsKarp":
                        if (visitedVertices != null && visitedVertices.contains(from) && visitedVertices.contains(to)) {
                            g2.setColor(Color.ORANGE); // Edmonds-Karp: оранжевый
                        } else {
                            g2.setColor(Color.BLACK);
                        }
                        break;
                    case "TopologicalSort":
                        if (topologicalOrder != null) {
                            int fromOrder = topologicalOrder.indexOf(from);
                            int toOrder = topologicalOrder.indexOf(to);
                            if (fromOrder < toOrder) {
                                g2.setColor(Color.BLUE); // TopologicalSort: синий
                            } else {
                                g2.setColor(Color.BLACK);
                            }
                        } else {
                            g2.setColor(Color.BLACK);
                        }
                        break;
                    case "ARRANGE":
                        // Можно добавить цветовое выделение для различных укладок, если необходимо
                        g2.setColor(Color.BLACK);
                        break;
                    default:
                        g2.setColor(Color.BLACK);
                }
            } else if (lastAlgorithm.equals("MST") && mstEdges != null) {
                if (mstEdges.contains(e)) {
                    g2.setColor(Color.GREEN); // MST: зелёный
                } else {
                    g2.setColor(Color.LIGHT_GRAY);
                }
            } else {
                g2.setColor(Color.BLACK);
            }

            g2.drawLine(p1.x, p1.y, p2.x, p2.y);

            // Если граф ориентированный, рисуем стрелку
            if (graph instanceof model.graphs.DirectedGraph) {
                drawArrowHead(g2, p1, p2);
            }

            double weight = e.getWeight();
            if (weight != 0) {
                String wStr = String.valueOf(weight);
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(wStr);
                int textHeight = fm.getAscent();

                int midX = (p1.x + p2.x) / 2;
                int midY = (p1.y + p2.y) / 2;

                g2.setColor(Color.BLUE);
                g2.drawString(wStr, midX - textWidth / 2, midY - textHeight / 2);
            }
        }
    }

    /**
     * Рисует стрелку для ориентированного ребра.
     *
     * @param g2   Графический контекст.
     * @param from Начальная точка.
     * @param to   Конечная точка.
     */
    private void drawArrowHead(Graphics2D g2, Point from, Point to) {
        int arrowSize = 10;
        double angle = Math.atan2(to.y - from.y, to.x - from.x);
        int x1 = (int) (to.x - arrowSize * Math.cos(angle - Math.PI / 6));
        int y1 = (int) (to.y - arrowSize * Math.sin(angle - Math.PI / 6));
        int x2 = (int) (to.x - arrowSize * Math.cos(angle + Math.PI / 6));
        int y2 = (int) (to.y - arrowSize * Math.sin(angle + Math.PI / 6));

        g2.setColor(Color.BLACK);
        g2.fillPolygon(new int[]{to.x, x1, x2}, new int[]{to.y, y1, y2}, 3);
    }

    /**
     * Добавление новой вершины.
     */
    private void addVertex() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        AddVertexDialog dialog = new AddVertexDialog(parentFrame);
        dialog.setVisible(true);
        Vertex newVertex = dialog.getVertex();
        if (newVertex != null) {
            graph.addVertex(newVertex);
            layoutVertices();
            repaint();
        }
    }

    /**
     * Добавление нового ребра.
     */
    private void addEdge() {
        if (graph.getVertices().size() < 2) {
            JOptionPane.showMessageDialog(this, "Для добавления ребра необходимо как минимум две вершины.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        List<Vertex> vertexList = new ArrayList<>(graph.getVertices());
        AddEdgeDialog dialog = new AddEdgeDialog(parentFrame, vertexList);
        dialog.setVisible(true);
        Edge newEdge = dialog.getEdge();
        if (newEdge != null) {
            graph.addEdge(newEdge);
            layoutVertices();
            repaint();
        }
    }

    /**
     * Удаление вершины.
     */
    private void deleteVertex() {
        if (graph.getVertices().isEmpty()) {
            JOptionPane.showMessageDialog(this, "В графе нет вершин для удаления.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        List<Vertex> vertexList = new ArrayList<>(graph.getVertices());
        DeleteVertexDialog dialog = new DeleteVertexDialog(parentFrame, vertexList);
        dialog.setVisible(true);
        Vertex selectedVertex = dialog.getSelectedVertex();
        if (selectedVertex != null) {
            graph.removeVertex(selectedVertex);
            visitedVertices = null; // Сброс результатов алгоритма
            shortestDistances = null; // Сброс результатов алгоритма Дейкстры
            maxFlow = 0.0; // Сброс результата алгоритма Эдмондса-Карпа
            topologicalOrder = null; // Сброс результата топологической сортировки
            maxFlowLabel.setText("Максимальный поток: 0.0"); // Сброс метки
            layoutVertices();
            repaint();
        }
    }

    /**
     * Удаление ребра.
     */
    private void deleteEdge() {
        if (graph.getEdges().isEmpty()) {
            JOptionPane.showMessageDialog(this, "В графе нет рёбер для удаления.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        List<Edge> edgeList = new ArrayList<>(graph.getEdges());
        DeleteEdgeDialog dialog = new DeleteEdgeDialog(parentFrame, edgeList);
        dialog.setVisible(true);
        Edge selectedEdge = dialog.getSelectedEdge();
        if (selectedEdge != null) {
            graph.removeEdge(selectedEdge);
            visitedVertices = null; // Сброс результатов алгоритма
            shortestDistances = null; // Сброс результатов алгоритма Дейкстры
            maxFlow = 0.0; // Сброс результата алгоритма Эдмондса-Карпа
            topologicalOrder = null; // Сброс результата топологической сортировки
            maxFlowLabel.setText("Максимальный поток: 0.0"); // Сброс метки
            layoutVertices();
            repaint();
        }
    }

    /**
     * Изменение веса ребра.
     */
    private void changeEdgeWeight() {
        if (graph.getEdges().isEmpty()) {
            JOptionPane.showMessageDialog(this, "В графе нет рёбер для изменения веса.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        List<Edge> edgeList = new ArrayList<>(graph.getEdges());
        ChangeEdgeWeightDialog dialog = new ChangeEdgeWeightDialog(parentFrame, edgeList);
        dialog.setVisible(true);
        Edge selectedEdge = dialog.getSelectedEdge();
        double newWeight = dialog.getNewWeight();

        if (selectedEdge != null) {
            selectedEdge.setWeight(newWeight);
            repaint();
        }
    }

    /**
     * Сохранение графа в файл.
     */
    private void saveGraph() {
        if (graph == null || graph.getVertices().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Граф пуст или не инициализирован.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Сохранить граф");
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            // Убедимся, что файл имеет расширение .json
            if (!filePath.toLowerCase().endsWith(".json")) {
                filePath += ".json";
            }

            GraphWriter writer = new GraphWriter();
            try {
                writer.saveGraph(graph, filePath);
                JOptionPane.showMessageDialog(this, "Граф успешно сохранён в файл:\n" + filePath, "Успех", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Ошибка при сохранении графа:\n" + ex.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Запуск алгоритма DFS.
     */
    private void runDFS() {
        if (graph == null || graph.getVertices().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Граф пуст или не инициализирован.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        List<Vertex> vertexList = new ArrayList<>(graph.getVertices());

        // Открываем диалог для выбора начальной вершины
        SelectStartVertexDialog dialog = new SelectStartVertexDialog(parentFrame, vertexList, "Запуск DFS");
        dialog.setVisible(true);
        Vertex startVertex = dialog.getSelectedVertex();

        if (startVertex != null) {
            // Выполнение DFS
            visitedVertices = DFS.dfs(graph, startVertex);
            lastAlgorithm = "DFS";
            shortestDistances = null; // Сброс результатов алгоритма Дейкстры
            maxFlow = 0.0; // Сброс результата алгоритма Эдмондса-Карпа
            topologicalOrder = null; // Сброс результата топологической сортировки
            maxFlowLabel.setText("Максимальный поток: 0.0"); // Сброс метки
            repaint();

            // Отображение результата DFS
            JOptionPane.showMessageDialog(this,
                    "Алгоритм DFS завершён.\n" +
                            "Посещённые вершины:\n" + visitedVertices,
                    "Результат DFS",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Запуск алгоритма BFS.
     */
    private void runBFS() {
        if (graph == null || graph.getVertices().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Граф пуст или не инициализирован.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        List<Vertex> vertexList = new ArrayList<>(graph.getVertices());

        // Открываем диалог для выбора начальной вершины
        SelectStartVertexDialog dialog = new SelectStartVertexDialog(parentFrame, vertexList, "Запуск BFS");
        dialog.setVisible(true);
        Vertex startVertex = dialog.getSelectedVertex();

        if (startVertex != null) {
            // Выполнение BFS
            visitedVertices = BFS.bfs(graph, startVertex);
            lastAlgorithm = "BFS";
            shortestDistances = null; // Сброс результатов алгоритма Дейкстры
            maxFlow = 0.0; // Сброс результата алгоритма Эдмондса-Карпа
            topologicalOrder = null; // Сброс результата топологической сортировки
            maxFlowLabel.setText("Максимальный поток: 0.0"); // Сброс метки
            repaint();

            // Отображение результата BFS
            JOptionPane.showMessageDialog(this,
                    "Алгоритм BFS завершён.\n" +
                            "Посещённые вершины:\n" + visitedVertices,
                    "Результат BFS",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Запуск алгоритма Дейкстры.
     */
    private void runDijkstra() {
        if (graph == null || graph.getVertices().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Граф пуст или не инициализирован.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        List<Vertex> vertexList = new ArrayList<>(graph.getVertices());

        // Открываем диалог для выбора начальной вершины
        SelectStartVertexDialog dialog = new SelectStartVertexDialog(parentFrame, vertexList, "Запуск Dijkstra");
        dialog.setVisible(true);
        Vertex startVertex = dialog.getSelectedVertex();

        if (startVertex != null) {
            // Выполнение Дейкстры
            shortestDistances = Dijkstra.dijkstra(graph, startVertex);
            lastAlgorithm = "Dijkstra";
            visitedVertices = null; // Сброс результатов DFS и BFS
            maxFlow = 0.0; // Сброс результата алгоритма Эдмондса-Карпа
            topologicalOrder = null; // Сброс результата топологической сортировки
            maxFlowLabel.setText("Максимальный поток: 0.0"); // Сброс метки
            repaint();

            // Отображение результата Дейкстры
            StringBuilder resultMessage = new StringBuilder("Алгоритм Dijkstra завершён.\n");
            for (Map.Entry<Vertex, Double> entry : shortestDistances.entrySet()) {
                String distanceStr = (entry.getValue() == Double.POSITIVE_INFINITY) ? "∞" : String.valueOf(entry.getValue());
                resultMessage.append(entry.getKey().getLabel()).append(": ").append(distanceStr).append("\n");
            }

            JOptionPane.showMessageDialog(this,
                    resultMessage.toString(),
                    "Результат Dijkstra",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Запуск алгоритма Эдмондса-Карпа.
     */
    private void runEdmondsKarp() {
        if (graph == null || graph.getVertices().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Граф пуст или не инициализирован.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        List<Vertex> vertexList = new ArrayList<>(graph.getVertices());

        // Открываем диалог для выбора исходной и конечной вершин
        SelectSourceSinkVertexDialog dialog = new SelectSourceSinkVertexDialog(parentFrame, vertexList);
        dialog.setVisible(true);
        Vertex source = dialog.getSelectedSource();
        Vertex sink = dialog.getSelectedSink();

        if (source != null && sink != null) {
            // Проверка, что граф ориентированный, так как алгоритм Эдмондса-Карпа применим к направленным графам
            if (!(graph instanceof model.graphs.DirectedGraph)) {
                JOptionPane.showMessageDialog(this, "Алгоритм Эдмондса-Карпа применим только к ориентированным графам.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Выполнение алгоритма Эдмондса-Карпа
            maxFlow = EdmondsKarp.edmondsKarp(graph, source, sink);
            lastAlgorithm = "EdmondsKarp";
            visitedVertices = null; // Сброс результатов других алгоритмов
            shortestDistances = null; // Сброс результатов алгоритма Дейкстры
            topologicalOrder = null; // Сброс результата топологической сортировки
            repaint();

            // Обновление метки максимального потока
            maxFlowLabel.setText("Максимальный поток: " + maxFlow);

            // Отображение результата алгоритма Эдмондса-Карпа
            JOptionPane.showMessageDialog(this,
                    "Алгоритм Эдмондса-Карпа завершён.\n" +
                            "Максимальный поток: " + maxFlow,
                    "Результат Edmonds-Karp",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Запуск алгоритма топологической сортировки.
     */
    private void runTopologicalSort() {
        if (graph == null || graph.getVertices().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Граф пуст или не инициализирован.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Проверка, что граф ориентированный
        if (!(graph instanceof model.graphs.DirectedGraph)) {
            JOptionPane.showMessageDialog(this, "Топологическая сортировка применима только к ориентированным графам.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Выполнение топологической сортировки
            topologicalOrder = TopologicalSort.topologicalSort(graph);
            lastAlgorithm = "TopologicalSort";
            visitedVertices = null; // Сброс результатов других алгоритмов
            shortestDistances = null; // Сброс результатов алгоритма Дейкстры
            maxFlow = 0.0; // Сброс результата алгоритма Эдмондса-Карпа
            maxFlowLabel.setText("Максимальный поток: 0.0"); // Сброс метки
            repaint();

            // Отображение результата топологической сортировки
            TopologicalSortDialog sortDialog = new TopologicalSortDialog((JFrame) SwingUtilities.getWindowAncestor(this), topologicalOrder);
            sortDialog.setVisible(true);
        } catch (IllegalStateException ex) {
            // Если граф содержит цикл
            JOptionPane.showMessageDialog(this, "Граф содержит цикл! Топологическая сортировка невозможна.", "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Укладка графа различными способами.
     */
    private void arrangeGraph() {
        if (graph == null || graph.getVertices().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Граф пуст или не инициализирован.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Открываем диалог для выбора типа укладки
        ArrangeGraphDialog dialog = new ArrangeGraphDialog((JFrame) SwingUtilities.getWindowAncestor(this));
        dialog.setVisible(true);
        String selectedLayout = dialog.getSelectedLayout();

        if (selectedLayout != null) {
            switch (selectedLayout) {
                case "CIRCULAR":
                    arrangeCircular();
                    lastAlgorithm = "ARRANGE";
                    break;
                case "TOPOLOGICAL":
                    arrangeTopological();
                    lastAlgorithm = "ARRANGE";
                    break;
                case "MST":
                    arrangeMST();
                    lastAlgorithm = "MST";
                    break;
                default:
                    break;
            }
            repaint();
        }
    }

    /**
     * Укладка графа по круговой схеме.
     */
    private void arrangeCircular() {
        int width = getWidth();
        int height = getHeight() - toolPanel.getHeight(); // Учёт высоты панели инструментов
        int centerX = width / 2;
        int centerY = height / 2 + toolPanel.getHeight();
        int radius = Math.min(width, height) / 3;

        int n = graph.getVertices().size();
        List<Vertex> vertexList = new ArrayList<>(graph.getVertices());

        vertexPositions.clear();

        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            vertexPositions.add(new Point(x, y));
        }
    }

    /**
     * Укладка графа по топологической сортировке.
     */
    private void arrangeTopological() {
        if (!(graph instanceof model.graphs.DirectedGraph)) {
            JOptionPane.showMessageDialog(this, "Топологическая укладка применима только к ориентированным графам.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (topologicalOrder == null || topologicalOrder.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Выполните топологическую сортировку перед укладкой.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int width = getWidth();
        int height = getHeight() - toolPanel.getHeight();
        int centerX = width / 2;
        int topMargin = 50;
        int verticalSpacing = (height - 2 * topMargin) / topologicalOrder.size();

        vertexPositions.clear();

        for (int i = 0; i < topologicalOrder.size(); i++) {
            Vertex v = topologicalOrder.get(i);
            int x = centerX;
            int y = topMargin + i * verticalSpacing;
            vertexPositions.add(new Point(x, y));
        }
    }

    /**
     * Укладка графа по минимальному остовному дереву.
     */
    private void arrangeMST() {
        // Получаем MST с использованием алгоритма Краскала
        Set<Edge> mstEdges = MinimumSpanningTree.kruskalMST(graph);
        if (mstEdges.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Минимальное остовное дерево не может быть построено.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Создаём подграф MST
        Graph mstGraph = new model.graphs.UndirectedGraph(); // Минимальное остовное дерево — неориентированное
        for (Edge edge : mstEdges) {
            mstGraph.addEdge(edge);
        }
        for (Vertex v : graph.getVertices()) {
            mstGraph.addVertex(v);
        }

        // Определяем корень дерева (можно выбрать произвольно, например, первую вершину)
        Vertex root = new ArrayList<>(mstGraph.getVertices()).get(0);

        // Расставляем вершины в виде дерева
        layoutTree(root, mstGraph);
    }

    /**
     * Расставляет вершины в виде дерева с заданным корнем.
     *
     * @param root     Корневая вершина.
     * @param mstGraph Граф, представляющий MST.
     */
    private void layoutTree(Vertex root, Graph mstGraph) {
        int width = getWidth();
        int height = getHeight() - toolPanel.getHeight();
        int centerX = width / 2;
        int topMargin = 50;
        int verticalSpacing = 80;
        vertexPositions.clear();

        // Используем BFS для определения уровней дерева
        Map<Vertex, Integer> levels = new HashMap<>();
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(root);
        levels.put(root, 0);

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            int currentLevel = levels.get(current);

            for (Edge edge : mstGraph.getEdges()) {
                if (edge.getFrom().equals(current)) {
                    Vertex neighbor = edge.getTo();
                    if (!levels.containsKey(neighbor)) {
                        levels.put(neighbor, currentLevel + 1);
                        queue.add(neighbor);
                    }
                } else if (edge.getTo().equals(current)) {
                    Vertex neighbor = edge.getFrom();
                    if (!levels.containsKey(neighbor)) {
                        levels.put(neighbor, currentLevel + 1);
                        queue.add(neighbor);
                    }
                }
            }
        }

        // Группируем вершины по уровням
        Map<Integer, List<Vertex>> levelMap = new HashMap<>();
        int maxLevel = 0;
        for (Map.Entry<Vertex, Integer> entry : levels.entrySet()) {
            int level = entry.getValue();
            maxLevel = Math.max(maxLevel, level);
            levelMap.computeIfAbsent(level, k -> new ArrayList<>()).add(entry.getKey());
        }

        // Расставляем вершины
        for (int level = 0; level <= maxLevel; level++) {
            List<Vertex> verticesAtLevel = levelMap.get(level);
            if (verticesAtLevel == null) continue;

            int n = verticesAtLevel.size();
            int radius = width / (n + 1);
            int y = topMargin + level * verticalSpacing;

            for (int i = 0; i < n; i++) {
                int x = (i + 1) * radius;
                vertexPositions.add(new Point(x, y));
            }
        }
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        layoutVertices();
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        layoutVertices();
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        layoutVertices();
    }
}
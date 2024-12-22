package tests;

import model.*;
import model.graphs.*;
import model.algorithms.MinimumSpanningTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

class MinimumSpanningTreeTest {

    private Vertex A, B, C, D;
    private Graph graph;

    @BeforeEach
    void setUp() {
        // Создание вершин
        A = new Vertex("A");
        B = new Vertex("B");
        C = new Vertex("C");
        D = new Vertex("D");

        // Создание неориентированного графа с весами
        graph = new UndirectedGraph();
        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addVertex(D);

        // Добавление рёбер с весами
        graph.addEdge(new Edge(A, B, 1.0));
        graph.addEdge(new Edge(A, C, 3.0));
        graph.addEdge(new Edge(B, C, 2.0));
        graph.addEdge(new Edge(B, D, 4.0));
        graph.addEdge(new Edge(C, D, 4.0));
    }

    @Test
    void testKruskalMST() {
        // Запуск алгоритма Краскала
        Set<Edge> mst = MinimumSpanningTree.kruskalMST(graph);

        // Ожидаемое количество рёбер в MST: V-1 = 3
        assertEquals(3, mst.size());

        // Проверяем, что все рёбра MST имеют минимальные веса и не образуют цикл
        assertTrue(mst.contains(new Edge(A, B, 1.0)));
        assertTrue(mst.contains(new Edge(B, C, 2.0)));
    }

    @Test
    void testKruskalMSTWithCycle() {
        // Добавляем ребро, образующее цикл
        graph.addEdge(new Edge(C, A, 3.0));

        // Запуск алгоритма Краскала
        Set<Edge> mst = MinimumSpanningTree.kruskalMST(graph);

        // Ожидаемое количество рёбер в MST: V-1 = 3
        assertEquals(3, mst.size());

        // Проверяем, что ребро C-A не включено, так как оно образует цикл
        assertFalse(mst.contains(new Edge(C, A, 3.0)));
    }

    @Test
    void testKruskalMSTSingleVertex() {
        // Создаём граф с одной вершиной
        Graph singleVertexGraph = new UndirectedGraph();
        Vertex single = new Vertex("Single");
        singleVertexGraph.addVertex(single);

        // Запуск алгоритма Краскала
        Set<Edge> mst = MinimumSpanningTree.kruskalMST(singleVertexGraph);

        // Ожидаем, что MST пусто, так как только одна вершина
        assertTrue(mst.isEmpty());
    }

    @Test
    void testKruskalMSTDisconnectedGraph() {
        // Создаём граф с двумя компонентами связности
        Vertex E = new Vertex("E");
        graph.addVertex(E);
        graph.addEdge(new Edge(E, E)); // Добавляем петлю, чтобы E была отдельной компонентой

        // Запуск алгоритма Краскала
        Set<Edge> mst = MinimumSpanningTree.kruskalMST(graph);

        // Ожидаем, что MST содержит рёбра одной из компонент связности
        // В нашем случае MST не может соединить все вершины, так как граф несвязный
        // Алгоритм Краскала находит минимальные остовные леса
        assertEquals(3, mst.size());
    }
}


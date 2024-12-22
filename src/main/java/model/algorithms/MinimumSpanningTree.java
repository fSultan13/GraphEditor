package model.algorithms;

import model.*;

import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class MinimumSpanningTree {


    // Метод для нахождения минимального остовного дерева с использованием алгоритма Краскала
    public static Set<Edge> kruskalMST(Graph graph) {
        class DisjointSet {
            private Map<Vertex, Vertex> parent;

            public DisjointSet(Set<Vertex> vertices) {
                parent = new HashMap<>();
                for (Vertex vertex : vertices) {
                    parent.put(vertex, vertex); // Каждый элемент является своим собственным родителем
                }
            }

            // Метод для нахождения корня множества, с которым принадлежит вершина
            public Vertex find(Vertex vertex) {
                if (!parent.get(vertex).equals(vertex)) {
                    parent.put(vertex, find(parent.get(vertex))); // Путь сжатия
                }
                return parent.get(vertex);
            }

            // Метод для объединения двух множеств
            public void union(Vertex vertex1, Vertex vertex2) {
                Vertex root1 = find(vertex1);
                Vertex root2 = find(vertex2);
                if (!root1.equals(root2)) {
                    parent.put(root1, root2); // Объединяем два множества
                }
            }
        }

        Set<Edge> mst = new HashSet<>();

        // Сортируем все рёбра по возрастанию их весов
        List<Edge> sortedEdges = new ArrayList<>(graph.getEdges());
        sortedEdges.sort(Comparator.comparingDouble(Edge::getWeight));

        // Инициализируем структуру данных Union-Find
        DisjointSet disjointSet = new DisjointSet(graph.getVertices());

        for (Edge edge : sortedEdges) {
            Vertex u = edge.getFrom();
            Vertex v = edge.getTo();

            // Находим корни множеств, к которым принадлежат вершины u и v
            Vertex setU = disjointSet.find(u);
            Vertex setV = disjointSet.find(v);

            // Если они принадлежат разным множествам, добавляем ребро в MST и объединяем множества
            if (!setU.equals(setV)) {
                mst.add(edge);
                disjointSet.union(setU, setV);
            }
        }

        return mst;
    }


}

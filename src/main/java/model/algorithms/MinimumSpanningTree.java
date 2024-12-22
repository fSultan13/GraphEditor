package model.algorithms;

import model.*;

import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class MinimumSpanningTree {
    public static Set<Edge> kruskalMST(Graph graph) {
        class DisjointSet {
            private Map<Vertex, Vertex> parent;

            public DisjointSet(Set<Vertex> vertices) {
                parent = new HashMap<>();
                for (Vertex vertex : vertices) {
                    parent.put(vertex, vertex);
                }
            }

            public Vertex find(Vertex vertex) {
                if (!parent.get(vertex).equals(vertex)) {
                    parent.put(vertex, find(parent.get(vertex)));
                }
                return parent.get(vertex);
            }


            public void union(Vertex vertex1, Vertex vertex2) {
                Vertex root1 = find(vertex1);
                Vertex root2 = find(vertex2);
                if (!root1.equals(root2)) {
                    parent.put(root1, root2);
                }
            }
        }

        Set<Edge> mst = new HashSet<>();

        List<Edge> sortedEdges = new ArrayList<>(graph.getEdges());
        sortedEdges.sort(Comparator.comparingDouble(Edge::getWeight));

        DisjointSet disjointSet = new DisjointSet(graph.getVertices());

        for (Edge edge : sortedEdges) {
            Vertex u = edge.getFrom();
            Vertex v = edge.getTo();

            Vertex setU = disjointSet.find(u);
            Vertex setV = disjointSet.find(v);

            if (!setU.equals(setV)) {
                mst.add(edge);
                disjointSet.union(setU, setV);
            }
        }

        return mst;
    }
}

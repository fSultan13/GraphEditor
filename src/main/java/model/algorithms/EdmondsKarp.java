package model.algorithms;

import model.*;

import java.util.*;

public class EdmondsKarp {

    public static double edmondsKarp(Graph graph, Vertex source, Vertex sink) {
        Map<Vertex, Map<Vertex, Double>> capacity = new HashMap<>();

        for (Vertex vertex : graph.getVertices()) {
            capacity.put(vertex, new HashMap<>());
        }

        for (Edge edge : graph.getEdges()) {
            capacity.get(edge.getFrom()).put(edge.getTo(), edge.getWeight());
        }

        double maxFlow = 0;

        while (true) {
            Map<Vertex, Vertex> parent = bfs(capacity, source, sink);

            if (parent == null) {
                break;
            }

            double pathFlow = Double.POSITIVE_INFINITY;
            Vertex v = sink;
            while (!v.equals(source)) {
                Vertex u = parent.get(v);
                pathFlow = Math.min(pathFlow, capacity.get(u).get(v));
                v = u;
            }

            v = sink;
            while (!v.equals(source)) {
                Vertex u = parent.get(v);
                capacity.get(u).put(v, capacity.get(u).get(v) - pathFlow);
                capacity.get(v).put(u, capacity.get(v).getOrDefault(u, 0.0) + pathFlow);
                v = u;
            }

            maxFlow += pathFlow;
        }

        return maxFlow;
    }

    private static Map<Vertex, Vertex> bfs(Map<Vertex, Map<Vertex, Double>> capacity, Vertex source, Vertex sink) {
        Map<Vertex, Vertex> parent = new HashMap<>();
        Set<Vertex> visited = new HashSet<>();
        Queue<Vertex> queue = new LinkedList<>();
        queue.add(source);
        visited.add(source);

        while (!queue.isEmpty()) {
            Vertex u = queue.poll();

            for (Vertex v : capacity.get(u).keySet()) {
                if (!visited.contains(v) && capacity.get(u).get(v) > 0) {
                    queue.add(v);
                    visited.add(v);
                    parent.put(v, u);
                    if (v.equals(sink)) {
                        return parent;
                    }
                }
            }
        }
        return null;
    }
}


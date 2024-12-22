package model.algorithms;

import model.*;
import java.util.*;

public class Dijkstra {

    public static Map<Vertex, Double> dijkstra(Graph graph, Vertex start) {
        Map<Vertex, Double> distances = new HashMap<>();
        PriorityQueue<VertexDistance> pq = new PriorityQueue<>(Comparator.comparingDouble(VertexDistance::getDistance));

        for (Vertex vertex : graph.getVertices()) {
            distances.put(vertex, Double.POSITIVE_INFINITY);
        }
        distances.put(start, 0.0);
        pq.add(new VertexDistance(start, 0.0));

        Set<Vertex> visited = new HashSet<>();

        while (!pq.isEmpty()) {
            VertexDistance current = pq.poll();
            Vertex currentVertex = current.getVertex();

            if (visited.contains(currentVertex)) continue;

            visited.add(currentVertex);

            Set<Vertex> neighbors = graph.getNeighbors(currentVertex);
            for (Vertex neighbor : neighbors) {
                double newDist = distances.get(currentVertex) + getEdgeWeight(graph, currentVertex, neighbor);
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    pq.add(new VertexDistance(neighbor, newDist));
                }
            }
        }

        return distances;
    }

    private static double getEdgeWeight(Graph graph, Vertex from, Vertex to) {
        for (Edge edge : graph.getEdges()) {
            if (edge.getFrom().equals(from) && edge.getTo().equals(to)) {
                return edge.getWeight();
            }
        }
        return Double.POSITIVE_INFINITY;
    }

    private static class VertexDistance {
        private final Vertex vertex;
        private final double distance;

        public VertexDistance(Vertex vertex, double distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        public Vertex getVertex() {
            return vertex;
        }

        public double getDistance() {
            return distance;
        }
    }
}

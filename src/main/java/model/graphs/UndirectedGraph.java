package model.graphs;

import model.Edge;
import model.Graph;
import model.Vertex;
import java.util.HashSet;
import java.util.Set;

public class UndirectedGraph extends Graph {
    public UndirectedGraph() {
        super();
    }

    @Override
    public Set<Vertex> getNeighbors(Vertex vertex) {
        Set<Vertex> neighbors = new HashSet<>();
        for (Edge edge : edges) {
            if (edge.getFrom().equals(vertex)) {
                neighbors.add(edge.getTo());
            } else if (edge.getTo().equals(vertex)) {
                neighbors.add(edge.getFrom());
            }
        }
        return neighbors;
    }

    @Override
    public void printGraph() {
        System.out.println("Undirected Graph:");
        for (Edge edge : edges) {
            System.out.println(edge);
        }
    }
}

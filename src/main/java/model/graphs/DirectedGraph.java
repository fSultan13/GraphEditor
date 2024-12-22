package model.graphs;

import model.Edge;
import model.Graph;
import model.Vertex;
import java.util.HashSet;
import java.util.Set;

public class DirectedGraph extends Graph {
    public DirectedGraph() {
        super();
    }

    @Override
    public Set<Vertex> getNeighbors(Vertex vertex) {
        Set<Vertex> neighbors = new HashSet<>();
        for (Edge edge : edges) {
            if (edge.getFrom().equals(vertex)) {
                neighbors.add(edge.getTo());
            }
        }
        return neighbors;
    }
    @Override
    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    @Override
    public void printGraph() {
        System.out.println("Directed Graph:");
        for (Edge edge : edges) {
            System.out.println(edge);
        }
    }
}

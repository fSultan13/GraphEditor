package model;

import java.util.HashSet;
import java.util.Set;

public abstract class Graph {
    protected Set<Vertex> vertices;
    protected Set<Edge> edges;

    public Graph() {
        this.vertices = new HashSet<>();
        this.edges = new HashSet<>();
    }

    public void addVertex(Vertex vertex) {
        vertices.add(vertex);
    }

    public void removeVertex(Vertex vertex) {
        vertices.remove(vertex);
        edges.removeIf(edge -> edge.getFrom().equals(vertex) || edge.getTo().equals(vertex));
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
        edges.add(new Edge(edge.getTo(), edge.getFrom(), edge.getWeight()));

    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
        edges.remove(new Edge(edge.getTo(), edge.getFrom(), edge.getWeight()));
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    public Set<Vertex> getVertices() {
        return vertices;
    }

    public boolean containsVertex(Vertex vertex) {
        return vertices.contains(vertex);
    }

    public boolean containsEdge(Edge edge) {
        return edges.contains(edge);
    }

    public abstract Set<Vertex> getNeighbors(Vertex vertex);

    public abstract void printGraph();
}

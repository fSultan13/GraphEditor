package model;

import java.util.Objects;

public class Edge {
    private Vertex from;
    private Vertex to;
    private double weight;

    public Edge(Vertex from, Vertex to, double weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Edge(Vertex from, Vertex to) {
        this(from, to, 0.0);
    }

    public Vertex getFrom() {
        return from;
    }

    public Vertex getTo() {
        return to;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


    @Override
    public String toString() {
        if (weight != 0.0) {
            return from + " -> " + to + " (Вес: " + weight + ")";
        } else {
            return from + " -> " + to;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        return Double.compare(edge.weight, weight) == 0 &&
                Objects.equals(from, edge.from) &&
                Objects.equals(to, edge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, weight);
    }
}

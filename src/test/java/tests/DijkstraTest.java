package tests;

import model.*;
import model.graphs.*;
import model.algorithms.Dijkstra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

class DijkstraTest {

    private Vertex A, B, C, D, E;
    private Graph graph;

    @BeforeEach
    void setUp() {
        A = new Vertex("A");
        B = new Vertex("B");
        C = new Vertex("C");
        D = new Vertex("D");
        E = new Vertex("E");

        graph = new DirectedGraph();

        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addVertex(D);
        graph.addVertex(E);

        graph.addEdge(new Edge(A, B, 1.0));
        graph.addEdge(new Edge(B, C, 2.0));
        graph.addEdge(new Edge(A, D, 4.0));
        graph.addEdge(new Edge(D, E, 1.0));
        graph.addEdge(new Edge(E, C, 3.0));
    }

    @Test
    void testDijkstra() {
        Map<Vertex, Double> distances = Dijkstra.dijkstra(graph, A);

        assertEquals(0.0, distances.get(A));
        assertEquals(1.0, distances.get(B));
        assertEquals(3.0, distances.get(C));
        assertEquals(4.0, distances.get(D));
        assertEquals(5.0, distances.get(E));
    }
}


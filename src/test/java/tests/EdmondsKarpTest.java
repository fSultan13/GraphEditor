package tests;

import model.*;
import model.graphs.*;
import model.algorithms.EdmondsKarp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdmondsKarpTest {

    private Vertex s, t, a, b, c;
    private Graph graph;

    @BeforeEach
    void setUp() {
        s = new Vertex("S");
        t = new Vertex("T");
        a = new Vertex("A");
        b = new Vertex("B");
        c = new Vertex("C");

        graph = new DirectedGraph();

        graph.addVertex(s);
        graph.addVertex(t);
        graph.addVertex(a);
        graph.addVertex(b);
        graph.addVertex(c);

        graph.addEdge(new Edge(s, a, 10));
        graph.addEdge(new Edge(s, b, 5));
        graph.addEdge(new Edge(a, t, 10));
        graph.addEdge(new Edge(b, a, 15));
        graph.addEdge(new Edge(b, c, 10));
        graph.addEdge(new Edge(c, t, 10));
    }

    @Test
    void testEdmondsKarp() {
        double maxFlow = EdmondsKarp.edmondsKarp(graph, s, t);

        assertEquals(15.0, maxFlow);
    }
}


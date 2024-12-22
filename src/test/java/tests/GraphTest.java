package tests;


import model.*;
import model.graphs.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

class GraphTest {

    private Vertex v1, v2, v3;
    private Edge e1, e2, e3;

    @BeforeEach
    void setUp() {
        // Create vertices
        v1 = new Vertex("A");
        v2 = new Vertex("B");
        v3 = new Vertex("C");

        // Create edges
        e1 = new Edge(v1, v2, 5.0);
        e2 = new Edge(v2, v3, 10.0);
        e3 = new Edge(v1, v3);
    }

    @Test
    void testDirectedGraph() {
        Graph directedGraph = new DirectedGraph();

        directedGraph.addVertex(v1);
        directedGraph.addVertex(v2);
        directedGraph.addVertex(v3);

        directedGraph.addEdge(e1);
        directedGraph.addEdge(e2);

        assertTrue(directedGraph.containsVertex(v1));
        assertTrue(directedGraph.containsVertex(v2));
        assertTrue(directedGraph.containsVertex(v3));
        assertTrue(directedGraph.containsEdge(e1));
        assertTrue(directedGraph.containsEdge(e2));

        Set<Vertex> neighborsV1 = directedGraph.getNeighbors(v1);
        assertTrue(neighborsV1.contains(v2));
        assertFalse(neighborsV1.contains(v3));

        directedGraph.removeEdge(e1);
        assertFalse(directedGraph.containsEdge(e1));

        directedGraph.removeVertex(v1);
        assertFalse(directedGraph.containsVertex(v1));
    }

    @Test
    void testUndirectedGraph() {
        Graph undirectedGraph = new UndirectedGraph();

        undirectedGraph.addVertex(v1);
        undirectedGraph.addVertex(v2);
        undirectedGraph.addVertex(v3);

        undirectedGraph.addEdge(e3);

        assertTrue(undirectedGraph.containsVertex(v1));
        assertTrue(undirectedGraph.containsVertex(v2));
        assertTrue(undirectedGraph.containsVertex(v3));
        assertTrue(undirectedGraph.containsEdge(e3));

        Set<Vertex> neighborsV1 = undirectedGraph.getNeighbors(v1);
        assertFalse(neighborsV1.contains(v2));
        assertTrue(neighborsV1.contains(v3));

        undirectedGraph.removeEdge(e3);
        assertFalse(undirectedGraph.containsEdge(e3));

        undirectedGraph.removeVertex(v1);
        assertFalse(undirectedGraph.containsVertex(v1));
    }
}

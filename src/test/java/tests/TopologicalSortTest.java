package tests;

import model.*;
import model.graphs.*;
import model.algorithms.TopologicalSort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class TopologicalSortTest {

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

        graph.addEdge(new Edge(A, B));
        graph.addEdge(new Edge(A, D));
        graph.addEdge(new Edge(B, C));
        graph.addEdge(new Edge(D, E));
        graph.addEdge(new Edge(C, E));
    }

    @Test
    void testTopologicalSort() {
        List<Vertex> sorted = TopologicalSort.topologicalSort(graph);

        assertTrue(sorted.contains(A));
        assertTrue(sorted.contains(B));
        assertTrue(sorted.contains(C));
        assertTrue(sorted.contains(D));
        assertTrue(sorted.contains(E));

        int idxA = sorted.indexOf(A);
        int idxB = sorted.indexOf(B);
        int idxC = sorted.indexOf(C);
        int idxD = sorted.indexOf(D);
        int idxE = sorted.indexOf(E);

        assertTrue(idxA < idxB);
        assertTrue(idxB < idxC);
        assertTrue(idxA < idxD);
        assertTrue(idxD < idxE);
        assertTrue(idxC < idxE);
    }

    @Test
    void testGraphWithCycle() {
        graph.addEdge(new Edge(C, A));

        assertThrows(IllegalStateException.class, () -> {
            TopologicalSort.topologicalSort(graph);
        });
    }
}

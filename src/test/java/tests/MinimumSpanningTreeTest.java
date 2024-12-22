package tests;

import model.*;
import model.graphs.*;
import model.algorithms.MinimumSpanningTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

class MinimumSpanningTreeTest {

    private Vertex A, B, C, D;
    private Graph graph;

    @BeforeEach
    void setUp() {
        A = new Vertex("A");
        B = new Vertex("B");
        C = new Vertex("C");
        D = new Vertex("D");

        graph = new UndirectedGraph();
        graph.addVertex(A);
        graph.addVertex(B);
        graph.addVertex(C);
        graph.addVertex(D);

        graph.addEdge(new Edge(A, B, 1.0));
        graph.addEdge(new Edge(A, C, 3.0));
        graph.addEdge(new Edge(B, C, 2.0));
        graph.addEdge(new Edge(B, D, 4.0));
        graph.addEdge(new Edge(C, D, 4.0));
    }

    @Test
    void testKruskalMST() {
        Set<Edge> mst = MinimumSpanningTree.kruskalMST(graph);

        assertEquals(3, mst.size());

        assertTrue(mst.contains(new Edge(A, B, 1.0)));
        assertTrue(mst.contains(new Edge(B, C, 2.0)));
    }

    @Test
    void testKruskalMSTWithCycle() {
        graph.addEdge(new Edge(C, A, 3.0));

        Set<Edge> mst = MinimumSpanningTree.kruskalMST(graph);

        assertEquals(3, mst.size());

        assertFalse(mst.contains(new Edge(C, A, 3.0)));
    }

    @Test
    void testKruskalMSTSingleVertex() {
        Graph singleVertexGraph = new UndirectedGraph();
        Vertex single = new Vertex("Single");
        singleVertexGraph.addVertex(single);

        Set<Edge> mst = MinimumSpanningTree.kruskalMST(singleVertexGraph);

        assertTrue(mst.isEmpty());
    }

    @Test
    void testKruskalMSTDisconnectedGraph() {
        Vertex E = new Vertex("E");
        graph.addVertex(E);
        graph.addEdge(new Edge(E, E));

        Set<Edge> mst = MinimumSpanningTree.kruskalMST(graph);

        assertEquals(3, mst.size());
    }
}


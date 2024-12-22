package tests;

import model.*;
import model.graphs.*;
import model.algorithms.BFS;
import model.algorithms.DFS;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class BfsDfsTest {

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
        graph.addEdge(new Edge(B, C));
        graph.addEdge(new Edge(A, D));
        graph.addEdge(new Edge(D, E));
        graph.addEdge(new Edge(B, E));
    }

    @Test
    void testBFS() {
        Set<Vertex> visited = BFS.bfs(graph, A);

        assertTrue(visited.contains(A));
        assertTrue(visited.contains(B));
        assertTrue(visited.contains(D));
        assertTrue(visited.contains(C));
        assertTrue(visited.contains(E));
    }

    @Test
    void testDFS() {
        Set<Vertex> visited = DFS.dfs(graph, A);

        assertTrue(visited.contains(A));
        assertTrue(visited.contains(B));
        assertTrue(visited.contains(E));
        assertTrue(visited.contains(D));
        assertTrue(visited.contains(C));
    }

    @Test
    void testEmptyGraph() {
        Graph emptyGraph = new DirectedGraph();

        Set<Vertex> visitedBFS = BFS.bfs(emptyGraph, null);
        Set<Vertex> visitedDFS = DFS.dfs(emptyGraph, null);

        assertTrue(visitedBFS.isEmpty());
        assertTrue(visitedDFS.isEmpty());
    }

    @Test
    void testSingleVertexGraph() {
        Graph singleVertexGraph = new DirectedGraph();
        Vertex singleVertex = new Vertex("Single");
        singleVertexGraph.addVertex(singleVertex);

        Set<Vertex> visitedBFS = BFS.bfs(singleVertexGraph, singleVertex);
        Set<Vertex> visitedDFS = DFS.dfs(singleVertexGraph, singleVertex);

        assertEquals(1, visitedBFS.size());
        assertEquals(1, visitedDFS.size());
        assertTrue(visitedBFS.contains(singleVertex));
        assertTrue(visitedDFS.contains(singleVertex));
    }
}


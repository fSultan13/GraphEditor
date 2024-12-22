package tests;

import model.Vertex;
import model.Edge;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EdgeTest {

    @Test
    public void testEdgeCreationDirected() {
        Vertex from = new Vertex("A");
        Vertex to = new Vertex("B");
        Edge edge = new Edge(from, to, 10.0);

        assertEquals(from, edge.getFrom());
        assertEquals(to, edge.getTo());
        assertEquals(10.0, edge.getWeight());
    }

    @Test
    public void testEdgeCreationUndirected() {
        Vertex from = new Vertex("A");
        Vertex to = new Vertex("B");
        Edge edge = new Edge(from, to, 5.0);

        assertEquals(from, edge.getFrom());
        assertEquals(to, edge.getTo());
        assertEquals(5.0, edge.getWeight());
    }

    @Test
    public void testEdgeWeight() {
        Vertex from = new Vertex("A");
        Vertex to = new Vertex("B");
        Edge edge = new Edge(from, to, 15.0);

        assertEquals(15.0, edge.getWeight());
        edge.setWeight(20.0);
        assertEquals(20.0, edge.getWeight());
    }

    @Test
    public void testEdgeToString() {
        Vertex from = new Vertex("A");
        Vertex to = new Vertex("B");
        Edge edge = new Edge(from, to, 10.0);

        String expected = "A -> B (Вес: 10.0)";
        assertEquals(expected, edge.toString());
    }

    @Test
    public void testEdgeEquality() {
        Vertex from = new Vertex("A");
        Vertex to = new Vertex("B");
        Edge edge1 = new Edge(from, to, 10.0);
        Edge edge2 = new Edge(from, to, 10.0);
        Edge edge3 = new Edge(from, to, 15.0);

        assertEquals(edge1, edge2);
        assertNotEquals(edge1, edge3);
    }
}

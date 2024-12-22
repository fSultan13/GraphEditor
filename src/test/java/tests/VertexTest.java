package tests;

import model.Vertex;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class VertexTest {

    @Test
    public void testVertexCreationWithLabel() {
        Vertex vertex = new Vertex("A");
        assertEquals("A", vertex.getLabel());
        assertNotNull(vertex.getId());  // ID должен быть уникальным
    }

    @Test
    public void testVertexCreationWithoutLabel() {
        Vertex vertex = new Vertex();
        assertNotNull(vertex.getLabel());  // Вершина должна иметь метку по умолчанию
        assertNotNull(vertex.getId());    // ID должен быть уникальным
    }

    @Test
    public void testVertexEquality() {
        Vertex vertex1 = new Vertex("A");
        Vertex vertex2 = new Vertex("A");
        Vertex vertex3 = vertex1;

        assertEquals(vertex1, vertex3);  // Они должны быть одинаковыми, так как это одна и та же вершина
        assertNotEquals(vertex1, vertex2);  // Разные объекты с одинаковыми метками
    }

    @Test
    public void testVertexEquals() {
        Vertex vertex1 = new Vertex("A");
        Vertex vertex2 = new Vertex("A");
        Vertex vertex3 = new Vertex("B");

        assertNotEquals(vertex1, vertex3);
    }

    @Test
    public void testToString() {
        Vertex vertex = new Vertex("A");
        String expected = "A";
        assertEquals(expected, vertex.toString());
    }
}

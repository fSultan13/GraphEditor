package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.GraphWriter;
import io.GraphWriter.SerializableGraph;
import io.GraphWriter.SerializableVertex;
import io.GraphWriter.SerializableEdge;
import model.Edge;
import model.Vertex;
import model.graphs.DirectedGraph;
import model.graphs.UndirectedGraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.Set;

public class GraphWriterTest {

    @Test
    void testSaveDirectedGraph(@TempDir Path tempDir) throws Exception {
        DirectedGraph directedGraph = new DirectedGraph();
        Vertex v1 = new Vertex("A");
        Vertex v2 = new Vertex("B");
        directedGraph.addVertex(v1);
        directedGraph.addVertex(v2);
        directedGraph.addEdge(new Edge(v1, v2, 1.5));

        File outputFile = tempDir.resolve("D:\\programming\\repos\\Java\\GraphEditor\\GraphEditor\\src\\main\\resources\\directed_graph.json").toFile();

        GraphWriter writer = new GraphWriter();
        writer.saveGraph(directedGraph, outputFile.getAbsolutePath());

        ObjectMapper mapper = new ObjectMapper();
        SerializableGraph sGraph = mapper.readValue(outputFile, SerializableGraph.class);

        Assertions.assertEquals("DIRECTED", sGraph.getGraphType());

        Assertions.assertNotNull(sGraph.getVertices());
        Assertions.assertEquals(2, sGraph.getVertices().size());
        SerializableVertex sv1 = sGraph.getVertices().get(0);
        SerializableVertex sv2 = sGraph.getVertices().get(1);
        Assertions.assertTrue((sv1.getLabel().equals("A") && sv2.getLabel().equals("B")) ||
                (sv1.getLabel().equals("B") && sv2.getLabel().equals("A")));

        Assertions.assertNotNull(sGraph.getEdges());
        Assertions.assertEquals(1, sGraph.getEdges().size());
        SerializableEdge se = sGraph.getEdges().get(0);
        Assertions.assertEquals(v1.getId(), se.getFromId());
        Assertions.assertEquals(v2.getId(), se.getToId());
        Assertions.assertEquals(1.5, se.getWeight());
    }

    @Test
    void testSaveUndirectedGraph(@TempDir Path tempDir) throws Exception {
        UndirectedGraph undirectedGraph = new UndirectedGraph();
        Vertex v1 = new Vertex("X");
        Vertex v2 = new Vertex("Y");
        undirectedGraph.addVertex(v1);
        undirectedGraph.addVertex(v2);
        undirectedGraph.addEdge(new Edge(v1, v2, 2.0));

        File outputFile = tempDir.resolve("D:\\programming\\repos\\Java\\GraphEditor\\GraphEditor\\src\\main\\resources\\undirected_graph.json").toFile();

        GraphWriter writer = new GraphWriter();
        writer.saveGraph(undirectedGraph, outputFile.getAbsolutePath());

        ObjectMapper mapper = new ObjectMapper();
        GraphWriter.SerializableGraph sGraph = mapper.readValue(outputFile, GraphWriter.SerializableGraph.class);

        Assertions.assertEquals("UNDIRECTED", sGraph.getGraphType());

        Assertions.assertNotNull(sGraph.getVertices());
        Assertions.assertEquals(2, sGraph.getVertices().size());
        Set<String> labels = Set.of(sGraph.getVertices().get(0).getLabel(), sGraph.getVertices().get(1).getLabel());
        Assertions.assertTrue(labels.contains("X"));
        Assertions.assertTrue(labels.contains("Y"));

        Assertions.assertNotNull(sGraph.getEdges());
        Assertions.assertEquals(2, sGraph.getEdges().size());
        GraphWriter.SerializableEdge se = sGraph.getEdges().get(0);
        Assertions.assertTrue((se.getFromId() == v1.getId() && se.getToId() == v2.getId()) ||
                (se.getFromId() == v2.getId() && se.getToId() == v1.getId()));
        Assertions.assertEquals(2.0, se.getWeight());
    }
}

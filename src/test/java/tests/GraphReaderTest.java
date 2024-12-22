package tests;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.GraphReader;
import io.GraphWriter;
import model.Edge;
import model.Graph;
import model.Vertex;
import model.graphs.DirectedGraph;
import model.graphs.UndirectedGraph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

public class GraphReaderTest {

    @Test
    void testLoadDirectedGraph(@TempDir Path tempDir) throws Exception {
        DirectedGraph originalGraph = new DirectedGraph();
        Vertex vA = new Vertex("A");
        Vertex vB = new Vertex("B");
        originalGraph.addVertex(vA);
        originalGraph.addVertex(vB);
        originalGraph.addEdge(new Edge(vA, vB, 1.5));

        File outputFile = tempDir.resolve("directed_graph.json").toFile();
        GraphWriter writer = new GraphWriter();
        writer.saveGraph(originalGraph, outputFile.getAbsolutePath());

        GraphReader reader = new GraphReader();
        Graph loadedGraph = reader.loadGraph(outputFile.getAbsolutePath());

        Assertions.assertTrue(loadedGraph instanceof DirectedGraph, "Loaded graph should be directed");

        Set<String> originalLabels = originalGraph.getVertices().stream().map(Vertex::getLabel).collect(Collectors.toSet());
        Set<String> loadedLabels = loadedGraph.getVertices().stream().map(Vertex::getLabel).collect(Collectors.toSet());
        Assertions.assertEquals(originalLabels, loadedLabels, "Vertex labels should match");

        Set<String> originalEdges = originalGraph.getEdges()
                .stream()
                .map(e -> e.getFrom().getLabel() + "->" + e.getTo().getLabel() + ":" + e.getWeight())
                .collect(Collectors.toSet());

        Set<String> loadedEdges = loadedGraph.getEdges()
                .stream()
                .map(e -> e.getFrom().getLabel() + "->" + e.getTo().getLabel() + ":" + e.getWeight())
                .collect(Collectors.toSet());

        Assertions.assertEquals(originalEdges, loadedEdges, "Edges should match in direction and weight");
    }

    @Test
    void testLoadUndirectedGraph(@TempDir Path tempDir) throws Exception {
        UndirectedGraph originalGraph = new UndirectedGraph();
        Vertex vX = new Vertex("X");
        Vertex vY = new Vertex("Y");
        originalGraph.addVertex(vX);
        originalGraph.addVertex(vY);
        originalGraph.addEdge(new Edge(vX, vY, 2.0));

        File outputFile = tempDir.resolve("undirected_graph.json").toFile();
        GraphWriter writer = new GraphWriter();
        writer.saveGraph(originalGraph, outputFile.getAbsolutePath());

        GraphReader reader = new GraphReader();
        Graph loadedGraph = reader.loadGraph(outputFile.getAbsolutePath());

        Assertions.assertTrue(loadedGraph instanceof UndirectedGraph, "Loaded graph should be undirected");

        Set<String> originalLabels = originalGraph.getVertices().stream().map(Vertex::getLabel).collect(Collectors.toSet());
        Set<String> loadedLabels = loadedGraph.getVertices().stream().map(Vertex::getLabel).collect(Collectors.toSet());
        Assertions.assertEquals(originalLabels, loadedLabels, "Vertex labels should match");

        Set<String> originalEdges = originalGraph.getEdges().stream()
                .map(e -> edgeToNormalizedString(e))
                .collect(Collectors.toSet());

        Set<String> loadedEdges = loadedGraph.getEdges().stream()
                .map(e -> edgeToNormalizedString(e))
                .collect(Collectors.toSet());

        Assertions.assertEquals(originalEdges, loadedEdges, "Edges should match ignoring direction");
    }

    private String edgeToNormalizedString(Edge e) {
        String l1 = e.getFrom().getLabel();
        String l2 = e.getTo().getLabel();
        if (l1.compareTo(l2) > 0) {
            String tmp = l1;
            l1 = l2;
            l2 = tmp;
        }
        return l1 + "<->" + l2 + ":" + e.getWeight();
    }
}

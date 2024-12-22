package io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import model.Edge;
import model.Graph;
import model.Vertex;
import model.graphs.DirectedGraph;
import model.graphs.UndirectedGraph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphWriter {

    public static class SerializableGraph {
        private String graphType;
        private List<SerializableVertex> vertices;
        private List<SerializableEdge> edges;

        public String getGraphType() {
            return graphType;
        }

        public void setGraphType(String graphType) {
            this.graphType = graphType;
        }

        public List<SerializableVertex> getVertices() {
            return vertices;
        }

        public void setVertices(List<SerializableVertex> vertices) {
            this.vertices = vertices;
        }

        public List<SerializableEdge> getEdges() {
            return edges;
        }

        public void setEdges(List<SerializableEdge> edges) {
            this.edges = edges;
        }
    }

    public static class SerializableVertex {
        private int id;
        private String label;

        public SerializableVertex() {}

        public SerializableVertex(int id, String label) {
            this.id = id;
            this.label = label;
        }

        public int getId() { return id; }
        public String getLabel() { return label; }

        public void setId(int id) { this.id = id; }
        public void setLabel(String label) { this.label = label; }
    }

    public static class SerializableEdge {
        private int fromId;
        private int toId;
        private double weight;

        public SerializableEdge() {}

        public SerializableEdge(int fromId, int toId, double weight) {
            this.fromId = fromId;
            this.toId = toId;
            this.weight = weight;
        }

        public int getFromId() { return fromId; }
        public int getToId() { return toId; }
        public double getWeight() { return weight; }

        public void setFromId(int fromId) { this.fromId = fromId; }
        public void setToId(int toId) { this.toId = toId; }
        public void setWeight(double weight) { this.weight = weight; }
    }

    public void saveGraph(Graph graph, String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String graphType;
        if (graph instanceof DirectedGraph) {
            graphType = "DIRECTED";
        } else if (graph instanceof UndirectedGraph) {
            graphType = "UNDIRECTED";
        } else {
            graphType = "UNKNOWN";
        }

        SerializableGraph sGraph = new SerializableGraph();
        sGraph.setGraphType(graphType);

        List<SerializableVertex> sVertices = new ArrayList<>();
        for (Vertex v : graph.getVertices()) {
            sVertices.add(new SerializableVertex(v.getId(), v.getLabel()));
        }
        sGraph.setVertices(sVertices);

        List<SerializableEdge> sEdges = new ArrayList<>();
        for (Edge e : graph.getEdges()) {
            sEdges.add(new SerializableEdge(e.getFrom().getId(), e.getTo().getId(), e.getWeight()));
        }
        sGraph.setEdges(sEdges);

        mapper.writeValue(new File(filePath), sGraph);
    }
}


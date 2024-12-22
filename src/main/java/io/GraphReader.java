package io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import model.Edge;
import model.Graph;
import model.Vertex;
import model.graphs.DirectedGraph;
import model.graphs.UndirectedGraph;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraphReader {

    static class SerializableGraph {
        private String graphType;
        private java.util.List<SerializableVertex> vertices;
        private java.util.List<SerializableEdge> edges;

        public String getGraphType() {
            return graphType;
        }

        public void setGraphType(String graphType) {
            this.graphType = graphType;
        }

        public java.util.List<SerializableVertex> getVertices() {
            return vertices;
        }

        public void setVertices(java.util.List<SerializableVertex> vertices) {
            this.vertices = vertices;
        }

        public java.util.List<SerializableEdge> getEdges() {
            return edges;
        }

        public void setEdges(java.util.List<SerializableEdge> edges) {
            this.edges = edges;
        }
    }

    static class SerializableVertex {
        private int id;
        private String label;

        public int getId() { return id; }
        public String getLabel() { return label; }

        public void setId(int id) { this.id = id; }
        public void setLabel(String label) { this.label = label; }
    }

    static class SerializableEdge {
        private int fromId;
        private int toId;
        private double weight;

        public int getFromId() { return fromId; }
        public int getToId() { return toId; }
        public double getWeight() { return weight; }

        public void setFromId(int fromId) { this.fromId = fromId; }
        public void setToId(int toId) { this.toId = toId; }
        public void setWeight(double weight) { this.weight = weight; }
    }

    public Graph loadGraph(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        SerializableGraph sGraph = mapper.readValue(new File(filePath), SerializableGraph.class);

        Graph graph;
        if ("DIRECTED".equalsIgnoreCase(sGraph.getGraphType())) {
            graph = new DirectedGraph();
        } else if ("UNDIRECTED".equalsIgnoreCase(sGraph.getGraphType())) {
            graph = new UndirectedGraph();
        } else {
            throw new IllegalArgumentException("Unknown graph type: " + sGraph.getGraphType());
        }

        Map<Integer, Vertex> vertexMap = new HashMap<>();
        for (SerializableVertex sv : sGraph.getVertices()) {
            Vertex v = new Vertex(sv.getLabel());
            vertexMap.put(sv.getId(), v);
            graph.addVertex(v);
        }

        for (SerializableEdge se : sGraph.getEdges()) {
            Vertex from = vertexMap.get(se.getFromId());
            Vertex to = vertexMap.get(se.getToId());
            if (from == null || to == null) {
                throw new IllegalStateException("Edge references a vertex id that does not exist in the vertex set.");
            }
            Edge e = new Edge(from, to, se.getWeight());
            graph.addEdge(e);
        }

        return graph;
    }
}


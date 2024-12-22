package model.algorithms;

import model.*;
import java.util.*;

public class TopologicalSort {

    public static List<Vertex> topologicalSort(Graph graph) throws IllegalStateException {
        Map<Vertex, Boolean> visited = new HashMap<>();
        Map<Vertex, Boolean> inRecursionStack = new HashMap<>();
        List<Vertex> sortedList = new ArrayList<>();

        for (Vertex vertex : graph.getVertices()) {
            visited.put(vertex, false);
            inRecursionStack.put(vertex, false);
        }

        for (Vertex vertex : graph.getVertices()) {
            if (!visited.get(vertex)) {
                if (!dfs(graph, vertex, visited, inRecursionStack, sortedList)) {
                    throw new IllegalStateException("Graph contains a cycle!");
                }
            }
        }

        Collections.reverse(sortedList);
        return sortedList;
    }

    private static boolean dfs(Graph graph, Vertex vertex, Map<Vertex, Boolean> visited,
                               Map<Vertex, Boolean> inRecursionStack, List<Vertex> sortedList) {
        if (inRecursionStack.get(vertex)) {
            return false;
        }

        if (visited.get(vertex)) {
            return true;
        }

        visited.put(vertex, true);
        inRecursionStack.put(vertex, true);


        for (Vertex neighbor : graph.getNeighbors(vertex)) {
            if (!dfs(graph, neighbor, visited, inRecursionStack, sortedList)) {
                return false;
            }
        }

        inRecursionStack.put(vertex, false);
        sortedList.add(vertex);
        return true;
    }
}


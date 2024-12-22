package model.algorithms;

import model.*;
import java.util.*;

public class BFS {

    public static Set<Vertex> bfs(Graph graph, Vertex start) {
        Set<Vertex> visited = new HashSet<>();
        if (start == null) {
            return visited;
        }

        Queue<Vertex> queue = new LinkedList<>();
        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            Set<Vertex> neighbors = graph.getNeighbors(current);

            for (Vertex neighbor : neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        return visited;
    }
}

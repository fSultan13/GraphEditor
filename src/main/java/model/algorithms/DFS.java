package model.algorithms;

import model.*;
import java.util.*;

public class DFS {

    public static Set<Vertex> dfs(Graph graph, Vertex start) {
        Set<Vertex> visited = new HashSet<>();
        if (start == null) {
            return visited;
        }

        Stack<Vertex> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            Vertex current = stack.pop();
            if (!visited.contains(current)) {
                visited.add(current);
                Set<Vertex> neighbors = graph.getNeighbors(current);

                for (Vertex neighbor : neighbors) {
                    if (!visited.contains(neighbor)) {
                        stack.push(neighbor);
                    }
                }
            }
        }
        return visited;
    }
}


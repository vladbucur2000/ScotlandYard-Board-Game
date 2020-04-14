package uk.ac.bris.cs.scotlandyard.ui.ai;

import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ImmutableValueGraph;
import uk.ac.bris.cs.scotlandyard.model.ScotlandYard;

import java.util.*;

public class OurDijkstra { //it is actually a BFS because the weight on an edge is 1

    public int[] compute(ImmutableValueGraph<Integer, ImmutableSet<ScotlandYard.Transport>> graph, int source) {

        //preparing for calculating the distances
        final int inf = Integer.MAX_VALUE;
        boolean[] visited = new boolean[graph.nodes().size() + 1];
        int[] distances = new int[graph.nodes().size() + 1];

        for (int i = 0; i <= graph.nodes().size(); ++i) {
            distances[i] = inf;
            visited[i] = false;
        }

        distances[source] = 0;
        visited[source] = true;

        Queue <Integer> queue = new LinkedList<>();
        queue.add(source);

        //BFS 
        while (!queue.isEmpty()) {
            int current = queue.peek();

            for (int node : graph.adjacentNodes(current))
                if (!visited[node]) {
                    distances[node] = distances[current] + 1;
                    queue.add(node);
                    visited[node] = true;
                }

            queue.remove();
        }

        return distances;
    }

}

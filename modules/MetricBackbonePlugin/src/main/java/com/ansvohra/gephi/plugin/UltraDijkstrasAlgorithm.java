package com.ansvohra.gephi.plugin;

import org.gephi.algorithms.shortestpath.AbstractShortestPathAlgorithm;
import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class UltraDijkstrasAlgorithm extends AbstractShortestPathAlgorithm {

    protected final Graph graph;
    protected final HashMap<Node, Edge> predecessors;

    public UltraDijkstrasAlgorithm(Graph graph, Node sourceNode) {
        super(sourceNode);
        this.graph = graph;
        predecessors = new HashMap<Node, Edge>();
    }

    @Override
    public void compute() {

        graph.readLock();
        try {
            Set<Node> unsettledNodes = new HashSet<>();
            Set<Node> settledNodes = new HashSet<>();

            //Initialize
            for (Node node : graph.getNodes()) {
                distances.put(node, Double.POSITIVE_INFINITY);
            }
            distances.put(sourceNode, 0d);
            unsettledNodes.add(sourceNode);

            while (!unsettledNodes.isEmpty()) {

                // find node with smallest distance value
                Double minDistance = Double.POSITIVE_INFINITY;
                Node minDistanceNode = null;
                for (Node k : unsettledNodes) {
                    Double dist = distances.get(k);
                    if (minDistanceNode == null) {
                        minDistanceNode = k;
                    }

                    if (dist.compareTo(minDistance) < 0) {
                        minDistance = dist;
                        minDistanceNode = k;
                    }
                }
                unsettledNodes.remove(minDistanceNode);
                settledNodes.add(minDistanceNode);

                for (Edge edge : graph.getEdges(minDistanceNode)) {
                    Node neighbor = graph.getOpposite(minDistanceNode, edge);
                    if (!settledNodes.contains(neighbor)) {
                        double dist = Math.max(getShortestDistance(minDistanceNode),edgeWeight(edge)); //the key difference
                        if (getShortestDistance(neighbor) > dist) {

                            distances.put(neighbor, dist);
                            predecessors.put(neighbor, edge);
                            unsettledNodes.add(neighbor);
                            maxDistance = Math.max(maxDistance, dist);
                        }
                    }
                }
            }
        } finally {
            graph.readUnlockAll();
        }
    }

    private double getShortestDistance(Node destination) {
        Double d = distances.get(destination);
        if (d == null) {
            return Double.POSITIVE_INFINITY;
        } else {
            return d;
        }
    }

    @Override
    public Map<Node, Edge> getPredecessors() {
        return predecessors;
    }
}

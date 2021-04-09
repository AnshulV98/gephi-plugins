package com.ansvohra.gephi.plugin;

import org.gephi.graph.api.*;
import org.gephi.statistics.spi.Statistics;
import org.gephi.algorithms.shortestpath.DijkstraShortestPathAlgorithm;

import java.util.ArrayList;
import java.util.HashMap;

public class MetricBackboneCompute implements Statistics {

    //TODO: Look into Longtask if needed

    public static final String COLUMN_ID = "me";
    public static final String COL_NAME = "Metric-Edge";
    public static final String S_MEASURE_COL_ID = "s_m";
    public static final String S_MEASURE_COL_NAME = "S Measure";
    int totalEdges;
    int backboneEdges;

    public void execute(GraphModel graphModel) {

        Graph graph = graphModel.getGraph();
        graph.writeLock();
        HashMap<Node,HashMap<Node, Double>> final_distances = new HashMap<Node, HashMap<Node, Double>>();

        Table edgeTable = graphModel.getEdgeTable();
        Column edgeCol = edgeTable.getColumn(COLUMN_ID);
        Column s_Measure_Col = edgeTable.getColumn("s_m");

        if (edgeCol == null)
            edgeTable.addColumn(COLUMN_ID, COL_NAME, Boolean.class, false);

        if(s_Measure_Col == null)
            edgeTable.addColumn(S_MEASURE_COL_ID, S_MEASURE_COL_NAME, Double.class, 0d);

        try {
            //all_nodes_shortest_path_computation
            for (Node node : graph.getNodes()) {
                DijkstraShortestPathAlgorithm algo = new DijkstraShortestPathAlgorithm(graph, node);
                algo.compute();
                HashMap<Node, Double> distances = algo.getDistances();
                final_distances.put(node, distances);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            graph.writeUnlock();
        }

            //checking if edge is part of the metric backbone or not
            for (Edge edge : graph.getEdges()) {
                Node source = edge.getSource();
                Node target = edge.getTarget();
                double original_weight = edge.getWeight();
                double final_distance = final_distances.get(source).get(target);
                edge.setAttribute(S_MEASURE_COL_ID, original_weight/final_distance);
                if (final_distance == original_weight) {
                    edge.setAttribute(COLUMN_ID, true);
                    backboneEdges++;
                }
                totalEdges++;
            }
    }

    public String getReport() {
        return "Out of total no. of edges: "
                + this.totalEdges
                + "<br/> The no. of edges part of the backbone are: "
                + this.backboneEdges
                + "<br/> Every edge in the backbone now has an attribute \"" + COL_NAME + "\" set equal to \"Checked\"."
                + "<br/> The backbone can be visualized by creating a Filter on the Attribute \"" + COL_NAME + "\" with value = \"Checked\""
                +"<br/> An attribute for the s-measure is added with the name \""+ S_MEASURE_COL_NAME +"\" so that can be used as a filter as well";
    }
}

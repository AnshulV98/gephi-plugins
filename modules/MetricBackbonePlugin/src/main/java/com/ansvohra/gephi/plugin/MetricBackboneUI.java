package com.ansvohra.gephi.plugin;

import org.gephi.statistics.spi.Statistics;
import org.gephi.statistics.spi.StatisticsUI;
import org.openide.util.lookup.ServiceProvider;

import javax.swing.*;

@ServiceProvider(service = StatisticsUI.class)
public class MetricBackboneUI implements StatisticsUI{

    private MetricBackboneCompute myStat;
    private MetricBackbonePanel MetricPanel;

    public MetricBackbonePanel getSettingsPanel() {
        //look into JPanel logic and possible implementation of two logics for Metric and Ultrametric computation
        MetricPanel = new MetricBackbonePanel();
        return MetricPanel;
    }

    public void setup(Statistics statistics) {
        this.myStat = (MetricBackboneCompute) statistics;
    }

    public void unsetup() {
        MetricPanel = null;
    }

    public Class<? extends Statistics> getStatisticsClass() {
        return MetricBackboneCompute.class;
    }

    public String getValue() {
        //empty for now
        return null;
    }

    public String getDisplayName() {
        return "Metric Backbone Computer";
    }

    public String getShortDescription() {
        return "Metric Backbone Computer";
    }

    public String getCategory() {
        return StatisticsUI.CATEGORY_NETWORK_OVERVIEW;
    }

    public int getPosition() {
        return 1;
    }
}

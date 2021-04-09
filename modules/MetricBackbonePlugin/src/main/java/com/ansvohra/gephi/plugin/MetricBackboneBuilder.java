package com.ansvohra.gephi.plugin;

import org.gephi.statistics.spi.Statistics;
import org.gephi.statistics.spi.StatisticsBuilder;
import org.openide.util.lookup.ServiceProvider;


@ServiceProvider(service = StatisticsBuilder.class)
public class MetricBackboneBuilder implements StatisticsBuilder{

    public String getName() {
        return "Metric Backbone Computer";
    }

    public Statistics getStatistics() {
        return new MetricBackboneCompute();
    }

    public Class<? extends Statistics> getStatisticsClass() {
        return MetricBackboneCompute.class;
    }

}

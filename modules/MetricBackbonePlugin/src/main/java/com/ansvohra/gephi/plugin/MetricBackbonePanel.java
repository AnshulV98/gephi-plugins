package com.ansvohra.gephi.plugin;

import javax.swing.*;

public class MetricBackbonePanel extends JPanel {

    private JCheckBox metric = new JCheckBox(" Compute metric backbone");
    private JCheckBox ultra = new JCheckBox("Compute ultraMetric backbone");

    public MetricBackbonePanel(){
        initComponents();
    }

    public boolean computeMetric(){
        return metric.isSelected();
    }

    public boolean computeUltraMetric(){
        return ultra.isSelected();
    }

    private void initComponents(){
        this.add(metric);
        this.add(ultra);
        metric.setSelected(true);
        ultra.setSelected(false);
    }

}

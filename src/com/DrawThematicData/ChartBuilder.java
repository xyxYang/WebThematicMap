package com.DrawThematicData;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

public class ChartBuilder {
	public static ChartBuilder instance = new ChartBuilder();
	
	private ChartBuilder(){
		
	}
	
	public static JFreeChart build(Map<String, Double> columnValues, List<String>rows, ChartInfo info){
		
	}
	
	private static JFreeChart buildBarChart(Map<String, Double> columnValues, List<String>rows, ChartInfo info){
		
	}
	
	private static JFreeChart buildLineChart(Map<String, Double> columnValues, List<String>rows, ChartInfo info){
		CategoryDataset dataset = getCategoryDataset(columnValues, rows);
		JFreeChart chart = ChartFactory.createLineChart(title, categoryAxisLabel, valueAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, false);
		return setChart(chart, info);
	}
	
	private static JFreeChart buildPieChart(Map<String, Double> columnValues, ChartInfo info){
		
	}
	
	private static CategoryDataset getCategoryDataset(Map<String, Double> columnValues, List<String>rows){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(Entry<String, Double> entry:columnValues.entrySet()){
			for(String row:rows){
				dataset.addValue(entry.getValue(), row, entry.getKey());
			}
		}
		return dataset;
	}
	
	private static JFreeChart setChart(JFreeChart chart, ChartInfo info){
		return chart;
	}
}

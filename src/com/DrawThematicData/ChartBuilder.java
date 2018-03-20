package com.DrawThematicData;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class ChartBuilder {
	public static ChartBuilder instance = new ChartBuilder();
	
	private ChartBuilder(){
		
	}
	
	public static JFreeChart build(Map<String, Double> columnValues, List<String>rows, ChartInfo info){
		
	}
	
	private static JFreeChart buildBarChart(Map<String, Double> columnValues, List<String>rows, ChartInfo info){
		CategoryDataset dataset = getCategoryDataset(columnValues, rows);
		JFreeChart chart = ChartFactory.createBaeChart(info.title, info.categoryAxisLabel, info.valueAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, false);
		return setChart(chart, info);
	}
	
	private static JFreeChart buildLineChart(Map<String, Double> columnValues, List<String>rows, ChartInfo info){
		CategoryDataset dataset = getCategoryDataset(columnValues, rows);
		JFreeChart chart = ChartFactory.createLineChart(info.title, info.categoryAxisLabel, info.valueAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, false);
		return setChart(chart, info);
	}
	
	private static JFreeChart buildPieChart(Map<String, Double> columnValues, ChartInfo info){
		PieDataset dataset = getPieDataset(columnValues);
		JFreeChart chart = ChartFactory.createPieChart(info.title, info.categoryAxisLabel, info.valueAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, false);
		return setChart(chart, info);
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
	
	private static PieDataset getPieDataset(Map<String, Double> columnValues) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		int i=0;
		for(Entry<String, Double> entry:columnValues.entrySet()) {
			dataset.insertValue(i, entry.getKey(), entry.getValue());
			++i;
		}
		return dataset;
	}
	
	private static JFreeChart setChart(JFreeChart chart, ChartInfo info){
		return chart;
	}
}

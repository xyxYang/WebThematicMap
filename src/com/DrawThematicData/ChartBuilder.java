package com.DrawThematicData;

import java.util.ArrayList;
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
	
	public static JFreeChart build(List<String>keys, List<Double>values, List<String>rows, ChartInfo info){
		if(rows == null) {
			rows = new ArrayList<String>();
			rows.add("");
		}
		if(keys == null || values == null) {
			return null;
		}
		
		switch(info.type) {
		case Line:return buildLineChart(keys, values, rows, info);
		case Bar:return buildBarChart(keys, values, rows, info);
		case Pie:return buildPieChart(keys, values, info);
		}
		return null;
	}
	
	private static JFreeChart buildBarChart(List<String>keys, List<Double>values, List<String>rows, ChartInfo info){
		CategoryDataset dataset = getCategoryDataset(keys, values,rows);
		JFreeChart chart = ChartFactory.createBarChart(info.title, info.categoryAxisLabel, info.valueAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, false);
		return setChart(chart, info);
	}
	
	private static JFreeChart buildLineChart(List<String>keys, List<Double>values, List<String>rows, ChartInfo info){
		CategoryDataset dataset = getCategoryDataset(keys, values, rows);
		JFreeChart chart = ChartFactory.createLineChart(info.title, info.categoryAxisLabel, info.valueAxisLabel, dataset, PlotOrientation.VERTICAL, true, true, false);
		return setChart(chart, info);
	}
	
	private static JFreeChart buildPieChart(List<String>keys, List<Double>values, ChartInfo info){
		PieDataset dataset = getPieDataset(keys, values);
		JFreeChart chart = ChartFactory.createPieChart(info.title, dataset, true, true, false);
		return setChart(chart, info);
	}
	
	private static CategoryDataset getCategoryDataset(List<String>keys, List<Double>values, List<String>rows){
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int i=0;
		for(String row:rows){
			for(String key:keys){
				if(i == values.size()) {
					return dataset;
				}
				dataset.addValue(values.get(i), row, key);
				++i;
			}
		}
		return dataset;
	}
	
	private static PieDataset getPieDataset(List<String>keys, List<Double>values) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		int i=0;
		for(String key : keys) {
			dataset.insertValue(i, key, values.get(i));
			++i;
		}
		return dataset;
	}
	
	private static JFreeChart setChart(JFreeChart chart, ChartInfo info){
		//TODO 
		return chart;
	}
}

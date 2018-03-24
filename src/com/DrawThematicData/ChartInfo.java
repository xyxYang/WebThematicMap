package com.DrawThematicData;

public class ChartInfo {

	public String title;
	public String categoryAxisLabel;
	public String valueAxisLabel;
	public ChartType type;
	
	private ChartInfo() {
		
	}
	
	public static ChartInfo createLineInfo(String title, String categoryAxisLabel, String valueAxisLabel) {
		ChartInfo ret = new ChartInfo();
		ret.title = title;
		ret.categoryAxisLabel = categoryAxisLabel;
		ret.valueAxisLabel = valueAxisLabel;
		ret.type = ChartType.Line;
		return ret;
	}
}



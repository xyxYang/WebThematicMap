package com.DataCalculate;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class KindCalculator {
	
	private static double getMax(List<Double> datas){
		double max = datas.get(0);
		for(double data:datas){
			max = Math.max(max, data);
		}
		return max;
	}
	
	private static double getMin(List<Double> datas){
		double min = datas.get(0);
		for(double data:datas){
			min = Math.min(min, data);
		}
		return min;
	}

	public static List<Color> getColors(int num){
		//TODO
		return null;
	}
	
	public static List<KindInfo> EqualDistanceClassification(List<Double> datas, int kindNum, List<Color> colors){
		double min = getMin(datas);
		double max = getMax(datas);
		double distance = (max-min)/kindNum;
		double lower = min;
		double upper = min + distance;
		List<KindInfo> ret = new ArrayList<KindInfo>();
		for(int i=0; i<kindNum; ++i){
			KindInfo kindInfo = new KindInfo();
			kindInfo.lower = lower;
			kindInfo.upper = upper;
			kindInfo.color = colors.get(i);
		}
		return ret;
	}
}

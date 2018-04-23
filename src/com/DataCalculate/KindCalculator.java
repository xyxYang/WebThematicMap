package com.DataCalculate;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KindCalculator {
	
	private static double getMax(Collection<Double> datas){
		double max = Double.MIN_VALUE;
		for(double data:datas){
			max = Math.max(max, data);
		}
		return max;
	}
	
	private static double getMin(Collection<Double> datas){
		double min = Double.MAX_VALUE;
		for(double data:datas){
			min = Math.min(min, data);
		}
		return min;
	}

	public static List<Color> getColors(int num){
		return getColors(Color.yellow, Color.red, num);
	}
	
	public static List<Color> getColors(Color startColor, Color endColor, int num){
		int startR = startColor.getRed();
		int startG = startColor.getGreen();
		int startB = startColor.getBlue();
		int endR = endColor.getRed();
		int endG = endColor.getGreen();
		int endB = endColor.getBlue();
		int stepR = (endR - startR) / num;
		int stepG = (endG - startG) / num;
		int stepB = (endB - startB) / num;
		
		List<Color> ret = new ArrayList<Color>();
		for(int i=0; i<num; ++i){
			int R = startR + stepR * i;
			int G = startG + stepG * i;
			int B = startB + stepB * i;
			ret.add(new Color(R, G, B));
		}
		return ret;
	}
	
	public static List<KindInfo> EqualDistanceClassification(Collection<Double> datas, int kindNum){
		return EqualDistanceClassification(datas, kindNum, getColors(kindNum));
	}
	
	public static List<KindInfo> EqualDistanceClassification(Collection<Double> datas, int kindNum, List<Color> colors){
		double min = getMin(datas);
		double max = getMax(datas);
		double distance = (max-min)/kindNum;
		double lower = min;
		double upper = min + distance;
		
		if(colors.size() < kindNum){
			colors = getColors(kindNum);
		}
		
		List<KindInfo> ret = new ArrayList<KindInfo>();
		for(int i=0; i<kindNum; ++i){
			KindInfo kindInfo = new KindInfo();
			kindInfo.lower = lower;
			kindInfo.upper = upper;
			kindInfo.color = colors.get(i);
			lower += distance;
			upper += distance;
			ret.add(kindInfo);
		}
		return ret;
	}
}

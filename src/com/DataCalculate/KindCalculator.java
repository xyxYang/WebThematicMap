package com.DataCalculate;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.DataCalculate.calculate.EqualDistanceCalculate;
import com.DataCalculate.calculate.QuantileCalculate;

public class KindCalculator {
	public static KindCalculator instance = new KindCalculator();
	private Map<String, KindCalculate> methods = new HashMap<String, KindCalculate>();
	
	private KindCalculator(){
		methods.put("EqualDistance", new EqualDistanceCalculate());
		methods.put("Quantile", new QuantileCalculate());
	}
	
	public boolean isMethod(String method){
		return methods.containsKey(method);
	}
	
	public Collection<String> getMethodNames(){
		return methods.keySet();
	}
	
	public double getMax(Collection<Double> datas){
		double max = Double.MIN_VALUE;
		for(double data:datas){
			max = Math.max(max, data);
		}
		return max;
	}
	
	public double getMin(Collection<Double> datas){
		double min = Double.MAX_VALUE;
		for(double data:datas){
			min = Math.min(min, data);
		}
		return min;
	}

	public List<Color> getColors(int num){
		return getColors(Color.yellow, Color.red, num);
	}
	
	public List<Color> getColors(Color startColor, Color endColor, int num){
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
	
	public List<KindInfo> classification(Collection<Double> datas, String method, int kindNum, List<Color> colors){
		if( !isMethod(method)){
			return null;
		}
		if(colors.size() < kindNum){
			colors = KindCalculator.instance.getColors(kindNum);
		}
		KindCalculate cal = methods.get(method);
		return cal.classification(datas, kindNum, colors);
	}
	
	public List<KindInfo> classification(Collection<Double> datas, String method, int kindNum){
		return classification(datas, method, kindNum, getColors(kindNum));
	}
	
}

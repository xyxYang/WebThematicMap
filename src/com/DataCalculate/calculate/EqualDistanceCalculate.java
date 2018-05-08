package com.DataCalculate.calculate;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.DataCalculate.KindCalculate;
import com.DataCalculate.KindCalculator;
import com.DataCalculate.KindInfo;

public class EqualDistanceCalculate implements KindCalculate {

	@Override
	public List<KindInfo> classification(Collection<Double> datas, int kindNum,
			List<Color> colors) {
		double min = KindCalculator.instance.getMin(datas);
		double max = KindCalculator.instance.getMax(datas);
		double distance = (max-min)/kindNum;
		double lower = min;
		double upper = min + distance;
		
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

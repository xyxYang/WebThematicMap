package com.DataCalculate.calculate;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.DataCalculate.KindCalculate;
import com.DataCalculate.KindInfo;

public class QuantileCalculate implements KindCalculate {

	@Override
	public List<KindInfo> classification(Collection<Double> datas, int kindNum,
			List<Color> colors) {
		List<Double> dataList = new ArrayList<Double>(datas);
		Collections.sort(dataList);
		int length = dataList.size();
		List<KindInfo> ret = new ArrayList<KindInfo>();
		
		int[] kindDataNums = getKindDataNums(length, kindNum);
		int count = 0;
		for(int i=0; i<kindDataNums.length; ++i){
			KindInfo info = new KindInfo();
			info.lower = dataList.get(count);
			count += kindDataNums[i];
			if(i == kindDataNums.length - 1){
				info.upper = dataList.get(count - 1) + 1;
			}else{
				info.upper = dataList.get(count);
			}
			info.color = colors.get(i);
			ret.add(info);
		}
		return ret;
	}
	
	private int[] getKindDataNums(int length, int kindNum){
		int baseNum = length / kindNum;
		int addOneBefore = length % kindNum;
		int[] ret = new int[kindNum];
		for(int i=0; i<kindNum; ++i){
			if(i<addOneBefore){
				ret[i] = baseNum + 1;
			}else{
				ret[i] = baseNum;
			}
		}
		return ret;
	}

}

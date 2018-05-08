package com.DataCalculate;

import java.awt.Color;
import java.util.Collection;
import java.util.List;

public interface KindCalculate {
	public List<KindInfo> classification(Collection<Double> datas, int kindNum, List<Color> colors);
}

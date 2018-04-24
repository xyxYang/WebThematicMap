package com.Test;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.json.JSONArray;
import org.json.JSONObject;

import com.DataCalculate.KindCalculator;
import com.DataCalculate.KindInfo;
import com.DataStruct.DataUtils;
import com.DataStruct.GeoTable;
import com.DataStruct.ThematicTable;
import com.DrawThematicData.ChartBuilder;
import com.DrawThematicData.ChartInfo;

public class Main {

	public static void main(String args[]) {
		test();
	}
	
	public static void test1(){
		String thematicTableName = "province_thematic";
		String mapTableName = "province";
		GeoTable geoTable = DataUtils.getGeoTable(mapTableName);
		ThematicTable thematicTable = DataUtils.getThematicTable(mapTableName, thematicTableName);
		Map<Integer, Double> gid2data = thematicTable.getList("gdp08");

		List<JSONObject> geolist = DataUtils.getGeometryDatas(geoTable, gid2data);
		System.out.println(geolist.get(0).toString());
		JSONArray geoJa = new JSONArray(geolist);

		Collection<Double> values = gid2data.values();
		List<KindInfo> kindList = KindCalculator.EqualDistanceClassification(gid2data.values(), 5);
		List<JSONObject> kindjsonList = new ArrayList<JSONObject>();
		for(KindInfo info : kindList){
			kindjsonList.add(new JSONObject(info.toJson()));
		}
		JSONArray kindJa = new JSONArray(kindjsonList);

		JSONObject ret = new JSONObject();
		//ret.put("features", geoJa);
		ret.put("kindInfos", kindJa);

		System.out.print(ret.toString());
	}
	
	public static void test(){
		String mapTableName = "province";
		GeoTable geoTable = DataUtils.getGeoTable(mapTableName);
		
		System.out.print(DataUtils.toJson(geoTable.getJsonDatas()));
	}
	
	public static void ThematicTest(){
		String thematicTableName = "province_thematic";
		String mapTableName = "province";
		ThematicTable thematicTable = DataUtils.getThematicTable(mapTableName, thematicTableName);
		System.out.print(DataUtils.toJson(thematicTable.getJsonDatas()));
	}

	public static void drawPictureTest() {
		DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
		// ��һ���������� ������Series 1
		localDefaultCategoryDataset.addValue(21.0D, "Series 1", " 1�� ");
		localDefaultCategoryDataset.addValue(50.0D, "Series 1", " 2�� ");
		localDefaultCategoryDataset.addValue(152.0D, "Series 1", " 3�� ");
		localDefaultCategoryDataset.addValue(184.0D, "Series 1", " 4�� ");
		localDefaultCategoryDataset.addValue(299.0D, "Series 1", " 5�� ");
		//localDefaultCategoryDataset.

		CategoryDataset dataset = localDefaultCategoryDataset;
		JFreeChart chart = ChartFactory.createLineChart("default", "x", "y",
				dataset, PlotOrientation.VERTICAL, true, true, false);
		// ��������
		chart.setTextAntiAlias(false);
		// ���ñ�����ɫ
		chart.setBackgroundPaint(Color.WHITE);

		// ����ͼ���������
		Font font = new Font("����", Font.BOLD, 25);
		chart.getTitle().setFont(font);

		// �����������
		Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
		// ����ͼʾ������
		chart.getLegend().setItemFont(labelFont);

		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		// x�� // �����������Ƿ�ɼ�
		categoryplot.setDomainGridlinesVisible(true);
		// y�� //�����������Ƿ�ɼ�
		categoryplot.setRangeGridlinesVisible(true);
		categoryplot.setRangeGridlinePaint(Color.WHITE);// ����ɫ��
		categoryplot.setDomainGridlinePaint(Color.WHITE);// ����ɫ��
		categoryplot.setBackgroundPaint(Color.lightGray);// ����ͼ�ı�����ɫ

		// ����������֮��ľ���
		// categoryplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));

		// ���� x
		CategoryAxis domainAxis = categoryplot.getDomainAxis();
		domainAxis.setLabelFont(labelFont);// �����
		domainAxis.setTickLabelFont(labelFont);// ����ֵ
		// domainAxis.setLabelPaint(Color.BLUE);//��������ɫ
		// domainAxis.setTickLabelPaint(Color.BLUE);//����ֵ����ɫ

		// ���� lable ��λ�� �����ϵ� Lable 45����б DOWN_45
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);

		// ���þ���ͼƬ��˾���
		domainAxis.setLowerMargin(0.0);
		// ���þ���ͼƬ�Ҷ˾���
		domainAxis.setUpperMargin(0.0);

		// ���� y
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setLabelFont(labelFont);
		numberaxis.setTickLabelFont(labelFont);
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setAutoRangeIncludesZero(true);

		// ���renderer ע���������������͵�lineandshaperenderer����
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot
				.getRenderer();
		lineandshaperenderer.setBaseShapesVisible(true); // series �㣨�����ݵ㣩�ɼ�
		lineandshaperenderer.setBaseLinesVisible(true); // series �㣨�����ݵ㣩�������߿ɼ�

		// ��ʾ�۵�����
		lineandshaperenderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		lineandshaperenderer.setBaseItemLabelsVisible(true);

		FileOutputStream out = null;
		try {
			File outFile = new File("F:\\picture.png");
			out = new FileOutputStream(outFile);
			ChartUtilities.writeChartAsJPEG(out, chart, 600, 400);
		} catch (Exception e) {

		}
	}

	public static void datasetTest() {
		String url = "jdbc:postgresql://localhost:5432/WebThematicMap_DB";// ���ݿ���Դ��Ϣ
		String user = "postage"; // �û���
		String password = "WHU(2014)"; // ����
		String sql = "select *, ST_AsGeoJson(geom) as geojson from province";
		Connection con;
		Statement st;
		ResultSet rs;
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			con = DriverManager.getConnection(url, "postgres", "Jack1203");
			st = con.createStatement(); // ������ѯ��
			rs = st.executeQuery(sql); // ���ɽ����
			/* ����Ϊ���ݽ������ */

			while (rs.next()) {
				System.out.println(rs.getString("geojson"));
			}

			/* ����Ϊ���ݽ������ */
			st.close();
			rs.close();
			con.close(); // �ر����ݼ�
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
}

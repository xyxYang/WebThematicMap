package com.Test;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

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

import com.DrawThematicData.ChartBuilder;
import com.DrawThematicData.ChartInfo;

public class Main {

	public static void main(String args[]) {
		datasetTest();
		/*
		//drawPictureTest();
		
		List<String> keys = Arrays.asList("1��", "2��", "3��","4��","5��");
		List<Double> values = Arrays.asList(21.0D, 50.0D, 152.0D, 184.0D, 299.0D);
		List<String> rows = Arrays.asList("Series 1");
		ChartInfo info = ChartInfo.createLineInfo("����", "x", "y");
		
		JFreeChart chart = ChartBuilder.build(keys, values, rows, info);
		
		FileOutputStream out = null;
		try {
			File outFile = new File("/users/yangyuxin/test.jpg");
			out = new FileOutputStream(outFile);
			ChartUtilities.writeChartAsJPEG(out, chart, 600, 400);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
		*/
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

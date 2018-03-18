package com.Test;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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

public class Main {

	public static void main(String args[]) {
		drawPictureTest();
	}

	public static void drawPictureTest() {
		DefaultCategoryDataset localDefaultCategoryDataset = new DefaultCategoryDataset();
		// 第一条折线数据 折线名Series 1
		localDefaultCategoryDataset.addValue(21.0D, "Series 1", " 1月 ");
		localDefaultCategoryDataset.addValue(50.0D, "Series 1", " 2月 ");
		localDefaultCategoryDataset.addValue(152.0D, "Series 1", " 3月 ");
		localDefaultCategoryDataset.addValue(184.0D, "Series 1", " 4月 ");
		localDefaultCategoryDataset.addValue(299.0D, "Series 1", " 5月 ");
		//localDefaultCategoryDataset.

		CategoryDataset dataset = localDefaultCategoryDataset;
		JFreeChart chart = ChartFactory.createLineChart("default", "x", "y",
				dataset, PlotOrientation.VERTICAL, true, true, false);
		// 字体清晰
		chart.setTextAntiAlias(false);
		// 设置背景颜色
		chart.setBackgroundPaint(Color.WHITE);

		// 设置图标题的字体
		Font font = new Font("隶书", Font.BOLD, 25);
		chart.getTitle().setFont(font);

		// 设置面板字体
		Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);
		// 设置图示的字体
		chart.getLegend().setItemFont(labelFont);

		CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
		// x轴 // 分类轴网格是否可见
		categoryplot.setDomainGridlinesVisible(true);
		// y轴 //数据轴网格是否可见
		categoryplot.setRangeGridlinesVisible(true);
		categoryplot.setRangeGridlinePaint(Color.WHITE);// 虚线色彩
		categoryplot.setDomainGridlinePaint(Color.WHITE);// 虚线色彩
		categoryplot.setBackgroundPaint(Color.lightGray);// 折线图的背景颜色

		// 设置轴和面板之间的距离
		// categoryplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));

		// 横轴 x
		CategoryAxis domainAxis = categoryplot.getDomainAxis();
		domainAxis.setLabelFont(labelFont);// 轴标题
		domainAxis.setTickLabelFont(labelFont);// 轴数值
		// domainAxis.setLabelPaint(Color.BLUE);//轴标题的颜色
		// domainAxis.setTickLabelPaint(Color.BLUE);//轴数值的颜色

		// 横轴 lable 的位置 横轴上的 Lable 45度倾斜 DOWN_45
		domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);

		// 设置距离图片左端距离
		domainAxis.setLowerMargin(0.0);
		// 设置距离图片右端距离
		domainAxis.setUpperMargin(0.0);

		// 纵轴 y
		NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
		numberaxis.setLabelFont(labelFont);
		numberaxis.setTickLabelFont(labelFont);
		numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		numberaxis.setAutoRangeIncludesZero(true);

		// 获得renderer 注意这里是下嗍造型到lineandshaperenderer！！
		LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot
				.getRenderer();
		lineandshaperenderer.setBaseShapesVisible(true); // series 点（即数据点）可见
		lineandshaperenderer.setBaseLinesVisible(true); // series 点（即数据点）间有连线可见

		// 显示折点数据
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
		String url = "jdbc:postgresql://localhost:5432/WebThematicMap_DB";// 数据库资源信息
		String user = "postage"; // 用户名
		String password = "WHU(2014)"; // 密码
		String sql = "select *, ST_AsGeoJson(geom) as geojson from province";
		Connection con;
		Statement st;
		ResultSet rs;
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			con = DriverManager.getConnection(url, "postgres", "Jack1203");
			st = con.createStatement(); // 创建查询集
			rs = st.executeQuery(sql); // 生成结果集
			/* 以下为数据结果处理 */

			while (rs.next()) {
				System.out.println(rs.getString("geojson"));
			}

			/* 以上为数据结果处理 */
			st.close();
			rs.close();
			con.close(); // 关闭数据集
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
}

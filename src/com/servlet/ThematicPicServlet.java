package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.servlet.ServletUtilities;

import com.DBManage.DBConBuilder;
import com.DBManage.DBConn;
import com.DrawThematicData.ChartBuilder;
import com.DrawThematicData.ChartInfo;

public class ThematicPicServlet extends HttpServlet {
	public ThematicPicServlet() {
		super();
	}
	
	public void destroy() {
		super.destroy();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//DBConn con = DBConBuilder.instance.buildDBConn();
		
		List<String> keys = Arrays.asList("1月", "2月", "3月","4月","5月");
		List<Double> values = Arrays.asList(21.0D, 50.0D, 152.0D, 184.0D, 299.0D);
		List<String> rows = Arrays.asList("Series 1");
		ChartInfo info = ChartInfo.createLineInfo("标题", "x", "y");
		
		JFreeChart chart = ChartBuilder.build(keys, values, rows, info);
		
		//保存图片 返回图片文件名
		String filename = ServletUtilities.saveChartAsPNG(chart, 600, 400, null); 
		//获取图片路径（内存中）
		String graphURL = req.getContextPath() + "/DisplayChart?filename="   + filename;
		//拼接<img src="  "  />
		String image=  "<img src='" 
		            + graphURL 
		            + "' width=600 height=400 border=0 usemap='#" 
		            + filename + "'/>";    
		req.setAttribute("image", image);
		req.getRequestDispatcher("/hrv/hrv.jsp").forward(req,
		resp);
		
		
		super.doGet(req, resp);
	}
	
	
}

package com.DataStruct;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.DBManage.DBConBuilder;
import com.DBManage.ResultType;

public class ThematicTable {
	private String name;
	private List<String> fields = new ArrayList<String>();
	private List<ThematicData> datas = new ArrayList<ThematicData>();
	private static final String selectSQL = "select * from %s";

	public ThematicTable(String tableName) {
		this.name = tableName;
	}

	public ResultType initial() {
		Connection con = DBConBuilder.instance.build();
		if (con == null) {
			return ResultType.ConnectFailed;
		}

		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(String.format(selectSQL, name));
			ResultSetMetaData metaData = rs.getMetaData();
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				String columnName = metaData.getCatalogName(i);
				if(!(columnName.equals("id") || columnName.equals("name"))){
					fields.add(columnName);
				}
			}
			while (rs.next()) {
				ThematicData data = new ThematicData();
				data.id = rs.getInt("id");
				data.name = rs.getString("name");
				for(String fieldName:fields){
					data.datas.add(rs.getDouble(fieldName));
				}
			}
			if (datas.size() != 0) {
				return ResultType.Success;
			} else {
				return ResultType.NoTable;
			}
		} catch (Exception e) {
			System.out.print(e.getMessage());
			return ResultType.ConnectFailed;
		}
	}
	
	public int getFieldIndex(String fieldName){
		return fields.indexOf(fieldName);
	}
	
	public List<String> getFields(){
		return new ArrayList<String>(fields);
	}
	
	public List<ThematicData> getDatas(){
		return new ArrayList<ThematicData>(datas);
	}
}

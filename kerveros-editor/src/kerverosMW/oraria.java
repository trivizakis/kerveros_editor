package kerverosMW;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.HashMap;

public class oraria {
	private String perigrafiOrariou;
	private HashMap<String, Time> ores = new HashMap<String, Time>();

	public oraria(){}
	
	public oraria(ResultSet rs) {
		try {
			this.setPerigrafiOrariou(rs.getString("Περιγραφή ωραρίου"));
			
			for(int i=3; i<rs.getMetaData().getColumnCount(); i++)
				this.ores.put(rs.getMetaData().getColumnName(i), rs.getTime(rs.getMetaData().getColumnName(i)));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getPerigrafiOrariou() {
		return perigrafiOrariou;
	}
	private void setPerigrafiOrariou(String perigrafiOrariou) {
		this.perigrafiOrariou = perigrafiOrariou;
	}
	
	public HashMap<String, Time> getOres() {
		return ores;
	}
}

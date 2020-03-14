
/**
 * 
 */
package kerverosMW;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author webserver
 *
 */
@SuppressWarnings("unused")
public class connectorDB {
	private Connection conn = null;
	private Statement stmt = null;
	/**
	 * 
	 */
	public connectorDB() {
		
	}
//epistrefei pelates apo tin eyresi tilefonou
	public ArrayList<helperDB> fetchTel(String lima){
		ArrayList<helperDB> result = new ArrayList<helperDB>();
		ResultSet rs = null;
		openDB();
		try {
			if (conn != null) {	        	
	        	//ΑΡΧΗ QUERY
				for(int i=1; i<6; i++){
						rs = stmt.executeQuery("SELECT  Πελάτες.*, Είδη_Χώρων.[Περιγραφή χώρου] FROM Πελάτες"
												+" INNER JOIN Είδη_Χώρων ON Πελάτες.[Είδος χώρου]=Είδη_Χώρων.Αναγνωριστικό"
												+" WHERE [Τηλέφωνο "
												+i
												+"] LIKE '%"+lima+"%'");	
			        	if(countRS(rs)>0){
			        		while(rs.next()){
			        				result.add(new helperDB(rs));
			        			}
			        		}
				}
				for(int i=1; i<16; i++){
					rs = stmt.executeQuery("SELECT  Πελάτες.*, Είδη_Χώρων.[Περιγραφή χώρου] FROM Πελάτες"
											+" INNER JOIN Είδη_Χώρων ON Πελάτες.[Είδος χώρου]=Είδη_Χώρων.Αναγνωριστικό"
											+" WHERE [Αριθμός τηλεφώνου "
											+i
											+"] LIKE '%"+lima+"%'");	
		        	if(countRS(rs)>0){
		        		while(rs.next()){
		        				result.add(new helperDB(rs));
		        			}
		        		}
			}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeDB();
        }
		return result;
	}
//epistrefei ORARIA
	public ArrayList<HashMap<Integer, oraria>> fetchOraria(){
		ArrayList<HashMap<Integer, oraria>> result = new ArrayList<HashMap<Integer, oraria>>();
		HashMap<Integer, oraria> temp = new HashMap<Integer, oraria>();
		ResultSet rs = null;
		openDB();
		try {
			if (conn != null) {	        	
	        	//ΑΡΧΗ QUERY
						rs = stmt.executeQuery("SELECT * FROM Ωράρια");	
			        	if(countRS(rs)>0){
			        		while(rs.next()){
			        			temp.put(rs.getInt("Αναγνωριστικό"), new oraria(rs));
			    				result.add(temp);
			    				//temp.clear();
			        		}
			        	}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeDB();
        }
		return result;
	}
//epistrefei XOROUS
	public ArrayList<String> fetchXoroi(){
		ArrayList<String> result = new ArrayList<String>();
		ResultSet rs = null;
		openDB();
		try {
			if (conn != null) {
	        	//ΑΡΧΗ QUERY
							rs = stmt.executeQuery("SELECT * FROM Είδη_Χώρων");	
			        	if(countRS(rs)>0){
			        		while(rs.next()){
			        			result.add(rs.getString("Περιγραφή χώρου"));
			        		}
			        	}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeDB();
        }
		return result;
	}	
//epistrefei olus tus pelates me AE i Idiait
	public ArrayList<helperDB> findAE(boolean ae){
		ArrayList<helperDB> results = new ArrayList<helperDB>();
		ResultSet rs = null;
		openDB();
		try{
			if (conn != null) {
	        	
	        	//ΑΡΧΗ QUERY
				if(ae){
						rs = stmt.executeQuery("SELECT Πελάτες.*, Είδη_Χώρων.[Περιγραφή χώρου] FROM Πελάτες"
								+" INNER JOIN Είδη_Χώρων ON Πελάτες.[Είδος χώρου]=Είδη_Χώρων.Αναγνωριστικό"
								+" WHERE Πελάτες.[Άμεση επέμβαση]='-1'");			        	
			        	if(countRS(rs)>0){
			        		while(rs.next())
			    				results.add(new helperDB(rs));
			        	}
				}else{
						rs = stmt.executeQuery("SELECT Πελάτες.*, Είδη_Χώρων.[Περιγραφή χώρου] FROM Πελάτες"
							+" INNER JOIN Είδη_Χώρων ON Πελάτες.[Είδος χώρου]=Είδη_Χώρων.Αναγνωριστικό"
							+" WHERE Πελάτες.Ιδιαιτερότητα='-1'");			        	
		        	if(countRS(rs)>0){
		        		while(rs.next())
		    				results.add(new helperDB(rs));
		        	}
				}
			    //ΤΕΛΟΣ QUERY
			}
		} catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
        	closeDB();
        }
		
		return results;
	}
//epistrefei apotelesmata me pelates apo CODE, EPONIMIA kai EPIGRAFI
	public ArrayList<helperDB> execQuery(String lima){
		ArrayList<helperDB> results = new ArrayList<helperDB>();
		//Set<String> s = new LinkedHashSet<>(list)
		//Set<helperDB> finalResults = new HashSet<>();
		ResultSet rs = null;
		openDB();
		try{
			if (conn != null) {
	        	
	        	//ΑΡΧΗ QUERY
				if(isNumeric(lima)){
						rs = stmt.executeQuery("SELECT Πελάτες.*, Είδη_Χώρων.[Περιγραφή χώρου] FROM Πελάτες"
								+" INNER JOIN Είδη_Χώρων ON Πελάτες.[Είδος χώρου]=Είδη_Χώρων.Αναγνωριστικό"
								+" WHERE Κωδικός='"+lima+"'");			        	
			        	if(countRS(rs)>0){	
			        		while(rs.next())
			    				results.add(new helperDB(rs));
				        	//traverseRS(rs);
			        	}
				}else{
			        		rs = stmt.executeQuery("SELECT Πελάτες.*, Είδη_Χώρων.[Περιγραφή χώρου] FROM Πελάτες"
														+" INNER JOIN Είδη_Χώρων ON Πελάτες.[Είδος χώρου]=Είδη_Χώρων.Αναγνωριστικό"
														+" WHERE Επωνυμία LIKE '%"+lima+"%'");
				        	if(countRS(rs)>0){	
				        		while(rs.next())				        			
				    				results.add(new helperDB(rs));
				        		//traverseRS(rs);
				        	}
				        	rs = stmt.executeQuery("SELECT *, Είδη_Χώρων.[Περιγραφή χώρου] FROM Πελάτες"
									+" INNER JOIN Είδη_Χώρων ON Πελάτες.[Είδος χώρου]=Είδη_Χώρων.Αναγνωριστικό"
									+" WHERE Επιγραφή LIKE '%"+lima+"%'");
			        		if(countRS(rs)>0){
			        			while(rs.next()){
			        					results.add(new helperDB(rs));
			        			}
				        		//traverseRS(rs);
				        	}
			        	}			        	
			        	//finalResults.addAll(results);			        	
			        	//Set<helperDB> finalResults = new LinkedHashSet<>(results);
			        	//results.clear();
			        	//results.addAll(finalResults);
	        	//ΤΕΛΟΣ QUERY
			}
        } catch (SQLException ex) {
            ex.printStackTrace();
        }finally{
        	closeDB();
        }
	    //System.out.print(this.code);
		return results;
	}
//poses grammes apo DB 
	private int countRS(ResultSet rs) {
		int row = 0;
		try {
			rs.last();
			row = rs.getRow();
	    	//System.out.println(""+row);
	    	rs.beforeFirst();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return row;
	}
//ΕΛΕΓΧΟΙ	
		private boolean isNumeric(String str)
		{
		    for (char c : str.toCharArray())
		    {
		        if (!Character.isDigit(c)) return false;
		    }
		    return true;
		}
// klisimo i kai anoigma apo DB		
	private void closeDB(){
		try {
             if (conn != null && !conn.isClosed()) {
                 conn.close();
             }
         } catch (SQLException ex) {
             ex.printStackTrace();
         }
	}
	
	private void openDB(){
		try {
			//DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
			String serverName = "SQLEXPRESS";
			String ipAddr = "localhost";
			Short port = 1433;
			String dbName = "kos_S1";
			
            String dbURL = "jdbc:sqlserver://"+ipAddr+"\\"+serverName+":"+port+";databaseName="+dbName;
            
            String user = "sa";
            String pass = "14091986";
            conn = DriverManager.getConnection(dbURL, user, pass);
            
            if (conn != null) {   
	        	stmt = conn.createStatement(
	            		ResultSet.TYPE_SCROLL_SENSITIVE,
	            		ResultSet.CONCUR_UPDATABLE);
	        /*
	        	DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
	        	System.out.println("Driver name: " + dm.getDriverName());
	        	System.out.println("Driver version: " + dm.getDriverVersion());
	        	System.out.println("Product name: " + dm.getDatabaseProductName());
	        	System.out.println("Product version: " + dm.getDatabaseProductVersion());
	        */
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
	}
//typwsi grammwn ResultSet	
	private void traverseRS(ResultSet rs){			//γρήγορο διαγνωστικό για queries
		try {
			while(rs.next()){			//πάντα: το rs δίνει δείκτη πριν το πρώτο στοιχείο
				//rs.next();rs.next();rs.next();rs.next();
				System.out.println("ΑΡΧΗ: " + rs.getString("Κωδικός"));
				//System.out.println(rs.getString("ΤΚ"));
					for(int i=1; i<66; i++){				//next field in row
						System.out.print( rs.getString(i));
					}
				System.out.println("ΤΕΛΟΣ: " + rs.getString("Κωδικός"));
					//System.out.println(rs.getDate("Ημερομηνία εγκατάστασης").toString());
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}

//palios kwdikas

/*	DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
System.out.println("Driver name: " + dm.getDriverName());
System.out.println("Driver version: " + dm.getDriverVersion());
System.out.println("Product name: " + dm.getDatabaseProductName());
System.out.println("Product version: " + dm.getDatabaseProductVersion());  //an ta vgazei tote exei connect

Statement stmt = conn.createStatement(		
		ResultSet.TYPE_SCROLL_SENSITIVE,
		ResultSet.CONCUR_UPDATABLE);
		
		
ResultSet rs = stmt.executeQuery("SELECT * FROM Πελάτες WHERE Κωδικός='"+1538+"'");
while(rs.next()){			//πάντα: το rs δίνει δείκτη πριν το πρώτο στοιχείο
//rs.next();rs.next();rs.next();rs.next();
	for(int i=1; i<66; i++){				//next field in row
		System.out.print( rs.getString(i));
			}
		//System.out.print( rs.getString("Κωδικός"));
	//System.out.println(rs.getDate("Ημερομηνία εγκατάστασης").toString());
}

//original execQuery :
public helperDB execQuery(int code){
		helperDB result = null;

		try{
			if (conn != null) {
	        	
	        	//ΑΡΧΗ QUERY
			        	ResultSet rs = stmt.executeQuery("SELECT * FROM Πελάτες WHERE Κωδικός='"+code+"'");
			        	
			        	if(rs.last()){						//getRowCount from query
			        		int row = rs.getRow();
				        	System.out.println(""+row);
				        	rs.beforeFirst();
			        	}
			        	
			        	while(rs.next()){			//πάντα: το rs δίνει δείκτη πριν το πρώτο στοιχείο
			        	//rs.next();rs.next();rs.next();rs.next();
			        		for(int i=1; i<66; i++){				//next field in row
			        			System.out.print( rs.getString(i));
			        		}
			        			//System.out.print( rs.getString("Κωδικός"));
			        		//System.out.println(rs.getDate("Ημερομηνία εγκατάστασης").toString());
			        	}
	        	//ΤΕΛΟΣ QUERY
			}
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
		
	    //System.out.print(this.code);
		return result;
	}
*/

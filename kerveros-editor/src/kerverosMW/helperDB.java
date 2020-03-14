/**
 * 
 */
package kerverosMW;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author webserver
 *
 */
public class helperDB {

	private short company;
	private short sodtype;
	private int id;
	private String code;
	private String eponymia;
	private String afm;
	private short anenergos;
	private short country;
	private short socurrency;
	private short branch;
	private String address;
	private String tk;
	private String doy;
	private String epaggelma;
	private short amesiEpemvasi;
	private String poly;
	private String paratiriseis;
	private String eidosXorou;
	private short parakolou8isi;
	private Date dateEgkatastasis;
	private int morfiSimatos;
	private short idiait;
	private int codeTest;
	private int codePartner;
	private String odigiesAE;
	private String odigiesidiait;
	private String name;
	private String orofos;
	private String typosMix;
	private int orario;
	private String epigrafi;
	private ArrayList<String> tilefono = new ArrayList<String>();
	private ArrayList<String> tilAnagkisOnoma = new ArrayList<String>();
	private ArrayList<String> tilAnagkisTilefono = new ArrayList<String>();
	private int trdr;
	/**
	 * 
	 */
	public helperDB(ResultSet rs) {
		try {
			this.setCompany(rs.getShort("COMPANY"));
			this.setSodtype(rs.getShort("SODTYPE"));
			this.setId(rs.getInt("Αναγνωριστικό"));
			this.setCode(rs.getString("Κωδικός"));
			this.setEponymia(rs.getString("Επωνυμία"));
			this.setAfm(rs.getString("ΑΦΜ"));
			this.setAnenergos(rs.getShort("Ανενεργός"));
			this.setCountry(rs.getShort("COUNTRY"));
			this.setSocurrency(rs.getShort("SOCURRENCY"));
			this.setBranch(rs.getShort("BRANCH"));
			this.setAddress(rs.getString("Διεύθυνση"));
			this.setTk(rs.getString("ΤΚ"));
			this.setDoy(rs.getString("ΔΟΥ"));
			this.setEpaggelma(rs.getString("Επάγγελμα"));
			this.setAmesiEpemvasi(rs.getShort("Αμεση επέμβαση"));
			this.setPoly(rs.getString("Πόλη"));
			this.setParatiriseis(rs.getString("Παρατηρήσεις"));
			this.setEidosXorou(rs.getString("Περιγραφή χώρου")); // Υπάρχει και "Είδος χώρου" αλλά μετα το join στο query χρησιμοποιούμε το  "Περιγραφή χώρου"
			this.setParakolou8isi(rs.getShort("Εξαίρεση παρακολούθησης"));
			this.setDateEgkatastasis(rs.getDate("Ημερομηνία εγκατάστασης"));
			this.setMorfiSimatos(rs.getInt("Ιδιαίτερη μορφή σήματος"));
			this.setIdiait(rs.getShort("Ιδιαιτερότητα"));
			this.setCodeTest(rs.getInt("Κωδικός test"));
			this.setCodePartner(rs.getInt("Κωδικός συνεργάτη"));
			this.setOdigiesAE(rs.getString("Οδηγίες επέμβασης"));
			this.setOdigiesidiait(rs.getString("Οδηγίες ιδιαιτερότητας"));
			this.setName(rs.getString("Ονομα"));
			this.setOrofos(rs.getString("Οροφος"));
			this.setTyposMix(rs.getString("Τύπος μηχανήματος"));
			this.setOrario(rs.getInt("Ωράριο λειτουργίας"));
			this.setEpigrafi(rs.getString("Επιγραφή"));
			tilefonaXorou(rs);
			tilefonaAnagkis(rs);
			this.setTrdr(rs.getInt("TRDR"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	private void tilefonaXorou(ResultSet rs){
		try {
			for (int i=0; i<5; i++){
				String temp;
				temp = rs.getString("Τηλέφωνο "+(i+1));				
				if(temp != null){
					tilefono.add(temp);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void tilefonaAnagkis(ResultSet rs){
		try {
			for (int i=0; i<15; i++){
				String tempOnoma = rs.getString("Τηλέφωνο ανάγκης "+ (i+1));
				String tempTil = rs.getString("Αριθμός τηλεφώνου "+ (i+1));
				if(tempOnoma!=null || tempTil!=null){
					tilAnagkisOnoma.add(tempOnoma);
					tilAnagkisTilefono.add(tempTil);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @return the company
	 */
	public short getCompany() {
		return company;
	}
	/**
	 * @return the sodtype
	 */
	public short getSodtype() {
		return sodtype;
	}
	/**
	 * @param sodtype the sodtype to set
	 */
	public void setSodtype(short sodtype) {
		this.sodtype = sodtype;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getEponymia() {
		return eponymia;
	}
	public void setEponymia(String eponymia) {
		this.eponymia = eponymia;
	}
	public String getAfm() {
		return afm;
	}
	public void setAfm(String afm) {
		this.afm = afm;
	}
	public short getAnenergos() {
		return anenergos;
	}
	public void setAnenergos(short anenergos) {
		this.anenergos = anenergos;
	}
	public short getCountry() {
		return country;
	}
	public void setCountry(short country) {
		this.country = country;
	}
	public short getSocurrency() {
		return socurrency;
	}
	public void setSocurrency(short socurrency) {
		this.socurrency = socurrency;
	}
	public short getBranch() {
		return branch;
	}
	public void setBranch(short branch) {
		this.branch = branch;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTk() {
		return tk;
	}
	public void setTk(String tk) {
		this.tk = tk;
	}
	public String getDoy() {
		return doy;
	}
	public void setDoy(String doy) {
		this.doy = doy;
	}
	public String getEpaggelma() {
		return epaggelma;
	}
	public void setEpaggelma(String epaggelma) {
		this.epaggelma = epaggelma;
	}
	public short getAmesiEpemvasi() {
		return amesiEpemvasi;
	}
	public void setAmesiEpemvasi(short amesiEpemvasi) {
		this.amesiEpemvasi = amesiEpemvasi;
	}
	public String getPoly() {
		return poly;
	}
	public void setPoly(String poly) {
		this.poly = poly;
	}
	public String getParatiriseis() {
		return paratiriseis;
	}
	public void setParatiriseis(String paratiriseis) {
		this.paratiriseis = paratiriseis;
	}
	public String getEidosXorou() {
		return eidosXorou;
	}
	public void setEidosXorou(String eidosXorou) {
		this.eidosXorou = eidosXorou;
	}
	public short getParakolou8isi() {
		return parakolou8isi;
	}
	public void setParakolou8isi(short parakolou8isi) {
		this.parakolou8isi = parakolou8isi;
	}
	public Date getDateEgkatastasis() {
		return dateEgkatastasis;
	}
	public void setDateEgkatastasis(Date dateEgkatastasis) {
		this.dateEgkatastasis = dateEgkatastasis;
	}
	public int getMorfiSimatos() {
		return morfiSimatos;
	}
	public void setMorfiSimatos(int morfiSimatos) {
		this.morfiSimatos = morfiSimatos;
	}
	public short getIdiait() {
		return idiait;
	}
	public void setIdiait(short idiait) {
		this.idiait = idiait;
	}
	public int getCodeTest() {
		return codeTest;
	}
	public void setCodeTest(int codeTest) {
		this.codeTest = codeTest;
	}
	public int getCodePartner() {
		return codePartner;
	}
	public void setCodePartner(int codePartner) {
		this.codePartner = codePartner;
	}
	public String getOdigiesAE() {
		return odigiesAE;
	}
	public void setOdigiesAE(String odigiesAE) {
		this.odigiesAE = odigiesAE;
	}
	public String getOdigiesidiait() {
		return odigiesidiait;
	}
	public void setOdigiesidiait(String odigiesidiait) {
		this.odigiesidiait = odigiesidiait;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrofos() {
		return orofos;
	}
	public void setOrofos(String orofos) {
		this.orofos = orofos;
	}
	public String getTyposMix() {
		return typosMix;
	}
	public void setTyposMix(String typosMix) {
		this.typosMix = typosMix;
	}
	public int getOrario() {
		return orario;
	}
	public void setOrario(int orario) {
		this.orario = orario;
	}
	public String getEpigrafi() {
		return epigrafi;
	}
	public void setEpigrafi(String epigrafi) {
		this.epigrafi = epigrafi;
	}
	public ArrayList<String> getTilefono() {
		return tilefono;
	}
	public void setTilefono(ArrayList<String> tilefono) {
		this.tilefono = tilefono;
	}
	public ArrayList<String> getTilAnagkisOnoma() {
		return tilAnagkisOnoma;
	}
	public void setTilAnagkisOnoma(ArrayList<String> tilAnagkisOnoma) {
		this.tilAnagkisOnoma = tilAnagkisOnoma;
	}
	/**
	 * @param company the company to set
	 */
	public void setCompany(short company) {
		this.company = company;
	}
	/**
	 * @return the tilAnagkisTilefono
	 */
	public ArrayList<String> getTilAnagkisTilefono() {
		return tilAnagkisTilefono;
	}
	/**
	 * @param tilAnagkisTilefono the tilAnagkisTilefono to set
	 */
	public void setTilAnagkisTilefono(ArrayList<String> tilAnagkisTilefono) {
		this.tilAnagkisTilefono = tilAnagkisTilefono;
	}
	/**
	 * @return the trdr
	 */
	public int getTrdr() {
		return trdr;
	}
	/**
	 * @param trdr the trdr to set
	 */
	public void setTrdr(int trdr) {
		this.trdr = trdr;
	}

	
}

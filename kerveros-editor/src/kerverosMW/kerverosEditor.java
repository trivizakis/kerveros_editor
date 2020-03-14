/**
 * 
 */
package kerverosMW;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionListener;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollBar;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JEditorPane;
/**
 * @author webserver
 *
 */
public class kerverosEditor extends JFrame{
	private final JTextField tfSearch = new JTextField();
	private JTextField tfEponymia;
	private JTextField tfCode;
	private JTextField tfName;
	private JTextField tfEpigrafi;
	private JTextField tfCity;
	private JTextField tfAddress;
	private JTextField tfTk;
	private JTextField tfAfm;
	private JTextField tfEpagelma;
	private JTextField tfDoy;
	private JTextField tfOrofos;
	private JTextField tfKodSinergati;
	private JTextField tfTyposMix;
	private JTextField tfInstallDate;
	private JTextPane tpAe;
	private JTextPane tpIdiat;
	private JComboBox cbOraria;
	private JCheckBox cbEksParak;
	private JCheckBox cbAnenergos;
	private JCheckBox cbIdiait;
	private JComboBox cbEidosXorou;
	private JCheckBox cbxAe;
	private JComboBox cbSelectForSearch;
	private static connectorDB connectMe= new connectorDB();
	private static ArrayList<HashMap<Integer, oraria>> oraria;
	private static ArrayList<String> xoroi;
	private static splashScreen splashThis;
	private JList listApotelesmata;
	private ArrayList<helperDB> listApotel = new ArrayList<helperDB>();
	/**
	 * 
	 */
	public kerverosEditor() throws HeadlessException {
		splashThis = new splashScreen("img/cerberus_logo.png", this);
		this.setTitle("Kerveros Editor");		
		this.initWindow();	//eisagei components sto JFrame
	//init oraria combobox from db
		oraria = connectMe.fetchOraria();
		setOrariaToCB(oraria);
		cbOraria.setSelectedIndex(-1);		
	//init eidi xorwn combobox from db
		xoroi = connectMe.fetchXoroi();
		setXoroiToCB(xoroi);
		cbEidosXorou.setSelectedIndex(-1);
		
		
	//τέλος βδ		
		splashThis.closeThisSplash();
		this.setVisible(true);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new kerverosEditor();
		//connectMe = new connectorDB();
	}
//ΤΥΠΩΣΗ ΩΡΑΡΙΟΥ
	private void findOrario(ArrayList<HashMap<Integer, oraria>> oraria, int lima){
		kerverosMW.oraria ora = new oraria();
		for(int i=0; i<oraria.size(); i++) 
			if(oraria.get(i).get(lima) != null){
				ora = oraria.get(i).get(lima);
				break;
			}
		cbOraria.setSelectedItem(ora.getPerigrafiOrariou());
		
		System.out.println(ora.getPerigrafiOrariou());
		System.out.println(ora.getOres().get("Δευτέρα άνοιγμα 1")+"-"+ ora.getOres().get("Δευτέρα κλείσιμο 1"));
		System.out.println(ora.getOres().get("Δευτέρα άνοιγμα 2")+"-"+ ora.getOres().get("Δευτέρα κλείσιμο 2"));
	}
//ΒΑΖΕΙ ΑΠΟΤΕΛΕΣΜΑΤΑ ΣΤΗ ΛΙΣΤΑ
	private void setListResults(ArrayList<helperDB> result){
		ArrayList<String> title = new ArrayList<String>();
		DefaultListModel listModel;
		
		if(listApotelesmata==null)
			listModel = new DefaultListModel();
		else
			listModel = (DefaultListModel) listApotelesmata.getModel();
		//fillTheGaps(result.get(0));
		
		for(int i=0; i<result.size(); i++){
			//Διαγνωσικό
			System.out.println(result.get(i).getCode()
					+"|"+result.get(i).getEponymia()
					+"|"+result.get(i).getEidosXorou()
					);
			//διαγνωστικό τελος
			listApotel.add(result.get(i));
			listModel.addElement(result.get(i).getCode()+":"+result.get(i).getEponymia());
		}
		if(listApotelesmata==null){
			listApotelesmata = new JList(listModel);
			//listApotelesmata.setBounds(610,30,230,340);
			//listApotelesmata.setPreferredSize(new Dimension(230,340));
			listApotelesmata.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
			listApotelesmata.setLayoutOrientation(JList.VERTICAL);
			listApotelesmata.setVisibleRowCount(-1);
			
			ListSelectionModel selModel = listApotelesmata.getSelectionModel();
			selModel.addListSelectionListener(new SharedListSelectionHandler(this));
			
			//listApotelesmata.setVisible(true);
			//this.add(listApotelesmata);
			JScrollPane listScroller = new JScrollPane(listApotelesmata);
			listScroller.setBounds(610,30,230,340);
			//listScroller.setVisible(true);
			getContentPane().add(listScroller);
		}
		this.repaint();
	}
//QUERY-ΕΥΡΕΣΗ ΣΤΗ ΒΔ
	private void searchTel(String lima){
		ArrayList<helperDB> result;
		if(lima.length()>3 && lima.length()<11 && isNumeric(lima)){
			result = connectMe.fetchTel(lima);
			
			setListResults(result);
		}else{
			showWarning("Προσοχή λάθος είσοδος!","Δώστε έως 10 αριθμούς!");
		}
	}
	private void allSpecial(boolean ae){
		ArrayList<helperDB> result;
		if(ae){
			result =connectMe.findAE(ae);
		}else{

			result = connectMe.findAE(ae);
		}
		
		if(result.isEmpty()){
			showWarning( "Δοκίμασε ξανά!","Δεν υπάχει κάτι σχετικό!");
		}else{
			setListResults(result);
		}
	}
	private void findLima(String lima){
		ArrayList<helperDB> result = connectMe.execQuery(lima);	//χρησιμοποιούμε array την περίπτωση πολλαπλών αποτελεσμάτων
		if(result.isEmpty())
			showWarning( "Δοκίμασε ξανά!","Δεν υπάχει κάτι σχετικό!");
		else
			setListResults(result);
		
	}
//ΓΕΜΙΣΜΑ ΣΤΟΙΧΕΙΩΝ GUI
	private void setXoroiToCB(ArrayList<String> xoroi) {
		for(int i=0; i<xoroi.size(); i++){
				cbEidosXorou.addItem(xoroi.get(i));
		}
	}
	
	private void setOrariaToCB(ArrayList<HashMap<Integer, oraria>> oraria){
		ArrayList<oraria> onlyOrariaObj= new ArrayList<oraria>();

		for(int i=0; i<oraria.size(); i++){
			for(int key : oraria.get(i).keySet()){
				onlyOrariaObj.add(oraria.get(i).get(key));
			}
		}
		for(int index=0; index<onlyOrariaObj.size(); index++){
			cbOraria.addItem(onlyOrariaObj.get(index).getPerigrafiOrariou());
			//System.out.println(index+": "+onlyOrariaObj.get(index).getPerigrafiOrariou());
		}
	}
	private void clearForm(){
		tfSearch.setText("");
		
		tfEponymia.setText("");
		tfCode.setText("");
		tfName.setText("");
		tfEpigrafi.setText("");
		tfCity.setText("");
		tfAddress.setText("");
		tfTk.setText("");
		tfAfm.setText("");
		tfEpagelma.setText("");
		tfDoy.setText("");
		tfOrofos.setText("");
		tfKodSinergati.setText("");
		tfTyposMix.setText("");
		tfInstallDate.setText("");
		
		tpIdiat.setText("");
		tpAe.setText("");
		
		cbEksParak.setSelected(false);
		cbAnenergos.setSelected(false);
		cbIdiait.setSelected(false);
		cbxAe.setSelected(false);

		cbEidosXorou.setSelectedIndex(-1);
		cbOraria.setSelectedIndex(-1);
	}
	public void fillTheGaps(helperDB result){
		tfEponymia.setText(result.getEponymia());
		tfCode.setText(result.getCode());
		tfName.setText(result.getName());
		tfEpigrafi.setText(result.getEpigrafi());
		tfCity.setText(result.getPoly());
		tfAddress.setText(result.getAddress());
		tfTk.setText(result.getTk());
		tfAfm.setText(result.getAfm());
		tfEpagelma.setText(result.getEpaggelma());
		tfDoy.setText(result.getDoy());
		tfOrofos.setText(result.getOrofos());
		tfKodSinergati.setText(String.valueOf(result.getCodePartner()));
		tfTyposMix.setText(result.getTyposMix());
		tfInstallDate.setText(result.getDateEgkatastasis().toString());
		
		tpIdiat.setText(result.getOdigiesidiait());
		tpAe.setText(result.getOdigiesAE());
				
		cbEksParak.setSelected(isCheckedThen(result.getParakolou8isi()));
		cbAnenergos.setSelected(isCheckedThen(result.getAnenergos()));
		cbIdiait.setSelected(isCheckedThen(result.getIdiait()));
		cbxAe.setSelected(isCheckedThen(result.getAmesiEpemvasi()));

		if(result.getOrario()>0)			//αν υπάρχει ωραριο
			findOrario(oraria, result.getOrario());
		cbEidosXorou.setSelectedIndex(xoroi.indexOf(result.getEidosXorou()));
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
	
	private boolean isCheckedThen(short num){
		boolean isIt;
		
		if(num==0)
			isIt=false;
		else
			isIt=true;
		
		return isIt;
	}
//ΜΗΝΥΜΑΤΑ	
	private void showWarning(String title, String msg){
		JOptionPane.showMessageDialog(this,
			    msg,//"Παρακαλώ, όχι κενή αναζήτηση!", //μήνυμα
			    title,//"Προσέχουμε τι πληκτρολογούμε...", //τίτλος
			    JOptionPane.WARNING_MESSAGE);
	}
	/*private void showNotFoundMsg() {
		JOptionPane.showMessageDialog(this,
		    "Δεν υπάχει κάτι σχετικό!", //μήνυμα
		    "Δοκίμασε ξανά!", //τίτλος
		    JOptionPane.WARNING_MESSAGE);
	}*/
//ΑΡΧΙΚΟΠΟΙΗΣΗ ΠΑΡΑΘΥΡΟΥ	
	private void initWindow(){
		getContentPane().setLayout(null);
		
		JButton btnSearch = new JButton("ψάξε");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String lima = tfSearch.getText();
				cbOraria.setSelectedIndex(-1);
				if(lima.isEmpty() && (cbSelectForSearch.getSelectedIndex()==0 ||cbSelectForSearch.getSelectedIndex()==1)){		//ελεγχος κενού λήματος
					showWarning("Προσέχουμε τι πληκτρολογούμε...","Παρακαλώ, όχι κενή αναζήτηση!");
				}else{
					//0=kamia, 1=tel, 2=ae, 3=idiait
					switch(cbSelectForSearch.getSelectedIndex()){
					case 0:	findLima(lima);
							break;
					case 1: searchTel(lima);
							break;
					case 2: allSpecial(true);
							break;
					case 3: allSpecial(false);
							break;
							
					}
				}
			}
		});
		btnSearch.setBounds(497, 0, 84, 23);
		getContentPane().add(btnSearch);
		tfSearch.setBounds(10, 1, 249, 20);
		getContentPane().add(tfSearch);
		tfSearch.setColumns(10);
		
		tfEponymia = new JTextField();
		tfEponymia.setBounds(373, 55, 205, 20);
		getContentPane().add(tfEponymia);
		tfEponymia.setColumns(10);
		
		JLabel label_1 = new JLabel("\u0395\u03C0\u03C9\u03BD\u03C5\u03BC\u03AF\u03B1");
		label_1.setBounds(310, 58, 72, 14);
		getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("\u039A\u03C9\u03B4\u03B9\u03BA\u03CC\u03C2");
		label_2.setBounds(10, 30, 60, 14);
		getContentPane().add(label_2);
		
		tfCode = new JTextField();
		tfCode.setBounds(80, 30, 86, 20);
		getContentPane().add(tfCode);
		tfCode.setColumns(10);
		
		JLabel label_3 = new JLabel("\u038C\u03BD\u03BF\u03BC\u03B1");
		label_3.setBounds(10, 58, 60, 14);
		getContentPane().add(label_3);
		
		tfName = new JTextField();
		tfName.setBounds(80, 55, 205, 20);
		getContentPane().add(tfName);
		tfName.setColumns(10);
		
		JLabel label_4 = new JLabel("\u0395\u03C0\u03B9\u03B3\u03C1\u03B1\u03C6\u03AE");
		label_4.setBounds(310, 77, 72, 14);
		getContentPane().add(label_4);
		
		tfEpigrafi = new JTextField();
		tfEpigrafi.setBounds(373, 80, 205, 20);
		getContentPane().add(tfEpigrafi);
		tfEpigrafi.setColumns(10);
		
		JLabel label_5 = new JLabel("\u03A0\u03CC\u03BB\u03B7");
		label_5.setBounds(301, 109, 33, 14);
		getContentPane().add(label_5);
		
		tfCity = new JTextField();
		tfCity.setBounds(333, 106, 154, 20);
		getContentPane().add(tfCity);
		tfCity.setColumns(10);
		
		JLabel label_6 = new JLabel("\u0394\u03B9\u03B5\u03CD\u03B8\u03C5\u03BD\u03C3\u03B7");
		label_6.setBounds(10, 83, 60, 14);
		getContentPane().add(label_6);
		
		tfAddress = new JTextField();
		tfAddress.setBounds(80, 80, 205, 20);
		getContentPane().add(tfAddress);
		tfAddress.setColumns(10);
		
		tfTk = new JTextField();
		tfTk.setBounds(518, 106, 60, 20);
		getContentPane().add(tfTk);
		tfTk.setColumns(10);
		
		JLabel lblTk = new JLabel("TK");
		lblTk.setBounds(497, 109, 25, 14);
		getContentPane().add(lblTk);
		
		JLabel label_7 = new JLabel("\u0391\u03A6\u039C");
		label_7.setBounds(274, 135, 33, 14);
		getContentPane().add(label_7);
		
		tfAfm = new JTextField();
		tfAfm.setBounds(311, 132, 119, 20);
		getContentPane().add(tfAfm);
		tfAfm.setColumns(10);
		
		JLabel label = new JLabel("\u0395\u03C0\u03AC\u03B3\u03B3\u03B5\u03BB\u03BC\u03B1");
		label.setBounds(10, 109, 77, 14);
		getContentPane().add(label);
		
		tfEpagelma = new JTextField();
		tfEpagelma.setBounds(80, 106, 179, 20);
		getContentPane().add(tfEpagelma);
		tfEpagelma.setColumns(10);
		
		JLabel label_8 = new JLabel("\u0394\u039F\u03A5");
		label_8.setBounds(434, 135, 25, 14);
		getContentPane().add(label_8);
		
		tfDoy = new JTextField();
		tfDoy.setBounds(459, 129, 119, 20);
		getContentPane().add(tfDoy);
		tfDoy.setColumns(10);
		
		JLabel label_9 = new JLabel("\u0395\u03AF\u03B4\u03BF\u03C2 \u03C7\u03CE\u03C1\u03BF\u03C5");
		label_9.setBounds(10, 135, 77, 14);
		getContentPane().add(label_9);
		
		cbEidosXorou = new JComboBox();
		cbEidosXorou.setBounds(80, 131, 179, 22);
		getContentPane().add(cbEidosXorou);
		
		cbxAe = new JCheckBox("A/E");
		cbxAe.setBounds(373, 180, 43, 23);
		getContentPane().add(cbxAe);
		
		JLabel label_10 = new JLabel("\u038C\u03C1\u03BF\u03C6\u03BF\u03C2");
		label_10.setBounds(10, 164, 60, 14);
		getContentPane().add(label_10);
		
		tfOrofos = new JTextField();
		tfOrofos.setBounds(80, 161, 86, 20);
		getContentPane().add(tfOrofos);
		tfOrofos.setColumns(10);
		
		JLabel label_11 = new JLabel("\u039A\u03C9\u03B4. \u03C3\u03C5\u03BD\u03B5\u03C1\u03B3\u03AC\u03C4\u03B7");
		label_11.setBounds(186, 213, 84, 14);
		getContentPane().add(label_11);
		
		tfKodSinergati = new JTextField();
		tfKodSinergati.setBounds(274, 210, 86, 20);
		getContentPane().add(tfKodSinergati);
		tfKodSinergati.setColumns(10);
		
		JLabel label_12 = new JLabel("\u03A4\u03CD\u03C0\u03BF\u03C2 \u039C\u03B7\u03C7\u03B1\u03BD.");
		label_12.setBounds(10, 189, 84, 14);
		getContentPane().add(label_12);
		
		tfTyposMix = new JTextField();
		tfTyposMix.setBounds(80, 186, 86, 20);
		getContentPane().add(tfTyposMix);
		tfTyposMix.setColumns(10);
		
		cbAnenergos = new JCheckBox("\u0391\u03BD\u03B5\u03BD\u03B5\u03C1\u03B3\u03CC\u03C2");
		cbAnenergos.setBounds(481, 156, 88, 23);
		getContentPane().add(cbAnenergos);
		
		cbIdiait = new JCheckBox("\u0399\u03B4\u03B9\u03B1\u03B9\u03C4\u03B5\u03C1\u03CC\u03C4\u03B7\u03C4\u03B1");
		cbIdiait.setBounds(481, 180, 97, 23);
		getContentPane().add(cbIdiait);
		
		JLabel label_13 = new JLabel("\u0397\u03BC\u03B5\u03C1. \u03B5\u03B3\u03BA\u03B1\u03C4.");
		label_13.setBounds(10, 210, 77, 14);
		getContentPane().add(label_13);
		
		tfInstallDate = new JTextField();
		tfInstallDate.setBounds(80, 210, 86, 20);
		getContentPane().add(tfInstallDate);
		tfInstallDate.setColumns(10);
		
		JLabel label_14 = new JLabel("\u03A9\u03C1\u03AC\u03C1\u03B9\u03BF");
		label_14.setBounds(384, 213, 46, 14);
		getContentPane().add(label_14);
		
		cbOraria = new JComboBox();
		cbOraria.setBounds(434, 209, 144, 22);
		getContentPane().add(cbOraria);
		
		cbEksParak = new JCheckBox("\u03B5\u03BE\u03B1\u03B9\u03C1. \u03C0\u03B1\u03C1.");
		cbEksParak.setBounds(373, 156, 106, 23);
		getContentPane().add(cbEksParak);
		
		JLabel label_15 = new JLabel("\u039F\u03B4\u03B7\u03B3\u03AF\u03B5\u03C2 \u03B5\u03C0\u03AD\u03BC\u03B2\u03B1\u03C3\u03B7\u03C2");
		label_15.setBounds(10, 235, 144, 14);
		getContentPane().add(label_15);
		
		JLabel label_16 = new JLabel("\u039F\u03B4\u03B7\u03B3\u03AF\u03B5\u03C2 \u03B9\u03B4\u03B9\u03B1\u03B9\u03C4\u03B5\u03C1\u03CC\u03C4\u03B7\u03C4\u03B1\u03C2");
		label_16.setBounds(310, 235, 127, 14);
		getContentPane().add(label_16);
		
		tpAe = new JTextPane();
		tpAe.setBounds(10, 251, 215, 84);
		getContentPane().add(tpAe);
		
		tpIdiat = new JTextPane();
		tpIdiat.setBounds(310, 251, 215, 84);
		getContentPane().add(tpIdiat);

		cbSelectForSearch = new JComboBox();
		cbSelectForSearch.setBounds(295, 0, 164, 22);
		cbSelectForSearch.addItem("Καμία επιλογή");
		cbSelectForSearch.addItem("Τηλεφωνικός αριθμός");
		cbSelectForSearch.addItem("Πελάτες με Α/Ε");
		cbSelectForSearch.addItem("Πελάτες με Ιδιαιτερότητα");
		cbSelectForSearch.setSelectedIndex(0);
		getContentPane().add(cbSelectForSearch);
		
		JLabel label1 = new JLabel("Αποτελέσματα αναζήτησης:");
		label1.setBounds(609, 4, 164, 14);
		getContentPane().add(label1);
		
		JButton btnClearList = new JButton("εκκαθάριση λίστας");
		btnClearList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefaultListModel lmToClear = (DefaultListModel) listApotelesmata.getModel();
				lmToClear.removeAllElements();
				//lmToClear.clear();
				listApotel.clear();
				clearForm();
			}
		});
		btnClearList.setBounds(609, 372, 164, 23);
		getContentPane().add(btnClearList);
		
		this.setSize(900,700);
	}
//setters+getters
	public ArrayList<helperDB> getListApotel(){
		return listApotel;
	}
}
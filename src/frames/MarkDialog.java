/*
 * Created by JFormDesigner on Sun Jan 26 07:48:24 GMT+05:00 2014
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import javax.xml.bind.JAXBElement;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Text;

import padeg.lib.Padeg;
import tablemodels.SvidTableModel;
import util.DocumentUtil;
import beans.Students;

import com.jgoodies.forms.factories.*;

/**
 * @author ????????
 */
public class MarkDialog extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8335855057103799693L;
	private boolean result= false;
	private Students s;
	public MarkDialog(Frame owner, Students s) {
		super(owner);
		initComponents();
		this.s = s;
		saveButton.addActionListener(this);
		saveButton.setName("OK");
		cancelButton.addActionListener(this);
		cancelButton.setName("Cancel");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
        // Добавляем ставку, но не закрываем окно
		if (src.getName().equals("OK")) {
	           try {
				print();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Docx4JException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	           this.dispose();
		}
	    if (src.getName().equals("Cancel")) {
	        result = false;
	        dispose();
	    }
		
	}
	
	private void print() throws IOException, Docx4JException{
	String[] s1 = new String[10];
	
	s1 = getInfo();
	
        
        Format formatter = new SimpleDateFormat("dd.MM.yyyy");
        HashMap<String, String> searchTerms = new HashMap<String, String>();
		searchTerms.put("varfam", s.getSurname());
		searchTerms.put("varname", s.getName());
		searchTerms.put("otchname", s.getPatronymicname());
		searchTerms.put("daeBirth", formatter.format(s.getDatebirth()));
		searchTerms.put("BirthPlace", s.getBirthplace());
		searchTerms.put("homeAddr", s.getAddress());
		
		if(s.getDocseries()!= null) {
			searchTerms.put("serPassp", s.getDocseries()); }
		if(s.getDocnumber()!= null) {
			searchTerms.put("numPassp", s.getDocnumber()); }
		if(s.getDoinfo()!= null) {
			searchTerms.put("docInfo", s.getDoinfo()); }
		else {
			searchTerms.put("docInfo", "       ");}
		if(s.getSvidInfo()!= null) {
		searchTerms.put("svidInfo", s.getSvidInfo());}
		String dt=new java.text.SimpleDateFormat("dd-MM-yy_hh_mm").format(java.util.Calendar.getInstance ().getTime());
		String filename="C:\\Водительская_карточка_"+dt+".rtf";
		DocumentUtil.searchAndReplace("C:\\vod_cart.doc",    // Source Document
				filename,                 // Result Document
				searchTerms); 
		// заполняем свидетельство
		
		WordprocessingMLPackage wordT = getTemplate("C:\\svidetelstvo.docx");
		if(s.getSvidInfo()!= null) {
			replacePlaceholder(wordT, s.getSvidInfo(), "svidInfo" );}
		replacePlaceholder(wordT, Padeg.getFIOPadegAS(s.getSurname(),s.getName(),s.getPatronymicname(),3), "FIO");
		replacePlaceholder(wordT, formatter.format(s.getGroups().getGruopstartdate()), "startDate" );
		replacePlaceholder(wordT, formatter.format(s.getGroups().getGroupenddate()), "endDate" );
		replacePlaceholder(wordT, s1[0], "mark1" );
		replacePlaceholder(wordT, s1[1], "m2" );
		replacePlaceholder(wordT, s1[2], "3m" );
		replacePlaceholder(wordT, s1[3], "pm" );
		replacePlaceholder(wordT, s1[4], "vm" );
		replacePlaceholder(wordT, formatter.format(s.getEkzData()), "ekzDate" );
		replacePlaceholder(wordT, s.getSurname() +" " + s.getName() + " " + s.getPatronymicname(), "IM" );
		writeDocxToStream(wordT, "C:\\Свидетельство_"+dt+".docx");
		
		
		/*if (Desktop.isDesktopSupported()) {
		       Desktop.getDesktop().open(new File(filename));
		     }*/
		JOptionPane.showMessageDialog(MarkDialog.this,
                "Документы успешно созданы в каталоге: " + filename, "Внимание", 1);
	}

	


private WordprocessingMLPackage getTemplate(String name) throws Docx4JException, FileNotFoundException {
  WordprocessingMLPackage template = WordprocessingMLPackage.load(new FileInputStream(new File(name)));
  return template;
 }

private static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
  List<Object> result = new ArrayList<Object>();
  if (obj instanceof JAXBElement) obj = ((JAXBElement<?>) obj).getValue();
 
  if (obj.getClass().equals(toSearch))
   result.add(obj);
  else if (obj instanceof ContentAccessor) {
   List<?> children = ((ContentAccessor) obj).getContent();
   for (Object child : children) {
    result.addAll(getAllElementFromObject(child, toSearch));
   }
 
  }
  return result;
 }

private void replacePlaceholder(WordprocessingMLPackage template, String name, String placeholder ) {
  List<Object> texts = getAllElementFromObject(template.getMainDocumentPart(), Text.class);
 
  for (Object text : texts) {
   Text textElement = (Text) text;
   if (textElement.getValue().equals(placeholder)) {
    textElement.setValue(name);
   }
  }
 }

private void writeDocxToStream(WordprocessingMLPackage template, String target) throws IOException, Docx4JException {
 File f = new File(target);
 template.save(f);
}
	
	public boolean getResult() {
        return result;
    }
	
	public String[] getInfo() {
		String[] st = new String[10];
		st[0] = (String) comboBox1.getSelectedItem();
		st[1] = (String) comboBox2.getSelectedItem();
		st[2] = (String) comboBox3.getSelectedItem();
		st[3] = (String) comboBox4.getSelectedItem();
		st[4] = (String) comboBox5.getSelectedItem();
		return st;
		
	}


	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel1 = new JPanel();
		panel3 = new JPanel();
		scrollPane1 = new JScrollPane();
		textArea1 = new JTextArea();
		comboBox1 = new JComboBox();
		panel4 = new JPanel();
		scrollPane2 = new JScrollPane();
		textArea2 = new JTextArea();
		comboBox2 = new JComboBox();
		panel5 = new JPanel();
		scrollPane3 = new JScrollPane();
		textArea3 = new JTextArea();
		comboBox3 = new JComboBox();
		panel6 = new JPanel();
		scrollPane4 = new JScrollPane();
		textArea4 = new JTextArea();
		comboBox4 = new JComboBox();
		panel7 = new JPanel();
		scrollPane5 = new JScrollPane();
		textArea5 = new JTextArea();
		comboBox5 = new JComboBox();
		panel2 = new JPanel();
		panel13 = new JPanel();
		saveButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("\u0412\u044b\u0431\u0435\u0440\u0438\u0442\u0435 \u043e\u0446\u0435\u043d\u043a\u0438");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== panel1 ========
		{
			panel1.setBorder(new CompoundBorder(
				new TitledBorder(" "),
				Borders.DLU2_BORDER));
			panel1.setPreferredSize(new Dimension(605, 270));
			panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

			//======== panel3 ========
			{
				panel3.setPreferredSize(new Dimension(315, 38));
				panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

				//======== scrollPane1 ========
				{
					scrollPane1.setBorder(null);

					//---- textArea1 ----
					textArea1.setText("\u041e\u0441\u043d\u043e\u0432\u044b \u0437\u0430\u043a\u043e\u043d\u043e\u0434\u0430\u0442\u0435\u043b\u044c\u0441\u0442\u0432\u0430 \u0432   \u0441\u0444\u0435\u0440\u0435 \u0434\u043e\u0440\u043e\u0436\u043d\u043e\u0433\u043e \u0434\u0432\u0438\u0436\u0435\u043d\u0438\u044f");
					textArea1.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
					textArea1.setEditable(false);
					textArea1.setRows(2);
					textArea1.setBackground(new Color(235, 233, 237));
					textArea1.setBorder(null);
					textArea1.setWrapStyleWord(true);
					scrollPane1.setViewportView(textArea1);
				}
				panel3.add(scrollPane1);
			}
			panel1.add(panel3);

			//---- comboBox1 ----
			comboBox1.setPreferredSize(new Dimension(100, 20));
			comboBox1.setModel(new DefaultComboBoxModel(new String[] {
				"\u041e\u0442\u043b\u0438\u0447\u043d\u043e",
				"\u0425\u043e\u0440\u043e\u0448\u043e",
				"\u0423\u0434\u043e\u0432\u043b\u0435\u0442\u0432\u043e\u0440\u0438\u0442\u0435\u043b\u044c\u043d\u043e"
			}));
			panel1.add(comboBox1);

			//======== panel4 ========
			{
				panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

				//======== scrollPane2 ========
				{
					scrollPane2.setBorder(null);
					scrollPane2.setPreferredSize(new Dimension(295, 28));

					//---- textArea2 ----
					textArea2.setText("\u0423\u0441\u0442\u0440\u043e\u0439\u0441\u0442\u0432\u043e \u0438 \u0442\u0435\u0445\u043d\u0438\u0447\u0435\u0441\u043a\u043e\u0435 \u043e\u0431\u0441\u043b\u0443\u0436\u0438\u0432\u0430\u043d\u0438\u0435 \u0442\u0440\u0430\u043d\u0441\u043f\u043e\u0440\u0442\u043d\u044b\u0445 \u0441\u0440\u0435\u0434\u0441\u0442\u0432 (\u0437\u0430\u0447\u0435\u0442)");
					textArea2.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
					textArea2.setEditable(false);
					textArea2.setRows(2);
					textArea2.setBackground(new Color(235, 233, 237));
					textArea2.setBorder(null);
					textArea2.setColumns(40);
					textArea2.setLineWrap(true);
					textArea2.setWrapStyleWord(true);
					textArea2.setPreferredSize(new Dimension(295, 28));
					scrollPane2.setViewportView(textArea2);
				}
				panel4.add(scrollPane2);

				//---- comboBox2 ----
				comboBox2.setPreferredSize(new Dimension(100, 20));
				comboBox2.setModel(new DefaultComboBoxModel(new String[] {
					"\u0417\u0430\u0447\u0435\u0442",
					"\u041d\u0435\u0437\u0430\u0447\u0435\u0442"
				}));
				panel4.add(comboBox2);
			}
			panel1.add(panel4);

			//======== panel5 ========
			{
				panel5.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

				//======== scrollPane3 ========
				{
					scrollPane3.setBorder(null);
					scrollPane3.setPreferredSize(new Dimension(295, 28));

					//---- textArea3 ----
					textArea3.setText("\u041e\u0441\u043d\u043e\u0432\u044b \u0431\u0435\u0437\u043e\u043f\u0430\u0441\u043d\u043e\u0433\u043e \u0443\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u0438\u044f \u0442\u0440\u0430\u043d\u0441\u043f\u043e\u0440\u0442\u043d\u044b\u043c \u0441\u0440\u0435\u0434\u0441\u0442\u0432\u043e\u043c");
					textArea3.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
					textArea3.setEditable(false);
					textArea3.setRows(2);
					textArea3.setBackground(new Color(235, 233, 237));
					textArea3.setBorder(null);
					textArea3.setColumns(40);
					textArea3.setLineWrap(true);
					textArea3.setWrapStyleWord(true);
					textArea3.setPreferredSize(new Dimension(295, 28));
					scrollPane3.setViewportView(textArea3);
				}
				panel5.add(scrollPane3);

				//---- comboBox3 ----
				comboBox3.setPreferredSize(new Dimension(100, 20));
				comboBox3.setModel(new DefaultComboBoxModel(new String[] {
					"\u041e\u0442\u043b\u0438\u0447\u043d\u043e",
					"\u0425\u043e\u0440\u043e\u0448\u043e",
					"\u0423\u0434\u043e\u0432\u043b\u0435\u0442\u0432\u043e\u0440\u0438\u0442\u0435\u043b\u044c\u043d\u043e"
				}));
				panel5.add(comboBox3);
			}
			panel1.add(panel5);

			//======== panel6 ========
			{
				panel6.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

				//======== scrollPane4 ========
				{
					scrollPane4.setBorder(null);
					scrollPane4.setPreferredSize(new Dimension(295, 26));

					//---- textArea4 ----
					textArea4.setText("\u041f\u0435\u0440\u0432\u0430\u044f \u043f\u043e\u043c\u043e\u0449\u044c");
					textArea4.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
					textArea4.setEditable(false);
					textArea4.setRows(1);
					textArea4.setBackground(new Color(235, 233, 237));
					textArea4.setBorder(null);
					textArea4.setColumns(40);
					textArea4.setLineWrap(true);
					textArea4.setWrapStyleWord(true);
					textArea4.setPreferredSize(new Dimension(295, 26));
					scrollPane4.setViewportView(textArea4);
				}
				panel6.add(scrollPane4);

				//---- comboBox4 ----
				comboBox4.setPreferredSize(new Dimension(100, 20));
				comboBox4.setModel(new DefaultComboBoxModel(new String[] {
					"\u041e\u0442\u043b\u0438\u0447\u043d\u043e",
					"\u0425\u043e\u0440\u043e\u0448\u043e",
					"\u0423\u0434\u043e\u0432\u043b\u0435\u0442\u0432\u043e\u0440\u0438\u0442\u0435\u043b\u044c\u043d\u043e"
				}));
				panel6.add(comboBox4);
			}
			panel1.add(panel6);

			//======== panel7 ========
			{
				panel7.setPreferredSize(new Dimension(425, 30));
				panel7.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

				//======== scrollPane5 ========
				{
					scrollPane5.setBorder(null);
					scrollPane5.setPreferredSize(new Dimension(295, 26));

					//---- textArea5 ----
					textArea5.setText("\u0412\u043e\u0436\u0434\u0435\u043d\u0438\u0435");
					textArea5.setFont(new Font("Trebuchet MS", Font.PLAIN, 11));
					textArea5.setEditable(false);
					textArea5.setRows(1);
					textArea5.setBackground(new Color(235, 233, 237));
					textArea5.setBorder(null);
					textArea5.setColumns(40);
					textArea5.setLineWrap(true);
					textArea5.setWrapStyleWord(true);
					textArea5.setPreferredSize(new Dimension(295, 20));
					scrollPane5.setViewportView(textArea5);
				}
				panel7.add(scrollPane5);

				//---- comboBox5 ----
				comboBox5.setPreferredSize(new Dimension(100, 20));
				comboBox5.setModel(new DefaultComboBoxModel(new String[] {
					"\u041e\u0442\u043b\u0438\u0447\u043d\u043e",
					"\u0425\u043e\u0440\u043e\u0448\u043e",
					"\u0423\u0434\u043e\u0432\u043b\u0435\u0442\u0432\u043e\u0440\u0438\u0442\u0435\u043b\u044c\u043d\u043e"
				}));
				panel7.add(comboBox5);
			}
			panel1.add(panel7);
		}
		contentPane.add(panel1, BorderLayout.NORTH);

		//======== panel2 ========
		{
			panel2.setPreferredSize(new Dimension(1733, 50));
			panel2.setLayout(new FlowLayout());

			//======== panel13 ========
			{
				panel13.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 5));

				//---- saveButton ----
				saveButton.setSelectedIcon(new ImageIcon(getClass().getResource("/images/Nnm 16/check.png")));
				saveButton.setIcon(new ImageIcon(getClass().getResource("/images/check.png")));
				saveButton.setText("\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c");
				panel13.add(saveButton);

				//---- cancelButton ----
				cancelButton.setText("\u041e\u0442\u043c\u0435\u043d\u0430");
				cancelButton.setIcon(new ImageIcon(getClass().getResource("/images/logout.png")));
				panel13.add(cancelButton);
			}
			panel2.add(panel13);
		}
		contentPane.add(panel2, BorderLayout.CENTER);
		setSize(490, 365);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel1;
	private JPanel panel3;
	private JScrollPane scrollPane1;
	private JTextArea textArea1;
	private JComboBox comboBox1;
	private JPanel panel4;
	private JScrollPane scrollPane2;
	private JTextArea textArea2;
	private JComboBox comboBox2;
	private JPanel panel5;
	private JScrollPane scrollPane3;
	private JTextArea textArea3;
	private JComboBox comboBox3;
	private JPanel panel6;
	private JScrollPane scrollPane4;
	private JTextArea textArea4;
	private JComboBox comboBox4;
	private JPanel panel7;
	private JScrollPane scrollPane5;
	private JTextArea textArea5;
	private JComboBox comboBox5;
	private JPanel panel2;
	private JPanel panel13;
	private JButton saveButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

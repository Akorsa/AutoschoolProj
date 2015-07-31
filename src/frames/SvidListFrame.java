/*
 * Created by JFormDesigner on Tue Jan 07 12:27:23 GMT+05:00 2014
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Date;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.xml.bind.JAXBElement;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Text;

import padeg.lib.Padeg;

import rmi.AutoschoolRmiInterface;
import tablemodels.SvidTableModel;
import util.DocumentUtil;
import beans.Groups;
import beans.Students;

import com.jgoodies.forms.factories.*;

/**
 * @author ????????
 */
public class SvidListFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 443215134237693540L;
	private AutoschoolRmiInterface ari; 
	private Groups g = null;
	

	public SvidListFrame(ZamDirectPRFrame zamDirectPRFrame,
			AutoschoolRmiInterface ari, Groups group) {
		initComponents();
		checkButton.addActionListener(this);
		checkButton.setName("Check");
		removeButton.addActionListener(this);
		removeButton.setName("Remove");
		numerButton.addActionListener(this);
		numerButton.setName("SetSvid");
		printButton.addActionListener(this);
		printButton.setName("Print");
		this.ari = ari;
		this.g = group;
		loadStudents(g);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Component) {
			Component c = (Component) e.getSource();
			
			if (c.getName().equals("Check")) {
            	check();
            }
			if (c.getName().equals("Remove")) {
            	remove();
            }
			if (c.getName().equals("SetSvid")) {
				setSvid();
            }
			if (c.getName().equals("Print")) {
				try {
					print();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
	
		}	
	}
	
	
	
	private void print() throws IOException, Docx4JException {
		SvidTableModel stm = (SvidTableModel) groupTable.getModel();
		if (groupTable.getSelectedRow() >= 0) {
			Students s = stm.getStudent(groupTable.getSelectedRow());
			MarkDialog md = new MarkDialog(SvidListFrame.this,s);
			md.setVisible(true);
			
			
		} else {
	        JOptionPane.showMessageDialog(SvidListFrame.this,
	                "Необходимо выделить запись в таблице!", "Внимание", 2);
	    }
	}
		

	private void setSvid() {
		SvidTableModel stm = (SvidTableModel) groupTable.getModel();
		if (groupTable.getSelectedRow() >= 0) {
	        Students s = stm.getStudent(groupTable.getSelectedRow());
	        if (s.getSvidInfo()!=null) {
	        	JOptionPane.showMessageDialog(SvidListFrame.this,
                        "У данного учащегося уже есть запись о выдаче свид-ва!", "Внимание", 2);
	        } else {
	        	if(s.getEkzFlag()!= null) {
	        		if(s.getEkzFlag().equals("Да")) {
	        			SvidDialog sd = new SvidDialog(SvidListFrame.this,s,ari);
	        			sd.setModal(true);
	        			sd.setVisible(true);
	        			if (sd.getResult()) {
	        				loadStudents(g);
	        			}
	        		} 
	        	}else {
	        		JOptionPane.showMessageDialog(SvidListFrame.this,
	                        "У данного учащегося нет отметки о сдаче вн. экзамена! Выдача свид. невозможна.", "Внимание", 2);
	        	}
	        } 
		} else {
            JOptionPane.showMessageDialog(SvidListFrame.this,
                    "Необходимо выделить запись в таблице!", "Внимание", 2);
        }	
	}

	private void remove() {
		SvidTableModel stm = (SvidTableModel) groupTable.getModel();
		if (groupTable.getSelectedRow() >= 0) {
	        Students s = stm.getStudent(groupTable.getSelectedRow());
	        if (s.getEkzFlag().equals("Да")) {
	        	if (JOptionPane.showOptionDialog(
	        			SvidListFrame.this, 
						  "Вы хотите удалить отметку?", 
						  "Удаление отметки", 
						  JOptionPane.YES_NO_OPTION, 
						  JOptionPane.ERROR_MESSAGE, 
						  null, 
						  new Object[]{"Да", "Нет"}, 
						  "Да") == JOptionPane.YES_OPTION) {
	    
	        	s.setEkzFlag(null);
	        	s.setEkzData(null);
	        	s.setSvidInfo(null);
	        	try {
					ari.updateStudent(s);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
	        	loadStudents(g);
	        } 
	        }else {
	        	JOptionPane.showMessageDialog(SvidListFrame.this,
                        "У данного учащегося нет отметки о сдаче вн. экзамена!", "Внимание", 2);
	        } 
		} else {
            JOptionPane.showMessageDialog(SvidListFrame.this,
                    "Необходимо выделить запись в таблице!", "Внимание", 2);
        }	   
	}

	private void check() {
		SvidTableModel stm = (SvidTableModel) groupTable.getModel();
		if (groupTable.getSelectedRow() >= 0) {
	        Students s = stm.getStudent(groupTable.getSelectedRow());
	        if(s.getEkzFlag()!= null) {
	        	if (s.getEkzFlag().equals("Да")) {
	        		JOptionPane.showMessageDialog(SvidListFrame.this,
                        "У данного учащегося уже есть отметка о сдаче вн. экзамена!", "Внимание", 2);
	        	}
	        }else {
	        	s.setEkzFlag("Да");
	        	EkzDataDialog ed = new EkzDataDialog(SvidListFrame.this);
	        	ed.setModal(true);
    			ed.setVisible(true);
    			if (ed.getResult()) {
    				Date dt = ed.getDate();
    				s.setEkzData(dt);
    			}
    			
	        	try {
					ari.updateStudent(s);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
	        	loadStudents(g);
	        } 
		} else {
            JOptionPane.showMessageDialog(SvidListFrame.this,
                    "Необходимо выделить запись в таблице!", "Внимание", 2);
        }	        	
	}

	protected void loadStudents(Groups group) {
		try {
			List<Students> stud = ari.getStudentsFromGroup(group.getGroupid());
			updateProgress(stud);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
	
	
	private void updateProgress(final List<Students> stud) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (stud!= null){
					groupTable.setModel(new SvidTableModel(stud));
					groupTable.getColumnModel().getColumn(0).setPreferredWidth(100);
					groupTable.getColumnModel().getColumn(1).setPreferredWidth(120);
					groupTable.getColumnModel().getColumn(2).setPreferredWidth(80);
					groupTable.getColumnModel().getColumn(3).setPreferredWidth(150);
					groupTable.getColumnModel().getColumn(4).setPreferredWidth(100);
					groupTable.getColumnModel().getColumn(5).setPreferredWidth(120);
					groupTable.getColumnModel().getColumn(6).setPreferredWidth(120);
                    }
			}
		});
		
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		grupNameLabl = new JLabel();
		scrollPane1 = new JScrollPane();
		groupTable = new JTable();
		panel1 = new JPanel();
		panel2 = new JPanel();
		checkButton = new JButton();
		removeButton = new JButton();
		panel3 = new JPanel();
		numerButton = new JButton();
		printButton = new JButton();

		//======== this ========
		setTitle("\u0421\u0432\u0438\u0434\u0435\u0442\u0435\u043b\u044c\u0441\u0442\u0432\u0430 \u043e\u0431 \u043e\u043a\u043e\u043d\u0447\u0430\u043d\u0438\u0438");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//---- grupNameLabl ----
		grupNameLabl.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		contentPane.add(grupNameLabl, BorderLayout.NORTH);

		//======== scrollPane1 ========
		{

			//---- groupTable ----
			groupTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			groupTable.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
			groupTable.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null},
				},
				new String[] {
					"\u041e\u0442\u043c\u0435\u0442\u043a\u0430 \u043e \u0441\u0434\u0430\u0447\u0435 \u0432\u043d\u0443\u0442\u0440. \u044d\u043a\u0437", "\u0424\u0430\u043c\u0438\u043b\u0438\u044f", "\u0418\u043c\u044f ", "\u041e\u0442\u0447\u0435\u0441\u0442\u0432\u043e", "\u0414\u0430\u0442\u0430 \u0440\u043e\u0436\u0434\u0435\u043d\u0438\u044f", "\u0414\u0430\u0442\u0430 \u0441\u0434\u0430\u0447\u0438 \u0432\u043d. \u044d\u043a\u0437\u0430\u043c\u0435\u043d\u0430", "\u0421\u0432\u0438\u0434\u0435\u0442\u0435\u043b\u044c\u0441\u0442\u0432\u043e"
				}
			));
			{
				TableColumnModel cm = groupTable.getColumnModel();
				cm.getColumn(0).setPreferredWidth(100);
				cm.getColumn(1).setPreferredWidth(120);
				cm.getColumn(2).setPreferredWidth(80);
				cm.getColumn(3).setPreferredWidth(150);
				cm.getColumn(4).setPreferredWidth(100);
				cm.getColumn(5).setPreferredWidth(120);
				cm.getColumn(6).setPreferredWidth(120);
			}
			scrollPane1.setViewportView(groupTable);
		}
		contentPane.add(scrollPane1, BorderLayout.CENTER);

		//======== panel1 ========
		{
			panel1.setBorder(new CompoundBorder(
				new TitledBorder(""),
				Borders.DLU2_BORDER));
			panel1.setPreferredSize(new Dimension(823, 95));
			panel1.setLayout(new BorderLayout());

			//======== panel2 ========
			{
				panel2.setBorder(new CompoundBorder(
					new TitledBorder("\u041e\u0442\u043c\u0435\u0442\u043a\u0430 \u043e \u0441\u0434\u0430\u0447\u0435 \u0432\u043d\u0443\u0442\u0440. \u044d\u043a\u0437"),
					Borders.DLU2_BORDER));
				panel2.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
				panel2.setPreferredSize(new Dimension(237, 20));
				panel2.setLayout(new FlowLayout());

				//---- checkButton ----
				checkButton.setText("\u041e\u0442\u043c\u0435\u0442\u0438\u0442\u044c");
				checkButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
				checkButton.setIcon(new ImageIcon(getClass().getResource("/images/Nnm 16/check.png")));
				panel2.add(checkButton);

				//---- removeButton ----
				removeButton.setText("\u0423\u0434\u0430\u043b\u0438\u0442\u044c");
				removeButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
				removeButton.setIcon(new ImageIcon(getClass().getResource("/images/Nnm 16/sign-out.png")));
				panel2.add(removeButton);
			}
			panel1.add(panel2, BorderLayout.WEST);

			//======== panel3 ========
			{
				panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 25));

				//---- numerButton ----
				numerButton.setText("\u041f\u0440\u0438\u0441\u0432\u043e\u0438\u0442\u044c \u043d\u043e\u043c\u0435\u0440 \u0441\u0432\u0438\u0434\u0435\u0442\u0435\u043b\u044c\u0441\u0442\u0432\u0430");
				numerButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
				numerButton.setIcon(new ImageIcon(getClass().getResource("/images/cost.png")));
				panel3.add(numerButton);

				//---- printButton ----
				printButton.setText("\u041f\u0435\u0447\u0430\u0442\u044c \u0441\u0432\u0438\u0434\u0435\u0442\u0435\u043b\u044c\u0441\u0442\u0432\u0430");
				printButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
				printButton.setIcon(new ImageIcon(getClass().getResource("/images/print.png")));
				panel3.add(printButton);
			}
			panel1.add(panel3, BorderLayout.CENTER);
		}
		contentPane.add(panel1, BorderLayout.SOUTH);
		setSize(800, 360);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel grupNameLabl;
	private JScrollPane scrollPane1;
	private JTable groupTable;
	private JPanel panel1;
	private JPanel panel2;
	private JButton checkButton;
	private JButton removeButton;
	private JPanel panel3;
	private JButton numerButton;
	private JButton printButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables


	
}

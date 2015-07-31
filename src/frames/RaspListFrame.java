/*
 * Created by JFormDesigner on Wed Jan 08 15:29:16 GMT+05:00 2014
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

import beans.Groups;
import beans.Schedules;
import rmi.AutoschoolRmiInterface;
import tablemodels.RaspTableModel;
import util.ExcelJob;


/**
 * @author ????????
 */
public class RaspListFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AutoschoolRmiInterface ari; 
	private Groups g = null;
	private Date date;
	private Date date2;
	public RaspListFrame(ZamDirectURFrame ZamDirectURFrame, AutoschoolRmiInterface ari,Groups gr, Date date, Date date2) {
		initComponents();
		this.ari = ari;
		this.g = gr;
		this.date = date;
		this.date2 = date2;
		loadRasp(g);
		button1.setName("addRasp");
		button2.setName("editRasp");
		button4.setName("delRasp");
		button3.setName("printRasp");
		button1.addActionListener(this);
		button2.addActionListener(this);
		button4.addActionListener(this);
		button3.addActionListener(this);
	}
	
	private List<Schedules> loadRasp(Groups group) {
		List<Schedules> stud = null;
		try {
			stud = (List<Schedules>) ari.getRaspList(group.getGroupid(),date,date2);
			if (stud.isEmpty()){
				 JOptionPane.showMessageDialog(RaspListFrame.this,
                        "Записи в данном интервале времени не найдены!", "Внимание", 2);
				 this.setVisible(false);
			} else {
				 this.setVisible(true);
				updateProgress(stud);
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Component) {
			Component c = (Component) e.getSource();
			
			if (c.getName().equals("addRasp")) {
				addRasp();
            }
			if (c.getName().equals("editRasp")) {
				editRasp();
            }
			if (c.getName().equals("delRasp")) {
				delRasp();
				//prin();
            }
			if (c.getName().equals("printRasp")) {
				//delRasp();
				prin();
            }
	
		}	
	}
	
	
	
	
	
	private void prin() {
		List<Schedules> lst = loadRasp(g);
		ExcelJob.exelSet(lst,g,date,date2);
		
	}

	private void delRasp() {
		Thread t = new Thread() {
			public void run() {
				RaspTableModel stm = (RaspTableModel) groupTable.getModel();
					// Проверяем - выделен ли хоть какой-нибудь элемент
					if (groupTable.getSelectedRow() >= 0) {
						if (JOptionPane.showOptionDialog(
								RaspListFrame.this, 
								  "Вы хотите удалить запись?", 
								  "Удаление записи", 
								  JOptionPane.YES_NO_OPTION, 
								  JOptionPane.ERROR_MESSAGE, 
								  null, 
								  new Object[]{"Да", "Нет"}, 
								  "Да") == JOptionPane.YES_OPTION) {
							Schedules s = stm.getRasp(groupTable.getSelectedRow());
							try {
								ari.deleteRasp(s);
								loadRasp(g);
							} catch (RemoteException e) {
                                JOptionPane.showMessageDialog(RaspListFrame.this, e.getMessage());
                            }
						}
					} // Если элемент не выделен - сообщаем пользователю, что это необходимо
					else {
						 JOptionPane.showMessageDialog(RaspListFrame.this,
	                                "Необходимо выделить запись в таблице!", "Внимание", 2);
                    }
				}
		};
		t.start();
		
	}

	private void editRasp() {
		Thread t = new Thread() {
			public void run() {
                    RaspTableModel stm = (RaspTableModel) groupTable.getModel();
                    // Проверяем - выделен ли хоть какой-нибудь элемент
                    if (groupTable.getSelectedRow() >= 0) {
                    	Schedules s = stm.getRasp(groupTable.getSelectedRow());
                    	RaspDialog gd =  new RaspDialog(RaspListFrame.this,ari);
                            gd.setRasp(s);
                            gd.setModal(true);
                            gd.setVisible(true);
                            if (gd.getResult()) {
                            	Schedules sh = gd.getRasp();
                            	try {
            						ari.updateRasp(sh);
            						loadRasp(g);
            					} catch (RemoteException e) {
            						 JOptionPane.showMessageDialog(RaspListFrame.this, e.getMessage());
            					}
                            }
                    } // Если элемент не выделен - сообщаем пользователю, что это необходимо
                    else {
                        JOptionPane.showMessageDialog(RaspListFrame.this,
                                "Необходимо выделить запись в таблице!", "Внимание", 2);
                    }
                
            }
        };
        t.start();
		
	}

	private void addRasp() {
		Thread t = new Thread() {
            public void run() {
            	RaspDialog gd =  new RaspDialog(RaspListFrame.this,ari);
				gd.setModal(true);
				gd.setVisible(true);
                if (gd.getResult()) {
                	Schedules sh = gd.getRasp();
				    try {
						ari.insertRasp(sh);
						loadRasp(g);
					} catch (RemoteException e) {
						 JOptionPane.showMessageDialog(RaspListFrame.this, e.getMessage());
					}
				}
            }
        };
        t.start();
		
	}
	
	private void updateProgress(final List<Schedules> stud) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (stud!= null){
					groupTable.setModel(new RaspTableModel(stud));
					groupTable.getColumnModel().getColumn(0).setPreferredWidth(100);
					groupTable.getColumnModel().getColumn(1).setPreferredWidth(160);
					groupTable.getColumnModel().getColumn(2).setPreferredWidth(180);
					groupTable.getColumnModel().getColumn(3).setPreferredWidth(140);
					groupTable.getColumnModel().getColumn(4).setPreferredWidth(140);
                    }
			}
		});
		
	}


	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		scrollPane1 = new JScrollPane();
		groupTable = new JTable();
		groupTable.getTableHeader().setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		panel1 = new JPanel();
		button1 = new JButton();
		button2 = new JButton();
		button4 = new JButton();
		button3 = new JButton();
		panel2 = new JPanel();

		//======== this ========
		setTitle("\u0420\u0430\u0441\u043f\u0438\u0441\u0430\u043d\u0438\u044f \u0437\u0430\u043d\u044f\u0442\u0438\u0439");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== scrollPane1 ========
		{

			//---- groupTable ----
			groupTable.setModel(new DefaultTableModel(
				new Object[][] {
					{"", null, null, null, null},
					{null, "", null, null, null},
				},
				new String[] {
					"\u0413\u0440\u0443\u043f\u043f\u0430", "\u0422\u0435\u043c\u0430", "\u041f\u0440\u0435\u043f\u043e\u0434\u0430\u0432\u0430\u0442\u0435\u043b\u044c", "\u0414\u0430\u0442\u0430 \u0438 \u0432\u0440\u0435\u043c\u044f \u043d\u0430\u0447\u0430\u043b\u0430 \u0437\u0430\u043d\u044f\u0442\u0438\u0439", "\u0414\u0430\u0442\u0430 \u0438 \u0432\u0440\u0435\u043c\u044f \u043a\u043e\u043d\u0446\u0430 \u0437\u0430\u043d\u044f\u0442\u0438\u0439"
				}
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 8562035629416988284L;
				Class<?>[] columnTypes = new Class<?>[] {
					String.class, String.class, Date.class, String.class, Object.class
				};
				boolean[] columnEditable = new boolean[] {
					false, false, false, false, true
				};
				@Override
				public Class<?> getColumnClass(int columnIndex) {
					return columnTypes[columnIndex];
				}
				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					return columnEditable[columnIndex];
				}
			});
			{
				TableColumnModel cm = groupTable.getColumnModel();
				cm.getColumn(0).setPreferredWidth(100);
				cm.getColumn(1).setResizable(false);
				cm.getColumn(1).setPreferredWidth(120);
				cm.getColumn(2).setResizable(false);
				cm.getColumn(2).setPreferredWidth(130);
				cm.getColumn(3).setResizable(false);
				cm.getColumn(3).setPreferredWidth(140);
				cm.getColumn(4).setPreferredWidth(140);
			}
			groupTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			groupTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
			scrollPane1.setViewportView(groupTable);
		}
		contentPane.add(scrollPane1, BorderLayout.CENTER);

		//======== panel1 ========
		{
			panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 13));

			//---- button1 ----
			button1.setText("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0437\u0430\u043f\u0438\u0441\u044c");
			button1.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Add.png")));
			button1.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
			panel1.add(button1);

			//---- button2 ----
			button2.setText("\u0420\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c");
			button2.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Pencil_2.png")));
			button2.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
			panel1.add(button2);

			//---- button4 ----
			button4.setText("\u0423\u0434\u0430\u043b\u0438\u0442\u044c");
			button4.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Denided.png")));
			button4.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
			panel1.add(button4);

			//---- button3 ----
			button3.setText("\u041f\u0435\u0447\u0430\u0442\u044c \u0440\u0430\u0441\u043f\u0438\u0441\u0430\u043d\u0438\u044f");
			button3.setIcon(new ImageIcon(getClass().getResource("/images/print.png")));
			panel1.add(button3);
		}
		contentPane.add(panel1, BorderLayout.SOUTH);

		//======== panel2 ========
		{
			panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
		}
		contentPane.add(panel2, BorderLayout.NORTH);
		setSize(680, 530);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JScrollPane scrollPane1;
	private JTable groupTable;
	private JPanel panel1;
	private JButton button1;
	private JButton button2;
	private JButton button4;
	private JButton button3;
	private JPanel panel2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	
}

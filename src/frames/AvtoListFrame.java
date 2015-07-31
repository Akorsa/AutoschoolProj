/*
 * Created by JFormDesigner on Fri Dec 20 20:04:59 GMT+05:00 2013
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

import rmi.AutoschoolRmiInterface;
import tablemodels.AvtoTableModel;

import beans.Avto;

/**
 * @author ????????
 */
public class AvtoListFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6710148153679889458L;
	private AutoschoolRmiInterface ari; 
	public AvtoListFrame(Frame frame, AutoschoolRmiInterface ar) {
		initComponents();
		this.ari = ar;
		loadAvtos();
		button1.setName("addTeacher");
		button2.setName("editTeacher");
		button3.setName("cancel");
		button4.setName("delTeacher");
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Component) {
			Component c = (Component) e.getSource();
			if (c.getName().equals("addTeacher")) {
				insertAvto();
			}
			if (c.getName().equals("editTeacher")) {
            	updateAvto();
            }
			if (c.getName().equals("cancel")) {
				dispose();
            }
			if (c.getName().equals("delTeacher")) {
				delAvto();
            }
		
		}
	}
	
	private void delAvto() {
		Thread t = new Thread() {
			public void run() {
				AvtoTableModel stm = (AvtoTableModel) groupTable.getModel();
					// Проверяем - выделен ли хоть какой-нибудь элемент
					if (groupTable.getSelectedRow() >= 0) {
						if (JOptionPane.showOptionDialog(
								AvtoListFrame.this, 
								  "Вы хотите удалить запись?", 
								  "Удаление записи", 
								  JOptionPane.YES_NO_OPTION, 
								  JOptionPane.ERROR_MESSAGE, 
								  null, 
								  new Object[]{"Да", "Нет"}, 
								  "Да")== JOptionPane.YES_OPTION) {
							Avto s = stm.getAvto(groupTable.getSelectedRow());
							try {
								ari.deleteAvto(s);
								loadAvtos();
							} catch (RemoteException e) {
                                JOptionPane.showMessageDialog(AvtoListFrame.this, e.getMessage());
                            }
						}
					} // Если элемент не выделен - сообщаем пользователю, что это необходимо
					else {
						 JOptionPane.showMessageDialog(AvtoListFrame.this,
	                                "Необходимо выделить запись в таблице!", "Внимание", 2);
                    }
				}
		};
		t.start();
		
	}

	private void updateAvto() {
		Thread t = new Thread() {
			public void run() {
				AvtoTableModel stm = (AvtoTableModel) groupTable.getModel();
                    // Проверяем - выделен ли хоть какой-нибудь элемент
                    if (groupTable.getSelectedRow() >= 0) {
                    	Avto s = stm.getAvto(groupTable.getSelectedRow());
                    	AvtoDialog gd =  new AvtoDialog(AvtoListFrame.this);
                		    gd.setAvto(s);
                            gd.setModal(true);
                            gd.setVisible(true);
                            if (gd.getResult()) {
                            	Avto s1 = gd.getAvto();
                            	try {
            						ari.updateAvto(s1);
            						loadAvtos();
            					} catch (RemoteException e) {
            						 JOptionPane.showMessageDialog(AvtoListFrame.this, e.getMessage());
            					}
                            }
                    } // Если элемент не выделен - сообщаем пользователю, что это необходимо
                    else {
                        JOptionPane.showMessageDialog(AvtoListFrame.this,
                                "Необходимо выделить запись в таблице!", "Внимание", 2);
                    }
                
            }
        };
        t.start();
		
	}

	private void insertAvto() {
		Thread t = new Thread() {
            public void run() {
            	AvtoDialog gd =  new AvtoDialog(AvtoListFrame.this);
				gd.setModal(true);
				gd.setVisible(true);
                if (gd.getResult()) {
                	Avto g = gd.getAvto();
				    try {
						ari.insertAvto(g);
						loadAvtos();
					} catch (RemoteException e) {
						 JOptionPane.showMessageDialog(AvtoListFrame.this, e.getMessage());
					}
				}
            }
        };
        t.start();
		
	}

	private List<Avto> loadAvtos() {
		List<Avto> stud = null;
		try {
			stud = (List<Avto>) ari.getAvtoList();
			updateProgress(stud);
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
		
	}
	
	private void updateProgress(final List<Avto> stud) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (stud!= null){
					groupTable.setModel(new AvtoTableModel(stud));
					groupTable.getColumnModel().getColumn(0).setPreferredWidth(100);
					groupTable.getColumnModel().getColumn(1).setPreferredWidth(100);
					groupTable.getColumnModel().getColumn(2).setPreferredWidth(80);
					groupTable.getColumnModel().getColumn(3).setPreferredWidth(110);
					groupTable.getColumnModel().getColumn(4).setPreferredWidth(110);
					groupTable.getColumnModel().getColumn(5).setPreferredWidth(110);
					groupTable.getColumnModel().getColumn(6).setPreferredWidth(160);
					groupTable.getColumnModel().getColumn(7).setPreferredWidth(120);
					groupTable.getColumnModel().getColumn(8).setPreferredWidth(150);
					groupTable.getColumnModel().getColumn(9).setPreferredWidth(150);
					groupTable.getColumnModel().getColumn(10).setPreferredWidth(150);
                    }
			}
		});
		
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		scrollPane1 = new JScrollPane();
		groupTable = new JTable();
		panel1 = new JPanel();
		button1 = new JButton();
		button2 = new JButton();
		button4 = new JButton();
		button3 = new JButton();
		panel2 = new JPanel();
		groupTable.getTableHeader().setFont(new Font("Trebuchet MS", Font.BOLD, 14));

		//======== this ========
		setTitle("\u0423\u0447\u0435\u0431\u043d\u044b\u0435 \u0430\u0432\u0442\u043e\u043c\u043e\u0431\u0438\u043b\u0438");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== scrollPane1 ========
		{

			//---- groupTable ----
			groupTable.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null, null, null, null, null, null, null, "", null, null},
					{null, "", null, null, null, null, null, null, null, null, null, null},
				},
				new String[] {
					"\u041c\u0430\u0440\u043a\u0430 \u0430\u0432\u0442\u043e", "\u0413\u043e\u0441. \u043d\u043e\u043c\u0435\u0440", "\u0426\u0432\u0435\u0442", "\u0413\u043e\u0434 \u0432\u044b\u043f\u0443\u0441\u043a\u0430", "\u2116 \u0434\u0432\u0438\u0433\u0430\u0442\u0435\u043b\u044f", "\u0413\u0430\u0440\u0430\u0436\u043d\u044b\u0439 \u2116", "\u0421\u0435\u0440\u0438\u044f \u0438 \u2116 \u0442\u0435\u0445. \u043f\u0430\u0441\u043f\u043e\u0440\u0442\u0430", "\u041c\u0435\u0441\u0442\u043e \u0432\u044b\u0434\u0430\u0447\u0438 \u0442\u0435\u0445. \u043f\u0430\u0441\u043f\u043e\u0440\u0442\u0430", "\u0414\u0430\u0442\u0430 \u0432\u044b\u0434\u0430\u0447\u0438 \u0442\u0435\u0445. \u043f\u0430\u0441\u043f\u043e\u0440\u0442\u0430", "\u0414\u0430\u0442\u0430 \u043f\u043e\u0441\u043b\u0435\u0434\u043d\u0435\u0433\u043e \u0422\u041e", "\u0414\u0430\u0442\u0430 \u0441\u043b\u0435\u0434\u0443\u044e\u0449\u0435\u0433\u043e \u0422\u041e", "\u041c\u0430\u0440\u043a\u0430 \u0413\u0421\u041c"
				}
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = -1675832613494885750L;
				Class<?>[] columnTypes = new Class<?>[] {
					String.class, String.class, Date.class, String.class, String.class, Date.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class
				};
				boolean[] columnEditable = new boolean[] {
					false, false, false, false, false, false, true, true, true, true, true, true
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
				cm.getColumn(0).setResizable(false);
				cm.getColumn(1).setResizable(false);
				cm.getColumn(2).setResizable(false);
				cm.getColumn(3).setResizable(false);
				cm.getColumn(4).setResizable(false);
				cm.getColumn(5).setResizable(false);
			}
			groupTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			groupTable.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
			scrollPane1.setViewportView(groupTable);
		}
		contentPane.add(scrollPane1, BorderLayout.CENTER);

		//======== panel1 ========
		{
			panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 13));

			//---- button1 ----
			button1.setText("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0437\u0430\u043f\u0438\u0441\u044c");
			button1.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Add.png")));
			panel1.add(button1);

			//---- button2 ----
			button2.setText("\u0420\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c");
			button2.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Pencil_2.png")));
			panel1.add(button2);

			//---- button4 ----
			button4.setText("\u0423\u0434\u0430\u043b\u0438\u0442\u044c");
			button4.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Denided.png")));
			panel1.add(button4);

			//---- button3 ----
			button3.setText("\u041e\u0442\u043c\u0435\u043d\u0430");
			button3.setIcon(new ImageIcon(getClass().getResource("/images/Nnm 16/logout.png")));
			panel1.add(button3);
		}
		contentPane.add(panel1, BorderLayout.SOUTH);

		//======== panel2 ========
		{
			panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
		}
		contentPane.add(panel2, BorderLayout.NORTH);
		setSize(710, 383);
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

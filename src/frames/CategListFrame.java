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
import tablemodels.CategTableModel;
import beans.Categories;

/**
 * @author ????????
 */
public class CategListFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6710148153679889458L;
	private AutoschoolRmiInterface ari; 
	public CategListFrame(Frame frame, AutoschoolRmiInterface ar) {
		initComponents();
		this.ari = ar;
		loadCategories();
		button1.setName("addCateg");
		button2.setName("editCateg");
		button4.setName("delCateg");
		button1.addActionListener(this);
		button2.addActionListener(this);
		button4.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Component) {
			Component c = (Component) e.getSource();
			if (c.getName().equals("addCateg")) {
				addCateg();
			}
			if (c.getName().equals("editCateg")) {
				editCateg();
            }
			if (c.getName().equals("delCateg")) {
				delCateg();
            }

		
		}
	}
	
	private void delCateg() {
		Thread t = new Thread() {
			public void run() {
				CategTableModel stm = (CategTableModel) groupTable.getModel();
					// Проверяем - выделен ли хоть какой-нибудь элемент
					if (groupTable.getSelectedRow() >= 0) {
						if (JOptionPane.showOptionDialog(
								CategListFrame.this, 
								  "Вы хотите удалить запись?", 
								  "Удаление записи", 
								  JOptionPane.YES_NO_OPTION, 
								  JOptionPane.ERROR_MESSAGE, 
								  null, 
								  new Object[]{"Да", "Нет"}, 
								  "Да")== JOptionPane.YES_OPTION) {
							Categories s = stm.getGroup(groupTable.getSelectedRow());
							try {
								ari.deleteCateg(s);
								loadCategories();
							} catch (RemoteException e) {
                                JOptionPane.showMessageDialog(CategListFrame.this, e.getMessage());
                            }
						}
					} // Если элемент не выделен - сообщаем пользователю, что это необходимо
					else {
						 JOptionPane.showMessageDialog(CategListFrame.this,
	                                "Необходимо выделить запись в таблице!", "Внимание", 2);
                    }
				}
		};
		t.start();
		
	}

	private void editCateg() {
		Thread t = new Thread() {
			public void run() {
				CategTableModel stm = (CategTableModel) groupTable.getModel();
                    // Проверяем - выделен ли хоть какой-нибудь элемент
                    if (groupTable.getSelectedRow() >= 0) {
                    	Categories s = stm.getGroup(groupTable.getSelectedRow());
                    	CategDialog gd =  new CategDialog(CategListFrame.this);
                		    gd.setCateg(s);
                            gd.setModal(true);
                            gd.setVisible(true);
                            if (gd.getResult()) {
                            	Categories s1 = gd.getCateg();
                            	try {
            						ari.updateCateg(s1);
            						loadCategories();
            					} catch (RemoteException e) {
            						 JOptionPane.showMessageDialog(CategListFrame.this, e.getMessage());
            					}
                            }
                    } // Если элемент не выделен - сообщаем пользователю, что это необходимо
                    else {
                        JOptionPane.showMessageDialog(CategListFrame.this,
                                "Необходимо выделить запись в таблице!", "Внимание", 2);
                    }
                
            }
        };
        t.start();
		
	}

	private void addCateg() {
		Thread t = new Thread() {
            public void run() {
            	CategDialog gd =  new CategDialog(CategListFrame.this);
				gd.setModal(true);
				gd.setVisible(true);
                if (gd.getResult()) {
                	Categories g = gd.getCateg();
				    try {
						ari.insertCateg(g);
						loadCategories();
					} catch (RemoteException e) {
						 JOptionPane.showMessageDialog(CategListFrame.this, e.getMessage());
					}
				}
            }
        };
        t.start();
		
	}

	private List<Categories> loadCategories() {
		List<Categories> stud = null;
		try {
			stud = (List<Categories>) ari.getCategoryList();
			updateProgress(stud);
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
		
	}
	
	private void updateProgress(final List<Categories> stud) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (stud!= null){
					groupTable.setModel(new CategTableModel(stud));
					groupTable.getColumnModel().getColumn(0).setPreferredWidth(190);
					groupTable.getColumnModel().getColumn(1).setPreferredWidth(100);
					groupTable.getColumnModel().getColumn(2).setPreferredWidth(120);
					groupTable.getColumnModel().getColumn(3).setPreferredWidth(110);
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
		panel2 = new JPanel();
		groupTable.getTableHeader().setFont(new Font("Trebuchet MS", Font.BOLD, 14));

		//======== this ========
		setTitle("\u041f\u0440\u043e\u0433\u0440\u0430\u043c\u043c\u044b \u043f\u043e\u0434\u0433\u043e\u0442\u043e\u0432\u043a\u0438 \u0432\u043e\u0434\u0438\u0442\u0435\u043b\u0435\u0439 \u0422\u0421");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== scrollPane1 ========
		{

			//---- groupTable ----
			groupTable.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null, null},
					{null, "", null, null},
				},
				new String[] {
					"\u041f\u0440\u043e\u0433\u0440\u0430\u043c\u043c\u0430 \u043f\u043e\u0434\u0433\u043e\u0442\u043e\u0432\u043a\u0438", "\u0427\u0430\u0441\u043e\u0432 \u0442\u0435\u043e\u0440\u0438\u0438", "\u0427\u0430\u0441\u043e\u0432 \u043f\u0440\u0430\u043a\u0442\u0438\u043a\u0438", "\u0421\u0442\u043e\u0438\u043c\u043e\u0441\u0442\u044c"
				}
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = -4837263683066921616L;
				Class<?>[] columnTypes = new Class<?>[] {
					String.class, String.class, Date.class, String.class
				};
				boolean[] columnEditable = new boolean[] {
					false, false, false, false
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
				cm.getColumn(0).setPreferredWidth(145);
				cm.getColumn(1).setResizable(false);
				cm.getColumn(1).setPreferredWidth(90);
				cm.getColumn(2).setResizable(false);
				cm.getColumn(2).setPreferredWidth(105);
				cm.getColumn(3).setResizable(false);
				cm.getColumn(3).setPreferredWidth(90);
			}
			groupTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			groupTable.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
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
		}
		contentPane.add(panel1, BorderLayout.SOUTH);

		//======== panel2 ========
		{
			panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
		}
		contentPane.add(panel2, BorderLayout.NORTH);
		setSize(526, 276);
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
	private JPanel panel2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

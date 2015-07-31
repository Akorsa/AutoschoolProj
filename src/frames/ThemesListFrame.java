/*
 * Created by JFormDesigner on Sun Dec 22 15:04:01 GMT+05:00 2013
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

import beans.Subjects;
import beans.Themes;

import rmi.AutoschoolRmiInterface;
import tablemodels.ThemesTableModel;

/**
 * @author ????????
 */
public class ThemesListFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6500104996845791325L;
	private AutoschoolRmiInterface ari; 
	public ThemesListFrame(Frame frame, AutoschoolRmiInterface ar) {
		initComponents();
		this.ari = ar;
		loadThemes();
		addButton.setName("addTheme");
		editButton.setName("editTheme");
		delButton.setName("delTheme");
		addButton.addActionListener(this);
		editButton.addActionListener(this);
		delButton.addActionListener(this);
	}
	
	private List<Themes> loadThemes() {
		List<Themes> stud = null;
		try {
			stud = (List<Themes>) ari.getThemeList();
			updateProgress(stud);
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
		
	}
	
	private void updateProgress(final List<Themes> stud) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (stud!= null){
					groupTable.setModel(new ThemesTableModel(stud));
					groupTable.getColumnModel().getColumn(0).setPreferredWidth(180);
					groupTable.getColumnModel().getColumn(1).setPreferredWidth(160);
					groupTable.getColumnModel().getColumn(2).setPreferredWidth(165);
					groupTable.getColumnModel().getColumn(3).setPreferredWidth(150);
                    }
			}
		});
		
	}
	
	protected List<Subjects> loadSubjects() {
		List<Subjects > stud = null;
		try {
			stud = ari.getSubjectList();
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Component) {
			Component c = (Component) e.getSource();
			if (c.getName().equals("addTheme")) {
				addTheme();
			}
			if (c.getName().equals("editTheme")) {
				editTheme();
            }
			if (c.getName().equals("delTheme")) {
				delTheme();
            }
		
		}
	}

	private void delTheme() {
		Thread t = new Thread() {
			public void run() {
				ThemesTableModel stm = (ThemesTableModel) groupTable.getModel();
					// Проверяем - выделен ли хоть какой-нибудь элемент
					if (groupTable.getSelectedRow() >= 0) {
						if (JOptionPane.showOptionDialog(
								ThemesListFrame.this, 
								  "Вы хотите удалить запись?", 
								  "Удаление записи", 
								  JOptionPane.YES_NO_OPTION, 
								  JOptionPane.ERROR_MESSAGE, 
								  null, 
								  new Object[]{"Да", "Нет"}, 
								  "Да")== JOptionPane.YES_OPTION) {
							Themes s = stm.getTheme(groupTable.getSelectedRow());
							try {
								ari.deleteTheme(s);
								loadThemes();
							} catch (RemoteException e) {
                                JOptionPane.showMessageDialog(ThemesListFrame.this, e.getMessage());
                            }
						}
					} // Если элемент не выделен - сообщаем пользователю, что это необходимо
					else {
						 JOptionPane.showMessageDialog(ThemesListFrame.this,
	                                "Необходимо выделить запись в таблице!", "Внимание", 2);
                    }
				}
		};
		t.start();
		
	}

	private void editTheme() {
		Thread t = new Thread() {
			public void run() {
				ThemesTableModel stm = (ThemesTableModel) groupTable.getModel();
                    // Проверяем - выделен ли хоть какой-нибудь элемент
                    if (groupTable.getSelectedRow() >= 0) {
                    	Themes s = stm.getTheme(groupTable.getSelectedRow());
                    	ThemesDialog gd =  new ThemesDialog(ThemesListFrame.this,loadSubjects());
                		    gd.setTheme(s);
                            gd.setModal(true);
                            gd.setVisible(true);
                            if (gd.getResult()) {
                            	Themes s1 = gd.getThemes();
                            	try {
            						ari.updateTheme(s1);
            						loadThemes();
            					} catch (RemoteException e) {
            						 JOptionPane.showMessageDialog(ThemesListFrame.this, e.getMessage());
            					}
                            }
                    } // Если элемент не выделен - сообщаем пользователю, что это необходимо
                    else {
                        JOptionPane.showMessageDialog(ThemesListFrame.this,
                                "Необходимо выделить запись в таблице!", "Внимание", 2);
                    }
                
            }
        };
        t.start();
		
	}

	private void addTheme() {
		Thread t = new Thread() {
            public void run() {
            	ThemesDialog gd =  new ThemesDialog(ThemesListFrame.this,loadSubjects());
				gd.setModal(true);
				gd.setVisible(true);
                if (gd.getResult()) {
                	Themes g = gd.getThemes();
				    try {
						ari.insertTheme(g);
						loadThemes();
					} catch (RemoteException e) {
						 JOptionPane.showMessageDialog(ThemesListFrame.this, e.getMessage());
					}
				}
            }
        };
        t.start();
		
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		scrollPane1 = new JScrollPane();
		groupTable = new JTable();
		panel1 = new JPanel();
		addButton = new JButton();
		editButton = new JButton();
		delButton = new JButton();
		panel2 = new JPanel();
		groupTable.getTableHeader().setFont(new Font("Trebuchet MS", Font.BOLD, 14));

		//======== this ========
		setTitle("\u0422\u0435\u043c\u044b");
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
					"\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u0442\u0435\u043c\u044b", "\u041a\u043e\u043b\u0438\u0447\u0435\u0441\u0442\u0432\u043e \u0447\u0430\u0441\u043e\u0432", "\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u0430", "\u041a\u043e\u043c\u043c\u0435\u043d\u0442\u0430\u0440\u0438\u0439"
				}
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 6231192521787097412L;
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
				cm.getColumn(0).setPreferredWidth(137);
				cm.getColumn(1).setPreferredWidth(160);
				cm.getColumn(2).setPreferredWidth(165);
				cm.getColumn(3).setResizable(false);
				cm.getColumn(3).setPreferredWidth(150);
			}
			groupTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			groupTable.setFont(new Font("Tahoma", Font.PLAIN, 12));
			scrollPane1.setViewportView(groupTable);
		}
		contentPane.add(scrollPane1, BorderLayout.CENTER);

		//======== panel1 ========
		{
			panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 13));

			//---- addButton ----
			addButton.setText("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0437\u0430\u043f\u0438\u0441\u044c");
			addButton.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Add.png")));
			panel1.add(addButton);

			//---- editButton ----
			editButton.setText("\u0420\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c");
			editButton.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Pencil_2.png")));
			panel1.add(editButton);

			//---- delButton ----
			delButton.setText("\u0423\u0434\u0430\u043b\u0438\u0442\u044c");
			delButton.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Denided.png")));
			panel1.add(delButton);
		}
		contentPane.add(panel1, BorderLayout.SOUTH);

		//======== panel2 ========
		{
			panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
		}
		contentPane.add(panel2, BorderLayout.NORTH);
		setSize(604, 410);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JScrollPane scrollPane1;
	private JTable groupTable;
	private JPanel panel1;
	private JButton addButton;
	private JButton editButton;
	private JButton delButton;
	private JPanel panel2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

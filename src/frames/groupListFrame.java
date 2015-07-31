/*
 * Created by JFormDesigner on Mon Dec 16 23:48:37 GMT+05:00 2013
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

import beans.Categories;
import beans.Educationtypes;
import beans.Groups;
import beans.YearOfEducation;

import rmi.AutoschoolRmiInterface;
import tablemodels.GroupTableModel;


/**
 * @author ????????
 */
public class groupListFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8786847974748351436L;
	private AutoschoolRmiInterface ari; 
	private int flag; 
	private ZamDirectPRFrame own; 
	private ZamDirectURFrame own1; 
	private ZavAvtodeloFrame own2; 
	public groupListFrame(int ch, Frame frame, AutoschoolRmiInterface ar) {
		initComponents();
		this.ari = ar;
		this.flag = ch;
		if (flag ==1) {
			this.own = (ZamDirectPRFrame) frame;}
		if (flag ==2) {
			this.own1 = (ZamDirectURFrame) frame;
		}
		if (flag ==3) {
			this.own2 = (ZavAvtodeloFrame) frame;
		}
		loadGroup();
		button1.setName("addGroup");
		button2.setName("editGroup");
		button3.setName("cancel");
		button1.addActionListener(this);
		button2.addActionListener(this);
		button3.addActionListener(this);
		button4.setName("addGroup");
		button5.setName("editGroup");
		button6.setName("delGroup");
		button4.addActionListener(this);
		button5.addActionListener(this);
		button6.addActionListener(this);
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Component) {
			Component c = (Component) e.getSource();
			if (c.getName().equals("addGroup")) {
				insertGrup();
			}
			if (c.getName().equals("editGroup")) {
            	updateGrup();
            }
			if (c.getName().equals("cancel")) {
				dispose();
            }
			if (c.getName().equals("delGroup")) {
				delGrup();
            }
		
	}
		
	}

	private void delGrup() {
		Thread t = new Thread() {
			public void run() {
				GroupTableModel stm = (GroupTableModel) groupTable.getModel();
					// Проверяем - выделен ли хоть какой-нибудь элемент
					if (groupTable.getSelectedRow() >= 0) {
						if (JOptionPane.showOptionDialog(
								groupListFrame.this, 
								  "Вы хотите удалить запись?", 
								  "Удаление записи", 
								  JOptionPane.YES_NO_OPTION, 
								  JOptionPane.ERROR_MESSAGE, 
								  null, 
								  new Object[]{"Да", "Нет"}, 
								  "Да")== JOptionPane.YES_OPTION) {
							Groups s = stm.getGroup(groupTable.getSelectedRow());
							try {
								ari.deleteBet(s);
								reloadTable();
								if (flag ==1) {
									own.pop_tree();}
								if (flag ==2) {
									own1.pop_tree();;
								}
								if (flag ==3) {
									own2.pop_tree();;
								}
								
							} catch (RemoteException e) {
                                JOptionPane.showMessageDialog(groupListFrame.this, e.getMessage());
                            }
						}
					} // Если элемент не выделен - сообщаем пользователю, что это необходимо
					else {
						 JOptionPane.showMessageDialog(groupListFrame.this,
	                                "Необходимо выделить группу в таблице!", "Внимание", 2);
                    }
				}
		};
		t.start();
		
	}

	private void reloadTable() {
		List<Groups> stud = null;
		try {
			stud = (List<Groups>) ari.getGroupList();
			updateProgress(stud);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private void updateGrup() {
		Thread t = new Thread() {
			public void run() {
				GroupTableModel stm = (GroupTableModel) groupTable.getModel();
                    // Проверяем - выделен ли хоть какой-нибудь элемент
                    if (groupTable.getSelectedRow() >= 0) {
                    	Groups s = stm.getGroup(groupTable.getSelectedRow());
                    	GroupDialog gd =  new GroupDialog( groupListFrame.this,loadCategories(),loadEducationtypes(),loadYearEd());
                            gd.setGrup(s);
                            gd.setModal(true);
                            gd.setVisible(true);
                            if (gd.getResult()) {
                            	Groups s1 = gd.getGrup();
                            	try {
            						ari.updateGrup(s1);
            						reloadTable();
            						if (flag ==1) {
    									own.pop_tree();}
    								if (flag ==2) {
    									own1.pop_tree();
    								}
    								if (flag ==3) {
    									own2.pop_tree();
    								}
            					} catch (RemoteException e) {
            						 JOptionPane.showMessageDialog(groupListFrame.this, e.getMessage());
            					}
                            }
                    } // Если элемент не выделен - сообщаем пользователю, что это необходимо
                    else {
                        JOptionPane.showMessageDialog(groupListFrame.this,
                                "Необходимо выделить группу в таблице!", "Внимание", 2);
                    }
                
            }
        };
        t.start();
		
	}

	private void insertGrup() {
		Thread t = new Thread() {
            public void run() {
               GroupDialog gd =  new GroupDialog( groupListFrame.this,loadCategories(),loadEducationtypes(),loadYearEd());
				gd.setModal(true);
				gd.setVisible(true);
                if (gd.getResult()) {
				    Groups g = gd.getGrup();
				    try {
						ari.insertGrup(g);
						reloadTable();
						if (flag ==1) {
							own.pop_tree();}
						if (flag ==2) {
							own1.pop_tree();
						}
						if (flag ==3) {
							own2.pop_tree();
						}
					} catch (RemoteException e) {
						 JOptionPane.showMessageDialog(groupListFrame.this, e.getMessage());
					}
				}
            }
        };
        t.start();
		
	}

	protected List<Groups> loadGroup() {
		List<Groups> stud = null;
		try {
			stud = (List<Groups>) ari.getGroupList();
			updateProgress(stud);
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
		
	}
	
	protected List<Categories> loadCategories() {
		List<Categories> stud = null;
		try {
			stud = (List<Categories>) ari.getCategoryList();
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
		
	}
	
	protected List<Educationtypes > loadEducationtypes() {
		List<Educationtypes > stud = null;
		try {
			stud = (List<Educationtypes >) ari.getEducationtypes();
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
		
	}
	
	protected List<YearOfEducation> loadYearEd() {
		List<YearOfEducation> stud = null;
		try {
			stud = (List<YearOfEducation>) ari.getYearOfEducation();
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
		
	}
	
	private void updateProgress(final List<Groups> stud) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (stud!= null){
					groupTable.setModel(new GroupTableModel(stud));
					groupTable.getColumnModel().getColumn(0).setPreferredWidth(132);
					groupTable.getColumnModel().getColumn(1).setPreferredWidth(100);
					groupTable.getColumnModel().getColumn(2).setPreferredWidth(143);
					groupTable.getColumnModel().getColumn(3).setPreferredWidth(100);
					groupTable.getColumnModel().getColumn(4).setPreferredWidth(130);
					groupTable.getColumnModel().getColumn(5).setPreferredWidth(130);
					groupTable.getColumnModel().getColumn(6).setPreferredWidth(103);
					groupTable.getColumnModel().getColumn(7).setPreferredWidth(103);
					groupTable.getColumnModel().getColumn(8).setPreferredWidth(143);
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
		button3 = new JButton();
		panel2 = new JPanel();
		toolBar1 = new JToolBar();
		button4 = new JButton();
		button5 = new JButton();
		button6 = new JButton();
		groupTable.getTableHeader().setFont(new Font("Trebuchet MS", Font.BOLD, 14));

		//======== this ========
		setTitle("\u0413\u0440\u0443\u043f\u043f\u044b");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== scrollPane1 ========
		{

			//---- groupTable ----
			groupTable.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null, null, null, null, null, null},
					{null, "", null, null, null, null, null, null},
				},
				new String[] {
					"\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u0433\u0440\u0443\u043f\u043f\u044b", "\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0438\u044f \u0422\u0421", "\u0424\u043e\u0440\u043c\u0430 \u043e\u0431\u0443\u0447\u0435\u043d\u0438\u044f", "\u0413\u043e\u0434 \u043e\u0431\u0443\u0447\u0435\u043d\u0438\u044f", "\u041d\u0430\u0447\u0430\u043b\u043e \u0437\u0430\u043d\u044f\u0442\u0438\u0439", "\u041a\u043e\u043d\u0435\u0446 \u0437\u0430\u043d\u044f\u0442\u0438\u0439", "\u041d\u043e\u043c\u0435\u0440 \u043f\u0440\u0438\u043a\u0430\u0437\u0430", "\u0414\u0430\u0442\u0430 \u043f\u0440\u0438\u043a\u0430\u0437\u0430"
				}
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = -4840515592546571598L;
				Class<?>[] columnTypes = new Class<?>[] {
					String.class, String.class, String.class, String.class, Date.class, String.class, String.class, Date.class
				};
				boolean[] columnEditable = new boolean[] {
					false, false, false, false, false, false, false, false
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
				cm.getColumn(6).setResizable(false);
				cm.getColumn(7).setResizable(false);
			}
			groupTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			scrollPane1.setViewportView(groupTable);
		}
		contentPane.add(scrollPane1, BorderLayout.CENTER);

		//======== panel1 ========
		{
			panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 13, 13));

			//---- button1 ----
			button1.setText("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0437\u0430\u043f\u0438\u0441\u044c");
			button1.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Add.png")));
			panel1.add(button1);

			//---- button2 ----
			button2.setText("\u0420\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c");
			button2.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Pencil_2.png")));
			panel1.add(button2);

			//---- button3 ----
			button3.setText("\u041e\u0442\u043c\u0435\u043d\u0430");
			button3.setIcon(new ImageIcon(getClass().getResource("/images/Nnm 16/logout.png")));
			panel1.add(button3);
		}
		contentPane.add(panel1, BorderLayout.SOUTH);

		//======== panel2 ========
		{
			panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));

			//======== toolBar1 ========
			{

				//---- button4 ----
				button4.setIcon(new ImageIcon(getClass().getResource("/images/Folder_Add.png")));
				button4.setToolTipText("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u043d\u043e\u0432\u0443\u044e \u0433\u0440\u0443\u043f\u043f\u0443");
				toolBar1.add(button4);

				//---- button5 ----
				button5.setIcon(new ImageIcon(getClass().getResource("/images/Folder_Edit.png")));
				button5.setToolTipText("\u0420\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u0437\u0430\u043f\u0438\u0441\u044c");
				toolBar1.add(button5);

				//---- button6 ----
				button6.setIcon(new ImageIcon(getClass().getResource("/images/Folder_Remove.png")));
				button6.setToolTipText("\u0423\u0434\u0430\u043b\u0438\u0442\u044c \u0437\u0430\u043f\u0438\u0441\u044c");
				toolBar1.add(button6);
			}
			panel2.add(toolBar1);
		}
		contentPane.add(panel2, BorderLayout.NORTH);
		setSize(800, 530);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JScrollPane scrollPane1;
	private JTable groupTable;
	private JPanel panel1;
	private JButton button1;
	private JButton button2;
	private JButton button3;
	private JPanel panel2;
	private JToolBar toolBar1;
	private JButton button4;
	private JButton button5;
	private JButton button6;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	
}

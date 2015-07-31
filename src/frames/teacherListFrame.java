/*
 * Created by JFormDesigner on Thu Dec 19 18:04:39 GMT+05:00 2013
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

import beans.Teachers;
import beans.TypeEducation;

import rmi.AutoschoolRmiInterface;
import tablemodels.TeacherTableModel;

/**
 * @author ????????
 */
public class teacherListFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2066775559748445453L;
	private int flag;
	private AutoschoolRmiInterface ari; 
	public teacherListFrame(Frame frame, AutoschoolRmiInterface ar, int flag) {
		initComponents();
		this.ari = ar;
		this.flag = flag;
		if (flag == 1) {
			loadTeacher();
			setTitle("Преподаватели");
		}
		if (flag == 2) {
			loadMasters();
			setTitle("Мастера ПО");
		}
		button1.setName("addTeacher");
		button2.setName("editTeacher");
		button3.setName("zakrFrame");
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
				insertTeacher();
			}
			if (c.getName().equals("editTeacher")) {
            	updateTeacher();
            }
			if (c.getName().equals("zakrFrame")) {
				zakrListFrame td = new  zakrListFrame(teacherListFrame.this,ari);
		        td.setVisible(true);
            }
			if (c.getName().equals("delTeacher")) {
				delTeacher();
            }
		
	}
		
	}
	
	
	private void delTeacher() {
		Thread t = new Thread() {
			public void run() {
				TeacherTableModel stm = (TeacherTableModel) groupTable.getModel();
					// Проверяем - выделен ли хоть какой-нибудь элемент
					if (groupTable.getSelectedRow() >= 0) {
						if (JOptionPane.showOptionDialog(
								teacherListFrame.this, 
								  "Вы хотите удалить запись?", 
								  "Удаление записи", 
								  JOptionPane.YES_NO_OPTION, 
								  JOptionPane.ERROR_MESSAGE, 
								  null, 
								  new Object[]{"Да", "Нет"}, 
								  "Да")== JOptionPane.YES_OPTION) {
							Teachers s = stm.getTeacher(groupTable.getSelectedRow());
							try {
								ari.deleteTeacher(s);
								if (flag == 1) loadTeacher();
								if (flag == 2) loadMasters();
							} catch (RemoteException e) {
                                JOptionPane.showMessageDialog(teacherListFrame.this, e.getMessage());
                            }
						}
					} // Если элемент не выделен - сообщаем пользователю, что это необходимо
					else {
						 JOptionPane.showMessageDialog(teacherListFrame.this,
	                                "Необходимо выделить запись в таблице!", "Внимание", 2);
                    }
				}
		};
		t.start();
		
	}

	private void updateTeacher() {
		Thread t = new Thread() {
			public void run() {
				TeacherTableModel stm = (TeacherTableModel) groupTable.getModel();
                    // Проверяем - выделен ли хоть какой-нибудь элемент
                    if (groupTable.getSelectedRow() >= 0) {
                    	Teachers s = stm.getTeacher(groupTable.getSelectedRow());
                    	TeacherDialog gd = null;
                    	if (flag == 1) {gd =  new TeacherDialog(teacherListFrame.this,loadTypeEd(),1);}
                		if (flag == 2) {gd =  new TeacherDialog(teacherListFrame.this,loadTypeEd(),2);}
                    	
                            gd.setPrepod(s);
                            gd.setModal(true);
                            gd.setVisible(true);
                            if (gd.getResult()) {
                            	Teachers s1 = gd.getPrepod();
                            	try {
            						ari.updateTeachers(s1);
            						if (flag == 1) loadTeacher();
            						if (flag == 2) loadMasters();
            					} catch (RemoteException e) {
            						 JOptionPane.showMessageDialog(teacherListFrame.this, e.getMessage());
            					}
                            }
                    } // Если элемент не выделен - сообщаем пользователю, что это необходимо
                    else {
                        JOptionPane.showMessageDialog(teacherListFrame.this,
                                "Необходимо выделить запись в таблице!", "Внимание", 2);
                    }
                
            }
        };
        t.start();
		
	}

	protected List<TypeEducation> loadTypeEd() {
		List<TypeEducation> stud = null;
		try {
			stud = ari.getTypeEducation();
			//for (Students cat : stud) {
				//System.out.println(cat.getName()+cat.getSurname());
			//}
			if( stud.isEmpty()){ System.out.println("Fucking shit typeEd");}
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
		
	}

	private void insertTeacher() {
		Thread t = new Thread() {
            public void run() {
            	TeacherDialog gd = null;
            	if (flag == 1) {gd =  new TeacherDialog(teacherListFrame.this,loadTypeEd(),1);}
        		if (flag == 2) {gd =  new TeacherDialog(teacherListFrame.this,loadTypeEd(),2);}
				gd.setModal(true);
				gd.setVisible(true);
                if (gd.getResult()) {
                	Teachers g = gd.getPrepod();
				    try {
						ari.insertPrepod(g);
						if (flag == 1) loadTeacher();
						if (flag == 2) loadMasters();
					} catch (RemoteException e) {
						 JOptionPane.showMessageDialog(teacherListFrame.this, e.getMessage());
					}
				}
            }
        };
        t.start();
		
	}

	private List<Teachers> loadTeacher() {
		List<Teachers> stud = null;
		try {
			stud = (List<Teachers>) ari.getTeacherList();
			updateProgress(stud);
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
		
	}
	
	private List<Teachers> loadMasters() {
		List<Teachers> stud = null;
		try {
			stud = (List<Teachers>) ari.getMastersList();
			updateProgress(stud);
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
		
	}

	private void updateProgress(final List<Teachers> stud) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (stud!= null){
					groupTable.setModel(new TeacherTableModel(stud));
					groupTable.getColumnModel().getColumn(0).setPreferredWidth(100);
					groupTable.getColumnModel().getColumn(1).setPreferredWidth(110);
					groupTable.getColumnModel().getColumn(2).setPreferredWidth(115);
					groupTable.getColumnModel().getColumn(3).setPreferredWidth(116);
					groupTable.getColumnModel().getColumn(4).setPreferredWidth(134);
					groupTable.getColumnModel().getColumn(7).setPreferredWidth(163);
					groupTable.getColumnModel().getColumn(8).setPreferredWidth(107);
					groupTable.getColumnModel().getColumn(10).setPreferredWidth(129);
					groupTable.getColumnModel().getColumn(11).setPreferredWidth(111);
					groupTable.getColumnModel().getColumn(12).setPreferredWidth(115);
					groupTable.getColumnModel().getColumn(13).setPreferredWidth(118);
					groupTable.getColumnModel().getColumn(14).setPreferredWidth(175);
					groupTable.getColumnModel().getColumn(15).setPreferredWidth(107);
					groupTable.getColumnModel().getColumn(16).setPreferredWidth(174);
					groupTable.getColumnModel().getColumn(17).setPreferredWidth(246);
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
		setTitle("\u041f\u0440\u0435\u043f\u043e\u0434\u0430\u0432\u0430\u0442\u0435\u043b\u0438");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== scrollPane1 ========
		{

			//---- groupTable ----
			groupTable.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null, null, null, null, null, null, null, "", null, null, null, null, null, null, null, null},
					{null, "", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
				},
				new String[] {
					"\u0424\u0430\u043c\u0438\u043b\u0438\u044f ", "\u0418\u043c\u044f", "\u041e\u0442\u0447\u0435\u0441\u0442\u0432\u043e", "\u0414\u0430\u0442\u0430 \u0440\u043e\u0436\u0434\u0435\u043d\u0438\u044f", "\u041c\u0435\u0441\u0442\u043e \u0440\u043e\u0436\u0434\u0435\u043d\u0438\u044f", "\u0410\u0434\u0440\u0435\u0441", "\u0422\u0435\u043b\u0435\u0444\u043e\u043d", "\u0421\u0435\u0440\u0438\u044f \u2116 \u0432\u043e\u0434. \u0443\u0434\u043e\u0441\u0442.", "\u0414\u0430\u0442\u0430 \u0432\u044b\u0434\u0430\u0447\u0438", "\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0438\u044f", "\u041c\u0435\u0441\u0442\u043e \u0432\u044b\u0434\u0430\u0447\u0438", "\u041e\u0431\u0440\u0430\u0437\u043e\u0432\u0430\u043d\u0438\u0435", "\u0423\u0447. \u0437\u0430\u0432\u0435\u0434\u0435\u043d\u0438\u0435", "\u0413\u043e\u0434 \u043e\u043a\u043e\u043d\u0447\u0430\u043d\u0438\u044f", "\u041d\u043e\u043c\u0435\u0440, \u0441\u0435\u0440\u0438\u044f \u0434\u0438\u043f\u043b\u043e\u043c\u0430", "\u0412\u043e\u0434\u0438\u0442. \u0441\u0442\u0430\u0436", "\u041e\u0442\u043c\u0435\u0442\u043a\u0430 \u043e\u0431 \u0443\u0432\u043e\u043b\u044c\u043d\u0435\u043d\u0438\u0438", "\u0414\u0430\u0442\u0430 \u0438 \u2116 \u043f\u0440\u0438\u043a\u0430\u0437\u0430 \u043e\u0431 \u0443\u0432\u043e\u043b\u044c\u043d\u0435\u043d\u0438\u0438"
				}
			) {
				Class<?>[] columnTypes = new Class<?>[] {
					String.class, String.class, String.class, Date.class, String.class, String.class, Date.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class, Object.class
				};
				boolean[] columnEditable = new boolean[] {
					false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true
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
			}
			groupTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			//groupTable.setFont(new Font("Tahoma", Font.BOLD, 12));
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
			button3.setText("\u0417\u0430\u043a\u0440\u0435\u043f\u043b\u0435\u043d\u0438\u044f");
			button3.setIcon(new ImageIcon(getClass().getResource("/images/16x16/address.png")));
			panel1.add(button3);
		}
		contentPane.add(panel1, BorderLayout.SOUTH);

		//======== panel2 ========
		{
			panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
		}
		contentPane.add(panel2, BorderLayout.NORTH);
		setSize(820, 410);
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

/*
 * Created by JFormDesigner on Sun Dec 22 14:47:43 GMT+05:00 2013
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

import rmi.AutoschoolRmiInterface;
import tablemodels.SubjectTableModel;
import beans.Subjects;

/**
 * @author ????????
 */
public class subjectListFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8327816229322487960L;
	private AutoschoolRmiInterface ari; 
	public subjectListFrame(Frame frame,AutoschoolRmiInterface ar) {
		initComponents();
		this.ari = ar;
		loadSubjects();
		addButton.setName("addSubject");
		editButton.setName("editSubject");
		delButton.setName("delSubject");
		addButton.addActionListener(this);
		editButton.addActionListener(this);
		delButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Component) {
			Component c = (Component) e.getSource();
			if (c.getName().equals("addSubject")) {
				addSubject();
			}
			if (c.getName().equals("editSubject")) {
				editSubject();
            }
			if (c.getName().equals("delSubject")) {
				delSubject();
            }
			
		
		}
	}
	
	private void delSubject() {
		Thread t = new Thread() {
			public void run() {
				SubjectTableModel stm = (SubjectTableModel) groupTable.getModel();
					// Проверяем - выделен ли хоть какой-нибудь элемент
					if (groupTable.getSelectedRow() >= 0) {
						if (JOptionPane.showOptionDialog(
								subjectListFrame.this, 
								  "Вы хотите удалить запись?", 
								  "Удаление записи", 
								  JOptionPane.YES_NO_OPTION, 
								  JOptionPane.ERROR_MESSAGE, 
								  null, 
								  new Object[]{"Да", "Нет"}, 
								  "Да")== JOptionPane.YES_OPTION) {
							Subjects s = stm.getSubj(groupTable.getSelectedRow());
							try {
								ari.deleteSubject(s);
								loadSubjects();
							} catch (RemoteException e) {
                                JOptionPane.showMessageDialog(subjectListFrame.this, e.getMessage());
                            }
						}
					} // Если элемент не выделен - сообщаем пользователю, что это необходимо
					else {
						 JOptionPane.showMessageDialog(subjectListFrame.this,
	                                "Необходимо выделить запись в таблице!", "Внимание", 2);
                    }
				}
		};
		t.start();
		
	}

	private void editSubject() {
		Thread t = new Thread() {
			public void run() {
				SubjectTableModel stm = (SubjectTableModel) groupTable.getModel();
                    // Проверяем - выделен ли хоть какой-нибудь элемент
                    if (groupTable.getSelectedRow() >= 0) {
                    	Subjects s = stm.getSubj(groupTable.getSelectedRow());
                    	SubjectDialog gd =  new SubjectDialog(subjectListFrame.this);
                		    gd.setSubject(s);
                            gd.setModal(true);
                            gd.setVisible(true);
                            if (gd.getResult()) {
                            	Subjects s1 = gd.getSubject();
                            	try {
            						ari.updateSubject(s1);
            						loadSubjects();
            					} catch (RemoteException e) {
            						 JOptionPane.showMessageDialog(subjectListFrame.this, e.getMessage());
            					}
                            }
                    } // Если элемент не выделен - сообщаем пользователю, что это необходимо
                    else {
                        JOptionPane.showMessageDialog(subjectListFrame.this,
                                "Необходимо выделить запись в таблице!", "Внимание", 2);
                    }
                
            }
        };
        t.start();
		
	}

	private void addSubject() {
		Thread t = new Thread() {
            public void run() {
            	SubjectDialog gd =  new SubjectDialog(subjectListFrame.this);
				gd.setModal(true);
				gd.setVisible(true);
                if (gd.getResult()) {
                	Subjects g = gd.getSubject();
				    try {
						ari.insertSubject(g);
						loadSubjects();
					} catch (RemoteException e) {
						 JOptionPane.showMessageDialog(subjectListFrame.this, e.getMessage());
					}
				}
            }
        };
        t.start();
		
	}

	private List<Subjects> loadSubjects() {
		List<Subjects> stud = null;
		try {
			stud = (List<Subjects>) ari.getSubjectList();
			updateProgress(stud);
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
		
	}
	
	private void updateProgress(final List<Subjects> stud) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (stud!= null){
					groupTable.setModel(new SubjectTableModel(stud));
					groupTable.getColumnModel().getColumn(0).setPreferredWidth(150);
                    }
			}
		});
		
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
		setTitle("\u041f\u0440\u0435\u0434\u043c\u0435\u0442\u044b");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== scrollPane1 ========
		{

			//---- groupTable ----
			groupTable.setModel(new DefaultTableModel(
				new Object[][] {
					{null},
					{null},
				},
				new String[] {
					"\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u0430"
				}
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = -4910537311913001318L;
				Class<?>[] columnTypes = new Class<?>[] {
					String.class
				};
				boolean[] columnEditable = new boolean[] {
					false
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
				cm.getColumn(0).setPreferredWidth(150);
			}
			scrollPane1.setViewportView(groupTable);
		}
		contentPane.add(scrollPane1, BorderLayout.CENTER);

		//======== panel1 ========
		{
			panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 13, 13));

			//---- addButton ----
			addButton.setText("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0437\u0430\u043f\u0438\u0441\u044c");
			addButton.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Add.png")));
			panel1.add(addButton);

			//---- editButton ----
			editButton.setText("\u0420\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c");
			editButton.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Pencil_2.png")));
			panel1.add(editButton);

			//---- delButton ----
			delButton.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Denided.png")));
			delButton.setText("\u0423\u0434\u0430\u043b\u0438\u0442\u044c");
			panel1.add(delButton);
		}
		contentPane.add(panel1, BorderLayout.SOUTH);

		//======== panel2 ========
		{
			panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
		}
		contentPane.add(panel2, BorderLayout.NORTH);
		setSize(461, 376);
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

/*
 * Created by JFormDesigner on Thu Dec 26 18:16:05 GMT+05:00 2013
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
import beans.Questions;
import beans.Themes;
import rmi.AutoschoolRmiInterface;
import tablemodels.QuestionsTableModel;

/**
 * @author ????????
 */
public class QuestionsListFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AutoschoolRmiInterface ari; 
	public QuestionsListFrame(Frame frame, AutoschoolRmiInterface ar) {
		initComponents();
		this.ari = ar;
		loadQuestions();
		button1.setName("addQuest");
		button2.setName("editQuest");
		button4.setName("delQuest");
		button1.addActionListener(this);
		button2.addActionListener(this);
		button4.addActionListener(this);
	}

	private List<Questions> loadQuestions() {
		List<Questions> stud = null;
		try {
			stud = (List<Questions>) ari.getQuestList();
			updateProgress(stud);
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
		
	}
	
	private List<Themes> loadThemes() {
		List<Themes> stud = null;
		try {
			stud = (List<Themes>) ari.getThemeList();
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Component) {
			Component c = (Component) e.getSource();
			if (c.getName().equals("addQuest")) {
				addQuest();
			}
			if (c.getName().equals("editQuest")) {
				editQuest();
            }
			if (c.getName().equals("cancel")) {
				dispose();
            }
			if (c.getName().equals("delQuest")) {
				delQuest();
            }
		
		}
	}
	
	private void delQuest() {
		Thread t = new Thread() {
			public void run() {
				QuestionsTableModel stm = (QuestionsTableModel) groupTable.getModel();
					// Проверяем - выделен ли хоть какой-нибудь элемент
					if (groupTable.getSelectedRow() >= 0) {
						if (JOptionPane.showOptionDialog(
								QuestionsListFrame.this, 
								  "Вы хотите удалить запись?", 
								  "Удаление записи", 
								  JOptionPane.YES_NO_OPTION, 
								  JOptionPane.ERROR_MESSAGE, 
								  null, 
								  new Object[]{"Да", "Нет"}, 
								  "Да")== JOptionPane.YES_OPTION) {
							Questions s = stm.getQuestion(groupTable.getSelectedRow());
							try {
								ari.deleteQuestion(s);
								loadQuestions();
							} catch (RemoteException e) {
                                JOptionPane.showMessageDialog(QuestionsListFrame.this, e.getMessage());
                            }
						}
					} // Если элемент не выделен - сообщаем пользователю, что это необходимо
					else {
						 JOptionPane.showMessageDialog(QuestionsListFrame.this,
	                                "Необходимо выделить запись в таблице!", "Внимание", 2);
                    }
				}
		};
		t.start();
		
	}

	private void editQuest() {
		Thread t = new Thread() {
			public void run() {
				QuestionsTableModel stm = (QuestionsTableModel) groupTable.getModel();
                    // Проверяем - выделен ли хоть какой-нибудь элемент
                    if (groupTable.getSelectedRow() >= 0) {
                    	Questions s = stm.getQuestion(groupTable.getSelectedRow());
                    	QuestionsDialog gd =  new QuestionsDialog(QuestionsListFrame.this,loadThemes());
                		    gd.setQuest(s);
                            gd.setModal(true);
                            gd.setVisible(true);
                            if (gd.getResult()) {
                            	Questions g = gd.getQuest();
                            	try {
                            		ari.updateQuestion(g);
            						loadQuestions();
            					} catch (Exception e) {
            						 JOptionPane.showMessageDialog(QuestionsListFrame.this, e.getMessage());
            					}
                            }
                    } // Если элемент не выделен - сообщаем пользователю, что это необходимо
                    else {
                        JOptionPane.showMessageDialog(QuestionsListFrame.this,
                                "Необходимо выделить запись в таблице!", "Внимание", 2);
                    }
                
            }
        };
        t.start();
		
	}

	private void addQuest() {
		Thread t = new Thread() {
            public void run() {
            	QuestionsDialog gd =  new QuestionsDialog(QuestionsListFrame.this,loadThemes());
				gd.setModal(true);
				gd.setVisible(true);
                if (gd.getResult()) {
                	Questions g = gd.getQuest();
				    try {
						ari.insertQuestion(g);
						loadQuestions();
					} catch (Exception e) {
						 JOptionPane.showMessageDialog(QuestionsListFrame.this, e.getMessage());
					}
				    
				}
            }
        };
        t.start();
		
	}

	private void updateProgress(final List<Questions> stud) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (stud!= null){
					groupTable.setModel(new QuestionsTableModel(stud));
					groupTable.getColumnModel().getColumn(0).setPreferredWidth(376);
					groupTable.getColumnModel().getColumn(1).setPreferredWidth(90);
					groupTable.getColumnModel().getColumn(2).setPreferredWidth(165);
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
		setTitle("\u0422\u0435\u0441\u0442\u043e\u0432\u044b\u0435 \u0437\u0430\u0434\u0430\u043d\u0438\u044f");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== scrollPane1 ========
		{

			//---- groupTable ----
			groupTable.setModel(new DefaultTableModel(
				new Object[][] {
					{null, null, null},
					{null, "", null},
				},
				new String[] {
					"\u0422\u0435\u043a\u0441\u0442 \u0432\u043e\u043f\u0440\u043e\u0441\u0430", "\u0421\u043b\u043e\u0436\u043d\u043e\u0441\u0442\u044c", "\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u0442\u0435\u043c\u044b"
				}
			) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;
				Class<?>[] columnTypes = new Class<?>[] {
					String.class, String.class, Date.class
				};
				boolean[] columnEditable = new boolean[] {
					false, false, false
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
				cm.getColumn(0).setPreferredWidth(300);
				cm.getColumn(1).setResizable(false);
				cm.getColumn(1).setPreferredWidth(90);
				cm.getColumn(2).setResizable(false);
				cm.getColumn(2).setPreferredWidth(105);
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
		setSize(641, 533);
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

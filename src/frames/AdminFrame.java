/*
 * Created by JFormDesigner on Sun Dec 08 19:49:52 GMT+05:00 2013
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.rmi.RemoteException;
import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import beans.Questions;
import beans.Themes;
import beans.TypeUser;
import beans.Users;
import rmi.AutoschoolRmiInterface;
import tablemodels.UsersTableModel;

/**
 * @author ????????
 */
public class AdminFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7672732730773983037L;
	private static final transient Logger log = LoggerFactory.getLogger(AdminFrame.class);
	private AutoschoolRmiInterface ari;
	private static final String ADD_STUDENT = "addStudent";
	private static final String UPDATE_ST = "updateStudent";
	private static final String DEL_ST = "delStudent";
	
	public AdminFrame(AutoschoolRmiInterface ar) {
		dbConnect(ar);
		initComponents();
		loadUsers();
		process();
	}
	
	private void dbConnect(AutoschoolRmiInterface ar)
	{
		
		this.ari = ar;
	}
	
	private void process() {
		addUserButton.setName(ADD_STUDENT);
		UsrMenuAddUsr.setName(ADD_STUDENT);
		UsrMenuEditUsr.setName(UPDATE_ST);
		editUserButton.setName(UPDATE_ST);
		UsrMenuDelUsr.setName(DEL_ST);
		delUserButton.setName(DEL_ST);
		DBMenuBackup.setName("backupDB");
		backupDBButton.setName("backupDB");
		restoreDBButton.setName("restoreDB");
		DBMenuRestore.setName("restoreDB");
		DBMenuCheck.setName("checkDB");
		DBMenuStat.setName("statDB");
		UsrMenuRolMng.setName("listRoles");
		TestMenuList.setName("listQuestions");
		infoButton.setName("exit");
		infoButton.addActionListener(this);
		UsrMenuRolMng.addActionListener(this);
		DBMenuStat.addActionListener(this);
		TestMenuList.addActionListener(this);
		TestMenuAddTest.setName("addTest");
		StatsDBButton.setName("statDB");
		listTestsButton.setName("listQuestions");
		//EditAddGroup.setName("addGroup");
		//EditFind.setName("searchStud");
		CatalogTeacher.setName("listTeachers");
		//teachersButton.setName("listTeachers");
		CatalogMasters.setName("listMasters");
		CatalogAuto.setName("listAvto");
		CatalogCategories.setName("listCategories");
		//CatalogSubject.setName("listSubjects");
		//CatalogThemes.setName("listThemes");
		DBMenuBackup.addActionListener(this);
		listTestsButton.addActionListener(this);
		DBMenuCheck.addActionListener(this);
		backupDBButton.addActionListener(this);
		restoreDBButton.addActionListener(this);
		DBMenuRestore.addActionListener(this);
		addUserButton.addActionListener(this);
		UsrMenuAddUsr.addActionListener(this);
		UsrMenuEditUsr.addActionListener(this);
		editUserButton.addActionListener(this);
		UsrMenuDelUsr.addActionListener(this);
		delUserButton.addActionListener(this);
		CatalogTeacher.addActionListener(this);
		CatalogMasters.addActionListener(this);
		CatalogAuto.addActionListener(this);
		TestMenuAddTest.addActionListener(this);
		StatsDBButton.addActionListener(this);
		CatalogCategories.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Component) {
			Component c = (Component) e.getSource();
			if (c.getName().equals(ADD_STUDENT)) {
				insertUser();
			}
			if (c.getName().equals(UPDATE_ST)) {
            	updateuser();
            }
			if (c.getName().equals(DEL_ST)) {
            	deleteUser();
            }
			if (c.getName().equals("listCategories")) {
            	listCategories();
            }
			if (c.getName().equals("addTest")) {
            	addTest();
            }
			if (c.getName().equals("listTeachers")) {
				listTeachers();
            }
			if (c.getName().equals("listMasters")) {
				listMasters();
            }
			if (c.getName().equals("listAvto")) {
				listAvto();
            }
			if (c.getName().equals("listSubjects")) {
				//listSubjects();
            }
			if (c.getName().equals("listThemes")) {
				//listThemes();
            }
			if (c.getName().equals("backupDB")) {
				backupDB();
            }
			if (c.getName().equals("restoreDB")) {
				restoreDB();
            }
			if (c.getName().equals("checkDB")) {
				checkDB();
            }
			if (c.getName().equals("statDB")) {
				statDB();
            }
			if (c.getName().equals("listRoles")) {
				listRoles();
            }
			if (c.getName().equals("listQuestions")) {
				listQuestions();
            }
			if (c.getName().equals("exit")) {
				AuthFrame inst;
				inst = new AuthFrame(ari);
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				this.dispose();
				
			}
		}
	}
	
	private List<Themes> loadThemes() {
		List<Themes> stud = null;
		try {
			stud = (List<Themes>) ari.getThemeList();
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
		
	}
	
	private void addTest() {
		Thread t = new Thread() {
            public void run() {
            	QuestionsDialog gd =  new QuestionsDialog(AdminFrame.this,loadThemes());
				gd.setModal(true);
				gd.setVisible(true);
                if (gd.getResult()) {
                	Questions g = gd.getQuest();
				    try {
						ari.insertQuestion(g);
					} catch (Exception e) {
						 JOptionPane.showMessageDialog(AdminFrame.this, e.getMessage());
					}
				    
				}
            }
        };
        t.start();
		
	}

	private void listCategories() {
		CategListFrame clf = new CategListFrame(AdminFrame.this,ari );
		clf.setVisible(true);
		
	}

	
	
	private void listAvto() {
		AvtoListFrame td = new  AvtoListFrame(AdminFrame.this,ari);
        td.setVisible(true);
		
	}

	private void listTeachers() {
		teacherListFrame td = new  teacherListFrame(AdminFrame.this,ari,1);
        td.setVisible(true);
		
	}
	
	private void listMasters() {
		teacherListFrame td = new  teacherListFrame(AdminFrame.this,ari,2);
        td.setVisible(true);
		
	}

	private void listQuestions() {
		QuestionsListFrame qlf = new QuestionsListFrame(AdminFrame.this,ari);
		qlf.setVisible(true);
	}

	private void listRoles() {
		roleListFrame td = new  roleListFrame(AdminFrame.this,ari);
        td.setVisible(true);
		
	}

	private void deleteUser() {
		Thread t = new Thread() {
			public void run() {
				UsersTableModel stm = (UsersTableModel) table1.getModel();
					// Проверяем - выделен ли хоть какой-нибудь элемент
					if (table1.getSelectedRow() >= 0) {
						if (JOptionPane.showOptionDialog(
								AdminFrame.this, 
								  "Вы хотите удалить запись?", 
								  "Удаление записи", 
								  JOptionPane.YES_NO_OPTION, 
								  JOptionPane.ERROR_MESSAGE, 
								  null, 
								  new Object[]{"Да", "Нет"}, 
								  "Да")== JOptionPane.YES_OPTION) {
							Users s = stm.getUser(table1.getSelectedRow());
							try {
								ari.deleteUser(s);
								loadUsers();
							} catch (RemoteException e) {
                                JOptionPane.showMessageDialog(AdminFrame.this, e.getMessage());
                            }
						}
					} // Если элемент не выделен - сообщаем пользователю, что это необходимо
					else {
						 JOptionPane.showMessageDialog(AdminFrame.this,
	                                "Необходимо выделить запись в таблице!", "Внимание", 2);
                    }
				}
		};
		t.start();
		
	}

	private void updateuser() {
		Thread t = new Thread() {
			public void run() {
				UsersTableModel stm = (UsersTableModel) table1.getModel();
                    // Проверяем - выделен ли хоть какой-нибудь элемент
                    if (table1.getSelectedRow() >= 0) {
                    	Users s = stm.getUser(table1.getSelectedRow());
                    	UserDialog gd =  new UserDialog(AdminFrame.this,loadRoles());
                		    gd.setUser(s);
                            gd.setModal(true);
                            gd.setVisible(true);
                            if (gd.getResult()) {
                            	Users s1 = gd.getUser();
                            	try {
            						ari.updateUser(s1);
            						loadUsers();
            					} catch (RemoteException e) {
            						 JOptionPane.showMessageDialog(AdminFrame.this, e.getMessage());
            					}
                            }
                    } // Если элемент не выделен - сообщаем пользователю, что это необходимо
                    else {
                        JOptionPane.showMessageDialog(AdminFrame.this,
                                "Необходимо выделить запись в таблице!", "Внимание", 2);
                    }
                
            }
        };
        t.start();
		
	}

	private void insertUser() {
		Thread t = new Thread() {
            public void run() {
            	UserDialog gd =  new UserDialog(AdminFrame.this,loadRoles());
				gd.setModal(true);
				gd.setVisible(true);
                if (gd.getResult()) {
                	Users g = gd.getUser();
				    try {
						ari.insertUser(g);
						loadUsers();
					} catch (RemoteException e) {
						 JOptionPane.showMessageDialog(AdminFrame.this, e.getMessage());
					}
				}
            }
        };
        t.start();
		
	}

	private void statDB() {
		
		DBStatFrame dbf = new DBStatFrame(AdminFrame.this);
		dbf.setVisible(true);
	}

	private void checkDB() {
            try {
            	String user = "postgres";
        		String dbName = "Autoschool";///////////////исправить!!!
        		//String[] executeCmd = new String[]{"psql", "--username=" + user, "--file=" + path, dbName};
        		//String executeCmd = "pg_restore -i -U " + user + " -d " + dbName+" -v "+ path;
        		ProcessBuilder pb = new ProcessBuilder("psql",
        			    "--dbname",
        			    dbName,
        			    "--username",
        			    user,
        			    "-w",
        			    "--command",
        			    "vacuum analyze;" );
        		Process pr;

        		try {
        		  pr = pb.start();
        		  StreamRender outputStdout = new StreamRender(pr.getInputStream(), StreamRender.STDOUT);
        		  // your STDOUT output here
        		  outputStdout.start();

        		  StreamRender outputStderr = new StreamRender(pr.getErrorStream(), StreamRender.STDERR);
        		  // your STDERR outpu here
        		  outputStderr.start();

        		  pr.waitFor();
        		  if (pr.exitValue() != 0) {
        				javax.swing.JOptionPane.showMessageDialog(AdminFrame.this, "Не удалось проверить БД. Воспользуйтесь восстановлением!","Внимание", 2);
                    	log.info("Не удалось восстановить резервную копию базы данных");
        		  } else {
        			  javax.swing.JOptionPane.showMessageDialog(AdminFrame.this, "Проверка БД на ошибки успешно проведена! Ошибки не найдены","Внимание", 2);
                  	log.info("Резервная копия базы данных успешно восстановлена!");
        		  }

        		} catch (Exception e) {
        			 e.printStackTrace();
        		}  
        		/*Process runtimeProcess;
            	runtimeProcess = Runtime.getRuntime().exec(executeCmd);
                int processComplete = runtimeProcess.waitFor();
     
                if (processComplete == 0) {
                	javax.swing.JOptionPane.showMessageDialog(null, "Резервная копия базы данных успешно восстановлена!");
                	log.info("Резервная копия базы данных успешно восстановлена!");
                } else {
                	javax.swing.JOptionPane.showMessageDialog(null, "Не удалось восстановить резервную копию базы данных");
                	log.info("Не удалось восстановить резервную копию базы данных");
                }*/
        }catch (Exception ex) {
            ex.printStackTrace();
        }
		
	}
	
	

	private void restoreDB() {
		JFileChooser fileopen = new JFileChooser();   
		FileNameExtensionFilter filter = new FileNameExtensionFilter("DB FILES", "sql");
		fileopen.addChoosableFileFilter(filter);
		fileopen.setCurrentDirectory(new java.io.File("C:/backups/"));
		fileopen.setDialogTitle("Выберите папку для резервной копии");
		fileopen.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileopen.setAcceptAllFileFilterUsed(false);   
        int ret = fileopen.showDialog(null, "Восстановить резервную копию из файла");                
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            try {
            	String path = file.getAbsolutePath();
            	System.out.println(path);
            	String user = "postgres";
        		String dbName = "Auto";///////////////исправить!!!
        		//String[] executeCmd = new String[]{"psql", "--username=" + user, "--file=" + path, dbName};
        		//String executeCmd = "pg_restore -i -U " + user + " -d " + dbName+" -v "+ path;
        		ProcessBuilder pb = new ProcessBuilder("pg_restore",
        			    "-i",
        			    "-U",
        			    user,
        			    "-d",
        			    dbName,
        			    "-v",
        			    path );
        		Process pr;

        		try {
        		  pr = pb.start();
        		  StreamRender outputStdout = new StreamRender(pr.getInputStream(), StreamRender.STDOUT);
        		  // your STDOUT output here
        		  outputStdout.start();

        		  StreamRender outputStderr = new StreamRender(pr.getErrorStream(), StreamRender.STDERR);
        		  // your STDERR outpu here
        		  outputStderr.start();

        		  pr.waitFor();
        		  if (pr.exitValue() != 0) {
        				javax.swing.JOptionPane.showMessageDialog(AdminFrame.this, "Не удалось восстановить резервную копию базы данных","Внимание", 2);
                    	log.info("Не удалось восстановить резервную копию базы данных");
        		  } else {
        			  javax.swing.JOptionPane.showMessageDialog(AdminFrame.this, "Резервная копия базы данных успешно восстановлена!","Внимание", 2);
                  	log.info("Резервная копия базы данных успешно восстановлена!");
        		  }

        		} catch (Exception e) {
        			 e.printStackTrace();
        		}  
        		/*Process runtimeProcess;
            	runtimeProcess = Runtime.getRuntime().exec(executeCmd);
                int processComplete = runtimeProcess.waitFor();
     
                if (processComplete == 0) {
                	javax.swing.JOptionPane.showMessageDialog(null, "Резервная копия базы данных успешно восстановлена!");
                	log.info("Резервная копия базы данных успешно восстановлена!");
                } else {
                	javax.swing.JOptionPane.showMessageDialog(null, "Не удалось восстановить резервную копию базы данных");
                	log.info("Не удалось восстановить резервную копию базы данных");
                }*/
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
		
	}

	private void backupDB() {
		JFileChooser chooser = new JFileChooser(); 
		FileNameExtensionFilter filter = new FileNameExtensionFilter("DB FILES", "sql");
		chooser.addChoosableFileFilter(filter);
		chooser.setCurrentDirectory(new java.io.File("C:/backups/"));
		chooser.setDialogTitle("Выберите папку для резервной копии");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setAcceptAllFileFilterUsed(false);              
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
        File file = chooser.getCurrentDirectory();  
        String chooserPath = file.getPath();
		String user = "postgres";
		String dbName = "Autoschool";
		String dt = new java.text.SimpleDateFormat("dd-MM-yy_hh_mm").format(java.util.Calendar.getInstance ().getTime());
		String path = chooserPath + "/backup_"+dt+".sql";
		String executeCmd = "pg_dump -U " + user + " -F t -C -c -f " + path+ " " + dbName;
        try {
        	Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
            int processComplete = runtimeProcess.waitFor();
 
            if (processComplete == 0) {
            	javax.swing.JOptionPane.showMessageDialog(AdminFrame.this, "Резервная копия базы данных создана успешно по пути: " + path +" !","Внимание", 2);
            	log.info("Резервная копия базы данных создана успешно!");
            } else {
            	javax.swing.JOptionPane.showMessageDialog(AdminFrame.this, "Не удалось создать резервную копию базы данных","Внимание", 2);
            	log.info("Не удалось создать резервную копию базы данных");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        }
		
	}

	protected void loadUsers() {
		try {
			List<Users> stud = ari.getUsersList();
			updateProgress(stud);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}
	
	protected List<TypeUser> loadRoles() {
		List<TypeUser> stud = null;
		try {
			stud = ari.getRolesList();
		} catch (RemoteException e) {
			e.printStackTrace();
		} 
		return stud;
		
	}
	
	private void updateProgress(final List<Users> stud) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (stud!= null){
					table1.setModel(new UsersTableModel(stud));
					table1.getColumnModel().getColumn(0).setPreferredWidth(100);
					table1.getColumnModel().getColumn(1).setPreferredWidth(130);
					table1.getColumnModel().getColumn(2).setPreferredWidth(150);
					table1.getColumnModel().getColumn(3).setPreferredWidth(225);
					table1.getColumnModel().getColumn(4).setPreferredWidth(140);
							
                    }
			}
		});
		
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		menuBar1 = new JMenuBar();
		DBmenu = new JMenu();
		DBMenuBackup = new JMenuItem();
		DBMenuRestore = new JMenuItem();
		DBMenuCheck = new JMenuItem();
		DBMenuStat = new JMenuItem();
		DBMenuOpJourn = new JMenuItem();
		DBMenuCryptJrnl = new JMenuItem();
		CatalogMenu = new JMenu();
		CatalogTeacher = new JMenuItem();
		CatalogMasters = new JMenuItem();
		CatalogAuto = new JMenuItem();
		CatalogCategories = new JMenuItem();
		UsrMenu = new JMenu();
		UsrMenuAddUsr = new JMenuItem();
		UsrMenuEditUsr = new JMenuItem();
		UsrMenuDelUsr = new JMenuItem();
		UsrMenuRolMng = new JMenuItem();
		TestMenu = new JMenu();
		TestMenuList = new JMenuItem();
		TestMenuAddTest = new JMenuItem();
		TestMenuResult = new JMenuItem();
		TestMenuReport = new JMenuItem();
		StatMenu = new JMenu();
		HelpMenu = new JMenu();
		HelpMenuItem = new JMenuItem();
		HelpAbout = new JMenuItem();
		ExitMenu = new JMenu();
		panel1 = new JPanel();
		toolBar1 = new JToolBar();
		restoreDBButton = new JButton();
		backupDBButton = new JButton();
		StatsDBButton = new JButton();
		openJournalButton = new JButton();
		addUserButton = new JButton();
		editUserButton = new JButton();
		delUserButton = new JButton();
		listTestsButton = new JButton();
		TestRsltButton = new JButton();
		statButton = new JButton();
		infoButton = new JButton();
		panel2 = new JPanel();
		panel3 = new JPanel();
		scrollPane2 = new JScrollPane();
		table1 = new JTable();
		table1.getTableHeader().setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		

		//======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		setTitle("Клиентское приложение для системного программиста");
		//======== menuBar1 ========
		{
			menuBar1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			menuBar1.setBorder(new EtchedBorder());

			//======== DBmenu ========
			{
				DBmenu.setText("\u0411\u0430\u0437\u0430 \u0434\u0430\u043d\u043d\u044b\u0445");
				DBmenu.setIcon(new ImageIcon(getClass().getResource("/images/16x16/DB1.png")));
				DBmenu.setBorder(new EmptyBorder(5, 5, 5, 5));
				DBmenu.setBorderPainted(true);

				//---- DBMenuBackup ----
				DBMenuBackup.setText("\u0421\u0434\u0435\u043b\u0430\u0442\u044c \u0440\u0435\u0437\u0435\u0440\u0432\u043d\u0443\u044e \u043a\u043e\u043f\u0438\u044e \u0411\u0414");
				DBMenuBackup.setIcon(new ImageIcon(getClass().getResource("/images/16x16/DB2.png")));
				DBmenu.add(DBMenuBackup);

				//---- DBMenuRestore ----
				DBMenuRestore.setText("\u0412\u043e\u0441\u0441\u0442\u0430\u043d\u043e\u0432\u043b\u0435\u043d\u0438\u0435 \u0438\u0437 \u0440\u0435\u0437\u0435\u0440\u0432\u043d\u043e\u0439 \u043a\u043e\u043f\u0438\u0438 \u0411\u0414");
				DBMenuRestore.setIcon(new ImageIcon(getClass().getResource("/images/16x16/DB3.png")));
				DBmenu.add(DBMenuRestore);

				//---- DBMenuCheck ----
				DBMenuCheck.setText("\u041f\u0440\u043e\u0432\u0435\u0440\u0438\u0442\u044c \u0411\u0414 \u043d\u0430 \u0446\u0435\u043b\u043e\u0441\u0442\u043d\u043e\u0441\u0442\u044c");
				DBMenuCheck.setIcon(new ImageIcon(getClass().getResource("/images/16x16/DB4.png")));
				DBmenu.add(DBMenuCheck);

				//---- DBMenuStat ----
				DBMenuStat.setText("\u0421\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043a\u0430 \u043f\u043e \u0411\u0414");
				DBMenuStat.setIcon(new ImageIcon(getClass().getResource("/images/16x16/DB5.png")));
				DBmenu.add(DBMenuStat);
				DBmenu.addSeparator();

				//---- DBMenuOpJourn ----
				DBMenuOpJourn.setText("\u041e\u0442\u043a\u0440\u044b\u0442\u044c \u0436\u0443\u0440\u043d\u0430\u043b \u0441\u043e\u0431\u044b\u0442\u0438\u0439");
				DBMenuOpJourn.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Folder_Add.png")));
				//DBmenu.add(DBMenuOpJourn);

				//---- DBMenuCryptJrnl ----
				DBMenuCryptJrnl.setText("\u0417\u0430\u0448\u0438\u0444\u0440\u043e\u0432\u0430\u0442\u044c \u0436\u0443\u0440\u043d\u0430\u043b \u0441\u043e\u0431\u044b\u0442\u0438\u0439");
				DBMenuCryptJrnl.setIcon(new ImageIcon(getClass().getResource("/images/16x16/DB6.png")));
				//DBmenu.add(DBMenuCryptJrnl);
			}
			menuBar1.add(DBmenu);

			//======== CatalogMenu ========
			{
				CatalogMenu.setText("\u0421\u043f\u0440\u0430\u0432\u043e\u0447\u043d\u0438\u043a\u0438");
				CatalogMenu.setBorder(new EmptyBorder(5, 5, 5, 5));

				//---- CatalogTeacher ----
				CatalogTeacher.setText("\u041f\u0440\u0435\u043f\u043e\u0434\u0430\u0432\u0430\u0442\u0435\u043b\u0438");
				CatalogTeacher.setIcon(new ImageIcon(getClass().getResource("/images/16x16/address.png")));
				CatalogMenu.add(CatalogTeacher);

				//---- CatalogMasters ----
				CatalogMasters.setText("\u041c\u0430\u0441\u0442\u0435\u0440\u0430 \u043f\u0440\u043e\u0438\u0437\u0432\u043e\u0434\u0441\u0442\u0432\u0435\u043d\u043d\u043e\u0433\u043e \u043e\u0431\u0443\u0447\u0435\u043d\u0438\u044f");
				CatalogMasters.setIcon(new ImageIcon(getClass().getResource("/images/16x16/User.png")));
				CatalogMenu.add(CatalogMasters);

				//---- CatalogAuto ----
				CatalogAuto.setText("\u0423\u0447\u0435\u0431\u043d\u044b\u0435 \u0430\u0432\u0442\u043e\u043c\u043e\u0431\u0438\u043b\u0438");
				CatalogAuto.setSelected(true);
				CatalogAuto.setIcon(new ImageIcon(getClass().getResource("/images/16x16/auto_financing_icon.png")));
				CatalogMenu.add(CatalogAuto);

				//---- CatalogCategories ----
				CatalogCategories.setText("\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0438\u0438 \u0422\u0421");
				CatalogCategories.setIcon(new ImageIcon(getClass().getResource("/images/16x16/File_List.png")));
				CatalogMenu.add(CatalogCategories);
			}
			menuBar1.add(CatalogMenu);

			//======== UsrMenu ========
			{
				UsrMenu.setText("\u0423\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u0438\u0435 \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f\u043c\u0438");
				UsrMenu.setIcon(new ImageIcon(getClass().getResource("/images/16x16/DB7.png")));
				UsrMenu.addSeparator();

				//---- UsrMenuAddUsr ----
				UsrMenuAddUsr.setText("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f");
				UsrMenuAddUsr.setIcon(new ImageIcon(getClass().getResource("/images/16x16/DB8.png")));
				UsrMenu.add(UsrMenuAddUsr);

				//---- UsrMenuEditUsr ----
				UsrMenuEditUsr.setText("\u0420\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f");
				UsrMenuEditUsr.setIcon(new ImageIcon(getClass().getResource("/images/16x16/DB9.png")));
				UsrMenu.add(UsrMenuEditUsr);

				//---- UsrMenuDelUsr ----
				UsrMenuDelUsr.setText("\u0423\u0434\u0430\u043b\u0438\u0442\u044c \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f");
				UsrMenuDelUsr.setIcon(new ImageIcon(getClass().getResource("/images/16x16/DB11.png")));
				UsrMenu.add(UsrMenuDelUsr);
				UsrMenu.addSeparator();

				//---- UsrMenuRolMng ----
				UsrMenuRolMng.setText("\u0423\u043f\u0440\u0430\u0432\u043b\u0435\u043d\u0438\u0435 \u0440\u043e\u043b\u044f\u043c\u0438");
				UsrMenuRolMng.setIcon(new ImageIcon(getClass().getResource("/images/16x16/DB10.png")));
				UsrMenu.add(UsrMenuRolMng);
			}
			menuBar1.add(UsrMenu);

			//======== TestMenu ========
			{
				TestMenu.setText("\u0422\u0435\u0441\u0442\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u0435");

				//---- TestMenuList ----
				TestMenuList.setText("\u0421\u043f\u0438\u0441\u043e\u043a \u0442\u0435\u0441\u0442\u043e\u0432\u044b\u0445 \u0437\u0430\u0434\u0430\u043d\u0438\u0439");
				TestMenuList.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Test_16.png")));
				TestMenu.add(TestMenuList);

				//---- TestMenuAddTest ----
				TestMenuAddTest.setText("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u043d\u043e\u0432\u044b\u0435 \u0442\u0435\u0441\u0442\u043e\u0432\u044b\u0435 \u0437\u0430\u0434\u0430\u043d\u0438\u044f");
				TestMenuAddTest.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Add_Symbol.png")));
				TestMenu.add(TestMenuAddTest);
				TestMenu.addSeparator();

				//---- TestMenuResult ----
				TestMenuResult.setText("\u0420\u0435\u0437\u0443\u043b\u044c\u0442\u0430\u0442\u044b \u0442\u0435\u0441\u0442\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u044f");
				TestMenuResult.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Calendar_2.png")));
				TestMenu.add(TestMenuResult);

				//---- TestMenuReport ----
				TestMenuReport.setText("\u0412\u044b\u0432\u0435\u0441\u0442\u0438 \u043e\u0442\u0447\u0435\u0442 \u043e \u0440\u0435\u0437\u0443\u043b\u044c\u0442\u0430\u0442\u0430\u0445 \u0442\u0435\u0441\u0442\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u044f");
				TestMenuReport.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Checkmark.png")));
				//TestMenu.add(TestMenuReport);
			}
			menuBar1.add(TestMenu);

			//======== StatMenu ========
			{
				StatMenu.setText("\u0421\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043a\u0430");
				StatMenu.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Chart_Bar_Files.png")));
			}
			//menuBar1.add(StatMenu);

			//======== HelpMenu ========
			{
				HelpMenu.setText("\u0421\u043f\u0440\u0430\u0432\u043a\u0430");

				//---- HelpMenuItem ----
				HelpMenuItem.setText("\u0421\u043f\u0440\u0430\u0432\u043a\u0430");
				HelpMenuItem.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Help.png")));
				HelpMenu.add(HelpMenuItem);

				//---- HelpAbout ----
				HelpAbout.setText("\u041e \u043f\u0440\u043e\u0433\u0440\u0430\u043c\u043c\u0435");
				HelpAbout.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Info.png")));
				HelpMenu.add(HelpAbout);
			}
			menuBar1.add(HelpMenu);

			//======== ExitMenu ========
			{
				ExitMenu.setText("\u0412\u044b\u0445\u043e\u0434 ");
				ExitMenu.setIcon(new ImageIcon(getClass().getResource("/images/16x16/logout.png")));
			}
			//menuBar1.add(ExitMenu);
		}
		setJMenuBar(menuBar1);

		//======== panel1 ========
		{
			panel1.setLayout(new FlowLayout(FlowLayout.LEFT));

			//======== toolBar1 ========
			{
				//toolBar1.setBorder(LineBorder.createBlackLineBorder());

				//---- restoreDBButton ----
				restoreDBButton.setToolTipText("\u0412\u043e\u0441\u0441\u0442\u0430\u043d\u043e\u0432\u0438\u0442\u044c \u0438\u0437 \u0440\u0435\u0437\u0435\u0440\u0432\u043d\u043e\u0439 \u043a\u043e\u043f\u0438\u0438 \u0411\u0414");
				restoreDBButton.setIcon(new ImageIcon(getClass().getResource("/images/DB3.png")));
				toolBar1.add(restoreDBButton);

				//---- backupDBButton ----
				backupDBButton.setIcon(new ImageIcon(getClass().getResource("/images/DB2.png")));
				backupDBButton.setToolTipText("\u0421\u0434\u0435\u043b\u0430\u0442\u044c \u0440\u0435\u0437\u0435\u0440\u0432\u043d\u0443\u044e \u043a\u043e\u043f\u0438\u044e");
				toolBar1.add(backupDBButton);

				//---- StatsDBButton ----
				StatsDBButton.setToolTipText("\u0421\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043a\u0430 \u043f\u043e \u0411\u0414");
				StatsDBButton.setIcon(new ImageIcon(getClass().getResource("/images/DB12.png")));
				toolBar1.add(StatsDBButton);
				toolBar1.addSeparator();

				//---- openJournalButton ----
				openJournalButton.setIcon(new ImageIcon(getClass().getResource("/images/Blocknotes_2.png")));
				openJournalButton.setToolTipText("\u041e\u0442\u043a\u0440\u044b\u0442\u044c \u0436\u0443\u0440\u043d\u0430\u043b \u0441\u043e\u0431\u044b\u0442\u0438\u0439");
				toolBar1.add(openJournalButton);
				toolBar1.addSeparator();

				//---- addUserButton ----
				addUserButton.setIcon(new ImageIcon(getClass().getResource("/images/DB8.png")));
				addUserButton.setToolTipText("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f");
				toolBar1.add(addUserButton);

				//---- editUserButton ----
				editUserButton.setIcon(new ImageIcon(getClass().getResource("/images/DB9.png")));
				editUserButton.setToolTipText("\u0420\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u0437\u0430\u043f\u0438\u0441\u044c \u043e\u0431 \u0443\u0447\u0435\u043d\u0438\u043a\u0435");
				toolBar1.add(editUserButton);

				//---- delUserButton ----
				delUserButton.setIcon(new ImageIcon(getClass().getResource("/images/DB11.png")));
				toolBar1.add(delUserButton);
				toolBar1.addSeparator();

				//---- listTestsButton ----
				listTestsButton.setIcon(new ImageIcon(getClass().getResource("/images/Test_32.png")));
				listTestsButton.setToolTipText("\u0421\u043f\u0438\u0441\u043e\u043a \u0442\u0435\u0441\u0442\u043e\u0432\u044b\u0445 \u0437\u0430\u0434\u0430\u043d\u0438\u0439");
				toolBar1.add(listTestsButton);

				//---- TestRsltButton ----
				TestRsltButton.setIcon(new ImageIcon(getClass().getResource("/images/Calendar_2.png")));
				TestRsltButton.setToolTipText("\u0420\u0435\u0437\u0443\u043b\u044c\u0442\u0430\u0442\u044b \u0442\u0435\u0441\u0442\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u044f");
				toolBar1.add(TestRsltButton);

				//---- statButton ----
				statButton.setIcon(new ImageIcon(getClass().getResource("/images/DB5.png")));
				statButton.setToolTipText("\u0421\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043a\u0430");
				toolBar1.add(statButton);
				toolBar1.addSeparator();

				//---- infoButton ----
				infoButton.setIcon(new ImageIcon(getClass().getResource("/images/logout.png")));
				toolBar1.add(infoButton);
			}
			panel1.add(toolBar1);
		}
		contentPane.add(panel1, BorderLayout.NORTH);

		//======== panel2 ========
		{
			panel2.setLayout(new BorderLayout());
		}
		contentPane.add(panel2, BorderLayout.SOUTH);

		//======== panel3 ========
		{
			panel3.setLayout(new BorderLayout());

			//======== scrollPane2 ========
			{

				//---- table1 ----
				table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
				table1.setModel(new DefaultTableModel(
					new Object[][] {
						{null, null, null, null, null},
						{null, null, null, null, null},
					},
					new String[] {
						"\u041b\u043e\u0433\u0438\u043d", "\u0418\u043c\u044f \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f", "\u041f\u0430\u0440\u043e\u043b\u044c (\u0445\u044d\u0448-\u0444\u0443\u043d\u043a\u0446\u0438\u044f)", "\u0420\u043e\u043b\u044c \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f", "\u041a\u043e\u043c\u043c\u0435\u043d\u0442\u0430\u0440\u0438\u0439"
					}
				));
				{
					TableColumnModel cm = table1.getColumnModel();
					cm.getColumn(0).setPreferredWidth(100);
					cm.getColumn(1).setPreferredWidth(130);
					cm.getColumn(2).setPreferredWidth(150);
					cm.getColumn(3).setPreferredWidth(110);
					cm.getColumn(4).setPreferredWidth(140);
				}
				scrollPane2.setViewportView(table1);
			}
			panel3.add(scrollPane2, BorderLayout.CENTER);
		}
		contentPane.add(panel3, BorderLayout.CENTER);
		this.setSize(753, 445);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JMenuBar menuBar1;
	private JMenu DBmenu;
	private JMenuItem DBMenuBackup;
	private JMenuItem DBMenuRestore;
	private JMenuItem DBMenuCheck;
	private JMenuItem DBMenuStat;
	private JMenuItem DBMenuOpJourn;
	private JMenuItem DBMenuCryptJrnl;
	private JMenu CatalogMenu;
	private JMenuItem CatalogTeacher;
	private JMenuItem CatalogMasters;
	private JMenuItem CatalogAuto;
	private JMenuItem CatalogCategories;
	private JMenu UsrMenu;
	private JMenuItem UsrMenuAddUsr;
	private JMenuItem UsrMenuEditUsr;
	private JMenuItem UsrMenuDelUsr;
	private JMenuItem UsrMenuRolMng;
	private JMenu TestMenu;
	private JMenuItem TestMenuList;
	private JMenuItem TestMenuAddTest;
	private JMenuItem TestMenuResult;
	private JMenuItem TestMenuReport;
	private JMenu StatMenu;
	private JMenu HelpMenu;
	private JMenuItem HelpMenuItem;
	private JMenuItem HelpAbout;
	private JMenu ExitMenu;
	private JPanel panel1;
	private JToolBar toolBar1;
	private JButton restoreDBButton;
	private JButton backupDBButton;
	private JButton StatsDBButton;
	private JButton openJournalButton;
	private JButton addUserButton;
	private JButton editUserButton;
	private JButton delUserButton;
	private JButton listTestsButton;
	private JButton TestRsltButton;
	private JButton statButton;
	private JButton infoButton;
	private JPanel panel2;
	private JPanel panel3;
	private JScrollPane scrollPane2;
	private JTable table1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

}

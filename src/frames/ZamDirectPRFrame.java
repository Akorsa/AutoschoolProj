/*
 * Created by JFormDesigner on Sat Dec 07 23:48:12 GMT+05:00 2013
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import beans.Categories;
import beans.Educationtypes;
import beans.Groups;
import beans.Students;
import beans.TypeEducation;
import beans.YearOfEducation;

import rmi.AutoschoolRmiInterface;
import tablemodels.StudentTableModel;
import util.ExcelJob;

/**
 * @author ????????
 */
public class ZamDirectPRFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7126545798843739500L;
	private AutoschoolRmiInterface ari;
	private static final String ADD_STUDENT = "addStudent";
	private static final String UPDATE_ST = "updateStudent";
	private static final String DEL_ST = "delStudent";
	private static final String MOV_ST = "moveStudent";

	public ZamDirectPRFrame(AutoschoolRmiInterface ar) {
		super();
		dbConnect(ar);
		initComponents();
		pop_tree();
		process();
	}

	private void process() {
		addStudentButton.setName(ADD_STUDENT);
		EditAddStud.setName(ADD_STUDENT);
		editStudentButton.setName(UPDATE_ST);
		EditEditStud.setName(UPDATE_ST);
		delStudentButton.setName(DEL_ST);
		EditDelStud.setName(DEL_ST);
		EditMoveStud.setName(MOV_ST);
		listGroupButton.setName("listGroup");
		EditGroup.setName("listGroup");
		addGroupButton.setName("addGroup");
		EditAddGroup.setName("addGroup");
		EditFind.setName("searchStud");
		findStudButton.setName("searchStud");
		CatalogTeacher.setName("listTeachers");
		teachersButton.setName("listTeachers");
		CatalogMasters.setName("listMasters");
		CatalogAuto.setName("listAvto");
		CatalogCategory.setName("listCategory");
		DocumListSvid.setName("listSvid");
		EditPatterns.setName("OpenCatalog");
		infoButton.setName("help");
		DocumListGroup.setName("SpisokGrup");
		statMenuItem.setName("stat");
		exitButton.setName("exit");
		DocumPutevList.setName("reestrSvid");
		DocumProtocol.setName("protocol");
		scheduleButton.setName("listSvid");
		addStudentButton.addActionListener(this);
		EditAddStud.addActionListener(this);
		editStudentButton.addActionListener(this);
		EditEditStud.addActionListener(this);
		delStudentButton.addActionListener(this);
		EditDelStud.addActionListener(this);
		EditMoveStud.addActionListener(this);
		listGroupButton.addActionListener(this);
		addGroupButton.addActionListener(this);
		EditAddGroup.addActionListener(this);
		EditGroup.addActionListener(this);
		EditFind.addActionListener(this);
		findStudButton.addActionListener(this);
		CatalogTeacher.addActionListener(this);
		teachersButton.addActionListener(this);
		CatalogMasters.addActionListener(this);
		CatalogAuto.addActionListener(this);
		CatalogCategory.addActionListener(this);
		DocumListSvid.addActionListener(this);
		EditPatterns.addActionListener(this);
		infoButton.addActionListener(this);
		DocumListGroup.addActionListener(this);
		exitButton.addActionListener(this);
		statMenuItem.addActionListener(this);
		DocumPutevList.addActionListener(this);
		DocumProtocol.addActionListener(this);
		scheduleButton.addActionListener(this);
	}

	private void dbConnect(AutoschoolRmiInterface ar) {

		this.ari = ar;
	}

	private DefaultMutableTreeNode processHierarchy() {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(
				"Учебные годы   ");
		try {
			List<YearOfEducation> cat = (List<YearOfEducation>) ari
					.getYearOfEducation();
			DefaultMutableTreeNode child, grandchild;
			for (YearOfEducation categ : cat) {
				child = new DefaultMutableTreeNode(categ);
				node.add(child);
				List<Groups> group = ari.getGroupYearEdu(categ
						.getNumberOfYear());
				for (Groups categ1 : group) {
					grandchild = new DefaultMutableTreeNode(categ1);
					child.add(grandchild);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (node);
	}

	public final void pop_tree() {
		try {

			DefaultMutableTreeNode root = processHierarchy();
			DefaultTreeModel treeModel = new DefaultTreeModel(root);
			tree1.setModel(treeModel);
			tree1.getSelectionModel().setSelectionMode(
					TreeSelectionModel.SINGLE_TREE_SELECTION);
			tree1.addTreeSelectionListener(new TreeSelectionListener() {
				@Override
				public void valueChanged(TreeSelectionEvent e) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree1
							.getLastSelectedPathComponent();
					if (node != null) {
						Object nodeInfo = node.getUserObject();
						if (nodeInfo instanceof Groups) {
							Groups group = (Groups) nodeInfo;
							System.out.println(group.getGroupName());
							loadStudents(group);
						}
						if (nodeInfo instanceof YearOfEducation) {
							YearOfEducation year = (YearOfEducation) nodeInfo;
							System.out.println(year.getNumberOfYear());
						} else
							return;
					}
				}
			});
			ImageIcon tutorialIcon = new ImageIcon(getClass().getResource(
					"/images/16x16/Folder.png"));

			if (tutorialIcon != null) {
				DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
				renderer.setLeafIcon(tutorialIcon);
				tree1.setCellRenderer(renderer);
			}
		} catch (Exception e) {
			e.printStackTrace();
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

	protected List<Educationtypes> loadEducationtypes() {
		List<Educationtypes> stud = null;
		try {
			stud = (List<Educationtypes>) ari.getEducationtypes();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return stud;

	}

	protected List<Categories> loadCategories() {
		List<Categories> stud = null;
		try {
			stud = (List<Categories>) ari.getCategoryList();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return stud;

	}

	protected List<TypeEducation> loadTypeEd() {
		List<TypeEducation> stud = null;
		try {
			stud = ari.getTypeEducation();
			// for (Students cat : stud) {
			// System.out.println(cat.getName()+cat.getSurname());
			// }
			if (stud.isEmpty()) {
				System.out.println("Fucking shit typeEd");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return stud;

	}

	protected List<YearOfEducation> loadYearEd() {
		List<YearOfEducation> stud = null;
		try {
			stud = (List<YearOfEducation>) ari.getYearOfEducation();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return stud;

	}

	protected List<Groups> loadGroup2() {
		List<Groups> stud = null;
		try {
			stud = (List<Groups>) ari.getGroupList();
			// for (Students cat : stud) {
			// System.out.println(cat.getName()+cat.getSurname());
			// }
			if (stud.isEmpty()) {
				System.out.println("Fucking shit goup");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return stud;

	}

	protected List<Groups> loadGroup() {
		List<Groups> stud = null;
		try {
			stud = (List<Groups>) ari.getCurrentGroupList();
			// for (Students cat : stud) {
			// System.out.println(cat.getName()+cat.getSurname());
			// }
			if (stud.isEmpty()) {
				System.out.println("Fucking shit goup");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return stud;

	}

	private void updateProgress(final List<Students> stud) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (stud != null) {
					tableStudents.setModel(new StudentTableModel(stud));
					tableStudents.getColumnModel().getColumn(0)
							.setPreferredWidth(135);
					tableStudents.getColumnModel().getColumn(2)
							.setPreferredWidth(150);
					tableStudents.getColumnModel().getColumn(4)
							.setPreferredWidth(122);
					tableStudents.getColumnModel().getColumn(5)
							.setPreferredWidth(122);
					tableStudents.getColumnModel().getColumn(6)
							.setPreferredWidth(157);
					tableStudents.getColumnModel().getColumn(7)
							.setPreferredWidth(150);
					tableStudents.getColumnModel().getColumn(8)
							.setPreferredWidth(115);
					tableStudents.getColumnModel().getColumn(9)
							.setPreferredWidth(136);
					tableStudents.getColumnModel().getColumn(10)
							.setPreferredWidth(134);
					tableStudents.getColumnModel().getColumn(11)
							.setPreferredWidth(116);
					tableStudents.getColumnModel().getColumn(12)
							.setPreferredWidth(114);
					tableStudents.getColumnModel().getColumn(13)
							.setPreferredWidth(107);
					tableStudents.getColumnModel().getColumn(14)
							.setPreferredWidth(215);
					tableStudents.getColumnModel().getColumn(15)
							.setPreferredWidth(130);
					tableStudents.getColumnModel().getColumn(17)
							.setPreferredWidth(175);
					tableStudents.getColumnModel().getColumn(18)
							.setPreferredWidth(161);
				}
			}
		});

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Component) {
			Component c = (Component) e.getSource();
			if (c.getName().equals(ADD_STUDENT)) {
				insertStudent();
			}
			if (c.getName().equals(UPDATE_ST)) {
				updateStudent();
			}
			if (c.getName().equals(DEL_ST)) {
				deleteStudent();
			}
			if (c.getName().equals(MOV_ST)) {
				movStudent();
			}
			if (c.getName().equals("listGroup")) {
				listGroup();
			}
			if (c.getName().equals("addGroup")) {
				addGroup();
			}
			if (c.getName().equals("searchStud")) {
				searchStud();
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
			if (c.getName().equals("listCategory")) {
				listCategory();
			}
			if (c.getName().equals("listSvid")) {
				listSvid();
			}
			if (c.getName().equals("stat")) {
				stat();
			}
			if (c.getName().equals("reestrSvid")) {
				reestrSvid();
			}
			if (c.getName().equals("protocol")) {
				printProtocol();
			}
			
			
			if (c.getName().equals("OpenCatalog")) {
				try {
					Desktop.getDesktop().open(new File("c:/DOC"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (c.getName().equals("help")) {
				try {
					Desktop.getDesktop().open(new File("c:/docs/HELP.docx"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (c.getName().equals("SpisokGrup")) {
				printSpisokGrup();
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

	private void printProtocol() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree1
				.getLastSelectedPathComponent();

		if (node != null) {
			Object nodeInfo = node.getUserObject();
			if (nodeInfo instanceof Groups) {
				Groups group = (Groups) nodeInfo;
				List<Students> stud = null;

				try {
					stud = ari.getStudentsFromGroup(group.getGroupid());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ExcelJob.exelSet8(stud);

			} else {
				JOptionPane.showMessageDialog(ZamDirectPRFrame.this,
						"Необходимо выделить группу в дереве учебных групп!",
						"Внимание", 2);
			}
		} else {
			JOptionPane.showMessageDialog(ZamDirectPRFrame.this,
					"Необходимо выделить группу в дереве учебных групп!",
					"Внимание", 2);
		}
		
	}

	private void reestrSvid() {
		List<Students> lst = null;
		try {
			lst = ari.getSvid();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ExcelJob.exelSet7(lst);
		
	}

	private void stat() {
		DriveStatDate rlf = new DriveStatDate(ZamDirectPRFrame.this, ari, loadGroup());
		rlf.setVisible(true);
		
	}

	private void printSpisokGrup() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree1
				.getLastSelectedPathComponent();

		if (node != null) {
			Object nodeInfo = node.getUserObject();
			if (nodeInfo instanceof Groups) {
				Groups group = (Groups) nodeInfo;
				List<Students> stud = null;

				try {
					stud = ari.getStudentsFromGroup(group.getGroupid());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ExcelJob.exelSet3(stud, group);

			} else {
				JOptionPane.showMessageDialog(ZamDirectPRFrame.this,
						"Необходимо выделить группу в дереве учебных групп!",
						"Внимание", 2);
			}
		} else {
			JOptionPane.showMessageDialog(ZamDirectPRFrame.this,
					"Необходимо выделить группу в дереве учебных групп!",
					"Внимание", 2);
		}

	}

	private void listSvid() {

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree1
				.getLastSelectedPathComponent();

		if (node != null) {
			Object nodeInfo = node.getUserObject();
			if (nodeInfo instanceof Groups) {
				Groups group = (Groups) nodeInfo;
				SvidListFrame slf = new SvidListFrame(ZamDirectPRFrame.this,
						ari, group);
				slf.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(ZamDirectPRFrame.this,
						"Необходимо выделить группу в дереве учебных групп!",
						"Внимание", 2);
			}
		} else {
			JOptionPane.showMessageDialog(ZamDirectPRFrame.this,
					"Необходимо выделить группу в дереве учебных групп!",
					"Внимание", 2);
		}
	}

	private void listCategory() {
		CategListFrame clf = new CategListFrame(ZamDirectPRFrame.this, ari);
		clf.setVisible(true);

	}

	private void listAvto() {
		AvtoListFrame td = new AvtoListFrame(ZamDirectPRFrame.this, ari);
		td.setVisible(true);

	}

	private void listMasters() {
		teacherListFrame td = new teacherListFrame(ZamDirectPRFrame.this, ari,
				2);
		td.setVisible(true);

	}

	private void listTeachers() {
		teacherListFrame td = new teacherListFrame(ZamDirectPRFrame.this, ari,
				1);
		td.setVisible(true);

	}

	private void searchStud() {
		Thread t = new Thread() {
			public void run() {
				findStudDialog gd = new findStudDialog(ZamDirectPRFrame.this);
				gd.setModal(true);
				gd.setVisible(true);
				if (gd.getResult()) {
					int i = gd.getNumber();
					if (i == 0) {
						try {

							String[] s = gd.getString();
							List<Students> lst = ari.searchStudByFIO(s);
							updateProgress(lst);
						} catch (Exception e) {
							JOptionPane.showMessageDialog(
									ZamDirectPRFrame.this, e.getMessage());
						}
					}
					if (i == 1) {
						try {
							String s = gd.getNomSvid();
							List<Students> lst = ari.searchStudByNomSvid(s);
							updateProgress(lst);
						} catch (RemoteException e) {
							JOptionPane.showMessageDialog(
									ZamDirectPRFrame.this, e.getMessage());
						}
					}
					if (i == 2) {
						try {
							Date d = gd.getDate();
							List<Students> lst = ari.searchStudByDate(d);
							updateProgress(lst);
						} catch (RemoteException e) {
							JOptionPane.showMessageDialog(
									ZamDirectPRFrame.this, e.getMessage());
						}
					}

				}
			}
		};
		t.start();

	}

	private void addGroup() {
		Thread t = new Thread() {
			public void run() {
				GroupDialog gd = new GroupDialog(ZamDirectPRFrame.this,
						loadCategories(), loadEducationtypes(), loadYearEd());
				gd.setModal(true);
				gd.setVisible(true);
				if (gd.getResult()) {
					Groups g = gd.getGrup();
					try {
						ari.insertGrup(g);
						pop_tree();
					} catch (RemoteException e) {
						JOptionPane.showMessageDialog(ZamDirectPRFrame.this,
								e.getMessage());
					}
				}
			}
		};
		t.start();

	}

	private void listGroup() {
		groupListFrame sd = new groupListFrame(1, ZamDirectPRFrame.this, ari);
		sd.setVisible(true);

	}

	private void movStudent() {
		Thread t = new Thread() {
			public void run() {
				StudentTableModel stm = (StudentTableModel) tableStudents
						.getModel();
				// Проверяем - выделен ли хоть какой-нибудь элемент
				if (tableStudents.getSelectedRow() >= 0) {
					Students s = stm.getStudent(tableStudents.getSelectedRow());
					moveToGroupDialog sd = new moveToGroupDialog(
							ZamDirectPRFrame.this, loadYearEd(), ari);
					sd.setStudent(s);
					sd.setModal(true);
					sd.setVisible(true);
					if (sd.getResult()) {
						Groups gr = sd.getGroupsStudents();
						int id = sd.getStId();
						try {
							ari.updateStGr(gr, id);
							reloadTable();
						} catch (RemoteException e) {
							JOptionPane.showMessageDialog(
									ZamDirectPRFrame.this, e.getMessage());
						}
					}
				} // Если элемент не выделен - сообщаем пользователю, что это
					// необходимо
				else {
					JOptionPane.showMessageDialog(ZamDirectPRFrame.this,
							"Необходимо выделить студента в таблице!",
							"Внимание", 2);
				}

			}
		};
		t.start();

	}

	private void deleteStudent() {
		Thread t = new Thread() {
			public void run() {
				StudentTableModel stm = (StudentTableModel) tableStudents
						.getModel();
				// Проверяем - выделен ли хоть какой-нибудь элемент
				if (tableStudents.getSelectedRow() >= 0) {
					if (JOptionPane.showOptionDialog(ZamDirectPRFrame.this,
							"Вы хотите удалить запись?", "Удаление записи",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.ERROR_MESSAGE, null, new Object[] {
									"Да", "Нет" }, "Да") == JOptionPane.YES_OPTION) {
						Students s = stm.getStudent(tableStudents
								.getSelectedRow());
						try {
							ari.deleteBet(s);
							reloadTable();
						} catch (RemoteException e) {
							JOptionPane.showMessageDialog(
									ZamDirectPRFrame.this, e.getMessage());
						}
					}
				} // Если элемент не выделен - сообщаем пользователю, что это
					// необходимо
				else {
					JOptionPane.showMessageDialog(ZamDirectPRFrame.this,
							"Необходимо выделить студента в таблице!",
							"Внимание", 2);
				}
			}
		};
		t.start();

	}

	private void reloadTable() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree1
				.getLastSelectedPathComponent();

		Object nodeInfo = node.getUserObject();
		if (nodeInfo instanceof Groups) {
			Groups group = (Groups) nodeInfo;
			loadStudents(group);
		}
	}

	private void updateStudent() {
		Thread t = new Thread() {
			public void run() {
				StudentTableModel stm = (StudentTableModel) tableStudents
						.getModel();
				// Проверяем - выделен ли хоть какой-нибудь элемент
				if (tableStudents.getSelectedRow() >= 0) {
					Students s = stm.getStudent(tableStudents.getSelectedRow());
					StudentDialog sd = new StudentDialog(ZamDirectPRFrame.this,
							loadTypeEd(), loadGroup2(), 2, ari);
					sd.setStudent(s);
					sd.setModal(true);
					sd.setVisible(true);
					if (sd.getResult()) {
						Students s1 = sd.getStudents();
						try {
							ari.updateStudent(s1);
							reloadTable();
						} catch (RemoteException e) {
							JOptionPane.showMessageDialog(
									ZamDirectPRFrame.this, e.getMessage());
						}
					}
				} // Если элемент не выделен - сообщаем пользователю, что это
					// необходимо
				else {
					JOptionPane.showMessageDialog(ZamDirectPRFrame.this,
							"Необходимо выделить учащегося в таблице!",
							"Внимание", 2);
				}

			}
		};
		t.start();

	}

	private void insertStudent() {
		Thread t = new Thread() {
			public void run() {
				StudentDialog sd = new StudentDialog(ZamDirectPRFrame.this,
						loadTypeEd(), loadGroup(), 1, ari);
				sd.setModal(true);
				sd.setVisible(true);
				if (sd.getResult()) {
					Students s = sd.getStudents();
					try {
						ari.insertStudent(s);
						reloadTable();
					} catch (RemoteException e) {
						JOptionPane.showMessageDialog(ZamDirectPRFrame.this,
								e.getMessage());
					}
				}
			}
		};
		t.start();

	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		menuBar1 = new JMenuBar();
		editMenu = new JMenu();
		EditFind = new JMenuItem();
		EditAddStud = new JMenuItem();
		EditEditStud = new JMenuItem();
		EditDelStud = new JMenuItem();
		EditMoveStud = new JMenuItem();
		EditGroup = new JMenuItem();
		EditAddGroup = new JMenuItem();
		EditPatterns = new JMenuItem();
		DocumMenu = new JMenu();
		DocumSvid = new JMenuItem();
		DocumListGroup = new JMenuItem();
		DocumListSvid = new JMenuItem();
		DocumProtocol = new JMenuItem();
		DocumReestr = new JMenuItem();
		DocumPutevList = new JMenuItem();
		CatalogMenu = new JMenu();
		CatalogStud = new JMenuItem();
		CatalogTeacher = new JMenuItem();
		CatalogMasters = new JMenuItem();
		CatalogAuto = new JMenuItem();
		CatalogCategory = new JMenuItem();
		statMenu = new JMenu();
		statMenuItem = new JMenuItem();
		HelpMenu = new JMenu();
		HelpMenuItem = new JMenuItem();
		HelpAbout = new JMenuItem();
		ExitMenu = new JMenu();
		panel1 = new JPanel();
		toolBar1 = new JToolBar();
		addStudentButton = new JButton();
		editStudentButton = new JButton();
		delStudentButton = new JButton();
		findStudButton = new JButton();
		listGroupButton = new JButton();
		addGroupButton = new JButton();
		scheduleButton = new JButton();
		teachersButton = new JButton();
		infoButton = new JButton();
		panel2 = new JPanel();
		statusLabel = new JLabel();
		panel3 = new JPanel();
		scrollPane2 = new JScrollPane();
		tableStudents = new JTable();
		scrollPane1 = new JScrollPane();
		exitButton = new JButton();
		tableStudents.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableStudents.getTableHeader().setFont(
				new Font("Trebuchet MS", Font.BOLD, 14));
		tree1 = new JTree();

		// ======== this ========
		setTitle("\u041a\u043b\u0438\u0435\u043d\u0442\u0441\u043a\u043e\u0435 \u043f\u0440\u0438\u043b\u043e\u0436\u0435\u043d\u0438\u0435 \u0434\u043b\u044f \u0437\u0430\u043c. \u0434\u0438\u0440\u0435\u043a\u0442\u043e\u0440\u0430 \u043f\u043e \u041f\u0420");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		// ======== menuBar1 ========
		{
			menuBar1.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
			menuBar1.setBorder(new EmptyBorder(5, 5, 5, 5));

			// ======== editMenu ========
			{
				editMenu.setText("\u041f\u0440\u0430\u0432\u043a\u0430");

				// ---- EditFind ----
				EditFind.setText("\u041d\u0430\u0439\u0442\u0438 \u0443\u0447\u0435\u043d\u0438\u043a\u0430");
				EditFind.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/Search.png")));
				editMenu.add(EditFind);
				editMenu.addSeparator();

				// ---- EditAddStud ----
				EditAddStud
						.setText("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u043d\u043e\u0432\u043e\u0433\u043e \u0443\u0447\u0435\u043d\u0438\u043a\u0430");
				EditAddStud.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/Add.png")));
				editMenu.add(EditAddStud);

				// ---- EditEditStud ----
				EditEditStud
						.setText("\u0420\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u0437\u0430\u043f\u0438\u0441\u044c \u043e\u0431 \u0443\u0447\u0435\u043d\u0438\u043a\u0435");
				EditEditStud.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/File_Edit.png")));
				editMenu.add(EditEditStud);

				// ---- EditDelStud ----
				EditDelStud
						.setText("\u0423\u0434\u0430\u043b\u0438\u0442\u044c \u0437\u0430\u043f\u0438\u0441\u044c \u043e\u0431 \u0443\u0447\u0435\u043d\u0438\u043a\u0435");
				EditDelStud.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/File_Delete.png")));
				editMenu.add(EditDelStud);

				// ---- EditMoveStud ----
				EditMoveStud
						.setText("\u041f\u0435\u0440\u0435\u0432\u0435\u0441\u0442\u0438 \u0443\u0447\u0435\u043d\u0438\u043a\u0430 \u0432 \u0434\u0440\u0443\u0433\u0443\u044e \u0433\u0440\u0443\u043f\u043f\u0443");
				EditMoveStud.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/Button_Sync.png")));
				editMenu.add(EditMoveStud);
				editMenu.addSeparator();

				// ---- EditGroup ----
				EditGroup.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/Folder.png")));
				EditGroup
						.setText("\u0412\u044b\u0432\u0435\u0441\u0442\u0438 \u0441\u043f\u0438\u0441\u043e\u043a \u0432\u0441\u0435\u0445 \u0433\u0440\u0443\u043f\u043f");
				editMenu.add(EditGroup);

				// ---- EditAddGroup ----
				EditAddGroup
						.setText("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0443\u0447\u0435\u0431\u043d\u0443\u044e \u0433\u0440\u0443\u043f\u043f\u0443");
				EditAddGroup.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/Folder_Add.png")));
				editMenu.add(EditAddGroup);
				editMenu.addSeparator();

				// ---- EditPatterns ----
				EditPatterns
						.setText("\u0428\u0430\u0431\u043b\u043e\u043d\u044b \u0434\u043e\u043a\u0443\u043c\u0435\u043d\u0442\u043e\u0432");
				EditPatterns.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/File_Doc_Text_Word.png")));
				editMenu.add(EditPatterns);
			}
			menuBar1.add(editMenu);

			// ======== DocumMenu ========
			{
				DocumMenu
						.setText("\u0414\u043e\u043a\u0443\u043c\u0435\u043d\u0442\u044b");

				// ---- DocumSvid ----
				DocumSvid
						.setText("\u0421\u0432\u0438\u0434\u0435\u0442\u0435\u043b\u044c\u0441\u0442\u0432\u043e \u043e\u0431 \u043e\u043a\u043e\u043d\u0447\u0430\u043d\u0438\u0438");
				DocumSvid.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/File_List.png")));
				//DocumMenu.add(DocumSvid);
				DocumMenu.addSeparator();

				// ---- DocumListGroup ----
				DocumListGroup
						.setText("\u0421\u043f\u0438\u0441\u043e\u043a \u0443\u0447\u0435\u0431\u043d\u043e\u0439 \u0433\u0440\u0443\u043f\u043f\u044b");
				DocumListGroup.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/Blocknotes.png")));
				DocumMenu.add(DocumListGroup);

				// ---- DocumListSvid ----
				DocumListSvid
						.setText("\u0421\u043f\u0438\u0441\u043e\u043a \u0441\u0432\u0438\u0434\u0435\u0442\u0435\u043b\u044c\u0441\u0442\u0432 \u043e\u0431 \u043e\u043a\u043e\u043d\u0447\u0430\u043d\u0438\u0438 ");
				DocumListSvid.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/Blocknotes_Blue.png")));
				DocumMenu.add(DocumListSvid);
				DocumMenu.addSeparator();

				// ---- DocumProtocol ----
				DocumProtocol
						.setText("\u041f\u0440\u043e\u0442\u043e\u043a\u043e\u043b \u0441\u0434\u0430\u0447\u0438 \u044d\u043a\u0437\u0430\u043c\u0435\u043d\u0430");
				DocumProtocol.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/Bookmark.png")));
				DocumMenu.add(DocumProtocol);

				// ---- DocumReestr ----
				DocumReestr
						.setText("\u0420\u0435\u0435\u0441\u0442\u0440 \u0432\u044b\u0434\u0430\u0447\u0438 \u0441\u0432\u0438\u0434\u0435\u0442\u0435\u043b\u044c\u0441\u0442\u0432 ");
				DocumReestr.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/Archive.png")));
				//DocumMenu.add(DocumReestr);

				// ---- DocumPutevList ----
				DocumPutevList
						.setText("Реестр выданных свидетельств");
				DocumPutevList.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/Calendar_2.png")));
				DocumMenu.add(DocumPutevList);
			}
			menuBar1.add(DocumMenu);

			// ======== CatalogMenu ========
			{
				CatalogMenu
						.setText("\u0421\u043f\u0440\u0430\u0432\u043e\u0447\u043d\u0438\u043a\u0438");

				// ---- CatalogStud ----
				CatalogStud
						.setText("\u0423\u0447\u0435\u043d\u0438\u043a\u0438");
				CatalogStud.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/customers.png")));
				//CatalogMenu.add(CatalogStud);

				// ---- CatalogTeacher ----
				CatalogTeacher
						.setText("\u041f\u0440\u0435\u043f\u043e\u0434\u0430\u0432\u0430\u0442\u0435\u043b\u0438");
				CatalogTeacher.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/address.png")));
				CatalogMenu.add(CatalogTeacher);

				// ---- CatalogMasters ----
				CatalogMasters
						.setText("\u041c\u0430\u0441\u0442\u0435\u0440\u0430 \u043f\u0440\u043e\u0438\u0437\u0432\u043e\u0434\u0441\u0442\u0432\u0435\u043d\u043d\u043e\u0433\u043e \u043e\u0431\u0443\u0447\u0435\u043d\u0438\u044f");
				CatalogMasters.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/User.png")));
				CatalogMenu.add(CatalogMasters);

				// ---- CatalogAuto ----
				CatalogAuto
						.setText("\u0423\u0447\u0435\u0431\u043d\u044b\u0435 \u0430\u0432\u0442\u043e\u043c\u043e\u0431\u0438\u043b\u0438");
				CatalogAuto.setSelected(true);
				CatalogAuto.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/auto_financing_icon.png")));
				CatalogMenu.add(CatalogAuto);

				// ---- CatalogCategory ----
				CatalogCategory
						.setText("\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0438\u0438");
				CatalogCategory.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/Blackboard.png")));
				CatalogMenu.add(CatalogCategory);
			}
			menuBar1.add(CatalogMenu);
			//======== statMenu ========
			{
				statMenu.setText("\u0421\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043a\u0430");

				//---- statMenuItem ----
				statMenuItem.setText("\u0421\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043a\u0430 \u043f\u043e \u0432\u043e\u0436\u0434\u0435\u043d\u0438\u044e");
				statMenuItem.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Chart_Bar_Big.png")));
				statMenu.add(statMenuItem);
			}
			menuBar1.add(statMenu);
			

			// ======== HelpMenu ========
			{
				HelpMenu.setText("\u0421\u043f\u0440\u0430\u0432\u043a\u0430");

				// ---- HelpMenuItem ----
				HelpMenuItem
						.setText("\u0421\u043f\u0440\u0430\u0432\u043a\u0430");
				HelpMenuItem.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/Help.png")));
				HelpMenu.add(HelpMenuItem);

				// ---- HelpAbout ----
				HelpAbout
						.setText("\u041e \u043f\u0440\u043e\u0433\u0440\u0430\u043c\u043c\u0435");
				HelpAbout.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/Info.png")));
				HelpMenu.add(HelpAbout);
			}
			menuBar1.add(HelpMenu);

			// ======== ExitMenu ========
			{
				ExitMenu.setText("\u0412\u044b\u0445\u043e\u0434 ");
				ExitMenu.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/logout.png")));
			}
			//menuBar1.add(ExitMenu);
		}
		setJMenuBar(menuBar1);

		// ======== panel1 ========
		{
			panel1.setLayout(new FlowLayout(FlowLayout.LEFT));

			// ======== toolBar1 ========
			{

				// ---- addStudentButton ----
				addStudentButton.setIcon(new ImageIcon(getClass().getResource(
						"/images/Add.png")));
				addStudentButton
						.setToolTipText("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c \u0443\u0447\u0435\u043d\u0438\u043a\u0430");
				toolBar1.add(addStudentButton);

				// ---- editStudentButton ----
				editStudentButton.setIcon(new ImageIcon(getClass().getResource(
						"/images/Pencil_2.png")));
				editStudentButton
						.setToolTipText("\u0420\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u0437\u0430\u043f\u0438\u0441\u044c \u043e\u0431 \u0443\u0447\u0435\u043d\u0438\u043a\u0435");
				toolBar1.add(editStudentButton);

				// ---- delStudentButton ----
				delStudentButton.setIcon(new ImageIcon(getClass().getResource(
						"/images/Error.png")));
				toolBar1.add(delStudentButton);
				toolBar1.addSeparator();

				// ---- findStudButton ----
				findStudButton.setIcon(new ImageIcon(getClass().getResource(
						"/images/Search.png")));
				toolBar1.add(findStudButton);
				toolBar1.addSeparator();

				// ---- listGroupButton ----
				listGroupButton.setIcon(new ImageIcon(getClass().getResource(
						"/images/Folder.png")));
				toolBar1.add(listGroupButton);

				// ---- addGroupButton ----
				addGroupButton.setIcon(new ImageIcon(getClass().getResource(
						"/images/Folder_Add.png")));
				toolBar1.add(addGroupButton);
				toolBar1.addSeparator();

				// ---- scheduleButton ----
				scheduleButton.setIcon(new ImageIcon(getClass().getResource(
						"/images/Calendar_2.png")));
				scheduleButton
						.setToolTipText("Открыть список свидетельств");
				toolBar1.add(scheduleButton);

				// ---- teachersButton ----
				teachersButton.setIcon(new ImageIcon(getClass().getResource(
						"/images/address.png")));
				toolBar1.add(teachersButton);
				toolBar1.addSeparator();

				// ---- infoButton ----
				infoButton.setIcon(new ImageIcon(getClass().getResource(
						"/images/Help.png")));
				toolBar1.add(infoButton);
				
				exitButton.setIcon(new ImageIcon(getClass().getResource(
						"/images/logout.png")));
				exitButton.setToolTipText("\u0412\u044b\u0445\u043e\u0434");
				toolBar1.add(exitButton);
			}
			panel1.add(toolBar1);
		}
		contentPane.add(panel1, BorderLayout.NORTH);

		// ======== panel2 ========
		{
			panel2.setLayout(new BorderLayout());

			// ---- statusLabel ----
			statusLabel.setText("text");
			//panel2.add(statusLabel, BorderLayout.CENTER);
		}
		contentPane.add(panel2, BorderLayout.SOUTH);

		// ======== panel3 ========
		{
			panel3.setLayout(new BorderLayout());

			// ======== scrollPane2 ========
			{
				scrollPane2.setViewportView(tableStudents);
			}
			panel3.add(scrollPane2, BorderLayout.CENTER);

			// ======== scrollPane1 ========
			{
				scrollPane1.setViewportView(tree1);
			}
			panel3.add(scrollPane1, BorderLayout.WEST);
		}
		contentPane.add(panel3, BorderLayout.CENTER);
		setBounds(100, 100, 1300, 427);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	private JMenuBar menuBar1;
	private JMenu editMenu;
	private JMenuItem EditFind;
	private JMenuItem EditAddStud;
	private JMenuItem EditEditStud;
	private JMenuItem EditDelStud;
	private JMenuItem EditMoveStud;
	private JMenuItem EditGroup;
	private JMenuItem EditAddGroup;
	private JMenuItem EditPatterns;
	private JMenu DocumMenu;
	private JMenuItem DocumSvid;
	private JMenuItem DocumListGroup;
	private JMenuItem DocumListSvid;
	private JMenuItem DocumProtocol;
	private JMenuItem DocumReestr;
	private JMenuItem DocumPutevList;
	private JMenu CatalogMenu;
	private JMenuItem CatalogStud;
	private JMenuItem CatalogTeacher;
	private JMenuItem CatalogMasters;
	private JMenuItem CatalogAuto;
	private JMenuItem CatalogCategory;
	private JMenu statMenu;
	private JMenuItem statMenuItem;
	private JMenu HelpMenu;
	private JMenuItem HelpMenuItem;
	private JMenuItem HelpAbout;
	private JMenu ExitMenu;
	private JPanel panel1;
	private JToolBar toolBar1;
	private JButton addStudentButton;
	private JButton editStudentButton;
	private JButton delStudentButton;
	private JButton findStudButton;
	private JButton listGroupButton;
	private JButton addGroupButton;
	private JButton scheduleButton;
	private JButton teachersButton;
	private JButton infoButton;
	private JPanel panel2;
	private JLabel statusLabel;
	private JPanel panel3;
	private JScrollPane scrollPane2;
	private JTable tableStudents;
	private JScrollPane scrollPane1;
	private JTree tree1;
	private JButton exitButton;
	// JFormDesigner - End of variables declaration //GEN-END:variables

}

/*
 * Created by JFormDesigner on Sat Dec 07 13:34:30 GMT+05:00 2013
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
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

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.jfree.ui.RefineryUtilities;

import beans.Categories;
import beans.Educationtypes;
import beans.Groups;
import beans.Students;
import beans.TypeEducation;
import beans.YearOfEducation;

import rmi.AutoschoolRmiInterface;
import tablemodels.StudentTableModel;
import util.ExcelJob;
import util.WordUtil;

/**
 * @author ????????
 */
public class ZamDirectURFrame extends JFrame implements ActionListener {
	/**
	 * 
	 */

	private static final long serialVersionUID = 4896732680442936124L;
	private AutoschoolRmiInterface ari;
	private static final String ADD_STUDENT = "addStudent";
	private static final String UPDATE_ST = "updateStudent";
	private static final String DEL_ST = "delStudent";
	private static final String MOV_ST = "moveStudent";

	public ZamDirectURFrame(AutoschoolRmiInterface ar) {
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
		ListGroupButton.setName("listGroup");
		EditListGroup.setName("listGroup");
		addGroupButton.setName("addGroup");
		EditAddGroup.setName("addGroup");
		EditFind.setName("searchStud");
		findStudButton.setName("searchStud");
		CatalogTeacher.setName("listTeachers");
		teachersButton.setName("listTeachers");
		CatalogMasters.setName("listMasters");
		CatalogAuto.setName("listAvto");
		CatalogSubject.setName("listSubjects");
		CatalogThemes.setName("listThemes");
		DocumRaspisanie.setName("listRasp");
		DocumPricaz.setName("Pricaz");
		DocumListGroup.setName("SpisokGrup");
		EditPatterns.setName("OpenCatalog");
		HelpMenuItem.setName("help");
		infoButton.setName("help");
		scheduleButton.setName("listRasp");
		exitButton.setName("exit");
		addStudentButton.addActionListener(this);
		EditAddStud.addActionListener(this);
		editStudentButton.addActionListener(this);
		EditEditStud.addActionListener(this);
		delStudentButton.addActionListener(this);
		EditDelStud.addActionListener(this);
		EditMoveStud.addActionListener(this);
		ListGroupButton.addActionListener(this);
		addGroupButton.addActionListener(this);
		EditAddGroup.addActionListener(this);
		EditListGroup.addActionListener(this);
		EditFind.addActionListener(this);
		findStudButton.addActionListener(this);
		CatalogTeacher.addActionListener(this);
		teachersButton.addActionListener(this);
		CatalogMasters.addActionListener(this);
		CatalogAuto.addActionListener(this);
		CatalogSubject.addActionListener(this);
		CatalogThemes.addActionListener(this);
		DocumRaspisanie.addActionListener(this);
		DocumPricaz.addActionListener(this);
		DocumListGroup.addActionListener(this);
		EditPatterns.addActionListener(this);
		HelpMenuItem.addActionListener(this);
		infoButton.addActionListener(this);
		scheduleButton.addActionListener(this);
		exitButton.addActionListener(this);
		statMenuItem.addActionListener(this);
		statMenuItem.setName("stat");
	}

	private void dbConnect(AutoschoolRmiInterface ar) {

		this.ari = ar;
	}

	private DefaultMutableTreeNode processHierarchy() {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(
				"������� ������     ");
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

	private void updateProgress(final List<Students> stud) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (stud != null) {
					table1.setModel(new StudentTableModel(stud));
					table1.getColumnModel().getColumn(0).setPreferredWidth(135);
					table1.getColumnModel().getColumn(2).setPreferredWidth(150);
					table1.getColumnModel().getColumn(4).setPreferredWidth(122);
					table1.getColumnModel().getColumn(5).setPreferredWidth(122);
					table1.getColumnModel().getColumn(6).setPreferredWidth(157);
					table1.getColumnModel().getColumn(7).setPreferredWidth(150);
					table1.getColumnModel().getColumn(8).setPreferredWidth(115);
					table1.getColumnModel().getColumn(9).setPreferredWidth(136);
					table1.getColumnModel().getColumn(10)
							.setPreferredWidth(134);
					table1.getColumnModel().getColumn(11)
							.setPreferredWidth(116);
					table1.getColumnModel().getColumn(12)
							.setPreferredWidth(114);
					table1.getColumnModel().getColumn(13)
							.setPreferredWidth(107);
					table1.getColumnModel().getColumn(14)
							.setPreferredWidth(215);
					table1.getColumnModel().getColumn(15)
							.setPreferredWidth(130);
					table1.getColumnModel().getColumn(17)
							.setPreferredWidth(175);
					table1.getColumnModel().getColumn(18)
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
			if (c.getName().equals("listSubjects")) {
				listSubjects();
			}
			if (c.getName().equals("listThemes")) {
				listThemes();
			}
			if (c.getName().equals("listRasp")) {
				listRasp();
			}
			if (c.getName().equals("Pricaz")) {
				printPricaz();
			}
			if (c.getName().equals("SpisokGrup")) {
				printSpisokGrup();
			}
			if (c.getName().equals("stat")) {
				stat();
			}
			
			if (c.getName().equals("exit")) {
				AuthFrame inst;
				inst = new AuthFrame(ari);
				inst.setLocationRelativeTo(null);
				inst.setVisible(true);
				this.dispose();
				
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
			

		}
	}


	private void stat() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree1
				.getLastSelectedPathComponent();

		if (node != null) {
			Object nodeInfo = node.getUserObject();
			if (nodeInfo instanceof Groups) {
				Groups group = (Groups) nodeInfo;
				List<Students> lst = null;
				try {
					lst = ari.getStudentsFromGroup2(group.getGroupid());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				StudentBallDialog slf = new StudentBallDialog(ZamDirectURFrame.this,
						ari, lst);
				slf.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(ZamDirectURFrame.this,
						"���������� �������� ������ � ������ ������� �����!",
						"��������", 2);
			}
		} else {
			JOptionPane.showMessageDialog(ZamDirectURFrame.this,
					"���������� �������� ������ � ������ ������� �����!",
					"��������", 2);
		}
		
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
				JOptionPane.showMessageDialog(ZamDirectURFrame.this,
						"���������� �������� ������ � ������ ������� �����!",
						"��������", 2);
			}
		} else {
			JOptionPane.showMessageDialog(ZamDirectURFrame.this,
					"���������� �������� ������ � ������ ������� �����!",
					"��������", 2);
		}

	}

	private void printPricaz() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree1
				.getLastSelectedPathComponent();

		if (node != null) {
			Object nodeInfo = node.getUserObject();
			if (nodeInfo instanceof Groups) {
				Groups group = (Groups) nodeInfo;
				List<Students> stud = null;
				YearOfEducation year = group.getYearOfEducation();
				YearOfEducation year2 = null;
				try {
					year2 = ari.getYearById(year.getYearOfEducationId());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (Integer.parseInt(year2.getNumberOfYear()) < 2013) {
					JOptionPane.showMessageDialog(ZamDirectURFrame.this,
							"������ ��������� ��������!", "��������", 2);
				} else {
					try {
						stud = ari.getStudentsFromGroup(group.getGroupid());
					} catch (RemoteException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ExcelJob.exelSet2(stud, group);
				}
			} else {
				JOptionPane.showMessageDialog(ZamDirectURFrame.this,
						"���������� �������� ������ � ������ ������� �����!",
						"��������", 2);
			}
		} else {
			JOptionPane.showMessageDialog(ZamDirectURFrame.this,
					"���������� �������� ������ � ������ ������� �����!",
					"��������", 2);
		}

	}

	private void listRasp() {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree1
				.getLastSelectedPathComponent();

		if (node != null) {
			Object nodeInfo = node.getUserObject();
			if (nodeInfo instanceof Groups) {
				Groups group = (Groups) nodeInfo;
				DriveDateDialog rlf = new DriveDateDialog(null, ZamDirectURFrame.this,
						ari, group,2);
				rlf.setVisible(true);
			} else {
				JOptionPane.showMessageDialog(ZamDirectURFrame.this,
						"���������� �������� ������ � ������ ������� �����!",
						"��������", 2);
			}
		} else {
			JOptionPane.showMessageDialog(ZamDirectURFrame.this,
					"���������� �������� ������ � ������ ������� �����!",
					"��������", 2);
		}

	}

	private void listThemes() {
		ThemesListFrame tlf = new ThemesListFrame(ZamDirectURFrame.this, ari);
		tlf.setVisible(true);

	}

	private void listSubjects() {
		subjectListFrame slf = new subjectListFrame(ZamDirectURFrame.this, ari);
		slf.setVisible(true);
	}

	private void listAvto() {
		AvtoListFrame td = new AvtoListFrame(ZamDirectURFrame.this, ari);
		td.setVisible(true);

	}

	private void listMasters() {
		teacherListFrame td = new teacherListFrame(ZamDirectURFrame.this, ari,
				2);
		td.setVisible(true);

	}

	private void listTeachers() {
		teacherListFrame td = new teacherListFrame(ZamDirectURFrame.this, ari,
				1);
		td.setVisible(true);

	}

	private void searchStud() {
		Thread t = new Thread() {
			public void run() {
				findStudDialog gd = new findStudDialog(ZamDirectURFrame.this);
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
									ZamDirectURFrame.this, e.getMessage());
						}
					}
					if (i == 1) {
						try {
							String s = gd.getNomSvid();
							List<Students> lst = ari.searchStudByNomSvid(s);
							updateProgress(lst);
						} catch (RemoteException e) {
							JOptionPane.showMessageDialog(
									ZamDirectURFrame.this, e.getMessage());
						}
					}
					if (i == 2) {
						try {
							Date d = gd.getDate();
							List<Students> lst = ari.searchStudByDate(d);
							updateProgress(lst);
						} catch (RemoteException e) {
							JOptionPane.showMessageDialog(
									ZamDirectURFrame.this, e.getMessage());
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
				GroupDialog gd = new GroupDialog(ZamDirectURFrame.this,
						loadCategories(), loadEducationtypes(), loadYearEd());
				gd.setModal(true);
				gd.setVisible(true);
				if (gd.getResult()) {
					Groups g = gd.getGrup();
					try {
						ari.insertGrup(g);
						pop_tree();
					} catch (RemoteException e) {
						JOptionPane.showMessageDialog(ZamDirectURFrame.this,
								e.getMessage());
					}
				}
			}
		};
		t.start();

	}

	private void listGroup() {
		groupListFrame sd = new groupListFrame(2, ZamDirectURFrame.this, ari);
		sd.setVisible(true);

	}

	private void movStudent() {
		Thread t = new Thread() {
			public void run() {
				StudentTableModel stm = (StudentTableModel) table1.getModel();
				// ��������� - ������� �� ���� �����-������ �������
				if (table1.getSelectedRow() >= 0) {
					Students s = stm.getStudent(table1.getSelectedRow());
					moveToGroupDialog sd = new moveToGroupDialog(
							ZamDirectURFrame.this, loadYearEd(), ari);
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
									ZamDirectURFrame.this, e.getMessage());
						}
					}
				} // ���� ������� �� ������� - �������� ������������, ��� ���
					// ����������
				else {
					JOptionPane.showMessageDialog(ZamDirectURFrame.this,
							"���������� �������� ��������� � �������!",
							"��������", 2);
				}

			}
		};
		t.start();

	}

	private void deleteStudent() {
		Thread t = new Thread() {
			public void run() {
				StudentTableModel stm = (StudentTableModel) table1.getModel();
				// ��������� - ������� �� ���� �����-������ �������
				if (table1.getSelectedRow() >= 0) {
					if (JOptionPane.showOptionDialog(ZamDirectURFrame.this,
							"�� ������ ������� ������?", "�������� ������",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.ERROR_MESSAGE, null, new Object[] {
									"��", "���" }, "��") == JOptionPane.YES_OPTION) {
						Students s = stm.getStudent(table1.getSelectedRow());
						try {
							ari.deleteBet(s);
							reloadTable();
						} catch (RemoteException e) {
							JOptionPane.showMessageDialog(
									ZamDirectURFrame.this, e.getMessage());
						}
					}
				} // ���� ������� �� ������� - �������� ������������, ��� ���
					// ����������
				else {
					JOptionPane.showMessageDialog(ZamDirectURFrame.this,
							"���������� �������� ��������� � �������!",
							"��������", 2);
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
				StudentTableModel stm = (StudentTableModel) table1.getModel();
				// ��������� - ������� �� ���� �����-������ �������
				if (table1.getSelectedRow() >= 0) {
					Students s = stm.getStudent(table1.getSelectedRow());
					StudentDialog sd = new StudentDialog(ZamDirectURFrame.this,
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
									ZamDirectURFrame.this, e.getMessage());
						}
					}
				} // ���� ������� �� ������� - �������� ������������, ��� ���
					// ����������
				else {
					JOptionPane.showMessageDialog(ZamDirectURFrame.this,
							"���������� �������� ��������� � �������!",
							"��������", 2);
				}

			}
		};
		t.start();

	}

	private void insertStudent() {
		Thread t = new Thread() {
			public void run() {
				StudentDialog sd = new StudentDialog(ZamDirectURFrame.this,
						loadTypeEd(), loadGroup(), 1, ari);
				sd.setModal(true);
				sd.setVisible(true);
				if (sd.getResult()) {
					Students s = sd.getStudents();
					try {
						ari.insertStudent(s);
						reloadTable();
					} catch (RemoteException e) {
						JOptionPane.showMessageDialog(ZamDirectURFrame.this,
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
		EditListGroup = new JMenuItem();
		EditAddGroup = new JMenuItem();
		EditPatterns = new JMenuItem();
		DocumMenu = new JMenu();
		DocumPricaz = new JMenuItem();
		DocumListGroup = new JMenuItem();
		DocumListStud = new JMenuItem();
		DocumRaspisanie = new JMenuItem();
		DocumReestr = new JMenuItem();
		CatalogMenu = new JMenu();
		CatalogTeacher = new JMenuItem();
		CatalogMasters = new JMenuItem();
		CatalogAuto = new JMenuItem();
		CatalogSubject = new JMenuItem();
		CatalogThemes = new JMenuItem();
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
		ListGroupButton = new JButton();
		addGroupButton = new JButton();
		scheduleButton = new JButton();
		teachersButton = new JButton();
		exitButton = new JButton();
		infoButton = new JButton();
		panel2 = new JPanel();
		panel3 = new JPanel();
		scrollPane2 = new JScrollPane();
		table1 = new JTable();
		statMenu = new JMenu();
		statMenuItem = new JMenuItem();
		table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table1.getTableHeader()
				.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
		scrollPane1 = new JScrollPane();
		tree1 = new JTree();

		// ======== this ========
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		setTitle("���������� ���������� ��� ���. ��������� �� ��");
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

				// ---- EditListGroup ----
				EditListGroup
						.setText("\u0421\u043f\u0438\u0441\u043e\u043a \u0443\u0447\u0435\u0431\u043d\u044b\u0445 \u0433\u0440\u0443\u043f\u043f");
				EditListGroup.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/Folder.png")));
				editMenu.add(EditListGroup);

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

				// ---- DocumPricaz ----
				DocumPricaz
						.setText("\u041f\u0440\u0438\u043a\u0430\u0437 \u043e \u0444\u043e\u0440\u043c\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u0438 \u0443\u0447\u0435\u0431\u043d\u043e\u0439 \u0433\u0440\u0443\u043f\u043f\u044b");
				DocumPricaz.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/File_List.png")));
				DocumMenu.add(DocumPricaz);
				DocumMenu.addSeparator();

				// ---- DocumListGroup ----
				DocumListGroup
						.setText("\u0421\u043f\u0438\u0441\u043e\u043a \u0443\u0447\u0435\u0431\u043d\u043e\u0439 \u0433\u0440\u0443\u043f\u043f\u044b");
				DocumListGroup.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/Blocknotes.png")));
				DocumMenu.add(DocumListGroup);

				// ---- DocumListStud ----
				DocumListStud
						.setText("����������");
				DocumListStud.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/Blocknotes_Blue.png")));
				//DocumMenu.add(DocumListStud);
				DocumMenu.addSeparator();

				// ---- DocumRaspisanie ----
				DocumRaspisanie
						.setText("\u0420\u0430\u0441\u043f\u0438\u0441\u0430\u043d\u0438\u0435 \u0437\u0430\u043d\u044f\u0442\u0438\u0439 \u043d\u0430 \u0443\u0447\u0435\u0431\u043d\u0443\u044e \u0433\u0440\u0443\u043f\u043f\u0443");
				DocumRaspisanie.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/Calendar.png")));
				DocumMenu.add(DocumRaspisanie);

				// ---- DocumReestr ----
				DocumReestr
						.setText("\u0420\u0435\u0435\u0441\u0442\u0440 \u0432\u044b\u0434\u0430\u0447\u0438 \u0441\u0432\u0438\u0434\u0435\u0442\u0435\u043b\u044c\u0441\u0442\u0432 ");
				DocumReestr.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/Archive.png")));
				DocumMenu.add(DocumReestr);
			}
			menuBar1.add(DocumMenu);

			// ======== CatalogMenu ========
			{
				CatalogMenu
						.setText("\u0421\u043f\u0440\u0430\u0432\u043e\u0447\u043d\u0438\u043a\u0438");

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

				// ---- CatalogSubject ----
				CatalogSubject
						.setText("\u041f\u0440\u0435\u0434\u043c\u0435\u0442\u044b");
				CatalogSubject.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/Blackboard.png")));
				CatalogMenu.add(CatalogSubject);

				// ---- CatalogThemes ----
				CatalogThemes.setText("\u0422\u0435\u043c\u044b");
				CatalogThemes.setIcon(new ImageIcon(getClass().getResource(
						"/images/16x16/archives.png")));
				CatalogMenu.add(CatalogThemes);
			}
			menuBar1.add(CatalogMenu);
			//======== statMenu ========
			{
				statMenu.setText("\u0421\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043a\u0430");

				//---- statMenuItem ----
				statMenuItem.setText("���������� �� �������������");
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

				// ---- findStudButton ----
				findStudButton.setIcon(new ImageIcon(getClass().getResource(
						"/images/Search.png")));
				toolBar1.add(findStudButton);
				toolBar1.addSeparator();

				// ---- ListGroupButton ----
				ListGroupButton.setIcon(new ImageIcon(getClass().getResource(
						"/images/Folder.png")));
				toolBar1.add(ListGroupButton);

				// ---- addGroupButton ----
				addGroupButton.setIcon(new ImageIcon(getClass().getResource(
						"/images/Folder_Add.png")));
				toolBar1.add(addGroupButton);
				toolBar1.addSeparator();

				// ---- scheduleButton ----
				scheduleButton.setIcon(new ImageIcon(getClass().getResource(
						"/images/Calendar_2.png")));
				scheduleButton
						.setToolTipText("\u0420\u0430\u0441\u043f\u0438\u0441\u0430\u043d\u0438\u0435 \u0437\u0430\u043d\u044f\u0442\u0438\u0439 \u043d\u0430 \u0443\u0447\u0435\u0431\u043d\u0443\u044e \u0433\u0440\u0443\u043f\u043f\u0443");
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
				toolBar1.add(exitButton);
			}
			panel1.add(toolBar1);
		}
		contentPane.add(panel1, BorderLayout.NORTH);

		// ======== panel2 ========
		{
			panel2.setLayout(new BorderLayout());
		}
		contentPane.add(panel2, BorderLayout.SOUTH);

		// ======== panel3 ========
		{
			panel3.setLayout(new BorderLayout());

			// ======== scrollPane2 ========
			{
				scrollPane2.setViewportView(table1);
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
	private JMenuItem EditListGroup;
	private JMenuItem EditAddGroup;
	private JMenuItem EditPatterns;
	private JMenu DocumMenu;
	private JMenuItem DocumPricaz;
	private JMenuItem DocumListGroup;
	private JMenuItem DocumListStud;
	private JMenuItem DocumRaspisanie;
	private JMenuItem DocumReestr;
	private JMenu CatalogMenu;
	private JMenuItem CatalogTeacher;
	private JMenuItem CatalogMasters;
	private JMenuItem CatalogAuto;
	private JMenuItem CatalogSubject;
	private JMenuItem CatalogThemes;
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
	private JButton ListGroupButton;
	private JButton addGroupButton;
	private JButton scheduleButton;
	private JButton teachersButton;
	private JButton infoButton;
	private JButton exitButton;
	private JPanel panel2;
	private JPanel panel3;
	private JScrollPane scrollPane2;
	private JTable table1;
	private JScrollPane scrollPane1;
	private JTree tree1;
	private JMenu statMenu;
	private JMenuItem statMenuItem;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}

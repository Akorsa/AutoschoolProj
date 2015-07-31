/*
 * Created by JFormDesigner on Sat Dec 28 23:46:23 GMT+05:00 2013
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rmi.AutoschoolRmiInterface;
import beans.Students;
import beans.Subjects;
import beans.Themes;
import beans.Users;

import com.jgoodies.forms.factories.*;

/**
 * @author ????????
 */
public class StudAuthFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3171989387753045165L;
	private static final transient Logger log = LoggerFactory.getLogger(StudAuthFrame.class);
	private AutoschoolRmiInterface ari;
	private Users usr;
	public StudAuthFrame(AutoschoolRmiInterface ar,Users user) {
		super();
		dbConnect(ar);
		this.usr = user;
		initComponents(loadSubjects());
		if(radioButton1.isSelected()){
			subjectBox.setEnabled(false);
			themesBox.setEnabled(false);
		} else {
			subjectBox.setEnabled(true);
			themesBox.setEnabled(true);
		}
		radioButton1.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	if(radioButton1.isSelected()){
					subjectBox.setEnabled(false);
					themesBox.setEnabled(false);
				} 
		    }});
		radioButton2.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	if(radioButton2.isSelected()){
		    		subjectBox.setEnabled(true);
					themesBox.setEnabled(true);
				} 
		    }});
		 
		
		try {
			loadThemes();
		} catch (RemoteException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		saveButton.addActionListener(this);
		saveButton.setName("OK");
		cancelButton.addActionListener(this);
		cancelButton.setName("Cancel");
		subjectBox.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	try {
					loadThemes();
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
		    }});
	}
	
	private void dbConnect(AutoschoolRmiInterface ar)
	{
		
		this.ari = ar;
	}
	
	protected Students loadStudents(int id) {
		Students st = null;
		try {
			st = ari.getStudentByUser(id);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(st!=null){
			return st;
		} else {
			return null;
		}
		
	}
	
 	protected List<Subjects> loadSubjects() {
		List<Subjects> stud = null;
		//List<Questions> qst = ari.getQuestionsByTheme(id);
		try {
			stud = (List<Subjects>) ari.getSubjectByName("Правила дорожного движения");
			if( stud.isEmpty()){ System.out.println("Fucking shit goup");}
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
		
	}
	
	private void loadThemes() throws RemoteException {
		Subjects grup = (Subjects)subjectBox.getSelectedItem();
		List<Themes> students = ari.getThemeListBySubj(grup.getSubjectId());
		if( students.isEmpty()){ System.out.println("Fucking shit");}
		themesBox.removeAllItems();
		for (Themes gr:students){
			themesBox.addItem(gr);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
        // Добавляем ставку, но не закрываем окно
		if (src.getName().equals("OK")) {
	        if (subjectBox.getSelectedItem() == null) {
	        	JOptionPane.showMessageDialog(this, "Выберите себя из списка!", "Внимание", 2);	
	        	
	        }else {
	        	Students stud = null;
	        	if(loadStudents(usr.getUserid())!=null){
	        		stud = loadStudents(usr.getUserid());
	        	}
	        	if(radioButton1.isSelected()){
	        		TestFrame sf = new TestFrame(stud,ari,null,1);
		        	sf.setVisible(true);
		        	this.dispose();
	        	}
	        	if(radioButton2.isSelected()){
	        		TestFrame sf = new TestFrame(stud,ari,(Themes)themesBox.getSelectedItem(),2);
	        		if((Themes)themesBox.getSelectedItem()==null){
	        		JOptionPane.showMessageDialog(this, "Fuck!", "Внимание", 2);	}
		        	sf.setVisible(true);
		        	this.dispose();
	        	}
	        	
	        }
		}
	    if (src.getName().equals("Cancel")) {
	        this.dispose();
	    }
	  
		
	}

	

	private void initComponents(List<Subjects> list) {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel1 = new JPanel();
		panel3 = new JPanel();
		radioButton1 = new JRadioButton();
		panel4 = new JPanel();
		radioButton2 = new JRadioButton();
		panel5 = new JPanel();
		yearLabel = new JLabel();
		subjectBox = new JComboBox(new Vector<Subjects>(list));
		panel6 = new JPanel();
		yearLabel2 = new JLabel();
		themesBox = new JComboBox();
		panel2 = new JPanel();
		panel13 = new JPanel();
		saveButton = new JButton();
		cancelButton = new JButton();
		ButtonGroup bG = new ButtonGroup();
		radioButton1.setSelected(true);
		bG.add(radioButton1);
		bG.add(radioButton2);


		//======== this ========
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("\u041d\u0430\u0447\u0430\u0442\u044c \u0442\u0435\u0441\u0442\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u0435");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== panel1 ========
		{
			panel1.setBorder(new CompoundBorder(
				new TitledBorder(" "),
				Borders.DLU2_BORDER));
			panel1.setPreferredSize(new Dimension(605, 120));
			panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

			//======== panel3 ========
			{
				panel3.setPreferredSize(new Dimension(480, 33));
				panel3.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 5));

				//---- radioButton1 ----
				radioButton1.setText("\u041f\u043e \u0432\u0441\u0435\u043c \u0442\u0435\u043c\u0430\u043c");
				panel3.add(radioButton1);

				//======== panel4 ========
				{
					panel4.setLayout(new FlowLayout());
				}
				panel3.add(panel4);
			}
			panel1.add(panel3);

			//---- radioButton2 ----
			radioButton2.setText("\u041f\u043e \u0442\u0435\u043c\u0435");
			panel1.add(radioButton2);

			//======== panel5 ========
			{
				panel5.setLayout(new FlowLayout());

				//---- yearLabel ----
				yearLabel.setText("\u041f\u0440\u0435\u0434\u043c\u0435\u0442");
				yearLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel5.add(yearLabel);

				//---- subjectBox ----
				subjectBox.setPreferredSize(new Dimension(140, 20));
				panel5.add(subjectBox);
			}
			panel1.add(panel5);

			//======== panel6 ========
			{
				panel6.setLayout(new FlowLayout());

				//---- yearLabel2 ----
				yearLabel2.setText("\u0422\u0435\u043c\u0430");
				yearLabel2.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel6.add(yearLabel2);

				//---- themesBox ----
				themesBox.setPreferredSize(new Dimension(140, 20));
				panel6.add(themesBox);
			}
			panel1.add(panel6);
		}
		contentPane.add(panel1, BorderLayout.NORTH);

		//======== panel2 ========
		{
			panel2.setPreferredSize(new Dimension(1733, 50));
			panel2.setLayout(new FlowLayout());

			//======== panel13 ========
			{
				panel13.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 5));

				//---- saveButton ----
				saveButton.setSelectedIcon(new ImageIcon(getClass().getResource("/images/Nnm 16/check.png")));
				saveButton.setIcon(new ImageIcon(getClass().getResource("/images/check.png")));
				saveButton.setText("\u041d\u0430\u0447\u0430\u0442\u044c");
				panel13.add(saveButton);

				//---- cancelButton ----
				cancelButton.setText("\u041e\u0442\u043c\u0435\u043d\u0430");
				cancelButton.setIcon(new ImageIcon(getClass().getResource("/images/logout.png")));
				panel13.add(cancelButton);
			}
			panel2.add(panel13);
		}
		contentPane.add(panel2, BorderLayout.CENTER);
		setSize(570, 235);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel1;
	private JPanel panel3;
	private JRadioButton radioButton1;
	private JPanel panel4;
	private JRadioButton radioButton2;
	private JPanel panel5;
	private JLabel yearLabel;
	private JComboBox subjectBox;
	private JPanel panel6;
	private JLabel yearLabel2;
	private JComboBox themesBox;
	private JPanel panel2;
	private JPanel panel13;
	private JButton saveButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

/*
 * Created by JFormDesigner on Wed Jan 08 15:31:35 GMT+05:00 2014
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;

import rmi.AutoschoolRmiInterface;


import beans.Groups;
import beans.LinkTeacherSubject;
import beans.Schedules;
import beans.Subjects;
import beans.Teachers;
import beans.Themes;

import com.jgoodies.forms.factories.*;

/**
 * @author ????????
 */
public class RaspDialog extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7022820420980814172L;
	private boolean result = false;
	private int raspId = 0;
	private AutoschoolRmiInterface ari; 
	
	public RaspDialog(Frame owner, AutoschoolRmiInterface ari) {
		super(owner);
		this.ari = ari;
		
		
		initComponents(loadGroups(),loadTeachers());
		spinner2.setVisible(false);
		prepodBox.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	loadSubjectsBox();
		    	//loadThemesBox();
		    }});
		
		loadSubjectsBox();
		loadThemesBox();
		subjectBox.addItemListener(new ItemListener() {
		
			@Override
			public void itemStateChanged(ItemEvent evt) {
				 if(evt.getStateChange() == java.awt.event.ItemEvent.SELECTED){
				loadThemesBox();}
				
			}});
		saveButton.addActionListener(this);
		saveButton.setName("OK");
		cancelButton.addActionListener(this);
		cancelButton.setName("Cancel");
		
	}
	
	protected void loadSubjectsBox() {
		Teachers teacher = (Teachers)prepodBox.getSelectedItem();
		List<LinkTeacherSubject> grup = loadLinkTeacherSubject(teacher);
		if( grup.isEmpty()){ System.out.println("Fucking shit");}
		subjectBox.removeAllItems();
		for (LinkTeacherSubject gr:grup){
			subjectBox.addItem(gr.getSubjects());
		}
		
	}

	private List<LinkTeacherSubject> loadLinkTeacherSubject(Teachers teacher) {
		List<LinkTeacherSubject> stud = null;
		try {
			stud = (List<LinkTeacherSubject>) ari.getZakrByTeacher(teacher);
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
	}

	public void setRasp(Schedules g) {
		raspId = g.getScheduleId();
		spinner1.getModel().setValue(g.getBeginTime());
		spinner2.getModel().setValue(g.getEndTime());
		
		for (int i = 0; i < grupBox.getModel().getSize(); i++) {
			Groups y = (Groups) grupBox.getModel().getElementAt(i);
            if (y.getGroupid()== g.getGroups().getGroupid()) {
            	grupBox.setSelectedIndex(i);
                break;
            }
        }
		for (int i = 0; i < prepodBox.getModel().getSize(); i++) {
			Teachers cat = (Teachers) prepodBox.getModel().getElementAt(i);
            if (cat.getPrepodid()== g.getTeachers().getPrepodid()) {
            	prepodBox.setSelectedIndex(i);
                break;
            }
        }
		for (int i = 0; i < themeBox.getModel().getSize(); i++) {
			Themes cat = (Themes) themeBox.getModel().getElementAt(i);
            if (cat.getThemeId()== g.getThemes().getThemeId()) {
            	themeBox.setSelectedIndex(i);
                break;
            }
        }	
	}
	
	public Schedules getRasp() {
		Schedules st = new Schedules();
		st.setScheduleId(raspId);
		st.setGroups((Groups)grupBox.getSelectedItem());
		st.setTeachers((Teachers)prepodBox.getSelectedItem());
		Themes t1 = (Themes)themeBox.getSelectedItem();
		st.setThemes(t1);
		Themes t = null;
		try {
			t = ari.getThemeById(t1.getThemeId());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date dt1 = ((SpinnerDateModel)spinner1.getModel()).getDate();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(dt1); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY, t.getNumberOfHours()); // adds one hour
	    Date dt2 = cal.getTime();
		//Date dt2 = ((SpinnerDateModel)spinner2.getModel()).getDate();
		st.setBeginTime(dt1);
		st.setEndTime(dt2);
        return st;
    }
	
	public boolean getResult() {
        return result;
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
        // Добавляем ставку, но не закрываем окно
		if (src.getName().equals("OK")) {
	      
	        	result = true;
	        	dispose();
	        }
	    if (src.getName().equals("Cancel")) {
	        result = false;
	        dispose();
	    }
		
	}
	
	private List<Themes> loadThemes(int id) {
		List<Themes> stud = null;
		try {
			stud = (List<Themes>) ari.getThemeListBySubj(id);
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
		
	}
	
	private void loadThemesBox()  {
		Subjects year = (Subjects)subjectBox.getSelectedItem();
		List<Themes> grup = loadThemes(year.getSubjectId());
		if( grup.isEmpty()){themeBox.removeAllItems();}
		else {
		themeBox.removeAllItems();
		for (Themes gr:grup){
			themeBox.addItem(gr);
		}}
	}
	
	private List<Groups> loadGroups() {
		List<Groups> stud = null;
		try {
			stud = (List<Groups>) ari.getGroupList();
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
		
	}
	
	private List<Teachers> loadTeachers() {
		List<Teachers> stud = null;
		try {
			stud = (List<Teachers>) ari.getTeacherList();
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
		
	}
	


	private void initComponents(List<Groups> list2, List<Teachers> list3) {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel13 = new JPanel();
		saveButton = new JButton();
		cancelButton = new JButton();
		panel1 = new JPanel();
		panel3 = new JPanel();
		nameLabel = new JLabel();
		grupBox = new JComboBox(new Vector<Groups>(list2));
		formEdLab = new JLabel();
		prepodBox = new JComboBox(new Vector<Teachers>(list3));
		panel7 = new JPanel();
		label1 = new JLabel();
		subjectBox = new JComboBox();
		yearLabel = new JLabel();
		themeBox = new JComboBox();
		panel2 = new JPanel();
		panel5 = new JPanel();
		birthdateLabel = new JLabel();
		panel4 = new JPanel();
		spinner1 = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH));
		panel6 = new JPanel();
		label11 = new JLabel();
		spinner2 = new JSpinner();

		//======== this ========
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c/\u0440\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u0437\u0430\u043f\u0438\u0441\u044c");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== panel13 ========
		{
			panel13.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 5));

			//---- saveButton ----
			saveButton.setSelectedIcon(new ImageIcon(getClass().getResource("/images/Nnm 16/check.png")));
			saveButton.setIcon(new ImageIcon(getClass().getResource("/images/Nnm 16/check.png")));
			saveButton.setText("\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c");
			saveButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
			panel13.add(saveButton);

			//---- cancelButton ----
			cancelButton.setText("\u041e\u0442\u043c\u0435\u043d\u0430");
			cancelButton.setIcon(new ImageIcon(getClass().getResource("/images/Nnm 16/logout.png")));
			cancelButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
			panel13.add(cancelButton);
		}
		contentPane.add(panel13, BorderLayout.SOUTH);

		//======== panel1 ========
		{
			panel1.setBorder(new CompoundBorder(
				new TitledBorder(" "),
				Borders.DLU2_BORDER));
			panel1.setPreferredSize(new Dimension(605, 120));
			panel1.setLayout(new FlowLayout());

			//======== panel3 ========
			{
				panel3.setPreferredSize(new Dimension(500, 80));
				panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

				//---- nameLabel ----
				nameLabel.setText("\u0413\u0440\u0443\u043f\u043f\u0430*");
				nameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel3.add(nameLabel);

				//---- grupBox ----
				grupBox.setPreferredSize(new Dimension(90, 20));
				grupBox.setMaximumRowCount(10);
				panel3.add(grupBox);

				//---- formEdLab ----
				formEdLab.setText("\u041f\u0440\u0435\u043f\u043e\u0434\u0430\u0432\u0430\u0442\u0435\u043b\u044c*");
				formEdLab.setFont(new Font("Tahoma", Font.BOLD, 12));
				formEdLab.setPreferredSize(new Dimension(117, 15));
				panel3.add(formEdLab);

				//---- prepodBox ----
				prepodBox.setPreferredSize(new Dimension(180, 20));
				prepodBox.setMaximumRowCount(10);
				panel3.add(prepodBox);

				//======== panel7 ========
				{
					panel7.setLayout(new FlowLayout());

					//---- label1 ----
					label1.setText("\u041f\u0440\u0435\u0434\u043c\u0435\u0442");
					label1.setFont(new Font("Tahoma", Font.BOLD, 12));
					panel7.add(label1);

					//---- subjectBox ----
					subjectBox.setPreferredSize(new Dimension(160, 20));
					panel7.add(subjectBox);

					//---- yearLabel ----
					yearLabel.setText("\u0422\u0435\u043c\u0430*");
					yearLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
					panel7.add(yearLabel);

					//---- themeBox ----
					themeBox.setMaximumRowCount(50);
					themeBox.setPreferredSize(new Dimension(180, 20));
					panel7.add(themeBox);
				}
				panel3.add(panel7);
			}
			panel1.add(panel3);
		}
		contentPane.add(panel1, BorderLayout.NORTH);

		//======== panel2 ========
		{
			panel2.setPreferredSize(new Dimension(1733, 50));
			panel2.setLayout(new FlowLayout());

			//======== panel5 ========
			{
				panel5.setBorder(new CompoundBorder(
					new TitledBorder(" "),
					new EmptyBorder(5, 5, 5, 5)));
				panel5.setPreferredSize(new Dimension(500, 120));
				panel5.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 5));

				//---- birthdateLabel ----
				birthdateLabel.setText("\u041d\u0430\u0447\u0430\u043b\u043e \u0437\u0430\u043d\u044f\u0442\u0438\u044f*");
				birthdateLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
				birthdateLabel.setPreferredSize(new Dimension(130, 15));
				panel5.add(birthdateLabel);

				//======== panel4 ========
				{
					panel4.setLayout(new FlowLayout());

					//---- spinner1 ----
					spinner1.setModel(new SpinnerDateModel());
					panel4.add(spinner1);
				}
				panel5.add(panel4);

				//======== panel6 ========
				{
					panel6.setLayout(new FlowLayout());

					//---- label11 ----
					label11.setText("\u041a\u043e\u043d\u0435\u0446 \u0437\u0430\u043d\u044f\u0442\u0438\u044f формируется автоматически");
					label11.setFont(new Font("Tahoma", Font.BOLD, 12));
					label11.setPreferredSize(new Dimension(300, 15));
					panel6.add(label11);

					//---- spinner2 ----
					spinner2.setModel(new SpinnerDateModel());
					panel6.add(spinner2);
				}
				panel5.add(panel6);
			}
			panel2.add(panel5);
		}
		contentPane.add(panel2, BorderLayout.CENTER);
		setSize(575, 315);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel13;
	private JButton saveButton;
	private JButton cancelButton;
	private JPanel panel1;
	private JPanel panel3;
	private JLabel nameLabel;
	private JComboBox grupBox;
	private JLabel formEdLab;
	private JComboBox prepodBox;
	private JPanel panel7;
	private JLabel label1;
	private JComboBox subjectBox;
	private JLabel yearLabel;
	private JComboBox themeBox;
	private JPanel panel2;
	private JPanel panel5;
	private JLabel birthdateLabel;
	private JPanel panel4;
	private JSpinner spinner1;
	private JPanel panel6;
	private JLabel label11;
	private JSpinner spinner2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

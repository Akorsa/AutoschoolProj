/*
 * Created by JFormDesigner on Sat Jan 18 11:20:00 GMT+05:00 2014
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;

import rmi.AutoschoolRmiInterface;

import beans.Avto;
import beans.Drivingschedule;
import beans.Groups;
import beans.Students;
import beans.Teachers;
import com.jgoodies.forms.factories.*;

/**
 * @author ????????
 */
public class DriveDialog extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8125314430804263747L;
	private boolean result = false;
	private int driveId = 0;
	private AutoschoolRmiInterface ari; 
	private Groups gr;
	private Date endTime;
	private int c;
	public DriveDialog(Frame owner,AutoschoolRmiInterface ari, Groups g,int c) {
		super(owner);
		this.ari = ari;
		this.gr = g;
		this.c = c;
		initComponents(loadStudents(),loadMasters(),loadAvto());
		saveButton.addActionListener(this);
		saveButton.setName("OK");
		cancelButton.addActionListener(this);
		cancelButton.setName("Cancel");
	}

	

	private List<Avto>  loadAvto() {
		List<Avto> stud = null;
		try {
			stud = (List<Avto>) ari.getAvtoList();
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
	}



	private List<Teachers> loadMasters() {
		List<Teachers> stud = null;
		try {
			stud = (List<Teachers>) ari.getMastersList();
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
	}



	private List<Students> loadStudents() {
		List<Students> stud = null;
		try {
			stud = (List<Students>) ari.getStudentsFromGroup2(gr.getGroupid());
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
	}
	
	public void setRasp(Drivingschedule g) {
		driveId = g.getIddrivingshedule();
		//endTime = g.getDateend();
		spinner1.getModel().setValue(g.getDatebegin());
		textField1.setText(Integer.toString(g.getNumberOfHours()));
		if (g.isDoneFlag()){
			checkBox1.setSelected(true);
		} else {
			checkBox1.setSelected(false);
		}
		if (g.getDescription()== null){
			commentFild.setText("");
		} else {
			commentFild.setText(g.getDescription());
		}
		
		for (int i = 0; i < studentBox.getModel().getSize(); i++) {
			Students y = (Students) studentBox.getModel().getElementAt(i);
            if (y.getIdstudent()== g.getStudents().getIdstudent()) {
            	studentBox.setSelectedIndex(i);
                break;
            }
        }
		for (int i = 0; i < avtoBox.getModel().getSize(); i++) {
			Avto cat = (Avto) avtoBox.getModel().getElementAt(i);
            if (cat.getAvtoid()== g.getAvto().getAvtoid()) {
            	avtoBox.setSelectedIndex(i);
                break;
            }
        }
		for (int i = 0; i < masterBox.getModel().getSize(); i++) {
			Teachers cat = (Teachers) masterBox.getModel().getElementAt(i);
            if (cat.getPrepodid()== g.getTeachers().getPrepodid()) {
            	masterBox.setSelectedIndex(i);
                break;
            }
        }	
	}
	
	public Drivingschedule getRasp() {
		Drivingschedule st = new Drivingschedule();
		st.setIddrivingshedule(driveId);
		st.setAvto((Avto)avtoBox.getSelectedItem());
		st.setTeachers((Teachers)masterBox.getSelectedItem());
		st.setStudents((Students)studentBox.getSelectedItem());
		st.setDescription(commentFild.getText());
		
		Date dt1 = ((SpinnerDateModel)spinner1.getModel()).getDate();
		Calendar cal = Calendar.getInstance(); // creates calendar
	    cal.setTime(dt1); // sets calendar time/date
	    cal.add(Calendar.HOUR_OF_DAY,Integer.parseInt(textField1.getText())); // adds one hour
	    Date dt2 = cal.getTime();
		//Date dt2 = ((SpinnerDateModel)spinner2.getModel()).getDate();
		st.setDatebegin(dt1);
		st.setNumberOfHours(Integer.parseInt(textField1.getText()));
		st.setDateend(dt2);

	   
		if(checkBox1.isSelected()){
			st.setDoneFlag(true);
		} else {
			st.setDoneFlag(false);
		}
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
				if( c == 1){
					Teachers teach = (Teachers)masterBox.getSelectedItem();
					int prepodId = teach.getPrepodid();
					Date dt1 = ((SpinnerDateModel)spinner1.getModel()).getDate();
					Calendar cal = Calendar.getInstance(); // creates calendar
				    cal.setTime(dt1); // sets calendar time/date
				    cal.add(Calendar.HOUR_OF_DAY,Integer.parseInt(textField1.getText())); // adds one hour
				    Date dt2 = cal.getTime();
				    System.out.println(dt1 + " " + dt2);
				    List<Drivingschedule> ds  = null;
				    try {
						ds = ari.getDriveByTeacherAndTime(prepodId, dt1, dt2);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				    if (!ds.isEmpty()) {
				    	JOptionPane.showMessageDialog(DriveDialog.this,
	                            "Нельзя добавить занятие! Выберите другого мастера", "Внимание", 2);
				    } else {
				    	Students st = (Students)studentBox.getSelectedItem();
				    	List<Integer> ls = null;
				    	try {
							ls = ari.getHours(st.getIdstudent());
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
				    	int bt = 0;
				    	//ListIterator<Integer> lit = ls.listIterator();
				    	for(Object object : ls)
				         {
				            Map row = (Map)object;
				            //System.out.print("First Name: " + row.get("surname")); 
				            //System.out.println(", Salary: " + row.get("summ")); 
				            bt = ((BigInteger) row.get("summ")).intValue();
				         }
				    	
				    	
						int bt1 = 50 - bt;
				    	JOptionPane.showMessageDialog(DriveDialog.this,
	                            "Осталось "+ bt1 +" часов практического вождения!", "Внимание", 2);
				    	result = true;
			        	dispose();
				    }
				} else {
					result = true;
		        	dispose();
				}
				
	        }
	    if (src.getName().equals("Cancel")) {
	        result = false;
	        dispose();
	    }
		
	}



	private void initComponents(List<Students> list, List<Teachers> list2, List<Avto> list3) {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel13 = new JPanel();
		saveButton = new JButton();
		cancelButton = new JButton();
		panel1 = new JPanel();
		panel3 = new JPanel();
		formEdLab = new JLabel();
		studentBox = new JComboBox(new Vector<Students>(list));
		panel7 = new JPanel();
		label1 = new JLabel();
		avtoBox = new JComboBox(new Vector<Avto>(list3));
		yearLabel = new JLabel();
		masterBox = new JComboBox(new Vector<Teachers>(list2));
		panel2 = new JPanel();
		panel5 = new JPanel();
		birthdateLabel = new JLabel();
		panel4 = new JPanel();
		spinner1 = new JSpinner();
		panel6 = new JPanel();
		label11 = new JLabel();
		textField1 = new JTextField();
		panel9 = new JPanel();
		checkBox1 = new JCheckBox();
		panel8 = new JPanel();
		label2 = new JLabel();
		commentFild = new JTextField();

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

				//---- formEdLab ----
				formEdLab.setText("\u0423\u0447\u0430\u0449\u0438\u0439\u0441\u044f*");
				formEdLab.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
				formEdLab.setPreferredSize(new Dimension(117, 15));
				panel3.add(formEdLab);

				//---- studentBox ----
				studentBox.setPreferredSize(new Dimension(160, 20));
				studentBox.setMaximumRowCount(50);
				panel3.add(studentBox);

				//======== panel7 ========
				{
					panel7.setLayout(new FlowLayout());

					//---- label1 ----
					label1.setText("\u0410\u0432\u0442\u043e\u043c\u043e\u0431\u0438\u043b\u044c");
					label1.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
					panel7.add(label1);

					//---- avtoBox ----
					avtoBox.setPreferredSize(new Dimension(140, 20));
					avtoBox.setMaximumRowCount(50);
					panel7.add(avtoBox);

					//---- yearLabel ----
					yearLabel.setText("\u041c\u0430\u0441\u0442\u0435\u0440 \u041f\u041e*");
					yearLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
					panel7.add(yearLabel);

					//---- masterBox ----
					masterBox.setMaximumRowCount(50);
					masterBox.setPreferredSize(new Dimension(180, 20));
					panel7.add(masterBox);
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
				panel5.setPreferredSize(new Dimension(500, 100));
				panel5.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 5));

				//---- birthdateLabel ----
				birthdateLabel.setText("\u041d\u0430\u0447\u0430\u043b\u043e \u0437\u0430\u043d\u044f\u0442\u0438\u044f*");
				birthdateLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
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
					label11.setText("\u041a\u043e\u043b\u0438\u0447\u0435\u0441\u0442\u0432\u043e \u0447\u0430\u0441\u043e\u0432*");
					label11.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
					label11.setPreferredSize(new Dimension(130, 15));
					panel6.add(label11);
				}
				panel5.add(panel6);

				//---- textField1 ----
				textField1.setPreferredSize(new Dimension(40, 20));
				panel5.add(textField1);

				//======== panel9 ========
				{
					panel9.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 5));

					//---- checkBox1 ----
					checkBox1.setText("\u0412\u044b\u043f\u043e\u043b\u043d\u0435\u043d\u043e");
					checkBox1.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
					panel9.add(checkBox1);

					//======== panel8 ========
					{
						panel8.setLayout(new FlowLayout());

						//---- label2 ----
						label2.setText("\u041a\u043e\u043c\u043c\u0435\u043d\u0442\u0430\u0440\u0438\u0439");
						label2.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
						panel8.add(label2);

						//---- commentFild ----
						commentFild.setPreferredSize(new Dimension(80, 20));
						panel8.add(commentFild);
					}
					panel9.add(panel8);
				}
				panel5.add(panel9);
			}
			panel2.add(panel5);
		}
		contentPane.add(panel2, BorderLayout.CENTER);
		setSize(560, 310);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel13;
	private JButton saveButton;
	private JButton cancelButton;
	private JPanel panel1;
	private JPanel panel3;
	private JLabel formEdLab;
	private JComboBox studentBox;
	private JPanel panel7;
	private JLabel label1;
	private JComboBox avtoBox;
	private JLabel yearLabel;
	private JComboBox masterBox;
	private JPanel panel2;
	private JPanel panel5;
	private JLabel birthdateLabel;
	private JPanel panel4;
	private JSpinner spinner1;
	private JPanel panel6;
	private JLabel label11;
	private JTextField textField1;
	private JPanel panel9;
	private JCheckBox checkBox1;
	private JPanel panel8;
	private JLabel label2;
	private JTextField commentFild;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

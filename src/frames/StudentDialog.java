/*
 * Created by JFormDesigner on Sun Dec 15 12:49:42 GMT+05:00 2013
 */

package frames;

import java.awt.*;
import java.awt.event.*;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;

import rmi.AutoschoolRmiInterface;

import util.HashUtil;
import beans.Groups;
import beans.Questions;
import beans.Students;
import beans.TypeEducation;
import beans.TypeUser;
import beans.Users;

import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import com.toedter.calendar.*;


/**
 * @author ????????
 */
public class StudentDialog extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2706870771212357319L;
	private boolean result = false;
	private int studId = 0;
	private Users usrs = null;
	private String svidInfo;
	private String passw;
	private String login;
	private Date ekzDate;
	private int newItem;
	private AutoschoolRmiInterface ari;
	public StudentDialog(Frame owner, List<TypeEducation> list, List<Groups> groups, int newItem,AutoschoolRmiInterface ari) {
	//public StudentDialog(ZamDirectPRFrame owner, List<TypeEducation> list) {
		super(owner);
		initComponents(groups,list);
		this.newItem = newItem;
		this.ari = ari;
		saveButton.addActionListener(this);
		saveButton.setName("OK");
		cancelButton.addActionListener(this);
		cancelButton.setName("Cancel");
		
		
	}
	

	public void setStudent(Students st) {
		studId = st.getIdstudent();
		svidInfo = st.getSvidInfo();
		ekzDate = st.getEkzData();
		famField.setText(st.getSurname());
		imField.setText(st.getName());
		otchField.setText(st.getPatronymicname());
		birthdateChooser.setDate(st.getDatebirth());
		birthpalceTextField.setText(st.getBirthplace());
		adressTextfield.setText(st.getAddress());
		typeDocField.setText(st.getTypeDocum());
		serDocField.setText(st.getDocseries());
		numDocField.setText(st.getDocnumber());
		docdateChooser.setDate(st.getDocdate());
		kevvidanfield.setText(st.getDoinfo());
		textField12.setText(st.getPhone());
		
		for (int i = 0; i < groupBox.getModel().getSize(); i++) {
            Groups g = (Groups) groupBox.getModel().getElementAt(i);
            if (g.getGroupid()== st.getGroups().getGroupid()) {
                groupBox.setSelectedIndex(i);
                break;
            }
        }
		for (int i = 0; i < typeOfEducationBox.getModel().getSize(); i++) {
			TypeEducation g = (TypeEducation) typeOfEducationBox.getModel().getElementAt(i);
            if (g.getTypeEducationId()== st.getTypeEducation().getTypeEducationId()) {
            	typeOfEducationBox.setSelectedIndex(i);
                break;
            }
        }
		uzField.setText(st.getEducationinfo());
		workField.setText(st.getJobplace());
		if (st.getOtchislen() != null) {    
				if (st.getOtchislen().equals ("Да")) {
					otchCheckBox.setSelected(true);
				}
		}
		if (st.getEkzFlag() != null) {    
			if (st.getEkzFlag().equals ("Да")) {
				okonchilBox.setSelected(true);
			}
		}
		if (st.getUsers() != null) {    
			usrs = st.getUsers();
		}
		
	}
	
	// Вернуть данные в виде новой ставки с соответствующими полями
		public Students getStudents() {
			Students st = new Students();
			st.setIdstudent(studId);
			st.setSvidInfo(svidInfo);
			st.setEkzData(ekzDate);
		
			st.setSurname(famField.getText());
			st.setName(imField.getText());
			st.setPatronymicname(otchField.getText());
			st.setDatebirth(birthdateChooser.getDate());
			st.setBirthplace(birthpalceTextField.getText());
			st.setAddress(adressTextfield.getText());
			st.setTypeDocum(typeDocField.getText());
			st.setDocseries(serDocField.getText());
			st.setDocnumber(numDocField.getText());
			st.setDocdate(docdateChooser.getDate());
			st.setDoinfo(kevvidanfield.getText());
			st.setGroups((Groups)groupBox.getSelectedItem());
			st.setTypeEducation((TypeEducation)typeOfEducationBox.getSelectedItem());
			st.setEducationinfo(uzField.getText());
			st.setJobplace(workField.getText());
			st.setPhone(textField12.getText());
			if (otchCheckBox.isSelected()==true) {
				st.setOtchislen("Да");
			}
			if (okonchilBox.isSelected()==true) {
				st.setEkzFlag("Да");
			}
			if (newItem == 1) {
				
				login = famField.getText();
				TypeUser tu = null;
				try {
					tu = ari.getTypeUserById(7);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Users u1 = new Users();
				u1.setLogin(famField.getText());
				u1.setName(famField.getText() + " " + imField.getText());
				u1.setPassword(HashUtil.simpleHash(passw));
				
				u1.setTypeUser(tu);
				try {
					ari.insertUser(u1);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				List<Users> u = null;
				try {
					u = ari.getUsersByLogin(login);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				ListIterator<Users> iter ;
				iter = u.listIterator();
				Users usrlast = iter.next();
				st.setUsers(usrlast);
				
			}  else {
				st.setUsers(usrs);
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
	        if (famField.getText().equals("") || imField.getText().equals("") || otchField.getText().equals("")|| 
	        		birthdateChooser.getDate()== null || numDocField.getText().equals("")) {
	        	JOptionPane.showMessageDialog(this, "Заполните все обязательные поля, помеченные звездочкой!", "Внимание", 2);	
	        	
	        }else {
	        	if (newItem == 1) {
	        		Random r = new Random();
	        		int pas =  r.nextInt(999 - 100) + 100;
					passw = Integer.toString(pas);
	        		JOptionPane.showMessageDialog(this, "Запомните авторизационные данные для этого учащегося: логин -"+ famField.getText()+", пароль - "+passw, "Внимание", 2);
	        	}
	        	result = true;
	        	dispose();
	        }
		}
	    if (src.getName().equals("Cancel")) {
	        result = false;
	        dispose();
	    }
	  
		
	}
	
	
	private void initComponents(List<Groups> groups, List<TypeEducation> list) {
	
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel1 = new JPanel();
		panel3 = new JPanel();
		famLabel = new JLabel();
		famField = new JTextField();
		imLabel = new JLabel();
		imField = new JTextField();
		otchLabel = new JLabel();
		otchField = new JTextField();
		panel4 = new JPanel();
		birthdateLabel = new JLabel();
		birthdateChooser = new JDateChooser();
		panel2 = new JPanel();
		panel5 = new JPanel();
		gruopLabel = new JLabel();
		groupBox = new JComboBox(new Vector<Groups>(groups));
		label5 = new JLabel();
		birthpalceTextField = new JTextField();
		label7 = new JLabel();
		adressTextfield = new JTextField();
		panel10 = new JPanel();
		label8 = new JLabel();
		typeDocField = new JTextField();
		label9 = new JLabel();
		serDocField = new JTextField();
		label10 = new JLabel();
		numDocField = new JTextField();
		label11 = new JLabel();
		docdateChooser = new JDateChooser();
		label12 = new JLabel();
		kevvidanfield = new JTextField();
		panel12 = new JPanel();
		panel8 = new JPanel();
		label13 = new JLabel();
		typeOfEducationBox = new JComboBox(new Vector<TypeEducation>(list));
		label14 = new JLabel();
		uzField = new JTextField();
		label15 = new JLabel();
		workField = new JTextField();
		label16 = new JLabel();
		textField12 = new JTextField();
		otchCheckBox = new JCheckBox();
		okonchilBox = new JCheckBox();
		okonchilBox.setEnabled(false);
		panel13 = new JPanel();
		saveButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Добавить/редактировать запись");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== panel1 ========
		{
			panel1.setBorder(new CompoundBorder(
				new TitledBorder(" "),
				Borders.DLU2_BORDER));
			panel1.setLayout(new FlowLayout());

			//======== panel3 ========
			{
				panel3.setLayout(new FlowLayout());

				//---- famLabel ----
				famLabel.setText("\u0424\u0430\u043c\u0438\u043b\u0438\u044f* ");
				famLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
				panel3.add(famLabel);

				//---- famField ----
				famField.setColumns(8);
				panel3.add(famField);

				//---- imLabel ----
				imLabel.setText("\u0418\u043c\u044f*");
				imLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
				panel3.add(imLabel);

				//---- imField ----
				imField.setColumns(8);
				panel3.add(imField);

				//---- otchLabel ----
				otchLabel.setText("\u041e\u0442\u0447\u0435\u0441\u0442\u0432\u043e*");
				otchLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
				panel3.add(otchLabel);

				//---- otchField ----
				otchField.setColumns(8);
				panel3.add(otchField);
			}
			panel1.add(panel3);

			//======== panel4 ========
			{
				panel4.setLayout(new FlowLayout());

				//---- birthdateLabel ----
				birthdateLabel.setText("\u0414\u0430\u0442\u0430 \u0440\u043e\u0436\u0434\u0435\u043d\u0438\u044f*");
				birthdateLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
				panel4.add(birthdateLabel);

				//---- birthdateChooser ----
				birthdateChooser.setPreferredSize(new Dimension(90, 20));
				panel4.add(birthdateChooser);
			}
			panel1.add(panel4);
		}
		contentPane.add(panel1, BorderLayout.NORTH);

		//======== panel2 ========
		{
			panel2.setLayout(new FlowLayout());

			//======== panel5 ========
			{
				panel5.setBorder(new CompoundBorder(
					new TitledBorder(" "),
					new EmptyBorder(5, 5, 5, 5)));
				panel5.setPreferredSize(new Dimension(1000, 150));
				panel5.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 5));

				//---- gruopLabel ----
				gruopLabel.setText("\u0413\u0440\u0443\u043f\u043f\u0430*");
				gruopLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
				panel5.add(gruopLabel);

				//---- groupBox ----
				groupBox.setMaximumRowCount(50);
				groupBox.setPreferredSize(new Dimension(70, 20));
				panel5.add(groupBox);

				//---- label5 ----
				label5.setText("\u041c\u0435\u0441\u0442\u043e \u0440\u043e\u0436\u0434\u0435\u043d\u0438\u044f");
				panel5.add(label5);

				//---- birthpalceTextField ----
				birthpalceTextField.setColumns(12);
				panel5.add(birthpalceTextField);

				//---- label7 ----
				label7.setText("\u0410\u0434\u0440\u0435\u0441 \u043f\u0440\u043e\u0436\u0438\u0432\u0430\u043d\u0438\u044f");
				panel5.add(label7);

				//---- adressTextfield ----
				adressTextfield.setColumns(12);
				panel5.add(adressTextfield);

				//======== panel10 ========
				{
					panel10.setBorder(new CompoundBorder(
						new TitledBorder("\u0414\u043e\u043a\u0443\u043c\u0435\u043d\u0442, \u0443\u0434\u043e\u0441\u0442\u043e\u0432\u0435\u0440\u044f\u044e\u0449\u0438\u0439 \u043b\u0438\u0447\u043d\u043e\u0441\u0442\u044c"),
						new EmptyBorder(5, 5, 5, 5)));
					panel10.setLayout(new FormLayout(
						"8*(default, $lcgap), default",
						"fill:default, $lgap, default, $lgap"));

					//---- label8 ----
					label8.setText("\u0422\u0438\u043f \u0434\u043e\u043a\u0443\u043c\u0435\u043d\u0442\u0430");
					panel10.add(label8, CC.xy(1, 1));

					//---- typeDocField ----
					typeDocField.setColumns(12);
					panel10.add(typeDocField, CC.xy(3, 1));

					//---- label9 ----
					label9.setText("\u0421\u0435\u0440\u0438\u044f");
					panel10.add(label9, CC.xy(5, 1));

					//---- serDocField ----
					serDocField.setColumns(8);
					panel10.add(serDocField, CC.xy(7, 1));

					//---- label10 ----
					label10.setText("\u041d\u043e\u043c\u0435\u0440*");
					label10.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
					panel10.add(label10, CC.xy(9, 1));

					//---- numDocField ----
					numDocField.setColumns(6);
					panel10.add(numDocField, CC.xy(11, 1));

					//---- label11 ----
					label11.setText("\u0414\u0430\u0442\u0430 \u0432\u044b\u0434\u0430\u0447\u0438");
					panel10.add(label11, CC.xy(13, 1));

					//---- docdateChooser ----
					docdateChooser.setPreferredSize(new Dimension(95, 20));
					panel10.add(docdateChooser, CC.xy(15, 1));

					//---- label12 ----
					label12.setText("\u041a\u0435\u043c \u0432\u044b\u0434\u0430\u043d\u043e");
					panel10.add(label12, CC.xy(1, 3));
					panel10.add(kevvidanfield, CC.xy(3, 3));
				}
				panel5.add(panel10);
			}
			panel2.add(panel5);

			//======== panel12 ========
			{
				panel12.setLayout(new FlowLayout());

				//======== panel8 ========
				{
					panel8.setBorder(new CompoundBorder(
						new TitledBorder("\u0414\u043e\u043f\u043e\u043b\u043d\u0438\u0442\u0435\u043b\u044c\u043d\u044b\u0435 \u0441\u0432\u0435\u0434\u0435\u043d\u0438\u044f"),
						Borders.DLU2_BORDER));
					panel8.setLayout(new FormLayout(
						"6*(default, $lcgap), default",
						"fill:default, $lgap, default, $lgap"));

					//---- label13 ----
					label13.setText("\u041e\u0431\u0440\u0430\u0437\u043e\u0432\u0430\u043d\u0438\u0435");
					label13.setFont(new Font("Trebuchet MS", Font.BOLD, 13));
					panel8.add(label13, CC.xy(1, 1));

					//---- typeOfEducationBox ----
					typeOfEducationBox.setMaximumRowCount(10);
					typeOfEducationBox.setEditable(true);
					panel8.add(typeOfEducationBox, CC.xy(3, 1));

					//---- label14 ----
					label14.setText("\u0423\u0447\u0435\u0431\u043d\u043e\u0435 \u0437\u0430\u0432\u0435\u0434\u0435\u043d\u0438\u0435");
					panel8.add(label14, CC.xy(5, 1));

					//---- uzField ----
					uzField.setColumns(10);
					panel8.add(uzField, CC.xy(7, 1));

					//---- label15 ----
					label15.setText("\u041c\u0435\u0441\u0442\u043e \u0440\u0430\u0431\u043e\u0442\u044b");
					panel8.add(label15, CC.xy(9, 1));

					//---- workField ----
					workField.setColumns(10);
					panel8.add(workField, CC.xy(11, 1));

					//---- label16 ----
					label16.setText("\u0422\u0435\u043b\u0435\u0444\u043e\u043d");
					panel8.add(label16, CC.xy(1, 3));

					//---- textField12 ----
					textField12.setColumns(10);
					panel8.add(textField12, CC.xy(3, 3));

					//---- otchCheckBox ----
					otchCheckBox.setText("\u041e\u0442\u0447\u0438\u0441\u043b\u0435\u043d");
					panel8.add(otchCheckBox, CC.xy(7, 3));

					//---- okonchilBox ----
					okonchilBox.setText("\u0421\u0434\u0430\u043b \u044d\u043a\u0437\u0430\u043c\u0435\u043d");
					panel8.add(okonchilBox, CC.xy(11, 3));
				}
				panel12.add(panel8);
			}
			panel2.add(panel12);

			//======== panel13 ========
			{
				panel13.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 5));

				//---- saveButton ----
				saveButton.setSelectedIcon(new ImageIcon(getClass().getResource("/images/Nnm 16/check.png")));
				saveButton.setIcon(new ImageIcon(getClass().getResource("/images/Nnm 16/check.png")));
				saveButton.setText("\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c");
				panel13.add(saveButton);

				//---- cancelButton ----
				cancelButton.setText("\u041e\u0442\u043c\u0435\u043d\u0430");
				cancelButton.setIcon(new ImageIcon(getClass().getResource("/images/Nnm 16/logout.png")));
				panel13.add(cancelButton);
			}
			panel2.add(panel13);
		}
		contentPane.add(panel2, BorderLayout.CENTER);
		setResizable(true);
		setSize(865, 425);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel1;
	private JPanel panel3;
	private JLabel famLabel;
	private JTextField famField;
	private JLabel imLabel;
	private JTextField imField;
	private JLabel otchLabel;
	private JTextField otchField;
	private JPanel panel4;
	private JLabel birthdateLabel;
	private JDateChooser birthdateChooser;
	private JPanel panel2;
	private JPanel panel5;
	private JLabel gruopLabel;
	private JComboBox groupBox;
	private JLabel label5;
	private JTextField birthpalceTextField;
	private JLabel label7;
	private JTextField adressTextfield;
	private JPanel panel10;
	private JLabel label8;
	private JTextField typeDocField;
	private JLabel label9;
	private JTextField serDocField;
	private JLabel label10;
	private JTextField numDocField;
	private JLabel label11;
	private JDateChooser docdateChooser;
	private JLabel label12;
	private JTextField kevvidanfield;
	private JPanel panel12;
	private JPanel panel8;
	private JLabel label13;
	private JComboBox typeOfEducationBox;
	private JLabel label14;
	private JTextField uzField;
	private JLabel label15;
	private JTextField workField;
	private JLabel label16;
	private JTextField textField12;
	private JCheckBox otchCheckBox;
	private JCheckBox okonchilBox;
	private JPanel panel13;
	private JButton saveButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	
}

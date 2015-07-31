/*
 * Created by JFormDesigner on Thu Dec 19 17:51:48 GMT+05:00 2013
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;

import beans.Teachers;
import beans.TypeEducation;
import beans.TypeTeacher;

import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import com.toedter.calendar.*;

/**
 * @author ????????
 */
public class TeacherDialog extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8110723309664046966L;
	private boolean result = false;
	private int prepodId = 0;
	private int flag;
	public TeacherDialog(Frame owner,List<TypeEducation> list, int flag) {
		super(owner);
		this.flag = flag;
		initComponents(list);
		saveButton.addActionListener(this);
		saveButton.setName("OK");
		cancelButton.addActionListener(this);
		cancelButton.setName("Cancel");
	}

	public void setPrepod(Teachers st) {
		prepodId = st.getPrepodid();
		famField.setText(st.getPrepodfam());
		imField.setText(st.getPrepodim());
		otchField.setText(st.getPrepodot());
		birthdateChooser.setDate(st.getBirthdate());
		birthpalceTextField.setText(st.getBirthplace());
		adressTextfield.setText(st.getAddress());
		phoneFild.setText(st.getPhone());
		serDocField.setText(st.getDiplomseria());
		yearChooser1.setYear(st.getGodokonchaniya());
		serFild.setText(st.getPravan());
		dataVidChooser.setDate(st.getPravadata());
		categField.setText(st.getPravakat());
		stazhFild.setText(Integer.toString(st.getVoditstazh()));
		vidanFild.setText(st.getPravavidano());
		for (int i = 0; i < typeOfEducationBox.getModel().getSize(); i++) {
			TypeEducation g = (TypeEducation) typeOfEducationBox.getModel().getElementAt(i);
            if (g.getTypeEducationId()== st.getTypeEducation().getTypeEducationId()) {
            	typeOfEducationBox.setSelectedIndex(i);
                break;
            }
        }
		uzField.setText(st.getUchzavedenie());
		if (st.isUvolen()== true){
			checkBox1.setSelected(true);
		}
		textField3.setText(st.getUvolinfo());
		
	}
	
	public Teachers getPrepod() {
		Teachers st = new Teachers();
		st.setPrepodid(prepodId);
		st.setPrepodfam(famField.getText());
		st.setPrepodim(imField.getText());
		st.setPrepodot(otchField.getText());
		st.setBirthdate(birthdateChooser.getDate());
		st.setBirthplace(birthpalceTextField.getText());
		st.setAddress(adressTextfield.getText());
		st.setPhone(phoneFild.getText());
		st.setDiplomseria(serDocField.getText());
		st.setGodokonchaniya(yearChooser1.getYear());
		st.setPravan(serFild.getText());
		st.setPravadata(dataVidChooser.getDate());
		st.setPravakat(categField.getText());
		TypeTeacher tt = null;
		if (flag==1) {
			tt = new TypeTeacher (1,"Преподаватель");
		} if (flag == 2){
			tt = new TypeTeacher (2,"Мастер ПО");
		}
		st.setTypeTeacher(tt);
		
		try {
        	int s = Integer.parseInt(stazhFild.getText());
            st.setVoditstazh(s);
        } catch (NumberFormatException nfe) {
        }
		st.setTypeEducation((TypeEducation)typeOfEducationBox.getSelectedItem());
		st.setPravavidano(vidanFild.getText());
		st.setUchzavedenie(uzField.getText());
		if (checkBox1.isSelected()==true) {
			st.setUvolen(true);
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
	        		birthdateChooser.getDate()== null || serDocField.getText().equals("")) {
	        	JOptionPane.showMessageDialog(this, "Заполните все обязательные поля, помеченные звездочкой!", "Внимание", 2);	
	        	
	        }else {
	        	result = true;
	        	dispose();
	        }
		}
	    if (src.getName().equals("Cancel")) {
	        result = false;
	        dispose();
	    }
		
	}

	private void initComponents(List<TypeEducation> list) {
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
		label16 = new JLabel();
		phoneFild = new JTextField();
		label5 = new JLabel();
		birthpalceTextField = new JTextField();
		label7 = new JLabel();
		adressTextfield = new JTextField();
		panel10 = new JPanel();
		label13 = new JLabel();
		typeOfEducationBox = new JComboBox(new Vector<TypeEducation>(list));
		label14 = new JLabel();
		uzField = new JTextField();
		label9 = new JLabel();
		label11 = new JLabel();
		yearChooser1 = new JYearChooser();
		serDocField = new JTextField();
		panel12 = new JPanel();
		panel8 = new JPanel();
		label1 = new JLabel();
		serFild = new JTextField();
		label2 = new JLabel();
		dataVidChooser = new JDateChooser();
		label15 = new JLabel();
		categField = new JTextField();
		label3 = new JLabel();
		stazhFild = new JTextField();
		label4 = new JLabel();
		vidanFild = new JTextField();
		panel6 = new JPanel();
		checkBox1 = new JCheckBox();
		label6 = new JLabel();
		textField3 = new JTextField();
		panel13 = new JPanel();
		saveButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c/\u0440\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u0437\u0430\u043f\u0438\u0441\u044c");
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
				famLabel.setText("\u0424\u0430\u043c\u0438\u043b\u0438\u044f *");
				famLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel3.add(famLabel);

				//---- famField ----
				famField.setColumns(8);
				panel3.add(famField);

				//---- imLabel ----
				imLabel.setText("\u0418\u043c\u044f *");
				imLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel3.add(imLabel);

				//---- imField ----
				imField.setColumns(8);
				panel3.add(imField);

				//---- otchLabel ----
				otchLabel.setText("\u041e\u0442\u0447\u0435\u0441\u0442\u0432\u043e*");
				otchLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
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
				birthdateLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
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
				panel5.setBorder(null);
				panel5.setPreferredSize(new Dimension(800, 150));
				panel5.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 5));

				//---- label16 ----
				label16.setText("\u0422\u0435\u043b\u0435\u0444\u043e\u043d");
				panel5.add(label16);

				//---- phoneFild ----
				phoneFild.setColumns(10);
				panel5.add(phoneFild);

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
						new TitledBorder("\u041e\u0431\u0440\u0430\u0437\u043e\u0432\u0430\u043d\u0438\u0435"),
						new EmptyBorder(5, 5, 5, 5)));
					panel10.setLayout(new FormLayout(
						"5*(default, $lcgap), default",
						"fill:default, $lgap, default, $lgap"));

					//---- label13 ----
					label13.setText("\u041e\u0431\u0440\u0430\u0437\u043e\u0432\u0430\u043d\u0438\u0435");
					label13.setFont(new Font("Tahoma", Font.BOLD, 12));
					panel10.add(label13, CC.xy(1, 1));

					//---- typeOfEducationBox ----
					typeOfEducationBox.setMaximumRowCount(10);
					typeOfEducationBox.setEditable(true);
					panel10.add(typeOfEducationBox, CC.xy(3, 1));

					//---- label14 ----
					label14.setText("\u0423\u0447\u0435\u0431\u043d\u043e\u0435 \u0437\u0430\u0432\u0435\u0434\u0435\u043d\u0438\u0435");
					panel10.add(label14, CC.xy(5, 1));

					//---- uzField ----
					uzField.setColumns(10);
					panel10.add(uzField, CC.xy(7, 1));

					//---- label9 ----
					label9.setText("\u0421\u0435\u0440\u0438\u044f/\u043d\u043e\u043c\u0435\u0440 (\u0434\u0438\u043f\u043b\u043e\u043c\u0430, \u0441\u0432\u0438\u0434.)*");
					panel10.add(label9, CC.xy(9, 1));

					//---- label11 ----
					label11.setText("\u0413\u043e\u0434 \u043e\u043a\u043e\u043d\u0447\u0430\u043d\u0438\u044f");
					panel10.add(label11, CC.xy(5, 3));
					panel10.add(yearChooser1, CC.xy(7, 3));

					//---- serDocField ----
					serDocField.setColumns(8);
					panel10.add(serDocField, CC.xy(9, 3));
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
						new TitledBorder("\u0412\u043e\u0434\u0438\u0442\u0435\u043b\u044c\u0441\u043a\u043e\u0435 \u0443\u0434\u043e\u0441\u0442\u043e\u0432\u0435\u0440\u0435\u043d\u0438\u0435, \u0441\u0442\u0430\u0436"),
						Borders.DLU2_BORDER));
					panel8.setLayout(new FormLayout(
						"6*(default, $lcgap), default",
						"fill:default, $lgap, default, $lgap"));

					//---- label1 ----
					label1.setText("\u0421\u0435\u0440\u0438\u044f, \u043d\u043e\u043c\u0435\u0440");
					panel8.add(label1, CC.xy(1, 1));

					//---- serFild ----
					serFild.setColumns(7);
					panel8.add(serFild, CC.xy(3, 1));

					//---- label2 ----
					label2.setText("\u0414\u0430\u0442\u0430 \u0432\u044b\u0434\u0430\u0447\u0438");
					panel8.add(label2, CC.xy(5, 1));

					//---- dataVidChooser ----
					dataVidChooser.setPreferredSize(new Dimension(95, 20));
					panel8.add(dataVidChooser, CC.xy(7, 1));

					//---- label15 ----
					label15.setText("\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0438\u044f");
					panel8.add(label15, CC.xy(9, 1));

					//---- categField ----
					categField.setColumns(10);
					panel8.add(categField, CC.xy(11, 1));

					//---- label3 ----
					label3.setText("\u0412\u043e\u0434. \u0441\u0442\u0430\u0436(\u043b\u0435\u0442)");
					panel8.add(label3, CC.xy(1, 3));
					panel8.add(stazhFild, CC.xy(3, 3));

					//---- label4 ----
					label4.setText("\u041a\u0435\u043c \u0432\u044b\u0434\u0430\u043d\u043e ");
					panel8.add(label4, CC.xy(5, 3));
					panel8.add(vidanFild, CC.xy(7, 3));
				}
				panel12.add(panel8);
			}
			panel2.add(panel12);

			//======== panel6 ========
			{
				panel6.setLayout(new FormLayout(
					"left:default:grow",
					"fill:default, default, fill:default"));

				//---- checkBox1 ----
				checkBox1.setText("\u0423\u0432\u043e\u043b\u0435\u043d");
				panel6.add(checkBox1, CC.xy(1, 1));

				//---- label6 ----
				label6.setText("\u041e\u0441\u043d\u043e\u0432\u0430\u043d\u0438\u0435, \u2116 \u043f\u0440\u0438\u043a\u0430\u0437\u0430, \u0434\u0430\u0442\u0430");
				panel6.add(label6, CC.xy(1, 2));

				//---- textField3 ----
				textField3.setColumns(18);
				panel6.add(textField3, CC.xy(1, 3));
			}
			panel2.add(panel6);

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
		setSize(820, 410);
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
	private JLabel label16;
	private JTextField phoneFild;
	private JLabel label5;
	private JTextField birthpalceTextField;
	private JLabel label7;
	private JTextField adressTextfield;
	private JPanel panel10;
	private JLabel label13;
	private JComboBox typeOfEducationBox;
	private JLabel label14;
	private JTextField uzField;
	private JLabel label9;
	private JLabel label11;
	private JYearChooser yearChooser1;
	private JTextField serDocField;
	private JPanel panel12;
	private JPanel panel8;
	private JLabel label1;
	private JTextField serFild;
	private JLabel label2;
	private JDateChooser dataVidChooser;
	private JLabel label15;
	private JTextField categField;
	private JLabel label3;
	private JTextField stazhFild;
	private JLabel label4;
	private JTextField vidanFild;
	private JPanel panel6;
	private JCheckBox checkBox1;
	private JLabel label6;
	private JTextField textField3;
	private JPanel panel13;
	private JButton saveButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	
}

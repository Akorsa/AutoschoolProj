/*
 * Created by JFormDesigner on Mon Dec 16 23:21:06 GMT+05:00 2013
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;

import beans.Categories;
import beans.Educationtypes;
import beans.Groups;
import beans.YearOfEducation;

import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import com.toedter.calendar.*;

/**
 * @author ????????
 */
public class GroupDialog extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1582433524157098110L;
	private boolean result = false;
	private int grupId = 0;
	
	public GroupDialog(Frame owner,List<Categories> cat, List<Educationtypes> edTyp, List<YearOfEducation> yeEd) {
		super(owner);
		initComponents(cat,edTyp,yeEd);
		saveButton.addActionListener(this);
		saveButton.setName("OK");
		cancelButton.addActionListener(this);
		cancelButton.setName("Cancel");
	}

	public void setGrup(Groups g) {
		grupId = g.getGroupid();
		nameField.setText(g.getGroupName());
		startDateChooser.setDate(g.getGruopstartdate());
		enddateChooser.setDate(g.getGroupenddate());
		numberPricField.setText(g.getPricazn());
		datepricChooser.setDate(g.getPricazdata());
		
		for (int i = 0; i < yearBox.getModel().getSize(); i++) {
			YearOfEducation y = (YearOfEducation) yearBox.getModel().getElementAt(i);
            if (y.getYearOfEducationId()== g.getYearOfEducation().getYearOfEducationId()) {
            	yearBox.setSelectedIndex(i);
                break;
            }
        }
		for (int i = 0; i < categBox.getModel().getSize(); i++) {
			Categories cat = (Categories) categBox.getModel().getElementAt(i);
            if (cat.getKategoriesid()== g.getCategories().getKategoriesid()) {
            	categBox.setSelectedIndex(i);
                break;
            }
        }
		for (int i = 0; i < formEdBox.getModel().getSize(); i++) {
			Educationtypes cat = (Educationtypes) formEdBox.getModel().getElementAt(i);
            if (cat.getEducationtypeid()== g.getEducationtypes().getEducationtypeid()) {
            	formEdBox.setSelectedIndex(i);
                break;
            }
        }
		if (g.getFlagZakr() != null) {    
			if (g.getFlagZakr().equals ("Да")) {
				checkBox1.setSelected(true);
			}
			if (g.getFlagZakr().equals ("Нет")) {
				checkBox1.setSelected(false);
			}
	}
	}
	
	// Вернуть данные в виде новой ставки с соответствующими полями
			public Groups getGrup() {
				Groups st = new Groups();
				st.setGroupid(grupId);
				st.setGroupName(nameField.getText());
				st.setGruopstartdate(startDateChooser.getDate());
				st.setGroupenddate(enddateChooser.getDate());
				st.setPricazn(numberPricField.getText());
				st.setPricazdata(datepricChooser.getDate());
				st.setYearOfEducation((YearOfEducation)yearBox.getSelectedItem());
				st.setCategories((Categories)categBox.getSelectedItem());
				st.setEducationtypes((Educationtypes)formEdBox.getSelectedItem());
				if (checkBox1.isSelected()==true) {
					st.setFlagZakr("Да");
				}
				if (checkBox1.isSelected()==false) {
					st.setFlagZakr("Нет");
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
			        if (nameField.getText().equals("") || startDateChooser.getDate()== null || enddateChooser.getDate()== null || 
			        		datepricChooser.getDate()== null || numberPricField.getText().equals("")) {
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

	private void initComponents(List<Categories> cat, List<Educationtypes> edTyp, List<YearOfEducation> yeEd) {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel1 = new JPanel();
		panel3 = new JPanel();
		nameLabel = new JLabel();
		nameField = new JTextField();
		yearLabel = new JLabel();
		yearBox = new JComboBox(new Vector<YearOfEducation>(yeEd));
		categLabel = new JLabel();
		categBox = new JComboBox(new Vector<Categories>(cat));
		formEdLab = new JLabel();
		formEdBox = new JComboBox(new Vector<Educationtypes>(edTyp));
		panel2 = new JPanel();
		panel5 = new JPanel();
		birthdateLabel = new JLabel();
		startDateChooser = new JDateChooser();
		label11 = new JLabel();
		enddateChooser = new JDateChooser();
		checkBox1 = new JCheckBox();
		panel12 = new JPanel();
		panel8 = new JPanel();
		label13 = new JLabel();
		numberPricField = new JTextField();
		label14 = new JLabel();
		datepricChooser = new JDateChooser();
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
			panel1.setPreferredSize(new Dimension(605, 100));
			panel1.setLayout(new FlowLayout());

			//======== panel3 ========
			{
				panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

				//---- nameLabel ----
				nameLabel.setText("\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435*");
				nameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel3.add(nameLabel);

				//---- nameField ----
				nameField.setColumns(10);
				panel3.add(nameField);

				//---- yearLabel ----
				yearLabel.setText("\u0413\u043e\u0434*");
				yearLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel3.add(yearLabel);

				//---- yearBox ----
				yearBox.setMaximumRowCount(50);
				yearBox.setPreferredSize(new Dimension(70, 20));
				yearBox.setBorder(null);
				panel3.add(yearBox);

				//---- categLabel ----
				categLabel.setText("\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0438\u044f \u0422\u0421*");
				categLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel3.add(categLabel);

				//---- categBox ----
				categBox.setPreferredSize(new Dimension(100, 20));
				categBox.setMaximumRowCount(10);
				panel3.add(categBox);
			}
			panel1.add(panel3);

			//---- formEdLab ----
			formEdLab.setText("\u0424\u043e\u0440\u043c\u0430 \u043e\u0431\u0443\u0447\u0435\u043d\u0438\u044f*");
			formEdLab.setFont(new Font("Tahoma", Font.BOLD, 12));
			panel1.add(formEdLab);

			//---- formEdBox ----
			formEdBox.setPreferredSize(new Dimension(95, 20));
			formEdBox.setMaximumRowCount(10);
			panel1.add(formEdBox);
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
				panel5.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

				//---- birthdateLabel ----
				birthdateLabel.setText("\u041d\u0430\u0447\u0430\u043b\u043e \u0437\u0430\u043d\u044f\u0442\u0438\u0439*");
				birthdateLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel5.add(birthdateLabel);

				//---- startDateChooser ----
				startDateChooser.setPreferredSize(new Dimension(90, 20));
				panel5.add(startDateChooser);

				//---- label11 ----
				label11.setText("\u041a\u043e\u043d\u0435\u0446 \u0437\u0430\u043d\u044f\u0442\u0438\u0439");
				panel5.add(label11);

				//---- enddateChooser ----
				enddateChooser.setPreferredSize(new Dimension(95, 20));
				panel5.add(enddateChooser);

				//---- checkBox1 ----
				checkBox1.setText("\u0417\u0430\u043a\u043e\u043d\u0447\u0435\u043d\u043e \u043e\u0431\u0443\u0447\u0435\u043d\u0438\u0435");
				checkBox1.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel5.add(checkBox1);
			}
			panel2.add(panel5);

			//======== panel12 ========
			{
				panel12.setPreferredSize(new Dimension(468, 60));
				panel12.setLayout(new FlowLayout());

				//======== panel8 ========
				{
					panel8.setBorder(new CompoundBorder(
						new TitledBorder("\u041f\u0440\u0438\u043a\u0430\u0437 \u043e \u0444\u043e\u0440\u043c\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u0438 \u0443\u0447\u0435\u0431\u043d\u043e\u0439 \u0433\u0440\u0443\u043f\u043f\u044b"),
						Borders.DLU2_BORDER));
					panel8.setLayout(new FormLayout(
						"6*(default, $lcgap), default",
						"fill:default"));

					//---- label13 ----
					label13.setText("\u041d\u043e\u043c\u0435\u0440 \u043f\u0440\u0438\u043a\u0430\u0437\u0430*");
					label13.setFont(new Font("Tahoma", Font.BOLD, 12));
					panel8.add(label13, CC.xy(1, 1));

					//---- numberPricField ----
					numberPricField.setColumns(10);
					panel8.add(numberPricField, CC.xy(3, 1));

					//---- label14 ----
					label14.setText("\u0414\u0430\u0442\u0430 \u043f\u0440\u0438\u043a\u0430\u0437\u0430");
					panel8.add(label14, CC.xy(7, 1));

					//---- datepricChooser ----
					datepricChooser.setPreferredSize(new Dimension(95, 20));
					panel8.add(datepricChooser, CC.xy(9, 1));
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
		setSize(560, 350);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel1;
	private JPanel panel3;
	private JLabel nameLabel;
	private JTextField nameField;
	private JLabel yearLabel;
	private JComboBox yearBox;
	private JLabel categLabel;
	private JComboBox categBox;
	private JLabel formEdLab;
	private JComboBox formEdBox;
	private JPanel panel2;
	private JPanel panel5;
	private JLabel birthdateLabel;
	private JDateChooser startDateChooser;
	private JLabel label11;
	private JDateChooser enddateChooser;
	private JCheckBox checkBox1;
	private JPanel panel12;
	private JPanel panel8;
	private JLabel label13;
	private JTextField numberPricField;
	private JLabel label14;
	private JDateChooser datepricChooser;
	private JPanel panel13;
	private JButton saveButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables

	
}

/*
 * Created by JFormDesigner on Sat Dec 21 01:22:56 GMT+05:00 2013
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;


import beans.Categories;

import com.jgoodies.forms.factories.*;

/**
 * @author ????????
 */
public class CategDialog extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean result = false;
	private int kategoriesid = 0;
	public CategDialog(Frame owner) {
		super(owner);
		initComponents();
		saveButton.addActionListener(this);
		saveButton.setName("OK");
		cancelButton.addActionListener(this);
		cancelButton.setName("Cancel");
	}
	
	public void setCateg(Categories st) {
		kategoriesid = st.getKategoriesid();
		nameField.setText(st.getKategoriesName());
		textField1.setText(Integer.toString(st.getTimeTheory()));
		textField2.setText(Integer.toString(st.getTimePractice()));
		textField3.setText(Integer.toString(st.getCostEducation()));
		
	}
	
	public Categories getCateg() {
		Categories st = new Categories();
		st.setKategoriesid(kategoriesid);
		st.setKategoriesName(nameField.getText());
		try {
        	int s = Integer.parseInt(textField1.getText());
            st.setTimeTheory(s);
        } catch (NumberFormatException nfe) {
        }
		try {
        	int s = Integer.parseInt(textField2.getText());
            st.setTimePractice(s);
        } catch (NumberFormatException nfe) {
        }
		try {
        	int s = Integer.parseInt(textField3.getText());
            st.setCostEducation(s);
        } catch (NumberFormatException nfe) {
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
	        if (nameField.getText().equals("") || textField1.getText().equals("") || textField2.getText().equals("")|| 
	        				textField3.getText().equals("")) {
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

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel1 = new JPanel();
		panel3 = new JPanel();
		nameLabel = new JLabel();
		nameField = new JTextField();
		yearLabel = new JLabel();
		textField1 = new JTextField();
		categLabel = new JLabel();
		textField2 = new JTextField();
		formEdLab = new JLabel();
		textField3 = new JTextField();
		panel2 = new JPanel();
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
			panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

			//======== panel3 ========
			{
				panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

				//---- nameLabel ----
				nameLabel.setText("\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435*");
				nameLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
				panel3.add(nameLabel);

				//---- nameField ----
				nameField.setColumns(10);
				panel3.add(nameField);

				//---- yearLabel ----
				yearLabel.setText("\u0427\u0430\u0441\u043e\u0432 \u0442\u0435\u043e\u0440\u0438\u0438*");
				yearLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
				panel3.add(yearLabel);

				//---- textField1 ----
				textField1.setColumns(6);
				panel3.add(textField1);
			}
			panel1.add(panel3);

			//---- categLabel ----
			categLabel.setText("\u0427\u0430\u0441\u043e\u0432 \u043f\u0440\u0430\u043a\u0442\u0438\u043a\u0438*");
			categLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			panel1.add(categLabel);

			//---- textField2 ----
			textField2.setColumns(6);
			panel1.add(textField2);

			//---- formEdLab ----
			formEdLab.setText("\u0421\u0442\u043e\u0438\u043c\u043e\u0441\u0442\u044c*");
			formEdLab.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			panel1.add(formEdLab);

			//---- textField3 ----
			textField3.setColumns(7);
			panel1.add(textField3);
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
				saveButton.setText("\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c");
				panel13.add(saveButton);

				//---- cancelButton ----
				cancelButton.setText("\u041e\u0442\u043c\u0435\u043d\u0430");
				cancelButton.setIcon(new ImageIcon(getClass().getResource("/images/logout.png")));
				panel13.add(cancelButton);
			}
			panel2.add(panel13);
		}
		contentPane.add(panel2, BorderLayout.CENTER);
		setSize(490, 200);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel1;
	private JPanel panel3;
	private JLabel nameLabel;
	private JTextField nameField;
	private JLabel yearLabel;
	private JTextField textField1;
	private JLabel categLabel;
	private JTextField textField2;
	private JLabel formEdLab;
	private JTextField textField3;
	private JPanel panel2;
	private JPanel panel13;
	private JButton saveButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

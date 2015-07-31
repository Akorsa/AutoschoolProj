/*
 * Created by JFormDesigner on Wed Dec 25 21:51:44 GMT+05:00 2013
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;
import beans.TypeUser;

import com.jgoodies.forms.factories.*;

/**
 * @author ????????
 */
public class RoleDialog extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2366596262845741694L;
	private boolean result = false;
	private int rolesId = 0;
	public RoleDialog(Frame owner) {
		super(owner);
		initComponents();
		saveButton.addActionListener(this);
		saveButton.setName("OK");
		cancelButton.addActionListener(this);
		cancelButton.setName("Cancel");
	}
	
	public void setRole(TypeUser st) {
		rolesId = st.getUsertypeId();
		nameField.setText(st.getTypeOfUserDescription());
		commentFild.setText(st.getTypeOfUserPermissions());
	}
	
	public TypeUser getRole() {
		TypeUser st = new TypeUser();
		st.setUsertypeId(rolesId);
		st.setTypeOfUserDescription(nameField.getText());
		st.setTypeOfUserPermissions(commentFild.getText());
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
	        if (nameField.getText().equals("") || commentFild.getText().equals("")) {
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
		formEdLab = new JLabel();
		commentFild = new JTextField();
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
				nameLabel.setText("\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u0440\u043e\u043b\u0438*              ");
				nameLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
				panel3.add(nameLabel);

				//---- nameField ----
				nameField.setColumns(14);
				panel3.add(nameField);
			}
			panel1.add(panel3);

			//---- formEdLab ----
			formEdLab.setText("\u041f\u043e\u043b\u043d\u043e\u043c\u043e\u0447\u0438\u044f \u0440\u043e\u043b\u0438*");
			formEdLab.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			formEdLab.setHorizontalAlignment(SwingConstants.CENTER);
			formEdLab.setPreferredSize(new Dimension(195, 15));
			panel1.add(formEdLab);

			//---- commentFild ----
			commentFild.setColumns(20);
			panel1.add(commentFild);
		}
		contentPane.add(panel1, BorderLayout.CENTER);

		//======== panel2 ========
		{
			panel2.setPreferredSize(new Dimension(1733, 70));
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
		contentPane.add(panel2, BorderLayout.SOUTH);
		setSize(490, 220);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel1;
	private JPanel panel3;
	private JLabel nameLabel;
	private JTextField nameField;
	private JLabel formEdLab;
	private JTextField commentFild;
	private JPanel panel2;
	private JPanel panel13;
	private JButton saveButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

/*
 * Created by JFormDesigner on Wed Dec 25 18:06:08 GMT+05:00 2013
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;

import util.HashUtil;

import beans.TypeUser;
import beans.Users;

import com.jgoodies.forms.factories.*;

/**
 * @author ????????
 */
public class UserDialog extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -466998834421409966L;
	private boolean result = false;
	private int userId = 0;
	public UserDialog(Frame owner, List<TypeUser> list) {
		super(owner);
		initComponents(list);
		saveButton.addActionListener(this);
		saveButton.setName("OK");
		cancelButton.addActionListener(this);
		cancelButton.setName("Cancel");
		
	}
	
	public void setUser(Users st) {
		userId = st.getUserid();
		loginField.setText(st.getLogin());
		nameFild.setText(st.getName());
		passFild.setText(st.getPassword());
		commentFild.setText(st.getComment());
		for (int i = 0; i < roleBox.getModel().getSize(); i++) {
			TypeUser g = (TypeUser) roleBox.getModel().getElementAt(i);
            if (g.getUsertypeId()== st.getTypeUser().getUsertypeId()) {
            	roleBox.setSelectedIndex(i);
                break;
            }
        }	
	}
	
	// ¬ернуть данные в виде новой ставки с соответствующими пол€ми
	public Users getUser() {
		Users st = new Users();
		st.setUserid(userId);
		st.setLogin(loginField.getText());
		st.setName(nameFild.getText());
		st.setPassword(HashUtil.simpleHash(new String(passFild.getText())));
		st.setComment(commentFild.getText());
		st.setTypeUser((TypeUser)roleBox.getSelectedItem());
		return st;
	}
	
	public boolean getResult() {
        return result;
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
        // ƒобавл€ем ставку, но не закрываем окно
		if (src.getName().equals("OK")) {
	        if (loginField.getText().equals("") || nameFild.getText().equals("") || passFild.getText().equals("")) {
	        	JOptionPane.showMessageDialog(this, "«аполните все об€зательные пол€, помеченные звездочкой!", "¬нимание", 2);	
	        	
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


	private void initComponents(List<TypeUser> list) {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel1 = new JPanel();
		panel3 = new JPanel();
		nameLabel = new JLabel();
		loginField = new JTextField();
		categLabel = new JLabel();
		passFild = new JTextField();
		yearLabel = new JLabel();
		nameFild = new JTextField();
		formEdLab = new JLabel();
		roleBox = new JComboBox(new Vector<TypeUser>(list));
		label1 = new JLabel();
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
				nameLabel.setText("\u041b\u043e\u0433\u0438\u043d*");
				nameLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
				panel3.add(nameLabel);

				//---- loginField ----
				loginField.setColumns(10);
				panel3.add(loginField);
			}
			panel1.add(panel3);

			//---- categLabel ----
			categLabel.setText("\u041f\u0430\u0440\u043e\u043b\u044c* ");
			categLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			panel1.add(categLabel);

			//---- passFild ----
			passFild.setColumns(12);
			panel1.add(passFild);

			//---- yearLabel ----
			yearLabel.setText("\u0418\u043c\u044f \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f*");
			yearLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			panel1.add(yearLabel);

			//---- nameFild ----
			nameFild.setColumns(10);
			panel1.add(nameFild);

			//---- formEdLab ----
			formEdLab.setText("\u0420\u043e\u043b\u044c*");
			formEdLab.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			panel1.add(formEdLab);

			//---- roleBox ----
			roleBox.setPreferredSize(new Dimension(130, 20));
			panel1.add(roleBox);

			//---- label1 ----
			label1.setText("\u041a\u043e\u043c\u043c\u0435\u043d\u0442\u0430\u0440\u0438\u0439");
			label1.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			panel1.add(label1);

			//---- commentFild ----
			commentFild.setColumns(15);
			panel1.add(commentFild);
		}
		contentPane.add(panel1, BorderLayout.CENTER);

		//======== panel2 ========
		{
			panel2.setPreferredSize(new Dimension(400, 70));
			panel2.setLayout(new FlowLayout());

			//======== panel13 ========
			{
				panel13.setPreferredSize(new Dimension(530, 70));
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
		contentPane.add(panel2, BorderLayout.PAGE_END);
		setSize(500, 240);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel1;
	private JPanel panel3;
	private JLabel nameLabel;
	private JTextField loginField;
	private JLabel categLabel;
	private JTextField passFild;
	private JLabel yearLabel;
	private JTextField nameFild;
	private JLabel formEdLab;
	private JComboBox roleBox;
	private JLabel label1;
	private JTextField commentFild;
	private JPanel panel2;
	private JPanel panel13;
	private JButton saveButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

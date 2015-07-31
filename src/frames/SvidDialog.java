/*
 * Created by JFormDesigner on Tue Jan 07 14:18:59 GMT+05:00 2014
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.Format;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.border.*;

import rmi.AutoschoolRmiInterface;
import beans.Students;

import com.jgoodies.forms.factories.*;
import com.toedter.calendar.*;

/**
 * @author ????????
 */
public class SvidDialog extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1342962568596876210L;
	private AutoschoolRmiInterface ari; 
	private Students st = null;
	private boolean result = false;
	public SvidDialog(Frame owner, Students s, AutoschoolRmiInterface ari) {
		super(owner);
		initComponents();
		this.ari = ari;
		this.st = s;
		label1.setText(s.getSurname() + " " + s.getName() + " " + s.getPatronymicname());
		saveButton.addActionListener(this);
		saveButton.setName("OK");
		cancelButton.addActionListener(this);
		cancelButton.setName("Cancel");
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
        // Добавляем ставку, но не закрываем окно
		if (src.getName().equals("OK")) {
	        if (seriaField.getText().equals("") || dateChooser1.getDate()== null ||  numField.getText().equals("")) {
	        	JOptionPane.showMessageDialog(this, "Заполните все обязательные поля, помеченные звездочкой!", "Внимание", 2);	
	        	
	        }else {
	        	Format formatter = new SimpleDateFormat("dd.MM.yyyy");
	        	st.setSvidInfo(seriaField.getText() + " " + numField.getText() + " от " +formatter.format(dateChooser1.getDate()));
	        	try {
					ari.updateStudent(st);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        	result = true;
	        	dispose();
	        }
		}
	    if (src.getName().equals("Cancel")) {
	        dispose();
	    }
		
	}
	
	public boolean getResult() {
        return result;
    }

	

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel1 = new JPanel();
		panel4 = new JPanel();
		label1 = new JLabel();
		panel5 = new JPanel();
		panel3 = new JPanel();
		nameLabel = new JLabel();
		seriaField = new JTextField();
		yearLabel = new JLabel();
		numField = new JTextField();
		categLabel = new JLabel();
		dateChooser1 = new JDateChooser();
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
			panel1.setPreferredSize(new Dimension(605, 110));
			panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

			//======== panel4 ========
			{
				panel4.setMinimumSize(new Dimension(350, 20));
				panel4.setPreferredSize(new Dimension(350, 30));
				panel4.setLayout(new FlowLayout());

				//---- label1 ----
				label1.setText("\u0440\u0440\u0440\u0440");
				label1.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
				panel4.add(label1);
			}
			panel1.add(panel4);

			//======== panel5 ========
			{
				panel5.setPreferredSize(new Dimension(350, 10));
				panel5.setLayout(new FlowLayout());
			}
			panel1.add(panel5);

			//======== panel3 ========
			{
				panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

				//---- nameLabel ----
				nameLabel.setText("\u0421\u0435\u0440\u0438\u044f*");
				nameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel3.add(nameLabel);

				//---- seriaField ----
				seriaField.setColumns(10);
				panel3.add(seriaField);

				//---- yearLabel ----
				yearLabel.setText("\u2116*");
				yearLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel3.add(yearLabel);

				//---- numField ----
				numField.setColumns(9);
				panel3.add(numField);
			}
			panel1.add(panel3);

			//---- categLabel ----
			categLabel.setText("\u0414\u0430\u0442\u0430 \u0432\u044b\u0434\u0430\u0447\u0438*");
			categLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			panel1.add(categLabel);

			//---- dateChooser1 ----
			dateChooser1.setPreferredSize(new Dimension(95, 20));
			panel1.add(dateChooser1);
		}
		contentPane.add(panel1, BorderLayout.NORTH);

		//======== panel2 ========
		{
			panel2.setPreferredSize(new Dimension(1733, 50));
			panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));

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
		setSize(618, 224);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel1;
	private JPanel panel4;
	private JLabel label1;
	private JPanel panel5;
	private JPanel panel3;
	private JLabel nameLabel;
	private JTextField seriaField;
	private JLabel yearLabel;
	private JTextField numField;
	private JLabel categLabel;
	private JDateChooser dateChooser1;
	private JPanel panel2;
	private JPanel panel13;
	private JButton saveButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

/*
 * Created by JFormDesigner on Tue Jan 07 23:26:41 GMT+05:00 2014
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.*;
import com.jgoodies.forms.factories.*;
import com.toedter.calendar.*;

/**
 * @author ????????
 */
public class EkzDataDialog extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2780685436434889258L;
	private boolean result = false;
	public EkzDataDialog(Frame owner) {
		super(owner);
		initComponents();
		saveButton.addActionListener(this);
		saveButton.setName("OK");
		cancelButton.addActionListener(this);
		cancelButton.setName("Cancel");
	}
	
	public Date getDate() {
		return dateChooser1.getDate();
	}
	
	public boolean getResult() {
        return result;
    }
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
        // Добавляем ставку, но не закрываем окно
		if (src.getName().equals("OK")) {
	        if ( dateChooser1.getDate()== null) {
	        	JOptionPane.showMessageDialog(this, "Заполните бязательное поле, помеченное звездочкой!", "Внимание", 2);	
	        	
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
		dateChooser1 = new JDateChooser();
		panel2 = new JPanel();
		panel13 = new JPanel();
		saveButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("\u0414\u0430\u0442\u0430 \u0441\u0434\u0430\u0447\u0438 \u044d\u043a\u0437\u0430\u043c\u0435\u043d\u0430");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== panel1 ========
		{
			panel1.setBorder(new CompoundBorder(
				new TitledBorder(" "),
				Borders.DLU2_BORDER));
			panel1.setPreferredSize(new Dimension(605, 80));
			panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

			//======== panel3 ========
			{
				panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

				//---- nameLabel ----
				nameLabel.setText("\u0414\u0430\u0442\u0430 \u0441\u0434\u0430\u0447\u0438 \u0432\u043d\u0443\u0442. \u044d\u043a\u0437\u0430\u043c\u0435\u043d\u0430*");
				nameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel3.add(nameLabel);
			}
			panel1.add(panel3);

			//---- dateChooser1 ----
			dateChooser1.setPreferredSize(new Dimension(95, 20));
			panel1.add(dateChooser1);
		}
		contentPane.add(panel1, BorderLayout.NORTH);

		//======== panel2 ========
		{
			panel2.setPreferredSize(new Dimension(1733, 50));
			panel2.setLayout(new FlowLayout());

			//======== panel13 ========
			{
				panel13.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 5));

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
		setSize(355, 190);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel1;
	private JPanel panel3;
	private JLabel nameLabel;
	private JDateChooser dateChooser1;
	private JPanel panel2;
	private JPanel panel13;
	private JButton saveButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

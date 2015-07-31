/*
 * Created by JFormDesigner on Tue Dec 17 18:49:10 GMT+05:00 2013
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;

import javax.swing.*;
import javax.swing.border.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import com.toedter.calendar.*;

/**
 * @author ????????
 */
public class findStudDialog extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7546206624519359743L;
	private boolean result = false;
	private int number = 0;
	public findStudDialog(Frame owner) {
		super(owner);
		initComponents();
		number = 0;
		nomsvidFild.setEditable(false);
		dateChooser1.setEnabled(false);
		famFild.setEditable(true);
		nameFild.setEditable(true);
		otchFild.setEditable(true);
		findCombo.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		    	JComboBox cb = (JComboBox)e.getSource();
		    	int i = cb.getSelectedIndex();
		    	if (i == 0) {
		    		number = 0;
		    		nomsvidFild.setEditable(false);
		    		dateChooser1.setEnabled(false);
		    		famFild.setEditable(true);
		    		nameFild.setEditable(true);
		    		otchFild.setEditable(true);
		    		
		    	} 
		    	if (i == 1) {
		    		number = 1;
		    		nomsvidFild.setEditable(true);
		    		dateChooser1.setEnabled(false);
		    		famFild.setEditable(false);
		    		nameFild.setEditable(false);
		    		otchFild.setEditable(false);
		    	}
		    	if (i == 2) {
		    		number = 2;
		    		nomsvidFild.setEditable(false);
		    		dateChooser1.setEnabled(true);
		    		famFild.setEditable(false);
		    		nameFild.setEditable(false);
		    		otchFild.setEditable(false);
		    	}
		    }});
		findButton.addActionListener(this);
		findButton.setName("OK");
		cancelButton.addActionListener(this);
		cancelButton.setName("Cancel");
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton src = (JButton) e.getSource();
		if (src.getName().equals("OK")) {
			result = true;
            }
        if (src.getName().equals("Cancel")) {
            result = false;
            dispose();
        }
        setVisible(false);
		
	}
	
	public String[] getString () {
		String[] s = { nameFild.getText() , famFild.getText(), otchFild.getText()};
		return s;
	}
	
	public String getNomSvid () {
		String number1 = nomsvidFild.getText();
		return number1;
	}
	
	public Date getDate () {
		Date date = dateChooser1.getDate();
		return date;
	}
	
	public boolean getResult() {
        return result;
    }
	public int getNumber() {
		return number;
	}
	
	


	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		dialogPane = new JPanel();
		buttonBar = new JPanel();
		findButton = new JButton();
		cancelButton = new JButton();
		panel3 = new JPanel();
		label3 = new JLabel();
		String[] petStrings = { "ФИО", "№ свидетельства", "Дата рождения"};
		findCombo = new JComboBox(petStrings);
		label4 = new JLabel();
		famFild = new JTextField();
		label7 = new JLabel();
		nomsvidFild = new JTextField();
		label5 = new JLabel();
		nameFild = new JTextField();
		label8 = new JLabel();
		dateChooser1 = new JDateChooser();
		label6 = new JLabel();
		otchFild = new JTextField();

		//======== this ========
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("\u041f\u043e\u0438\u0441\u043a \u0443\u0447\u0430\u0449\u0435\u0433\u043e\u0441\u044f");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== dialogPane ========
		{
			dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));
			dialogPane.setLayout(new BorderLayout());

			//======== buttonBar ========
			{
				buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
				buttonBar.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 5));

				//---- findButton ----
				findButton.setText("\u041d\u0430\u0439\u0442\u0438");
				findButton.setIcon(new ImageIcon(getClass().getResource("/images/Search.png")));
				buttonBar.add(findButton);

				//---- cancelButton ----
				cancelButton.setText("\u041e\u0442\u043c\u0435\u043d\u0430");
				cancelButton.setIcon(new ImageIcon(getClass().getResource("/images/logout.png")));
				buttonBar.add(cancelButton);
			}
			dialogPane.add(buttonBar, BorderLayout.SOUTH);

			//======== panel3 ========
			{
				panel3.setLayout(new FormLayout(
					"5*(default, $lcgap), default",
					"4*(default, $lgap), default"));

				//---- label3 ----
				label3.setText("\u0423\u0441\u043b\u043e\u0432\u0438\u0435 \u043f\u043e\u0438\u0441\u043a\u0430");
				panel3.add(label3, CC.xy(3, 1));

				//---- findCombo ----
				findCombo.setPreferredSize(new Dimension(95, 20));
				panel3.add(findCombo, CC.xy(5, 1));

				//---- label4 ----
				label4.setText("\u0424\u0430\u043c\u0438\u043b\u0438\u044f");
				panel3.add(label4, CC.xy(3, 3));

				//---- famFild ----
				famFild.setColumns(10);
				panel3.add(famFild, CC.xy(5, 3));

				//---- label7 ----
				label7.setText("\u2116 \u0441\u0432\u0438\u0434. \u043e\u0431 \u043e\u043a\u043e\u043d\u0447\u0430\u043d\u0438\u0438");
				panel3.add(label7, CC.xy(7, 3));

				//---- nomsvidFild ----
				nomsvidFild.setColumns(8);
				panel3.add(nomsvidFild, CC.xy(9, 3));

				//---- label5 ----
				label5.setText("\u0418\u043c\u044f");
				panel3.add(label5, CC.xy(3, 5));

				//---- nameFild ----
				nameFild.setColumns(10);
				panel3.add(nameFild, CC.xy(5, 5));

				//---- label8 ----
				label8.setText("\u0414\u0430\u0442\u0430 \u0440\u043e\u0436\u0434\u0435\u043d\u0438\u044f");
				panel3.add(label8, CC.xy(7, 5));
				panel3.add(dateChooser1, CC.xy(9, 5));

				//---- label6 ----
				label6.setText("\u041e\u0442\u0447\u0435\u0441\u0442\u0432\u043e");
				panel3.add(label6, CC.xy(3, 7));

				//---- otchFild ----
				otchFild.setColumns(10);
				panel3.add(otchFild, CC.xy(5, 7));
			}
			dialogPane.add(panel3, BorderLayout.CENTER);
		}
		contentPane.add(dialogPane, BorderLayout.CENTER);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel dialogPane;
	private JPanel buttonBar;
	private JButton findButton;
	private JButton cancelButton;
	private JPanel panel3;
	private JLabel label3;
	private JComboBox findCombo;
	private JLabel label4;
	private JTextField famFild;
	private JLabel label7;
	private JTextField nomsvidFild;
	private JLabel label5;
	private JTextField nameFild;
	private JLabel label8;
	private JDateChooser dateChooser1;
	private JLabel label6;
	private JTextField otchFild;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	
}

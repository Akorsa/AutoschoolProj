/*
 * Created by JFormDesigner on Sun Jan 19 00:46:40 GMT+05:00 2014
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;

import rmi.AutoschoolRmiInterface;
import beans.Groups;

import com.jgoodies.forms.factories.*;
import com.toedter.calendar.*;

/**
 * @author ????????
 */
public class DriveDateDialog extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4196259153044574509L;
	private AutoschoolRmiInterface ari;
	private Groups g = null;
	private ZavAvtodeloFrame zavAvtodeloFrame;
	private ZamDirectURFrame zamDirectURFrame;
	private int t;

	public DriveDateDialog(ZavAvtodeloFrame zavAvtodeloFrame,ZamDirectURFrame zamDirectURFrame,
			AutoschoolRmiInterface ari, Groups gr, int i) {
		// super(owner);
		initComponents();
		this.ari = ari;
		this.g = gr;
		this.t = i;
		if (t==1){
			this.zavAvtodeloFrame = zavAvtodeloFrame;
		}
		if (t==2){
			this.zamDirectURFrame = zamDirectURFrame;
		}
		
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
			if (dateChooser1.getDate()== null
					|| dateChooser2.getDate()== null) {
				JOptionPane
						.showMessageDialog(
								this,
								"Заполните все обязательные поля, помеченные звездочкой!",
								"Внимание", 2);

			} else {
				if (t == 1){
					DriveListFrame rlf = new DriveListFrame(zavAvtodeloFrame, ari, g,
							dateChooser1.getDate(), dateChooser2.getDate());
					//rlf.setVisible(true);
					this.dispose();
				}
				if (t == 2){
					RaspListFrame rlf = new RaspListFrame(zamDirectURFrame, ari, g,
							dateChooser1.getDate(), dateChooser2.getDate());
					//rlf.setVisible(true);
					this.dispose();
				}
				
			}
			

		}
		if (src.getName().equals("Cancel")) {
			dispose();
		}

	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		panel1 = new JPanel();
		panel3 = new JPanel();
		nameLabel = new JLabel();
		dateChooser1 = new JDateChooser();
		categLabel = new JLabel();
		dateChooser2 = new JDateChooser();
		panel2 = new JPanel();
		panel13 = new JPanel();
		saveButton = new JButton();
		cancelButton = new JButton();

		// ======== this ========
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("\u0412\u044b\u0431\u0440\u0430\u0442\u044c \u043f\u0435\u0440\u0438\u043e\u0434");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		// ======== panel1 ========
		{
			panel1.setBorder(new CompoundBorder(new TitledBorder(" "),
					Borders.DLU2_BORDER));
			panel1.setPreferredSize(new Dimension(605, 80));
			panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

			// ======== panel3 ========
			{
				panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

				// ---- nameLabel ----
				nameLabel.setText("\u041d\u0430\u0447\u0430\u043b\u043e*");
				nameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel3.add(nameLabel);
			}
			panel1.add(panel3);

			// ---- dateChooser1 ----
			dateChooser1.setPreferredSize(new Dimension(95, 20));
			panel1.add(dateChooser1);

			// ---- categLabel ----
			categLabel.setText("\u041a\u043e\u043d\u0435\u0446*");
			categLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			panel1.add(categLabel);

			// ---- dateChooser2 ----
			dateChooser2.setPreferredSize(new Dimension(95, 20));
			panel1.add(dateChooser2);
		}
		contentPane.add(panel1, BorderLayout.NORTH);

		// ======== panel2 ========
		{
			panel2.setPreferredSize(new Dimension(1733, 50));
			panel2.setLayout(new FlowLayout());

			// ======== panel13 ========
			{
				panel13.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 5));

				// ---- saveButton ----
				saveButton.setSelectedIcon(new ImageIcon(getClass()
						.getResource("/images/Nnm 16/check.png")));
				saveButton.setIcon(new ImageIcon(getClass().getResource(
						"/images/check.png")));
				saveButton
						.setText("\u0412\u044b\u0431\u0440\u0430\u0442\u044c");
				panel13.add(saveButton);

				// ---- cancelButton ----
				cancelButton.setText("\u041e\u0442\u043c\u0435\u043d\u0430");
				cancelButton.setIcon(new ImageIcon(getClass().getResource(
						"/images/logout.png")));
				panel13.add(cancelButton);
			}
			panel2.add(panel13);
		}
		contentPane.add(panel2, BorderLayout.CENTER);
		setSize(490, 180);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	private JPanel panel1;
	private JPanel panel3;
	private JLabel nameLabel;
	private JDateChooser dateChooser1;
	private JLabel categLabel;
	private JDateChooser dateChooser2;
	private JPanel panel2;
	private JPanel panel13;
	private JButton saveButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}

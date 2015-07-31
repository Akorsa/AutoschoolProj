/*
 * Created by JFormDesigner on Wed Jan 22 11:35:46 GMT+05:00 2014
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;

import org.jfree.ui.RefineryUtilities;

import rmi.AutoschoolRmiInterface;
import beans.Groups;

import com.jgoodies.forms.factories.*;
import com.toedter.calendar.*;

/**
 * @author ????????
 */
public class DriveStatDate extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7204097583210067403L;
	private AutoschoolRmiInterface ari;

	public DriveStatDate(ZamDirectPRFrame owner,  AutoschoolRmiInterface ari, List<Groups> list) {
		super(owner);
		this.ari = ari;
		
		
		initComponents(list);
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
					Groups g = (Groups)comboBox1.getSelectedItem();
					List students = null;
					Date date1 = dateChooser1.getDate();
					Date date2 = dateChooser2.getDate();
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
					try {
						students = ari.getDriveStat(g.getGroupid(), date1, date2);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if(!students.isEmpty()) {
						final BarChartDemo demo = new BarChartDemo("Статистика по вождению за период с "+ dateFormat.format(date1) +" по "+dateFormat.format(date2),students,date1,date2);
						demo.setSize(644,364);
						RefineryUtilities.centerFrameOnScreen(demo);
						demo.setVisible(true);
						this.dispose();	
					} else {
						JOptionPane.showMessageDialog(DriveStatDate.this,
		                         "Записи в данном интервале времени не найдены!", "Внимание", 2);
					}
					
			}
			

		}
		if (src.getName().equals("Cancel")) {
			dispose();
		}

	}


	private void initComponents(List<Groups> list2) {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel1 = new JPanel();
		label1 = new JLabel();
		comboBox1 = new JComboBox(new Vector<Groups>(list2));
		panel3 = new JPanel();
		nameLabel = new JLabel();
		dateChooser1 = new JDateChooser();
		panel4 = new JPanel();
		categLabel = new JLabel();
		dateChooser2 = new JDateChooser();
		panel2 = new JPanel();
		panel13 = new JPanel();
		saveButton = new JButton();
		cancelButton = new JButton();

		//======== this ========
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("\u0412\u044b\u0431\u0440\u0430\u0442\u044c \u043f\u0435\u0440\u0438\u043e\u0434");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== panel1 ========
		{
			panel1.setBorder(new CompoundBorder(
				new TitledBorder(" "),
				Borders.DLU2_BORDER));
			panel1.setPreferredSize(new Dimension(605, 110));
			panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

			//---- label1 ----
			label1.setText("\u0413\u0440\u0443\u043f\u043f\u0430*");
			label1.setFont(new Font("Tahoma", Font.BOLD, 12));
			panel1.add(label1);

			//---- comboBox1 ----
			comboBox1.setMinimumSize(new Dimension(100, 18));
			comboBox1.setPreferredSize(new Dimension(100, 20));
			panel1.add(comboBox1);

			//======== panel3 ========
			{
				panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

				//---- nameLabel ----
				nameLabel.setText("\u041d\u0430\u0447\u0430\u043b\u043e*");
				nameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel3.add(nameLabel);
			}
			panel1.add(panel3);

			//---- dateChooser1 ----
			dateChooser1.setPreferredSize(new Dimension(95, 20));
			panel1.add(dateChooser1);

			//======== panel4 ========
			{
				panel4.setLayout(new FlowLayout());

				//---- categLabel ----
				categLabel.setText("\u041a\u043e\u043d\u0435\u0446*");
				categLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel4.add(categLabel);

				//---- dateChooser2 ----
				dateChooser2.setPreferredSize(new Dimension(95, 20));
				panel4.add(dateChooser2);
			}
			panel1.add(panel4);
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
				saveButton.setText("\u0412\u044b\u0431\u0440\u0430\u0442\u044c");
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
	private JLabel label1;
	private JComboBox comboBox1;
	private JPanel panel3;
	private JLabel nameLabel;
	private JDateChooser dateChooser1;
	private JPanel panel4;
	private JLabel categLabel;
	private JDateChooser dateChooser2;
	private JPanel panel2;
	private JPanel panel13;
	private JButton saveButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

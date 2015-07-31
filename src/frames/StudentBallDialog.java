/*
 * Created by JFormDesigner on Wed Jan 22 13:27:52 GMT+05:00 2014
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
import beans.Students;
import beans.Tests;

import com.jgoodies.forms.factories.*;

/**
 * @author ????????
 */
public class StudentBallDialog extends JDialog implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4718181180776390574L;
	private AutoschoolRmiInterface ari;

	public StudentBallDialog(Frame owner, AutoschoolRmiInterface ari,
			List<Students> lst) {
		super(owner);
		initComponents(lst);
		this.ari = ari;
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

			Students g = (Students) comboBox1.getSelectedItem();

			List<Tests> tst = null;
			//SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			try {
				tst = ari.getStudStat(g.getIdstudent());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (!tst.isEmpty()) {
				final LineChartDemo1 demo = new LineChartDemo1(
						"Статистика по тестированиям учащегося: "+ g.getSurname()+" " + g.getName(), tst);
				demo.setSize(850, 491);
				RefineryUtilities.centerFrameOnScreen(demo);
				demo.setVisible(true);
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(StudentBallDialog.this,
						"Записи по тестированиям не найдены! Выберите другого учащегося",
						"Внимание", 2);
			}

		}

		if (src.getName().equals("Cancel")) {
			dispose();
		}

	}

	private void initComponents(List<Students> lst) {
		// JFormDesigner - Component initialization - DO NOT MODIFY
		// //GEN-BEGIN:initComponents
		panel1 = new JPanel();
		panel3 = new JPanel();
		nameLabel = new JLabel();
		comboBox1 = new JComboBox(new Vector<Students>(lst));
		panel2 = new JPanel();
		panel13 = new JPanel();
		saveButton = new JButton();
		cancelButton = new JButton();

		// ======== this ========
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("\u0412\u044b\u0431\u0435\u0440\u0438\u0442\u0435 \u0443\u0447\u0430\u0449\u0435\u0433\u043e\u0441\u044f");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		// ======== panel1 ========
		{
			panel1.setBorder(new CompoundBorder(new TitledBorder(" "),
					Borders.DLU2_BORDER));
			panel1.setPreferredSize(new Dimension(605, 0));
			panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

			// ======== panel3 ========
			{
				panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

				// ---- nameLabel ----
				nameLabel
						.setText("\u0423\u0447\u0430\u0449\u0438\u0439\u0441\u044f*     ");
				nameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
				panel3.add(nameLabel);

				// ---- comboBox1 ----
				comboBox1.setPreferredSize(new Dimension(180, 20));
				comboBox1.setMaximumRowCount(50);
				panel3.add(comboBox1);
			}
			panel1.add(panel3);
		}
		contentPane.add(panel1, BorderLayout.CENTER);

		// ======== panel2 ========
		{
			panel2.setPreferredSize(new Dimension(1733, 70));
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
						.setText("\u0421\u043e\u0445\u0440\u0430\u043d\u0438\u0442\u044c");
				panel13.add(saveButton);

				// ---- cancelButton ----
				cancelButton.setText("\u041e\u0442\u043c\u0435\u043d\u0430");
				cancelButton.setIcon(new ImageIcon(getClass().getResource(
						"/images/logout.png")));
				panel13.add(cancelButton);
			}
			panel2.add(panel13);
		}
		contentPane.add(panel2, BorderLayout.SOUTH);
		setSize(490, 185);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization
		// //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY
	// //GEN-BEGIN:variables
	private JPanel panel1;
	private JPanel panel3;
	private JLabel nameLabel;
	private JComboBox comboBox1;
	private JPanel panel2;
	private JPanel panel13;
	private JButton saveButton;
	private JButton cancelButton;
	// JFormDesigner - End of variables declaration //GEN-END:variables
}

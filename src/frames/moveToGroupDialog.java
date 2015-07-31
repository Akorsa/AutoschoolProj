/*
 * Created by JFormDesigner on Mon Dec 16 18:47:49 GMT+05:00 2013
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.*;

import rmi.AutoschoolRmiInterface;

import beans.Groups;
import beans.Students;
import beans.YearOfEducation;

import com.jgoodies.forms.factories.*;

/**
 * @author ????????
 */
public class moveToGroupDialog extends JDialog implements ActionListener{

	private static final long serialVersionUID = 8449528492001126608L;
	private int studId = 0;
	private AutoschoolRmiInterface ari; 
	private boolean result = false;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Component c = (Component) e.getSource();
		/*if (c.getName().equals("grupCombo")) {
	        try {
				loadGrups();
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}     
	    }*/
		if (c.getName().equals("OK")) {
			if (grupCombo.getSelectedItem() == null) {
	        	JOptionPane.showMessageDialog(this, "Заполните поле группы!", "Внимание", 2);	
	        	
	        }else {
	        	result = true;
	        	dispose();
	        }
	           
	    }
		if (c.getName().equals("Cancel")) {
	        result = false;
	        dispose();
	    }
		
	}
	
	
	private void loadGrups() throws RemoteException {
		YearOfEducation year = (YearOfEducation)yearCombo.getSelectedItem();
		List<Groups> grup = ari.getGroupYearEdu(year.getNumberOfYear());
		if( grup.isEmpty()){ System.out.println("Fucking shit");}
		grupCombo.removeAllItems();
		for (Groups gr:grup){
			grupCombo.addItem(gr);
			System.out.println(gr.getGroupName());
		}
	}


	public moveToGroupDialog(Frame owner,List<YearOfEducation> list,AutoschoolRmiInterface ar) {
		super(owner);
		initComponents(list);
		button1.addActionListener(this);
		button1.setName("OK");
		button2.addActionListener(this);
		button2.setName("Cancel");
		this.ari = ar;
		try {
			loadGrups();
		} catch (RemoteException e2) {
			e2.printStackTrace();
		}
		yearCombo.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		    	try {
					loadGrups();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }});
		grupCombo.setName("grupCombo");
		
		
		
	}
	
	public void setStudent(Students st) {
		studId = st.getIdstudent();
		studentLabel.setText(st.getName() + " " + st.getSurname());
		Groups gr = st.getGroups();
		curGrupLabel.setText(gr.getGroupName());
		curYearLabel.setText(gr.getYearOfEducation().getNumberOfYear());
	}
	
	public Groups getGroupsStudents() {
		
        return (Groups)grupCombo.getSelectedItem();
    }
    
	public Integer getStId() {
		
        return studId;
    }
	
	public boolean getResult() {
        return result;
    }

	private void initComponents(List<YearOfEducation> list) {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel1 = new JPanel();
		studentLabel = new JLabel();
		panel6 = new JPanel();
		panel3 = new JPanel();
		label1 = new JLabel();
		curYearLabel = new JLabel();
		label3 = new JLabel();
		curGrupLabel = new JLabel();
		panel4 = new JPanel();
		label5 = new JLabel();
		yearCombo = new JComboBox(new Vector<YearOfEducation>(list));
		label7 = new JLabel();
		grupCombo = new JComboBox();
		panel2 = new JPanel();
		button1 = new JButton();
		button2 = new JButton();

		//======== this ========
		setTitle("\u041f\u0435\u0440\u0435\u0432\u043e\u0434 \u0432 \u0434\u0440\u0443\u0433\u0443\u044e \u0433\u0440\u0443\u043f\u043f\u0443");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== panel1 ========
		{
			panel1.setLayout(new FlowLayout());

			//---- studentLabel ----
			studentLabel.setText("text");
			studentLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
			panel1.add(studentLabel);
		}
		contentPane.add(panel1, BorderLayout.NORTH);

		//======== panel6 ========
		{
			panel6.setPreferredSize(new Dimension(180, 140));
			panel6.setLayout(new BoxLayout(panel6, BoxLayout.Y_AXIS));

			//======== panel3 ========
			{
				panel3.setBorder(new CompoundBorder(
					new TitledBorder("\u0422\u0435\u043a\u0443\u0449\u0430\u044f \u0433\u0440\u0443\u043f\u043f\u0430"),
					Borders.DLU2_BORDER));
				panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 5));

				//---- label1 ----
				label1.setText("\u0413\u043e\u0434:");
				panel3.add(label1);

				//---- curYearLabel ----
				curYearLabel.setText("text");
				panel3.add(curYearLabel);

				//---- label3 ----
				label3.setText("\u0413\u0440\u0443\u043f\u043f\u0430:");
				panel3.add(label3);

				//---- curGrupLabel ----
				curGrupLabel.setText("text");
				panel3.add(curGrupLabel);
			}
			panel6.add(panel3);

			//======== panel4 ========
			{
				panel4.setBorder(new CompoundBorder(
					new TitledBorder("\u041f\u0435\u0440\u0435\u0432\u0435\u0441\u0442\u0438 \u0432..."),
					Borders.DLU2_BORDER));
				panel4.setLayout(new FlowLayout());

				//---- label5 ----
				label5.setText("\u0413\u043e\u0434:");
				panel4.add(label5);

				//---- yearCombo ----
				yearCombo.setPreferredSize(new Dimension(64, 20));
				panel4.add(yearCombo);

				//---- label7 ----
				label7.setText("\u0413\u0440\u0443\u043f\u043f\u0430:");
				panel4.add(label7);

				//---- grupCombo ----
				grupCombo.setPreferredSize(new Dimension(64, 20));
				panel4.add(grupCombo);
			}
			panel6.add(panel4);
		}
		contentPane.add(panel6, BorderLayout.CENTER);

		//======== panel2 ========
		{
			panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 35, 5));

			//---- button1 ----
			button1.setText("\u041f\u0435\u0440\u0435\u0432\u0435\u0441\u0442\u0438");
			button1.setIcon(new ImageIcon(getClass().getResource("/images/Nnm 16/communication.png")));
			panel2.add(button1);

			//---- button2 ----
			button2.setText("\u041e\u0442\u043c\u0435\u043d\u0430");
			button2.setIcon(new ImageIcon(getClass().getResource("/images/Nnm 16/logout.png")));
			panel2.add(button2);
		}
		contentPane.add(panel2, BorderLayout.SOUTH);
		pack();
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel1;
	private JLabel studentLabel;
	private JPanel panel6;
	private JPanel panel3;
	private JLabel label1;
	private JLabel curYearLabel;
	private JLabel label3;
	private JLabel curGrupLabel;
	private JPanel panel4;
	private JLabel label5;
	private JComboBox yearCombo;
	private JLabel label7;
	private JComboBox grupCombo;
	private JPanel panel2;
	private JButton button1;
	private JButton button2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	
}

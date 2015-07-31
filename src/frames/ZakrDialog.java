/*
 * Created by JFormDesigner on Sat Jan 11 23:07:32 GMT+05:00 2014
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
import beans.LinkTeacherSubject;
import beans.Subjects;
import beans.Teachers;


import com.jgoodies.forms.factories.*;

/**
 * @author ????????
 */
public class ZakrDialog extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8807770543682428393L;
	private AutoschoolRmiInterface ari;
	private int id;
	private boolean result = false;
	public ZakrDialog(Frame owner, AutoschoolRmiInterface ari) {
		super(owner);
		this.ari = ari;
		initComponents(loadTeachers(),loadSubjects());
		button1.addActionListener(this);
		button1.setName("OK");
		button2.addActionListener(this);
		button2.setName("Cancel");
	}


	private List<Teachers> loadTeachers() {
		List<Teachers> stud = null;
		//List<Questions> qst = ari.getQuestionsByTheme(id);
		try {
			stud = (List<Teachers>) ari.getTeacherList();
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
	}
	
	private List<Subjects> loadSubjects() {
		List<Subjects> stud = null;
		//List<Questions> qst = ari.getQuestionsByTheme(id);
		try {
			stud = (List<Subjects>) ari.getSubjectList();
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
	}
	
	public void setZakr(LinkTeacherSubject st) {
		id = st.getId();
		for (int i = 0; i < teacherBox.getModel().getSize(); i++) {
			Teachers g = (Teachers) teacherBox.getModel().getElementAt(i);
            if (g.getPrepodid() == st.getTeachers().getPrepodid()) {
            	teacherBox.setSelectedIndex(i);
                break;
            }
        }	
		for (int i = 0; i < subjectBox.getModel().getSize(); i++) {
			Subjects g = (Subjects) subjectBox.getModel().getElementAt(i);
            if (g.getSubjectId() == st.getSubjects().getSubjectId()) {
            	subjectBox.setSelectedIndex(i);
                break;
            }
        }	
	}
	
	// ¬ернуть данные в виде новой ставки с соответствующими пол€ми
	public LinkTeacherSubject getZakr() {
		LinkTeacherSubject st = new LinkTeacherSubject();
		st.setId(id);
		st.setSubjects((Subjects)subjectBox.getSelectedItem());
		st.setTeachers((Teachers)teacherBox.getSelectedItem());
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
	        	result = true;
	        	dispose();
		}
	    if (src.getName().equals("Cancel")) {
	        result = false;
	        dispose();
	    }
	  
		
	}


	private void initComponents(List<Teachers> list, List<Subjects> list2) {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel1 = new JPanel();
		panel6 = new JPanel();
		panel3 = new JPanel();
		teacherBox = new JComboBox(new Vector<Teachers>(list));
		panel4 = new JPanel();
		subjectBox = new JComboBox(new Vector<Subjects>(list2));
		panel2 = new JPanel();
		button1 = new JButton();
		button2 = new JButton();

		//======== this ========
		setTitle("\u0417\u0430\u043a\u0440\u0435\u043f\u0438\u0442\u044c \u043f\u0440\u0435\u043f\u043e\u0434\u0430\u0432\u0430\u0442\u0435\u043b\u044f \u043a \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u0443");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== panel1 ========
		{
			panel1.setLayout(new FlowLayout());
		}
		contentPane.add(panel1, BorderLayout.NORTH);

		//======== panel6 ========
		{
			panel6.setPreferredSize(new Dimension(180, 140));
			panel6.setLayout(new BoxLayout(panel6, BoxLayout.Y_AXIS));

			//======== panel3 ========
			{
				panel3.setBorder(new CompoundBorder(
					new TitledBorder("\u041f\u0440\u0435\u043f\u043e\u0434\u0430\u0432\u0430\u0442\u0435\u043b\u044c"),
					Borders.DLU2_BORDER));
				panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 5));

				//---- teacherBox ----
				teacherBox.setPreferredSize(new Dimension(200, 20));
				panel3.add(teacherBox);
			}
			panel6.add(panel3);

			//======== panel4 ========
			{
				panel4.setBorder(new CompoundBorder(
					new TitledBorder("\u041f\u0440\u0435\u0434\u043c\u0435\u0442"),
					Borders.DLU2_BORDER));
				panel4.setLayout(new FlowLayout());

				//---- subjectBox ----
				subjectBox.setMaximumRowCount(15);
				subjectBox.setPreferredSize(new Dimension(200, 20));
				panel4.add(subjectBox);
			}
			panel6.add(panel4);
		}
		contentPane.add(panel6, BorderLayout.CENTER);

		//======== panel2 ========
		{
			panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 35, 5));

			//---- button1 ----
			button1.setText("\u0417\u0430\u043a\u0440\u0435\u043f\u0438\u0442\u044c");
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
	private JPanel panel6;
	private JPanel panel3;
	private JComboBox teacherBox;
	private JPanel panel4;
	private JComboBox subjectBox;
	private JPanel panel2;
	private JButton button1;
	private JButton button2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

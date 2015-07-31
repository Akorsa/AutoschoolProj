/*
 * Created by JFormDesigner on Fri Dec 20 20:29:00 GMT+05:00 2013
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.*;

import beans.Avto;

import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import com.toedter.calendar.*;

/**
 * @author ????????
 */
public class AvtoDialog extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -616355114088490804L;
	private boolean result = false;
	private int avtoid = 0;
	public AvtoDialog(Frame owner) {
		super(owner);
		initComponents();
		saveButton.addActionListener(this);
		saveButton.setName("OK");
		cancelButton.addActionListener(this);
		cancelButton.setName("Cancel");
	}
	
	public void setAvto(Avto st) {
		avtoid = st.getAvtoid();
		marcField.setText(st.getModel());
		godvipChooser.setYear(st.getGodvip());
		gosnomFild.setText(st.getStatenumber());
		numdvigFIld.setText(st.getEnginenumber());
		colorFild.setText(st.getColor());
		garazhnomFild.setText(Integer.toString(st.getGarazhnumber()));
		numberPassField.setText(st.getTechpassportseria());
		datepasspChooser.setDate(st.getTechpassportdata());
		kemvidanFild.setText(st.getTechpassportvidan());
		markGSMFild.setText(st.getGsmMarka());
		lastTOChooser.setDate(st.getDatalastto());
		nextTOChooser.setDate(st.getDatanextto());
		
	}
	
	public Avto getAvto() {
		Avto st = new Avto();
		st.setAvtoid(avtoid);
		st.setModel(marcField.getText());
		st.setGodvip(godvipChooser.getYear());
		st.setStatenumber(gosnomFild.getText());
		st.setEnginenumber(numdvigFIld.getText());
		st.setColor(colorFild.getText());
		try {
        	int s = Integer.parseInt(garazhnomFild.getText());
            st.setGarazhnumber(s);
        } catch (NumberFormatException nfe) {
        }
		st.setTechpassportseria(numberPassField.getText());
		st.setTechpassportdata(datepasspChooser.getDate());
		st.setTechpassportvidan(kemvidanFild.getText());
		st.setGsmMarka(markGSMFild.getText());
		st.setDatalastto(lastTOChooser.getDate());
		st.setDatanextto(nextTOChooser.getDate());
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
	        if (marcField.getText().equals("") || gosnomFild.getText().equals("") || numdvigFIld.getText().equals("")|| 
	        		datepasspChooser.getDate()== null || lastTOChooser.getDate()== null|| 
	        				nextTOChooser.getDate()== null ||numberPassField.getText().equals("")) {
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
		panel13 = new JPanel();
		saveButton = new JButton();
		cancelButton = new JButton();
		panel1 = new JPanel();
		panel3 = new JPanel();
		nameLabel = new JLabel();
		marcField = new JTextField();
		yearLabel = new JLabel();
		godvipChooser = new JYearChooser();
		categLabel = new JLabel();
		gosnomFild = new JTextField();
		formEdLab = new JLabel();
		numdvigFIld = new JTextField();
		panel2 = new JPanel();
		panel5 = new JPanel();
		birthdateLabel = new JLabel();
		colorFild = new JTextField();
		label11 = new JLabel();
		garazhnomFild = new JTextField();
		panel12 = new JPanel();
		panel8 = new JPanel();
		label13 = new JLabel();
		numberPassField = new JTextField();
		label14 = new JLabel();
		datepasspChooser = new JDateChooser();
		label1 = new JLabel();
		kemvidanFild = new JTextField();
		label4 = new JLabel();
		markGSMFild = new JTextField();
		label2 = new JLabel();
		lastTOChooser = new JDateChooser();
		label3 = new JLabel();
		nextTOChooser = new JDateChooser();

		//======== this ========
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("\u0414\u043e\u0431\u0430\u0432\u0438\u0442\u044c/\u0440\u0435\u0434\u0430\u043a\u0442\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u0437\u0430\u043f\u0438\u0441\u044c");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

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
		contentPane.add(panel13, BorderLayout.SOUTH);

		//======== panel1 ========
		{
			panel1.setBorder(new CompoundBorder(
				new TitledBorder(" "),
				Borders.DLU2_BORDER));
			panel1.setPreferredSize(new Dimension(605, 100));
			panel1.setLayout(new FlowLayout());

			//======== panel3 ========
			{
				panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

				//---- nameLabel ----
				nameLabel.setText("\u041c\u0430\u0440\u043a\u0430*");
				nameLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
				panel3.add(nameLabel);

				//---- marcField ----
				marcField.setColumns(10);
				panel3.add(marcField);

				//---- yearLabel ----
				yearLabel.setText("\u0413\u043e\u0434 \u0432\u044b\u043f\u0443\u0441\u043a\u0430*");
				yearLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
				panel3.add(yearLabel);

				//---- godvipChooser ----
				godvipChooser.setPreferredSize(new Dimension(60, 20));
				panel3.add(godvipChooser);

				//---- categLabel ----
				categLabel.setText("\u0413\u043e\u0441. \u043d\u043e\u043c\u0435\u0440*");
				categLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
				panel3.add(categLabel);

				//---- gosnomFild ----
				gosnomFild.setColumns(8);
				panel3.add(gosnomFild);
			}
			panel1.add(panel3);

			//---- formEdLab ----
			formEdLab.setText("\u2116 \u0434\u0432\u0438\u0433\u0430\u0442\u0435\u043b\u044f*");
			formEdLab.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			panel1.add(formEdLab);

			//---- numdvigFIld ----
			numdvigFIld.setColumns(8);
			panel1.add(numdvigFIld);
		}
		contentPane.add(panel1, BorderLayout.NORTH);

		//======== panel2 ========
		{
			panel2.setPreferredSize(new Dimension(1733, 50));
			panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));

			//======== panel5 ========
			{
				panel5.setBorder(new CompoundBorder(
					new TitledBorder(" "),
					new EmptyBorder(5, 5, 5, 5)));
				panel5.setPreferredSize(new Dimension(500, 60));
				panel5.setLayout(new FlowLayout());

				//---- birthdateLabel ----
				birthdateLabel.setText("\u0426\u0432\u0435\u0442");
				birthdateLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
				panel5.add(birthdateLabel);

				//---- colorFild ----
				colorFild.setColumns(6);
				panel5.add(colorFild);

				//---- label11 ----
				label11.setText("\u0413\u0430\u0440\u0430\u0436\u043d\u044b\u0439 \u043d\u043e\u043c\u0435\u0440");
				panel5.add(label11);

				//---- garazhnomFild ----
				garazhnomFild.setColumns(8);
				panel5.add(garazhnomFild);
			}
			panel2.add(panel5);

			//======== panel12 ========
			{
				panel12.setPreferredSize(new Dimension(468, 60));
				panel12.setLayout(new FlowLayout());

				//======== panel8 ========
				{
					panel8.setBorder(new CompoundBorder(
						new TitledBorder("\u0422\u0435\u0445. \u043f\u0430\u0441\u043f\u043e\u0440\u0442"),
						Borders.DLU2_BORDER));
					panel8.setLayout(new FormLayout(
						"3*(default, $lcgap), default",
						"fill:default, $lgap"));

					//---- label13 ----
					label13.setText("\u041d\u043e\u043c\u0435\u0440 \u0438 \u0441\u0435\u0440\u0438\u044f*");
					label13.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
					panel8.add(label13, CC.xy(1, 1));

					//---- numberPassField ----
					numberPassField.setColumns(10);
					panel8.add(numberPassField, CC.xy(3, 1));

					//---- label14 ----
					label14.setText("\u0414\u0430\u0442\u0430 \u0432\u044b\u0434\u0430\u0447\u0438");
					panel8.add(label14, CC.xy(5, 1));

					//---- datepasspChooser ----
					datepasspChooser.setPreferredSize(new Dimension(95, 20));
					panel8.add(datepasspChooser, CC.xy(7, 1));
				}
				panel12.add(panel8);
			}
			panel2.add(panel12);

			//---- label1 ----
			label1.setText("\u041c\u0435\u0441\u0442\u043e \u0432\u044b\u0434\u0430\u0447\u0438 \u043f\u0430\u0441\u043f\u043e\u0440\u0442\u0430");
			panel2.add(label1);

			//---- kemvidanFild ----
			kemvidanFild.setColumns(16);
			panel2.add(kemvidanFild);

			//---- label4 ----
			label4.setText("\u041c\u0430\u0440\u043a\u0430 \u0431\u0435\u043d\u0437\u0438\u043d\u0430");
			panel2.add(label4);

			//---- markGSMFild ----
			markGSMFild.setColumns(10);
			panel2.add(markGSMFild);

			//---- label2 ----
			label2.setText("\u0414\u0430\u0442\u0430 \u043f\u043e\u0441\u043b\u0435\u0434\u043d\u0435\u0433\u043e \u0422\u041e*");
			panel2.add(label2);

			//---- lastTOChooser ----
			lastTOChooser.setPreferredSize(new Dimension(95, 20));
			panel2.add(lastTOChooser);

			//---- label3 ----
			label3.setText("\u0414\u0430\u0442\u0430 \u0441\u043b\u0435\u0434\u0443\u044e\u0449\u0435\u0433\u043e \u0422\u041e*");
			panel2.add(label3);

			//---- nextTOChooser ----
			nextTOChooser.setPreferredSize(new Dimension(95, 20));
			panel2.add(nextTOChooser);
		}
		contentPane.add(panel2, BorderLayout.CENTER);
		setSize(600, 395);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel13;
	private JButton saveButton;
	private JButton cancelButton;
	private JPanel panel1;
	private JPanel panel3;
	private JLabel nameLabel;
	private JTextField marcField;
	private JLabel yearLabel;
	private JYearChooser godvipChooser;
	private JLabel categLabel;
	private JTextField gosnomFild;
	private JLabel formEdLab;
	private JTextField numdvigFIld;
	private JPanel panel2;
	private JPanel panel5;
	private JLabel birthdateLabel;
	private JTextField colorFild;
	private JLabel label11;
	private JTextField garazhnomFild;
	private JPanel panel12;
	private JPanel panel8;
	private JLabel label13;
	private JTextField numberPassField;
	private JLabel label14;
	private JDateChooser datepasspChooser;
	private JLabel label1;
	private JTextField kemvidanFild;
	private JLabel label4;
	private JTextField markGSMFild;
	private JLabel label2;
	private JDateChooser lastTOChooser;
	private JLabel label3;
	private JDateChooser nextTOChooser;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

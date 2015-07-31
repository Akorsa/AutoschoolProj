/*
 * Created by JFormDesigner on Sun Jan 05 12:00:16 GMT+05:00 2014
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;

import javax.swing.*;

import beans.Users;

import rmi.AutoschoolRmiInterface;
import util.HashUtil;

/**
 * @author ????????
 */
public class AuthFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5420744913335134296L;
	private AutoschoolRmiInterface ari;
	public AuthFrame(AutoschoolRmiInterface clin) {
		initComponents();
		this.ari = clin;
		textField1.setText(null);
		textField1.setToolTipText("Введите логин");
		button1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					authenticate(textField1.getText(),passwordField1.getPassword());
				} catch (Exception e) {
			
					e.printStackTrace();
				}
				}
			});
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
	}
	

	protected void authenticate(String text, char[] password) {
		List<Users> usrs = null;
		
		String pass = HashUtil.simpleHash(new String(password));
		System.out.println(pass);
		try {
			usrs = ari.getUsersByLogin(text);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		if (!usrs.isEmpty()) {
			for(final Users u:usrs){
				System.out.println(u.getLogin()+ " "+ u.getPassword());
				if(u.getPassword().equals(pass)) {
					if (u.getTypeUser().getTypeOfUserDescription().equals("admin")) {
						this.setVisible(false);
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								AdminFrame inst;
								inst = new AdminFrame(ari);
								inst.setLocationRelativeTo(null);
								inst.setVisible(true);
								
							}
						});
					}
					if (u.getTypeUser().getTypeOfUserDescription().equals("Зам. директора по УР")) {
						this.setVisible(false);
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								ZamDirectURFrame inst;
								inst = new ZamDirectURFrame(ari);
								inst.setLocationRelativeTo(null);
								inst.setVisible(true);
								
							}
						});
					}
					if (u.getTypeUser().getTypeOfUserDescription().equals("Зам. директора по ПР")) {
						this.setVisible(false);
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								ZamDirectPRFrame inst;
								inst = new ZamDirectPRFrame(ari);
								inst.setLocationRelativeTo(null);
								inst.setVisible(true);
								
							}
						});
					}
					if (u.getTypeUser().getTypeOfUserDescription().equals("Заведующий отделением \"Автодело\"")) {
						this.setVisible(false);
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								ZavAvtodeloFrame inst;
								inst = new ZavAvtodeloFrame(ari);
								inst.setLocationRelativeTo(null);
								inst.setVisible(true);
								
							}
						});
					}
					if (u.getTypeUser().getTypeOfUserDescription().equals("Учащийся")) {
						this.setVisible(false);
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								StudAuthFrame inst;
								inst = new StudAuthFrame(ari,u);
								inst.setLocationRelativeTo(null);
								inst.setVisible(true);
								
							}
						});
					}
				} else {
					JOptionPane.showMessageDialog(AuthFrame.this,
	                        "Ошибка авторизации. Проверьте пароль", "Внимание", 2);
				}
			}
		} else {
			JOptionPane.showMessageDialog(AuthFrame.this,
                    "Ошибка авторизации. Проверьте логин", "Внимание", 2);
		}
		
	}


	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel4 = new JPanel();
		label5 = new JLabel();
		label6 = new JLabel();
		panel1 = new JPanel();
		label1 = new JLabel();
		textField1 = new JTextField();
		panel2 = new JPanel();
		label3 = new JLabel();
		passwordField1 = new JPasswordField();
		panel3 = new JPanel();
		button1 = new JButton();
		button2 = new JButton();

		//======== this ========
		setTitle("\u0412\u0445\u043e\u0434 \u0432 \u043f\u0440\u043e\u0433\u0440\u0430\u043c\u043c\u0443");
		setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== panel4 ========
		{
			panel4.setPreferredSize(new Dimension(447, 50));
			panel4.setLayout(new FlowLayout());

			//---- label5 ----
			label5.setIcon(null);
			label5.setHorizontalAlignment(SwingConstants.CENTER);
			label5.setText("\u0414\u043b\u044f \u0440\u0430\u0431\u043e\u0442\u044b \u0441 \u043f\u0440\u043e\u0433\u0440\u0430\u043c\u043c\u043e\u0439 \u043d\u0435\u043e\u0431\u0445\u043e\u0434\u0438\u043c\u043e \u043f\u0440\u043e\u0439\u0442\u0438 \u0430\u0432\u0442\u043e\u0440\u0438\u0437\u0430\u0446\u0438\u044e! ");
			label5.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			panel4.add(label5);

			//---- label6 ----
			label6.setText("\u0415\u0441\u043b\u0438 \u0412\u044b \u043d\u0435 \u0437\u0430\u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0438\u0440\u043e\u0432\u0430\u043d\u044b, \u043e\u0431\u0440\u0430\u0442\u0438\u0442\u0435\u0441\u044c \u043a \u0430\u0434\u043c\u0438\u043d\u0438\u0441\u0442\u0440\u0430\u0442\u043e\u0440\u0443.");
			label6.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			label6.setForeground(Color.red);
			panel4.add(label6);
		}
		contentPane.add(panel4, BorderLayout.NORTH);

		//======== panel1 ========
		{
			panel1.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 40));

			//---- label1 ----
			label1.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
			label1.setIcon(new ImageIcon(getClass().getResource("/images/User.png")));
			panel1.add(label1);

			//---- textField1 ----
			textField1.setColumns(15);
			textField1.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
			textField1.setText("\u041b\u043e\u0433\u0438\u043d");
			textField1.setToolTipText("\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043b\u043e\u0433\u0438\u043d");
			panel1.add(textField1);

			//======== panel2 ========
			{
				panel2.setLayout(new FlowLayout());

				//---- label3 ----
				label3.setIcon(new ImageIcon(getClass().getResource("/images/lock.png")));
				panel2.add(label3);

				//---- passwordField1 ----
				passwordField1.setColumns(15);
				passwordField1.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
				passwordField1.setToolTipText("\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043f\u0430\u0440\u043e\u043b\u044c");
				panel2.add(passwordField1);
			}
			panel1.add(panel2);
		}
		contentPane.add(panel1, BorderLayout.CENTER);

		//======== panel3 ========
		{
			panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 5));

			//---- button1 ----
			button1.setText("\u0412\u0445\u043e\u0434");
			button1.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
			button1.setIcon(new ImageIcon(getClass().getResource("/images/login.png")));
			panel3.add(button1);

			//---- button2 ----
			button2.setText("\u041e\u0442\u043c\u0435\u043d\u0430");
			button2.setFont(new Font("Trebuchet MS", Font.PLAIN, 14));
			panel3.add(button2);
		}
		contentPane.add(panel3, BorderLayout.SOUTH);
		setSize(440, 305);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel4;
	private JLabel label5;
	private JLabel label6;
	private JPanel panel1;
	private JLabel label1;
	private JTextField textField1;
	private JPanel panel2;
	private JLabel label3;
	private JPasswordField passwordField1;
	private JPanel panel3;
	private JButton button1;
	private JButton button2;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

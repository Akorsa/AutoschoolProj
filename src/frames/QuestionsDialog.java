/*
 * Created by JFormDesigner on Thu Dec 26 18:36:11 GMT+05:00 2013
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.Vector;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import beans.Questions;
import beans.Themes;

import com.jgoodies.forms.factories.*;

/**
 * @author ????????
 */
public class QuestionsDialog extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9191345872663483485L;
	private boolean result = false;
	private int questId = 0;
	private String ImgURL = null; 
	public QuestionsDialog(Frame owner, List<Themes> list) {
		super(owner);
		initComponents(list);
		saveButton.addActionListener(this);
		saveButton.setName("OK");
		cancelButton.addActionListener(this);
		cancelButton.setName("Cancel");
		fileChooserbutton.addActionListener(this);
		fileChooserbutton.setName("fileChoose");
	}
	
	public void setQuest(Questions st) {
		questId = st.getQuestionsId();
		questArea.setText(st.getQuestionText());
		levelFIld.setText(Double.toString(st.getDifficult()));
	
		if (st.getAns1()!= null) {
			ans1.setText(st.getAns1());
			if (st.getAns1flag()== true) {
				ans1Radio.setSelected(true);
			}
		}
		if (st.getAns2()!= null) {
			ans2.setText(st.getAns2());
			if (st.getAns2flag()== true) {
				ans2Radio.setSelected(true);
			}
		}
		if (st.getAns3()!= null) {
			ans3.setText(st.getAns3());
			if (st.getAns3flag()== true) {
				ans3Radio.setSelected(true);
			}
		}
		if (st.getAns4()!= null) {
			ans4.setText(st.getAns4());
			if (st.getAns4flag()== true) {
				ans4Radio.setSelected(true);
			}
		}
		for (int i = 0; i < themesBox.getModel().getSize(); i++) {
			Themes g = (Themes) themesBox.getModel().getElementAt(i);
			if (g.getThemeId()== st.getThemes().getThemeId()) {
				themesBox.setSelectedIndex(i);
				break;
			}
		}
		ImgURL = st.getImgpath();
	}
	
	
	public Questions getQuest() {
		Questions st = new Questions();
		st.setQuestionsId(questId);
		st.setDifficult(Double.parseDouble(levelFIld.getText()));
		st.setQuestionText(questArea.getText());
		st.setThemes((Themes)themesBox.getSelectedItem());
		if(ans1.getText().equals("")){
			System.out.println("Fuckin ans1");
		} else {
			st.setAns1(ans1.getText());
			st.setAns1flag(ans1Radio.isSelected());
		}
		if(ans2.getText().equals("")){
			System.out.println("Fuckin ans1");
		} else {
			st.setAns2(ans2.getText());
			st.setAns2flag(ans2Radio.isSelected());
		}
		if(ans3.getText().equals("")){
			System.out.println("Fuckin ans1");
		} else {
			st.setAns3(ans3.getText());
			st.setAns3flag(ans3Radio.isSelected());
		}
		if(ans4.getText().equals("")){
			System.out.println("Fuckin ans1");
		} else {
			st.setAns4(ans4.getText());
			st.setAns4flag(ans4Radio.isSelected());
		}
		st.setImgpath(ImgURL);
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
	        if (questArea.getText().equals("") || levelFIld.getText().equals("") || ans1.getText().equals("")|| 
	        		ans2.getText().equals("")) {
	        	JOptionPane.showMessageDialog(this, "Заполните все обязательные поля, помеченные звездочкой! (Мин. 2 варианта ответа)", "Внимание", 2);	
	        	
	        }else {
	        	result = true;
	        	dispose();
	        }
		}
	    if (src.getName().equals("Cancel")) {
	        result = false;
	        dispose();
	    }
	    if (src.getName().equals("fileChoose")) {
	    	this.ImgURL = fileChoose();
	    }
	  
		
	}
	
	


	private String fileChoose() {
		JFileChooser fileopen = new JFileChooser();   
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", "jpg");
		fileopen.addChoosableFileFilter(filter);
		fileopen.setCurrentDirectory(new java.io.File("C:/DOC/"));
		fileopen.setDialogTitle("Выберите изображение");
		fileopen.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileopen.setAcceptAllFileFilterUsed(false);   
		JFileChooser.setDefaultLocale(getLocale());
        int ret = fileopen.showDialog(this, "Сохранить");                
        String path = null;
        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            path = file.getAbsolutePath();
        }
        return path;
	}

	private void initComponents(List<Themes> list) {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel13 = new JPanel();
		saveButton = new JButton();
		cancelButton = new JButton();
		panel1 = new JPanel();
		panel3 = new JPanel();
		nameLabel = new JLabel();
		scrollPane1 = new JScrollPane();
		questArea = new JTextArea();
		yearLabel = new JLabel();
		themesBox = new JComboBox(new Vector<Themes>(list));
		formEdLab = new JLabel();
		levelFIld = new JTextField();
		label1 = new JLabel();
		fileChooserbutton = new JButton();
		panel2 = new JPanel();
		panel4 = new JPanel();
		nameLabel2 = new JLabel();
		scrollPane2 = new JScrollPane();
		ans1 = new JTextArea();
		yearLabel2 = new JLabel();
		ans1Radio = new JRadioButton();
		panel5 = new JPanel();
		nameLabel3 = new JLabel();
		scrollPane3 = new JScrollPane();
		ans2 = new JTextArea();
		yearLabel3 = new JLabel();
		ans2Radio = new JRadioButton();
		panel6 = new JPanel();
		nameLabel4 = new JLabel();
		scrollPane4 = new JScrollPane();
		ans3 = new JTextArea();
		yearLabel4 = new JLabel();
		ans3Radio = new JRadioButton();
		panel7 = new JPanel();
		nameLabel5 = new JLabel();
		scrollPane5 = new JScrollPane();
		ans4 = new JTextArea();
		yearLabel5 = new JLabel();
		ans4Radio = new JRadioButton();

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
			panel1.setPreferredSize(new Dimension(605, 130));
			panel1.setMinimumSize(new Dimension(522, 120));
			panel1.setLayout(new FlowLayout());

			//======== panel3 ========
			{
				panel3.setPreferredSize(new Dimension(700, 61));
				panel3.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

				//---- nameLabel ----
				nameLabel.setText("\u0422\u0435\u043a\u0441\u0442 \u0432\u043e\u043f\u0440\u043e\u0441\u0430*");
				nameLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
				panel3.add(nameLabel);

				//======== scrollPane1 ========
				{

					//---- questArea ----
					questArea.setColumns(35);
					questArea.setRows(3);
					questArea.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
					scrollPane1.setViewportView(questArea);
				}
				panel3.add(scrollPane1);

				//---- yearLabel ----
				yearLabel.setText("\u0422\u0435\u043c\u0430*");
				yearLabel.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
				panel3.add(yearLabel);

				//---- themesBox ----
				themesBox.setMinimumSize(new Dimension(110, 18));
				themesBox.setPreferredSize(new Dimension(140, 18));
				panel3.add(themesBox);
			}
			panel1.add(panel3);

			//---- formEdLab ----
			formEdLab.setText("\u0421\u043b\u043e\u0436\u043d\u043e\u0441\u0442\u044c \u0432\u043e\u043f\u0440\u043e\u0441\u0430 (0-1)*");
			formEdLab.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
			panel1.add(formEdLab);

			//---- levelFIld ----
			levelFIld.setColumns(8);
			panel1.add(levelFIld);

			//---- label1 ----
			label1.setText("\u0418\u0437\u043e\u0431\u0440\u0430\u0436\u0435\u043d\u0438\u0435");
			label1.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
			panel1.add(label1);

			//---- fileChooserbutton ----
			fileChooserbutton.setText("\u041e\u0431\u0437\u043e\u0440...");
			fileChooserbutton.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
			panel1.add(fileChooserbutton);
		}
		contentPane.add(panel1, BorderLayout.NORTH);

		//======== panel2 ========
		{
			panel2.setPreferredSize(new Dimension(1733, 50));
			panel2.setBorder(new CompoundBorder(
				new TitledBorder("\u0412\u0430\u0440\u0438\u0430\u043d\u0442\u044b \u043e\u0442\u0432\u0435\u0442\u043e\u0432"),
				Borders.DLU2_BORDER));
			panel2.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 5));

			//======== panel4 ========
			{
				panel4.setBorder(new CompoundBorder(
					new TitledBorder("\u21161"),
					Borders.DLU2_BORDER));
				panel4.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

				//---- nameLabel2 ----
				nameLabel2.setText("\u0422\u0435\u043a\u0441\u0442 \u043e\u0442\u0432\u0435\u0442\u0430*");
				nameLabel2.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
				panel4.add(nameLabel2);

				//======== scrollPane2 ========
				{

					//---- ans1 ----
					ans1.setColumns(40);
					ans1.setRows(2);
					ans1.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
					scrollPane2.setViewportView(ans1);
				}
				panel4.add(scrollPane2);

				//---- yearLabel2 ----
				yearLabel2.setText("\u041f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u044b\u0439*");
				yearLabel2.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
				panel4.add(yearLabel2);
				panel4.add(ans1Radio);
			}
			panel2.add(panel4);

			//======== panel5 ========
			{
				panel5.setBorder(new CompoundBorder(
					new TitledBorder("\u21162"),
					Borders.DLU2_BORDER));
				panel5.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

				//---- nameLabel3 ----
				nameLabel3.setText("\u0422\u0435\u043a\u0441\u0442 \u043e\u0442\u0432\u0435\u0442\u0430*");
				nameLabel3.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
				panel5.add(nameLabel3);

				//======== scrollPane3 ========
				{

					//---- ans2 ----
					ans2.setColumns(40);
					ans2.setRows(2);
					ans2.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
					scrollPane3.setViewportView(ans2);
				}
				panel5.add(scrollPane3);

				//---- yearLabel3 ----
				yearLabel3.setText("\u041f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u044b\u0439*");
				yearLabel3.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
				panel5.add(yearLabel3);
				panel5.add(ans2Radio);
			}
			panel2.add(panel5);

			//======== panel6 ========
			{
				panel6.setBorder(new CompoundBorder(
					new TitledBorder("\u21163"),
					Borders.DLU2_BORDER));
				panel6.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

				//---- nameLabel4 ----
				nameLabel4.setText("\u0422\u0435\u043a\u0441\u0442 \u043e\u0442\u0432\u0435\u0442\u0430*");
				nameLabel4.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
				panel6.add(nameLabel4);

				//======== scrollPane4 ========
				{

					//---- ans3 ----
					ans3.setColumns(40);
					ans3.setRows(2);
					ans3.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
					scrollPane4.setViewportView(ans3);
				}
				panel6.add(scrollPane4);

				//---- yearLabel4 ----
				yearLabel4.setText("\u041f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u044b\u0439*");
				yearLabel4.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
				panel6.add(yearLabel4);
				panel6.add(ans3Radio);
			}
			panel2.add(panel6);

			//======== panel7 ========
			{
				panel7.setBorder(new CompoundBorder(
					new TitledBorder("\u21164"),
					Borders.DLU2_BORDER));
				panel7.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

				//---- nameLabel5 ----
				nameLabel5.setText("\u0422\u0435\u043a\u0441\u0442 \u043e\u0442\u0432\u0435\u0442\u0430*");
				nameLabel5.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
				panel7.add(nameLabel5);

				//======== scrollPane5 ========
				{

					//---- ans4 ----
					ans4.setColumns(40);
					ans4.setRows(2);
					ans4.setFont(new Font("Trebuchet MS", Font.PLAIN, 12));
					scrollPane5.setViewportView(ans4);
				}
				panel7.add(scrollPane5);

				//---- yearLabel5 ----
				yearLabel5.setText("\u041f\u0440\u0430\u0432\u0438\u043b\u044c\u043d\u044b\u0439*");
				yearLabel5.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
				panel7.add(yearLabel5);
				panel7.add(ans4Radio);
			}
			panel2.add(panel7);
		}
		contentPane.add(panel2, BorderLayout.CENTER);
		setSize(735, 575);
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
	private JScrollPane scrollPane1;
	private JTextArea questArea;
	private JLabel yearLabel;
	private JComboBox themesBox;
	private JLabel formEdLab;
	private JTextField levelFIld;
	private JLabel label1;
	private JButton fileChooserbutton;
	private JPanel panel2;
	private JPanel panel4;
	private JLabel nameLabel2;
	private JScrollPane scrollPane2;
	private JTextArea ans1;
	private JLabel yearLabel2;
	private JRadioButton ans1Radio;
	private JPanel panel5;
	private JLabel nameLabel3;
	private JScrollPane scrollPane3;
	private JTextArea ans2;
	private JLabel yearLabel3;
	private JRadioButton ans2Radio;
	private JPanel panel6;
	private JLabel nameLabel4;
	private JScrollPane scrollPane4;
	private JTextArea ans3;
	private JLabel yearLabel4;
	private JRadioButton ans3Radio;
	private JPanel panel7;
	private JLabel nameLabel5;
	private JScrollPane scrollPane5;
	private JTextArea ans4;
	private JLabel yearLabel5;
	private JRadioButton ans4Radio;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

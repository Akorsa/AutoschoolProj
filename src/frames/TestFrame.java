/*
 * Created by JFormDesigner on Sat Dec 28 23:48:00 GMT+05:00 2013
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import rmi.AutoschoolRmiInterface;
import util.ExcelJob;
import beans.Students;
import beans.Questions;
import beans.Tests;
import beans.Themes;


/**
 * @author ????????
 */
public class TestFrame extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5738975834247048382L;
	private AutoschoolRmiInterface ari;
	private Students st;
	private List<Questions> quests;
	private List<Questions> curQuestions = new ArrayList<Questions>();
	private ListIterator<Questions> iter;
	private int count = 1,rightAns,result=0 , press =0;
	private int[] arr = new int[30];
	int kt = 20;
	double t = 20;
	double tn = 0;
	double tv = 100;
	double t1 = 0;
	ArrayList<Double> lvl = new ArrayList<Double>();
	public TestFrame(Students stud, AutoschoolRmiInterface ari, Themes themes, int i) {
		super();
		initComponents();
		dbConnect(ari);
		if(i==1){
			setTitle("\u0422\u0435\u0441\u0442\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u0435 \u043d\u0430 \u0437\u043d\u0430\u043d\u0438\u0435 \u041f\u0414\u0414 Режим:все темы");
			quests = loagQuestions(0.2);
		}
		if(i==2){
			setTitle("\u0422\u0435\u0441\u0442\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u0435 \u043d\u0430 \u0437\u043d\u0430\u043d\u0438\u0435 \u041f\u0414\u0414 Тема:"+themes.getNameOfTheme());
			quests = loadQuestionsByTheme(themes.getThemeId());
			timerLbl.setText("Тема:"+themes.getNameOfTheme());
		}
		
		Collections.shuffle(quests);
		iter = quests.listIterator();
		loadStud(stud);
		
		label2.setText("Приветствуем, " + st.getName() + " " + st.getSurname()+ "!");
		startButton.setName("start");
		forwardButton.setName("forward");
		endButton.setName("end");
		startButton.addActionListener(this);
		forwardButton.addActionListener(this);
		endButton.addActionListener(this);
		
	}
	
	private List<Questions> loadQuestionsByTheme(int id) {
		List<Questions > stud = null;
		try {
			stud =  ari.getQuestionsByTheme(id);
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
	}

	private void loadStud(Students stud)
	{
		this.st = stud;
	}
	
	private List<Questions> loagQuestions(double level) {
		List<Questions > stud = null;
		try {
			stud =  ari.getQuestByLvl(level);
		} catch (RemoteException e) {
			e.printStackTrace();
		} return stud;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Component) {
			Component c = (Component) e.getSource();
			if (c.getName().equals("start")) {
            	startTest();
            }
			if (c.getName().equals("forward")) {
				press++;
				forward();
            	
            
            }
			if (c.getName().equals("end")) {
            	end();
            }
		}
	}
	
	private void end() {
		for (int i=0;i < arr.length; i++){
			System.out.print(arr[i]);
		}
		this.dispose();
		
	}

	private void forward() {
		
		
			
			if (ans1Radio.isSelected() & this.rightAns == 1) {
				result++;
				arr[count] = 1;
				label6.setText(Integer.toString(result));
			}
			if (ans2Radio.isSelected() & this.rightAns == 2) {
				result++;
				arr[count] = 1;
				label6.setText(Integer.toString(result));
			}
			if (ans3Radio.isSelected() & this.rightAns == 3) {
				result++;
				arr[count] = 1;
				label6.setText(Integer.toString(result));
			}
			if (ans4Radio.isSelected() & this.rightAns == 4) {
				result++;
				arr[count] = 1;
				label6.setText(Integer.toString(result));
			}
			if (press % 2 == 0 ){
				double lvl1 = levelCount();
				if (lvl1 > 0 & lvl1 <0.3) {
					quests = loagQuestions(0.2);
					iter = quests.listIterator();
					Collections.shuffle(quests);
				}
				if (lvl1 >= 0.3 & lvl1 <0.5) {
					quests = loagQuestions(0.4);
					iter = quests.listIterator();
					Collections.shuffle(quests);
				}
				if (lvl1 >= 0.5 & lvl1 <0.7) {
					quests = loagQuestions(0.6);
					iter = quests.listIterator();
					Collections.shuffle(quests);
				}
				if (lvl1 >= 0.7 & lvl1 <0.9) {
					quests = loagQuestions(0.8);
					iter = quests.listIterator();
					Collections.shuffle(quests);
				}
				if (lvl1 >= 0.9 & lvl1 <1.1) {
					quests = loagQuestions(1);
					iter = quests.listIterator();
					Collections.shuffle(quests);
				}
			
				//System.out.println("Текущий уровень знаний" +lvl1);
				
			}
			Questions q = iter.next();
			curQuestions.add(q);
			questArea.setText(q.getQuestionText());
			if (q.getImgpath()!= null) {
				imgLbl.setIcon(new ImageIcon(q.getImgpath()));
			} else {
				imgLbl.setIcon(new ImageIcon("C:\\DOC\\PDDTickets\\default.jpg"));
			}
			ans1Area.setText(q.getAns1());
			ans2Area.setText(q.getAns2());
			if (q.getAns3()== null) {
				ans3Area.setText("");
				ans3Radio.setVisible(false);
			} else {
				ans3Area.setText(q.getAns3());
				ans3Radio.setVisible(true);
			}
			if (q.getAns4()== null) {
				ans4Area.setText("");
				ans4Radio.setVisible(false);
			} else {
				ans4Area.setText(q.getAns4());
				ans4Radio.setVisible(true);
			}
			if (q.getAns1flag()== true){
				this.rightAns = 1;
			}
			if (q.getAns2flag()== true){
				this.rightAns = 2;
			}
			if (q.getAns3flag() != null ) {
				if (q.getAns3flag()== true){
					this.rightAns = 3;
				}
			}
			if (q.getAns4flag() != null ) {
				if (q.getAns4flag()== true){
					this.rightAns = 4;
				}
			}
			
			
			
			count++;
			numbQuestLabl.setText("Вопрос "+count);
		
		//System.out.println("Финиш!");
		
		
		
	}
	
	private double levelCount(){
		double T = t/kt;
		//System.out.println("Сложность " +T);
		double normT = (T-0.2)/(5-0.2);
		//double normTT = normT/5;
		System.out.println("Сложность " +T + " Нормир " + normT);
		System.out.println("Последний "+ arr[count]+ " Препоследний "+ arr[count-1]);
		if(arr[count]==1 & arr[count-1]==1) {
			tn = t;
			tv = tv + t/2;
			if(tv > 100) {
				tv = 100;
			}
			System.out.println("два ответа");
		} else {
			if(arr[count]==1 & arr[count-1]==0) {
				tn = tn / 4;
				tv = tv + t/10;
				if(tv > 100) {
					tv = 100;
				}
				System.out.println("Один ответ");
			}
		
			if(arr[count]==0 & arr[count-1]==1) {
				tn = tn / 4;
				tv = tv + t/10;
				if(tv > 100) {
					tv = 100;
				}
				System.out.println("Один ответ");
			}
			if(arr[count]==0 & arr[count-1]==0) {
				tn = tn / 2;
				tv = t;
				System.out.println("Ноль ответ");
			}
		}
		t1 = (tv + tn)/2;
		if (Math.abs(t - t1) > 7) {
			t = t1;
			lvl.add(t);
			System.out.println("Уровень знаний "+ t);
			//return t;
		} else {
			System.out.println("Финиш!");
			double t1 = t/100;
			
			lvl.add(t);
			imgLbl.setVisible(false);
			questArea.setVisible(false);
			end();
			Tests test = new Tests();
			test.setStudents(st);
			test.setThemes(null);
			
		   //System.out.println("Constructor 1: " + dateFormat.format( currentDate ) );
			//Date curTime = Calendar.getInstance().getTime();
			long currentMillis = System.currentTimeMillis();
			Timestamp curTime = new Timestamp(currentMillis);
			test.setTimeOfTest(curTime);
			test.setTypeOfTest("Все темы");
			test.setBall(t1);
			//System.out.println(t);
			if((t1 >= 0) & (t1 <0.3)){
				test.setMark("Неудовлетворительно");
				JOptionPane.showMessageDialog(TestFrame.this,
						"Ваш результат: Неудовлетворительно ",
						"Внимание", 2);
				//System.out.println("1");
			}
			if(t1 >= 0.3 & t1 <0.5){
				test.setMark("Удовлетворительно");
				JOptionPane.showMessageDialog(TestFrame.this,
						"Ваш результат: Удовлетворительно",
						"Внимание", 2);
				//System.out.println("2");
			}
			if(t1 >= 0.5 & t1 <0.9){
				test.setMark("Хорошо");
				JOptionPane.showMessageDialog(TestFrame.this,
						"Ваш результат: Хорошо",
						"Внимание", 2);
				//System.out.println("3");
			}
			if(t1 >= 0.9 & t1 <1.1){
				test.setMark("Отлично");
				JOptionPane.showMessageDialog(TestFrame.this,
						"Ваш результат: Отлично",
						"Внимание", 2);
				//System.out.println("4");
			}
			
			//test.setMark("Fuck");
			try {
				ari.addTest(test);
				System.out.println(curQuestions.size());
				ExcelJob.exelSet5(curQuestions,arr,st,curTime);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		return normT;
		
		//return T;
		
	}

	private void startTest() {
		Questions q = iter.next();
		questArea.setText(q.getQuestionText());
		if (q.getImgpath()!= null) {
			imgLbl.setIcon(new ImageIcon(q.getImgpath()));
		} else {
			imgLbl.setIcon(new ImageIcon("C:\\DOC\\PDDTickets\\default.jpg"));
		}
		ans1Area.setText(q.getAns1());
		ans2Area.setText(q.getAns2());
		if (q.getAns3()== null) {
			ans3Area.setText("");
			ans3Radio.setVisible(false);
		} else {
			ans3Area.setText(q.getAns3());
			ans3Radio.setVisible(true);
		}
		if (q.getAns4()== null) {
			ans4Area.setText("");
			ans4Radio.setVisible(false);
		} else {
			ans4Area.setText(q.getAns4());
			ans4Radio.setVisible(true);
		}
		// Start the timer
		//timerLbl.timerStart();
		//count++;
		numbQuestLabl.setText("Вопрос "+count);
		startButton.setEnabled(false);
		if (q.getAns1flag()== true){
			this.rightAns = 1;
		}
		if (q.getAns2flag()== true){
			this.rightAns = 2;
		}
		if (q.getAns3flag() != null ) {
			if (q.getAns3flag()== true){
				this.rightAns = 3;
			}
		}
		if (q.getAns4flag() != null ) {
			if (q.getAns4flag()== true){
				this.rightAns = 4;
			}
		}
		
	}
	

	private void dbConnect(AutoschoolRmiInterface ar)
	{
		
		this.ari = ar;
	}

	private void initComponents() {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		label6 = new JLabel();
		panel1 = new JPanel();
		panel13 = new JPanel();
		label2 = new JLabel();
		panel2 = new JPanel();
		label3 = new JLabel();
		label1 = new JLabel();
		panel4 = new JPanel();
		panel7 = new JPanel();
		panel5 = new JPanel();
		numbQuestLabl = new JLabel();
		timerLbl = new JLabel();
		panel6 = new JPanel();
		imgLbl = new JLabel();
		panel8 = new JPanel();
		scrollPane2 = new JScrollPane();
		questArea = new JTextPane();
		panel9 = new JPanel();
		ans1Radio = new JRadioButton();
		scrollPane3 = new JScrollPane();
		ans1Area = new JTextPane();
		panel3 = new JPanel();
		ans2Radio = new JRadioButton();
		scrollPane1 = new JScrollPane();
		ans2Area = new JTextPane();
		panel10 = new JPanel();
		ans3Radio = new JRadioButton();
		scrollPane4 = new JScrollPane();
		ans3Area = new JTextPane();
		panel11 = new JPanel();
		ans4Radio = new JRadioButton();
		scrollPane5 = new JScrollPane();
		ans4Area = new JTextPane();
		panel12 = new JPanel();
		startButton = new JButton();
		forwardButton = new JButton();
		endButton = new JButton();
		ButtonGroup bG = new ButtonGroup();
		ans1Radio.setSelected(true);
		bG.add(ans1Radio);
		bG.add(ans2Radio);
		bG.add(ans3Radio);
		bG.add(ans4Radio);

		//======== this ========
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		//setTitle("\u0422\u0435\u0441\u0442\u0438\u0440\u043e\u0432\u0430\u043d\u0438\u0435 \u043d\u0430 \u0437\u043d\u0430\u043d\u0438\u0435 \u041f\u0414\u0414");
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//---- label6 ----
		label6.setText("text");
		//contentPane.add(label6, BorderLayout.SOUTH);

		//======== panel1 ========
		{
			panel1.setLayout(new FlowLayout(FlowLayout.TRAILING, 40, 5));

			//======== panel13 ========
			{
				panel13.setPreferredSize(new Dimension(270, 28));
				panel13.setLayout(new FlowLayout());

				//---- label2 ----
				label2.setText("\u041f\u0440\u0438\u0432\u0435\u0442\u0441\u0442\u0432\u0443\u0435\u043c!  2222222222222233333");
				label2.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
				panel13.add(label2);
			}
			panel1.add(panel13);

			//======== panel2 ========
			{
				panel2.setLayout(new FlowLayout());

				//---- label3 ----
				label3.setIcon(new ImageIcon("C:\\DOC\\PDDTickets\\2013-12-29_000013.jpg"));
				panel2.add(label3);

				//---- label1 ----
				label1.setIcon(new ImageIcon("C:\\DOC\\PDDTickets\\2013-12-28_234937.jpg"));
				panel2.add(label1);
			}
			panel1.add(panel2);
		}
		contentPane.add(panel1, BorderLayout.NORTH);

		//======== panel4 ========
		{
			panel4.setLayout(new BorderLayout());

			//======== panel7 ========
			{
				panel7.setLayout(new FlowLayout());

				//======== panel5 ========
				{
					panel5.setPreferredSize(new Dimension(600, 25));
					panel5.setLayout(new FlowLayout());

					//---- numbQuestLabl ----
					numbQuestLabl.setText("\u0412\u043e\u043f\u0440\u043e\u0441 ");
					numbQuestLabl.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
					numbQuestLabl.setPreferredSize(new Dimension(200, 15));
					panel5.add(numbQuestLabl);

					//---- timerLbl ----
					timerLbl.setPreferredSize(new Dimension(240, 14));
					timerLbl.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
					timerLbl.setBackground(SystemColor.inactiveCaption);
					//timerLbl.setForeground(Color.red);
					//timerLbl.setText("\u0422\u0430\u0439\u043c\u0435\u0440:");
					panel5.add(timerLbl);
				}
				panel7.add(panel5);

				//======== panel6 ========
				{
					panel6.setLayout(new FlowLayout());

					//---- imgLbl ----
					//imgLbl.setIcon(new ImageIcon("C:\\DOC\\PDDTickets\\6_19.jpg"));
					panel6.add(imgLbl);
				}
				panel7.add(panel6);

				//======== panel8 ========
				{
					panel8.setLayout(new FlowLayout());

					//======== scrollPane2 ========
					{
						scrollPane2.setBorder(null);

						//---- questArea ----
						questArea.setPreferredSize(new Dimension(600, 60));
						questArea.setEditable(false);
						questArea.setFont(new Font("Trebuchet MS", Font.BOLD, 16));
						questArea.setBackground(UIManager.getColor("EditorPane.disabledBackground"));
						questArea.setBorder(null);
						scrollPane2.setViewportView(questArea);
					}
					panel8.add(scrollPane2);
				}
				panel7.add(panel8);

				//======== panel9 ========
				{
					panel9.setPreferredSize(new Dimension(612, 65));
					panel9.setLayout(null);

					//---- ans1Radio ----
					ans1Radio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					ans1Radio.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
					ans1Radio.setHorizontalAlignment(SwingConstants.CENTER);
					ans1Radio.setVerticalAlignment(SwingConstants.TOP);
					panel9.add(ans1Radio);
					ans1Radio.setBounds(new Rectangle(new Point(10, 5), ans1Radio.getPreferredSize()));

					//======== scrollPane3 ========
					{
						scrollPane3.setBorder(null);

						//---- ans1Area ----
						ans1Area.setPreferredSize(new Dimension(550, 55));
						ans1Area.setBackground(UIManager.getColor("EditorPane.disabledBackground"));
						ans1Area.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
						ans1Area.setEditable(false);
						scrollPane3.setViewportView(ans1Area);
					}
					panel9.add(scrollPane3);
					scrollPane3.setBounds(new Rectangle(new Point(52, 5), scrollPane3.getPreferredSize()));

					{ // compute preferred size
						Dimension preferredSize = new Dimension();
						for(int i = 0; i < panel9.getComponentCount(); i++) {
							Rectangle bounds = panel9.getComponent(i).getBounds();
							preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
							preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
						}
						Insets insets = panel9.getInsets();
						preferredSize.width += insets.right;
						preferredSize.height += insets.bottom;
						panel9.setMinimumSize(preferredSize);
						panel9.setPreferredSize(preferredSize);
					}
				}
				panel7.add(panel9);

				//======== panel3 ========
				{
					panel3.setPreferredSize(new Dimension(612, 65));
					panel3.setLayout(null);

					//---- ans2Radio ----
					ans2Radio.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
					panel3.add(ans2Radio);
					ans2Radio.setBounds(new Rectangle(new Point(10, 5), ans2Radio.getPreferredSize()));

					//======== scrollPane1 ========
					{
						scrollPane1.setBorder(null);

						//---- ans2Area ----
						ans2Area.setPreferredSize(new Dimension(550, 55));
						ans2Area.setBackground(UIManager.getColor("EditorPane.disabledBackground"));
						ans2Area.setEditable(false);
						ans2Area.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
						scrollPane1.setViewportView(ans2Area);
					}
					panel3.add(scrollPane1);
					scrollPane1.setBounds(new Rectangle(new Point(52, 5), scrollPane1.getPreferredSize()));

					{ // compute preferred size
						Dimension preferredSize = new Dimension();
						for(int i = 0; i < panel3.getComponentCount(); i++) {
							Rectangle bounds = panel3.getComponent(i).getBounds();
							preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
							preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
						}
						Insets insets = panel3.getInsets();
						preferredSize.width += insets.right;
						preferredSize.height += insets.bottom;
						panel3.setMinimumSize(preferredSize);
						panel3.setPreferredSize(preferredSize);
					}
				}
				panel7.add(panel3);

				//======== panel10 ========
				{
					panel10.setPreferredSize(new Dimension(612, 65));
					panel10.setLayout(null);

					//---- ans3Radio ----
					ans3Radio.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
					panel10.add(ans3Radio);
					ans3Radio.setBounds(new Rectangle(new Point(10, 5), ans3Radio.getPreferredSize()));

					//======== scrollPane4 ========
					{
						scrollPane4.setBorder(null);

						//---- ans3Area ----
						ans3Area.setPreferredSize(new Dimension(550, 55));
						ans3Area.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
						ans3Area.setBackground(UIManager.getColor("EditorPane.disabledBackground"));
						ans3Area.setEditable(false);
						scrollPane4.setViewportView(ans3Area);
					}
					panel10.add(scrollPane4);
					scrollPane4.setBounds(new Rectangle(new Point(52, 5), scrollPane4.getPreferredSize()));

					{ // compute preferred size
						Dimension preferredSize = new Dimension();
						for(int i = 0; i < panel10.getComponentCount(); i++) {
							Rectangle bounds = panel10.getComponent(i).getBounds();
							preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
							preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
						}
						Insets insets = panel10.getInsets();
						preferredSize.width += insets.right;
						preferredSize.height += insets.bottom;
						panel10.setMinimumSize(preferredSize);
						panel10.setPreferredSize(preferredSize);
					}
				}
				panel7.add(panel10);

				//======== panel11 ========
				{
					panel11.setPreferredSize(new Dimension(612, 65));
					panel11.setLayout(null);

					//---- ans4Radio ----
					ans4Radio.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
					panel11.add(ans4Radio);
					ans4Radio.setBounds(new Rectangle(new Point(10, 5), ans4Radio.getPreferredSize()));

					//======== scrollPane5 ========
					{
						scrollPane5.setBorder(null);

						//---- ans4Area ----
						ans4Area.setPreferredSize(new Dimension(550, 55));
						ans4Area.setFont(new Font("Trebuchet MS", Font.BOLD, 14));
						ans4Area.setBackground(UIManager.getColor("EditorPane.disabledBackground"));
						ans4Area.setEditable(false);
						scrollPane5.setViewportView(ans4Area);
					}
					panel11.add(scrollPane5);
					scrollPane5.setBounds(new Rectangle(new Point(52, 5), scrollPane5.getPreferredSize()));

					{ // compute preferred size
						Dimension preferredSize = new Dimension();
						for(int i = 0; i < panel11.getComponentCount(); i++) {
							Rectangle bounds = panel11.getComponent(i).getBounds();
							preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
							preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
						}
						Insets insets = panel11.getInsets();
						preferredSize.width += insets.right;
						preferredSize.height += insets.bottom;
						panel11.setMinimumSize(preferredSize);
						panel11.setPreferredSize(preferredSize);
					}
				}
				panel7.add(panel11);

				//======== panel12 ========
				{
					panel12.setPreferredSize(new Dimension(615, 50));
					panel12.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 15));

					//---- startButton ----
					startButton.setText("\u041d\u0430\u0447\u0430\u0442\u044c ");
					startButton.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
					startButton.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Valid.png")));
					panel12.add(startButton);

					//---- forwardButton ----
					forwardButton.setText("\u0414\u0430\u043b\u0435\u0435");
					forwardButton.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
					forwardButton.setIcon(new ImageIcon(getClass().getResource("/images/Nnm 16/sign-in.png")));
					panel12.add(forwardButton);

					//---- endButton ----
					endButton.setText("\u0417\u0430\u0432\u0435\u0440\u0448\u0438\u0442\u044c");
					endButton.setFont(new Font("Trebuchet MS", Font.BOLD, 12));
					endButton.setIcon(new ImageIcon(getClass().getResource("/images/16x16/Button_Stop.png")));
					panel12.add(endButton);
				}
				panel7.add(panel12);
			}
			panel4.add(panel7, BorderLayout.CENTER);
		}
		contentPane.add(panel4, BorderLayout.CENTER);
		setSize(815, 745);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JLabel label6;
	private JPanel panel1;
	private JPanel panel13;
	private JLabel label2;
	private JPanel panel2;
	private JLabel label3;
	private JLabel label1;
	private JPanel panel4;
	private JPanel panel7;
	private JPanel panel5;
	private JLabel numbQuestLabl;
	private JLabel timerLbl;
	private JPanel panel6;
	private JLabel imgLbl;
	private JPanel panel8;
	private JScrollPane scrollPane2;
	private JTextPane questArea;
	private JPanel panel9;
	private JRadioButton ans1Radio;
	private JScrollPane scrollPane3;
	private JTextPane ans1Area;
	private JPanel panel3;
	private JRadioButton ans2Radio;
	private JScrollPane scrollPane1;
	private JTextPane ans2Area;
	private JPanel panel10;
	private JRadioButton ans3Radio;
	private JScrollPane scrollPane4;
	private JTextPane ans3Area;
	private JPanel panel11;
	private JRadioButton ans4Radio;
	private JScrollPane scrollPane5;
	private JTextPane ans4Area;
	private JPanel panel12;
	private JButton startButton;
	private JButton forwardButton;
	private JButton endButton;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}

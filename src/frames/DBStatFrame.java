/*
 * Created by JFormDesigner on Tue Dec 24 21:55:13 GMT+05:00 2013
 */

package frames;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.*;


/**
 * @author ????????
 */
public class DBStatFrame extends JDialog implements ActionListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1070479945488890005L;
	public DBStatFrame(Frame owner) {
		super(owner);
		try {
			initComponents(fillBox());
		} catch (Exception e) {
			e.printStackTrace();
		}
		button1.setName("run");
		button1.addActionListener(this);
	}
	
	private List<String> fillBox() throws Exception{
		Connection con = null;
		List<String> bets = new ArrayList<String>();
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/Autoschool";
			con = DriverManager.getConnection(url,"postgres","root");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		Statement stmt = null;
		ResultSet rs = null;
		try {

			String query1 = "Select relname from pg_stat_user_tables";
			stmt = con.createStatement();
			rs = stmt.executeQuery(query1);
			while (rs.next()) {
				String bt = new String(rs.getString(1));
                bets.add(bt);
            }
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {
            if (rs != null) {
				rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
		return bets;
	}
	
	private String sizeDB() throws Exception{
		Connection con = null;
		String bt = null;
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/Autoschool";
			con = DriverManager.getConnection(url,"postgres","root");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		Statement stmt = null;
		ResultSet rs = null;
		try {

			String query1 = "SELECT pg_size_pretty(pg_database_size('Autoschool'))";
			stmt = con.createStatement();
			rs = stmt.executeQuery(query1);
			while (rs.next()) {
				bt = new String(rs.getString(1));
            }
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {
            if (rs != null) {
				rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
		return bt;
	}
 	
	private DefaultTableModel statDB(String param)  throws Exception {
		
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			String url = "jdbc:postgresql://localhost:5432/Autoschool";
			con = DriverManager.getConnection(url,"postgres","root");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		Statement stmt = null;
		ResultSet rs = null;
		DefaultTableModel dtm = null;
		try {
			String g = new String("public."+param);
			String fr = new String(param);
			String query1 = "WITH x AS ( SELECT count(*) AS ct," +
			         "sum(length(t::text))    AS txt_len  " +
			         ",'"+g+ "'::regclass AS tbl FROM "+ fr+ " t), y AS (SELECT ARRAY [pg_relation_size(tbl),pg_table_size(tbl),pg_indexes_size(tbl),pg_total_relation_size(tbl)] AS val,ARRAY ['Размер таблицы (данные)'" +
			             ",'Размер таблицы + служебные данные'"+
			             ",'Размер индексов'"+
			             ",'Общий размер (данные + индексы)'"+
			           "] AS name" +
			    " FROM x"+
			    ")" +
			 " SELECT unnest(name)   AS what,unnest(val)   AS bytes,pg_size_pretty(unnest(val)) AS bytes_pretty,unnest(val) / ct            AS per_row_bytes" +
			    " FROM x,y " + 
			 " UNION  ALL SELECT 'Кол-во записей'::text, ct ,NULL::text, NULL::bigint FROM x"+
			 " UNION  ALL SELECT 'Доступно кортежей'::text, pg_stat_get_live_tuples(tbl),NULL::text, NULL::bigint FROM x " +
			 " UNION  ALL SELECT 'Мертвых кортежей'::text, pg_stat_get_dead_tuples(tbl),NULL::text, NULL::bigint FROM x";
			stmt = con.createStatement();
			rs = stmt.executeQuery(query1);
			dtm = buildTableModel(rs);
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {
            if (rs != null) {
                
					rs.close();
				
            }
            if (stmt != null) {
                stmt.close();
            }
        }
		return dtm;
	}
	
	public static DefaultTableModel buildTableModel(ResultSet rs)
	        throws SQLException {

	    ResultSetMetaData metaData = rs.getMetaData();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    columnNames.add("Параметр");
	    columnNames.add("Размер данных в байтах");
	    columnNames.add("Размер данных в Kб");
	    columnNames.add("Количество байт на одну запись");
	    

	    // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    while (rs.next()) {
	        Vector<Object> vector = new Vector<Object>();
	        for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
	            vector.add(rs.getObject(columnIndex));
	        }
	        data.add(vector);
	    }

	    return new DefaultTableModel(data, columnNames);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof Component) {
			Component c = (Component) e.getSource();
			if (c.getName().equals("run")) {
				try {
					listTables();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		
		}
		
	}
	
	private void listTables() throws Exception {
		String name = (String)tablesBox.getSelectedItem();
		updateProgress(statDB(name));
		
	}

	private void updateProgress(final DefaultTableModel dtm) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (dtm!= null){
					table1.setModel(dtm);
					table1.getColumnModel().getColumn(0).setPreferredWidth(205);
					table1.getColumnModel().getColumn(1).setPreferredWidth(200);
					table1.getColumnModel().getColumn(2).setPreferredWidth(163);
					table1.getColumnModel().getColumn(3).setPreferredWidth(235);
                    }
			}
		});
		
	}


	private void initComponents(List<String> list) {
		// JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
		panel1 = new JPanel();
		panel3 = new JPanel();
		Font font = new Font("Verdana", Font.BOLD, 16);
		try {
			dbInfoLabel = new JLabel("Размер БД = " + sizeDB());
			dbInfoLabel.setFont(font);
		} catch (Exception e) {
			e.printStackTrace();
		}
		panel2 = new JPanel();
		label2 = new JLabel();
		tablesBox = new JComboBox(new Vector<String>(list));
		button1 = new JButton();
		//panel4 = new JPanel();
		scrollPane1 = new JScrollPane();
		
		table1 = new JTable();
		table1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table1.getTableHeader().setFont(new Font("Trebuchet MS", Font.BOLD, 14));
			

		

		//======== this ========
		setTitle("\u0421\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043a\u0430 \u043f\u043e \u0411\u0414");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());

		//======== panel1 ========
		{
			panel1.setLayout(new BorderLayout());

			//======== panel3 ========
			{
				panel3.setLayout(new FlowLayout());

				//---- dbInfoLabel ----
				panel3.add(dbInfoLabel);
			}
			panel1.add(panel3, BorderLayout.CENTER);

			//======== panel2 ========
			{
				panel2.setLayout(new FlowLayout(FlowLayout.LEFT));

				//---- label2 ----
				label2.setText("\u0412\u044b\u0431\u0435\u0440\u0438\u0442\u0435 \u0442\u0430\u0431\u043b\u0438\u0446\u0443:");
				panel2.add(label2);

				//---- tablesBox ----
				tablesBox.setMaximumRowCount(30);
				tablesBox.setPreferredSize(new Dimension(140, 20));
				panel2.add(tablesBox);

				//---- button1 ----
				button1.setText("Ok");
				panel2.add(button1);
			}
			panel1.add(panel2, BorderLayout.SOUTH);
		}
		contentPane.add(panel1, BorderLayout.NORTH);

		//======== panel4 ========
		{
			//panel4.setLayout(new FlowLayout());

			//======== scrollPane1 ========
			{
			//	scrollPane1.setPreferredSize(new Dimension(600, 170));

				//---- table1 ----

				scrollPane1.setViewportView(table1);
			}
			//panel4.add(scrollPane1);
		}
		contentPane.add(scrollPane1, BorderLayout.CENTER);
		setSize(808, 255);
		setLocationRelativeTo(getOwner());
		// JFormDesigner - End of component initialization  //GEN-END:initComponents
	}

	// JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
	private JPanel panel1;
	private JPanel panel3;
	private JLabel dbInfoLabel;
	private JPanel panel2;
	private JLabel label2;
	private JComboBox tablesBox;
	private JButton button1;
	private JScrollPane scrollPane1;
	private JTable table1;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
	
}

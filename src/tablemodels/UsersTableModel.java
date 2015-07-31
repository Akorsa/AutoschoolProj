package tablemodels;

import java.util.List;


import javax.swing.table.AbstractTableModel;
import beans.Users;



public class UsersTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3116055540336871479L;
	// ������� ��������� ��� ������ ������ ��������
	private List<Users> groups = null;
	
	// ������ ��� �������� �������� ������ ������
	public UsersTableModel(List<Users> groups) {
		if(groups.iterator().hasNext()) 
		this.groups = groups;
	}

	// ���������� ����� ����� ����� �������
	public int getRowCount() {
		if (groups != null) {
			return groups.size();
		}
		return 0;
	}
	
	// ���������� �������� - 21
	public int getColumnCount() {
		return 5; 
	}
	
	public Users getRow(int row){
		return groups.get(row);
	}
	
	// ������ ������������ �������
	public String getColumnName(int column) {
		String[] colNames = {
				"\u041b\u043e\u0433\u0438\u043d", "\u0418\u043c\u044f \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f", "\u041f\u0430\u0440\u043e\u043b\u044c (\u0445\u044d\u0448)", "\u0420\u043e\u043b\u044c \u043f\u043e\u043b\u044c\u0437\u043e\u0432\u0430\u0442\u0435\u043b\u044f", "\u041a\u043e\u043c\u043c\u0435\u043d\u0442\u0430\u0440\u0438\u0439"
				};
		
		return colNames[column];
	}
	
	// ���������� ������ ��� ������������ ������ � �������
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (groups != null) {
			// �������� �� ������� ������
			Users bt = groups.get(rowIndex);
			// � ����������� �� ������� ���������� 
			switch (columnIndex) {
				case 0: 
					return bt.getLogin();
				case 1:
					return bt.getName();
				case 2: 
					return bt.getPassword();
				case 3:
					if (bt.getTypeUser() == null){
						return null;
					}else {
					return bt.getTypeUser().getTypeOfUserDescription();}
				case 4: 
					return bt.getComment();
				
			}
		}
		return null;
	}
	
	// ���������� ������ ��� ������������ ������ � �������
	public void setValueAt(Users aValue, int rowIndex, int columnIndex) {
		if (groups != null) {
			// �������� �� ������� ������
			Users bt = groups.get(rowIndex);
			// � ����������� �� ������� ���������� 
			switch (columnIndex) {
				case 0: 
					 bt.setLogin(aValue.getLogin());
				case 1:
					 bt.setName(aValue.getName());
				case 2: 
					 bt.setPassword(aValue.getPassword());
				case 3:
					 bt.setTypeUser(aValue.getTypeUser());
				case 4: 
					 bt.setComment(aValue.getComment());
				
			}
		}
	}
	
	// ������� �����, ������� ���������� ������ �� ������ ������
    public Users getUser(int rowIndex) {
        if (groups!= null) {
            if (rowIndex < groups.size() && rowIndex >= 0) {
                return groups.get(rowIndex);
            }
        }
        return null;
    }

}

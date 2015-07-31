package tablemodels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import beans.TypeUser;



public class RoleTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3116055540336871479L;
	// ������� ��������� ��� ������ ������ ��������
	private List<TypeUser> groups = null;
	
	// ������ ��� �������� �������� ������ ������
	public RoleTableModel(List<TypeUser> groups) {
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
		return 2; 
	}
	
	public TypeUser getRow(int row){
		return groups.get(row);
	}
	
	// ������ ������������ �������
	public String getColumnName(int column) {
		String[] colNames = {
				"\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u0440\u043e\u043b\u0438", "\u041f\u0440\u0430\u0432\u0430 \u0440\u043e\u043b\u0438"
			};
		
		return colNames[column];
	}
	
	// ���������� ������ ��� ������������ ������ � �������
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (groups != null) {
			// �������� �� ������� ������
			TypeUser bt = groups.get(rowIndex);
			// � ����������� �� ������� ���������� 
			switch (columnIndex) {
				case 0: 
					return bt.getTypeOfUserDescription();
				case 1:
					return bt.getTypeOfUserPermissions();
				
			}
		}
		return null;
	}
	
	// ���������� ������ ��� ������������ ������ � �������
	public void setValueAt(TypeUser aValue, int rowIndex, int columnIndex) {
		if (groups != null) {
			// �������� �� ������� ������
			TypeUser bt = groups.get(rowIndex);
			// � ����������� �� ������� ���������� 
			switch (columnIndex) {
				case 0: 
					 bt.setTypeOfUserDescription(aValue.getTypeOfUserDescription());
				case 1:
					 bt.setTypeOfUserPermissions(aValue.getTypeOfUserPermissions());
			}
		}
	}
	
	// ������� �����, ������� ���������� ������ �� ������ ������
    public TypeUser getRole(int rowIndex) {
        if (groups!= null) {
            if (rowIndex < groups.size() && rowIndex >= 0) {
                return groups.get(rowIndex);
            }
        }
        return null;
    }

}

package tablemodels;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import beans.LinkTeacherSubject;



public class ZakrTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3116055540336871479L;
	// ������� ��������� ��� ������ ������ ��������
	private List<LinkTeacherSubject> groups = null;
	
	// ������ ��� �������� �������� ������ ������
	public ZakrTableModel(List<LinkTeacherSubject> groups) {
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
	
	public LinkTeacherSubject getRow(int row){
		return groups.get(row);
	}
	
	// ������ ������������ �������
	public String getColumnName(int column) {
		String[] colNames = {
				"\u041f\u0440\u0435\u043f\u043e\u0434\u0430\u0432\u0430\u0442\u0435\u043b\u044c", "\u041f\u0440\u0435\u0434\u043c\u0435\u0442"
				};
		
		return colNames[column];
	}
	
	// ���������� ������ ��� ������������ ������ � �������
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (groups != null) {
			// �������� �� ������� ������
			LinkTeacherSubject bt = groups.get(rowIndex);
			// � ����������� �� ������� ���������� 
			switch (columnIndex) {
				case 0: 
					return bt.getTeachers().getPrepodfam()+ " " + bt.getTeachers().getPrepodim();
					
				case 1:
					return bt.getSubjects().getNameOfSubject();
				
			}
		}
		return null;
	}
	
	// ���������� ������ ��� ������������ ������ � �������
	public void setValueAt(LinkTeacherSubject aValue, int rowIndex, int columnIndex) {
		if (groups != null) {
			// �������� �� ������� ������
			LinkTeacherSubject bt = groups.get(rowIndex);
			// � ����������� �� ������� ���������� 
			switch (columnIndex) {
				case 0: 
					 bt.setTeachers(aValue.getTeachers());
					
				case 1:
					bt.setSubjects(aValue.getSubjects());
			}
		}
	}
	
	// ������� �����, ������� ���������� ������ �� ������ ������
    public LinkTeacherSubject getZakr(int rowIndex) {
        if (groups!= null) {
            if (rowIndex < groups.size() && rowIndex >= 0) {
                return groups.get(rowIndex);
            }
        }
        return null;
    }

}

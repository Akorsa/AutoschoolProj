package tablemodels;

import java.util.List;


import javax.swing.table.AbstractTableModel;

import beans.Themes;



public class ThemesTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3116055540336871479L;
	// ������� ��������� ��� ������ ������ ��������
	private List<Themes> groups = null;
	
	// ������ ��� �������� �������� ������ ������
	public ThemesTableModel(List<Themes> groups) {
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
		return 4; 
	}
	
	public Themes getRow(int row){
		return groups.get(row);
	}
	
	// ������ ������������ �������
	public String getColumnName(int column) {
		String[] colNames = {
				"\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u0442\u0435\u043c\u044b", "\u041a\u043e\u043b\u0438\u0447\u0435\u0441\u0442\u0432\u043e \u0447\u0430\u0441\u043e\u0432", "\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u0430", "\u041a\u043e\u043c\u043c\u0435\u043d\u0442\u0430\u0440\u0438\u0439"
			};
		
		return colNames[column];
	}
	
	// ���������� ������ ��� ������������ ������ � �������
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (groups != null) {
			// �������� �� ������� ������
			Themes bt = groups.get(rowIndex);
			// � ����������� �� ������� ���������� 
			switch (columnIndex) {
				case 0: 
					return bt.getNameOfTheme();
				case 1:
					return bt.getNumberOfHours();
				case 2: 
					if (bt.getSubjects() == null){
						return null;
					}else {
					return bt.getSubjects().getNameOfSubject();}
				case 3:
					return bt.getComment();
				
			}
		}
		return null;
	}
	
	// ���������� ������ ��� ������������ ������ � �������
	public void setValueAt(Themes aValue, int rowIndex, int columnIndex) {
		if (groups != null) {
			// �������� �� ������� ������
			Themes bt = groups.get(rowIndex);
			// � ����������� �� ������� ���������� 
			switch (columnIndex) {
				case 0: 
					 bt.setNameOfTheme(aValue.getNameOfTheme());
				case 1:
					 bt.setNumberOfHours(aValue.getNumberOfHours());
				case 2: 
					 bt.setSubjects(aValue.getSubjects());
				case 3:
					 bt.setComment(aValue.getComment());
				
			}
		}
	}
	
	// ������� �����, ������� ���������� ������ �� ������ ������
    public Themes getTheme(int rowIndex) {
        if (groups!= null) {
            if (rowIndex < groups.size() && rowIndex >= 0) {
                return groups.get(rowIndex);
            }
        }
        return null;
    }

}

package tablemodels;

import java.text.SimpleDateFormat;
import java.util.List;


import javax.swing.table.AbstractTableModel;

import beans.Groups;



public class GroupTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3116055540336871479L;
	// ������� ��������� ��� ������ ������ ��������
	private List<Groups> groups = null;
	
	// ������ ��� �������� �������� ������ ������
	public GroupTableModel(List<Groups> groups) {
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
		return 9; 
	}
	
	public Groups getRow(int row){
		return groups.get(row);
	}
	
	// ������ ������������ �������
	public String getColumnName(int column) {
		String[] colNames = {"\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u0433\u0440\u0443\u043f\u043f\u044b", "\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0438\u044f \u0422\u0421", "\u0424\u043e\u0440\u043c\u0430 \u043e\u0431\u0443\u0447\u0435\u043d\u0438\u044f", 
				"\u0413\u043e\u0434 \u043e\u0431\u0443\u0447\u0435\u043d\u0438\u044f", "\u041d\u0430\u0447\u0430\u043b\u043e \u0437\u0430\u043d\u044f\u0442\u0438\u0439", "\u041a\u043e\u043d\u0435\u0446 \u0437\u0430\u043d\u044f\u0442\u0438\u0439",
				"\u041d\u043e\u043c\u0435\u0440 \u043f\u0440\u0438\u043a\u0430\u0437\u0430","\u0414\u0430\u0442\u0430 \u043f\u0440\u0438\u043a\u0430\u0437\u0430",
			"��������� ��������"};
		
		return colNames[column];
	}
	
	// ���������� ������ ��� ������������ ������ � �������
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (groups != null) {
			// �������� �� ������� ������
			Groups bt = groups.get(rowIndex);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			// � ����������� �� ������� ���������� 
			switch (columnIndex) {
				case 0: 
					return bt.getGroupName();
				case 1:
					if (bt.getCategories() == null){
						return null;
					}else {
					return bt.getCategories().getKategoriesName();}
				case 2: 
					if (bt.getEducationtypes() == null){
						return null;
					}else {
					return bt.getEducationtypes().getName();}
				case 3:
					if (bt.getYearOfEducation() == null){
						return null;
					}else {
					return bt.getYearOfEducation().getNumberOfYear();}
				case 4:
					return dateFormat.format(bt.getGruopstartdate());
				case 5:
					return dateFormat.format(bt.getGroupenddate());
				case 6:
					return bt.getPricazn();
				case 7:
					return dateFormat.format(bt.getPricazdata());
				case 8:
					return bt.getFlagZakr();
			}
		}
		return null;
	}
	
	// ���������� ������ ��� ������������ ������ � �������
	public void setValueAt(Groups aValue, int rowIndex, int columnIndex) {
		if (groups != null) {
			// �������� �� ������� ������
			Groups bt = groups.get(rowIndex);
			// � ����������� �� ������� ���������� 
			switch (columnIndex) {
				case 0: 
					 bt.setGroupName(aValue.getGroupName());
				case 1:
					 bt.setCategories(aValue.getCategories());
				case 2: 
					 bt.setEducationtypes(aValue.getEducationtypes());
				case 3:
					 bt.setYearOfEducation(aValue.getYearOfEducation());
				case 4:
					 bt.setGruopstartdate(aValue.getGruopstartdate());
				case 5:
					 bt.setGroupenddate(aValue.getGroupenddate());
				case 6:
					 bt.setPricazn(aValue.getPricazn());
				case 7:
					 bt.setPricazdata(aValue.getPricazdata());
				case 8:
					 bt.setFlagZakr(aValue.getFlagZakr()); 
				
			}
		}
	}
	
	// ������� �����, ������� ���������� ������ �� ������ ������
    public Groups getGroup(int rowIndex) {
        if (groups!= null) {
            if (rowIndex < groups.size() && rowIndex >= 0) {
                return groups.get(rowIndex);
            }
        }
        return null;
    }

}

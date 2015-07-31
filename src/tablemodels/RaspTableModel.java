package tablemodels;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import beans.Schedules;

public class RaspTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3116055540336871479L;
	// ������� ��������� ��� ������ ������ ��������
	private List<Schedules> students = null;

	// ������ ��� �������� �������� ������ ������
	public RaspTableModel(List<Schedules> students) {
		if (students.iterator().hasNext())
			this.students = students;
	}

	// ���������� ����� ����� ����� �������
	public int getRowCount() {
		if (students != null) {
			return students.size();
		}
		return 0;
	}

	// ���������� �������� - 21
	public int getColumnCount() {
		return 5;
	}

	public Schedules getRow(int row) {
		return (Schedules) students.get(row);
	}

	// ������ ������������ �������
	public String getColumnName(int column) {
		String[] colNames = {
				"\u0413\u0440\u0443\u043f\u043f\u0430",
				"\u0422\u0435\u043c\u0430",
				"\u041f\u0440\u0435\u043f\u043e\u0434\u0430\u0432\u0430\u0442\u0435\u043b\u044c",
				"\u0414\u0430\u0442\u0430 \u0438 \u0432\u0440\u0435\u043c\u044f \u043d\u0430\u0447\u0430\u043b\u0430 \u0437\u0430\u043d\u044f\u0442\u0438\u0439",
				"\u0414\u0430\u0442\u0430 \u0438 \u0432\u0440\u0435\u043c\u044f \u043a\u043e\u043d\u0446\u0430 \u0437\u0430\u043d\u044f\u0442\u0438\u0439" };

		return colNames[column];
	}

	// ���������� ������ ��� ������������ ������ � �������
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (students != null) {
			// �������� �� ������� ������
			Schedules bt = (Schedules) students.get(rowIndex);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
			
			// � ����������� �� ������� ����������
			switch (columnIndex) {
			case 0:
				if (bt.getGroups() == null) {
					return null;
				} else {
					return bt.getGroups().getGroupName();
				}

			case 1:
				if (bt.getThemes() == null) {
					return null;
				} else {
					return bt.getThemes().getNameOfTheme();
				}
			case 2:
				if (bt.getTeachers() == null) {
					return null;
				} else {
					return bt.getTeachers().toString();
				}
			case 3:
				return dateFormat.format(bt.getBeginTime());
			case 4:
				return dateFormat.format(bt.getEndTime());
			}
		}
		return null;
	}

	// ���������� ������ ��� ������������ ������ � �������
	public void setValueAt(Schedules aValue, int rowIndex, int columnIndex) {
		if (students != null) {
			// �������� �� ������� ������
			Schedules bt = (Schedules) students.get(rowIndex);
			// � ����������� �� ������� ����������
			switch (columnIndex) {
			case 0:
				bt.setGroups(aValue.getGroups());
			case 1:
				bt.setThemes(aValue.getThemes());
			case 2:
				bt.setTeachers(aValue.getTeachers());
			case 3:
				bt.setBeginTime(aValue.getBeginTime());
			case 4:
				bt.setEndTime(aValue.getEndTime());
			}
		}
	}

	// ������� �����, ������� ���������� ������ �� ������ ������
	public Schedules getRasp(int rowIndex) {
		if (students != null) {
			if (rowIndex < students.size() && rowIndex >= 0) {
				return (Schedules) students.get(rowIndex);
			}
		}
		return null;
	}

}

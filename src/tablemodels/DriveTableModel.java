package tablemodels;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;

import beans.Drivingschedule;

public class DriveTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3116055540336871479L;
	// Сделаем хранилище для нашего списка учеников
	private List<Drivingschedule> students = null;

	// Модель при создании получает список ставок
	public DriveTableModel(List<Drivingschedule> students) {
		if (students.iterator().hasNext())
			this.students = students;
	}

	// Количество строк равно числу записей
	public int getRowCount() {
		if (students != null) {
			return students.size();
		}
		return 0;
	}

	// Количество столбцов - 21
	public int getColumnCount() {
		return 7;
	}

	public Drivingschedule getRow(int row) {
		return (Drivingschedule) students.get(row);
	}

	// Вернем наименование колонки
	public String getColumnName(int column) {
		String[] colNames = {

				"\u0423\u0447\u0430\u0449\u0438\u0439\u0441\u044f", "\u041c\u0430\u0441\u0442\u0435\u0440 \u041f\u041e", "\u0410\u0432\u0442\u043e\u043c\u043e\u0431\u0438\u043b\u044c", "\u0414\u0430\u0442\u0430 \u0438 \u0432\u0440\u0435\u043c\u044f \u043d\u0430\u0447\u0430\u043b\u0430 \u0437\u0430\u043d\u044f\u0442\u0438\u044f", "\u041a\u043e\u043b\u0438\u0447\u0435\u0441\u0442\u0432\u043e \u0447\u0430\u0441\u043e\u0432", "\u0412\u044b\u043f\u043e\u043b\u043d\u0435\u043d\u043e", "\u041a\u043e\u043c\u043c\u0435\u043d\u0442\u0430\u0440\u0438\u0439"
			 };

		return colNames[column];
	}

	// Возвращаем данные для определенной строки и столбца
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (students != null) {
			// Получаем из вектора ставку
			Drivingschedule bt = (Drivingschedule) students.get(rowIndex);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
			
			// В зависимости от колонки возвращаем
			switch (columnIndex) {
			case 0:
				if (bt.getStudents() == null) {
					return null;
				} else {
					return bt.getStudents().getSurname() + " " + bt.getStudents().getName();
				}

			case 1:
				if (bt.getTeachers() == null) {
					return null;
				} else {
					return bt.getTeachers().toString();
				}
			case 2:
				if (bt.getAvto() == null) {
					return null;
				} else {
					return bt.getAvto().getModel();
				}
			case 3:
				return dateFormat.format(bt.getDatebegin());
			case 4:
				return bt.getNumberOfHours();
			case 5:
				if(bt.isDoneFlag()){
					return "Да";
				}
				if(!bt.isDoneFlag()){
					return "Нет";
				}
					
			case 6:
				return bt.getDescription();		
			}
		}
		return null;
	}

	// Возвращаем данные для определенной строки и столбца
	public void setValueAt(Drivingschedule aValue, int rowIndex, int columnIndex) {
		if (students != null) {
			// Получаем из вектора ставку
			Drivingschedule bt = (Drivingschedule) students.get(rowIndex);
			// В зависимости от колонки возвращаем
			switch (columnIndex) {
			case 0:
				bt.setStudents(aValue.getStudents());
			case 1:
				bt.setTeachers(aValue.getTeachers());
			case 2:
				bt.setAvto(aValue.getAvto());
			case 3:
				bt.setDatebegin(aValue.getDatebegin());
			case 4:
				bt.setDateend(aValue.getDateend());
			case 5:
				bt.setDoneFlag(aValue.isDoneFlag());
			case 6:
				bt.setDescription(aValue.getDescription());	
			}
		}
	}

	// Добавим метод, который возвращает ставку по номеру строки
	public Drivingschedule getRasp(int rowIndex) {
		if (students != null) {
			if (rowIndex < students.size() && rowIndex >= 0) {
				return (Drivingschedule) students.get(rowIndex);
			}
		}
		return null;
	}

}

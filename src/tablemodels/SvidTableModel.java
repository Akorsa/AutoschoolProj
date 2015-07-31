package tablemodels;


import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import beans.Students;


public class SvidTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3116055540336871479L;
	// Сделаем хранилище для нашего списка учеников
	private List<Students> students = null;
	
	// Модель при создании получает список ставок
	public SvidTableModel(List<Students> students) {
		if(students.iterator().hasNext()) 
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
	
	public Students getRow(int row){
		return (Students) students.get(row);
	}
	
	// Вернем наименование колонки
	public String getColumnName(int column) {
		String[] colNames = {
				"\u041e\u0442\u043c\u0435\u0442\u043a\u0430 \u043e \u0441\u0434\u0430\u0447\u0435 \u0432\u043d\u0443\u0442\u0440. \u044d\u043a\u0437", "\u0424\u0430\u043c\u0438\u043b\u0438\u044f", "\u0418\u043c\u044f ", "\u041e\u0442\u0447\u0435\u0441\u0442\u0432\u043e", "\u0414\u0430\u0442\u0430 \u0440\u043e\u0436\u0434\u0435\u043d\u0438\u044f", "\u0414\u0430\u0442\u0430 \u0441\u0434\u0430\u0447\u0438 \u0432\u043d. \u044d\u043a\u0437\u0430\u043c\u0435\u043d\u0430", "\u0421\u0432\u0438\u0434\u0435\u0442\u0435\u043b\u044c\u0441\u0442\u0432\u043e"
				};
		
		return colNames[column];
	}
	
	// Возвращаем данные для определенной строки и столбца
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (students != null) {
			// Получаем из вектора ставку
			Students bt = (Students) students.get(rowIndex);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			// В зависимости от колонки возвращаем 
			switch (columnIndex) {
				case 0: 
					return bt.getEkzFlag();
				case 1:
					return bt.getSurname();
				case 2: 
					return bt.getName();
				case 3:
					return bt.getPatronymicname();
				case 4:
					return dateFormat.format(bt.getDatebirth());
				case 5:
					if(bt.getEkzData()!= null){
						return dateFormat.format(bt.getEkzData());
					}
					else return null;
				case 6:
					return bt.getSvidInfo();
			}
		}
		return null;
	}
	
	// Возвращаем данные для определенной строки и столбца
	public void setValueAt(Students aValue, int rowIndex, int columnIndex) {
		if (students != null) {
			// Получаем из вектора ставку
			Students bt = (Students) students.get(rowIndex);
			// В зависимости от колонки возвращаем 
			switch (columnIndex) {
				case 0: 
					 bt.setEkzFlag(aValue.getEkzFlag());
				case 1:
					 bt.setSurname(aValue.getSurname());
				case 2: 
					 bt.setName(aValue.getName());
				case 3:
					 bt.setPatronymicname(aValue.getPatronymicname());
				case 4:
					 bt.setDatebirth(aValue.getDatebirth());
				case 5:
					 bt.setEkzData(aValue.getEkzData());
				case 6:
					 bt.setSvidInfo(aValue.getSvidInfo());					
			}
		}
	}
	
	// Добавим метод, который возвращает ставку по номеру строки
    public Students getStudent(int rowIndex) {
        if (students != null) {
            if (rowIndex < students.size() && rowIndex >= 0) {
                return (Students) students.get(rowIndex);
            }
        }
        return null;
    }

}

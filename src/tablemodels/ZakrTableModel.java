package tablemodels;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import beans.LinkTeacherSubject;



public class ZakrTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3116055540336871479L;
	// Сделаем хранилище для нашего списка учеников
	private List<LinkTeacherSubject> groups = null;
	
	// Модель при создании получает список ставок
	public ZakrTableModel(List<LinkTeacherSubject> groups) {
		if(groups.iterator().hasNext()) 
		this.groups = groups;
	}

	// Количество строк равно числу записей
	public int getRowCount() {
		if (groups != null) {
			return groups.size();
		}
		return 0;
	}
	
	// Количество столбцов - 21
	public int getColumnCount() {
		return 2; 
	}
	
	public LinkTeacherSubject getRow(int row){
		return groups.get(row);
	}
	
	// Вернем наименование колонки
	public String getColumnName(int column) {
		String[] colNames = {
				"\u041f\u0440\u0435\u043f\u043e\u0434\u0430\u0432\u0430\u0442\u0435\u043b\u044c", "\u041f\u0440\u0435\u0434\u043c\u0435\u0442"
				};
		
		return colNames[column];
	}
	
	// Возвращаем данные для определенной строки и столбца
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (groups != null) {
			// Получаем из вектора ставку
			LinkTeacherSubject bt = groups.get(rowIndex);
			// В зависимости от колонки возвращаем 
			switch (columnIndex) {
				case 0: 
					return bt.getTeachers().getPrepodfam()+ " " + bt.getTeachers().getPrepodim();
					
				case 1:
					return bt.getSubjects().getNameOfSubject();
				
			}
		}
		return null;
	}
	
	// Возвращаем данные для определенной строки и столбца
	public void setValueAt(LinkTeacherSubject aValue, int rowIndex, int columnIndex) {
		if (groups != null) {
			// Получаем из вектора ставку
			LinkTeacherSubject bt = groups.get(rowIndex);
			// В зависимости от колонки возвращаем 
			switch (columnIndex) {
				case 0: 
					 bt.setTeachers(aValue.getTeachers());
					
				case 1:
					bt.setSubjects(aValue.getSubjects());
			}
		}
	}
	
	// Добавим метод, который возвращает ставку по номеру строки
    public LinkTeacherSubject getZakr(int rowIndex) {
        if (groups!= null) {
            if (rowIndex < groups.size() && rowIndex >= 0) {
                return groups.get(rowIndex);
            }
        }
        return null;
    }

}

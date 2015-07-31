package tablemodels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import beans.Subjects;



public class SubjectTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3116055540336871479L;
	// Сделаем хранилище для нашего списка учеников
	private List<Subjects> groups = null;
	
	// Модель при создании получает список ставок
	public SubjectTableModel(List<Subjects> groups) {
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
		return 1; 
	}
	
	public Subjects getRow(int row){
		return groups.get(row);
	}
	
	// Вернем наименование колонки
	public String getColumnName(int column) {
		String[] colNames = {
				"\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u043f\u0440\u0435\u0434\u043c\u0435\u0442\u0430"};
		return colNames[column];
	}
	
	// Возвращаем данные для определенной строки и столбца
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (groups != null) {
			// Получаем из вектора ставку
			Subjects bt = groups.get(rowIndex);
			// В зависимости от колонки возвращаем 
			switch (columnIndex) {
				case 0: 
					return bt.getNameOfSubject();
				
			}
		}
		return null;
	}
	
	// Возвращаем данные для определенной строки и столбца
	public void setValueAt(Subjects aValue, int rowIndex, int columnIndex) {
		if (groups != null) {
			// Получаем из вектора ставку
			Subjects bt = groups.get(rowIndex);
			// В зависимости от колонки возвращаем 
			switch (columnIndex) {
				case 0: 
					 bt.setNameOfSubject(aValue.getNameOfSubject());
				
			}
		}
	}
	
	// Добавим метод, который возвращает ставку по номеру строки
    public Subjects getSubj(int rowIndex) {
        if (groups!= null) {
            if (rowIndex < groups.size() && rowIndex >= 0) {
                return groups.get(rowIndex);
            }
        }
        return null;
    }

}

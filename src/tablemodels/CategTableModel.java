package tablemodels;

import java.util.List;


import javax.swing.table.AbstractTableModel;

import beans.Categories;



public class CategTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3116055540336871479L;
	// Сделаем хранилище для нашего списка учеников
	private List<Categories> groups = null;
	
	// Модель при создании получает список ставок
	public CategTableModel(List<Categories> groups) {
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
		return 4; 
	}
	
	public Categories getRow(int row){
		return groups.get(row);
	}
	
	// Вернем наименование колонки
	public String getColumnName(int column) {
		String[] colNames = {
				"\u041f\u0440\u043e\u0433\u0440\u0430\u043c\u043c\u0430 \u043f\u043e\u0434\u0433\u043e\u0442\u043e\u0432\u043a\u0438", "\u0427\u0430\u0441\u043e\u0432 \u0442\u0435\u043e\u0440\u0438\u0438", "\u0427\u0430\u0441\u043e\u0432 \u043f\u0440\u0430\u043a\u0442\u0438\u043a\u0438", "\u0421\u0442\u043e\u0438\u043c\u043e\u0441\u0442\u044c"
			};
		
		return colNames[column];
	}
	
	// Возвращаем данные для определенной строки и столбца
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (groups != null) {
			// Получаем из вектора ставку
			Categories bt = groups.get(rowIndex);
			// В зависимости от колонки возвращаем 
			switch (columnIndex) {
				case 0: 
					return bt.getKategoriesName();
				case 1:
					return bt.getTimeTheory();
				case 2: 
					return bt.getTimePractice();
				case 3:
					return bt.getCostEducation();
				
			}
		}
		return null;
	}
	
	// Возвращаем данные для определенной строки и столбца
	public void setValueAt(Categories aValue, int rowIndex, int columnIndex) {
		if (groups != null) {
			// Получаем из вектора ставку
			Categories bt = groups.get(rowIndex);
			// В зависимости от колонки возвращаем 
			switch (columnIndex) {
				case 0: 
					 bt.setKategoriesName(aValue.getKategoriesName());
				case 1:
					 bt.setTimeTheory(aValue.getTimeTheory());
				case 2: 
					 bt.setTimePractice(aValue.getTimePractice());
				case 3:
					 bt.setCostEducation(aValue.getCostEducation());
				
			}
		}
	}
	
	// Добавим метод, который возвращает ставку по номеру строки
    public Categories getGroup(int rowIndex) {
        if (groups!= null) {
            if (rowIndex < groups.size() && rowIndex >= 0) {
                return groups.get(rowIndex);
            }
        }
        return null;
    }

}

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
	// Сделаем хранилище для нашего списка учеников
	private List<Groups> groups = null;
	
	// Модель при создании получает список ставок
	public GroupTableModel(List<Groups> groups) {
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
		return 9; 
	}
	
	public Groups getRow(int row){
		return groups.get(row);
	}
	
	// Вернем наименование колонки
	public String getColumnName(int column) {
		String[] colNames = {"\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u0433\u0440\u0443\u043f\u043f\u044b", "\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0438\u044f \u0422\u0421", "\u0424\u043e\u0440\u043c\u0430 \u043e\u0431\u0443\u0447\u0435\u043d\u0438\u044f", 
				"\u0413\u043e\u0434 \u043e\u0431\u0443\u0447\u0435\u043d\u0438\u044f", "\u041d\u0430\u0447\u0430\u043b\u043e \u0437\u0430\u043d\u044f\u0442\u0438\u0439", "\u041a\u043e\u043d\u0435\u0446 \u0437\u0430\u043d\u044f\u0442\u0438\u0439",
				"\u041d\u043e\u043c\u0435\u0440 \u043f\u0440\u0438\u043a\u0430\u0437\u0430","\u0414\u0430\u0442\u0430 \u043f\u0440\u0438\u043a\u0430\u0437\u0430",
			"Закончено обучение"};
		
		return colNames[column];
	}
	
	// Возвращаем данные для определенной строки и столбца
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (groups != null) {
			// Получаем из вектора ставку
			Groups bt = groups.get(rowIndex);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			// В зависимости от колонки возвращаем 
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
	
	// Возвращаем данные для определенной строки и столбца
	public void setValueAt(Groups aValue, int rowIndex, int columnIndex) {
		if (groups != null) {
			// Получаем из вектора ставку
			Groups bt = groups.get(rowIndex);
			// В зависимости от колонки возвращаем 
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
	
	// Добавим метод, который возвращает ставку по номеру строки
    public Groups getGroup(int rowIndex) {
        if (groups!= null) {
            if (rowIndex < groups.size() && rowIndex >= 0) {
                return groups.get(rowIndex);
            }
        }
        return null;
    }

}

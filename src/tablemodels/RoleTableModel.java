package tablemodels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import beans.TypeUser;



public class RoleTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3116055540336871479L;
	// Сделаем хранилище для нашего списка учеников
	private List<TypeUser> groups = null;
	
	// Модель при создании получает список ставок
	public RoleTableModel(List<TypeUser> groups) {
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
	
	public TypeUser getRow(int row){
		return groups.get(row);
	}
	
	// Вернем наименование колонки
	public String getColumnName(int column) {
		String[] colNames = {
				"\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u0440\u043e\u043b\u0438", "\u041f\u0440\u0430\u0432\u0430 \u0440\u043e\u043b\u0438"
			};
		
		return colNames[column];
	}
	
	// Возвращаем данные для определенной строки и столбца
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (groups != null) {
			// Получаем из вектора ставку
			TypeUser bt = groups.get(rowIndex);
			// В зависимости от колонки возвращаем 
			switch (columnIndex) {
				case 0: 
					return bt.getTypeOfUserDescription();
				case 1:
					return bt.getTypeOfUserPermissions();
				
			}
		}
		return null;
	}
	
	// Возвращаем данные для определенной строки и столбца
	public void setValueAt(TypeUser aValue, int rowIndex, int columnIndex) {
		if (groups != null) {
			// Получаем из вектора ставку
			TypeUser bt = groups.get(rowIndex);
			// В зависимости от колонки возвращаем 
			switch (columnIndex) {
				case 0: 
					 bt.setTypeOfUserDescription(aValue.getTypeOfUserDescription());
				case 1:
					 bt.setTypeOfUserPermissions(aValue.getTypeOfUserPermissions());
			}
		}
	}
	
	// Добавим метод, который возвращает ставку по номеру строки
    public TypeUser getRole(int rowIndex) {
        if (groups!= null) {
            if (rowIndex < groups.size() && rowIndex >= 0) {
                return groups.get(rowIndex);
            }
        }
        return null;
    }

}

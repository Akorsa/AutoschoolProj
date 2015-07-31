package tablemodels;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import beans.Avto;



public class AvtoTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3116055540336871479L;
	// Сделаем хранилище для нашего списка учеников
	private List<Avto> students = null;
	
	// Модель при создании получает список ставок
	public AvtoTableModel(List<Avto> students) {
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
		return 12; 
	}
	
	public Avto getRow(int row){
		return (Avto) students.get(row);
	}
	
	// Вернем наименование колонки
	public String getColumnName(int column) {
		String[] colNames = {"\u041c\u0430\u0440\u043a\u0430 \u0430\u0432\u0442\u043e", "\u0413\u043e\u0441. \u043d\u043e\u043c\u0435\u0440", "\u0426\u0432\u0435\u0442", 
				"\u0413\u043e\u0434 \u0432\u044b\u043f\u0443\u0441\u043a\u0430", "\u2116 \u0434\u0432\u0438\u0433\u0430\u0442\u0435\u043b\u044f", 
				"\u0413\u0430\u0440\u0430\u0436\u043d\u044b\u0439 \u2116",
				"\u0421\u0435\u0440\u0438\u044f \u0438 \u2116 \u0442\u0435\u0445. \u043f\u0430\u0441\u043f\u043e\u0440\u0442\u0430","\u041c\u0435\u0441\u0442\u043e \u0432\u044b\u0434\u0430\u0447\u0438 \u0442\u0435\u0445. \u043f\u0430\u0441\u043f\u043e\u0440\u0442\u0430",
				"\u0414\u0430\u0442\u0430 \u0432\u044b\u0434\u0430\u0447\u0438 \u0442\u0435\u0445. \u043f\u0430\u0441\u043f\u043e\u0440\u0442\u0430", 
				"\u0414\u0430\u0442\u0430 \u043f\u043e\u0441\u043b\u0435\u0434\u043d\u0435\u0433\u043e \u0422\u041e","\u0414\u0430\u0442\u0430 \u0441\u043b\u0435\u0434\u0443\u044e\u0449\u0435\u0433\u043e \u0422\u041e",
				"\u041c\u0430\u0440\u043a\u0430 \u0413\u0421\u041c"};
		
		return colNames[column];
	}
	
	// Возвращаем данные для определенной строки и столбца
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (students != null) {
			// Получаем из вектора ставку
			Avto bt = (Avto) students.get(rowIndex);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			// В зависимости от колонки возвращаем 
			switch (columnIndex) {
				case 0: 
					return bt.getModel();
				case 1:
					return bt.getStatenumber();
				case 2: 
					return bt.getColor();
				case 3:
					return bt.getGodvip();
				case 4:
					return bt.getEnginenumber();
				case 5:
					return bt.getGarazhnumber();
				case 6:
					return bt.getTechpassportseria();
				case 7:
					return bt.getTechpassportvidan();
					
				case 8:
					return bt.getTechpassportdata();
				case 9:
					return dateFormat.format(bt.getDatalastto());
				case 10:
					return dateFormat.format(bt.getDatanextto());
				case 11:
					return bt.getGsmMarka();
			}
		}
		return null;
	}
	
	// Возвращаем данные для определенной строки и столбца
	public void setValueAt(Avto aValue, int rowIndex, int columnIndex) {
		if (students != null) {
			// Получаем из вектора ставку
			Avto bt = (Avto) students.get(rowIndex);
			// В зависимости от колонки возвращаем 
			switch (columnIndex) {
				case 0: 
					 bt.setModel(aValue.getModel());
				case 1:
					 bt.setStatenumber(aValue.getStatenumber());
				case 2: 
					 bt.setColor(aValue.getColor());
				case 3:
					 bt.setGodvip(aValue.getGodvip());
				case 4:
					 bt.setEnginenumber(aValue.getEnginenumber());
				case 5:
					 bt.setGarazhnumber(aValue.getGarazhnumber());
				case 6:
					 bt.setTechpassportseria(aValue.getTechpassportseria());
				case 7:
					 bt.setTechpassportvidan(aValue.getTechpassportvidan());
				case 8:
					bt.setTechpassportdata(aValue.getTechpassportdata());
				case 9:
					bt.setDatalastto(aValue.getDatalastto());
				case 10:
					 bt.setDatanextto(aValue.getDatanextto());
				case 11: 
					 bt.setGsmMarka(aValue.getGsmMarka());
			}
		}
	}
	
	// Добавим метод, который возвращает ставку по номеру строки
    public Avto getAvto(int rowIndex) {
        if (students != null) {
            if (rowIndex < students.size() && rowIndex >= 0) {
                return (Avto) students.get(rowIndex);
            }
        }
        return null;
    }

}

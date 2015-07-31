package tablemodels;


import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import beans.Teachers;


public class TeacherTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3116055540336871479L;
	// Сделаем хранилище для нашего списка учеников
	private List<Teachers> students = null;
	
	// Модель при создании получает список ставок
	public TeacherTableModel(List<Teachers> students) {
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
		return 18; 
	}
	
	public Teachers getRow(int row){
		return (Teachers) students.get(row);
	}
	
	// Вернем наименование колонки
	public String getColumnName(int column) {
		String[] colNames = {"\u0424\u0430\u043c\u0438\u043b\u0438\u044f", "\u0418\u043c\u044f", "\u041e\u0442\u0447\u0435\u0441\u0442\u0432\u043e", "\u0414\u0430\u0442\u0430 \u0440\u043e\u0436\u0434\u0435\u043d\u0438\u044f", 
				"\u041c\u0435\u0441\u0442\u043e \u0440\u043e\u0436\u0434\u0435\u043d\u0438\u044f", "\u0410\u0434\u0440\u0435\u0441",
				"\u0422\u0435\u043b\u0435\u0444\u043e\u043d","\u0421\u0435\u0440\u0438\u044f \u2116 \u0432\u043e\u0434. \u0443\u0434\u043e\u0441\u0442.",
				"\u0414\u0430\u0442\u0430 \u0432\u044b\u0434\u0430\u0447\u0438", "\u041a\u0430\u0442\u0435\u0433\u043e\u0440\u0438\u044f","\u041c\u0435\u0441\u0442\u043e \u0432\u044b\u0434\u0430\u0447\u0438",
				"\u041e\u0431\u0440\u0430\u0437\u043e\u0432\u0430\u043d\u0438\u0435","\u0423\u0447. \u0437\u0430\u0432\u0435\u0434\u0435\u043d\u0438\u0435",
				"\u0413\u043e\u0434 \u043e\u043a\u043e\u043d\u0447\u0430\u043d\u0438\u044f","\u041d\u043e\u043c\u0435\u0440, \u0441\u0435\u0440\u0438\u044f \u0434\u0438\u043f\u043b\u043e\u043c\u0430","\u0412\u043e\u0434\u0438\u0442. \u0441\u0442\u0430\u0436",
				"\u041e\u0442\u043c\u0435\u0442\u043a\u0430 \u043e\u0431 \u0443\u0432\u043e\u043b\u044c\u043d\u0435\u043d\u0438\u0438","\u0414\u0430\u0442\u0430 \u0438 \u2116 \u043f\u0440\u0438\u043a\u0430\u0437\u0430 \u043e\u0431 \u0443\u0432\u043e\u043b\u044c\u043d\u0435\u043d\u0438\u0438"};
		
		return colNames[column];
	}
	
	// Возвращаем данные для определенной строки и столбца
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (students != null) {
			// Получаем из вектора ставку
			Teachers bt = (Teachers) students.get(rowIndex);
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			// В зависимости от колонки возвращаем 
			switch (columnIndex) {
				case 0: 
					return bt.getPrepodfam();
				case 1:
					return bt.getPrepodim();
				case 2: 
					return bt.getPrepodot();
				case 3:
					return dateFormat.format(bt.getBirthdate());
				case 4:
					return bt.getBirthplace();
				case 5:
					return bt.getAddress();
				case 6:
					return bt.getPhone();
				case 7:
					return bt.getPravan();
				case 8:
					return bt.getPravadata();
				case 9:
					return bt.getPravakat();
				case 10:
					return bt.getPravavidano();
				case 11:
					if (bt.getTypeEducation() == null){
						return null;
					}else {
					return bt.getTypeEducation().getName();}
				case 12:
					return bt.getUchzavedenie();
				case 13:
					return bt.getGodokonchaniya();
				case 14:
					return bt.getDiplomseria();
				case 15:
					return bt.getVoditstazh();
				case 16:
					return bt.isUvolen();
				case 17:
					return bt.getUvolinfo();
			}
		}
		return null;
	}
	
	// Возвращаем данные для определенной строки и столбца
	public void setValueAt(Teachers aValue, int rowIndex, int columnIndex) {
		if (students != null) {
			// Получаем из вектора ставку
			Teachers bt = (Teachers) students.get(rowIndex);
			// В зависимости от колонки возвращаем 
			switch (columnIndex) {
				case 0: 
					 bt.setPrepodfam(aValue.getPrepodfam());
				case 1:
					 bt.setPrepodim(aValue.getPrepodim());
				case 2: 
					 bt.setPrepodot(aValue.getPrepodot());
				case 3:
					 bt.setBirthdate(aValue.getBirthdate());
				case 4:
					 bt.setBirthplace(aValue.getBirthplace());
				case 5:
					 bt.setAddress(aValue.getAddress());
				case 6:
					 bt.setPhone(aValue.getPhone());
				case 7:
					bt.setPravan(aValue.getPravan());
				case 8:
					bt.setPravadata(aValue.getPravadata());
				case 9:
					bt.setPravakat(aValue.getPravakat());
				case 10:
					bt.setPravavidano(aValue.getPravavidano());
				case 11: 
					bt.setTypeEducation(aValue.getTypeEducation());
				case 12:
					 bt.setUchzavedenie(aValue.getUchzavedenie());
				case 13:
					bt.setGodokonchaniya(aValue.getGodokonchaniya());
				case 14:
					bt.setDiplomseria(aValue.getDiplomseria());
				case 15:
					bt.setVoditstazh(aValue.getVoditstazh());
				case 16:
					bt.setUvolen(aValue.isUvolen());
				case 17:
					bt.setUvolinfo(aValue.getUvolinfo());					
			}
		}
	}
	
	// Добавим метод, который возвращает ставку по номеру строки
    public Teachers getTeacher(int rowIndex) {
        if (students != null) {
            if (rowIndex < students.size() && rowIndex >= 0) {
                return (Teachers) students.get(rowIndex);
            }
        }
        return null;
    }

}

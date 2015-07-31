package tablemodels;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import beans.Students;


public class StudentTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3116055540336871479L;
	// Сделаем хранилище для нашего списка учеников
	private List<Students> students = Collections.synchronizedList(new ArrayList<Students>());
	
	// Модель при создании получает список ставок
	public StudentTableModel(List<Students> students) {
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
		return 21; 
	}
	
	public Students getRow(int row){
		return (Students) students.get(row);
	}
	
	// Вернем наименование колонки
	public String getColumnName(int column) {
		String[] colNames = {"Отметка о сдаче внутр. экз", "Группа №", "Фамилия", "Имя", "Отчество", 
				"Дата рождения","Место рождения","Адрес", "Тип документа","Серия документа","Номер документа","Дата выдачи",
				"Кем выдано","Образование","Образовательное учреждение","Место работы","Телефон",
				"Дата сдачи внутр. экзамена","Свидетельство","Отметка об отчислении","Профиль пользователя"};
		
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
					if (bt.getGroups() == null){
						return null;
					}else {
					return bt.getGroups().getGroupName();
					}
				case 2: 
					return bt.getSurname();
				case 3:
					return bt.getName();
				case 4:
					return bt.getPatronymicname();
				case 5:
					return dateFormat.format(bt.getDatebirth());
				case 6:
					return bt.getBirthplace();
				case 7:
					return bt.getAddress();
				case 8:
					return bt.getTypeDocum();
				case 9:
					return bt.getDocseries();
				case 10:
					return bt.getDocnumber();
				case 11:
					return dateFormat.format(bt.getDocdate());
				case 12:
					return bt.getDoinfo();
				case 13:
					if (bt.getTypeEducation() == null){
						return null;
					}else {
					return bt.getTypeEducation().getName();}
				case 14:
					return bt.getEducationinfo();
				case 15:
					return bt.getJobplace();
				case 16:
					return bt.getPhone();
				case 17:
					return dateFormat.format(bt.getEkzData());
				case 18:
					return bt.getSvidInfo();
				case 19:
					return bt.getOtchislen();
				case 20:
					if (bt.getUsers() == null){
						return null;
					}else {
						return bt.getUsers().getLogin();}
					
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
					 bt.setGroups(aValue.getGroups());
				case 2: 
					 bt.setSurname(aValue.getSurname());
				case 3:
					 bt.setName(aValue.getName());
				case 4:
					 bt.setPatronymicname(aValue.getPatronymicname());
				case 5:
					 bt.setDatebirth(aValue.getDatebirth());
				case 6:
					 bt.setBirthplace(aValue.getBirthplace());
				case 7:
					 bt.setAddress(aValue.getAddress());
				case 8:
					bt.setTypeDocum(aValue.getTypeDocum());
				case 9:
					 bt.setDocseries(aValue.getDocseries());
				case 10: 
					 bt.setDocnumber(aValue.getDocnumber());
				case 11:
					 bt.setDocdate(aValue.getDocdate());
				case 12:
					 bt.setDoinfo(aValue.getDoinfo());
				case 13:
					 bt.setTypeEducation(aValue.getTypeEducation());
				case 14:
					 bt.setEducationinfo(aValue.getEducationinfo());
				case 15:
					 bt.setJobplace(aValue.getJobplace());
				case 16:
					 bt.setPhone(aValue.getPhone());
				case 17:
					bt.setEkzData(aValue.getEkzData());
				case 18:
					bt.setSvidInfo(aValue.getSvidInfo());	
				case 19:
					bt.setOtchislen(aValue.getOtchislen());
				case 20:
					bt.setUsers(aValue.getUsers());
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

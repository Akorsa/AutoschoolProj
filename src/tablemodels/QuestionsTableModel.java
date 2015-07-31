package tablemodels;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import beans.Questions;




public class QuestionsTableModel extends AbstractTableModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3116055540336871479L;
	// ������� ��������� ��� ������ ������ ��������
	private List<Questions> groups = null;
	
	// ������ ��� �������� �������� ������ ������
	public QuestionsTableModel(List<Questions> groups) {
		if(groups.iterator().hasNext()) 
		this.groups = groups;
	}

	// ���������� ����� ����� ����� �������
	public int getRowCount() {
		if (groups != null) {
			return groups.size();
		}
		return 0;
	}
	
	// ���������� �������� - 21
	public int getColumnCount() {
		return 3; 
	}
	
	public Questions getRow(int row){
		return groups.get(row);
	}
	
	// ������ ������������ �������
	public String getColumnName(int column) {
		String[] colNames = {
				"\u0422\u0435\u043a\u0441\u0442 \u0432\u043e\u043f\u0440\u043e\u0441\u0430", "\u0421\u043b\u043e\u0436\u043d\u043e\u0441\u0442\u044c", "\u041d\u0430\u0437\u0432\u0430\u043d\u0438\u0435 \u0442\u0435\u043c\u044b"
				};
		
		return colNames[column];
	}
	
	// ���������� ������ ��� ������������ ������ � �������
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (groups != null) {
			// �������� �� ������� ������
			Questions bt = groups.get(rowIndex);
			// � ����������� �� ������� ���������� 
			switch (columnIndex) {
				case 0: 
					return bt.getQuestionText();
				case 1:
					return bt.getDifficult();
				case 2:
					if (bt.getThemes() == null){
						return null;
					}else {
					return bt.getThemes().getNameOfTheme();}
				
			}
		}
		return null;
	}
	
	// ���������� ������ ��� ������������ ������ � �������
	public void setValueAt(Questions aValue, int rowIndex, int columnIndex) {
		if (groups != null) {
			// �������� �� ������� ������
			Questions bt = groups.get(rowIndex);
			// � ����������� �� ������� ���������� 
			switch (columnIndex) {
				case 0: 
					 bt.setQuestionText(aValue.getQuestionText());
				case 1:
					 bt.setDifficult(aValue.getDifficult());
				case 2: 
					 bt.setThemes(aValue.getThemes());
			}
		}
	}
	
	// ������� �����, ������� ���������� ������ �� ������ ������
    public Questions getQuestion(int rowIndex) {
        if (groups!= null) {
            if (rowIndex < groups.size() && rowIndex >= 0) {
                return groups.get(rowIndex);
            }
        }
        return null;
    }

}

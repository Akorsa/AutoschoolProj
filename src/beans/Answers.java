package beans;

// Generated 12.12.2013 21:23:38 by Hibernate Tools 4.0.0

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Answers generated by hbm2java
 */
@Entity
@Table(name = "answers", schema = "public")
public class Answers implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1603057149395494632L;
	private int answersId;
	private Questions questions;
	private String textOfAnswer;
	private boolean flagOfRight;

	public Answers() {
	}


	@SequenceGenerator(allocationSize=1, initialValue=1, sequenceName="answers_answers_id_seq", name="answers_answers_id_seq")
	@GeneratedValue(generator="answers_answers_id_seq", strategy=GenerationType.SEQUENCE)
	@Id
	@Column(name = "answers_id", unique = true, nullable = false)
	public int getAnswersId() {
		return this.answersId;
	}

	public void setAnswersId(int answersId) {
		this.answersId = answersId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "questiond_id", nullable = false)
	public Questions getQuestions() {
		return this.questions;
	}

	public void setQuestions(Questions questions) {
		this.questions = questions;
	}

	@Column(name = "text_of_answer", nullable = false)
	public String getTextOfAnswer() {
		return this.textOfAnswer;
	}

	public void setTextOfAnswer(String textOfAnswer) {
		this.textOfAnswer = textOfAnswer;
	}

	@Column(name = "flag_of_right", nullable = false)
	public boolean isFlagOfRight() {
		return this.flagOfRight;
	}

	public void setFlagOfRight(boolean flagOfRight) {
		this.flagOfRight = flagOfRight;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Answers [answersId=" + answersId + ", questions=" + questions
				+ ", textOfAnswer=" + textOfAnswer + ", flagOfRight="
				+ flagOfRight + "]";
	}

}

package beans;

// Generated 03.12.2013 23:30:51 by Hibernate Tools 4.0.0

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Drivingschedule generated by hbm2java
 */
@Entity
@Table(name = "drivingschedule", schema = "public")
public class Drivingschedule implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4783137423067219422L;
	private int iddrivingshedule;
	private Teachers teachers;
	private Avto avto;
	private Students students;
	private Date datebegin;
	private int numberhours;
	private String description;
	private boolean doneFlag;
	private Date dateend;

	public Drivingschedule() {
	}

	
	@SequenceGenerator(allocationSize=1, initialValue=1, sequenceName="drivingschedule_iddrivingshedule_seq", name="drivingschedule_iddrivingshedule_seq")
	@GeneratedValue(generator="drivingschedule_iddrivingshedule_seq", strategy=GenerationType.SEQUENCE)
	@Id
	@Column(name = "IDDRIVINGSHEDULE", nullable = false)
	public int getIddrivingshedule() {
		return this.iddrivingshedule;
	}

	public void setIddrivingshedule(int iddrivingshedule) {
		this.iddrivingshedule = iddrivingshedule;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEACHERID", nullable = false)
	public Teachers getTeachers() {
		return this.teachers;
	}

	public void setTeachers(Teachers teachers) {
		this.teachers = teachers;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "AUTOMOBILEID", nullable = false)
	public Avto getAvto() {
		return this.avto;
	}

	public void setAvto(Avto avto) {
		this.avto = avto;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STUDENTID", nullable = false)
	public Students getStudents() {
		return this.students;
	}

	public void setStudents(Students students) {
		this.students = students;
	}

	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATEBEGIN", nullable = false, length = 29)
	public Date getDatebegin() {
		return this.datebegin;
	}

	public void setDatebegin(Date datebegin) {
		this.datebegin = datebegin;
	}

	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "numberhours", nullable = false, length = 29)
	public int getNumberOfHours() {
		return this.numberhours;
	}

	public void setNumberOfHours(int dateend) {
		this.numberhours = dateend;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "DONE_FLAG", nullable = false)
	public boolean isDoneFlag() {
		return this.doneFlag;
	}

	public void setDoneFlag(boolean doneFlag) {
		this.doneFlag = doneFlag;
	}
	
	//@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dateend", length = 29)
	public Date getDateend() {
		return this.dateend;
	}

	public void setDateend(Date dateend) {
		this.dateend = dateend;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Drivingschedule [teachers=" + teachers + ", students="
				+ students + ", datebegin=" + datebegin + ", dateend="
				+ dateend + "]";
	}

}

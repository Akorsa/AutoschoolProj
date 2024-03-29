package beans;
// Generated 02.12.2013 21:28:10 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Educationtypes generated by hbm2java
 */
@Entity
@Table(name = "educationtypes", schema = "public")
public class Educationtypes implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 125742818952532242L;
	private int educationtypeid;
	private String name;
	private Set<Groups> groupses = new HashSet<Groups>(0);

	public Educationtypes() {
	}

	public Educationtypes(int educationtypeid, String name) {
		this.educationtypeid = educationtypeid;
		this.name = name;
	}

	public Educationtypes(int educationtypeid, String name, Set<Groups> groupses) {
		this.educationtypeid = educationtypeid;
		this.name = name;
		this.groupses = groupses;
	}
	@SequenceGenerator(allocationSize=1, initialValue=1, sequenceName="educationtypes_educationdtypeid_seq", name="educationtypes_educationdtypeid_seq")
	@GeneratedValue(generator="educationtypes_educationdtypeid_seq", strategy=GenerationType.SEQUENCE)
	@Id
	@Column(name = "EDUCATIONTYPEID", nullable = false)
	public int getEducationtypeid() {
		return this.educationtypeid;
	}

	public void setEducationtypeid(int educationtypeid) {
		this.educationtypeid = educationtypeid;
	}

	@Column(name = "NAME", nullable = false, length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "educationtypes")
	public Set<Groups> getGroupses() {
		return this.groupses;
	}

	public void setGroupses(Set<Groups> groupses) {
		this.groupses = groupses;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}

}

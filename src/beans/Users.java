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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Users generated by hbm2java
 */
@Entity
@Table(name = "users", schema = "public")
public class Users implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2458374090018885835L;
	private int userid;
	private TypeUser typeUser;
	private String login;
	private String name;
	private String password;
	private String comment;
	private Set<Students> studentses = new HashSet<Students>(0);

	public Users() {
	}


	@SequenceGenerator(allocationSize=1, initialValue=1, sequenceName="users_userid_seq", name="users_userid_seq")
	@GeneratedValue(generator="users_userid_seq", strategy=GenerationType.SEQUENCE)
	@Id
	@Column(name = "USERID", nullable = false)
	public int getUserid() {
		return this.userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USERTYPE", nullable = false)
	public TypeUser getTypeUser() {
		return this.typeUser;
	}

	public void setTypeUser(TypeUser typeUser) {
		this.typeUser = typeUser;
	}

	@Column(name = "LOGIN", length = 50)
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PASSWORD")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "COMMENT")
	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
	public Set<Students> getStudentses() {
		return this.studentses;
	}

	public void setStudentses(Set<Students> studentses) {
		this.studentses = studentses;
	}

}
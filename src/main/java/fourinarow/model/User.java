package fourinarow.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User implements java.io.Serializable {

	private static final long serialVersionUID = -6898704110923440929L;
	
	private Long idUser;
	private String username;
	private String password;
	private Date createdAt;
	
	public User() {
		
	}
	
	public User(Long idUser) {
		this.idUser = idUser;
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User(Long idUser, String username, String password, Date createdAt) {
		super();
		this.idUser = idUser;
		this.username = username;
		this.password = password;
		this.createdAt = createdAt;
	}
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_gen_seq")
    @SequenceGenerator(name="USER_gen_seq", sequenceName="USER_SEQ", allocationSize=1)
	public Long getIdUser() {
		return this.idUser;
	}
	
	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	@Column(unique=true)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column()
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column()
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	

}

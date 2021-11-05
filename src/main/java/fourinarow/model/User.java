package fourinarow.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name="users")
public class User implements java.io.Serializable {

	private static final long serialVersionUID = -6898704110923440929L;
	
	private Long idUser;
	private String username;
	private String password;
	private Timestamp createdAt;
	
	public User() {
		
	}
	
	public User(Long idUser) {
		this.idUser = idUser;
	}
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public User(Long idUser, String username, String password, Timestamp createdAt) {
		super();
		this.idUser = idUser;
		this.username = username;
		this.password = password;
		this.createdAt = createdAt;
	}
	
	@Id
	@Column(name="id", updatable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	  @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="USER_gen_seq")
//    @SequenceGenerator(name="USER_gen_seq", sequenceName="USER_SEQ", allocationSize=1)
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

	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	
	public JSONObject toJSON() {
		JSONObject o = new JSONObject();
		o.put("id", this.idUser);
		o.put("username", username);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		o.put( "createdAt", sdf.format(createdAt));
		return o;
	}
	
}

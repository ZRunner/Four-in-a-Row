package fourinarow.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tokens")
public class Token implements java.io.Serializable {

	private static final long serialVersionUID = -3584028907855103876L;
	
	private String token;
	private Long userId;
	private Timestamp createdAt;
	
	public Token() {
		
	}
	
	public Token(String token, Long userId) {
		this.token = token;
		this.userId = userId;
	}

	public Token(String token, Long userId, Timestamp createdAt) {
		super();
		this.token = token;
		this.userId = userId;
		this.createdAt = createdAt;
	}

	@Id
	@Column(unique=true)
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Column()
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
}


package form;

import javax.persistence.Column;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class ActorForm {

	private String	name;
	private String	surname;
	private String	email;
	private String	phone;
	private String	avatar;
	private String	username;


	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@NotBlank
	@Email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Pattern(regexp = "(^\\+?\\d+)?")
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Pattern(regexp = "^$|.+.(jpg|jpeg|gif|png)", message = "(jpg, jpeg, gif, png)")
	public String getAvatar() {
		return this.avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	@Size(min = 5, max = 32)
	@Column(unique = true)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}

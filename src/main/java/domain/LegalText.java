
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class LegalText extends DomainEntity {

	private String	type;
	private String	codeLanguage;
	private String	body;


	@NotBlank
	@Pattern(regexp = "(^TERMS|COOKIES$)")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getCodeLanguage() {
		return this.codeLanguage;
	}

	public void setCodeLanguage(String codeLanguage) {
		this.codeLanguage = codeLanguage;
	}

	@Lob
	@NotBlank
	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}

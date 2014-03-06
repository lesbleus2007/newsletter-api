package info.eurisko.core.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.springframework.beans.BeanUtils;

import info.eurisko.core.events.newsletters.NewsletterDetails;

import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Newsletter {
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Type(type = "pg-uuid")
	private UUID key; // = UUID.randomUUID()

	private Date dateTimeOfSubmission; // dateTimeOfSubmission

	public Newsletter() {
	}

	public UUID getKey() {
		return key;
	}

	public void setKey(UUID key) {
		this.key = key;
	}

	public Date getDateTimeOfSubmission() {
		return dateTimeOfSubmission;
	}

	public void setDateTimeOfSubmission(Date dateTimeOfSubmission) {
		this.dateTimeOfSubmission = dateTimeOfSubmission;
	}

	public NewsletterDetails toNewsletterDetails() {
		NewsletterDetails details = new NewsletterDetails();

		BeanUtils.copyProperties(this, details);

		return details;
	}

	public static Newsletter fromNewsletterDetails(NewsletterDetails newsletterDetails) {
		Newsletter newsletter = new Newsletter();

		BeanUtils.copyProperties(newsletterDetails, newsletter);

		return newsletter;
	}
}

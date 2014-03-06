package info.eurisko.core.events.newsletters;

import java.util.Date;
import java.util.UUID;

public class NewsletterDetails {

	private UUID key;
	private Date dateTimeOfSubmission;

	public NewsletterDetails() {
		key = null;
	}

	public NewsletterDetails(UUID key) {
		this.key = key;
	}

	public Date getDateTimeOfSubmission() {
		return this.dateTimeOfSubmission;
	}

	public void setDateTimeOfSubmission(Date dateTimeOfSubmission) {
		this.dateTimeOfSubmission = dateTimeOfSubmission;
	}

	public UUID getKey() {
		return key;
	}

	public void setKey(UUID key) {
		this.key = key;
	}
}

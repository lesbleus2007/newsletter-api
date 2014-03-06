package info.eurisko.rest.domain;

// {!begin import}
import org.springframework.hateoas.ResourceSupport;


// {!end import}

import info.eurisko.core.events.newsletters.NewsletterDetails;
import info.eurisko.rest.controller.*;

import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * This is added so that we can do jaxb serialisation.
 * This type of annotation is fine here, as this Newsletter implementation is made for integration with things like this.
 */
@SuppressWarnings("serial")
@XmlRootElement
// {!begin resourceSupport}
public class Newsletter extends ResourceSupport implements Serializable {
	// {!end resourceSupport}

	private Date dateTimeOfSubmission;

	private UUID key;

	public Date getDateTimeOfSubmission() {
		return dateTimeOfSubmission;
	}

	public UUID getKey() {
		return key;
	}

	public void setDateTimeOfSubmission(Date dateTimeOfSubmission) {
		this.dateTimeOfSubmission = dateTimeOfSubmission;
	}

	public void setKey(UUID key) {
		this.key = key;
	}

	public NewsletterDetails toNewsletterDetails() {
		NewsletterDetails details = new NewsletterDetails();

		details.setKey(key);
		details.setDateTimeOfSubmission(dateTimeOfSubmission);

		return details;
	}

	// {!begin fromNewsletterDetails}
	public static Newsletter fromNewsletterDetails(NewsletterDetails newsletterDetails) {
		Newsletter newsletter = new Newsletter();

		newsletter.dateTimeOfSubmission = newsletterDetails.getDateTimeOfSubmission();
		newsletter.key = newsletterDetails.getKey();

		// Adding the library, the above extends ResourceSupport and
		// this section is all that is actually needed in our model to add hateoas support.

		// Much of the rest of the framework is helping deal with the blending of domains that happens in many spring apps.
		// We have explicitly avoided that.
		// {!begin selfRel}
		newsletter.add(linkTo(NewsletterQueriesController.class).slash(newsletter.key).withSelfRel());
		// {!end selfRel}

		return newsletter;
	}
	// {!end fromNewsletterDetails}
}

package info.eurisko.rest.controller;

import info.eurisko.core.events.newsletters.CreateNewsletterEvent;
import info.eurisko.core.events.newsletters.NewsletterCreatedEvent;
import info.eurisko.core.services.NewsletterService;
import info.eurisko.rest.domain.Newsletter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/aggregators/newsletters")
public class NewsletterCommandsController {

    @Autowired
    private NewsletterService newsletterService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Newsletter> createNewsletter(@RequestBody Newsletter newsletter, UriComponentsBuilder builder) {

        NewsletterCreatedEvent newsletterCreated = newsletterService.createNewsletter(new CreateNewsletterEvent(newsletter.toNewsletterDetails()));

        Newsletter newNewsletter = Newsletter.fromNewsletterDetails(newsletterCreated.getDetails());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(
                builder.path("/aggregators/newsletters/{id}")
                        .buildAndExpand(newsletterCreated.getNewNewsletterKey().toString()).toUri());

        return new ResponseEntity<Newsletter>(newNewsletter, headers, HttpStatus.CREATED);
    }
}

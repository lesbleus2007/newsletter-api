package info.eurisko.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import info.eurisko.core.events.newsletters.*;
import info.eurisko.core.services.NewsletterService;
import info.eurisko.rest.domain.Newsletter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/aggregators/newsletters")
public class NewsletterQueriesController {

    @Autowired
    private NewsletterService newsletterService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Newsletter> getAllNewsletters() {
        List<Newsletter> newsletters = new ArrayList<Newsletter>();
        for (NewsletterDetails detail : newsletterService.requestAllNewsletters(new RequestAllNewslettersEvent()).getNewslettersDetails()) {
        	newsletters.add(Newsletter.fromNewsletterDetails(detail));
        }
        return newsletters;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<Newsletter> viewNewsletter(@PathVariable String id) {

        NewsletterDetailsEvent details = newsletterService.requestNewsletterDetails(new RequestNewsletterDetailsEvent(UUID.fromString(id)));

        if (!details.isEntityFound()) {
            return new ResponseEntity<Newsletter>(HttpStatus.NOT_FOUND);
        }

        Newsletter newsletter = Newsletter.fromNewsletterDetails(details.getNewsletterDetails());

        return new ResponseEntity<Newsletter>(newsletter, HttpStatus.OK);
    }
}

package info.eurisko.rest.controller;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;

import info.eurisko.core.events.newsletters.RequestNewsletterDetailsEvent;
import info.eurisko.core.services.NewsletterService;
import info.eurisko.rest.controller.NewsletterQueriesController;

import java.util.UUID;

import static info.eurisko.rest.controller.fixture.RestEventFixtures.newsletterDetailsEvent;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public class ViewNewsletterXmlIntegrationTest {

  MockMvc mockMvc;

  @InjectMocks
  NewsletterQueriesController controller;

  @Mock
  NewsletterService newsletterService;

  UUID key = UUID.fromString("f3512d26-72f6-4290-9265-63ad69eccc13");

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);

    this.mockMvc = standaloneSetup(controller)
            .setMessageConverters(new MappingJackson2HttpMessageConverter(),
                                  new Jaxb2RootElementHttpMessageConverter()).build();
  }

  @Test
  public void thatViewNewsletterRendersXMLCorrectly() throws Exception {

    when(newsletterService.requestNewsletterDetails(any(RequestNewsletterDetailsEvent.class))).thenReturn(
    		newsletterDetailsEvent(key));

    this.mockMvc.perform(
            get("/aggregators/newsletters/{id}", key.toString())
                    .accept(MediaType.TEXT_XML))
            .andDo(print())
            .andExpect(content().contentType(MediaType.TEXT_XML))
            .andExpect(xpath("/newsletter/key").string(key.toString()));
  }

  @Test
  public void thatViewNewsletterRendersJsonCorrectly() throws Exception {

    when(newsletterService.requestNewsletterDetails(any(RequestNewsletterDetailsEvent.class))).thenReturn(
    		newsletterDetailsEvent(key));

    //TODOCUMENT JSON Path in use here (really like this)

    this.mockMvc.perform(
            get("/aggregators/newsletters/{id}", key.toString())
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.key").value(key.toString()));
  }
}

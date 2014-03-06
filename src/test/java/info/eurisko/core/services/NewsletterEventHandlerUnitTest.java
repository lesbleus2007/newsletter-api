package info.eurisko.core.services;

import org.junit.Before;
import org.junit.Test;

import info.eurisko.core.domain.Newsletter;
import info.eurisko.core.events.newsletters.*;
import info.eurisko.core.repository.NewslettersMemoryRepository;
import info.eurisko.core.services.NewsletterEventHandler;

import java.util.Date;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class NewsletterEventHandlerUnitTest {

  NewsletterEventHandler uut;
  NewslettersMemoryRepository mockNewslettersMemoryRepository;

  @Before
  public void setupUnitUnderTest() {
    mockNewslettersMemoryRepository = mock(NewslettersMemoryRepository.class);
    uut = new NewsletterEventHandler(mockNewslettersMemoryRepository);
  }

  @Test
  public void addANewNewsletterToTheSystem() {
	Newsletter newsletter = new Newsletter();
	newsletter.setKey(UUID.randomUUID());
    newsletter.setDateTimeOfSubmission(new Date());

    when(mockNewslettersMemoryRepository.persist(any(Newsletter.class))).thenReturn(newsletter);

    CreateNewsletterEvent ev = new CreateNewsletterEvent(new NewsletterDetails());

    uut.createNewsletter(ev);

    verify(mockNewslettersMemoryRepository).persist(any(Newsletter.class));
    verifyNoMoreInteractions(mockNewslettersMemoryRepository);
  }

  @Test
  public void addTwoNewNewslettersToTheSystem() {
	Newsletter newsletter = new Newsletter();
	newsletter.setKey(UUID.randomUUID());
    newsletter.setDateTimeOfSubmission(new Date());

    when(mockNewslettersMemoryRepository.persist(any(Newsletter.class))).thenReturn(newsletter);

    CreateNewsletterEvent ev = new CreateNewsletterEvent(new NewsletterDetails());

    uut.createNewsletter(ev);
    uut.createNewsletter(ev);

    verify(mockNewslettersMemoryRepository, times(2)).persist(any(Newsletter.class));
    verifyNoMoreInteractions(mockNewslettersMemoryRepository);
  }
}

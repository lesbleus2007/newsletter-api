package info.eurisko.config;

import java.util.Set;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class WebAppInitializer implements WebApplicationInitializer {

	private static Logger LOG = LoggerFactory.getLogger(WebAppInitializer.class);

	@Override
	public void onStartup(final ServletContext servletContext) {
		final WebApplicationContext rootContext = createRootContext(servletContext);

		configureSpringMvc(servletContext, rootContext);

		configureSpringSecurity(servletContext, rootContext);
	}

	private WebApplicationContext createRootContext(final ServletContext servletContext) {
		final AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
		rootContext.register(CoreConfig.class, SecurityConfig.class);
		rootContext.refresh();

		servletContext.addListener(new ContextLoaderListener(rootContext));
		servletContext.setInitParameter("defaultHtmlEscape", "true");

		return rootContext;
	}

	private void configureSpringMvc(final ServletContext servletContext, final WebApplicationContext rootContext) {
		final AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
		mvcContext.register(MVCConfig.class);

		mvcContext.setParent(rootContext);

		ServletRegistration.Dynamic appServlet = servletContext.addServlet("webservice", new DispatcherServlet(mvcContext));
		appServlet.setLoadOnStartup(1);
		Set<String> mappingConflicts = appServlet.addMapping("/");

		if (!mappingConflicts.isEmpty()) {
			for (String s : mappingConflicts) {
				LOG.error("Mapping conflict: " + s);
			}
			throw new IllegalStateException("'webservice' cannot be mapped to '/'");
		}
	}

	private void configureSpringSecurity(final ServletContext servletContext, final WebApplicationContext rootContext) {
		final FilterRegistration.Dynamic springSecurity = servletContext.addFilter("springSecurityFilterChain",
				new DelegatingFilterProxy("springSecurityFilterChain", rootContext));
		springSecurity.addMappingForUrlPatterns(null, true, "/*");
	}
}

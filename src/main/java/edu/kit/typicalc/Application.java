package edu.kit.typicalc;

import com.vaadin.flow.component.page.AppShellConfigurator;

import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import edu.kit.typicalc.view.content.typeinferencecontent.TypeInferenceView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.vaadin.artur.helpers.LaunchUtil;

import java.util.regex.Pattern;

/**
 * Entry point of the Spring Boot application.
 */
@SpringBootApplication
public class Application extends SpringBootServletInitializer
        implements AppShellConfigurator, VaadinServiceInitListener {
    private static final Pattern ROUTE_PATTERN = Pattern.compile("/" + TypeInferenceView.ROUTE + "/[^/]+");

    /**
     * Main function executed in development mode.
     *
     * @param args empty array
     */
    public static void main(String[] args) {
        System.setProperty("user.home", "/home/arne/.cache");
        LaunchUtil.launchBrowserInDevelopmentMode(SpringApplication.run(Application.class, args));
    }

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.addRequestHandler((session, request, response) -> {
            // Vaadin does not set the error code:
            // https://github.com/vaadin/flow/issues/8942
            String url = request.getPathInfo();
            if (!url.equals("/") && !ROUTE_PATTERN.matcher(url).matches()) {
                response.setStatus(404);
            }
            return false;
        });
    }
}

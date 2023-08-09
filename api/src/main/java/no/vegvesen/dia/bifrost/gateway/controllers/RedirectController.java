package no.vegvesen.dia.bifrost.gateway.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * RedirectController class that handles redirecting the client to a specific location.
 * It's mainly used to redirect the root URL to the Swagger UI for API documentation.
 */
@Controller
public class RedirectController {

    /**
     * Handles a GET request to the root URL ("/") and redirects the client to the Swagger UI.
     * The HTTP status code 302 is used to perform the redirection.
     *
     * @param httpServletResponse The HttpServletResponse object to set the redirect status and location.
     */
    @GetMapping(value = "/")
    public void redirectToServices(HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Location", "/swagger-ui.html");
        httpServletResponse.setStatus(302);
    }
}

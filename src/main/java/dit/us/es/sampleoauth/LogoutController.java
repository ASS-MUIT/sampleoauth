/**
 * 
 */
package dit.us.es.sampleoauth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Para hacer un logout completo y efectivamente eliminar la sesión sso es necesario conectarse a /endsso
 * si se hace la conexió a /logout sólo se cierra la sesión local, lo que no sirve para nada porque el token sso sigue activo
 */

@Controller
public class LogoutController {

    @Value("${app.logout-endpoint}")
    private String logoutEndpoint;

    @Value("${app.post-logout-redirect-uri}")
    private String postLogoutRedirectUri;

    @GetMapping("/endsso")
    public String logout(HttpServletRequest request, HttpServletResponse response,
                         @AuthenticationPrincipal OidcUser oidcUser) throws ServletException {

        // Paso 1: logout local de Spring Security
        request.logout();

        // Paso 2: logout federado en WSO2 IS
        String idTokenA = oidcUser.getIdToken().getTokenValue();
        System.out.println("Token a: "+idTokenA);
        String idTokenB = oidcUser.getClaimAsString("isk");
        System.out.println("Token b: "+idTokenB);
        String redirectUrl = UriComponentsBuilder
                .fromUriString(logoutEndpoint)
                .queryParam("id_token_hint", idTokenA)
                .queryParam("post_logout_redirect_uri", postLogoutRedirectUri)
                .build()
                .toUriString();

        // Redirigir al endpoint de logout del Identity Server
        return "redirect:" + redirectUrl;
    }
}

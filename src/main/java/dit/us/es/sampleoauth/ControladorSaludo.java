/**
 * Basado en el tutorial
 * https://is.docs.wso2.com/en/7.0.0/quick-starts/springboot/
 * para la creación de aplicaciones springboot clientes oauth de is de wso2
 */
package dit.us.es.sampleoauth;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 */
@Controller
public class ControladorSaludo {
	@GetMapping("/")
	public String greeting(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
	
	    model.addAttribute("email", oidcUser.getEmail()); //dirección email del usuario
	    model.addAttribute("username", oidcUser.getClaimAsString("username")); //Nombre del usuario
	    model.addAttribute("userAttributes", oidcUser.getAttributes()); // TODOS
	    return "saludo";
	}
}

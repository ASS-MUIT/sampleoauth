/**
 * Basado en el tutorial
 * https://is.docs.wso2.com/en/7.0.0/quick-starts/springboot/
 * para la creación de aplicaciones springboot clientes oauth de is de wso2
 */
package dit.us.es.sampleoauth;

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
	    public String greeting(@RequestParam(name = "name", required = false, defaultValue = "World") String name,
	            Model model) {
	        model.addAttribute("name", name);
	        return "saludo";
	    }

}

package ed.iotssc.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomErrorController implements ErrorController {
	
    @RequestMapping(path = "/error")
    public String handle(HttpServletRequest request) {
        return "Oops";
    }

	@Override
	public String getErrorPath() {
		return null;
	}

}

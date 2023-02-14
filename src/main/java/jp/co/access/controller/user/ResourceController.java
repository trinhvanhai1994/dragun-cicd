package jp.co.access.controller.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

	@PreAuthorize("hasRole('USER')")
	@RequestMapping("/SH/hello")
	public String getUser() {
		return "Hello User";
	}
}

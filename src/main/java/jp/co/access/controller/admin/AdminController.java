package jp.co.access.controller.admin;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

	@ApiOperation(value = "Hello Admin")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Hello Admin successfully"),
			@ApiResponse(code = 400, message = "Bad request"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 500, message = "Internal server Error")
	})
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping("/AM/hello")
	public String getAdmin() {
		return "Hello Admin";
	}
}

package jp.co.access.controller;

import jp.co.access.request.AuthenticationRequest;
import jp.co.access.response.AuthenticationResponse;
import jp.co.access.exception.BasicException;
import jp.co.access.request.AccountRequest;
import jp.co.access.security.CustomUserDetailsService;
import jp.co.access.security.JwtUtil;
import jp.co.access.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static jp.co.access.utils.MessageConst.ACCOUNT_INCORRECT;
import static jp.co.access.utils.MessageConst.ACCOUNT_LOCKING;
import static jp.co.access.utils.MessageConst.E0002;
import static jp.co.access.utils.MessageConst.E0005;

@RestController
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;
	private final CustomUserDetailsService userDetailsService;
	private final AccountService accountService;
	private final JwtUtil jwtUtil;

	@Autowired
	public AuthenticationController(AuthenticationManager authenticationManager,
									CustomUserDetailsService userDetailsService,
									AccountService accountService, JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.userDetailsService = userDetailsService;
		this.accountService = accountService;
		this.jwtUtil = jwtUtil;
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (DisabledException e) {
			throw new BasicException(E0005, ACCOUNT_LOCKING, null);
		}
		catch (BadCredentialsException e) {
			throw new BasicException(E0002, ACCOUNT_INCORRECT, null);
		}
		
		UserDetails userdetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		String token = jwtUtil.generateToken(userdetails);
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Void> createAccount(@RequestBody AccountRequest accountRequest) {
		accountService.save(accountRequest);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
}

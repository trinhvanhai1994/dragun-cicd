package jp.co.access.security;

import jp.co.access.entity.Account;
import jp.co.access.exception.BasicException;
import jp.co.access.repository.AccountRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final AccountRepository accountRepository;

	public CustomUserDetailsService(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		Account user = accountRepository.findByUsername(username);
		if (user != null) {
			List<SimpleGrantedAuthority> roles = Collections.singletonList(new SimpleGrantedAuthority(user.getAuthorities()));
			return new User(user.getUsername(), user.getPassword(), roles);
		}
		throw new BasicException("User not found with the name " + username);
	}
}

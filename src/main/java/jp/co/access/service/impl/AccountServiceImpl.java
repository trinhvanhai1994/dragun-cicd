package jp.co.access.service.impl;

import jp.co.access.entity.Account;
import jp.co.access.enums.AccountStatus;
import jp.co.access.enums.Authorities;
import jp.co.access.repository.AccountRepository;
import jp.co.access.request.AccountRequest;
import jp.co.access.service.AccountService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final PasswordEncoder bcryptEncoder;
    private final AccountRepository accountRepository;

    public AccountServiceImpl(PasswordEncoder bcryptEncoder, AccountRepository accountRepository) {
        this.bcryptEncoder = bcryptEncoder;
        this.accountRepository = accountRepository;
    }

    @Override
    public void save(AccountRequest accountRequest) {
		Account account = new Account();
		account.setUsername(accountRequest.getUsername());
		account.setPassword(bcryptEncoder.encode(accountRequest.getPassword()));
		account.setAuthorities(Authorities.ROLE_USER.name());
        account.setAccountStatus(AccountStatus.VALID);
		accountRepository.save(account);
    }
}

package jp.co.access.config;

import jp.co.access.entity.Account;
import jp.co.access.enums.AccountStatus;
import jp.co.access.enums.Authorities;
import jp.co.access.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * LoadDatabase
 */
@Configuration
public class LoadDatabase {

    @Value("${access.account.username}")
    private String username;

    @Value("${access.account.password}")
    private String password;

    private static final String MAIL_ADDRESS = "admin@hugp.com";
    private static final String FULL_NAME = "admin";

    @Bean
    public CommandLineRunner initDatabase(AccountRepository accountRepository) {
        return (args) -> {
            // Add Account
            createAccount(accountRepository);
        };
    }

    private void createAccount(AccountRepository accountRepository) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Account account = accountRepository.findByUsername(username);
        if (account == null) {
            account = new Account();
            account.setAuthorities(Authorities.ROLE_ADMIN.name());
            account.setName(FULL_NAME);
            account.setEmailAddress(MAIL_ADDRESS);
            account.setPassword(passwordEncoder.encode(password));
            account.setUsername(username);
            account.setAccountStatus(AccountStatus.VALID);
            account.setDeleted(false);
            accountRepository.save(account);
        }
    }
}

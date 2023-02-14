package jp.co.access.entity;

import jp.co.access.enums.AccountStatus;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "accounts")
@Data
public class Account extends BaseEntity {

	@Id
	@SequenceGenerator(name = "account_id_seq", sequenceName = "account_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_id_seq")
	private Long id;

	// ログインID
	@Column(length = 20)
	private String username;

	// パスワード
	@NotNull
	private String password;

	// ステータス
	@NotNull
	@Enumerated(EnumType.STRING)
	private AccountStatus accountStatus;

	// 権限
	@NotNull
	private String authorities;

	// ハッシュ
	private String hash;

	@Column(length = 150)
	private String name;

	@Column(length = 150)
	private String emailAddress;

	@Column(length = 200)
	private String department;

	@Column(length = 20)
	private String phoneNumber;

	// 削除
	private boolean deleted;
}

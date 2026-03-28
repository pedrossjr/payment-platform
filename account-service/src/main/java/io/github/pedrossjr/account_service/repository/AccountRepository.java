package io.github.pedrossjr.account_service.repository;

import io.github.pedrossjr.account_service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}

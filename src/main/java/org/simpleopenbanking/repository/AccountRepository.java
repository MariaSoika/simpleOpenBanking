package org.simpleopenbanking.repository;

import org.simpleopenbanking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
  }
package org.simpleopenbanking.repository;

import org.simpleopenbanking.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Page<Transaction> findTop10ByReceiverAccountIbanOrderByTimestampDesc(Long accountIban, Pageable pageable);
}
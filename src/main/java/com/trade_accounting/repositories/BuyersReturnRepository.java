package com.trade_accounting.repositories;

import com.trade_accounting.models.BuyersReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyersReturnRepository extends JpaRepository<BuyersReturn, Long>, JpaSpecificationExecutor<BuyersReturn> {

}

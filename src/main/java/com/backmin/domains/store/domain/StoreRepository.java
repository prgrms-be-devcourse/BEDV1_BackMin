package com.backmin.domains.store.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Page<Store> findPagingStoresByCategoryId(Long categoryId, Pageable pageable);

}

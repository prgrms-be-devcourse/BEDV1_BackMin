package com.backmin.domains.store.domain;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Page<Store> findPagingStoresByCategoryId(Long categoryId, Pageable pageable);

    @Query("select distinct s from Store s "
           + "join fetch s.menus m ")
    Optional<Store> findStoreById(Long storeId);

}

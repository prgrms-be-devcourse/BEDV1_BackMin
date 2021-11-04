package com.backmin.domains.store.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Page<Store> findPagingStoresByCategoryId(Long categoryId, Pageable pageable);

    @EntityGraph(attributePaths = {"menus"})
    Optional<Store> findStoreById(Long storeId);

    @EntityGraph(attributePaths = {"menus"})
    Page<Store> findStoresByNameContaining(String name, Pageable pageable);

    boolean existsById(Long storeId);

}

package com.backmin.domains.menu.domain;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    @Query("select distinct m from Menu m where m.store.id = :storeId and m.isBest = true")
    List<Menu> findBestMenusByStore(@Param("storeId") Long storeId);

}

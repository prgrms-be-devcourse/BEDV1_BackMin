package com.backmin.domains.review.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT COUNT(r) from Review r WHERE r.store.id = :storeId")
    int getReviewTotalCountByStore(@Param("storeId") Long storeId);

    @Query("SELECT COALESCE(AVG(r.score),0) FROM Review r WHERE r.store.id = :storeId")
    double getReviewAverageByStore(@Param("storeId") Long storeId);

}

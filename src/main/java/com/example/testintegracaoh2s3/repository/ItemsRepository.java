package com.example.testintegracaoh2s3.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.example.testintegracaoh2s3.model.Items;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemsRepository extends JpaRepository<Items, Integer> {
    
    @Query(value = "SELECT * FROM items WHERE cart_id = null", nativeQuery = true)
    List<Items> itemsWithoutCartId();
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE items SET cart_id = :cartId IN :itemsId", nativeQuery = true)
    void updateDataExecucao(@Param("cartId") Integer cartId, @Param("itemsId") List<String> itemsId);
}

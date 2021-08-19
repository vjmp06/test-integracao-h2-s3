package com.example.testintegracaoh2s3.repository;

import javax.transaction.Transactional;

import com.example.testintegracaoh2s3.model.Items;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ItemsRepository extends JpaRepository <Items, Integer>{
    @Transactional
    @Modifying
    //@Query(value = "update items set items.cart_id = ?1 where items.cart_id = null;", nativeQuery = true)
    @Query("UPDATE items i SET i.cart.cart_id = ?1 WHERE i.cart.cart_id = null")
    void updateDataExecucao(Integer cartId);
}
    

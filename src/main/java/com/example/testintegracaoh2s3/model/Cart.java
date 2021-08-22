package com.example.testintegracaoh2s3.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Integer id;

    @Column
    private LocalDateTime dataExecucao;
    
    @OneToMany(mappedBy="cart")
    private Set<Items> items;

    public Cart() {       
    }

    public Cart(LocalDateTime dataExecucao) {
        this.dataExecucao = dataExecucao;
    }

    public Cart(LocalDateTime dataExecucao, Set<Items> items) {
        this.dataExecucao = dataExecucao;
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Items> getItems() {
        return items;
    }

    public void setItems(Set<Items> items) {
        this.items = items;
    }

    public LocalDateTime getDataExecucao() {
        return dataExecucao;
    }

    public void setDataExecucao(LocalDateTime dataExecucao) {
        this.dataExecucao = dataExecucao;
    }

    @Override
    public String toString() {
        return "Cart [dataExecucao=" + dataExecucao + ", id=" + id + ", items=" + items + "]";
    }

    
}

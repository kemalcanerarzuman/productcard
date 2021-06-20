package com.kca.efectura.card.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kca.efectura.card.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

	public Product findByName(String name);
}

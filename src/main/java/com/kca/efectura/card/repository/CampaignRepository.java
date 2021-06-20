package com.kca.efectura.card.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.kca.efectura.card.entity.Campaign;

@Repository
public interface CampaignRepository extends CrudRepository<Campaign, Integer> {

	public Campaign findByPriority(int priority);
}

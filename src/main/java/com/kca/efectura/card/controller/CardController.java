package com.kca.efectura.card.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kca.efectura.card.entity.Campaign;
import com.kca.efectura.card.entity.Product;
import com.kca.efectura.card.repository.CampaignRepository;
import com.kca.efectura.card.repository.ProductRepository;

@RestController
@RequestMapping("/api")
public class CardController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	CampaignRepository campaignRepository;

	@PostMapping("/card/nodiscount")
	public double getTotalAmountWithoutDiscount(@RequestBody List<Product> card) {
		double totalAmount = 0d;
		for (Product product : card) {
			product = productRepository.findById(product.getId()).get();
			if (product != null) {
				totalAmount += product.getPrice();				
			}
		}
		return totalAmount;
	}

	@GetMapping("/products")
	public List<Product> getAllProductsSorted() {
		List<Product> products = new ArrayList<Product>();
		Iterator<Product> iterator = productRepository.findAll().iterator();
		while (iterator.hasNext()) {
			products.add(iterator.next());
		}
		return products.stream().sorted((c1, c2) -> Integer.compare(c1.getId(), c2.getId())).collect(Collectors.toList());
	}

	@GetMapping("/campaigns")
	public List<Campaign> getAllCampaignsSorted() {
		List<Campaign> campaigns = new ArrayList<Campaign>();
		Iterator<Campaign> iterator = campaignRepository.findAll().iterator();
		while (iterator.hasNext()) {
			campaigns.add(iterator.next());
		}
		return campaigns.stream().sorted((c1, c2) -> Integer.compare(c1.getPriority(), c2.getPriority())).collect(Collectors.toList());
	}

	@PostMapping("/card")
	public double getTotalAmountWithDiscounts(@RequestBody List<Product> card) {
		double totalAmount = 0d;
		List<Campaign> campaignsSorted = getAllCampaignsSorted();
		List<Product> cardFromDB = new ArrayList<Product>();

		for (Product product : card) {
			cardFromDB.add(productRepository.findById(product.getId()).get());
		}

		List<Product> remaining = new ArrayList<Product>(cardFromDB);
		for (Campaign campaign : campaignsSorted) {
			while (remaining.containsAll(campaign.getProducts())) {
				totalAmount += campaign.calculateCampaignPrice();
				for (int i = 0; i < campaign.getProducts().size(); i++) {
					remaining.remove(campaign.getProducts().get(i));
				}
				if (remaining.size() == 0)
					break;
			}
		}

		for (Product product : remaining) {
			totalAmount += product.getPrice();
		}

		return totalAmount;
	}
}
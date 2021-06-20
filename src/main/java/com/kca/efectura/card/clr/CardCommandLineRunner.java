package com.kca.efectura.card.clr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.kca.efectura.card.entity.Campaign;
import com.kca.efectura.card.entity.Product;
import com.kca.efectura.card.repository.CampaignRepository;
import com.kca.efectura.card.repository.ProductRepository;

@Component
public class CardCommandLineRunner implements CommandLineRunner {

	@Autowired
	ProductRepository productRepository;

	@Autowired
	CampaignRepository campaignRepository;

	private Product prodA = new Product("A", 13000);
	private Product prodB = new Product("B", 12000);
	private Product prodC = new Product("C", 11000);
	private Product prodD = new Product("D", 10000);
	private Product prodE = new Product("E", 9000);
	private Product prodF = new Product("F", 8000);
	private Product prodG = new Product("G", 7000);
	private Product prodH = new Product("H", 6000);
	private Product prodI = new Product("I", 5000);
	private Product prodJ = new Product("J", 4000);
	private Product prodK = new Product("K", 3000);
	private Product prodL = new Product("L", 2000);
	private Product prodM = new Product("M", 1000);
	private List<Product> products = Arrays.asList(prodA, prodB, prodC, prodD, prodE, prodF, prodG, prodH, prodI, prodJ, prodK, prodL, prodM);

	private int priority = 1;

	@Override
	public void run(String... args) throws Exception {
		productRepository.saveAll(products);

		List<Campaign> campaigns = new ArrayList<Campaign>();
		Campaign campaign1 = new Campaign(priority++, 10);
		campaign1.add(prodA);
		campaign1.add(prodB);
		Campaign campaign2 = new Campaign(priority++, 6);
		campaign2.add(prodD);
		campaign2.add(prodE);
		Campaign campaign3 = new Campaign(priority++, 3);
		campaign3.add(prodE);
		campaign3.add(prodF);
		campaign3.add(prodG);
		Campaign campaign4 = new Campaign(priority++, 5, false);
		campaign4.add(prodA);
		campaign4.add(prodK);
		Campaign campaign5 = new Campaign(priority++, 5, false);
		campaign5.add(prodA);
		campaign5.add(prodL);
		Campaign campaign6 = new Campaign(priority++, 5, false);
		campaign6.add(prodA);
		campaign6.add(prodM);
		campaigns.addAll(Arrays.asList(campaign1, campaign2, campaign3, campaign4, campaign5, campaign6));
		
		for (int i = 0; i < products.size() - 4; i++) {
			for (int j = i + 1; j < products.size() - 3; j++) {
				for (int k = j + 1; k < products.size() - 2; k++) {
					for (int l = k + 1; l < products.size() - 1; l++) {
						for (int m = l + 1; m < products.size(); m++) {
							if (!products.get(i).getName().equals("A") && !products.get(i).getName().equals("C")
									&& !products.get(j).getName().equals("A") && !products.get(j).getName().equals("C")
									&& !products.get(k).getName().equals("A") && !products.get(k).getName().equals("C")
									&& !products.get(l).getName().equals("A") && !products.get(l).getName().equals("C")
									&& !products.get(m).getName().equals("A") && !products.get(m).getName().equals("C")) {
								Campaign campaign = new Campaign(priority++, 20);
								campaign.add(products.get(i));
								campaign.add(products.get(j));
								campaign.add(products.get(k));
								campaign.add(products.get(l));
								campaign.add(products.get(m));
								campaigns.add(campaign);
							}
						}
					}
				}
			}
		}
		for (int i = 0; i < products.size() - 3; i++) {
			for (int j = i + 1; j < products.size() - 2; j++) {
				for (int k = j +1; k < products.size() - 1; k++) {
					for (int l = k + 1; l < products.size(); l++) {
							if (!products.get(i).getName().equals("A") && !products.get(i).getName().equals("C")
									&& !products.get(j).getName().equals("A") && !products.get(j).getName().equals("C")
									&& !products.get(k).getName().equals("A") && !products.get(k).getName().equals("C")
									&& !products.get(l).getName().equals("A") && !products.get(l).getName().equals("C")) {
								Campaign campaign = new Campaign(priority++, 10);
								campaign.add(products.get(i));
								campaign.add(products.get(j));
								campaign.add(products.get(k));
								campaign.add(products.get(l));
								campaigns.add(campaign);
						}
					}
				}
			}
		}
		for (int i = 0; i < products.size() - 2; i++) {
			for (int j = i + 1; j < products.size() - 1; j++) {
				for (int k = j + 1; k < products.size(); k++) {
							if (!products.get(i).getName().equals("A") && !products.get(i).getName().equals("C")
									&& !products.get(j).getName().equals("A") && !products.get(j).getName().equals("C")
									&& !products.get(k).getName().equals("A") && !products.get(k).getName().equals("C")) {
								Campaign campaign = new Campaign(priority++, 5);
								campaign.add(products.get(i));
								campaign.add(products.get(j));
								campaign.add(products.get(k));
								campaigns.add(campaign);
					}
				}
			}
		}
		campaignRepository.saveAll(campaigns);

	}

}

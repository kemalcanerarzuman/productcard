package com.kca.efectura.card.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table
public class Campaign {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;

	@Column
	private int priority;

	@Column
	private double percentage;

	@Column
	private boolean toAll;

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
	@JoinTable(name = "product_campaign", joinColumns = @JoinColumn(name = "campaign_id"), inverseJoinColumns = @JoinColumn(name = "product_id"))
	private List<Product> products;

	public Campaign() {
	}

	public Campaign(int priority, double percentage) {
		this(priority, percentage, true);
	}

	public Campaign(int priority, double percentage, boolean toAll) {
		this.priority = priority;
		this.percentage = percentage;
		this.toAll = toAll;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public boolean isToAll() {
		return toAll;
	}

	public void setToAll(boolean toAll) {
		this.toAll = toAll;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public void add(Product product) {
		if (this.products == null)
			this.products = new ArrayList<Product>();
		for (Product prod : products) {
			if (prod.equals(product))
				return;
		}
		this.products.add(product);
		product.add(this);
	}

	public void remove(Product product) {
		for (Product prod : this.products) {
			if (prod.equals(product)) {
				this.products.remove(prod);
				prod.remove(this);
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Campaign other = (Campaign) obj;
		if (priority != other.priority)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + priority;
		return result;
	}

	@Override
	public String toString() {
		return "Campaign [id=" + id + ", priority=" + priority + ", percentage=" + percentage + ", toAll=" + toAll
				+ "]";
	}

	public double calculateCampaignPrice() {
		double price = 0;
		for (int i = 0; i < getProducts().size(); i++) {
			Product product = getProducts().get(i);
			if (!isToAll() && i == getProducts().size() - 1) {
				price += product.getPrice();
			} else {
				price += product.getPrice() * ((100 - getPercentage()) / 100);
			}
		}
		return price;
	}

}
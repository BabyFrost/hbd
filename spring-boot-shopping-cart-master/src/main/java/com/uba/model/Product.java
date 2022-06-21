package com.uba.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name")
    @Length(min = 3, message = "*Name must have at least 5 characters")
    private String name;
   
    @Column(name = "maxDay", nullable = true)
    private int maxDays;
    
    @Column(name = "numberOfBids", nullable = true)
    private int numberOfBids;
    
    public int getNumberOfBids() {
		return numberOfBids;
	}

	public void setNumberOfBids(int numberOfBids) {
		this.numberOfBids = numberOfBids;
	}

	public int getMaxDays() {
		return maxDays;
	}

	public void setMaxDays(int maxDays) {
		this.maxDays = maxDays;
	}

	@Column(name = "bidStart", nullable = true)
    private Date bidStart; 
    
    @Column(name = "bidEnd", nullable = true)
    private Date bidEnd;
    
   

	@Column(name = "solid")
    private String solid;

    public Date getBidStart() {
		return bidStart;
	}

	public void setBidStart(Date bidStart) {
		this.bidStart = bidStart;
	}

	public Date getBidEnd() {
		return bidEnd;
	}

	public void setBidEnd(Date bidEnd) {
		this.bidEnd = bidEnd;
	}

	public String getSolid() {
		return solid;
	}

	public void setSolid(String solid) {
		this.solid = solid;
	}

	@Column(name = "description")
    private String description;

    @Column(name = "quantity", nullable = false)
    @Min(value = 0, message = "*Quantity has to be non negative number")
    private Integer quantity;

    @Column(name = "price", nullable = false)
    @DecimalMin(value = "0.00", message = "*Price has to be non negative number")
    private BigDecimal price;
    
    @Column(name = "currentAmount", nullable = true)
    private BigDecimal currentAmount;
    @Column(name = "startAmount", nullable = true)
    private BigDecimal startAmount;
    
    @Column(name = "newAmount", nullable = true)
    private BigDecimal newAmount;

 

	public BigDecimal getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(BigDecimal currentAmount) {
		this.currentAmount = currentAmount;
	}

	public BigDecimal getNewAmount() {
		return newAmount;
	}

	public void setNewAmount(BigDecimal newAmount) {
		this.newAmount = newAmount;
	}

	public BigDecimal getStartAmount() {
		return startAmount;
	}

	public void setStartAmount(BigDecimal startAmount) {
		this.startAmount = startAmount;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal unitPrice) {
        this.price = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        return id.equals(product.id);
    }
    
    @Lob
    private byte[] data;

    public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Override
    public int hashCode() {
        return id.hashCode();
    }
}

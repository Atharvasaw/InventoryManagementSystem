package com.invent.InventoryManagementSystem.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.invent.InventoryManagementSystem.dtos.CategoryDTO;
import com.invent.InventoryManagementSystem.dtos.ProductDTO;
import com.invent.InventoryManagementSystem.dtos.Response;
import com.invent.InventoryManagementSystem.dtos.SupplierDTO;
import com.invent.InventoryManagementSystem.dtos.UserDTO;
import com.invent.InventoryManagementSystem.enums.TransactionStatus;
import com.invent.InventoryManagementSystem.enums.TransactionType;
import com.invent.InventoryManagementSystem.enums.UserRole;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions")
@Data
@Builder
public class Transaction {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer totalProducts;

    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType; // pruchase, sale, return

    @Enumerated(EnumType.STRING)
    private TransactionStatus status; //pending, completed, processing

    private String description;
    private String note;

    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updateAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private Supplier supplier;
    
    public Transaction() {
    }

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", totalProducts=" + totalProducts + ", totalPrice=" + totalPrice
				+ ", transactionType=" + transactionType + ", status=" + status + ", description=" + description
				+ ", note=" + note + ", createdAt=" + createdAt + ", updateAt=" + updateAt + ", product=" + product
				+ ", user=" + user + ", supplier=" + supplier + "]";
	}
	
	
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Integer getTotalProducts() {
		return totalProducts;
	}



	public void setTotalProducts(Integer totalProducts) {
		this.totalProducts = totalProducts;
	}



	public BigDecimal getTotalPrice() {
		return totalPrice;
	}



	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}



	public TransactionType getTransactionType() {
		return transactionType;
	}



	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}



	public TransactionStatus getStatus() {
		return status;
	}



	public void setStatus(TransactionStatus status) {
		this.status = status;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getNote() {
		return note;
	}



	public void setNote(String note) {
		this.note = note;
	}



	public LocalDateTime getUpdateAt() {
		return updateAt;
	}



	public void setUpdateAt(LocalDateTime updateAt) {
		this.updateAt = updateAt;
	}



	public Product getProduct() {
		return product;
	}



	public void setProduct(Product product) {
		this.product = product;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}



	public Supplier getSupplier() {
		return supplier;
	}



	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}



	public LocalDateTime getCreatedAt() {
		return createdAt;
	}



	private Transaction(Builder builder) {
        this.totalProducts = builder.totalProducts;
        this.totalPrice = builder.totalPrice;
        this.transactionType= builder.transactionType;
        this.status=builder.status;
        this.description=builder.description;
        this.note=builder.note;
        this.updateAt=builder.updateAt;
        this.product=builder.product;
        this.user=builder.user;
        this.supplier=builder.supplier;
        
    }

	 public static Builder builder() {
	        return new Builder();
	    } 
    
	 public static class Builder {
		 private Integer totalProducts;
		 private BigDecimal totalPrice;
		 private TransactionType transactionType;
		 private TransactionStatus status;
		 private String description;
		 private String note;
		 private LocalDateTime updateAt;
		 private Product product;
		 private User user;
		 private Supplier supplier;

	        public Builder totalProducts(int totalProducts) {
	            this.totalProducts = totalProducts;
	            return this;
	        }

	        public Builder totalPrice(BigDecimal totalPrice) {
	            this.totalPrice = totalPrice;
	            return this;
	        }
	        
	        public Builder transactionType(TransactionType transactionType) {
	            this.transactionType = transactionType;
	            return this;
	        }
	        
	        public Builder status(TransactionStatus status) {
	            this.status = status;
	            return this;
	        }

	        public Builder description(String description) {
	            this.description = description;
	            return this;
	        }
	        
	        public Builder note(String note) {
	            this.note =note;
	            return this;
	        }
	        
	        public Builder updateAt(LocalDateTime updateAt) {
	            this.updateAt =updateAt;
	            return this;
	        }
	        
	        public Builder product(Product product) {
	            this.product =product;
	            return this;
	        }
	        
	        public Builder user(User user) {
	            this.user =user;
	            return this;
	        }
	        
	        public Builder supplier(Supplier supplier) {
	            this.supplier =supplier;
	            return this;
	        }
	        
	       
	        public Transaction build() {
	            return new Transaction(this);
	        }
	 }
	

}

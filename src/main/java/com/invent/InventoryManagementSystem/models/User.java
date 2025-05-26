package com.invent.InventoryManagementSystem.models;

import java.time.LocalDateTime;
import java.util.List;

import com.invent.InventoryManagementSystem.dtos.Response;
import com.invent.InventoryManagementSystem.enums.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(force=true)
@Table(name="users")
@Data
@Builder
public class User {
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @NotBlank(message = "Name is required")
	    private String name;

	    @Column(unique = true)
	    @NotBlank(message = "Email is required")
	    private String email;

	    @NotBlank(message = "Password is required")
	    private String password;

	    @NotBlank(message = "PhoneNumber is required")
	    @Column(name = "phone_number")
	    private String phoneNumber;

	    @Enumerated(EnumType.STRING)
	    private UserRole role;

	    @OneToMany(mappedBy = "user")
	    private List<Transaction> transactions;

	    @Column(name = "created_at")
	    private final LocalDateTime createdAt = LocalDateTime.now();
	    
	    public User() {
	    
	    }

		@Override
		public String toString() {
			return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password
					+ ", phoneNumber=" + phoneNumber + ", role=" + role + ", transactions=" + transactions
					+ ", createdAt=" + createdAt + "]";
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

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getPhoneNumber() {
			return phoneNumber;
		}

		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public UserRole getRole() {
			return role;
		}

		public void setRole(UserRole role) {
			this.role = role;
		}

		public List<Transaction> getTransactions() {
			return transactions;
		}

		public void setTransactions(List<Transaction> transactions) {
			this.transactions = transactions;
		}

		public LocalDateTime getCreatedAt() {
			return createdAt;
		}

		private User(Builder builder) {
	        this.id = builder.id;
	        this.name = builder.name;
	        this.email= builder.email;
	        this.password=builder.password;
	        this.phoneNumber=builder.phoneNumber;
	        this.role=builder.role;
	        
	    }

		 public static Builder builder() {
		        return new Builder();
		    } 
	    
		 public static class Builder {
		        private long id;
		        private String name;
		        private String email;
		        private String password;
		        private String phoneNumber;
		        private UserRole role;

		        public Builder id(int id) {
		            this.id = id;
		            return this;
		        }

		        public Builder name(String name) {
		            this.name = name;
		            return this;
		        }
		        
		        public Builder email(String email) {
		            this.email = email;
		            return this;
		        }
		        
		        public Builder phoneNumber(String phoneNumber) {
		            this.phoneNumber = phoneNumber;
		            return this;
		        }
		        
		        public Builder password(String password) {
		            this.password = password;
		            return this;
		        }
		        
		        public Builder role(UserRole role) {
		            this.role = role;
		            return this;
		        }
		        

		        public User build() {
		            return new User(this);
		        }
		 }
		
		
	 

}

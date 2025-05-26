package com.invent.InventoryManagementSystem.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.invent.InventoryManagementSystem.dtos.Response;
import com.invent.InventoryManagementSystem.models.User;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthUser implements UserDetails{

	private User user;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority(user.getRole().name()));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getEmail();
	}

	@Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//	public static Object builder() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	private AuthUser(Builder builder) {
        this.user = builder.user;
        
        
    }

	 public static Builder builder() {
	        return new Builder();
	    } 
    
	 public static class Builder {
	        private User user;

	        public Builder user(User user) {
	            this.user = user;
	            return this;
	        }

	        public AuthUser build() {
	            return new AuthUser(this);
	        }
	 }
	
	
}

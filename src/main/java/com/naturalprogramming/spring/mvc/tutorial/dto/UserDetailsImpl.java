package com.naturalprogramming.spring.mvc.tutorial.dto;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.naturalprogramming.spring.mvc.tutorial.entities.User;
import com.naturalprogramming.spring.mvc.tutorial.entities.User.Role;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 473773535664086347L;
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public UserDetailsImpl(User user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(user.getRoles().size() + 1);

		for (Role role : user.getRoles())
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));

		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
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

}

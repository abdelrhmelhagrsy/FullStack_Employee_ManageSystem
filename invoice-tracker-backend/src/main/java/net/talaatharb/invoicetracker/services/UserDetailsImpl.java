package net.talaatharb.invoicetracker.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.talaatharb.invoicetracker.models.User;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	public static UserDetailsImpl build(User user) {
		List<? extends GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).toList();

		return new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(), authorities,
				user.getIsEnabled(),user.isNotPasswordExpired());
	}

	private Collection<? extends GrantedAuthority> authorities;

	private String email;

	private Long id;

	@JsonIgnore
	private String password;

	private String username;


	private Boolean isEnabled = true;
    
	private Boolean isCredentialsNonExpired;

	public UserDetailsImpl(Long id, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
	}
	
	public UserDetailsImpl(Long id, String username, String email, String password,
			Collection<? extends GrantedAuthority> authorities,Boolean isEnabled, Boolean isCredentialsNonExpired) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.authorities = authorities;
		this.isEnabled = isEnabled;
		this.isCredentialsNonExpired = isCredentialsNonExpired;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getEmail() {
		return email;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public int hashCode() {
		return id.hashCode();
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
		return isCredentialsNonExpired;	}

	@Override
	public boolean isEnabled() {
		if(isEnabled==null)
			return true;
		return isEnabled;
	}
}

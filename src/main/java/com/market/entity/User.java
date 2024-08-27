package com.market.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.market.domain.UserRole;
import com.market.domain.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@RequiredArgsConstructor
public class User implements UserDetails {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role = UserRole.CUSTOMER;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.ACTIVE;
    
    @CreatedDate
    @Column(name = "join_date", nullable = false)
    private LocalDate joinDate = LocalDate.now();

    @LastModifiedDate
    @Column(name = "last_updated")
    private LocalDate lastUpdated;

    @Column(name = "withdraw_date")
    private LocalDate withdrawDate;

    @Builder
	public User(String name, String email, String password, UserRole role) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<SimpleGrantedAuthority> roleList = new ArrayList<>();
		roleList.add(new SimpleGrantedAuthority(role.name()));
		return roleList;
	}

	@Override
	public String getUsername() {
		return email;
	}
    
    
}

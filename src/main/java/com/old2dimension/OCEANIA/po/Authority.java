package com.old2dimension.OCEANIA.po;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "authority")
public class Authority implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return this.name;
    }

    public void setAuthority(String name) {
        this.name = name;
    }
}

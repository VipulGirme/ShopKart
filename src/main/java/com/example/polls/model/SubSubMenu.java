package com.example.polls.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "subsubmenu",uniqueConstraints = { @UniqueConstraint(columnNames = "subsubmenuname") })
public class SubSubMenu {

 	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 	@Column(name = "id", unique = true, nullable = false)
    private Long id;

 	@NotBlank
 	private String subsubmenuname; 	
 	
 	@JsonIgnore
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
 	private SubMenu subMenu;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getSubsubmenuname() {
		return subsubmenuname;
	}

	public void setSubsubmenuname(String subsubmenuname) {
		this.subsubmenuname = subsubmenuname;
	}

	public SubMenu getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(SubMenu subMenu) {
		this.subMenu = subMenu;
	}
}

package com.example.polls.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "mainmenu",uniqueConstraints = { @UniqueConstraint(columnNames = "menuname") })
public class MainMenu {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 	@Column(unique = true,nullable = false)
	    private Long id;

	 	@NotBlank
	 	private String menuname; 	
	 	
	 	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	 	@JoinColumn(name = "main_menu_id",referencedColumnName = "id")
	 	private List<SubMenu> subMenuList = new ArrayList<>();

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getMenuname() {
			return menuname;
		}

		public void setMenuname(String menuname) {
			this.menuname = menuname;
		}

		public List<SubMenu> getSubMenuList() {
			return subMenuList;
		}

		public void setSubMenuList(List<SubMenu> subMenuList) {
			this.subMenuList = subMenuList;
		}


}

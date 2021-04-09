package com.example.polls.model;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "submenu",uniqueConstraints = { @UniqueConstraint(columnNames = "submenuname") })
public class SubMenu {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	 	@Column(name = "id", unique = true, nullable = false)
	    private Long id;

	 	@NotBlank
	 	private String submenuname; 	
	 	
	 	@JsonIgnore
		@ManyToOne
		@OnDelete(action = OnDeleteAction.CASCADE)
	 	private MainMenu mainMenu;
	 	
	 	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	 	@JoinColumn(name = "sub_menu_id",referencedColumnName = "id")
	 	private List<SubSubMenu> subSubMenuList = new ArrayList<>();

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getSubmenuname() {
			return submenuname;
		}

		public void setSubmenuname(String submenuname) {
			this.submenuname = submenuname;
		}

		public MainMenu getMainMenu() {
			return mainMenu;
		}

		public void setMainMenu(MainMenu mainMenu) {
			this.mainMenu = mainMenu;
		}

		public List<SubSubMenu> getSubSubMenuList() {
			return subSubMenuList;
		}

		public void setSubSubMenuList(List<SubSubMenu> subSubMenuList) {
			this.subSubMenuList = subSubMenuList;
		}

}

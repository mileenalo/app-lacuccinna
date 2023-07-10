package com.projectapi.lacuccina.demo.model;

import com.projectapi.lacuccina.demo.DTO.MenuRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

@Table(name = "MENU")
@Entity(name = "MENU")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Menu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String title;
    private String url;
    private Float price;

    public Menu(MenuRequestDTO food){
        this.description = food.description();
        this.title = food.title();
        this.url = food.url();
        this.price = food.price();
    }

}

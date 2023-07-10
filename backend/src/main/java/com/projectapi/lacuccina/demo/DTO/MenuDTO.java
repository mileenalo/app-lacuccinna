package com.projectapi.lacuccina.demo.DTO;

import com.projectapi.lacuccina.demo.model.Menu;

import java.util.Optional;

public record MenuDTO(Long id, String description, String title, String url, Float price) {
    public MenuDTO(Menu menu){
        this(menu.getId(), menu.getDescription(), menu.getTitle(), menu.getUrl(), menu.getPrice());
    }
}


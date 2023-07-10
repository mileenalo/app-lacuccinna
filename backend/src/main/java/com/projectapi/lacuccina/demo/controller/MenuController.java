package com.projectapi.lacuccina.demo.controller;
import java.util.List;

import com.projectapi.lacuccina.demo.DTO.MenuDTO;
import com.projectapi.lacuccina.demo.DTO.MenuRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projectapi.lacuccina.demo.service.MenuService;
@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping
    public List<MenuDTO> getAll() {
        return menuService.getAllFoods();
    }

    @PostMapping
    public void addFoodMenu(@RequestBody MenuRequestDTO food) {
        menuService.addFoodMenu(food);
    }

    @GetMapping("/food/{id}")
    public MenuDTO getEspecficFoodById(@PathVariable Long id) {
        return menuService.getEspecficFoodById(id);
    }



//
//    @PutMapping(value = "/{id}")
//    public Menu update(@PathVariable("id") String id, @RequestBody Menu menu) {
//        return this.menuService.update(id, menu);
//    }
//
//    @DeleteMapping(value = "/{id}")
//    public Menu delete(@PathVariable("id") String id) {
//        return this.menuService.delete(id);
//    }
}

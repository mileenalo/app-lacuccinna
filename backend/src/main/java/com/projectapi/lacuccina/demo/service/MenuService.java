package com.projectapi.lacuccina.demo.service;
import java.util.List;
import java.util.Optional;

import com.projectapi.lacuccina.demo.DTO.MenuDTO;
import com.projectapi.lacuccina.demo.DTO.MenuRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectapi.lacuccina.demo.model.Menu;
import com.projectapi.lacuccina.demo.repository.MenuRepository;

@Service
public class MenuService {
    @Autowired
    private MenuRepository menuRepository;

    public List<MenuDTO> getAllFoods() {
        List<MenuDTO> menuList = menuRepository.findAll().stream().map(MenuDTO::new).toList();
        return menuList;
    }

    public void addFoodMenu(MenuRequestDTO food) {
        Menu foodData = new Menu(food);
        menuRepository.save(foodData);
    }

    public MenuDTO getEspecficFoodById(Long id) {
        Optional<Menu> optionalFood = menuRepository.findById(id);
        if (optionalFood.isPresent()){
            Menu menu = optionalFood.get();
            return converterDTO(menu);
        } else {
            return null;
        }
    }

    public MenuDTO converterDTO(Menu menu) {
        MenuDTO dto = new MenuDTO(menu);
        return dto;
    }
//
//    public Menu update(Integer id, Menu menu) {
//        Menu updated = this.menuRepository.findById(id).get();
//        updated.setDesc(menu.getDesc());
//        updated.setTitle(menu.getTitle());
//        updated.setPrice(menu.getPrice());
//
//        return this.menuRepository.save(updated);
//    }
//
//    public Menu delete(String id) {
//        Menu menu = this.menuRepository.findById(id).get();
//        if (menu != null)
//            this.menuRepository.deleteById(id);
//        return menu;
//    }
}

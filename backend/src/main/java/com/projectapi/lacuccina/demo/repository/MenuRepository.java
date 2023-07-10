package com.projectapi.lacuccina.demo.repository;

import com.projectapi.lacuccina.demo.DTO.MenuDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import com.projectapi.lacuccina.demo.model.Menu;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<MenuDTO> findByIdIn(List<Long> produtoIds);
}

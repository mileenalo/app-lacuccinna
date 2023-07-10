package com.projectapi.lacuccina.demo.model;

import com.projectapi.lacuccina.demo.DTO.MenuRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "PEDIDO")
@Entity(name = "PEDIDO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mesa;
    private String dtpedido;
    private String hrpedido;
    private String status;
    private int qtditens;
    private float valor;
}

package com.projectapi.lacuccina.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "ITENS_PEDIDO")
@Entity(name = "ITENS_PEDIDO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItensPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idpedido;
    private Long idmenu;
    private int qtditem;
    private float valor;
    private String obsitem;
}

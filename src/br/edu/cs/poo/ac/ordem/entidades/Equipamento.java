package br.edu.cs.poo.ac.ordem.entidades;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Equipamento {
    private String descricao;
    // private TipoEquipamento tipo;
    private boolean ehNovo;
    private double valorEstimado;
}

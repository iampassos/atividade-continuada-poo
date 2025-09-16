package br.edu.cs.poo.ac.ordem.entidades;

import lombok.experimental.SuperBuilder;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@SuperBuilder
public class Desktop extends Equipamento {
    private boolean ehServidor;
}

package br.edu.cs.poo.ac.ordem.entidades;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@AllArgsConstructor
public class OrdemServico implements Serializable {
    private Cliente cliente;
    private PrecoBase precoBase;
    private Notebook notebook;
    private Desktop desktop;
    private LocalDateTime dataHoraAbertura;
    private int prazoEmDias;
    private double valor;

    public LocalDate getDataEstimadaEntrega() {
        return dataHoraAbertura.toLocalDate().plusDays(prazoEmDias);
    }

    public String getNumero() {
        StringBuilder temp = new StringBuilder();

        temp.append(notebook != null ? notebook.getIdTipo() : desktop.getIdTipo());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        temp.append(dataHoraAbertura.format(formatter));

        if (cliente.getCpfCnpj().length() <= 11) {
            temp.append("000");
        }

        temp.append(cliente.getCpfCnpj());

        return temp.toString();
    }
}

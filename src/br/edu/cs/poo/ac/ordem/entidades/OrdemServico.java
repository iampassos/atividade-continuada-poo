package br.edu.cs.poo.ac.ordem.entidades;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@AllArgsConstructor
public class OrdemServico {
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

    // Ficou confuso...
    public String getNumero() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formatted = dataHoraAbertura.format(formatter);

        if (cliente.getCpfCnpj().length() == 14) {
            formatted = formatted + "000";
        }

        return formatted + cliente.getCpfCnpj();
    }
}

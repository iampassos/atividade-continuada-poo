package br.edu.cs.poo.ac.ordem.mediators;

import br.edu.cs.poo.ac.ordem.daos.DesktopDAO;
import br.edu.cs.poo.ac.ordem.daos.NotebookDAO;
import br.edu.cs.poo.ac.ordem.entidades.Notebook;
import br.edu.cs.poo.ac.ordem.entidades.Desktop;
import br.edu.cs.poo.ac.utils.ListaString;
import br.edu.cs.poo.ac.utils.StringUtils;

public class EquipamentoMediator {
    private static EquipamentoMediator instancia;
    private DesktopDAO desktopDAO;
    private NotebookDAO notebookDAO;

    private EquipamentoMediator() {
        desktopDAO = new DesktopDAO();
        notebookDAO = new NotebookDAO();
    }

    public static EquipamentoMediator getInstancia() {
        return instancia != null ? instancia : new EquipamentoMediator();
    }

    public ResultadoMediator incluirNotebook(Notebook note) {
        ResultadoMediator resultado = validarNotebook(note);

        if (!resultado.isValidado()) {
            return resultado;
        }

        Notebook notebook = buscarNotebook(note.getIdTipo() + note.getSerial());
        if (notebook != null) {
            resultado.getMensagensErro().adicionar("Serial do notebook já existente");
            return resultado;
        }

        notebookDAO.incluir(note);
        return new ResultadoMediator(true, true, resultado.getMensagensErro());
    }

    public ResultadoMediator incluirDesktop(Desktop desk) {
        ResultadoMediator resultado = validarDesktop(desk);

        if (!resultado.isValidado()) {
            return resultado;
        }

        Desktop desktop = buscarDesktop(desk.getIdTipo() + desk.getSerial());
        if (desktop != null) {
            resultado.getMensagensErro().adicionar("Serial do desktop já existente");
            return resultado;
        }

        desktopDAO.incluir(desk);
        return new ResultadoMediator(true, true, resultado.getMensagensErro());
    }

    public ResultadoMediator alterarNotebook(Notebook note) {
        ResultadoMediator resultado = validarNotebook(note);

        if (!resultado.isValidado()) {
            return resultado;
        }

        Notebook notebook = buscarNotebook(note.getIdTipo() + note.getSerial());
        if (notebook == null) {
            resultado.getMensagensErro().adicionar("Serial do notebook não existente");
            return resultado;
        }

        notebookDAO.alterar(note);
        return new ResultadoMediator(true, true, resultado.getMensagensErro());
    }

    public ResultadoMediator alterarDesktop(Desktop desk) {
        ResultadoMediator resultado = validarDesktop(desk);

        if (!resultado.isValidado()) {
            return resultado;
        }

        Desktop desktop = buscarDesktop(desk.getIdTipo() + desk.getSerial());
        if (desktop == null) {
            resultado.getMensagensErro().adicionar("Serial do desktop não existente");
            return resultado;
        }

        desktopDAO.alterar(desk);
        return new ResultadoMediator(true, true, resultado.getMensagensErro());
    }

    public ResultadoMediator excluirNotebook(String idTipoSerial) {
        ListaString lista = new ListaString();

        if (StringUtils.estaVazia(idTipoSerial)) {
            lista.adicionar("Id do tipo + serial do notebook não informado");
            return new ResultadoMediator(false, false, lista);
        }

        Notebook notebook = buscarNotebook(idTipoSerial);
        if (notebook == null) {
            lista.adicionar("Serial do notebook não existente");
            return new ResultadoMediator(true, false, lista);
        }

        notebookDAO.excluir(idTipoSerial);
        return new ResultadoMediator(lista.tamanho() == 0, true, lista);
    }

    public ResultadoMediator excluirDesktop(String idTipoSerial) {
        ListaString lista = new ListaString();

        if (StringUtils.estaVazia(idTipoSerial)) {
            lista.adicionar("Id do tipo + serial do desktop não informado");
            return new ResultadoMediator(false, false, lista);
        }

        Desktop desktop = buscarDesktop(idTipoSerial);
        if (desktop == null) {
            lista.adicionar("Serial do desktop não existente");
            return new ResultadoMediator(true, false, lista);
        }

        desktopDAO.excluir(idTipoSerial);
        return new ResultadoMediator(lista.tamanho() == 0, true, lista);
    }

    public Notebook buscarNotebook(String idTipoSerial) {
        return StringUtils.estaVazia(idTipoSerial) ? null : notebookDAO.buscar(idTipoSerial);
    }

    public Desktop buscarDesktop(String idTipoSerial) {
        return StringUtils.estaVazia(idTipoSerial) ? null : desktopDAO.buscar(idTipoSerial);
    }

    public ResultadoMediator validarDesktop(Desktop desk) {
        ListaString lista = new ListaString();

        if (desk == null) {
            lista.adicionar("Desktop não informado");
            return new ResultadoMediator(false, false, lista);
        }

        ResultadoMediator resultado = validar(
                new DadosEquipamento(desk.getSerial(), desk.getDescricao(), desk.isEhNovo(), desk.getValorEstimado()));

        return new ResultadoMediator(resultado.getMensagensErro().tamanho() == 0, false, resultado.getMensagensErro());
    }

    public ResultadoMediator validarNotebook(Notebook note) {
        ListaString lista = new ListaString();

        if (note == null) {
            lista.adicionar("Notebook não informado");
            return new ResultadoMediator(false, false, lista);
        }

        ResultadoMediator resultado = validar(
                new DadosEquipamento(note.getSerial(), note.getDescricao(), note.isEhNovo(), note.getValorEstimado()));

        return new ResultadoMediator(resultado.getMensagensErro().tamanho() == 0, false, resultado.getMensagensErro());
    }

    public ResultadoMediator validar(DadosEquipamento equip) {
        ListaString lista = new ListaString();

        if (equip == null) {
            lista.adicionar("Dados básicos do equipamento não informados");
            return new ResultadoMediator(false, false, lista);
        }

        if (StringUtils.estaVazia(equip.getDescricao())) {
            lista.adicionar("Descrição não informada");
        } else if (StringUtils.tamanhoExcedido(equip.getDescricao(), 150)) {
            lista.adicionar("Descrição tem mais de 150 caracteres");
        } else if (StringUtils.tamanhoMenor(equip.getDescricao(), 10)) {
            lista.adicionar("Descrição tem menos de 10 caracteres");
        }

        if (StringUtils.estaVazia(equip.getSerial()))

        {
            lista.adicionar("Serial não informado");
        }

        if (equip.getValorEstimado() <= 0) {
            lista.adicionar("Valor estimado menor ou igual a zero");
        }

        return new ResultadoMediator(lista.tamanho() == 0, false, lista);
    }
}

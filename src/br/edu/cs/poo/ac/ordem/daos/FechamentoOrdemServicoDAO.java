package br.edu.cs.poo.ac.ordem.daos;

import java.io.Serializable;

import br.edu.cs.poo.ac.ordem.entidades.FechamentoOrdemServico;

public class FechamentoOrdemServicoDAO extends DAOGenerico {
    public FechamentoOrdemServicoDAO() {
        super(FechamentoOrdemServicoDAO.class);
    }

    public FechamentoOrdemServico buscar(String codigo) {
        return (FechamentoOrdemServico) cadastroObjetos.buscar(codigo);
    }

    public boolean incluir(FechamentoOrdemServico fechamentoOrdemServico) {
        if (buscar(fechamentoOrdemServico.getNumeroOrdemServico()) == null) {
            cadastroObjetos.incluir(fechamentoOrdemServico, fechamentoOrdemServico.getNumeroOrdemServico());
            return true;
        }
        return false;
    }

    public boolean alterar(FechamentoOrdemServico fechamentoOrdemServico) {
        if (buscar(fechamentoOrdemServico.getNumeroOrdemServico()) != null) {
            cadastroObjetos.alterar(fechamentoOrdemServico, fechamentoOrdemServico.getNumeroOrdemServico());
            return true;
        }
        return false;
    }

    public boolean excluir(String codigo) {
        if (buscar(codigo) != null) {
            cadastroObjetos.excluir(codigo);
            return true;
        }
        return false;
    }

    public FechamentoOrdemServico[] buscarTodos() {
        Serializable[] ret = cadastroObjetos.buscarTodos();
        FechamentoOrdemServico[] retorno;

        if (ret != null && ret.length > 0) {
            retorno = new FechamentoOrdemServico[ret.length];
            for (int i = 0; i < ret.length; i++) {
                retorno[i] = (FechamentoOrdemServico) ret[i];
            }
        } else {
            retorno = new FechamentoOrdemServico[0];
        }

        return retorno;
    }
}

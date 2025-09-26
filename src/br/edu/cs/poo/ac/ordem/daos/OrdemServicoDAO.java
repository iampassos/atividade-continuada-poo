package br.edu.cs.poo.ac.ordem.daos;

import java.io.Serializable;

import br.edu.cs.poo.ac.ordem.entidades.OrdemServico;

public class OrdemServicoDAO extends DAOGenerico {
    public OrdemServicoDAO() {
        super(OrdemServicoDAO.class);
    }

    public OrdemServico buscar(String codigo) {
        return (OrdemServico) cadastroObjetos.buscar(codigo);
    }

    public boolean incluir(OrdemServico ordemServico) {
        if (buscar(ordemServico.getNumero()) == null) {
            cadastroObjetos.incluir(ordemServico, ordemServico.getNumero());
            return true;
        }
        return false;
    }

    public boolean alterar(OrdemServico ordemServico) {
        if (buscar(ordemServico.getNumero()) != null) {
            cadastroObjetos.alterar(ordemServico, ordemServico.getNumero());
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

    public OrdemServico[] buscarTodos() {
        Serializable[] ret = cadastroObjetos.buscarTodos();
        OrdemServico[] retorno;

        if (ret != null && ret.length > 0) {
            retorno = new OrdemServico[ret.length];
            for (int i = 0; i < ret.length; i++) {
                retorno[i] = (OrdemServico) ret[i];
            }
        } else {
            retorno = new OrdemServico[0];
        }

        return retorno;
    }
}

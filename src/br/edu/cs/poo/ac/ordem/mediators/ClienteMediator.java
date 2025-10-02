package br.edu.cs.poo.ac.ordem.mediators;

import java.time.LocalDate;

import br.edu.cs.poo.ac.ordem.daos.ClienteDAO;
import br.edu.cs.poo.ac.ordem.entidades.Cliente;
import br.edu.cs.poo.ac.ordem.entidades.Contato;
import br.edu.cs.poo.ac.utils.ListaString;
import br.edu.cs.poo.ac.utils.ValidadorCPFCNPJ;
import br.edu.cs.poo.ac.utils.StringUtils;
import br.edu.cs.poo.ac.utils.ResultadoValidacaoCPFCNPJ;

public class ClienteMediator {
    private static ClienteMediator instancia;
    private ClienteDAO clienteDAO;

    private ClienteMediator() {
        clienteDAO = new ClienteDAO();
    }

    public static ClienteMediator getInstancia() {
        return instancia != null ? instancia : new ClienteMediator();
    }

    public ResultadoMediator incluir(Cliente cliente) {
        ResultadoMediator resultado = validar(cliente);

        if (!resultado.isValidado()) {
            return resultado;
        }

        Cliente existe = buscar(cliente.getCpfCnpj());
        if (existe != null) {
            resultado.getMensagensErro().adicionar("CPF/CNPJ já existente");
            return resultado;
        }

        clienteDAO.incluir(cliente);
        return new ResultadoMediator(true, true, resultado.getMensagensErro());
    }

    public ResultadoMediator alterar(Cliente cliente) {
        ResultadoMediator resultado = validar(cliente);

        if (!resultado.isValidado()) {
            return resultado;
        }

        Cliente existe = buscar(cliente.getCpfCnpj());
        if (existe == null) {
            resultado.getMensagensErro().adicionar("CPF/CNPJ inexistente");
            return resultado;
        }

        clienteDAO.alterar(cliente);
        return new ResultadoMediator(true, true, resultado.getMensagensErro());
    }

    public ResultadoMediator excluir(String cpfCnpj) {
        ListaString lista = new ListaString();

        if (StringUtils.estaVazia(cpfCnpj)) {
            lista.adicionar("CPF/CNPJ não informado");
            return new ResultadoMediator(false, false, lista);
        }

        Cliente existe = buscar(cpfCnpj);
        if (existe == null) {
            lista.adicionar("CPF/CNPJ inexistente");
            return new ResultadoMediator(true, false, lista);
        }

        clienteDAO.excluir(cpfCnpj);
        return new ResultadoMediator(true, true, lista);
    }

    public Cliente buscar(String cpfCnpj) {
        return StringUtils.estaVazia(cpfCnpj) ? null : clienteDAO.buscar(cpfCnpj);
    }

    public ResultadoMediator validar(Cliente cliente) {
        ListaString lista = new ListaString();

        if (cliente == null) {
            lista.adicionar("Cliente não informado");
            return new ResultadoMediator(false, false, lista);
        }

        if (StringUtils.estaVazia(cliente.getCpfCnpj())) {
            lista.adicionar("CPF/CNPJ não informado");
        } else {
            ResultadoValidacaoCPFCNPJ resultado = ValidadorCPFCNPJ.validarCPFCNPJ(cliente.getCpfCnpj());

            if (resultado.getErroValidacao() != null) {
                switch (resultado.getErroValidacao()) {
                    case CPF_CNPJ_NAO_E_CPF_NEM_CNPJ:
                        lista.adicionar("Não é CPF nem CNJP");
                        break;
                    case CPF_CNPJ_COM_DV_INVALIDO:
                        lista.adicionar("CPF ou CNPJ com dígito verificador inválido");
                        break;
                    default:
                        break;
                }
            }
        }

        if (StringUtils.estaVazia(cliente.getNome())) {
            lista.adicionar("Nome não informado");
        } else if (StringUtils.tamanhoExcedido(cliente.getNome(), 50)) {
            lista.adicionar("Nome tem mais de 50 caracteres");
        }

        Contato contato = cliente.getContato();
        if (contato == null) {
            lista.adicionar("Contato não informado");

            if (cliente.getDataCadastro() == null) {
                lista.adicionar("Data do cadastro não informada");
            } else if (cliente.getDataCadastro().isAfter(LocalDate.now())) {
                lista.adicionar("Data do cadastro não pode ser posterior à data atual");
            }
        } else {
            if (cliente.getDataCadastro() == null) {
                lista.adicionar("Data do cadastro não informada");
            } else if (cliente.getDataCadastro().isAfter(LocalDate.now())) {
                lista.adicionar("Data do cadastro não pode ser posterior à data atual");
            }

            String celular = contato.getCelular();
            String email = contato.getEmail();

            if (StringUtils.estaVazia(celular) && StringUtils.estaVazia(email)) {
                lista.adicionar("Celular e e-mail não foram informados");
            } else if (!StringUtils.estaVazia(email) && !StringUtils.emailValido(email)) {
                lista.adicionar("E-mail está em um formato inválido");
            } else if (!StringUtils.estaVazia(celular) && !StringUtils.telefoneValido(celular)) {
                lista.adicionar("Celular está em um formato inválido");
            } else if (StringUtils.estaVazia(celular) && contato.isEhZap()) {
                lista.adicionar("Celular não informado e indicador de zap ativo");
            }
        }

        return new ResultadoMediator(lista.tamanho() == 0, false, lista);
    }
}

package br.edu.cs.poo.ac.utils;

public class ValidadorCPFCNPJ {
    public static ResultadoValidacaoCPFCNPJ validarCPFCNPJ(String cpfCnpj) {
        boolean cpf = isCPF(cpfCnpj);
        boolean cnpj = isCNPJ(cpfCnpj);

        ErroValidacaoCPFCNPJ erro = null;

        if (cpf && !cnpj) {
            erro = validarCPF(cpfCnpj);
        } else if (cnpj && !cpf) {
            erro = validarCNPJ(cpfCnpj);
        } else {
            erro = ErroValidacaoCPFCNPJ.CPF_CNPJ_NAO_E_CPF_NEM_CNPJ;
        }

        return new ResultadoValidacaoCPFCNPJ(cpf, cnpj, erro);
    }

    public static boolean isCPF(String valor) {
        if (valor == null || valor.isBlank()) {
            return false;
        }

        for (char c : valor.toCharArray()) {
            if (c < '0' || c > '9') {
                return false;
            }
        }

        return valor.length() == 11;
    }

    public static boolean isCNPJ(String valor) {
        if (valor == null || valor.isBlank()) {
            return false;
        }

        for (char c : valor.toCharArray()) {
            if (c < '0' || c > '9') {
                return false;
            }
        }

        return valor.length() == 14;
    }

    public static ErroValidacaoCPFCNPJ validarCPF(String cpf) {
        if (StringUtils.estaVazia(cpf)) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_NULO_OU_BRANCO;
        }

        if (cpf.length() != 11) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_TAMANHO_INVALIDO;
        }

        for (char c : cpf.toCharArray()) {
            if (c < '0' || c > '9') {
                return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_CARACTERES_INVALIDOS;
            }
        }

        if (!isDigitoVerificadorValidoCPF(cpf)) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO;
        }

        return null;
    }

    public static ErroValidacaoCPFCNPJ validarCNPJ(String cnpj) {
        if (StringUtils.estaVazia(cnpj)) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_NULO_OU_BRANCO;
        }

        if (cnpj.length() != 14) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_TAMANHO_INVALIDO;
        }

        for (char c : cnpj.toCharArray()) {
            if (c < '0' || c > '9') {
                return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_CARACTERES_INVALIDOS;
            }
        }

        if (!isDigitoVerificadorValidoCNPJ(cnpj)) {
            return ErroValidacaoCPFCNPJ.CPF_CNPJ_COM_DV_INVALIDO;
        }

        return null;
    }

    private static boolean isDigitoVerificadorValidoCPF(String cpf) {
        if (cpf == null || cpf.isBlank())
            return false;

        cpf = cpf.replaceAll("\\D", "");
        if (cpf.length() != 11 || cpf.chars().distinct().count() == 1)
            return false;

        for (int t = 9; t < 11; t++) {
            int soma = 0;
            for (int i = 0; i < t; i++) {
                soma += (cpf.charAt(i) - '0') * (t + 1 - i);
            }
            int digito = (soma % 11 < 2) ? 0 : 11 - (soma % 11);
            if (digito != cpf.charAt(t) - '0')
                return false;
        }

        return true;
    }

    private static boolean isDigitoVerificadorValidoCNPJ(String cnpj) {
        if (cnpj == null || cnpj.isBlank())
            return false;

        cnpj = cnpj.replaceAll("\\D", "");
        if (cnpj.length() != 14 || cnpj.chars().distinct().count() == 1)
            return false;

        int[] pesos1 = { 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };
        int[] pesos2 = { 6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 };

        for (int t = 12; t <= 13; t++) {
            int soma = 0;
            int[] pesos = (t == 12) ? pesos1 : pesos2;
            for (int i = 0; i < pesos.length; i++) {
                soma += (cnpj.charAt(i) - '0') * pesos[i];
            }
            int digito = (soma % 11 < 2) ? 0 : 11 - (soma % 11);
            if (digito != cnpj.charAt(t) - '0')
                return false;
        }

        return true;
    }

}

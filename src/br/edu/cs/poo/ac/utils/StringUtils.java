package br.edu.cs.poo.ac.utils;

public class StringUtils {
    public static boolean estaVazia(String str) {
        return str == null || str.isBlank();
    }

    public static boolean tamanhoExcedido(String str, int tamanho) {
        if (tamanho < 0) {
            return false;
        }

        if (str == null) {
            return tamanho > 0;
        }

        return str.length() > tamanho;
    }

    public static boolean tamanhoMenor(String str, int tamanho) {
        if (tamanho < 0) {
            return false;
        }

        if (str == null || str.isBlank()) {
            return true;
        }

        return str.length() < tamanho;
    }

    public static boolean emailValido(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }

        if (!email.contains("@") || !email.contains(".") || email.contains(" ")) {
            return false;
        }

        return true;
    }

    public static boolean telefoneValido(String tel) {
        if (tel == null || tel.isBlank()) {
            return false;
        }

        if (!tel.contains("(") || !tel.contains(")") || tel.length() < 12 || tel.length() > 13) {
            return false;
        }

        return true;
    }

}

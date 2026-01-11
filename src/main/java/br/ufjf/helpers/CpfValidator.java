package br.ufjf.helpers;

public class CpfValidator {

    public static boolean validaCpf(String cpf) {

        cpf = cpf.replaceAll("\\D", "");

        if (cpf.length() != 11) return false;

        if (cpf.matches("(\\d)\\1{10}")) return false;

        int sum = 0;
        int weight = 10;

        for (int i = 0; i < 9; i++) {
            sum += (cpf.charAt(i) - '0') * weight--;
        }

        int digito1 = (sum * 10) % 11;
        if (digito1 == 10) digito1 = 0;

        if (digito1 != (cpf.charAt(9) - '0')) return false;

        sum = 0;
        weight = 11;

        for (int i = 0; i < 10; i++) {
            sum += (cpf.charAt(i) - '0') * weight--;
        }

        int digito2 = (sum * 10) % 11;
        if (digito2 == 10) digito2 = 0;

        return digito2 == (cpf.charAt(10) - '0');
    }
}

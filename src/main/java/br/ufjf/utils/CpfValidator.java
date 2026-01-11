package br.ufjf.utils;

public class CpfValidator {

    public static boolean validaCpf(String cpf) {

        cpf = cpf.replaceAll("\\D", "").trim();

        if(cpf.length() != 11){
            return false;
        }

        int primeiroDigito = Integer.parseInt(Character.toString(cpf.charAt(9)));
        int segundoDigito = Integer.parseInt(Character.toString(cpf.charAt(10)));

        int weight = 10;
        int sum = 0;

        for(int i = 0; i < cpf.length() - 3; i++) {
            int digitoAtual = Integer.parseInt(Character.toString(cpf.charAt(i)));
            sum += digitoAtual * weight--;
            System.out.print(digitoAtual + " ");
        }

        boolean primeiraVerificacao = (sum*10)%11 == primeiroDigito;

        if(!primeiraVerificacao) return false;

        weight = 11;
        sum = 0;

        for(int i = 0; i < cpf.length() - 2; i++) {
            int digitoAtual = Integer.parseInt(Character.toString(cpf.charAt(i)));
            sum += digitoAtual * weight--;
        }

        return (sum*10)%11 == segundoDigito;
    }
}

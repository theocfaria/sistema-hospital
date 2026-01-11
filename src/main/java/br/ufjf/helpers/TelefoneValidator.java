package br.ufjf.helpers;

public class TelefoneValidator {

    public static boolean validaTelefone(String telefone){

        String apenasDigitos = telefone.trim().replaceAll("\\D", "");

        if(apenasDigitos.length() > 11 || apenasDigitos.length() < 10 ){
            return false;
        }

        return true;
    }

    public static String formatarTelefone(String num) {

        String s = num.replaceAll("\\D", "").trim();

        if (s.length() == 11) {
            // (11) 98888-7777
            return String.format("(%s) %s-%s", s.substring(0, 2), s.substring(2, 7), s.substring(7));
        } else if (s.length() == 10) {
            // (11) 8888-7777
            return String.format("(%s) %s-%s", s.substring(0, 2), s.substring(2, 6), s.substring(6));
        }

        return num;
    }

}

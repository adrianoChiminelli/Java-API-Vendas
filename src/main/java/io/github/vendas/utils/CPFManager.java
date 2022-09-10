package io.github.vendas.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.MaskFormatter;

@Component
public class CPFManager {

    /**
     * Método que verifica se um CPF é válido
     *
     * @param cpf CPF informado para validação
     * @return Retorna um boolean informando se o CPF é válido ou não
     */
    public boolean checkCPF(String cpf) {

        if (!checkIfDigitsAreValid(cpf)) {
            return false;
        }
        if (cpf.contains(".") || cpf.contains("-")) {
            cpf = removeMascara(cpf);
        }

        List<Integer> nineDigitsArray = new ArrayList<>();

        for (int i = 0; i < (cpf.length() - 2); i++) {
            nineDigitsArray.add(Character.getNumericValue(cpf.charAt(i)));
        }

        String cpfToCompare = generateVerifierDigits(nineDigitsArray);

        return cpf.equals(cpfToCompare);
    }

    private String generateVerifierDigits(List<Integer> digitList) {
        int weight = 10;
        for (int i = 0; i < 2; i++) {
            if (i == 1) {
                weight = 11;
            }

            int sumDigitsWithWeight = 0;

            for (int digit : digitList) {
                sumDigitsWithWeight = sumDigitsWithWeight + (digit * weight);
                weight--;
            }
            int remainder = (sumDigitsWithWeight % 11);

            if (remainder < 2) {
                digitList.add(0);
            } else {
                digitList.add((11 - remainder));
            }
        }
        String cpf = "";
        for (int item : digitList) {
            cpf += item;
        }

        return cpf;
    }

    public String formatCPF(String cpf) {
        if (checkIfDigitsAreValid(cpf)) {
            String cpfFormatado = "";
            try {
                MaskFormatter mf = new MaskFormatter("###.###.###-##");
                mf.setValueContainsLiteralCharacters(false);
                cpfFormatado = mf.valueToString(cpf);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return cpfFormatado;
        } else {
            return cpf;
        }
    }

    private String removeMascara(String cpf) {
        return cpf.replaceAll("\\D", "");
    }

    private boolean checkIfDigitsAreValid(String cpf) {

        if (cpf.contains(".") || cpf.contains("-")) {
            cpf = removeMascara(cpf);
        }

        if (cpf.length() != 11) {
            return false;
        }

        for (int i = 0; i < cpf.length(); i++) {
            char c = cpf.charAt(i);
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

}

package com.robo.robo.service;

import com.robo.robo.entity.AbordagemEntity;
import com.robo.robo.service.whatsapp.WhatsAppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AbordagemHelpService {

    private final WhatsAppService whatsAppService;

    public void tryAbordar(AbordagemEntity a, String text) throws Exception {
        boolean sucesso = true;
        Exception ex = new Exception();
        try {
            Thread.sleep(1000L);
            whatsAppService.send(getWhatsAppNumberWithNonoDigito(a.getTelefone()), text);
        } catch (Exception e) {
            ex = e;
            sucesso = false;
            System.out.println("ERRO. TELEFONE: " + getWhatsAppNumberWithNonoDigito(a.getTelefone()));
        }
        try {
            Thread.sleep(1000L);
            whatsAppService.send(getWhatsAppNumberWithOutNonoDigito(a.getTelefone()), text);
        } catch (Exception e) {
            ex = e;
            sucesso = false;
            System.out.println("ERRO. TELEFONE: " + getWhatsAppNumberWithOutNonoDigito(a.getTelefone()));
        }
        if (!sucesso)
            throw ex;
    }


    private static String getWhatsAppNumberWithNonoDigito(String number) {
        String ddd = number.substring(0, 2);
        String numeroFormatado = "55" + ddd + "9" + number.substring(number.length() - 8);
        System.out.println("ABORDANDO COM NONO DIGITO " + numeroFormatado);
        return numeroFormatado;
    }

    private static String getWhatsAppNumberWithOutNonoDigito(String number) {
        String ddd = number.substring(0, 2);
        String numeroFormatado = "55" + ddd + number.substring(number.length() - 8);
        System.out.println("ABORDANDO SEM NONO DIGITO " + numeroFormatado);
        return numeroFormatado;
    }
}

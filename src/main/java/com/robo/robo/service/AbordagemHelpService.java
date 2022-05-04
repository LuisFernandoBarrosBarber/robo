package com.robo.robo.service;

import com.robo.robo.entity.AbordagemEntity;
import com.robo.robo.service.whatsapp.WhatsAppService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AbordagemHelpService {

    private final WhatsAppService whatsAppService;

    public void tryAbordar(AbordagemEntity a, String text) throws IOException {
        whatsAppService.send(a.getTelefone(), text);
    }
}

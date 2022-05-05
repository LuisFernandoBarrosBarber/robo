package com.robo.robo.service;

import com.robo.robo.entity.AbordagemEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.robo.robo.enumerator.EtapaAbordagem.PRIMEIRA_ETAPA;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportarService {

    private final FileService fileService;
    private final PhoneService phoneService;

    public List<AbordagemEntity> getAbordagensFromFile(List<String> alreadyInBase) {
        List<AbordagemEntity> abordagens = new ArrayList<>();

        try {
            InputStream is = fileService.getFileAsIOStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                AbordagemEntity entity = toEntity(line);
                // NAO SALVA SE NUMERO JA EXISTE
                if (!alreadyInBase.contains(entity.getTelefone())) {
                    abordagens.add(entity);
                }
            }
            is.close();
        } catch (Exception e) {
            log.error("Erro ao importar telefones");
            e.printStackTrace();
        }
        log.info("ENCONTRADOS " + abordagens.size() + " NOVOS TELEFONES");
        return abordagens;
    }

    private AbordagemEntity toEntity(String unformattedNumber) {
        return AbordagemEntity.builder()
                .criadoEm(LocalDateTime.now())
                .isAtivo(true)
                .telefoneNaoFormatado(unformattedNumber)
                .telefone(phoneService.formatNumberToSave(unformattedNumber))
                .etapaAbordagem(PRIMEIRA_ETAPA)
                .build();
    }
}

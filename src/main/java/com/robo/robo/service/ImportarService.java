package com.robo.robo.service;

import com.robo.robo.entity.AbordagemEntity;
import com.robo.robo.entity.IgnorarEntity;
import com.robo.robo.repository.AbordagemRepository;
import com.robo.robo.repository.IgnorarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import static com.robo.robo.enumerator.EtapaAbordagem.PRIMEIRA_ETAPA;
import static com.robo.robo.service.PhoneService.getNumber;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImportarService {

    private final FileService fileService;
    private final PhoneService phoneService;
    private final AbordagemRepository repository;
    private final IgnorarRepository ignorarRepository;

    public int getAbordagensFromFile() {
        int totalInseridos = 0;
        List<String> ignorarList = getIgnorarList();

        try {
            InputStream is = fileService.getFileAsIOStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                AbordagemEntity entity = toEntity(line);
                if (testAndSave(ignorarList, entity)) {
                    totalInseridos++;
                }
            }
            is.close();
        } catch (Exception e) {
            log.error("Erro ao importar telefones");
            e.printStackTrace();
        }
        return totalInseridos;
    }

    @Transactional
    public boolean testAndSave(List<String> ignorarList, AbordagemEntity entity) {
        if (!isIgnorado(entity.getTelefone(), ignorarList)) {
            try {
                repository.saveAndFlush(entity);
            } catch (DataIntegrityViolationException e) {
                log.info(entity.getTelefone() + " JÁ CADASTRADO!");
                return false;
            }
        }
        return true;
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

    private boolean isIgnorado(String telefone, List<String> ignorados) {
        return ignorados
                .stream()
                .anyMatch(ignorado -> getNumber(telefone).equals(getNumber(ignorado)));
    }

    private List<String> getIgnorarList() {
        return ignorarRepository.findAll()
                .stream()
                .map(IgnorarEntity::getTelefone)
                .collect(Collectors.toList());
    }

}

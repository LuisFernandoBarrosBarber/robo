package com.robo.robo.service.etapas;

import com.robo.robo.enumerator.EtapaAbordagem;
import org.springframework.stereotype.Service;

import static com.robo.robo.enumerator.EtapaAbordagem.PRIMEIRA_ETAPA;
import static com.robo.robo.enumerator.EtapaAbordagem.SEGUNDA_ETAPA;

@Service
public class PrimeiraEtapa implements Etapa {
    @Override
    public boolean matches(EtapaAbordagem e) {
        return e.equals(PRIMEIRA_ETAPA);
    }

    @Override
    public EtapaAbordagem nextEtapa() {
        return SEGUNDA_ETAPA;
    }

    @Override
    public String getTextToAbordar() {
        return "TEXTO DA PRIMEIRA ETAPA";
    }
}

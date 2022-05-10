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
        return """
                Bom dia, tudo bem?
                                
                Somos a *Barbeiro Agenda*, sistema para automatizar seu atendimento de clientes na barbearia!
                Temos como objetivo *economizar seu tempo*, através de um sistema interativo onde o cliente pode fazer o agendamento, além de entrar em contato direto com você, verificar outras informações da barbearia, como localização, rede socias e demais informações.
                                
                Gostariamos de te passar mais detalhes. Pode ser?
                """;
    }
}

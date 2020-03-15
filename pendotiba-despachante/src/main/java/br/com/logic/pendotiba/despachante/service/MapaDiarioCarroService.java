package br.com.logic.pendotiba.despachante.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.EntradaSaidaDeCarroDaGaragem;
import br.com.logic.pendotiba.core.model.MapaDiarioCarro;
import br.com.logic.pendotiba.core.model.Programacao;
import br.com.logic.pendotiba.core.repository.MapaDiarioCarroRepository;

@Service
@EnableScheduling
public class MapaDiarioCarroService {
	
	@Autowired
	MapaDiarioCarroRepository mapaDiarioCarroRepository;
	
	
	
	public void atualizarMapaPelaProgramacao(Programacao programacao) {
		List<MapaDiarioCarro> mapas = mapaDiarioCarroRepository.findByDataCompetenciaAndCarro(programacao.getDataCompetencia(), programacao.getCarroRealizado());
		if(!mapas.isEmpty()) {
			mapas.forEach(m -> { 
				m.setLinha(programacao.getLinha());
				mapaDiarioCarroRepository.save(m);
			});
		}
	}
	
	public void atualizarMapaPelaEntrada(EntradaSaidaDeCarroDaGaragem entradaSaidaDeCarroDaGaragem) {
		List<MapaDiarioCarro> mapas = mapaDiarioCarroRepository.findByDataCompetenciaAndCarro(entradaSaidaDeCarroDaGaragem.getDataCompetencia(), entradaSaidaDeCarroDaGaragem.getCarro());
		if(!mapas.isEmpty()) {
			mapas.forEach(m -> { 
				m.setLinha(entradaSaidaDeCarroDaGaragem.getLinha());
				mapaDiarioCarroRepository.save(m);
			});
		}
	}

}

package br.com.logic.pendotiba.logicbus.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.ViagemImportada;
import br.com.logic.pendotiba.core.repository.ViagemImportadaRepository;

@Service
public class ViagemImportadaService {
	
	@Autowired
	ViagemImportadaRepository viagemImportadaRepository;
	
	
	public void excluirViagensImportadasPorDataCompetencia(Date dataCompetencia) {
		List<ViagemImportada> viagens = viagemImportadaRepository.findByDataCompetencia(dataCompetencia);
		viagemImportadaRepository.delete(viagens);
	}


}

package br.com.logic.pendotiba.logicbus.service.relatorios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.logicbus.dto.relatorios.RelatorioMotoristaForaDoProgramadoDTO;
import br.com.logic.pendotiba.logicbus.filter.relatorios.RelatorioMotoristaForaDoProgramadoFilter;
import br.com.logic.pendotiba.logicbus.repo.relatorios.RelatorioMotoristaForaDoProgramadoRepositoryImpl;

@Service
public class RelatorioMotoristaForaDoProgramadoService {

	@Autowired
	RelatorioMotoristaForaDoProgramadoRepositoryImpl relatorioMotoristaForaDoProgramadoRepositoryImpl;
	
	
	public Page<RelatorioMotoristaForaDoProgramadoDTO> filtrar(RelatorioMotoristaForaDoProgramadoFilter filter, Pageable pageable) {
		List<RelatorioMotoristaForaDoProgramadoDTO> lista = relatorioMotoristaForaDoProgramadoRepositoryImpl.listar(filter);
		return new PageImpl<>(lista, pageable, lista.size());
	}

}

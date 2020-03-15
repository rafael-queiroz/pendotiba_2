package br.com.logic.pendotiba.logicbus.service.relatorios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.logicbus.dto.relatorios.RelatorioProgramacaoInciadaForaDoHorarioProgramadoDTO;
import br.com.logic.pendotiba.logicbus.filter.relatorios.RelatorioProgramacaoIniciadaForaDoHorarioProgramadoFilter;
import br.com.logic.pendotiba.logicbus.repo.relatorios.RelatorioProgramacaoIniciadaForaDoHorarioProgramadoRepositoryImpl;

@Service
public class RelatorioProgamacaoIniciadaForaDoHorarioProgramadoService {

	@Autowired
	RelatorioProgramacaoIniciadaForaDoHorarioProgramadoRepositoryImpl repository;
	
	
	public Page<RelatorioProgramacaoInciadaForaDoHorarioProgramadoDTO> filtrar(RelatorioProgramacaoIniciadaForaDoHorarioProgramadoFilter filter, Pageable pageable) {
		List<RelatorioProgramacaoInciadaForaDoHorarioProgramadoDTO> lista = repository.listar(filter);
		return new PageImpl<>(lista, pageable, lista.size());
	}

}

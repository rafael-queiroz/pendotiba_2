package br.com.logic.pendotiba.logicbus.service.relatorios;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.EntradaSaidaDeCarroDaGaragem;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.dto.relatorios.RelatorioEntradaSaidaPorHorarioDTO;
import br.com.logic.pendotiba.logicbus.filter.relatorios.RelatorioEntradaSaidaPorHorarioFilter;
import br.com.logic.pendotiba.logicbus.repo.EntradaSaidaDeCarroDaGaragemRepositoryImpl;

@Service
public class RelatorioEntradaSaidaPorHorarioService {

	@Autowired
	EntradaSaidaDeCarroDaGaragemRepositoryImpl entradaSaidaDeCarroDaGaragemRepositoryImpl;
	
	
	
	public List<RelatorioEntradaSaidaPorHorarioDTO> listarPorDatas(RelatorioEntradaSaidaPorHorarioFilter relatorioEntradaSaidaPorHorarioFilter) {
		List<RelatorioEntradaSaidaPorHorarioDTO> lista = new ArrayList<>();
		
		//List<Date> datas = entradaSaidaDeCarroDaGaragemFilter.getDatas();

		if (!relatorioEntradaSaidaPorHorarioFilter.getDatas().isEmpty()) {
			List<EntradaSaidaDeCarroDaGaragem> entradas = entradaSaidaDeCarroDaGaragemRepositoryImpl.filtrarParaRelatorioPorHorario(relatorioEntradaSaidaPorHorarioFilter);
	
			if(!entradas.isEmpty()) {
				relatorioEntradaSaidaPorHorarioFilter.getDatas().forEach(d -> lista.add(new RelatorioEntradaSaidaPorHorarioDTO(d)));
				for(EntradaSaidaDeCarroDaGaragem entrada : entradas) {
					for (RelatorioEntradaSaidaPorHorarioDTO rel : lista) {
						if(entrada.getDataHoraEntrada() != null && rel.getDataCompetencia().equals(entrada.getDataCompetencia())) {
							rel.adicionarQuantidadePorHora(DataUtil.getHora(entrada.getDataHoraEntrada()));
							break;
						}
					}
				}
			}
		}
		
		Collections.sort(lista);
		return lista;
	}

}

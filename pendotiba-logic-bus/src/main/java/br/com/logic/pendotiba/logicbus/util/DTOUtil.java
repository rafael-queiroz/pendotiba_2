package br.com.logic.pendotiba.logicbus.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.logic.pendotiba.core.model.Observacao;
import br.com.logic.pendotiba.core.model.PontoLinha;
import br.com.logic.pendotiba.core.model.Programacao;
import br.com.logic.pendotiba.core.model.Reclamacao;
import br.com.logic.pendotiba.core.model.Viagem;
import br.com.logic.pendotiba.logicbus.dto.CadastroViagemDTO;
import br.com.logic.pendotiba.logicbus.resources.dto.PontoLinhaDTO;
import br.com.logic.pendotiba.logicbus.resources.dto.ProgramacaoDTO;
import br.com.logic.pendotiba.logicbus.resources.dto.RespostaIdDTO;
import br.com.logic.pendotiba.logicbus.resources.dto.ViagemDTO;
import br.com.logic.pendotiba.logicbus.resources.dto.ViagemExtraDTO;
import br.com.logic.pendotiba.logicbus.resources.dto.ViagemPerdidaDTO;
import br.com.logic.pendotiba.logicbus.resources.dto.ViagemProximasChegadasDTO;

public class DTOUtil {
	
	
	// PROGRAMAÇÃO
	public static List<ProgramacaoDTO> programacoesParaProgramacoesDTO(List<Programacao> programacoes) {
		List<ProgramacaoDTO> programacoesDTO = new ArrayList<>();
		programacoes.forEach(p -> programacoesDTO.add(new ProgramacaoDTO(p)));
		return programacoesDTO;
	}
	
	
	public static List<RespostaIdDTO> programacoesParaRespostasIdDTO(List<Programacao> programacoes) {
		List<RespostaIdDTO> respostasIdDTO = new ArrayList<>();
		programacoes.forEach(p -> respostasIdDTO.add(new RespostaIdDTO(p.getId())));
		return respostasIdDTO;
	}
	
	
	// VIAGEM
	public static List<ViagemDTO> viagensParaViagensDTO(List<Viagem> viagens) {
		List<ViagemDTO> viagensDTO = new ArrayList<>();
		viagens.forEach(v -> viagensDTO.add(new ViagemDTO(v)));
		return viagensDTO;
	}

	
	public static List<ViagemProximasChegadasDTO> viagensParaViagemProximasChegadasDTO(List<Viagem> viagens) {
		List<ViagemProximasChegadasDTO> viagensProximasChegadasDTO = new ArrayList<>();
		viagens.forEach(v -> viagensProximasChegadasDTO.add(new ViagemProximasChegadasDTO(v)));
		return viagensProximasChegadasDTO;
	}


	public static List<RespostaIdDTO> viagensParaRespostasIdDTO(List<Viagem> viagens) {
		List<RespostaIdDTO> respostasIdDTO = new ArrayList<>();
		viagens.forEach(v -> respostasIdDTO.add(new RespostaIdDTO(v.getId())));
		return respostasIdDTO;
	}


	public static List<ViagemExtraDTO> viagensParaViagensExtrasDTO(List<Viagem> viagens) {
		List<ViagemExtraDTO> viagensExtraDTO = new ArrayList<>();
		viagens.forEach(v -> viagensExtraDTO.add(new ViagemExtraDTO(v)));
		return viagensExtraDTO;
	}
	
	
	public static List<CadastroViagemDTO> viagensParaCadastroViagensDTO(List<Viagem> viagens) {
		List<CadastroViagemDTO> cadastroViagemDTO = new ArrayList<>();
		Collections.sort(viagens);
		viagens.forEach(v -> cadastroViagemDTO.add(new CadastroViagemDTO(v)));
		return cadastroViagemDTO;
	}
	
	
	public static List<ViagemPerdidaDTO> viagensParaViagensPerdidasDTO(List<Viagem> viagens) {
		List<ViagemPerdidaDTO> listarViagensPerdidasPorPonto = new ArrayList<>();
		viagens.forEach(v -> listarViagensPerdidasPorPonto.add(new ViagemPerdidaDTO(v)));
		return listarViagensPerdidasPorPonto;
	}

	
	// RELAÇÃO PONTO-LINHA
	public static List<PontoLinhaDTO> pontosLinhaParaPontosLinhaDTO(List<PontoLinha> pontosLinha) {
		List<PontoLinhaDTO> pontosLinhaDTO = new ArrayList<>();
		pontosLinha.forEach(v -> pontosLinhaDTO.add(new PontoLinhaDTO(v)));
		return pontosLinhaDTO;
	}


	// RECLAMAÇÕES
	public static List<RespostaIdDTO> reclamacoesParaRespostasIdDTO(List<Reclamacao> reclamacoes) {
		List<RespostaIdDTO> respostasIdDTO = new ArrayList<>();
		reclamacoes.forEach(r -> respostasIdDTO.add(new RespostaIdDTO(r.getId())));
		return respostasIdDTO;
	}

	
	// OBSERVAÇÕES
	public static List<RespostaIdDTO> observacoesParaRespostasIdDTO(List<Observacao> observacoes) {
		List<RespostaIdDTO> respostasIdDTO = new ArrayList<>();
		observacoes.forEach(o -> respostasIdDTO.add(new RespostaIdDTO(o.getId())));
		return respostasIdDTO;
	}

}

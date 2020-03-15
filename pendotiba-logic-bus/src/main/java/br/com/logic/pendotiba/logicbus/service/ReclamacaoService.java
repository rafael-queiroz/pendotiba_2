package br.com.logic.pendotiba.logicbus.service;

import br.com.logic.pendotiba.core.model.Reclamacao;
import br.com.logic.pendotiba.core.repository.*;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.resources.dto.ReclamacaoDTO;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReclamacaoService {
	
	
	@Autowired
	ReclamacaoRepository reclamacaoRepository;
	
	@Autowired
	TipoReclamacaoRepository tipoReclamacaoRepository;
	
	@Autowired
	LinhaRepository linhaRepository;
	
	@Autowired
	CarroRepository carroRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	ProgramacaoRepository programacaoRepository;
	
	
	public Reclamacao salvarReclamacaoDTO(ReclamacaoDTO dto) {
		Reclamacao reclamacao = new Reclamacao();
		
		reclamacao.setProgramacao(programacaoRepository.findById(dto.getIdProgramacao()).orElse(null));
		reclamacao.setTipoReclamacao(tipoReclamacaoRepository.findById(dto.getIdTipoReclamacao()).orElse(null));
		reclamacao.setCarro(carroRepository.findById(dto.getIdCarro()).orElse(null));
		reclamacao.setLinha(linhaRepository.findById(dto.getIdLinha()).orElse(null));
		reclamacao.setUsuario(usuarioRepository.findById(dto.getIdUsuario()).orElse(null));
		reclamacao.setHora(DataUtil.getTime(DataUtil.getDataStringYYYYMMDD(reclamacao.getProgramacao().getDataCompetencia()), dto.getHora()));
		reclamacao.setObservacao(dto.getObservacao());
	
		return reclamacaoRepository.save(reclamacao);
	}
	
	
	public List<Reclamacao> salvarReclamacoesDTO(List<ReclamacaoDTO> reclamacoesDTO) {
		List<Reclamacao> reclamacoes = new ArrayList<>();
		reclamacoesDTO.forEach(dto -> reclamacoes.add(salvarReclamacaoDTO(dto)));
		return reclamacoes;
	}
	

	public void excluir(Reclamacao reclamacao) {
		try {
			reclamacaoRepository.delete(reclamacao);
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível excluir reclamação.");
		}
	}
	
}

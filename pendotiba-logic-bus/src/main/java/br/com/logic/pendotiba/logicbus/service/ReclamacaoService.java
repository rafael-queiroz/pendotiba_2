package br.com.logic.pendotiba.logicbus.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.Reclamacao;
import br.com.logic.pendotiba.core.repository.CarroRepository;
import br.com.logic.pendotiba.core.repository.LinhaRepository;
import br.com.logic.pendotiba.core.repository.ProgramacaoRepository;
import br.com.logic.pendotiba.core.repository.ReclamacaoRepository;
import br.com.logic.pendotiba.core.repository.TipoReclamacaoRepository;
import br.com.logic.pendotiba.core.repository.UsuarioRepository;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.resources.dto.ReclamacaoDTO;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;

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
		
		reclamacao.setProgramacao(programacaoRepository.findOne(dto.getIdProgramacao()));
		reclamacao.setTipoReclamacao(tipoReclamacaoRepository.findOne(dto.getIdTipoReclamacao()));
		reclamacao.setCarro(carroRepository.findOne(dto.getIdCarro()));
		reclamacao.setLinha(linhaRepository.findOne(dto.getIdLinha()));
		reclamacao.setUsuario(usuarioRepository.findOne(dto.getIdUsuario()));
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

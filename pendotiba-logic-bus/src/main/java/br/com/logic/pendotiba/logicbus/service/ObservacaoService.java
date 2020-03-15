package br.com.logic.pendotiba.logicbus.service;

import br.com.logic.pendotiba.core.model.Observacao;
import br.com.logic.pendotiba.core.repository.*;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.resources.dto.ObservacaoDTO;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ObservacaoService {
	
	
	@Autowired
	ObservacaoRepository observacaoRepository;
	
	@Autowired
	TipoObservacaoRepository tipoObservacaoRepository;
	
	@Autowired
	LinhaRepository linhaRepository;
	
	@Autowired
	CarroRepository carroRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	ProgramacaoRepository programacaoRepository;
	
	
	public Observacao salvarObservacaoDTO(ObservacaoDTO dto) {
		Observacao observacao = new Observacao();
		
		observacao.setProgramacao(programacaoRepository.findById(dto.getIdProgramacao()).orElse(null));
		observacao.setTipoObservacao(tipoObservacaoRepository.findById(dto.getIdTipoObservacao()).orElse(null));
		observacao.setCarro(carroRepository.findById(dto.getIdCarro()).orElse(null));
		observacao.setLinha(linhaRepository.findById(dto.getIdLinha()).orElse(null));
		observacao.setUsuario(usuarioRepository.findById(dto.getIdUsuario()).orElse(null));
		observacao.setHora(DataUtil.getTime(DataUtil.getDataStringYYYYMMDD(observacao.getProgramacao().getDataCompetencia()), dto.getHora()));
		observacao.setDescricao(dto.getDescricao());
	
		return observacaoRepository.save(observacao);
	}
	
	
	public List<Observacao> salvarObservacoesDTO(List<ObservacaoDTO> observacoesDTO) {
		List<Observacao> observacoes = new ArrayList<>();
		observacoesDTO.forEach(dto -> observacoes.add(salvarObservacaoDTO(dto)));
		return observacoes;
	}
	
	public void excluir(Observacao observacao) {
		try {
			observacaoRepository.delete(observacao);
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível excluir observação.");
		}
	}
	
}

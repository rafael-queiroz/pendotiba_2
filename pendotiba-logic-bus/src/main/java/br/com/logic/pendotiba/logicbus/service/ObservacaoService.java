package br.com.logic.pendotiba.logicbus.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.Observacao;
import br.com.logic.pendotiba.core.repository.CarroRepository;
import br.com.logic.pendotiba.core.repository.LinhaRepository;
import br.com.logic.pendotiba.core.repository.ObservacaoRepository;
import br.com.logic.pendotiba.core.repository.ProgramacaoRepository;
import br.com.logic.pendotiba.core.repository.TipoObservacaoRepository;
import br.com.logic.pendotiba.core.repository.UsuarioRepository;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.resources.dto.ObservacaoDTO;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;

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
		
		observacao.setProgramacao(programacaoRepository.findOne(dto.getIdProgramacao()));
		observacao.setTipoObservacao(tipoObservacaoRepository.findOne(dto.getIdTipoObservacao()));
		observacao.setCarro(carroRepository.findOne(dto.getIdCarro()));
		observacao.setLinha(linhaRepository.findOne(dto.getIdLinha()));
		observacao.setUsuario(usuarioRepository.findOne(dto.getIdUsuario()));
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

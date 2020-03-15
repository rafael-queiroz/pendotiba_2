package br.com.logic.pendotiba.logicbus.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.CarroProgramacao;
import br.com.logic.pendotiba.core.repository.CarroProgramacaoRepository;
import br.com.logic.pendotiba.core.repository.CarroRepository;
import br.com.logic.pendotiba.core.repository.MotivoTrocaCarroRepository;
import br.com.logic.pendotiba.core.repository.ProgramacaoRepository;
import br.com.logic.pendotiba.core.repository.UsuarioRepository;
import br.com.logic.pendotiba.logicbus.resources.dto.TrocaCarroProgramacaoDTO;

@Service
public class CarroProgramacaoService {
	
	
	@Autowired
	CarroProgramacaoRepository carroProgramacaoRepository;
	
	@Autowired
	MotivoTrocaCarroRepository motivoTrocaCarroRepository;
	
	@Autowired
	CarroRepository carroRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	ProgramacaoRepository programacaoRepository;
	
	
	
	public void salvarTrocasCarroProgramacaoDTO(List<TrocaCarroProgramacaoDTO> trocasDTO) {
		List<CarroProgramacao> trocasCarroProgramacao = new ArrayList<>();
		trocasDTO.forEach(dto -> trocasCarroProgramacao.add(salvarTrocaCarroProgramadoDTO(dto)));
	}

	
	public CarroProgramacao salvarTrocaCarroProgramadoDTO(TrocaCarroProgramacaoDTO dto) {
		CarroProgramacao carroProgramacao = new CarroProgramacao();
		carroProgramacao.setDataCompetencia(new Date());
		carroProgramacao.setProgramacao(programacaoRepository.findOne(dto.getIdProgramacao()));
		carroProgramacao.setMotivoTrocaCarro(motivoTrocaCarroRepository.findOne(dto.getIdTipoMotivoTroca()));
		carroProgramacao.setCarro(carroRepository.findOne(dto.getIdCarro()));
		carroProgramacao.setObservacao(dto.getObservacao());
		carroProgramacao.setUsuario(dto.getIdUsuario() != null ? usuarioRepository.findOne(dto.getIdUsuario()) : usuarioService.getUsuarioLogado());
		carroProgramacao.setRoletaInicial1(dto.getRoletaInicial1());
		
		return carroProgramacaoRepository.save(carroProgramacao);
	}


	public void salvar(CarroProgramacao carroProgramacao) {
		carroProgramacaoRepository.save(carroProgramacao);
	}
	
}

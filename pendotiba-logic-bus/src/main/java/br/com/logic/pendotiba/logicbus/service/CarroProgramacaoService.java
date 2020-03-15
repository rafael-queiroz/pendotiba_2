package br.com.logic.pendotiba.logicbus.service;

import br.com.logic.pendotiba.core.model.CarroProgramacao;
import br.com.logic.pendotiba.core.repository.*;
import br.com.logic.pendotiba.logicbus.resources.dto.TrocaCarroProgramacaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
		carroProgramacao.setProgramacao(programacaoRepository.findById(dto.getIdProgramacao()).orElse(null));
		carroProgramacao.setMotivoTrocaCarro(motivoTrocaCarroRepository.findById(dto.getIdTipoMotivoTroca()).orElse(null));
		carroProgramacao.setCarro(carroRepository.findById(dto.getIdCarro()).orElse(null));
		carroProgramacao.setObservacao(dto.getObservacao());
		carroProgramacao.setUsuario(dto.getIdUsuario() != null ? usuarioRepository.findById(dto.getIdUsuario()).orElse(null) : usuarioService.getUsuarioLogado());
		carroProgramacao.setRoletaInicial1(dto.getRoletaInicial1());
		
		return carroProgramacaoRepository.save(carroProgramacao);
	}


	public void salvar(CarroProgramacao carroProgramacao) {
		carroProgramacaoRepository.save(carroProgramacao);
	}
	
}

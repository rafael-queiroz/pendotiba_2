package br.com.logic.pendotiba.despachante.service;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.CarroProgramacao;
import br.com.logic.pendotiba.core.repository.MotivoTrocaCarroRepository;
import br.com.logic.pendotiba.despachante.dto.TrocaCarroProgramacaoDTO;

@Service
public class CarroProgramacaoService {
	
	@Autowired
	MotivoTrocaCarroRepository programacaoService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	CarroService carroService;
	
	@Autowired
	MotivoTrocaCarroService motivoTrocaCarroService;
	

	public CarroProgramacao gerarNovoViculoCarroProgramacao(TrocaCarroProgramacaoDTO dto) {
		CarroProgramacao obj = new CarroProgramacao();
		obj.setDataCompetencia(new Date());
		//obj.setMotivoTrocaCarro(motivoTrocaCarroService.buscarPorId(dto.getIdTipoMotivoTroca()));
		obj.setCarro(carroService.buscarPorId(dto.getIdCarro()));
		obj.setObservacao(dto.getObservacao());
		obj.setUsuario(usuarioService.buscarPorId(dto.getIdUsuario()));
		obj.setRoletaInicial1(dto.getRoletaInicial1());
		return obj;
	}
	
}

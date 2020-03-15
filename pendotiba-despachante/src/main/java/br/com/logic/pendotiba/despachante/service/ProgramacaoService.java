package br.com.logic.pendotiba.despachante.service;

import br.com.logic.pendotiba.core.model.*;
import br.com.logic.pendotiba.core.repository.ProgramacaoRepository;
import br.com.logic.pendotiba.core.repository.ViagemRepository;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.despachante.dto.ProgramacaoDTO;
import br.com.logic.pendotiba.despachante.dto.RespostaIdDTO;
import br.com.logic.pendotiba.despachante.dto.TrocaCarroProgramacaoDTO;
import br.com.logic.pendotiba.despachante.dto.TrocaMotoristaProgramacaoDTO;
import br.com.logic.pendotiba.despachante.exception.NegocioException;
import br.com.logic.pendotiba.despachante.repository.ProgramacaoRepositoryImpl;
import br.com.logic.pendotiba.despachante.repository.ViagemRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProgramacaoService {
	
	@Autowired
	ProgramacaoRepository programacaoRepository;
	
	@Autowired
	ProgramacaoRepositoryImpl programacaoRepositoryImpl;
	
	@Autowired
	ViagemRepository viagemRepository;
	
	@Autowired
	ViagemRepositoryImpl viagemRepositoryImpl;
	
	@Autowired
	PontoService pontoService;
	
	@Autowired
	StatusService statusService;
	
	@Autowired
	ViagemService viagemService;
	
	@Autowired
	CarroService carroService;
	
	@Autowired
	MapaDiarioCarroService mapaDiarioCarroService;
	
	@Autowired
	FuncionarioService funcionarioService;
	
	@Autowired
	LinhaService linhaService;
	
	@Autowired
	TurnoService turnoService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	PontoLinhaService pontoLinhaService;
	
	@Autowired
	EntradaSaidaDeCarroDaGaragemService entradaSaidaDeCarroDaGaragemService;
	
	@Autowired
	CarroProgramacaoService carroProgramacaoService;
	
	

	public Programacao buscarPorId(Long id) {
		return programacaoRepository.findById(id).orElse(null);
	}
	
	public Programacao carregarProgramacao(Long id) {
		return programacaoRepository.carregarProgramacao(id);
	}

	public List<ProgramacaoDTO> listarPorImeiDataCompetenciaIds(String imei, String dataCompetencia, List<Long> ids) {
		Ponto ponto = pontoService.buscarPorImei(imei);
		Date data = DataUtil.getDateDDMMYYYY(dataCompetencia);
		List<Programacao> programacoes = programacaoRepositoryImpl.listarPorPontoDataCompetenciaIds(ponto, data, ids);
		List<ProgramacaoDTO> listDto = programacoes.stream().map(obj -> new ProgramacaoDTO(obj)).collect(Collectors.toList());
		return listDto;
	}

	public List<ProgramacaoDTO> listarPorIds(List<Long> ids) {
		List<Programacao> programacoes = programacaoRepository.findByIdIn(ids);
		List<ProgramacaoDTO> listDto = programacoes.stream().map(obj -> new ProgramacaoDTO(obj)).collect(Collectors.toList());
		return listDto;
	}
	
	public List<ProgramacaoDTO> listarAtivosPorIds(List<Long> ids) {
		List<Programacao> programacoes = programacaoRepository.findByStatusIdAndIdIn(Status.LIBERADO, ids);
		List<ProgramacaoDTO> listDto = programacoes.stream().map(obj -> new ProgramacaoDTO(obj)).collect(Collectors.toList());
		return listDto;
	}
	
	public List<RespostaIdDTO> listarInativasPorIds(List<Long> ids) {
		List<Programacao> programacoes = programacaoRepository.findByStatusIdAndIdIn(Status.CORTADO, ids);
		List<RespostaIdDTO> listDto = programacoes.stream().map(obj -> new RespostaIdDTO(obj.getId())).collect(Collectors.toList());
		return listDto;
	}
	
	public List<RespostaIdDTO> listarEncerradasPorIds(List<Long> ids) {
		List<Programacao> programacoes = programacaoRepository.findByStatusIdAndIdIn(Status.ENCERRADO, ids);
		List<RespostaIdDTO> listDto = programacoes.stream().map(obj -> new RespostaIdDTO(obj.getId())).collect(Collectors.toList());
		return listDto;
	}
	
	
	@Transactional
	public Programacao encerrar(ProgramacaoDTO dto) {
		Viagem ultimaViagem = new Viagem();
		Programacao programacao = carregarProgramacao(dto.getId());
		
		programacao.setTerminoTrabalho(DataUtil.getTime(DataUtil.getDataStringYYYYMMDD(programacao.getDataCompetencia()), dto.getTerminoTrabalho()));
		programacao.setTerminoJornada(DataUtil.getTime(DataUtil.getDataStringYYYYMMDD(programacao.getDataCompetencia()), dto.getTerminoJornada()));
		programacao.setStatus(statusService.buscarPorId(Status.ENCERRADO));
		
		ultimaViagem = viagemRepositoryImpl.buscarUltimaViagemPorProgramacao(programacao);
		if(dto.getRoletaFinal1() == null && ultimaViagem != null) {
			programacao.atualizarRoletas(ultimaViagem.getRoletaFinal1());
			carroService.atualizarRoletas(programacao.carroAtual().getCarro(), ultimaViagem.getRoletaFinal1());
		} else if (dto.getRoletaFinal1() != null) {
			programacao.atualizarRoletas(dto.getRoletaFinal1());
			carroService.atualizarRoletas(programacao.carroAtual().getCarro(), dto.getRoletaFinal1());
		} else {
			programacao.atualizarRoletas(programacao.carroAtual().getCarro().getRoleta1());
			carroService.atualizarRoletas(programacao.carroAtual().getCarro(), programacao.carroAtual().getCarro().getRoleta1());
		}
		
		Programacao programacaoPersisitida = programacaoRepository.save(programacao);
		
		mapaDiarioCarroService.atualizarMapaPelaProgramacao(programacaoPersisitida);
		
		return programacaoPersisitida;
	}
	
	@Transactional
	public Programacao salvar(ProgramacaoDTO dto) {
		Programacao programacao = new Programacao();
		
		if(dto.getId() != null && dto.getId() != 0L)
			programacao = carregarProgramacao(dto.getId());
		
		programacao.setDataCompetencia(DataUtil.getDateDDMMYYYY(dto.getDataCompetencia()));
		programacao.setInicioJornada(DataUtil.getTime(dto.getDataCompetencia(), dto.getInicioJornada()));
		programacao.setInicioTrabalho(DataUtil.getTime(dto.getDataCompetencia(), dto.getInicioTrabalho()));
		programacao.setInicioTrabalho(DataUtil.getTime(dto.getDataCompetencia(), dto.getInicioTrabalho()));
		programacao.setMotorista(funcionarioService.buscarPorId(dto.getIdMotorista()));
		programacao.setParceiro(funcionarioService.buscarPorId(dto.getIdParceiro()));
		programacao.setLinha(linhaService.buscarPorId(dto.getIdLinha()));
		programacao.setTurno(turnoService.buscarPorId(dto.getIdTurno()));
		programacao.setCarroProgramado(dto.getIdCarroProgramado() != null ? carroService.buscarPorId(dto.getIdCarroProgramado()) : null);
		programacao.setCarroRealizado(dto.getIdCarroRealizado() != null ? carroService.buscarPorId(dto.getIdCarroRealizado()) : null);
		programacao.setStatus(statusService.buscarPorId(dto.getLiberadoGaragem()));
		
		programacao.setUsuario(dto.getIdUsuario() != null ? usuarioService.buscarPorId(dto.getIdUsuario()) : null );
		programacao.setHoraLiberacao(DataUtil.getTime(dto.getDataCompetencia(), dto.getHoraLiberacao()));
		
		programacao.getRoletas().clear();
		programacao.getRoletas().add(new CarroProgramacao(programacao, programacao.getCarroRealizado(), new Date(), dto.getRoletaInicial1(), null));
		
		verificarPegadaMotorista(dto, programacao);
		
		return salvar(programacao);
	}
	
	public Programacao trocarCarro(TrocaCarroProgramacaoDTO dto) {
		Programacao programacao = carregarProgramacao(dto.getIdProgramacao());
		Carro novoCarro = carroService.buscarPorId(dto.getIdCarro());
		if(programacao.getStatus().getId().equals(Status.LIBERADO) && programacaoRepositoryImpl.buscarPorDataCompetenciaCarroProgramadoAtiva(programacao, novoCarro) != null)
			throw new NegocioException("Já existe uma programação ativa para a data e carro informado");
		
		programacao.atualizarRoletas(dto.getRoletaFinal1());
		programacao.setCarroRealizado(novoCarro);
		programacao.getRoletas().add(carroProgramacaoService.gerarNovoViculoCarroProgramacao(dto));
		return programacaoRepository.save(programacao);
	}
	
	public Programacao trocarMotorista(TrocaMotoristaProgramacaoDTO dto) {
		Programacao programacao = carregarProgramacao(dto.getIdProgramacao());
		programacao.setMotorista(funcionarioService.buscarPorId(dto.getIdMotorista()));
		return programacaoRepository.save(programacao);
	}
	
	public Programacao inativar(ProgramacaoDTO dto) {
		Programacao programacao = carregarProgramacao(dto.getId());
		programacao.setStatus(statusService.buscarPorId(Status.CORTADO));
		return programacaoRepository.save(programacao);
	}
	
	
	
	
	//PRIVATE METHODS
	void verificarPegadaMotorista(ProgramacaoDTO dto, Programacao programacao) {
		Ponto pontoPegada = pontoService.buscarPorId(dto.getIdPontoPegadaMotorista());
		
		if(!pontoPegada.equals(programacao.getPontoPegadaMotorista())) {
			programacao.setPontoPegadaMotorista(pontoPegada);
			programacao.setMudouPontoPegada(1);
			Integer contadorOrdemViagem = 1;
			for (Viagem viagem : programacao.getViagens()) {
				viagem.setPontoLinha(pontoLinhaService.buscarPontoLinhaInverso(viagem.getPontoLinha()));
				viagem.setOrdemViagem(new BigInteger(contadorOrdemViagem.toString()));
				if(viagem.getPontoLinha().getSentido().equals("VOLTA"))
					contadorOrdemViagem++;
			}
		}
	}
	
	@Transactional
	Programacao salvar(Programacao programacao){
		if(programacao.getCarroRealizado() != null && (programacao.carroAtual() == null || programacao.carroAtual().getCarro() != programacao.getCarroRealizado()))
			programacao.getRoletas().add(new CarroProgramacao(programacao, null));
		
		if(programacao.getHoraLiberacao() == null && programacao.liberada()) {
			programacao.carroAtual().setRoletaInicial1(programacao.getCarroRealizado().getRoleta1());
			programacao.setHoraLiberacao(new Date());
			entradaSaidaDeCarroDaGaragemService.salvarPelaProgramacao(programacao);
		}
		
		return programacaoRepository.saveAndFlush(programacao);
	}

}

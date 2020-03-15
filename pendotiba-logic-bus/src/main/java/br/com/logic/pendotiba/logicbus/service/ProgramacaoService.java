package br.com.logic.pendotiba.logicbus.service;

import br.com.logic.pendotiba.core.model.*;
import br.com.logic.pendotiba.core.repository.*;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.repo.PontoLinhaRepositoryImpl;
import br.com.logic.pendotiba.logicbus.repo.ProgramacaoImportadaRepositoryImpl;
import br.com.logic.pendotiba.logicbus.repo.ProgramacaoRepositoryImpl;
import br.com.logic.pendotiba.logicbus.repo.ViagemRepositoryImpl;
import br.com.logic.pendotiba.logicbus.resources.dto.ProgramacaoDTO;
import br.com.logic.pendotiba.logicbus.resources.dto.TrocaCarroProgramacaoDTO;
import br.com.logic.pendotiba.logicbus.resources.dto.TrocaMotoristaProgramacaoDTO;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;
import br.com.logic.pendotiba.logicbus.validation.ImportacaoProgramacaoValidation;
import br.com.logic.pendotiba.logicbus.validation.ImportacaoViagemValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProgramacaoService {
	
	@Autowired
	ProgramacaoRepository programacaoRepository;
	
	@Autowired
	ProgramacaoRepositoryImpl programacaoRepositoryImpl;
	
	@Autowired
	ProgramacaoImportadaRepository programacaoImportadaRepository;
	
	@Autowired
	ProgramacaoImportadaRepositoryImpl programacaoImportadaRepositoryImpl;
	
	@Autowired
	EscalaImportadaRepository escalaImportadaRepository;
	
	@Autowired
	ErroProgramacaoRepository erroProgramacaoRepository;
	
	@Autowired
	ViagemRepository viagemRepository;
	
	@Autowired
	ViagemRepositoryImpl viagemRepositoryImpl;
	
	@Autowired
	ViagemImportadaRepository viagemImportadaRepository;
	
	@Autowired
	ErroViagemRepository erroViagemRepository;
	
	@Autowired
	LinhaRepository linhaRepository;
	
	@Autowired
	PontoRepository pontoRepository;
	
	@Autowired
	PontoLinhaRepository pontoLinhaRepository;
	
	@Autowired
	PontoLinhaRepositoryImpl pontoLinhaRepositoryImpl;
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	@Autowired
	TurnoRepository turnoRepository;
	
	@Autowired
	CarroRepository carroRepository;
	
	@Autowired
	CarroService carroService;
	
	@Autowired
	StatusRepository statusRepository;
	
	@Autowired
	GaragemRepository garagemRepository;
	
	@Autowired
	CarroProgramacaoRepository carroProgramacaoRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	EntradaSaidaDeCarroDaGaragemService movimentacaoCarroService;
	
	@Autowired
	MapaDiarioCarroService mapaDiarioCarroService;
	
	@Autowired
	EntradaSaidaDeCarroDaGaragemService entradaSaidaDeCarroDaGaragemService;
	
	
	
	@Transactional
	public Programacao salvar(Programacao programacao){
		if(programacao.getCarroRealizado() != null && (programacao.carroAtual() == null || programacao.carroAtual().getCarro() != programacao.getCarroRealizado()))
			programacao.getRoletas().add(new CarroProgramacao(programacao, null));
			//programacao.getRoletas().add(new CarroProgramacao(programacao, programacao.getUsuario() != null ? programacao.getUsuario() : usuarioService.getUsuarioLogado()));
		
		if(programacao.getHoraLiberacao() == null && programacao.liberada()) {
			programacao.carroAtual().setRoletaInicial1(programacao.getCarroRealizado().getRoleta1());
			programacao.setHoraLiberacao(new Date());
			entradaSaidaDeCarroDaGaragemService.salvarPelaProgramacao(programacao);
		}
		
		return programacaoRepository.save(programacao);
	}
	

	public void gerarProgramacoesPorDataCompetencia(Date dataCompetencia) throws NegocioException {
		//List<ProgramacaoImportada> programacoesImportadas = programacaoImportadaRepository.findByDataCompetencia(dataCompetencia);
		limparBasesProgramacaoViagensPorDataDeCompetencia(dataCompetencia);
		
		//buscar programacao valida para a linha
		//List<ProgramacaoImportada> programacoesImportadas = programacaoImportadaRepository.findAllByOrderBySaida();
		
		//buscar
		List<EscalaImportada> escalas = escalaImportadaRepository.findByDataCompetenciaOrderBySaida(dataCompetencia);

		for (EscalaImportada escalaImportada : escalas)
			programacaoRepository.save(gerarProgramacoes(escalaImportada));

	}


	public void limparBasesProgramacaoViagensPorDataDeCompetencia(Date dataCompetencia) {
		erroViagemRepository.deleteByDataCompetencia(dataCompetencia);
		erroProgramacaoRepository.deleteByDataCompetencia(dataCompetencia);
		viagemRepository.deleteByDataCompetencia(dataCompetencia);
		carroProgramacaoRepository.deleteByDataCompetencia(dataCompetencia);
		programacaoRepository.deleteByDataCompetencia(dataCompetencia);
	}

	
	Programacao gerarProgramacoes(EscalaImportada escalaImportada) {
		Programacao programacao = new Programacao();
		
		List<ProgramacaoImportada> viagens = new ArrayList<>();
		if (DataUtil.ehDomingo(escalaImportada.getDataCompetencia()))
			viagens = programacaoImportadaRepositoryImpl.listarPorLinhaOrdemProgramacaoDiaDaSemanaOrdenadoPorOrdemViagem(escalaImportada.getLinha(), escalaImportada.getOrdemProgramacao(), "D");
		else if (DataUtil.ehSabado(escalaImportada.getDataCompetencia()))
			viagens = programacaoImportadaRepositoryImpl.listarPorLinhaOrdemProgramacaoDiaDaSemanaOrdenadoPorOrdemViagem(escalaImportada.getLinha(), escalaImportada.getOrdemProgramacao(), "S");
		else 
			viagens = programacaoImportadaRepositoryImpl.listarPorLinhaOrdemProgramacaoDiaDaSemanaOrdenadoPorOrdemViagem(escalaImportada.getLinha(), escalaImportada.getOrdemProgramacao(), "U");
		
		programacao.setDataCompetencia(escalaImportada.getDataCompetencia());
		programacao.setInicioJornada(escalaImportada.getIncioJornada());
		programacao.setInicioTrabalho(escalaImportada.getSaida());
		programacao.setLinha(escalaImportada.getLinha());
		ImportacaoProgramacaoValidation.validarMotorista(programacao, escalaImportada.getMotorista());
		programacao.setMotoristaProgramado(programacao.getMotorista());
		programacao.setParceiro(escalaImportada.getParceiro());
		programacao.setTurno(escalaImportada.getTurno());
		ImportacaoProgramacaoValidation.validarCarroProgramado(programacao, escalaImportada.getCarro(), usuarioService.getUsuarioLogado());
		
		if(!viagens.isEmpty()) {
        	if(viagens.get(0).getMovimento().equals("VIAGEM"))
        		programacao.setPontoPegadaMotorista(viagens.get(0).getPontoOrigem());
        	else
        		programacao.setPontoPegadaMotorista(viagens.get(1).getPontoOrigem());
        }
		
		programacao.setCarroRealizado(programacao.getCarroProgramado());
		programacao.setStatus(statusRepository.findById(Status.ATIVO).orElse(null));
		
		Integer contadorOrdemViagem = 1;
		for (ProgramacaoImportada viagemImportada : viagens) {
			if(viagemImportada.getMovimento().trim().equals("VIAGEM")) {
			
				Viagem viagem = new Viagem();
				
				viagem.setDataCompetencia(programacao.getDataCompetencia());
				viagem.setLinhaProgramada(viagemImportada.getLinha());
				viagem.setLinhaRealizada(viagemImportada.getLinha()); // solicitado 23.11.2018 L
				viagem.setHoraSaidaProgramada(viagemImportada.getSaida());
				
				ImportacaoViagemValidation.validarPontoLinha(viagem, pontoLinhaRepository.findByPontoOrigemAndPontoDestinoAndLinhaAndSentido(viagemImportada.getPontoOrigem(), 
						viagemImportada.getPontoDestino(), viagemImportada.getLinha(), viagemImportada.getSentido()));
				
				viagem.setOrdemViagem(new BigInteger(contadorOrdemViagem.toString()));
				if(viagem.getPontoLinha() != null && viagem.getPontoLinha().getSentido().equals("VOLTA"))
					contadorOrdemViagem++;
				
				programacao.getViagens().add(viagem);
				
				//retirado 18.02.2018 glaydson
				//if(!viagem.getErros().isEmpty())
				//	programacao.setCompleta(false);
				
				//contadorOrdemViagem++;
			}
		}
		
		//ImportacaoProgramacaoValidation.validarPontoPegadaMotorista(programacao);
		//retirado 18.02.2018 glaydson
		//if(!programacao.getErros().isEmpty())
		//	programacao.setCompleta(false);
		
		if(programacao.getViagens().isEmpty())
			programacao.getErros().add(new ErroProgramacao("Programação sem viagens", programacao.getDataCompetencia()));
		
		return programacao;
	}
	 
	
	@Transactional
	public Programacao encerrar(ProgramacaoDTO dto) {
		Viagem ultimaViagem = new Viagem();
		Programacao programacao = programacaoRepository.carregarProgramacao(dto.getId());
		
		programacao.setTerminoTrabalho(DataUtil.getTime(DataUtil.getDataStringYYYYMMDD(programacao.getDataCompetencia()), dto.getTerminoTrabalho()));
		programacao.setTerminoJornada(DataUtil.getTime(DataUtil.getDataStringYYYYMMDD(programacao.getDataCompetencia()), dto.getTerminoJornada()));
		programacao.setStatus(statusRepository.findById(Status.ENCERRADO).orElse(null));
		
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

	
	void verificarPegadaMotorista(ProgramacaoDTO dto, Programacao programacao) {
		Ponto pontoPegada = pontoRepository.findById(dto.getIdPontoPegadaMotorista()).orElse(null);
		
		if(!pontoPegada.equals(programacao.getPontoPegadaMotorista())) {
			programacao.setPontoPegadaMotorista(pontoRepository.findById(dto.getIdPontoPegadaMotorista()).orElse(null));
			programacao.setMudouPontoPegada(1);
			Integer contadorOrdemViagem = 1;
			for (Viagem viagem : programacao.getViagens()) {
				viagem.setPontoLinha(pontoLinhaRepositoryImpl.buscarPontoLinhaInverso(viagem.getPontoLinha()));
				viagem.setOrdemViagem(new BigInteger(contadorOrdemViagem.toString()));
				if(viagem.getPontoLinha().getSentido().equals("VOLTA"))
					contadorOrdemViagem++;
			}
		}
	}
	

	public List<Programacao> realizarTrocasCarroProgramacaoDTO(List<TrocaCarroProgramacaoDTO> trocasDTO) {
		List<Programacao> programacoes = new ArrayList<>(); 
		trocasDTO.forEach(t -> programacoes.add(realizarTrocaCarroProgramacao(t)));
		return programacoes;
	}


	public Programacao realizarTrocaCarroProgramacao(TrocaCarroProgramacaoDTO dto) {
		Programacao programacao = programacaoRepository.carregarProgramacao(dto.getIdProgramacao());
		Carro novoCarro = carroRepository.findById(dto.getIdCarro()).orElse(null);
		if(programacao.getStatus().getId().equals(Status.LIBERADO) && programacaoRepositoryImpl.buscarPorDataCompetenciaCarroProgramadoAtiva(programacao, novoCarro) != null)
			throw new NegocioException("Já existe uma programação ativa para a data e carro informado");
		
		programacao.atualizarRoletas(dto.getRoletaFinal1());
		programacao.setCarroRealizado(novoCarro);
		return programacaoRepository.save(programacao);
	}

	
	public List<Programacao> salvarProgramacoesDTO(List<ProgramacaoDTO> programacoesDTO) {
		List<Programacao> programacoes = new ArrayList<>(); 
		programacoesDTO.forEach(p -> programacoes.add(salvarProgramacaoDTO(p)));
		return programacoes;
	}


	public Programacao salvarProgramacaoDTO(ProgramacaoDTO dto) {
		Programacao programacao = new Programacao();
		
		if(dto.getId() != null && dto.getId() != 0L)
			programacao = programacaoRepository.carregarProgramacao(dto.getId());
		
		programacao.setDataCompetencia(DataUtil.getDateDDMMYYYY(dto.getDataCompetencia()));
		programacao.setInicioJornada(DataUtil.getTime(dto.getDataCompetencia(), dto.getInicioJornada()));
		programacao.setInicioTrabalho(DataUtil.getTime(dto.getDataCompetencia(), dto.getInicioTrabalho()));
		programacao.setInicioTrabalho(DataUtil.getTime(dto.getDataCompetencia(), dto.getInicioTrabalho()));
		
		//programacao.setPontoPegadaMotorista(pontoRepository.findById(dto.getIdPontoPegadaMotorista()));
		
		programacao.setMotorista(funcionarioRepository.findById(dto.getIdMotorista()).orElse(null));
		programacao.setParceiro(funcionarioRepository.findById(dto.getIdParceiro()).orElse(null));
		programacao.setLinha(linhaRepository.findById(dto.getIdLinha()).orElse(null));
		programacao.setTurno(turnoRepository.findById(dto.getIdTurno()).orElse(null));
		programacao.setCarroProgramado(dto.getIdCarroProgramado() != null ? carroRepository.findById(dto.getIdCarroProgramado()).orElse(null) : null);
		programacao.setCarroRealizado(dto.getIdCarroRealizado() != null ? carroRepository.findById(dto.getIdCarroRealizado()).orElse(null) : null);
		programacao.setStatus(statusRepository.findById(dto.getLiberadoGaragem()).orElse(null));
		
		//Rafael 20190322
		if(dto.getIdUsuario() != null)
			programacao.setUsuario(dto.getIdUsuario() != null ? usuarioRepository.findById(dto.getIdUsuario()).orElse(null) : null );
		programacao.setHoraLiberacao(DataUtil.getTime(dto.getDataCompetencia(), dto.getHoraLiberacao()));
		
		programacao.getRoletas().clear();
		//programacao.getRoletas().add(new CarroProgramacao(programacao, programacao.getCarroProgramado(), new Date(), dto.getRoletaInicial1(), null));
		//Rafael 20190322
		programacao.getRoletas().add(new CarroProgramacao(programacao, programacao.getCarroRealizado(), new Date(), dto.getRoletaInicial1(), null));
		
		verificarPegadaMotorista(dto, programacao);
		
		//return programacaoRepository.save(programacao);
		return salvar(programacao);
	}


	public List<Programacao> realizarTrocasMotoristasProgramacaoDTO(List<TrocaMotoristaProgramacaoDTO> trocasDTO) {
		List<Programacao> programacoes = new ArrayList<>(); 
		trocasDTO.forEach(t -> programacoes.add(realizarTrocaMotoristaProgramacao(t)));
		return programacoes;
	}
	
	
	Programacao realizarTrocaMotoristaProgramacao(TrocaMotoristaProgramacaoDTO dto) {
		Programacao programacao = programacaoRepository.carregarProgramacao(dto.getIdProgramacao());
		programacao.setMotorista(funcionarioRepository.findById(dto.getIdMotorista()).orElse(null));
		return programacaoRepository.save(programacao);
	}


	public List<Programacao> inativarProgramacoesDTO(List<ProgramacaoDTO> programacoesDTO) {
		List<Programacao> programacoes = new ArrayList<>(); 
		for (ProgramacaoDTO dto : programacoesDTO) {
			Programacao p = programacaoRepository.carregarProgramacao(dto.getId());
			p.setStatus(statusRepository.findById(Status.CORTADO).orElse(null));
			programacoes.add(programacaoRepository.save(p));
		}
		return programacoes;
	}

}

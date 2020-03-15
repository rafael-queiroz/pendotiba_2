package br.com.logic.pendotiba.despachante.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.model.Linha;
import br.com.logic.pendotiba.core.model.Ponto;
import br.com.logic.pendotiba.core.model.PontoLinha;
import br.com.logic.pendotiba.core.model.Programacao;
import br.com.logic.pendotiba.core.model.Status;
import br.com.logic.pendotiba.core.model.Usuario;
import br.com.logic.pendotiba.core.model.Viagem;
import br.com.logic.pendotiba.core.repository.ViagemRepository;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.despachante.dto.RespostaIdDTO;
import br.com.logic.pendotiba.despachante.dto.ViagemDTO;
import br.com.logic.pendotiba.despachante.dto.ViagemPerdidaDTO;
import br.com.logic.pendotiba.despachante.dto.ViagemProximasChegadasDTO;
import br.com.logic.pendotiba.despachante.exception.ImpossivelIncluirEntidadeException;
import br.com.logic.pendotiba.despachante.repository.ViagemRepositoryImpl;

@Service
public class ViagemService {
	
	@Autowired
	ViagemRepository viagemRepository;
	
	@Autowired
	ViagemRepositoryImpl viagemRepositoryImpl;
	
	@Autowired
	PontoService pontoService;
	
	@Autowired
	PontoLinhaService pontoLinhaService;
	
	@Autowired
	ProgramacaoService programacaoService;
	
	@Autowired
	CarroService carroService;
	
	@Autowired
	LinhaService linhaService;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	TipoViagemPerdidaService tipoViagemPerdidaService;
	
	@Autowired
	MotivoPuloViagemService motivoPuloViagemService;
	
	@Autowired
	StatusService statusService;
	
	
		
	public Viagem buscarPorId(Long id) {
		return viagemRepository.findOne(id);
	}
	
	public List<ViagemDTO> listarPorImeiDataCompetencia(String imei, String data) {
		Ponto ponto = pontoService.buscarPorImei(imei);
		Date dataCompetencia = DataUtil.getDateDDMMYYYY(data);
		List<Viagem> viagens = viagemRepositoryImpl.listarPorPontoDataCompetencia(ponto, dataCompetencia);
		List<ViagemDTO> listDto = viagens.stream().map(obj -> new ViagemDTO(obj)).collect(Collectors.toList());
		return listDto;
	}

	public List<ViagemDTO> listarExtraPorPonto(Ponto pontoDestino, String data) {
		Date dataCompetencia = DataUtil.getDateDDMMYYYY(data);
		Status encerrado = statusService.buscarPorId(Status.ENCERRADO);
		List<Viagem> viagens =  viagemRepository.findByPontoLinhaPontoDestinoAndDataCompetenciaAndHoraSaidaProgramadaIsNullAndProgramacaoCompletaTrueAndProgramacaoStatusNot(pontoDestino, dataCompetencia, encerrado);
		List<ViagemDTO> listDto = viagens.stream().map(obj -> new ViagemDTO(obj)).collect(Collectors.toList());
		return listDto;
	}

	public List<ViagemProximasChegadasDTO> listarProximasChegadasPorPonto(Ponto pontoDestino, String data, List<Long> idsViagens) {
		Date dataCompetencia = DataUtil.getDateDDMMYYYY(data);
		List<Viagem> viagens =  viagemRepositoryImpl.listarProximasChegadasPorPonto(dataCompetencia, pontoDestino, idsViagens);
		List<ViagemProximasChegadasDTO> listDto = viagens.stream().map(obj -> new ViagemProximasChegadasDTO(obj)).collect(Collectors.toList());
		return listDto;
	}

	public List<ViagemPerdidaDTO> listarPerdidasPorPonto(Ponto pontoDestino, String data, List<Long> idsViagens) {
		Date dataCompetencia = DataUtil.getDateDDMMYYYY(data);
		List<Viagem> viagens =  viagemRepositoryImpl.listarPerdidasPorPonto(dataCompetencia, pontoDestino, idsViagens);
		List<ViagemPerdidaDTO> listDto = viagens.stream().map(obj -> new ViagemPerdidaDTO(obj)).collect(Collectors.toList());
		return listDto;
	}
	
	public List<ViagemDTO> listarPorPontoProgramacao(Ponto ponto, List<Long> idsProgramacoes) {
		List<Viagem> viagens =  viagemRepository.listarPorPontoProgramacao(ponto, idsProgramacoes);
		List<ViagemDTO> listDto = viagens.stream().map(obj -> new ViagemDTO(obj)).collect(Collectors.toList());
		return listDto;
	}
	
	public List<RespostaIdDTO> listarPerdidasPorIds(List<Long> idsViagens) {
		List<Viagem> viagens =  viagemRepository.findByIdInAndTipoViagemPerdidaNotNull(idsViagens);
		List<RespostaIdDTO> listDto = viagens.stream().map(obj -> new RespostaIdDTO(obj.getId())).collect(Collectors.toList());
		return listDto;
	}
	
	public List<RespostaIdDTO> listarViagensDescartadasPorPonto(Ponto ponto, List<Long> idsViagens) {
		List<Viagem> viagens =  viagemRepository.listarViagensDescartadasPorPonto(ponto, idsViagens);
		List<RespostaIdDTO> listDto = viagens.stream().map(obj -> new RespostaIdDTO(obj.getId())).collect(Collectors.toList());
		return listDto;
	}
	
	public List<ViagemDTO> listarPorIdsProgramacoes(List<Long> idsProgramacoes) {
		List<Viagem> viagens =  viagemRepository.findByProgramacaoIdIn(idsProgramacoes);
		List<ViagemDTO> listDto = viagens.stream().map(obj -> new ViagemDTO(obj)).collect(Collectors.toList());
		return listDto;
	}

	
	
	
	public Viagem atualizar(ViagemDTO dto) {
		
		Viagem viagem = new Viagem();
		
		if(dto.getId() == null) {
			viagem.setProgramacao(programacaoService.buscarPorId(dto.getIdProgramacao()));
			viagem.setHoraSaidaProgramada(DataUtil.getTime(DataUtil.getDataStringYYYYMMDD(viagem.getProgramacao().getDataCompetencia()), dto.getHoraSaidaProgramada()));
			viagem.setDataCompetencia(viagem.getProgramacao().getDataCompetencia());
			viagem.setOrdemViagem(dto.getOrdemViagem());
		} else 
			viagem = buscarPorId(dto.getId());
		
		//mudar validação  
		if(viagem.getHoraSaidaRealizada() == null || dto.getHoraSaidaRealizada() != null || dto.getFlagEditada().equals(new BigInteger("1")))
			viagem.setHoraSaidaRealizada(DataUtil.getTime(DataUtil.getDataStringYYYYMMDD(viagem.getDataCompetencia()), dto.getHoraSaidaRealizada()));
		
		if(viagem.getHoraChegada() == null || dto.getHoraChegada() != null)
			viagem.setHoraChegada(DataUtil.getTime(DataUtil.getDataStringYYYYMMDD(viagem.getDataCompetencia()), dto.getHoraChegada()));
		
		if(viagem.getRoletaFinal1() == null || dto.getRoletaFinal1() != null)
			viagem.setRoletaFinal1(dto.getRoletaFinal1() != null ? dto.getRoletaFinal1() : null);
		
		if(viagem.getLinhaRealizada() == null || dto.getIdLinhaRealizada() != null || dto.getFlagEditada().equals(new BigInteger("1"))) // pode dar errado quando tiver linha realizada diferente de linha programada
			viagem.setLinhaRealizada(dto.getIdLinhaRealizada() != null ? linhaService.buscarPorId(dto.getIdLinhaRealizada()) : null);
			
		// verificar o sincronismo com o serviço de troca de carro, pois pode dar problema quando o carro programado for diferente do carro realizado
		// se for enviado flagSD diferente de null (1 ou qq outro valor), sera atualizado o carro
		if( (dto.getIdCarroRealizado() != null && dto.getFlagSD() == null) || viagem.getCarroRealizado() == null || dto.getFlagEditada().equals(new BigInteger("1")) ) 
			viagem.setCarroRealizado(dto.getIdCarroRealizado() != null ? carroService.buscarPorId(dto.getIdCarroRealizado()) : null);
		
		if(viagem.getTipoViagemPerdida() == null || dto.getIdTipoViagemPerdida() != null )
			viagem.setTipoViagemPerdida(dto.getIdTipoViagemPerdida() != null ? tipoViagemPerdidaService.buscarPorId(dto.getIdTipoViagemPerdida()) : null);
			
		if(viagem.getUsuarioResponsavelSaida() == null || dto.getIdUsuarioResponsavelSaida() != null || dto.getFlagEditada().equals(new BigInteger("1")))
			viagem.setUsuarioResponsavelSaida(dto.getIdUsuarioResponsavelSaida() != null ? usuarioService.buscarPorId(dto.getIdUsuarioResponsavelSaida()) : null);
		
		if(dto.getIdUsuarioResponsavelChegada() > 0 && (viagem.getUsuarioResponsavelChegada() == null || dto.getIdUsuarioResponsavelChegada() != null ))
			viagem.setUsuarioResponsavelChegada(dto.getIdUsuarioResponsavelChegada() != null ? usuarioService.buscarPorId(dto.getIdUsuarioResponsavelChegada()) : null);
			
		if(viagem.getObsViagemPerdida() == null || dto.getObsViagemPerdida() != null)
			viagem.setObsViagemPerdida(dto.getObsViagemPerdida());
		
		if(viagem.getMotivoPuloViagem() == null || dto.getIdMotivoPuloViagem() != null )
			viagem.setMotivoPuloViagem(dto.getIdMotivoPuloViagem() != null ? motivoPuloViagemService.buscarPorId(dto.getIdMotivoPuloViagem()) : null);
		
		if(viagem.getObsViagemPulada() == null || dto.getObsViagemPulada() != null)
			viagem.setObsViagemPulada(dto.getObsViagemPulada());
		
		if(dto.getFlagSD() != null && dto.getHoraSD() != null ) {
			viagem.setSd(true);
			viagem.setHoraSD(DataUtil.getTime(DataUtil.getDataStringYYYYMMDD(viagem.getDataCompetencia()), dto.getHoraSD()));
		}
		
		//if(dto.getIdPontoOrigem() != null && dto.getIdPontoDestino() != null && (dto.getIdLinhaRealizada() != null || dto.getIdLinhaProgramada() != null)) {
		if(dto.getIdPontoOrigem() != null && dto.getIdPontoDestino() != null && dto.getIdLinhaRealizada() != null) { // mudado por cauda da criacao de guia nova pelo app que nao manda id linha 20190325
			PontoLinha pontoLinha = pontoLinhaService.buscarPorPontoOrigemPontoDestinoLinha(dto.getIdPontoOrigem(), dto.getIdPontoDestino(), 
																									    //dto.getIdLinhaRealizada() != null ? dto.getIdLinhaRealizada() : dto.getIdLinhaProgramada()).get();
																										dto.getIdLinhaRealizada()); // mudado por cauda da criacao de guia nova pelo app que nao manda id linha 20190325
			if (viagem.getPontoLinha() != null && !viagem.getPontoLinha().equals(pontoLinha)
					&& !viagem.getLinhaRealizada().equals(viagem.getPontoLinha().getLinha())) {
				verificarTrocaDeLinha(viagem, pontoLinha);
				viagem.setPontoLinha(pontoLinha);
			}
		}
		
		return salvar(viagem);
	}
	
	public Viagem cadastrar(ViagemDTO dto) {
		try {
			Programacao programacao = programacaoService.carregarProgramacao(dto.getIdProgramacao());
			Date dataCompetencia = programacao.getDataCompetencia();
			Date horaSaidaProgramada = DataUtil.getTime(programacao.getDataCompetencia(), dto.getHoraSaidaProgramada());
			Linha linhaProgramada = linhaService.buscarPorId(dto.getIdLinhaProgramada());
			PontoLinha pontoLinha = pontoLinhaService.buscarPorPontoOrigemPontoDestinoLinha(dto.getIdPontoOrigem(), dto.getIdPontoDestino(), dto.getIdLinhaProgramada());
			
			Viagem viagem = new Viagem(programacao, dataCompetencia, horaSaidaProgramada, linhaProgramada, pontoLinha);
			
			return salvar(viagem);
		} catch (Exception e) {
			throw new ImpossivelIncluirEntidadeException("Não foi possível cadastrar viagem " + dto.getHoraSaidaProgramada());
		}
	}
	
	public Viagem cadastrarExtra(ViagemDTO dto) {
		try {
			Programacao programacao = programacaoService.carregarProgramacao(dto.getIdProgramacao());
			Date dataCompetencia = programacao.getDataCompetencia();
			Date horaSaidaRealizada = DataUtil.getTime(programacao.getDataCompetencia(), dto.getHoraSaidaRealizada());
			Linha linhaRealizada = linhaService.buscarPorId(dto.getIdLinhaRealizada());
			PontoLinha pontoLinha = pontoLinhaService.buscarPorPontoOrigemPontoDestinoLinha(dto.getIdPontoOrigem(), dto.getIdPontoDestino(), dto.getIdLinhaRealizada());
			Carro carroRealizado = carroService.buscarPorId(dto.getIdCarroRealizado());
			Usuario usuarioRespSaida = usuarioService.buscarPorId(dto.getIdUsuarioResponsavelSaida()); 
			BigInteger roletaFinal1 = dto.getRoletaFinal1();
			
			Viagem viagem = new Viagem(programacao, dataCompetencia, horaSaidaRealizada, linhaRealizada, pontoLinha, carroRealizado, usuarioRespSaida, roletaFinal1);
			
			return salvar(viagem);
		} catch (Exception e) {
			throw new ImpossivelIncluirEntidadeException("Não foi possível cadastrar viagem ");
		}
	}
	
	
	//PRIVATE METHODS
	void verificarTrocaDeLinha(Viagem viagem, PontoLinha pontoLinha) {
		Viagem proximaViagem = viagemRepositoryImpl.buscarProximaVigem(viagem);
		if(proximaViagem != null) {
			proximaViagem.setPontoLinha(pontoLinhaService.buscarPontoLinhaInverso(pontoLinha));
			salvar(proximaViagem);
		}
	}
	
	Viagem salvar(Viagem viagem) {
		if(viagem.getOrdemViagem() == null)
			viagem.setOrdemViagem(viagemRepositoryImpl.buscarOrdemProximaViagem(viagem));
		
		return viagemRepository.save(viagem);
	}

}

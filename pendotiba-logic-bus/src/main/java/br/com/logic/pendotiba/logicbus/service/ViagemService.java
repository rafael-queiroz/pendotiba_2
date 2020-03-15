package br.com.logic.pendotiba.logicbus.service;

import br.com.logic.pendotiba.core.model.*;
import br.com.logic.pendotiba.core.repository.*;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.repo.PontoLinhaRepositoryImpl;
import br.com.logic.pendotiba.logicbus.repo.ViagemRepositoryImpl;
import br.com.logic.pendotiba.logicbus.resources.dto.ViagemDTO;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelIncluirEntidadeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ViagemService {
	
	@Autowired
	ViagemRepository viagemRepository;
	
	@Autowired
	ViagemRepositoryImpl viagemRepositoryImpl;
		
	@Autowired
	LinhaRepository linhaRepository;
	
	@Autowired
	CarroRepository carroRepository;
	
	@Autowired
	TipoViagemPerdidaRepository tipoViagemPerdidaRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	ProgramacaoRepository programacaoRepository;
	
	@Autowired
	PontoLinhaRepository pontoLinhaRepository;
	
	@Autowired
	PontoLinhaRepositoryImpl pontoLinhaRepositoryImpl;
	
	@Autowired
	MotivoPuloViagemRepository motivoPuloViagemRepository;
	
	@Autowired
	UsuarioService usuarioService;
	

	public List<Viagem> salvarViagensDTO(List<ViagemDTO> viagensDTO) {
		List<Viagem> viagens = new ArrayList<>();
		viagensDTO.forEach(dto -> viagens.add(salvarViagemDTO(dto)));
		return viagens;
	}
	
	
	public Viagem salvarViagemDTO(ViagemDTO dto) {
		
		Viagem viagem = new Viagem();
		
		if(dto.getId() == null) {
			viagem.setProgramacao(programacaoRepository.findById(dto.getIdProgramacao()).orElse(null));
			viagem.setHoraSaidaProgramada(DataUtil.getTime(DataUtil.getDataStringYYYYMMDD(viagem.getProgramacao().getDataCompetencia()), dto.getHoraSaidaProgramada()));
			viagem.setDataCompetencia(viagem.getProgramacao().getDataCompetencia());
			viagem.setOrdemViagem(dto.getOrdemViagem());
		} else 
			viagem = viagemRepository.findById(dto.getId()).orElse(null);
		
		//mudar validação  
		if(viagem.getHoraSaidaRealizada() == null || dto.getHoraSaidaRealizada() != null || dto.getFlagEditada().equals(new BigInteger("1")))
			viagem.setHoraSaidaRealizada(DataUtil.getTime(DataUtil.getDataStringYYYYMMDD(viagem.getDataCompetencia()), dto.getHoraSaidaRealizada()));
		
		if(viagem.getHoraChegada() == null || dto.getHoraChegada() != null)
			viagem.setHoraChegada(DataUtil.getTime(DataUtil.getDataStringYYYYMMDD(viagem.getDataCompetencia()), dto.getHoraChegada()));
		
		if(viagem.getRoletaFinal1() == null || dto.getRoletaFinal1() != null)
			viagem.setRoletaFinal1(dto.getRoletaFinal1() != null ? dto.getRoletaFinal1() : null);
		
		if(viagem.getLinhaRealizada() == null || dto.getIdLinhaRealizada() != null || dto.getFlagEditada().equals(new BigInteger("1"))) // pode dar errado quando tiver linha realizada diferente de linha programada
			viagem.setLinhaRealizada(dto.getIdLinhaRealizada() != null ? linhaRepository.findById(dto.getIdLinhaRealizada()).orElse(null) : null);
			
		// verificar o sincronismo com o serviço de troca de carro, pois pode dar problema quando o carro programado for diferente do carro realizado
		// se for enviado flagSD diferente de null (1 ou qq outro valor), sera atualizado o carro
		if( (dto.getIdCarroRealizado() != null && dto.getFlagSD() == null) || viagem.getCarroRealizado() == null || dto.getFlagEditada().equals(new BigInteger("1")) ) 
			viagem.setCarroRealizado(dto.getIdCarroRealizado() != null ? carroRepository.findById(dto.getIdCarroRealizado()).orElse(null) : null);
		
		if(viagem.getTipoViagemPerdida() == null || dto.getIdTipoViagemPerdida() != null )
			viagem.setTipoViagemPerdida(dto.getIdTipoViagemPerdida() != null ? tipoViagemPerdidaRepository.findById(dto.getIdTipoViagemPerdida()).orElse(null) : null);
		
		if(viagem.getMotivoPuloViagem() == null || dto.getIdMotivoPuloViagem() != null )
			viagem.setMotivoPuloViagem(dto.getIdMotivoPuloViagem() != null ? motivoPuloViagemRepository.findById(dto.getIdMotivoPuloViagem()).orElse(null) : null);
			
		if(viagem.getUsuarioResponsavelSaida() == null || dto.getIdUsuarioResponsavelSaida() != null || dto.getFlagEditada().equals(new BigInteger("1")))
			viagem.setUsuarioResponsavelSaida(dto.getIdUsuarioResponsavelSaida() != null ? usuarioRepository.findById(dto.getIdUsuarioResponsavelSaida()).orElse(null) : null);
		
		if(viagem.getUsuarioResponsavelChegada() == null || dto.getIdUsuarioResponsavelChegada() != null)
			viagem.setUsuarioResponsavelChegada(dto.getIdUsuarioResponsavelChegada() != null ? usuarioRepository.findById(dto.getIdUsuarioResponsavelChegada()).orElse(null) : null);
			
		if(viagem.getObsViagemPerdida() == null || dto.getObsViagemPerdida() != null)
			viagem.setObsViagemPerdida(dto.getObsViagemPerdida());
		
		if(viagem.getObsViagemPulada() == null || dto.getObsViagemPulada() != null)
			viagem.setObsViagemPulada(dto.getObsViagemPulada());
		
		if(dto.getFlagSD() != null && dto.getHoraSD() != null ) {
			viagem.setSd(true);
			viagem.setHoraSD(DataUtil.getTime(DataUtil.getDataStringYYYYMMDD(viagem.getDataCompetencia()), dto.getHoraSD()));
		}
		
		//if(dto.getIdPontoOrigem() != null && dto.getIdPontoDestino() != null && (dto.getIdLinhaRealizada() != null || dto.getIdLinhaProgramada() != null)) {
		if(dto.getIdPontoOrigem() != null && dto.getIdPontoDestino() != null && dto.getIdLinhaRealizada() != null) { // mudado por cauda da criacao de guia nova pelo app que nao manda id linha 20190325
			PontoLinha pontoLinha = pontoLinhaRepository.findByPontoOrigemIdAndPontoDestinoIdAndLinhaId(dto.getIdPontoOrigem(), 
																									    dto.getIdPontoDestino(), 
																										dto.getIdLinhaRealizada()).get(); // mudado por cauda da criacao de guia nova pelo app que nao manda id linha 20190325
			if (viagem.getPontoLinha() != null && !viagem.getPontoLinha().equals(pontoLinha)
					&& !viagem.getLinhaRealizada().equals(viagem.getPontoLinha().getLinha())) {
				verificarTrocaDeLinha(viagem, pontoLinha);
				viagem.setPontoLinha(pontoLinha);
			}
		}
		
		
		
		return salvar(viagem);
	}
	
	
	public Viagem salvar(Viagem viagem) {
		if(viagem.getOrdemViagem() == null)
			viagem.setOrdemViagem(viagemRepositoryImpl.buscarOrdemProximaVigem(viagem));
		
		return viagemRepository.save(viagem);
	}
	
	
	public void excluir(Viagem viagem){
		try {
			viagemRepository.delete(viagem);
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Existe registros cadastrados para a linha " + viagem);
		}
	}


	
	
	
	
	// CADASTRAR VIAGEM PROGRAMADA PELO APP APÓS CADASTRO DE UMA NOVA GUIA (PROGRAMACAO) - INICIO
	/*
	public List<Viagem> cadastrarViagensProgramadasDTO(List<ViagemDTO> viagensDTO) {
		List<Viagem> viagens = new ArrayList<>();
		viagensDTO.forEach(dto -> viagens.add(cadastrarViagemProgramadaPeloApp(dto)));
		return viagens;
	}
	*/

	
	public Viagem cadastrarViagemProgramadaPeloApp(ViagemDTO dto) {
		try {
			Programacao programacao = programacaoRepository.carregarProgramacao(dto.getIdProgramacao());
			Date dataCompetencia = programacao.getDataCompetencia();
			Date horaSaidaProgramada = DataUtil.getTime(programacao.getDataCompetencia(), dto.getHoraSaidaProgramada());
			Linha linhaProgramada = linhaRepository.findById(dto.getIdLinhaProgramada()).orElse(null);
			PontoLinha pontoLinha = pontoLinhaRepository.findByPontoOrigemIdAndPontoDestinoIdAndLinhaId(dto.getIdPontoOrigem(), dto.getIdPontoDestino(), dto.getIdLinhaProgramada()).get();
			
			Viagem viagem = new Viagem(programacao, dataCompetencia, horaSaidaProgramada, linhaProgramada, pontoLinha);
			
			return salvar(viagem);
		} catch (Exception e) {
			throw new ImpossivelIncluirEntidadeException("Não foi possível cadastrar viagem " + dto.getHoraSaidaProgramada());
		}
	}
	// CADASTRAR VIAGEM PROGRAMADA PELO APP APÓS CADASTRO DE UMA NOVA GUIA (PROGRAMACAO) - FIM
	
	
	// CADASTRAR VIAGEM EXTRA - INICIO
	public List<Viagem> cadastrarViagensExtrasDTO(List<ViagemDTO> viagensDTO) {
		List<Viagem> viagens = new ArrayList<>();
		viagensDTO.forEach(dto -> viagens.add(cadastarViagemExtras(dto)));
		return viagens;
	}

	Viagem cadastarViagemExtras(ViagemDTO dto) {
		try {
			Programacao programacao = programacaoRepository.carregarProgramacao(dto.getIdProgramacao());
			Date dataCompetencia = programacao.getDataCompetencia();
			Date horaSaidaRealizada = DataUtil.getTime(programacao.getDataCompetencia(), dto.getHoraSaidaRealizada());
			Linha linhaRealizada = linhaRepository.findById(dto.getIdLinhaRealizada()).orElse(null);
			PontoLinha pontoLinha = pontoLinhaRepository.findByPontoOrigemIdAndPontoDestinoIdAndLinhaId(dto.getIdPontoOrigem(), dto.getIdPontoDestino(), dto.getIdLinhaRealizada()).get();
			Carro carroRealizado = carroRepository.findById(dto.getIdCarroRealizado()).orElse(null);
			Usuario usuarioRespSaida = usuarioRepository.findById(dto.getIdUsuarioResponsavelSaida()).orElse(null);
			//BigInteger roletaFinal1 = programacao.carroAtual().getRoletaInicial1();
			BigInteger roletaFinal1 = dto.getRoletaFinal1();
			
			Viagem viagem = new Viagem(programacao, dataCompetencia, horaSaidaRealizada, linhaRealizada, pontoLinha, carroRealizado, usuarioRespSaida, roletaFinal1);
			
			return salvar(viagem);
		} catch (Exception e) {
			throw new ImpossivelIncluirEntidadeException("Não foi possível cadastrar viagem ");
		}
	}
	// CADASTRAR VIAGEM EXTRA - FIM
	
	
	
	
	
	//PRIVATE METHODS
	void verificarTrocaDeLinha(Viagem viagem, PontoLinha pontoLinha) {
		Viagem proximaViagem = viagemRepositoryImpl.buscarProximaVigem(viagem);
		if(proximaViagem != null) {
			proximaViagem.setPontoLinha(pontoLinhaRepositoryImpl.buscarPontoLinhaInverso(pontoLinha));
			salvar(proximaViagem);
		}
	}
	
}

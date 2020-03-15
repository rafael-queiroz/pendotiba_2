package br.com.logic.pendotiba.logicbus.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.EntradaSaidaDeCarroDaGaragem;
import br.com.logic.pendotiba.core.model.PontoLinha;
import br.com.logic.pendotiba.core.model.Programacao;
import br.com.logic.pendotiba.core.model.Viagem;
import br.com.logic.pendotiba.core.repository.EntradaSaidaDeCarroDaGaragemRepository;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Service
public class EntradaSaidaDeCarroDaGaragemService {
	
	@Autowired
	EntradaSaidaDeCarroDaGaragemRepository entradaSaidaDeCarroDaGaragemRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	MapaDiarioCarroService mapaDiarioCarroService;
	
	
	
	public EntradaSaidaDeCarroDaGaragem salvar(EntradaSaidaDeCarroDaGaragem entradaSaidaDeCarroDaGaragem){
		entradaSaidaDeCarroDaGaragem.setFuncionario(usuarioService.getUsuarioLogado().getFuncionario());
		
		Optional<EntradaSaidaDeCarroDaGaragem> persistido = entradaSaidaDeCarroDaGaragemRepository.findByCarroAndTurnoAndDataCompetencia(entradaSaidaDeCarroDaGaragem.getCarro(),
				entradaSaidaDeCarroDaGaragem.getTurno(), entradaSaidaDeCarroDaGaragem.getDataCompetencia());
		
		if (persistido.isPresent() && !persistido.get().getId().equals(entradaSaidaDeCarroDaGaragem.getId()))
			throw new NegocioException("Já existe um registro de entrada e saída com os dados informados");
		
		return entradaSaidaDeCarroDaGaragemRepository.save(entradaSaidaDeCarroDaGaragem);
	}


	public void salvarPelaProgramacao(Programacao programacao) {
		EntradaSaidaDeCarroDaGaragem obj = new EntradaSaidaDeCarroDaGaragem();
		obj.setDataCompetencia(programacao.getDataCompetencia());
		obj.setCarro(programacao.getCarroRealizado());
		obj.setLinha(programacao.getLinha());
		obj.setFuncionario(usuarioService.getUsuarioLogado().getFuncionario());
		obj.setTurno(programacao.getTurno());
		
		for(Viagem viagem : programacao.getViagens()){
			if(viagem.getPontoLinha().getSentido().equals(PontoLinha.SENTIDO_IDA))
				obj.setQtdViagemProgramadaIda(obj.getQtdViagemProgramadaIda() != null ? obj.getQtdViagemProgramadaIda().add(BigDecimal.ONE) : BigDecimal.ONE);
			else if(viagem.getPontoLinha().getSentido().equals(PontoLinha.SENTIDO_VOLTA))
				obj.setQtdViagemProgramadaVolta(obj.getQtdViagemProgramadaVolta() != null ?obj.getQtdViagemProgramadaVolta().add(BigDecimal.ONE) : BigDecimal.ONE);
		}
		
		Optional<EntradaSaidaDeCarroDaGaragem> persistido = entradaSaidaDeCarroDaGaragemRepository.findByCarroAndTurnoAndDataCompetencia(obj.getCarro(),obj.getTurno(), obj.getDataCompetencia());
		if(!persistido.isPresent()) {
			EntradaSaidaDeCarroDaGaragem entradaSaidaDeCarroDaGaragem = salvar(obj);
			mapaDiarioCarroService.atualizarMapaPelaEntrada(entradaSaidaDeCarroDaGaragem);
		}
	}

}

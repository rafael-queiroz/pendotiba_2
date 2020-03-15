package br.com.logic.pendotiba.logicbus.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import br.com.logic.pendotiba.core.model.Programacao;
import br.com.logic.pendotiba.core.repository.ProgramacaoRepository;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.repo.ProgramacaoRepositoryImpl;
import br.com.logic.pendotiba.logicbus.service.UsuarioService;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelLiberarCarroException;

@Service
public class ProgramacaoValidation {
	
	@Autowired
	ProgramacaoRepository programacaoRepository;
	
	@Autowired
	ProgramacaoRepositoryImpl programacaoRepositoryImpl;
	
	@Autowired
	UsuarioService usuarioService;
	
	
	public void validarProgramacao(Programacao programacao, BindingResult result) {
		// corrigir instanciação do javascript na criação da lupa
		if(programacao.getMotorista().getId() == null)
			programacao.setMotorista(null);
	
		if(programacao.getParceiro().getId() == null)
			programacao.setParceiro(null);
		
		// carro realizado deve ser igual na programacao antes de liberado e com alguma viagem realizada
		programacao.setCarroRealizado(programacao.getCarroProgramado());
		
		// validar obrigatoriedades
		if(programacao.getDataCompetencia() == null)
			result.addError(new FieldError("", "dataCompetencia", "Data de competência é obrigatória"));
		
		if(programacao.getTurno() == null)
			result.addError(new FieldError("", "turno", "Turno é obrigatório"));
		
		if(programacao.getLinha() == null)
			result.addError(new FieldError("", "linha", "Linha é obrigatória"));
		
		if(programacao.getPontoPegadaMotorista() == null)
			result.addError(new FieldError("", "pontoPegadaMotorista", "Ponto de pegada é obrigatório"));
		
		if(programacao.getInicioJornada() == null)
			result.addError(new FieldError("", "inicioJornada", "Hora de início de jornada é obrigatória"));
		
		if(programacao.getInicioTrabalho() == null)
			result.addError(new FieldError("", "inicioTrabalho", "Hora de início de trabalho é obrigatória"));
		
		// status LIBERADO
		if(programacao.liberada()) {
			if(programacao.getCarroProgramado() == null)
				result.addError(new FieldError("", "carroProgramado", "Carro é obrigatório"));
			else if (programacaoRepositoryImpl.buscarPorDataCompetenciaCarroProgramadoAtiva(programacao, programacao.getCarroProgramado()) != null)
				result.addError(new FieldError("", "carroProgramado", "Já existe uma programação ativa para a data e carro informado"));
			
			if(programacao.getMotorista() == null)
				result.addError(new FieldError("", "motorista", "Motorista é obrigatório"));
			else if (programacaoRepositoryImpl.buscarPorDataCompetenciaMotoristaAtiva(programacao) != null)
				result.addError(new FieldError("", "motorista", "Já existe uma programação ativa para a data e motorista informado"));
			
			if(!DataUtil.ehHoje(programacao.getDataCompetencia()))
				result.addError(new FieldError("", "dataCompetencia", "Não é possível liberar uma programação com data de competência anterior"));
		}
		
		// status ENCERRADO
		if (programacao.encerrado()) {
			if(programacao.getTerminoJornada() == null)
				result.addError(new FieldError("", "terminoJornada", "Hora de término de jornada é obrigatório"));
			
			if(programacao.getTerminoTrabalho() == null)
				result.addError(new FieldError("", "terminoTrabalho", "Hora de término de trabalho é obrigatório"));
		}
		
		// status CORTADO
		if (programacao.inativa()) {
			if(programacao.possuiViagensRealizadas())
				result.addError(new FieldError("", "terminoJornada", "Hora de término de jornada é obrigatório"));
		}
	}
	
	
	
	public void validarLiberarProgramacaoDireto(Programacao programacao) {
		if(programacao.liberada()) {
			if(programacaoRepositoryImpl.buscarPorDataCompetenciaCarroProgramadoAtiva(programacao, programacao.getCarroProgramado()) != null)
				throw new ImpossivelLiberarCarroException("Já existe uma programação ativa para a data e carro informado");
			
			if(programacaoRepositoryImpl.buscarPorDataCompetenciaMotoristaAtiva(programacao) != null)
				throw new ImpossivelLiberarCarroException("Já existe uma programação ativa para a data e motorista informado");
		}
	}

}

package br.com.logic.pendotiba.logicbus.validation;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.model.CarroProgramacao;
import br.com.logic.pendotiba.core.model.ErroProgramacao;
import br.com.logic.pendotiba.core.model.Funcionario;
import br.com.logic.pendotiba.core.model.Garagem;
import br.com.logic.pendotiba.core.model.Linha;
import br.com.logic.pendotiba.core.model.Programacao;
import br.com.logic.pendotiba.core.model.Turno;
import br.com.logic.pendotiba.core.model.Usuario;

public class ImportacaoProgramacaoValidation {

	public static void validarLinha(Programacao programacao, Optional<Linha> linha) {
		if(linha.isPresent())
			programacao.setLinha(linha.get());
		else
			programacao.getErros().add(new ErroProgramacao("Linha não encontrada", programacao.getDataCompetencia()));
	}
	

	public static void validarMotorista(Programacao programacao, Funcionario funcionario) {
		if(funcionario != null) 
			programacao.setMotorista(funcionario);
		else
			programacao.getErros().add(new ErroProgramacao("Motorista não encontrado", programacao.getDataCompetencia()));
	}
	
	
	public static void validarTurno(Programacao programacao, Turno turno) {
		if(turno != null)
			programacao.setTurno(turno);
		else
			programacao.getErros().add(new ErroProgramacao("Turno não encontrado", programacao.getDataCompetencia()));
	}


	public static void validarCarroProgramado(Programacao programacao, Carro carro, Usuario usuarioLogado) {
		if(carro != null) {
			programacao.setCarroProgramado(carro);
			programacao.getRoletas().add(
					new CarroProgramacao(programacao, carro, new Date(), carro.getRoleta1(), usuarioLogado)
					);
		} else
			programacao.getErros().add(new ErroProgramacao("CarroGlobus não encontrado", programacao.getDataCompetencia()));
	}


	public static void validarPontoPegadaMotorista(Programacao programacao) {
		Collections.sort(programacao.getViagens());
		if(!programacao.getViagens().isEmpty() && programacao.getViagens().get(0).getPontoLinha() != null)
			programacao.setPontoPegadaMotorista(programacao.getViagens().get(0).getPontoLinha().getPontoOrigem());
		else 
			programacao.getErros().add(new ErroProgramacao("Ponto de pegada não encontrado", programacao.getDataCompetencia()));
	}


	public static void validarPegadaLocal(Programacao programacao, Optional<Garagem> garagem) {
		if(garagem.isPresent())
			programacao.setGaragem(garagem.get());
	}
	
}
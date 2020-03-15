package br.com.logic.pendotiba.logicbus.validation;

import java.math.BigInteger;
import java.util.Date;
import java.util.Optional;

import br.com.logic.pendotiba.core.model.ErroViagem;
import br.com.logic.pendotiba.core.model.Linha;
import br.com.logic.pendotiba.core.model.PontoLinha;
import br.com.logic.pendotiba.core.model.Viagem;

public class ImportacaoViagemValidation {

	public static void validarLinha(Viagem viagem, Optional<Linha> linha) {
		if(linha.isPresent())
			viagem.setLinhaProgramada(linha.get());
		else
			viagem.getErros().add(new ErroViagem("Linha não encontrada", viagem.getDataCompetencia()));
	}

	
	public static void validarHora(Viagem viagem, Date hora) {
		if(hora != null)
			viagem.setHoraSaidaProgramada(hora);
		else
			viagem.getErros().add(new ErroViagem("Hora de saída programada não encontrada", viagem.getDataCompetencia()));
	}
	

	public static void validarOrdem(Viagem viagem, BigInteger ordem) {
		if(ordem != null)
			viagem.setOrdemViagem(ordem);
		else
			viagem.getErros().add(new ErroViagem("Ordem de viagem não encontrada", viagem.getDataCompetencia()));
	}


	public static void validarPontoLinha(Viagem viagem, Optional<PontoLinha> pontoLinha) {
		if(pontoLinha.isPresent())
			viagem.setPontoLinha(pontoLinha.get());
		else
			viagem.getErros().add(new ErroViagem("Ponto linha não encontrada", viagem.getDataCompetencia()));
	}


	public static boolean validarViagem(Viagem viagem) {
		boolean valido = true;
		
		return valido;
	}
	
}
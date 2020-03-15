package br.com.logic.pendotiba.logicbus.session;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import br.com.logic.pendotiba.core.model.Viagem;

@SessionScope
@Component
public class TabelaViagemSession {
	
	Set<TabelaViagemProgramacao> tabelas = new HashSet<>();

	
	
	public void adicionarViagem(String uuid, Viagem viagem) {
		TabelaViagemProgramacao tabela = buscarTabelaPorUuid(uuid);
		tabela.adicionarViagem(viagem);
		tabelas.add(tabela);
	}

	
	public void excluirViagem(String uuid, Long idViagem) {
		TabelaViagemProgramacao tabela = buscarTabelaPorUuid(uuid);
		tabela.excluirViagem(idViagem);
	}

	
	public List<Viagem> getViagens(String uuid) {
		return buscarTabelaPorUuid(uuid).getViagens();
	}
	
	
	TabelaViagemProgramacao buscarTabelaPorUuid(String uuid) {
		TabelaViagemProgramacao tabela = tabelas.stream()
				.filter(t -> t.getUuid().equals(uuid))
				.findAny()
				.orElse(new TabelaViagemProgramacao(uuid));
		return tabela;
	}


}

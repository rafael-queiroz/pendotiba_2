package br.com.logic.pendotiba.logicbus.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import br.com.logic.pendotiba.core.model.Viagem;

public class TabelaViagemProgramacao {
	
	String uuid;
	List<Viagem> viagens = new ArrayList<>();
	
	
	public TabelaViagemProgramacao(String uuid) {
		this.uuid = uuid;
	}

	
	public void adicionarViagem(Viagem viagem) {
		viagens.add(0, viagem);
	}
	
	
	public void excluirViagem(Long idViagem) {
		int indice = IntStream.range(0, viagens.size())
				.filter(i -> viagens.get(i).getId().equals(idViagem))
				.findAny().getAsInt();
		viagens.remove(indice);
	}
	
	
	public int total() {
		return viagens.size();
	}
	
	
	Optional<Viagem> buscarViagem(Viagem viagem) {
		return viagens.stream()
				.filter(p -> p.equals(viagem))
				.findAny();
	}

	
	public List<Viagem> getViagens() {
		return viagens;
	}
	
	
	public String getUuid() {
		return uuid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TabelaViagemProgramacao other = (TabelaViagemProgramacao) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}


}

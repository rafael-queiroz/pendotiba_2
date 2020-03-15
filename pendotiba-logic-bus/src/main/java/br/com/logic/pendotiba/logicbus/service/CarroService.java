package br.com.logic.pendotiba.logicbus.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.model.MapaDiarioCarro;
import br.com.logic.pendotiba.core.model.Programacao;
import br.com.logic.pendotiba.core.repository.CarroRepository;
import br.com.logic.pendotiba.core.repository.ProgramacaoRepository;
import br.com.logic.pendotiba.core.repository.TipoCarroRepository;
import br.com.logic.pendotiba.logicbus.repo.CarroRepositoryImpl;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.service.exception.NegocioException;

@Service
public class CarroService {
	
	@Autowired
	CarroRepository carroRepository;
	
	@Autowired
	CarroRepositoryImpl carroRepositoryImpl;
	
	@Autowired
	ProgramacaoRepository programacaoRepository;
	
	@Autowired
	TipoCarroRepository tipoCarroRepository;
	
	
	public Carro buscarCarroPorNumeroDeOrdem(String numeroDeOrdem) {
		Optional<Carro> obj = carroRepository.findByNumeroDeOrdemAndAtivoIsTrue(numeroDeOrdem);
		//return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! carro: " + numeroDeOrdem + ", Tipo: " + Carro.class.getName()));
		return obj.isPresent() ? obj.get() : null;
	}
	

	public Carro salvar(Carro carro){
		Optional<Carro> carroPersistido = carroRepository.findByNumeroDeOrdemAndAtivoIsTrue(carro.getNumeroDeOrdem());
		if (carroPersistido.isPresent() && !carroPersistido.get().getId().equals(carro.getId()) && carroPersistido.get().getAtivo())
			throw new NegocioException("Já existe um carro com os dados informados");
		
		return carroRepository.save(carro);
	}
	
	
	public void excluir(Carro carro) {
		try {
			carroRepository.delete(carro);
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível excluir carro. Já foi usado em algum movimento.");
		}
	}
	
	
	public List<Carro> listarCarrosDisponiveisParaProgramacao(Programacao programacao) {
		List<Carro> carrosDisponiveis = carroRepositoryImpl.listarCarrosDisponiveisPorDataCompetencia(programacao.getDataCompetencia());
		if(programacao.getCarroProgramado() != null)
			carrosDisponiveis.add(programacao.getCarroProgramado());
		return carrosDisponiveis;
	}

	public void atualizarRoletas(Carro carro, BigInteger roleta) {
		if(roleta != null) {
			Carro carroPersistido = carroRepository.findOne(carro.getId());
			carroPersistido.setRoleta1(roleta);
			carroRepository.save(carroPersistido);
		}
	}
	
	public void atualizarOdometro(Carro carro, BigInteger odometro) {
		if(odometro != null) {
			Carro carroPersistido = carroRepository.findOne(carro.getId());
			carroPersistido.setOdometro(odometro);
			carroRepository.save(carroPersistido);
		}
	}
	
	public void atualizarUltimoAbastecimentoDiesel(Carro carro, BigDecimal volumeDiesel) {
		if(volumeDiesel != null) {
			Carro carroPersistido = carroRepository.findOne(carro.getId());
			carroPersistido.setUltimoAbastecimentoDiesel(volumeDiesel);
			carroRepository.save(carroPersistido);
		}
	}
	
	public void atualizarUltimoAbastecimentoArla(Carro carro, BigDecimal volumeArla) {
		if(volumeArla != null) {
			Carro carroPersistido = carroRepository.findOne(carro.getId());
			carroPersistido.setUltimoAbastecimentoArla(volumeArla);
			carroRepository.save(carroPersistido);
		}
	}
	
	public void atualizarPeloMapaDiario(Carro carro, MapaDiarioCarro mapa) {
		Carro carroPersistido = carroRepository.findOne(carro.getId());

		if(mapa.getRoleta() != null)
			carroPersistido.setRoleta1(mapa.getRoleta());
		
		if(mapa.getOdometro() != null)
			carroPersistido.setOdometro(mapa.getOdometro());
		
		if(mapa.getVolumeDiesel() != null)
			carroPersistido.setUltimoAbastecimentoDiesel(mapa.getVolumeDiesel());
		
		if(mapa.getVolumeArla() != null)
			carroPersistido.setUltimoAbastecimentoArla(mapa.getVolumeArla());
		
		carroRepository.save(carroPersistido);
	}


	//importacao escala somente mudar quando for p producao
	public Carro buscarOuCadastrarCarro(String numeroDeOrdem) {
		Optional<Carro> carroOptional = carroRepository.findByNumeroDeOrdemAndAtivoIsTrue(numeroDeOrdem);
		if(carroOptional.isPresent()) 
			return carroOptional.get();

		return carroOptional.isPresent() ? carroOptional.get() : salvar(new Carro(numeroDeOrdem, new BigInteger("0"), 1L));
	}

}

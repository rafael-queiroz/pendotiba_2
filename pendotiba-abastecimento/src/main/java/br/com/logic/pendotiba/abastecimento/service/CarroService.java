package br.com.logic.pendotiba.abastecimento.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.abastecimento.exception.ObjectNotFoundException;
import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.model.MapaDiarioCarro;
import br.com.logic.pendotiba.core.repository.CarroRepository;

@Service
public class CarroService {
	
	@Autowired
	CarroRepository carroRepository;
	
	
	public Carro buscarPorId(Long id) {
		Optional<Carro> obj = carroRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Carro.class.getName()));
	}

	public void atualizarRoletas(Carro carro, BigInteger roleta) {
		if(roleta != null) {
			Carro carroPersistido = buscarPorId(carro.getId());
			carroPersistido.setRoleta1(roleta);
			carroRepository.save(carroPersistido);
		}
	}
	
	public void atualizarOdometro(Carro carro, BigInteger odometro) {
		if(odometro != null) {
			Carro carroPersistido = buscarPorId(carro.getId());
			carroPersistido.setOdometro(odometro);
			carroRepository.save(carroPersistido);
		}
	}
	
	public void atualizarUltimoAbastecimentoDiesel(Carro carro, BigDecimal volumeDiesel) {
		if(volumeDiesel != null) {
			Carro carroPersistido = buscarPorId(carro.getId());
			carroPersistido.setUltimoAbastecimentoDiesel(volumeDiesel);
			carroRepository.save(carroPersistido);
		}
	}
	
	public void atualizarUltimoAbastecimentoArla(Carro carro, BigDecimal volumeArla) {
		if(volumeArla != null) {
			Carro carroPersistido = buscarPorId(carro.getId());
			carroPersistido.setUltimoAbastecimentoArla(volumeArla);
			carroRepository.save(carroPersistido);
		}
	}
	
	public void atualizarPeloMapaDiario(Carro carro, MapaDiarioCarro mapa) {
		Carro carroPersistido = buscarPorId(carro.getId());

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

}

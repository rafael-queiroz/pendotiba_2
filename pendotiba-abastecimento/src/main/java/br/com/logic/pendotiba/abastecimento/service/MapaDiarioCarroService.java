package br.com.logic.pendotiba.abastecimento.service;

import br.com.logic.pendotiba.abastecimento.dto.MapaDiarioCarroDTO;
import br.com.logic.pendotiba.core.model.MapaDiarioCarro;
import br.com.logic.pendotiba.core.repository.MapaDiarioCarroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class MapaDiarioCarroService {
	
	@Autowired
	MapaDiarioCarroRepository mapaDiarioCarroRepository;
	
	@Autowired
	FuncionarioService funcionarioService;
	
	@Autowired
	BombaAbastecimentoService bombaAbastecimentoService;
	
	
	
	public MapaDiarioCarro buscarPorId(Long id) {
		return mapaDiarioCarroRepository.findById(id).orElse(null);
	}
	
	public MapaDiarioCarro salvar(MapaDiarioCarro mapaCarro){
		return mapaDiarioCarroRepository.save(mapaCarro);
	}
	
	
	public MapaDiarioCarro salvarTanqueCheio(MapaDiarioCarroDTO dto) {
		MapaDiarioCarro obj = buscarPorId(dto.getId());
		obj.setDataHoraCadastroAbastecimentoDiesel(new Date());
		obj.setOdometro(obj.getOdometroAnterior());
		obj.setVolumeDiesel(BigDecimal.ZERO);
		obj.setVolumeArla(BigDecimal.ZERO);
		obj.setFuncionarioAbastecimentoOdometro(funcionarioService.buscarPorId(dto.getIdFuncionarioAbastecimentoOdometro()));
		return mapaDiarioCarroRepository.save(obj);
	}
	
	public MapaDiarioCarro salvarRoleta(MapaDiarioCarroDTO dto) {
		MapaDiarioCarro obj = buscarPorId(dto.getId());
		obj.setRoleta(dto.getRoleta());
		obj.setRoletaAnterior(dto.getRoletaAnterior());
		obj.setFuncionarioRoleta(funcionarioService.buscarPorId(dto.getIdFuncionarioRoleta()));
		obj.setDataHoraCadastroRoleta(new Date());

		return mapaDiarioCarroRepository.save(obj);
	}
	
	public MapaDiarioCarro salvarAbastecimento(MapaDiarioCarroDTO dto) {
		MapaDiarioCarro obj = buscarPorId(dto.getId());
		obj.setVolumeDiesel(dto.getVolumeDiesel());
		obj.setBombaAbastecimentoDiesel(bombaAbastecimentoService.buscarPorId(dto.getIdBombaAbastecimentoDiesel()));
		obj.setVolumeDieselAnterior(dto.getVolumeDieselAnterior());
		obj.setFuncionarioAbastecimentoOdometro(funcionarioService.buscarPorId(dto.getIdFuncionarioAbastecimentoOdometro()));
		
		if(obj.getDataHoraCadastroAbastecimentoDiesel() == null)
			obj.setDataHoraCadastroAbastecimentoDiesel(new Date());

		return mapaDiarioCarroRepository.save(obj);
	}
	
	public MapaDiarioCarro salvarAbastecimentoArla(MapaDiarioCarroDTO dto) {
		MapaDiarioCarro obj = buscarPorId(dto.getId());
		obj.setVolumeArla(dto.getVolumeArla());
		obj.setBombaAbastecimentoArla(bombaAbastecimentoService.buscarPorId(dto.getIdBombaAbastecimentoArla()));
		obj.setFuncionarioAbastecimentoOdometro(funcionarioService.buscarPorId(dto.getIdFuncionarioAbastecimentoOdometro()));
		obj.setDataHoraCadastroAbastecimentoArla(new Date());

		return mapaDiarioCarroRepository.save(obj);
	}
	
	public MapaDiarioCarro salvarOdometro(MapaDiarioCarroDTO dto) {
		MapaDiarioCarro obj = buscarPorId(dto.getId());
		obj.setOdometro(dto.getOdometro());
		obj.setOdometroAnterior(dto.getOdometroAnterior());
		obj.setFuncionarioAbastecimentoOdometro(funcionarioService.buscarPorId(dto.getIdFuncionarioAbastecimentoOdometro()));
		obj.setDataHoraCadastroOdometro(new Date());

		return mapaDiarioCarroRepository.save(obj);
	}

}

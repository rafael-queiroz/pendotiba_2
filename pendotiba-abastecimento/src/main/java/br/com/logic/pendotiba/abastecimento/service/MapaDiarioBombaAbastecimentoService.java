package br.com.logic.pendotiba.abastecimento.service;

import br.com.logic.pendotiba.abastecimento.dto.MapaDiarioBombaAbastecimentoDTO;
import br.com.logic.pendotiba.abastecimento.dto.StatusDescricaoVO;
import br.com.logic.pendotiba.core.model.MapaDiarioBombaAbastecimento;
import br.com.logic.pendotiba.core.model.MapaDiarioCarro;
import br.com.logic.pendotiba.core.repository.MapaDiarioBombaAbastecimentoRepository;
import br.com.logic.pendotiba.core.repository.MapaDiarioCarroRepository;
import br.com.logic.pendotiba.core.util.ConsumoNumberUtil;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.core.util.VolumeNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MapaDiarioBombaAbastecimentoService {
	
	@Autowired
	MapaDiarioBombaAbastecimentoRepository mapaDiarioBombaAbastecimentoRepository;
	
	@Autowired
	MapaDiarioCarroRepository mapaDiarioCarroRepository;
	
	
	
	public MapaDiarioBombaAbastecimento buscarPorId(Long id) {
		return mapaDiarioBombaAbastecimentoRepository.findById(id).orElse(null);
	}
	
	@Transactional
	public MapaDiarioBombaAbastecimento salvar(MapaDiarioBombaAbastecimentoDTO dto) {
		MapaDiarioBombaAbastecimento obj = buscarPorId(dto.getId());
		obj.setVolumeFinal(dto.getVolumeFinal());
		atualizarValores(obj);
		gerarAtualizarMapaDiaSeguinte(obj);
		return mapaDiarioBombaAbastecimentoRepository.save(obj);
	}
	
	// retirar
	void atualizarValores(MapaDiarioBombaAbastecimento mapaBombaAbastecimento) {
		// DIFERENÇA
		if(mapaBombaAbastecimento.getVolumeInicial() != null && mapaBombaAbastecimento.getVolumeFinal() != null)
			mapaBombaAbastecimento.setVolumeTotal(VolumeNumberUtil.calcularDiferencaVolumeBombaBastecimento(mapaBombaAbastecimento.getVolumeFinal(), mapaBombaAbastecimento.getVolumeInicial()));
		
		// VOLUME PELO ABASTECIMENTO
		if(mapaBombaAbastecimento.getVolumeFinal() != null) {
			BigDecimal totalVolume;
			if(mapaBombaAbastecimento.getBombaAbastecimento().isDiesel()) {
				List<MapaDiarioCarro> abastecimentosDiesel = mapaDiarioCarroRepository.findByDataCompetenciaAndBombaAbastecimentoDieselOrderByDataHoraCadastroAbastecimentoDiesel(mapaBombaAbastecimento.getDataCompetencia() ,mapaBombaAbastecimento.getBombaAbastecimento());
				totalVolume = abastecimentosDiesel.stream()
						.map(m -> m.getVolumeDiesel())
						.reduce(BigDecimal.ZERO, BigDecimal::add);
			} else {
				List<MapaDiarioCarro> abastecimentosArla = mapaDiarioCarroRepository.findByDataCompetenciaAndBombaAbastecimentoArlaOrderByDataHoraCadastroAbastecimentoArla(mapaBombaAbastecimento.getDataCompetencia() ,mapaBombaAbastecimento.getBombaAbastecimento());
				totalVolume = abastecimentosArla.stream()
						.map(m -> m.getVolumeArla())
						.reduce(BigDecimal.ZERO, BigDecimal::add);
			}
				
			mapaBombaAbastecimento.setVolumePeloAbastecimento(totalVolume);
		}
		
		// DIFERENÇA MAPA ABASTECIMENTO
		if(mapaBombaAbastecimento.getVolumeTotal() != null && mapaBombaAbastecimento.getVolumePeloAbastecimento() != null)
			mapaBombaAbastecimento.setDiferencaMapaAbastecimento(ConsumoNumberUtil.subtrair(mapaBombaAbastecimento.getVolumeTotal(), mapaBombaAbastecimento.getVolumePeloAbastecimento()));
	}
	
	void gerarAtualizarMapaDiaSeguinte(MapaDiarioBombaAbastecimento obj) {
		if (obj.getVolumeFinal() != null) {
			Date dataPosterior = DataUtil.getDataPosteriorEmDias(obj.getDataCompetencia(), 1);
			Optional<MapaDiarioBombaAbastecimento> mapaPersistidoOpt = mapaDiarioBombaAbastecimentoRepository.findByDataCompetenciaAndBombaAbastecimento(dataPosterior, obj.getBombaAbastecimento());
			MapaDiarioBombaAbastecimento mapa = null;
			
			if(mapaPersistidoOpt.isPresent()) {
				mapa = mapaPersistidoOpt.get();
				mapa.setDataHoraCadastro(new Date());
				mapa.setVolumeInicial(obj.getVolumeFinal());
			} else 
				mapa = new MapaDiarioBombaAbastecimento(dataPosterior, new Date(), obj.getVolumeFinal(), obj.getBombaAbastecimento());
			
			mapaDiarioBombaAbastecimentoRepository.save(mapa);
		}
	}
	
	public StatusDescricaoVO podeAbastecer() {
		List<MapaDiarioBombaAbastecimento> mapas = mapaDiarioBombaAbastecimentoRepository.findByDataCompetencia(DataUtil.getDataAnteriorEmDias(DataUtil.getDataAppAbastecimentoOdometroRoleta(), 1));
		
		String errorDescricao = "";
		Boolean podeAbastecer = true;
	
		if(mapas.isEmpty()) {
			podeAbastecer = false;
			errorDescricao = "Não há mapa diário no dia anterior";
		} else {
			for (MapaDiarioBombaAbastecimento m : mapas) 
				if(m.getVolumeFinal() == null)
					if (errorDescricao.isEmpty())
						errorDescricao = errorDescricao.concat(m.getBombaAbastecimento().getDescricao());
					else
						errorDescricao = errorDescricao.concat(", ").concat(m.getBombaAbastecimento().getDescricao());
			
			podeAbastecer = errorDescricao.isEmpty();
		}
		
		return new StatusDescricaoVO(podeAbastecer, errorDescricao);
	}
	
}

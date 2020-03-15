package br.com.logic.pendotiba.logicbus.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.BombaAbastecimento;
import br.com.logic.pendotiba.core.model.MapaDiarioBombaAbastecimento;
import br.com.logic.pendotiba.core.model.MapaDiarioCarro;
import br.com.logic.pendotiba.core.model.Perfil;
import br.com.logic.pendotiba.core.repository.BombaAbastecimentoRepository;
import br.com.logic.pendotiba.core.repository.MapaDiarioBombaAbastecimentoRepository;
import br.com.logic.pendotiba.core.repository.MapaDiarioCarroRepository;
import br.com.logic.pendotiba.core.util.ConsumoNumberUtil;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.core.util.VolumeNumberUtil;
import br.com.logic.pendotiba.logicbus.filter.MapaDiarioBombaAbastecimentoFilter;
import br.com.logic.pendotiba.logicbus.repo.MapaDiarioBombaAbastecimentoRepositoryImpl;
import br.com.logic.pendotiba.logicbus.resources.dto.MapaDiarioBombaAbastecimentoDTO;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;
import br.com.logic.pendotiba.logicbus.vo.StatusDescricaoVO;

@Service
public class MapaDiarioBombaAbastecimentoService {
	
	@Autowired
	MapaDiarioBombaAbastecimentoRepository mapaDiarioBombaAbastecimentoRepository;
	
	@Autowired
	MapaDiarioBombaAbastecimentoRepositoryImpl mapaDiarioBombaAbastecimentoRepositoryImpl;
	
	@Autowired
	BombaAbastecimentoRepository bombaAbastecimentoRepository;
	
	@Autowired
	MapaDiarioCarroRepository mapaDiarioCarroRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	
	public Page<MapaDiarioBombaAbastecimento> filtrar(MapaDiarioBombaAbastecimentoFilter filtro, Pageable pageable) {
		Page<MapaDiarioBombaAbastecimento> mapas = mapaDiarioBombaAbastecimentoRepositoryImpl.filtrar(filtro, pageable);
		
		for (MapaDiarioBombaAbastecimento mapa : mapas) {
			Date dataPosteriorEmDias = DataUtil.getDataPosteriorEmDias(mapa.getDataCompetencia(),1);
			Optional<MapaDiarioBombaAbastecimento> mapaPosterior = mapaDiarioBombaAbastecimentoRepository.findByDataCompetenciaAndBombaAbastecimento(dataPosteriorEmDias, mapa.getBombaAbastecimento());
			if(mapaPosterior.isPresent() && mapaPosterior.get().getVolumeFinal() != null && !usuarioService.getUsuarioLogado().getPerfil().getId().equals(Perfil.LOGIC))
				mapa.setPodeAlterar(false);
		}

		return mapas;
	}

	
	public MapaDiarioBombaAbastecimento salvar(MapaDiarioBombaAbastecimento mapaBombaAbastecimento){
		atualizarValores(mapaBombaAbastecimento);
		mapaBombaAbastecimento = mapaDiarioBombaAbastecimentoRepository.save(mapaBombaAbastecimento);
		gerarAtualizarMapaDiaSeguinte(mapaBombaAbastecimento);
		return mapaBombaAbastecimento;
	}
	

	void atualizarValores(MapaDiarioBombaAbastecimento mapaBombaAbastecimento) {
		// DATA DE CADASTRO
		mapaBombaAbastecimento.setDataHoraCadastro(new Date());
				
		// VOLUME INICIAL
		if(!StringUtils.isEmpty(mapaBombaAbastecimento.getVolumeInicialStr())) 
			mapaBombaAbastecimento.setVolumeInicial(VolumeNumberUtil.novoBigDecimalVolumeBombaAbastecimento(mapaBombaAbastecimento.getVolumeInicialStr()));
		
		// VOLUME FINAL
		if(!StringUtils.isEmpty(mapaBombaAbastecimento.getVolumeFinalStr()))
			mapaBombaAbastecimento.setVolumeFinal(VolumeNumberUtil.novoBigDecimalVolumeBombaAbastecimento(mapaBombaAbastecimento.getVolumeFinalStr()));
		
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

	
	public void excluir(MapaDiarioBombaAbastecimento mapaBombaAbastecimento) {
		try {
			mapaDiarioBombaAbastecimentoRepository.delete(mapaBombaAbastecimento);
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível excluir mapa. Já foi usado em algum fechamento.");
		}
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
	
	
	public Boolean mapaFechadoPorDataEBomba(Date dataCompetencia, BombaAbastecimento bombaAbastecimento) {
		Optional<MapaDiarioBombaAbastecimento> mapa = mapaDiarioBombaAbastecimentoRepository.findByDataCompetenciaAndBombaAbastecimento(dataCompetencia, bombaAbastecimento);
		if(mapa.isPresent() && mapa.get().getVolumeFinal() != null)
			return true;
		return false;
	}
	
	
	// APP - INÍCIO
	public MapaDiarioBombaAbastecimento salvarMapaApp(MapaDiarioBombaAbastecimentoDTO dto) {
		MapaDiarioBombaAbastecimento obj = mapaDiarioBombaAbastecimentoRepository.getOne(dto.getId());
		obj.setVolumeFinal(dto.getVolumeFinal());
		obj.setVolumeTotal(VolumeNumberUtil.subtrair(obj.getVolumeFinal(), obj.getVolumeInicial(), true)); //preupdate
		obj.setDataHoraCadastro(new Date()); //preupdate
		atualizarValores(obj);
		gerarAtualizarMapaDiaSeguinte(obj);
		return mapaDiarioBombaAbastecimentoRepository.save(obj);
	}
	// APP - FIM


}

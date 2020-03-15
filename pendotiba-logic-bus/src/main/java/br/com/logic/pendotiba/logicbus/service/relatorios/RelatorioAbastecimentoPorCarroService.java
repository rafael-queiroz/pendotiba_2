package br.com.logic.pendotiba.logicbus.service.relatorios;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.model.EntradaSaidaDeCarroDaGaragem;
import br.com.logic.pendotiba.core.model.MapaDiarioCarro;
import br.com.logic.pendotiba.core.model.ParametrosConsumo;
import br.com.logic.pendotiba.core.model.PontoLinha;
import br.com.logic.pendotiba.core.repository.CarroRepository;
import br.com.logic.pendotiba.core.repository.EntradaSaidaDeCarroDaGaragemRepository;
import br.com.logic.pendotiba.core.repository.MapaDiarioCarroRepository;
import br.com.logic.pendotiba.core.repository.ParametrosConsumoRepository;
import br.com.logic.pendotiba.core.repository.PontoLinhaRepository;
import br.com.logic.pendotiba.core.util.Constantes;
import br.com.logic.pendotiba.core.util.ConsumoNumberUtil;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.core.util.VolumeNumberUtil;
import br.com.logic.pendotiba.logicbus.filter.MapaDiarioCarroFilter;
import br.com.logic.pendotiba.logicbus.repo.MapaDiarioCarroRepositoryImpl;

@Service
public class RelatorioAbastecimentoPorCarroService {

	@Autowired
	MapaDiarioCarroRepository mapaDiarioCarroRepository;
	
	@Autowired
	MapaDiarioCarroRepositoryImpl mapaDiarioCarroRepositoryImpl;
	
	@Autowired
	CarroRepository carroRepository;
	
	@Autowired
	EntradaSaidaDeCarroDaGaragemRepository entradaSaidaDeCarroDaGaragemRepository;	
	
	@Autowired
	PontoLinhaRepository pontoLinhaRepository;
	
	@Autowired
	ParametrosConsumoRepository parametrosConsumoRepository;
	
	
	
	public Page<MapaDiarioCarro> filtrarPaginando(MapaDiarioCarroFilter filtro, Pageable pageable) {
		List<MapaDiarioCarro> mapasTratados = filtrarMapasTratandoCarroComMaisDeUmAbastecimento(filtro);
		
		if(!filtro.getAgrupamento().equals(2L))
				mapasTratados = tratarCarrosQueRodaramEmMaisDeUmaLinha(mapasTratados);
		
		if(filtro.getCarro() != null) {
			MapaDiarioCarro.ordenaPorCarroPorEntrada(mapasTratados);
		} else {
			if(filtro.getAgrupamento().equals(0L))
				MapaDiarioCarro.ordenaPorLinhaPorCarro(mapasTratados);
			else if (filtro.getAgrupamento().equals(1L))
				MapaDiarioCarro.ordenaPorTipoDeChassiPorCarro(mapasTratados);
			else
				MapaDiarioCarro.ordenaPorCarroPorEntrada(mapasTratados);
		}
		
		return new PageImpl<>(mapasTratados, pageable, mapasTratados.size());
	}


	public List<MapaDiarioCarro> filtrar(MapaDiarioCarroFilter filtro) {
		List<MapaDiarioCarro> mapas = filtrarMapasTratandoCarroComMaisDeUmAbastecimento(filtro);
		
		if(!filtro.getAgrupamento().equals(2L))
			mapas = tratarCarrosQueRodaramEmMaisDeUmaLinha(mapas);
		
		if(filtro.getAgrupamento().equals(0L))
			MapaDiarioCarro.ordenaPorLinhaPorCarro(mapas);
		else if (filtro.getAgrupamento().equals(1L))
			MapaDiarioCarro.ordenaPorTipoDeChassiPorCarro(mapas);
		else
			MapaDiarioCarro.ordenaPorCarroPorEntrada(mapas);

		return mapas;
	}
	
	
	List<MapaDiarioCarro> tratarCarrosQueRodaramEmMaisDeUmaLinha(List<MapaDiarioCarro> mapas) {
		List<MapaDiarioCarro> mapaTratados = new ArrayList<>();
		
		for (MapaDiarioCarro mapa : mapas) {
			List<EntradaSaidaDeCarroDaGaragem> saidas = entradaSaidaDeCarroDaGaragemRepository.findByDataCompetenciaAndCarro(mapa.getDataCompetencia(), mapa.getCarro());
			boolean mesmaLinha = true;
			
			for (EntradaSaidaDeCarroDaGaragem saida : saidas)
				if(saida.getLinha() != null && !saida.getLinha().equals(mapa.getLinha())) {
					mesmaLinha = false;
					break;
				}
			
			if(mesmaLinha)
				mapaTratados.add(mapa);
			else 
				mapaTratados.addAll(dividirMapaPelasSaidas(mapa, saidas));
		}
		
		return mapaTratados;
	}


	List<MapaDiarioCarro> dividirMapaPelasSaidas(MapaDiarioCarro mapa, List<EntradaSaidaDeCarroDaGaragem> saidas) {
		List<MapaDiarioCarro> mapasTratados = new ArrayList<>();
		
		if(!validarSaidas(mapa, saidas))
			mapasTratados.add(mapa);
		else {
			
			BigDecimal distanciaPercorridaGeral = BigDecimal.ZERO;
			for (EntradaSaidaDeCarroDaGaragem saida : saidas) {
				BigDecimal distanciaOciosaGeral = BigDecimal.ZERO;
				BigDecimal distanciaProdutivaIda = BigDecimal.ZERO;
				BigDecimal distanciaProdutivaVolta = BigDecimal.ZERO;
				List<PontoLinha> pontos = pontoLinhaRepository.findByLinha(saida.getLinha());
				for (PontoLinha ponto : pontos) {
					distanciaOciosaGeral = distanciaOciosaGeral.add(ponto.getDistanciaOciosa()); 
					if(ponto.ehIda())
						distanciaProdutivaIda = distanciaProdutivaIda.add(ponto.getDistanciaProdutiva());
					else
						distanciaProdutivaVolta = distanciaProdutivaVolta.add(ponto.getDistanciaProdutiva());
				}
				
				if(saida.getQtdViagemRealizadaIda() != null && saida.getQtdViagemRealizadaVolta() != null)
					distanciaPercorridaGeral = distanciaPercorridaGeral.add(distanciaOciosaGeral)
																   .add(saida.getQtdViagemRealizadaIda().multiply(distanciaProdutivaIda))
																   .add(saida.getQtdViagemRealizadaVolta().multiply(distanciaProdutivaVolta));
			}
			
			saidas = verificarSaidasComMesmaLinhaAgrupando(saidas);
			
			for (EntradaSaidaDeCarroDaGaragem saida : saidas) {
				BigDecimal distanciaPercorrida = saida.getDistanciaOciosa().add(saida.getDistanciaProdutiva());
				BigDecimal t1 = distanciaPercorrida.multiply(Constantes.VALOR_CEM);
				
				BigDecimal percentualPercorrido = t1.divide(distanciaPercorridaGeral, 2, RoundingMode.HALF_UP);
				BigDecimal volumeDiesel = percentualPercorrido.multiply(mapa.getVolumeDiesel()).divide(Constantes.VALOR_CEM, 1, RoundingMode.HALF_UP);
				
				MapaDiarioCarro mapaNovo = new MapaDiarioCarro(mapa.getDataCompetencia(), mapa.getCarro());
				mapaNovo.setDataHoraCadastroAbastecimentoDiesel(mapa.getDataHoraCadastroAbastecimentoDiesel());
				mapaNovo.setVolumeDiesel(volumeDiesel);
				mapaNovo.setLinha(saida.getLinha());
				mapaNovo.setDiferencaOdometro(distanciaPercorrida.toBigInteger());
				
				// KM POR LITRO REALIZADO
				mapaNovo.setKmPorLitro(ConsumoNumberUtil.dividir(distanciaPercorrida, volumeDiesel));
				
				// META DE CONSUMO
				Optional<ParametrosConsumo> pc = parametrosConsumoRepository.findByLinhaAndTipoChassi(mapaNovo.getLinha(), mapaNovo.getCarro().getTipoChassi());
				if(pc.isPresent()) {
					BigDecimal meta = pc.get().getMetaConsumoDieselPorMes(DataUtil.getMes(mapaNovo.getDataCompetencia()));
					
					BigDecimal diferencaConsumidoParaMeta = ConsumoNumberUtil.subtrair(mapaNovo.getKmPorLitro(), meta);
					BigDecimal fatorPercentual = ConsumoNumberUtil.multiplicar(diferencaConsumidoParaMeta, VolumeNumberUtil.novoBigDecimal(100));
					BigDecimal valorPercentual = ConsumoNumberUtil.dividir(fatorPercentual, meta);
					mapaNovo.setDiferencaKmPorLitroPercentual(valorPercentual);
					mapaNovo.setMetaConsumoDiesel(meta);
				}
				
				mapasTratados.add(mapaNovo);
			}
		}
		
		return mapasTratados;
	}


	List<EntradaSaidaDeCarroDaGaragem> verificarSaidasComMesmaLinhaAgrupando(List<EntradaSaidaDeCarroDaGaragem> saidas) {
		List<EntradaSaidaDeCarroDaGaragem> saidasTratadas = new ArrayList<>();
		EntradaSaidaDeCarroDaGaragem.ordenarPorLinha(saidas);
		for(EntradaSaidaDeCarroDaGaragem saida : saidas)
			if(!saidasTratadas.isEmpty() && saida.getLinha().getId().equals(saidasTratadas.get(saidasTratadas.size()-1).getLinha().getId()) 
					&& saida.getDistanciaOciosa() != null &&  saida.getDistanciaProdutiva() != null
					&& saida.getQtdViagemRealizadaIda() != null && saida.getQtdViagemRealizadaVolta() != null) {
				EntradaSaidaDeCarroDaGaragem saidaTratada = saidasTratadas.get(saidasTratadas.size()-1);
				//saidaTratada.setQtdViagemRealizada(saidaTratada.getQtdViagemRealizada().add(saida.getQtdViagemRealizada()));
				
				saidaTratada.setQtdViagemRealizadaIda(saidaTratada.getQtdViagemRealizadaIda() != null ? 
						saidaTratada.getQtdViagemRealizadaIda().add(saida.getQtdViagemRealizadaIda())
						: saida.getQtdViagemRealizadaIda());
				
				saidaTratada.setQtdViagemRealizadaVolta(saidaTratada.getQtdViagemRealizadaVolta() != null ? 
						saidaTratada.getQtdViagemRealizadaVolta().add(saida.getQtdViagemRealizadaVolta())
						: saida.getQtdViagemRealizadaVolta());
				
				saidaTratada.setDistanciaOciosa(saidaTratada.getDistanciaOciosa().add(saida.getDistanciaOciosa()));
				
				saidaTratada.setDistanciaProdutiva(saidaTratada.getDistanciaProdutiva().add(saida.getDistanciaProdutiva()));
			} else
				saidasTratadas.add(saida);
		return saidasTratadas;
	}


	boolean validarSaidas(MapaDiarioCarro mapa, List<EntradaSaidaDeCarroDaGaragem> saidas) {
		for (EntradaSaidaDeCarroDaGaragem saida : saidas)
			if( (saida.getQtdViagemRealizadaIda() == null || saida.getQtdViagemRealizadaIda().equals(BigDecimal.ZERO)) 
					&& (saida.getQtdViagemRealizadaVolta() == null || saida.getQtdViagemRealizadaVolta().equals(BigDecimal.ZERO)))
				return false;
		
		if(mapa.getDiferencaOdometro() == null )
			return false;
		
		return true;
	}


	public List<MapaDiarioCarro> filtrarMapasComCarrosAbastecidos(List<MapaDiarioCarro> mapas) {
		List<MapaDiarioCarro> mapasCarrosAbastecidos = new ArrayList<>();
		for (MapaDiarioCarro mapa : mapas)
			if(mapa.getVolumeDiesel() != null && !mapa.getVolumeDiesel().equals(BigDecimal.ZERO.setScale(1)))
				mapasCarrosAbastecidos.add(mapa);
		return mapasCarrosAbastecidos;
	}
	
	
	public List<Carro> filtrarCarrosNaoAbastecidos(MapaDiarioCarroFilter filtro) {
		List<Carro> carros = carroRepository.findByAtivoIsTrueOrderByNumeroDeOrdem();
		List<MapaDiarioCarro> mapas = mapaDiarioCarroRepository.findByDataCompetenciaBetween(filtro.getDataInicial(), filtro.getDataFinal());

		mapas.forEach(m -> {
			if(m.getVolumeDiesel() != null)
				carros.remove(m.getCarro());
		});
		return carros;
	}
	
	
	public List<Carro> filtrarCarrosComTanqueCheio(MapaDiarioCarroFilter filtro) {
		List<Carro> carros = new ArrayList<>();
		List<MapaDiarioCarro> mapas = mapaDiarioCarroRepository.findByDataCompetenciaBetween(filtro.getDataInicial(), filtro.getDataFinal());
		
		for (MapaDiarioCarro mapa : mapas) 
			if(mapa.getVolumeDiesel() != null && mapa.getVolumeDiesel().equals(BigDecimal.ZERO.setScale(1)))
				carros.add(mapa.getCarro());
		return carros;
	}
	
	
	
	
	// PRIVATE METHODS
	List<MapaDiarioCarro> filtrarMapasTratandoCarroComMaisDeUmAbastecimento(MapaDiarioCarroFilter filtro) {
		List<MapaDiarioCarro> mapas = mapaDiarioCarroRepositoryImpl.listarPorFiltro(filtro);
		MapaDiarioCarro.ordenaPorCarroPorEntrada(mapas);
		
		List<MapaDiarioCarro> mapasTratados = new ArrayList<>();
		for (MapaDiarioCarro mapa : mapas) {
			MapaDiarioCarro ultimoMapa = null;
			if(!mapasTratados.isEmpty())
				ultimoMapa = mapasTratados.get(mapasTratados.size()-1);
			
			
			if (ultimoMapa != null && ultimoMapa.getCarro().equals(mapa.getCarro()) && ultimoMapa.getDataCompetencia().equals(mapa.getDataCompetencia())) {
				ultimoMapa.setVolumeDiesel(VolumeNumberUtil.somar(mapa.getVolumeDiesel(), ultimoMapa.getVolumeDiesel()));

				if(mapa.getOdometro() != null && ultimoMapa.getVolumeDiesel() != null && ultimoMapa.getVolumeDiesel().signum() > 0) {
					BigDecimal diferencaOdometro =  new BigDecimal(mapa.getOdometro()).subtract(new BigDecimal( ultimoMapa.getOdometroAnterior()));
					ultimoMapa.setOdometro(mapa.getOdometro());
					ultimoMapa.setKmPorLitro(ConsumoNumberUtil.dividir(diferencaOdometro,  ultimoMapa.getVolumeDiesel()));
				}
				
				if(ultimoMapa.getMetaConsumoDiesel() != null) {
					BigDecimal diferencaConsumidoParaMeta = ConsumoNumberUtil.subtrair(ultimoMapa.getKmPorLitro(), ultimoMapa.getMetaConsumoDiesel());
					BigDecimal fatorPercentual = ConsumoNumberUtil.multiplicar(diferencaConsumidoParaMeta, VolumeNumberUtil.novoBigDecimal(100));
					BigDecimal valorPercentual = ConsumoNumberUtil.dividir(fatorPercentual, ultimoMapa.getMetaConsumoDiesel());
					ultimoMapa.setDiferencaKmPorLitroPercentual(valorPercentual);
				}
			} else
				mapasTratados.add(mapa);
		}
		
		return mapasTratados;
	}

}

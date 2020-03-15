package br.com.logic.pendotiba.logicbus.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.model.Carro;
import br.com.logic.pendotiba.core.model.EntradaSaidaDeCarroDaGaragem;
import br.com.logic.pendotiba.core.model.MapaDiarioCarro;
import br.com.logic.pendotiba.core.model.ParametrosConsumo;
import br.com.logic.pendotiba.core.model.Programacao;
import br.com.logic.pendotiba.core.model.Turno;
import br.com.logic.pendotiba.core.repository.BombaAbastecimentoRepository;
import br.com.logic.pendotiba.core.repository.CarroRepository;
import br.com.logic.pendotiba.core.repository.EntradaSaidaDeCarroDaGaragemRepository;
import br.com.logic.pendotiba.core.repository.FuncionarioRepository;
import br.com.logic.pendotiba.core.repository.MapaDiarioCarroRepository;
import br.com.logic.pendotiba.core.repository.ParametrosConsumoRepository;
import br.com.logic.pendotiba.core.repository.TurnoRepository;
import br.com.logic.pendotiba.core.util.ConsumoNumberUtil;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.core.util.VolumeNumberUtil;
import br.com.logic.pendotiba.logicbus.filter.MapaDiarioCarroFilter;
import br.com.logic.pendotiba.logicbus.repo.MapaDiarioCarroRepositoryImpl;
import br.com.logic.pendotiba.logicbus.service.exception.ImpossivelExcluirEntidadeException;

@Service
@EnableScheduling
public class MapaDiarioCarroService {
	
	static final String TIME_ZONE = "America/Sao_Paulo";
	
	@Autowired
	MapaDiarioCarroRepository mapaDiarioCarroRepository;
	
	@Autowired
	MapaDiarioCarroRepositoryImpl mapaDiarioCarroRepositoryImpl;
	
	@Autowired
	CarroRepository carroRepository;
	
	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	@Autowired
	BombaAbastecimentoRepository bombaAbastecimentoRepository;
	
	@Autowired
	ParametrosConsumoRepository parametrosConsumoRepository;
	
	@Autowired
	MapaDiarioBombaAbastecimentoService mapaDiarioBombaAbastecimentoService;
	
	@Autowired
	EntradaSaidaDeCarroDaGaragemRepository entradaSaidaDeCarroDaGaragemRepository;
	
	@Autowired
	TurnoRepository turnoRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	
	
	public Page<MapaDiarioCarro>  filtrar(MapaDiarioCarroFilter filtro, Pageable pageable) {
		Page<MapaDiarioCarro> mapas = mapaDiarioCarroRepositoryImpl.filtrar(filtro, pageable);
		/*
		for (MapaDiarioCarro mapaDiarioCarro : mapas) {
			Boolean mapaDieselFechado = mapaDiarioBombaAbastecimentoService.mapaFechadoPorDataEBomba(mapaDiarioCarro.getDataCompetencia(), mapaDiarioCarro.getBombaAbastecimentoDiesel());
			Boolean mapaArlaFechado = mapaDiarioBombaAbastecimentoService.mapaFechadoPorDataEBomba(mapaDiarioCarro.getDataCompetencia(), mapaDiarioCarro.getBombaAbastecimentoArla());
			mapaDiarioCarro.setPodeAlterar(mapaArlaFechado == false || mapaDieselFechado == false);
		}
		*/
		
		for (MapaDiarioCarro mapaDiarioCarro : mapas) {
			Date dataPosteriorEmDias = DataUtil.getDataPosteriorEmDias(mapaDiarioCarro.getDataCompetencia(),3);
			Boolean mapaDieselFechado = mapaDiarioBombaAbastecimentoService.mapaFechadoPorDataEBomba(dataPosteriorEmDias, mapaDiarioCarro.getBombaAbastecimentoDiesel());
			mapaDiarioCarro.setPodeAlterar(mapaDieselFechado == false);
		}
		
		return mapas;
	}

	
	public MapaDiarioCarro salvar(MapaDiarioCarro mapaCarro){
		atualizarValores(mapaCarro);
		return mapaDiarioCarroRepository.save(mapaCarro);
	}
	
	void atualizarValores(MapaDiarioCarro mapaCarro) {
		// VOLUME DE DIESEL
		if(!StringUtils.isEmpty(mapaCarro.getVolumeDieselStr())) {
			mapaCarro.setVolumeDiesel(VolumeNumberUtil.novoBigDecimal(mapaCarro.getVolumeDieselStr().replace(",", ".")));
			if(mapaCarro.getDataHoraCadastroAbastecimentoDiesel() == null)
				mapaCarro.setDataHoraCadastroAbastecimentoDiesel(new Date());
		}
		
		// VOLUME DE ARLA
		if(!StringUtils.isEmpty(mapaCarro.getVolumeArlaStr())) {
			mapaCarro.setVolumeArla(VolumeNumberUtil.novoBigDecimal(mapaCarro.getVolumeArlaStr().replace(",", ".")));
			if(mapaCarro.getDataHoraCadastroAbastecimentoArla() == null)
				mapaCarro.setDataHoraCadastroAbastecimentoArla(new Date());
		}
		
		// ODOMETRO
		if(!StringUtils.isEmpty(mapaCarro.getOdometroStr())) {
			mapaCarro.setOdometro(new BigInteger(mapaCarro.getOdometroStr().replace(".", "")));
			if(mapaCarro.getDataHoraCadastroOdometro() == null)
				mapaCarro.setDataHoraCadastroOdometro(new Date());
		}
		
		// ROLETA
		if(!StringUtils.isEmpty(mapaCarro.getRoletaStr())) {
			mapaCarro.setRoleta(new BigInteger(mapaCarro.getRoletaStr().replace(".", "")));
			if(mapaCarro.getDataHoraCadastroRoleta() == null)
				mapaCarro.setDataHoraCadastroRoleta(new Date());
		}
		
		// VOLUME DIESEL ANTERIOR 
		if(mapaCarro.getVolumeDieselAnterior() == null)
			mapaCarro.setVolumeDieselAnterior(mapaCarro.getCarro().getUltimoAbastecimentoDiesel());
		
		// ODOMETRO ANTERIOR
		if(mapaCarro.getOdometroAnterior() == null)
			mapaCarro.setOdometroAnterior(mapaCarro.getCarro().getOdometro());
		
		// ROLETA ANTERIOR
		if(mapaCarro.getRoletaAnterior() == null)
			mapaCarro.setRoletaAnterior(mapaCarro.getCarro().getRoleta1());
		
		// KM POR LITRO REALIZADO
		if(mapaCarro.getOdometroAnterior() != null && mapaCarro.getOdometro() != null && mapaCarro.getVolumeDiesel() != null && mapaCarro.getVolumeDiesel().signum() > 0) {
			BigDecimal diferencaOdometro =  new BigDecimal(mapaCarro.getOdometro()).subtract(new BigDecimal( mapaCarro.getOdometroAnterior()));
			mapaCarro.setKmPorLitro(ConsumoNumberUtil.dividir(diferencaOdometro,  mapaCarro.getVolumeDiesel()));
		}
		
		// META DE CONSUMO
		Optional<ParametrosConsumo> pc = parametrosConsumoRepository.findByLinhaAndTipoChassi(mapaCarro.getLinha(), mapaCarro.getCarro().getTipoChassi());
		if(pc.isPresent()) {
			BigDecimal meta = pc.get().getMetaConsumoDieselPorMes(DataUtil.getMes(mapaCarro.getDataCompetencia()));
			
			BigDecimal diferencaConsumidoParaMeta = ConsumoNumberUtil.subtrair(mapaCarro.getKmPorLitro(), meta);
			BigDecimal fatorPercentual = ConsumoNumberUtil.multiplicar(diferencaConsumidoParaMeta, VolumeNumberUtil.novoBigDecimal(100));
			BigDecimal valorPercentual = ConsumoNumberUtil.dividir(fatorPercentual, meta);
			mapaCarro.setDiferencaKmPorLitroPercentual(valorPercentual);
			mapaCarro.setMetaConsumoDiesel(meta);
		}
		
	}

	public void excluir(MapaDiarioCarro abastecimentoOdometroRoleta) {
		try {
			mapaDiarioCarroRepository.delete(abastecimentoOdometroRoleta);
		} catch (DataIntegrityViolationException e) {
			throw new ImpossivelExcluirEntidadeException("Impossível excluir abastecimento, odômetro ou roleta. Já foi usado em algum fechamento.");
		}
	}
	
	public void atualizarMapaPelaProgramacao(Programacao programacao) {
		List<MapaDiarioCarro> mapas = mapaDiarioCarroRepository.findByDataCompetenciaAndCarro(programacao.getDataCompetencia(), programacao.getCarroRealizado());
		if(!mapas.isEmpty()) {
			mapas.forEach(m -> { 
				m.setLinha(programacao.getLinha());
				salvar(m);
			});
		}
	}
	
	public void atualizarMapaPelaEntrada(EntradaSaidaDeCarroDaGaragem entradaSaidaDeCarroDaGaragem) {
		List<MapaDiarioCarro> mapas = mapaDiarioCarroRepository.findByDataCompetenciaAndCarro(entradaSaidaDeCarroDaGaragem.getDataCompetencia(), entradaSaidaDeCarroDaGaragem.getCarro());
		if(!mapas.isEmpty()) {
			mapas.forEach(m -> { 
				m.setLinha(entradaSaidaDeCarroDaGaragem.getLinha());
				salvar(m);
			});
		}
	}
	
	
	
	
	/*
	// APP - INÍCIO
	public MapaDiarioCarro salvarRoleta(AbastecimentoOdometroRoletaDTO dto) {
		MapaDiarioCarro obj = mapaDiarioCarroRepository.findOne(dto.getId());
		obj.setRoleta(dto.getRoleta());
		obj.setRoletaAnterior(dto.getRoletaAnterior());
		obj.setFuncionarioRoleta(funcionarioRepository.findOne(dto.getIdFuncionarioRoleta()));
		obj.setDataHoraCadastro(new Date());
		atualizarValores(obj);
		return mapaDiarioCarroRepository.save(obj);
	}
	
	public MapaDiarioCarro salvarAbastecimento(AbastecimentoOdometroRoletaDTO dto) {
		MapaDiarioCarro obj = mapaDiarioCarroRepository.findOne(dto.getId());
		obj.setVolumeDiesel(dto.getVolumeDiesel());
		obj.setBombaAbastecimentoDiesel(bombaAbastecimentoRepository.findOne(dto.getIdBombaAbastecimentoDiesel()));
		obj.setVolumeDieselAnterior(dto.getVolumeDieselAnterior());
		obj.setFuncionarioAbastecimentoOdometro(funcionarioRepository.findOne(dto.getIdFuncionarioAbastecimentoOdometro()));
		obj.setDataHoraCadastro(new Date());
		atualizarValores(obj);
		return mapaDiarioCarroRepository.save(obj);
	}
	
	public MapaDiarioCarro salvarAbastecimentoArla(AbastecimentoOdometroRoletaDTO dto) {
		MapaDiarioCarro obj = mapaDiarioCarroRepository.findOne(dto.getId());
		obj.setVolumeArla(dto.getVolumeArla());
		obj.setBombaAbastecimentoArla(bombaAbastecimentoRepository.findOne(dto.getIdBombaAbastecimentoArla()));
		obj.setFuncionarioAbastecimentoOdometro(funcionarioRepository.findOne(dto.getIdFuncionarioAbastecimentoOdometro()));
		obj.setDataHoraCadastro(new Date());
		atualizarValores(obj);
		return mapaDiarioCarroRepository.save(obj);
	}
	
	public MapaDiarioCarro salvarOdometro(AbastecimentoOdometroRoletaDTO dto) {
		MapaDiarioCarro obj = mapaDiarioCarroRepository.findOne(dto.getId());
		obj.setOdometro(dto.getOdometro());
		obj.setOdometroAnterior(dto.getOdometroAnterior());
		obj.setFuncionarioAbastecimentoOdometro(funcionarioRepository.findOne(dto.getIdFuncionarioAbastecimentoOdometro()));
		obj.setDataHoraCadastro(new Date());
		atualizarValores(obj);
		return mapaDiarioCarroRepository.save(obj);
	}
	
	public MapaDiarioCarro salvarTanqueCheio(AbastecimentoOdometroRoletaDTO dto) {
		MapaDiarioCarro obj = mapaDiarioCarroRepository.findOne(dto.getId());
		obj.setVolumeDiesel(dto.getVolumeDiesel());
		obj.setFuncionarioAbastecimentoOdometro(funcionarioRepository.findOne(dto.getIdFuncionarioAbastecimentoOdometro()));
		obj.setDataHoraCadastro(new Date());
		return mapaDiarioCarroRepository.save(obj);
	}
	// APP - FIM 
	*/
	
	@Scheduled(cron = "00 00 08 * * *", zone = TIME_ZONE)
	void gerarMapasDoDia() {
		List<Carro> carros = carroRepository.findByAtivoIsTrueOrderByNumeroDeOrdem();
		for (Carro carro : carros) {
			List<EntradaSaidaDeCarroDaGaragem> saidas = entradaSaidaDeCarroDaGaragemRepository.findByDataCompetenciaAndCarroAndTurnoIn(new Date(), carro, turnoRepository.findByAgrupamento(Turno.PRIMEIRO_TURNO));
			if(saidas.isEmpty())
				salvar(new MapaDiarioCarro(new Date(), carro));
			else
				salvar(new MapaDiarioCarro(new Date(), carro, saidas.get(0).getLinha()));
		}
	}
	
	
	@Scheduled(cron = "00 05 08 * * *", zone = TIME_ZONE)
	void deletarMapasSemDados() {
		Date dataOntem = DataUtil.getDataAnteriorEmDias(new Date(), 1);
		List<MapaDiarioCarro> mapas = mapaDiarioCarroRepository.listarMapasSemDados(dataOntem);
		mapas.forEach(obj -> excluir(obj));
	}


	public File gerarArquivoAbastecimentoDieselTxt(MapaDiarioCarroFilter filtro) throws IOException {
		List<MapaDiarioCarro> mapas = mapaDiarioCarroRepositoryImpl.listarParaExportacaoDieselTransoft(filtro);
		
		MapaDiarioCarro.ordenaPorDataHoraCadastroAbastecimentoDiesel(mapas);
		
		Path path = Paths.get(System.getProperty("user.home"), "exportacoes");
		File f = new File( path.toAbsolutePath().toString() + "\\" + DataUtil.getDataStringYYYYMMDD(filtro.getDataInicial()) + ".txt");
		
		FileWriter arq = new FileWriter(f);
	    PrintWriter gravarArq = new PrintWriter(arq);
	    
	    for (MapaDiarioCarro mapa : mapas) {
	    	StringBuilder linha = new StringBuilder();
	    	linha.append(String.format("%-10s", DataUtil.getDataStringDDMMYYYY(mapa.getDataHoraCadastroAbastecimentoDiesel()))); // data cadastro
	    	linha.append(String.format("%-1s", ""));
	    	linha.append(String.format("%-8s", DataUtil.getHoraMinutoSegundo(mapa.getDataHoraCadastroAbastecimentoDiesel()))); // hora cadastro
	    	linha.append(String.format("%-1s", ""));
	    	linha.append(String.format("%-15s", mapa.getFuncionarioAbastecimentoOdometro() != null ? mapa.getFuncionarioAbastecimentoOdometro().getMatricula() : "")); // funcionario
	    	linha.append(String.format("%-1s", ""));
	    	linha.append(String.format("%-15s", mapa.getCarro())); // carro
	    	linha.append(String.format("%-1s", ""));
	    	linha.append(String.format("%-7s", mapa.getOdometro() != null ? mapa.getOdometro() : "")); // odometro
	    	linha.append(String.format("%-1s", ""));
	    	linha.append(String.format("%-10s", mapa.getVolumeDieselStr().replace(".", "").concat("0"))); //volume
	    	linha.append(String.format("%-1s", ""));
	    	linha.append(String.format("%-10s", mapa.getDiferencaOdometro() != null ? mapa.getDiferencaOdometro() : "")); //km percorrida
	    	linha.append(String.format("%-1s", ""));
	    	linha.append(String.format("%-10s", "")); // observacao
	    	linha.append(String.format("%-1s", ""));
	    	linha.append(String.format("%-2s", "01")); // terminal
	    	linha.append(String.format("%-1s", ""));
	    	linha.append(String.format("%-1s", mapa.getBombaAbastecimentoDiesel() != null ? mapa.getBombaAbastecimentoDiesel().getCodigoExportacao() : "")); // bomba   	
	    	gravarArq.println(linha.toString());
		}

	    arq.close();
	    
	    return f;
	}

	public File gerarArquivoAbastecimentoArlaTxt(MapaDiarioCarroFilter filtro) throws IOException {
		List<MapaDiarioCarro> mapas = mapaDiarioCarroRepositoryImpl.listarParaExportacaoArlaTransoft(filtro);
		
		MapaDiarioCarro.ordenaPorDataHoraCadastroAbastecimentoDiesel(mapas);
		
		Path path = Paths.get(System.getProperty("user.home"), "exportacoes");
		File f = new File( path.toAbsolutePath().toString() + "\\" + DataUtil.getDataStringYYYYMMDD(filtro.getDataInicial()) + ".txt");
		
		FileWriter arq = new FileWriter(f);
	    PrintWriter gravarArq = new PrintWriter(arq);
	    
	    for (MapaDiarioCarro mapa : mapas) {
	    	StringBuilder linha = new StringBuilder();
	    	linha.append(String.format("%-10s", DataUtil.getDataStringDDMMYYYY(mapa.getDataHoraCadastroAbastecimentoDiesel()))); // data cadastro
	    	linha.append(String.format("%-1s", ""));
	    	linha.append(String.format("%-8s", DataUtil.getHoraMinutoSegundo(mapa.getDataHoraCadastroAbastecimentoDiesel()))); // hora cadastro
	    	linha.append(String.format("%-1s", ""));
	    	linha.append(String.format("%-15s", mapa.getFuncionarioAbastecimentoOdometro() != null ? mapa.getFuncionarioAbastecimentoOdometro().getMatricula() : "")); // funcionario
	    	linha.append(String.format("%-1s", ""));
	    	linha.append(String.format("%-15s", mapa.getCarro())); // carro
	    	linha.append(String.format("%-1s", ""));
	    	linha.append(String.format("%-7s", mapa.getOdometro() != null ? mapa.getOdometro() : "")); // odometro
	    	linha.append(String.format("%-1s", ""));
	    	linha.append(String.format("%-10s", mapa.getVolumeArlaStr().replace(".", "").concat("0"))); //volume
	    	linha.append(String.format("%-1s", ""));
	    	linha.append(String.format("%-10s", mapa.getDiferencaOdometro() != null ? mapa.getDiferencaOdometro() : "")); //km percorrida
	    	linha.append(String.format("%-1s", ""));
	    	linha.append(String.format("%-10s", "")); // observacao
	    	linha.append(String.format("%-1s", ""));
	    	linha.append(String.format("%-2s", "11")); // terminal
	    	linha.append(String.format("%-1s", ""));
	    	linha.append(String.format("%-1s", mapa.getBombaAbastecimentoArla() != null ? mapa.getBombaAbastecimentoArla().getCodigoExportacao() : "")); // bomba    	
	    	gravarArq.println(linha.toString());
		}

	    arq.close();
	    
	    return f;
	}
	
	public MapaDiarioCarro verificarAtualizandoProximoMapa(MapaDiarioCarro obj) {
		MapaDiarioCarro proximoMapa = mapaDiarioCarroRepositoryImpl.buscarProximoMapaPorDataPorCarro(obj.getDataCompetencia(), obj.getCarro());
		if(proximoMapa != null) {
			proximoMapa.setOdometroAnterior(obj.getOdometro());
			return salvar(proximoMapa);
		}
			
		return null;
	}

}

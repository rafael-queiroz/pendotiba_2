package br.com.logic.pendotiba.logicbus.service;

import br.com.logic.pendotiba.core.enums.TipoArquivoEnum;
import br.com.logic.pendotiba.core.model.*;
import br.com.logic.pendotiba.core.repository.*;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.repo.ProgramacaoImportadaRepositoryImpl;
import br.com.logic.pendotiba.logicbus.storage.local.ArquivoImportadoStorageLocal;
import br.com.logic.pendotiba.logicbus.util.ArquivoImportadoUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

@Service
public class ArquivoEscalaImportadoService {
	
	@Autowired
	ArquivoImportadoStorageLocal arquivoImportadoStorageLocal;
	
	@Autowired
	ArquivoImportadoRepository arquivoImportadoRepository;
	
	@Autowired
	EscalaImportadaRepository escalaImportadaRepository;
	
	@Autowired
	ViagemImportadaRepository viagemImportadaRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	PontoService pontoService;
	
	@Autowired
	LinhaService linhaService;
	
	@Autowired
	FuncionarioService funcionarioService;
	
	@Autowired
	CarroService carroService;
	
	@Autowired
	TurnoRepository turnoRepository;
	
	@Autowired
	ProgramacaoImportadaRepository programacaoImportadaRepository;
	
	@Autowired
	ProgramacaoImportadaRepositoryImpl programacaoImportadaRepositoryImpl;
	
	@Autowired
	ProgramacaoService programacaoService;
	
	

	
	public ArquivoImportado salvar(ArquivoImportado arquivoImportado){
		arquivoImportado.setDataImportacao(new Date());
		arquivoImportado.setTipoArquivo(TipoArquivoEnum.ESCALA);
		arquivoImportado.setUsuario(usuarioService.getUsuarioLogado());
		return arquivoImportadoRepository.save(arquivoImportado);
	}
	
	
	@SuppressWarnings({ "resource" })
	Iterator<Row> linhasPorArquivo(ArquivoImportado arquivoImportado) throws FileNotFoundException, IOException {
		FileInputStream fileInputStream = new FileInputStream(arquivoImportadoStorageLocal.recarregarArquivoImportado(arquivoImportado.getNomeArquivo()));
		
		//cria um workbook - planilha com todas as abas
		HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
		
		//recuperando a primeira aba
		HSSFSheet sheet = workbook.getSheetAt(0);
		
		//retorna todas as linhas da planilha 0 (aba 1)
		Iterator<Row> rowIterator = sheet.iterator();
		return rowIterator;
	}
	
	
	public Date buscarDataCompetencia(ArquivoImportado arquivoImportado) {
		try {
			Date data = null;
			Iterator<Row> rowIterator = linhasPorArquivo(arquivoImportado);
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();

				if(row.getRowNum() == 3 ) {
				//if(row.getRowNum() == 2 ) {
					String linha3 = ArquivoImportadoUtil.tratarCampoString(row.getCell(0));
					String dataLinha3 = linha3.substring(20, 30);
					data = DataUtil.getDateDDMMYYYYReturnDDMMYYYY(dataLinha3);
					break;
				}
			}		
			
			return data;
		} catch (FileNotFoundException fe) {
			throw new RuntimeException("Não foi possível encontrar arquivo", fe);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 
	}

	@Transactional
	public ArquivoImportado importar(ArquivoImportado arquivoImportado) {
		try {
			arquivoImportado.setDataCompetencia(buscarDataCompetencia(arquivoImportado));
			limparBasesImportadasPorDataDeCompetencia(arquivoImportado);
			
			// exlcuir arquivo com mesma data de competência
			ArquivoImportado arquivo = arquivoImportadoRepository.findByDataCompetenciaAndTipoArquivo(arquivoImportado.getDataCompetencia(), TipoArquivoEnum.ESCALA);
			if (arquivo != null)
				arquivoImportadoRepository.delete(arquivo);
			
			arquivoImportado = salvar(arquivoImportado);
			
			Iterator<Row> rowIterator = linhasPorArquivo(arquivoImportado);
			
			//varre todas as linhas da planilha 0 (aba 1)
			while (rowIterator.hasNext()) {
				
				//recebe cada linha da planilha
				Row row = rowIterator.next();
				
				//descartando a primeira linha com o header e linhas com codigo da escala em branco
				if(ArquivoImportadoUtil.linhaInvalida(row))
					continue;
				
				EscalaImportada ei = new EscalaImportada(arquivoImportado.getDataCompetencia(), //data de competencia
						DataUtil.getTime(arquivoImportado.getDataCompetencia(), ArquivoImportadoUtil.tratarCampoString(row.getCell(0))),  //hora de inicio da jornada
						DataUtil.getTime(arquivoImportado.getDataCompetencia(), ArquivoImportadoUtil.tratarCampoString(row.getCell(1))),  //hora de saida
						ArquivoImportadoUtil.tratarCampoFuncionarioMatricula(row.getCell(8)), 	//matricula motorista
						ArquivoImportadoUtil.tratarCampoFuncionarioNome(row.getCell(8)), 		//nome motorista
						//ArquivoImportadoUtil.tratarCampoNumeric(row.getCell(9)), 				//escala motorista
						//ArquivoImportadoUtil.tratarCampoString(row.getCell(9)), 				//escala motorista
						ArquivoImportadoUtil.tratarCampoFuncionarioMatricula(row.getCell(11)), 	//matricula parceiro
						ArquivoImportadoUtil.tratarCampoFuncionarioNome(row.getCell(11)), 		//nome parceiro
						///ArquivoImportadoUtil.tratarCampoNumeric(row.getCell(12)), 			//escala parceiro
						ArquivoImportadoUtil.tratarCampoString(row.getCell(15)), 				//numero de ordem carro
						buscarLinha(ArquivoImportadoUtil.tratarCampoNumeric(row.getCell(14))), 	//linha 
						buscarFuncionario(ArquivoImportadoUtil.tratarCampoFuncionarioMatricula(row.getCell(8))), 	//motorista
						buscarFuncionario(ArquivoImportadoUtil.tratarCampoFuncionarioMatricula(row.getCell(11))), 	//parceiro
						buscarCarro(ArquivoImportadoUtil.tratarCampoString(row.getCell(15))), //carro
						arquivoImportado); //arquivo importado
				
				/*20.12.2018
				if(ei.getMotorista() == null || !escalaImportadaRepository.findByArquivoImportadoAndMotorista(ei.getArquivoImportado(), ei.getMotorista()).isPresent()) {
					ProgramacaoImportada pi = programacaoImportadaRepository.findBySaidaAndOrdemViagem(ei.getSaida(), 1L);
					
					if(pi == null)
						pi = programacaoImportadaRepository.findByOrdemProgramacaoAndOrdemViagem("1A", 1L);

					if (pi.getOrdemProgramacao().contains("A"))
						ei.setTurno(turnoRepository.findById(1L));
					else 
						ei.setTurno(turnoRepository.findById(2L));

					ei.setOrdemProgramacao(pi.getOrdemProgramacao());
					//ei.setOrdemViagem(pi.getOrdemViagem());
					
					escalaImportadaRepository.save(ei);
				}
				*/
				/*
				if(ei.getCarro() != null)
				*/
				//ProgramacaoImportada pi = programacaoImportadaRepository.findBySaidaAndOrdemViagem(ei.getSaida(), 1L);
				
				String versao = null;
				if(DataUtil.ehDomingo(ei.getDataCompetencia()))
					versao = "D";
				else if(DataUtil.ehSabado(ei.getDataCompetencia()))
					versao = "S";
				else
					versao = "U";
				
				
				//ProgramacaoImportada pi = programacaoImportadaRepository.buscarPorHoraSaidaOrdemViagemDiaSemana(ei.getSaida(), 1L, versao); 11/10/2019
				ProgramacaoImportada pi = programacaoImportadaRepositoryImpl.buscarPorLinhaHoraSaidaHoraChegadaOrdemViagemDiaSemana(ei.getLinha(), ei.getIncioJornada(), ei.getSaida(), 1L, versao);
				
				if(pi == null)
					pi = programacaoImportadaRepositoryImpl.buscarPorLinhaHoraSaidaOrdemViagemDiaSemana(ei.getLinha(), ei.getSaida(), 1L, versao);
				
				if(pi != null) {
					//pi = programacaoImportadaRepository.findByOrdemProgramacaoAndOrdemViagem("1A", 1L);
					
					if(!pi.getMovimento().equals("VIAGEM"))
						pi = programacaoImportadaRepository.findByOrdemProgramacaoAndOrdemViagemAndVersao(pi.getOrdemProgramacao(), 2L, pi.getVersao());
					
					if (pi.getOrdemProgramacao().contains("A"))
						ei.setTurno(turnoRepository.findById(1L).orElse(null));
					
					else if (pi.getOrdemProgramacao().contains("B"))
						ei.setTurno(turnoRepository.findById(2L).orElse(null));
					
					else if (pi.getOrdemProgramacao().contains("C"))
						ei.setTurno(turnoRepository.findById(3L).orElse(null));
					
					else if (pi.getOrdemProgramacao().contains("E"))
						ei.setTurno(turnoRepository.findById(4L).orElse(null));
					
					else if (pi.getOrdemProgramacao().contains("F"))
						ei.setTurno(turnoRepository.findById(5L).orElse(null));
	
					ei.setOrdemProgramacao(pi.getOrdemProgramacao());
					//ei.setOrdemViagem(pi.getOrdemViagem());
				}
				
				escalaImportadaRepository.save(ei);
			}

			return arquivoImportado;
		} catch (FileNotFoundException fe) {
			throw new RuntimeException("Não foi possível encontrar arquivo", fe);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Não foi possível gerar registros", e);
		}
	}


	@Transactional
	public void limparBasesImportadasPorDataDeCompetencia(ArquivoImportado arquivoImportado) {
		viagemImportadaRepository.deleteByDataCompetencia(arquivoImportado.getDataCompetencia());
		escalaImportadaRepository.deleteByDataCompetencia(arquivoImportado.getDataCompetencia());
	}


	private Linha buscarLinha(String codigoLinha) {
		if(!StringUtils.isEmpty(codigoLinha)) 
			return linhaService.buscarPorCodigo(codigoLinha);
		return null;
	}


	Carro buscarCarro(String numeroDeOrdem) {
		if(!StringUtils.isEmpty(numeroDeOrdem)) 
			return carroService.buscarOuCadastrarCarro(numeroDeOrdem);
		return null;
	}


	Funcionario buscarFuncionario(String matricula) {
		if(!StringUtils.isEmpty(matricula))
			return funcionarioService.buscarFuncionarioPorMatricula(matricula);
		return null;
	}

}

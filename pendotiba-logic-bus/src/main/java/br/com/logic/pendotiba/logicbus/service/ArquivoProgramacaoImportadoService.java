package br.com.logic.pendotiba.logicbus.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.logic.pendotiba.core.enums.TipoArquivoEnum;
import br.com.logic.pendotiba.core.model.ArquivoImportado;
import br.com.logic.pendotiba.core.model.Linha;
import br.com.logic.pendotiba.core.model.ProgramacaoImportada;
import br.com.logic.pendotiba.core.repository.ArquivoImportadoRepository;
import br.com.logic.pendotiba.core.repository.ProgramacaoImportadaRepository;
import br.com.logic.pendotiba.core.repository.ViagemImportadaRepository;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.repo.ProgramacaoImportadaRepositoryImpl;
import br.com.logic.pendotiba.logicbus.storage.local.ArquivoImportadoStorageLocal;

@Service
public class ArquivoProgramacaoImportadoService {
	
	@Autowired
	ArquivoImportadoStorageLocal arquivoImportadoStorageLocal;
	
	@Autowired
	ArquivoImportadoRepository arquivoImportadoRepository;
	
	@Autowired
	ProgramacaoImportadaRepository programacaoImportadaRepository;
	
	@Autowired
	ProgramacaoImportadaRepositoryImpl programacaoImportadaRepositoryImpl;
	
	@Autowired
	ViagemImportadaRepository viagemImportadaRepository;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Autowired
	PontoService pontoService;
	
	@Autowired
	LinhaService linhaService;
	
	
	
	
	ArquivoImportado salvar(ArquivoImportado arquivoImportado){
		arquivoImportado.setDataImportacao(new Date());
		arquivoImportado.setUsuario(usuarioService.getUsuarioLogado());
		return arquivoImportadoRepository.save(arquivoImportado);
	}
	
	
	List<String> linhasPorArquivo(ArquivoImportado arquivoImportado) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader (new FileReader(arquivoImportadoStorageLocal.recarregarArquivoImportado(arquivoImportado.getNomeArquivo())));
		List<String> linhas = new ArrayList<>();
		
		String s = br.readLine();
		while (s != null){
        	linhas.add(s);
            s = br.readLine();
		}
		br.close();
		
		return linhas;
	}
	

	public ArquivoImportado importar(ArquivoImportado arquivoImportado) {
		try {
			// buscar se arquivo eh de sabado, domingo ou dia útil
			// deletarProgramacoes(programacaoImportadaRepository.buscarPorDiaSemana(buscarVersao(arquivoImportado)));
			String v = buscarVersao(arquivoImportado);
			Linha linhaVersao = linhaService.buscarPorCodigo(buscarCodigoLinha(arquivoImportado));
			List<ProgramacaoImportada> lista = programacaoImportadaRepositoryImpl.buscarPorDiaSemanaLinha(v, linhaVersao);
			deletarProgramacoes(lista);
			
			arquivoImportado.setDataCompetencia(buscarDataCompetencia(arquivoImportado));
			arquivoImportado.setTipoArquivo(TipoArquivoEnum.PROGRAMACAO);
			arquivoImportado = salvar(arquivoImportado);
			
			
			List<String> linhas = linhasPorArquivo(arquivoImportado);
			
			String ordemProgramacao = "";
			Long contadorOrdemViagem = 1L;
			
			for (String linha : linhas) {
				//if(!linha.isEmpty() && linha.substring(86,97).trim().toUpperCase().equals("VIAGEM") && !linha.substring(20, 24).contains("*")) { 11-10-2019
				if(!linha.isEmpty() && !linha.substring(20, 24).contains("*")) {
					ProgramacaoImportada pi = new ProgramacaoImportada(linha.substring(0, 8), //versao
												 arquivoImportado.getDataCompetencia(), //data de competencia 
												 linhaService.buscarPorCodigo(linha.substring(25, 29)), //linha 
												 linha.substring(44, 50).toUpperCase(), //sentido
												 DataUtil.getTime(arquivoImportado.getDataCompetencia(), linha.substring(50, 56)), //saida	
												 DataUtil.getTime(arquivoImportado.getDataCompetencia(), linha.substring(56, 62)), //chegada
												 pontoService.buscarPorCodigo(linha.substring(62, 68)), //ponto origem
												 pontoService.buscarPorCodigo(linha.substring(69 ,76)), //ponto destino 
												 linha.substring(86,97).toUpperCase(),  //movimento
												 linha.substring(20, 23)); //ordem 
												 //linha.substring(97, 100)); //ordem 
					
					if(!pi.getOrdemProgramacao().equals(ordemProgramacao)) {
						contadorOrdemViagem = 1L;
						ordemProgramacao = pi.getOrdemProgramacao();
					} else
						contadorOrdemViagem++;
						
					pi.setOrdemViagem(contadorOrdemViagem);
					
					programacaoImportadaRepository.save(pi);
				}
			}
					
			return arquivoImportado;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Não foi possível gerar registros", e);
		}
	}


	

	// PRIVATE METHODS
	Date buscarDataCompetencia(ArquivoImportado arquivoImportado) {
		try {
			return DataUtil.getDateDDMMYY(linhasPorArquivo(arquivoImportado).get(0).substring(11, 20));
		} catch (FileNotFoundException fe) {
			throw new RuntimeException("Não foi possível encontrar arquivo", fe);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 
	}

	
	String buscarVersao(ArquivoImportado arquivoImportado) {
		try {
			return linhasPorArquivo(arquivoImportado).get(0).substring(2, 3);
		} catch (FileNotFoundException fe) {
			throw new RuntimeException("Não foi possível encontrar arquivo", fe);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	
	String buscarCodigoLinha(ArquivoImportado arquivoImportado) {
		try {
			return linhasPorArquivo(arquivoImportado).get(0).substring(0, 2);
		} catch (FileNotFoundException fe) {
			throw new RuntimeException("Não foi possível encontrar arquivo", fe);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	
	void deletarProgramacoes(List<ProgramacaoImportada> programacoesImportadas) {
		programacoesImportadas.forEach(p -> programacaoImportadaRepository.delete(p.getId()));
	}
	
}

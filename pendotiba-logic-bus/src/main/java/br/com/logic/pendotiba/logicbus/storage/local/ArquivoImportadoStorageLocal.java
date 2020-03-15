package br.com.logic.pendotiba.logicbus.storage.local;

import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import br.com.logic.pendotiba.logicbus.storage.ArquivoImportadoStorage;

@Component
public class ArquivoImportadoStorageLocal implements ArquivoImportadoStorage {
	
	private static final Logger logger = LoggerFactory.getLogger(ArquivoImportadoStorageLocal.class);

	
	public Path local;
	public Path exportacoes;
	
	
	public ArquivoImportadoStorageLocal() {
		this(getDefault().getPath(System.getenv("user.home"), "programacoes"));
	}
	
	public ArquivoImportadoStorageLocal(Path path) {
		this.local = Paths.get(System.getProperty("user.home"), "programacoes");
		this.exportacoes = Paths.get(System.getProperty("user.home"), "exportacoes");
		criarPastas();
	}
	
	
	private void criarPastas() {
		try {
			Files.createDirectories(this.local);
			Files.createDirectories(this.exportacoes);
			//this.localTemporario = Paths.get(System.getProperty("user.home"), "programacoes", "temp");
			//Files.createDirectories(this.localTemporario);
			
			if (logger.isDebugEnabled()) {
				logger.debug("Pastas criadas para salvar programações");
				logger.debug("Pasta default: " + this.local.toAbsolutePath());
				//logger.debug("Pasta temporária: " + this.localTemporario.toAbsolutePath());
			}
		} catch (IOException e) {
			throw new RuntimeException("Erro ao criar as pastas para salvar programacao", e);
		}
	}


	@Override
	public List<String> salvar(MultipartFile[] files) {
		List<String> nomeOriginalNomeNovo = null;
		if(files != null && files.length > 0) {
			MultipartFile arquivo = files[0];
			nomeOriginalNomeNovo = new ArrayList<>();
			nomeOriginalNomeNovo.add(arquivo.getOriginalFilename());
			nomeOriginalNomeNovo.add(renomearArquivo(arquivo.getOriginalFilename()));
			try {
				arquivo.transferTo(new File(this.local.toAbsolutePath().toString() + getDefault().getSeparator() + nomeOriginalNomeNovo.get(1)));
			} catch (IOException e) {
				throw new RuntimeException("Erro salvando arquivo temporario", e);
			}
		}
		
		return nomeOriginalNomeNovo;
	}
	
	
	private String renomearArquivo(String nomeOriginal) {
		String novoNome = UUID.randomUUID().toString() + "_" + nomeOriginal;
		
		if(logger.isDebugEnabled()) 
			logger.debug(String.format("Nome original : %s, novo nome: %s", nomeOriginal, novoNome));
		
		return novoNome;
	}
	
	
	public File recarregarArquivoImportado(String nome) {
		File file = new File(this.local.toAbsolutePath().toString() + getDefault().getSeparator() + nome);
		return file;
	}
	
	public File recarregarArquivoAbastecimentoTxt(String nome) {
		File file = new File(this.exportacoes.toAbsolutePath().toString() + getDefault().getSeparator() + nome);
		return file;
	}
	
	/*
	@Override
	public List<String> salvarExportacoes(File file) {
		List<String> nomeOriginalNomeNovo = null;
		if(file != null) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				throw new RuntimeException("Erro salvando arquivo temporario", e);
			}
		}
		
		return nomeOriginalNomeNovo;
	}
	*/
}

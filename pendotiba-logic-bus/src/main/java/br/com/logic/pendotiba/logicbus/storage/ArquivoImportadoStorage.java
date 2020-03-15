package br.com.logic.pendotiba.logicbus.storage;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface ArquivoImportadoStorage {
	
	public List<String> salvar(MultipartFile[] files);
	
	//public List<String> salvarExportacoes(File file);
	
	public File recarregarArquivoAbastecimentoTxt(String nome);
	
}

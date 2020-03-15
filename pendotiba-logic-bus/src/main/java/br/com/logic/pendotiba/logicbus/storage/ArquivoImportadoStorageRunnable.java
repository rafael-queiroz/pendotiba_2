package br.com.logic.pendotiba.logicbus.storage;

import java.util.List;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.com.logic.pendotiba.logicbus.dto.ArquivoImportadoDTO;

public class ArquivoImportadoStorageRunnable implements Runnable {

	private MultipartFile[] files;
	private DeferredResult<ArquivoImportadoDTO> resultado;
	private ArquivoImportadoStorage programacaoStorage;
	
	public ArquivoImportadoStorageRunnable(MultipartFile[] files, DeferredResult<ArquivoImportadoDTO> resultado, ArquivoImportadoStorage programacaoStorage) {
		this.files = files;
		this.resultado = resultado;
		this.programacaoStorage = programacaoStorage;
	}

	@Override
	public void run() {
		List<String> nomes = this.programacaoStorage.salvar(files);
		String nomeArquivoOriginal = nomes.get(0);
		String nomeArquivo = nomes.get(1);
		String contentTypeArquivo = files[0].getContentType();
		resultado.setResult(new ArquivoImportadoDTO(nomeArquivo, nomeArquivoOriginal, contentTypeArquivo));
	}

}

package br.com.logic.pendotiba.logicbus.dto;

public class ArquivoImportadoDTO {
	
	private String nomeArquivo;
	private String nomeArquivoOriginal;
	private String contentTypeArquivo;
	private String urlArquivo;
	
	

	public ArquivoImportadoDTO(String nomeArquivo, String nomeArquivoOriginal, String contentTypeArquivo) {
		this.nomeArquivo = nomeArquivo;
		this.nomeArquivoOriginal = nomeArquivoOriginal;
		this.contentTypeArquivo = contentTypeArquivo;
	}

	
	
	//GETTERS E SETTERS
	public String getNomeArquivo() {
		return nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}
	
	public String getNomeArquivoOriginal() {
		return nomeArquivoOriginal;
	}
	
	public void setNomeArquivoOriginal(String nomeArquivoOriginal) {
		this.nomeArquivoOriginal = nomeArquivoOriginal;
	}

	public String getContentTypeArquivo() {
		return contentTypeArquivo;
	}

	public void setContentTypeArquivo(String contentTypeArquivo) {
		this.contentTypeArquivo = contentTypeArquivo;
	}
	
	public String getUrlArquivo() {
		return urlArquivo;
	}
	
	public void setUrlArquivo(String urlArquivo) {
		this.urlArquivo = urlArquivo;
	}
	
}

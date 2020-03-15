package br.com.logic.pendotiba.logicbus.controller.page;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper <T> {

	private Page<T> page;
	private UriComponentsBuilder uriBuilder;

	
	public PageWrapper(Page<T> page, HttpServletRequest httpServletRequest) {
		this.page = page;
		//this.uriBuilder = ServletUriComponentsBuilder.fromRequest(httpServletRequest);
		//para tratar consulta com campos contendo espaços
		String httpUrl = httpServletRequest.getRequestURL().append(
				httpServletRequest.getQueryString() != null ? "?" + httpServletRequest.getQueryString() : "")
				.toString().replaceAll("\\+", "%20");
		this.uriBuilder = UriComponentsBuilder.fromHttpUrl(httpUrl);
	}
	
	
	public List<T> getConteudo() {
		return page.getContent();
	}
	
	
	public boolean isVazia() {
		return page.getContent().isEmpty();
	}
	
	
	public int getAtual() {
		return page.getNumber();
	}
	
	
	public boolean isPrimeira() {
		return page.isFirst();
	}
	
	
	public boolean isUltima() {
		return page.isLast();
	}
	
	
	public int getTotal() {
		return page.getTotalPages();
	}
	
	
	public int getPaginaLimite(){
		if(getTotal() < 6)
			return  getTotal();
		
		if (getAtual() == 0 ) {
			if(getAtual()+5 < getTotal()) return getAtual()+5;
			if(getAtual()+4 < getTotal()) return getAtual()+4;
			if(getAtual()+2 < getTotal()) return getAtual()+2;
			if(getAtual()+1 < getTotal()) return getAtual()+1;
			return getAtual();
		} else if (getAtual() == 1 ) {
			if(getAtual()+4 < getTotal()) return getAtual()+4;
			if(getAtual()+2 < getTotal()) return getAtual()+2;
			if(getAtual()+1 < getTotal()) return getAtual()+1;
			return getAtual();
		} else if (getAtual()+3 > getTotal())
			return getTotal();
		
		return  getAtual()+3;
	}
	
	public int getInicial(){
		if(getTotal() < 6)
			return  1;
		
		if (getAtual() == 0 || getAtual() == 1)
			return 1;
		else if (getTotal()-getAtual() == 1)
			return getAtual()-3 ;
		else if (getTotal()-getAtual() == 2)
			return getAtual()-2 ;
		
		return getAtual()-1;
	}
	
	
	public String urlParaPagina(int pagina) {
		return uriBuilder.replaceQueryParam("page", pagina).build(true).encode().toUriString();
	}
	
	
	public String urlOrdenada(String propriedade) {
		UriComponentsBuilder uriBuilderOrder = UriComponentsBuilder.fromUriString(uriBuilder.build(true).encode().toUriString());
		
		String valorSort = String.format("%s,%s", propriedade, inverterDirecao(propriedade));
		
		return uriBuilderOrder.replaceQueryParam("sort", valorSort).build(true).encode().toUriString();
	}
	
	
	public String inverterDirecao(String propriedade) {
		String direcao = "asc";
		
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null;
		if (order != null) 
			direcao = Sort.Direction.ASC.equals(order.getDirection()) ? "desc" : "asc";
		
		return direcao;
	}
	
	
	
	public boolean descendente(String propriedade) {
		return inverterDirecao(propriedade).equals("asc");
	}
	
	
	public boolean ordenada(String propriedade) {
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null; 
		
		if (order == null) 
			return false;
		
		return page.getSort().getOrderFor(propriedade) != null ? true : false;
	}

}

Logic = Logic || {};

Logic.PesquisaRapidaCobrador = (function() {
	
	function PesquisaRapidaCobrador() {
		this.pesquisaRapidaCobradorModal = $('#pesquisaRapidaCobrador');
		this.nomeInput = $('#nomeCobradorModal');
		this.lupaBtn = $('.js-lupa-btn');
		this.pesquisaRapidaBtn = $('.js-pesquisa-rapida-cobrador-btn'); 
		this.containerTabelaPesquisa = $('#containerTabelaPesquisaRapidaCobrador');
		this.htmlTabelaPesquisa = $('#tabela-pesquisa-rapida-cobrador').html();
		this.template = Handlebars.compile(this.htmlTabelaPesquisa);
		this.mensagemErro = $('.js-mensagem-erro');
	}
	
	PesquisaRapidaCobrador.prototype.iniciar = function() {
		this.pesquisaRapidaBtn.on('click', onPesquisaRapidaClicado.bind(this));
		this.pesquisaRapidaCobradorModal.on('shown.bs.modal', onModalShow.bind(this));

	}
	
	function onModalShow() {
		console.log('funcao ' + this.pesquisaRapidaBtn.data('funcao'));
		this.nomeInput.focus();
	}
	
	function onPesquisaRapidaClicado(event) {
		event.preventDefault();
		$.ajax({
			url: this.pesquisaRapidaCobradorModal.find('form').attr('action'),
			method: 'GET',
			contentType: 'application/json',
			data: {
				nome: this.nomeInput.val()
			}, 
			success: onPesquisaConcluida.bind(this),
			error: onErroPesquisa.bind(this)
		});
	}
	
	function onPesquisaConcluida(resultado) {
		this.mensagemErro.addClass('hidden');
		
		var html = this.template(resultado);
		this.containerTabelaPesquisa.html(html);
		
		var tabelaCobradorPesquisaRapida = new Logic.TabelaCobradorPesquisaRapida(this.pesquisaRapidaCobradorModal);
		tabelaCobradorPesquisaRapida.iniciar();
	} 
	
	function onErroPesquisa() {
		this.mensagemErro.removeClass('hidden');
	}
	
	return PesquisaRapidaCobrador;
	
}());

Logic.TabelaCobradorPesquisaRapida = (function() {
	
	function TabelaCobradorPesquisaRapida(modal) {
		this.modalCobrador = modal;
		this.cobrador = $('.js-cobrador-pesquisa-rapida');
	}
	
	TabelaCobradorPesquisaRapida.prototype.iniciar = function() {
		this.cobrador.on('click', onCobradorSelecionado.bind(this));
	}
	
	function onCobradorSelecionado(evento) {
		this.modalCobrador.modal('hide');
		
		var cobradorSelecionado = $(evento.currentTarget);
		$('#nomeCobrador').val(cobradorSelecionado.data('nome'));
		$('#idCobrador').val(cobradorSelecionado.data('id'));
	}
	
	return TabelaCobradorPesquisaRapida;
	
}());

$(function() {
	var pesquisaRapidaCobrador = new Logic.PesquisaRapidaCobrador();
	pesquisaRapidaCobrador.iniciar();
});
Logic = Logic || {};

Logic.PesquisaRapidaMotorista = (function() {
	
	function PesquisaRapidaMotorista() {
		this.pesquisaRapidaMotoristaModal = $('#pesquisaRapidaMotorista');
		this.nomeInput = $('#nomeMotoristaModal');
		this.pesquisaRapidaBtn = $('.js-pesquisa-rapida-motorista-btn'); 
		this.containerTabelaPesquisa = $('#containerTabelaPesquisaRapidaMotorista');
		this.htmlTabelaPesquisa = $('#tabela-pesquisa-rapida-motorista').html();
		this.template = Handlebars.compile(this.htmlTabelaPesquisa);
		this.mensagemErro = $('.js-mensagem-erro');
	}
	
	PesquisaRapidaMotorista.prototype.iniciar = function() {
		this.pesquisaRapidaBtn.on('click', onPesquisaRapidaClicado.bind(this));
		this.pesquisaRapidaMotoristaModal.on('shown.bs.modal', onModalShow.bind(this));

	}
	
	function onModalShow() {
		this.nomeInput.focus();
	}
	
	function onPesquisaRapidaClicado(event) {
		event.preventDefault();
		$.ajax({
			url: this.pesquisaRapidaMotoristaModal.find('form').attr('action'),
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
		
		var tabelaMotoristaPesquisaRapida = new Logic.TabelaMotoristaPesquisaRapida(this.pesquisaRapidaMotoristaModal);
		tabelaMotoristaPesquisaRapida.iniciar();
	} 
	
	function onErroPesquisa() {
		this.mensagemErro.removeClass('hidden');
	}
	
	return PesquisaRapidaMotorista;
	
}());

Logic.TabelaMotoristaPesquisaRapida = (function() {
	
	function TabelaMotoristaPesquisaRapida(modal) {
		this.modalMotorista = modal;
		this.motorista = $('.js-motorista-pesquisa-rapida');
	}
	
	TabelaMotoristaPesquisaRapida.prototype.iniciar = function() {
		this.motorista.on('click', onMotoristaSelecionado.bind(this));
	}
	
	function onMotoristaSelecionado(evento) {
		this.modalMotorista.modal('hide');
		
		var motoristaSelecionado = $(evento.currentTarget);
		$('#nomeMotorista').val(motoristaSelecionado.data('nome'));
		$('#idMotorista').val(motoristaSelecionado.data('id'));
	}
	
	return TabelaMotoristaPesquisaRapida;
	
}());

$(function() {
	var pesquisaRapidaMotorista = new Logic.PesquisaRapidaMotorista();
	pesquisaRapidaMotorista.iniciar();
});
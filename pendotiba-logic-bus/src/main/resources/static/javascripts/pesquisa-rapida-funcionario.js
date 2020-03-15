Logic = Logic || {};

Logic.PesquisaRapidaFuncionario = (function() {
	
	function PesquisaRapidaFuncionario() {
		this.pesquisaRapidaFuncionarioModal = $('#pesquisaRapidaFuncionario');
		this.nomeInput = $('#nomeFuncionarioModal');
		this.pesquisaRapidaBtn = $('.js-pesquisa-rapida-funcionario-btn'); 
		this.containerTabelaPesquisa = $('#containerTabelaPesquisaRapidaFuncionario');
		this.htmlTabelaPesquisa = $('#tabela-pesquisa-rapida-funcionario').html();
		this.template = Handlebars.compile(this.htmlTabelaPesquisa);
		this.mensagemErro = $('.js-mensagem-erro');
	}
	
	PesquisaRapidaFuncionario.prototype.iniciar = function() {
		this.pesquisaRapidaBtn.on('click', onPesquisaRapidaClicado.bind(this));
		this.pesquisaRapidaFuncionarioModal.on('shown.bs.modal', onModalShow.bind(this));

	}
	
	function onModalShow() {
		this.nomeInput.focus();
	}
	
	function onPesquisaRapidaClicado(event) {
		event.preventDefault();
		$.ajax({
			url: this.pesquisaRapidaFuncionarioModal.find('form').attr('action'),
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
		
		var tabelaFuncionarioPesquisaRapida = new Logic.TabelaFuncionarioPesquisaRapida(this.pesquisaRapidaFuncionarioModal);
		tabelaFuncionarioPesquisaRapida.iniciar();
	} 
	
	function onErroPesquisa() {
		this.mensagemErro.removeClass('hidden');
	}
	
	return PesquisaRapidaFuncionario;
	
}());

Logic.TabelaFuncionarioPesquisaRapida = (function() {
	
	function TabelaFuncionarioPesquisaRapida(modal) {
		this.modalFuncionario = modal;
		this.funcionario = $('.js-funcionario-pesquisa-rapida');
	}
	
	TabelaFuncionarioPesquisaRapida.prototype.iniciar = function() {
		this.funcionario.on('click', onFuncionarioSelecionado.bind(this));
	}
	
	function onFuncionarioSelecionado(evento) {
		this.modalFuncionario.modal('hide');
		
		var funcionarioSelecionado = $(evento.currentTarget);
		$('#nomeFuncionario').val(funcionarioSelecionado.data('nome'));
		$('#idFuncionario').val(funcionarioSelecionado.data('id'));
	}
	
	return TabelaFuncionarioPesquisaRapida;
	
}());

$(function() {
	var pesquisaRapidaFuncionario = new Logic.PesquisaRapidaFuncionario();
	pesquisaRapidaFuncionario.iniciar();
});
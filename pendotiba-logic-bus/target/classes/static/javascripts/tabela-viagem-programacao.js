var Logic = Logic || {};

Logic.TabelaViagensProgramacao = (function() {
	
	function TabelaViagensProgramacao() {
		this.modal = $('#cadastroViagemModal');
		this.formModal = this.modal.find('form');
		this.url = this.formModal.attr('action');
		this.botaoSalvar = this.modal.find('.js-modal-cadastro-viagem-salvar-btn');
		
		this.tabelaViagensContainer = $('.js-tabela-viagens-container');
		this.uuid = $('#uuid').val();
		
		this.horaSaidaProgramada = $('#horaSaidaProgramada');
		this.linhaProgramada = $('#linhaProgramada');
		this.pontoOrigem = $('#pontoOrigem');
		this.pontoDestino = $('#pontoDestino');
		
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
		
		this.mensagemErro = $('.js-mensagem-erro');
	}

	
	TabelaViagensProgramacao.prototype.iniciar = function() {
		this.botaoSalvar.on('click', onBotaoSalvarClick.bind(this));
		bindTabelaViagem.call(this);
	}
	

	function onBotaoSalvarClick() {
		var resposta = $.ajax({
			url: '/pendotiba-logic-bus/programacao/viagem',
			method: 'POST',
			data: {
				uuid: this.uuid,
				horaSaidaProgramada: this.horaSaidaProgramada.val(),
				linhaProgramada: this.linhaProgramada.val(),
				pontoOrigem: this.pontoOrigem.val(),
				pontoDestino: this.pontoDestino.val()
			}
		});
		
		resposta.done(onViagemAtualizadoNoServidor.bind(this)).fail(onError.bind(this));
	}
	
	function onViagemAtualizadoNoServidor(html) {
		this.mensagemErro.addClass('hidden');
		this.tabelaViagensContainer.html(html);
		var tabelaViagem = bindTabelaViagem.call(this); 
		//this.emitter.trigger('tabela-viagens-atualizada', tabelaViagem.data('valor-total-entrada'));
	}
	
	
	function onExclusaoItemClick(evento) {
		event.preventDefault();
		var botaoClicado = $(evento.currentTarget);
		var idViagem = botaoClicado.data('id-viagem');
		var hora = botaoClicado.data('hora');
		
		swal({
			title: 'Confirma exclusão ?',
			text: 'Excluir "' + hora + '"?',
			showCancelButton: true,
			confirmButtonColor: '#663300',
			confirmButtonText: 'Confirma',
			closeOnConfirm: false
		}, onExcluirConfirmado.bind(this, idViagem));
	}
	
	
	function onExcluirConfirmado(idViagem) {
		$.ajax({
			url: 'viagem/' + this.uuid + '/' + idViagem,
			method: 'DELETE',
			success: onViagemAtualizadoNoServidor.bind(this)
		});
		
		swal('Pronto!', 'Excluído com sucesso!', 'success');
	}
	
	
	function bindTabelaViagem() {
		var tabelaViagem = $('.js-tabela-viagem');
		$('.js-exclusao-item-btn').on('click', onExclusaoItemClick.bind(this));
		
		this.horaSaidaProgramada.val('');
		this.linhaProgramada.val('');
		this.pontoOrigem.val('');
		this.pontoDestino.val('');
		
		this.modal.modal('hide');
		return tabelaViagem;
	}
	
	function onError() {
		this.mensagemErro.removeClass('hidden');
	}
	
	return TabelaViagensProgramacao;
	
}());

$(function() {
	
	var tabelaViagensProgramacao = new Logic.TabelaViagensProgramacao();
	tabelaViagensProgramacao.iniciar();
	
});


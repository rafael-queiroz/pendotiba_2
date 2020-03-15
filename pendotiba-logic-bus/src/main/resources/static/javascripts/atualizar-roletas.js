Logic.AtualizarAbastecimento = (function() {
	
	function AtualizarRoletas() {
		this.atualizarRoletasModal = $('#atualizarRoletasModal');
		this.formAtualizarRoletasModal = this.atualizarRoletasModal.find('form');
		this.urlDesconto = this.atualizarRoletasModal.attr('action');
		this.botaoAtualizarRoletasModal = this.formAtualizarRoletasModal.find('.js-atualizar-roletas-btn');
		
		this.idCarro = '';
		this.numeroCarro = '';
		this.roletaFinal1 = $('#roletaFinal1');
		
		this.mensagemErro = $('.js-mensagem-erro');
	}
	
	
	AtualizarRoletas.prototype.iniciar = function() {
		$('.js-roletas-btn').on('click', onAtualizarRoletasClick.bind(this));
		this.botaoAtualizarRoletasModal.on('click', onBotaoOkClicado.bind(this));
		//this.atualizarRoletasModal.on('shown.bs.modal', onModalShow.bind(this));
		
		//bindTabelaCarro.call(this);
	}
	
	/*
	function onModalShow() {
		this.roletaFinal1.focus();
	}
	*/
	
	function onAtualizarRoletasClick(evento) {
		this.mensagemErro.addClass('hidden');
		event.preventDefault();
		var botaoClicado = $(evento.currentTarget);
		this.idCarro = botaoClicado.data('id-carro');
		this.numeroCarro = botaoClicado.data('numero-carro');
	
		this.roletaFinal1.val(botaoClicado.data('roleta1'));
	}
	
	function onBotaoOkClicado() {
		event.preventDefault();
		console.log("roletas " + this.roletaFinal1.val());
		console.log("carro " + this.idCarro);
		var resposta = $.ajax({
			url: '/pendotiba-logic-bus/carro/roletas-atualizar',
			method: 'POST',
			data: {
				idCarro: this.idCarro,
				roletaFinal1: this.roletaFinal1.val()
			}
		});
		
		/*
		resposta.done(function(){ 
			location.reload();
		},5000).fail(onError.bind(this));
		this.atualizarRoletasModal.modal('hide');
		*/
		resposta.done(osSuccess.bind(this), 5000).fail(onError.bind(this));
	}
	
	function osSuccess() {
		location.reload();
		this.atualizarRoletasModal.modal('hide');
	}
	
	function onError() {
		this.mensagemErro.removeClass('hidden');
	}
	
	
	return AtualizarRoletas;
}());

$(function() {
	
	var atualizarRoletas = new Logic.AtualizarRoletas();
	atualizarRoletas.iniciar();
	
});



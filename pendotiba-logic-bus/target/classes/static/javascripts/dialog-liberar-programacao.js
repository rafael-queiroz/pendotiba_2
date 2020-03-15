Logic = Logic || {};

Logic.DialogLiberarProgramacao = (function() {
	
	function DialogLiberarProgramacao() {
		this.exclusaoBtn = $('.js-liberar-btn')
	}
	
	DialogLiberarProgramacao.prototype.iniciar = function() {
		this.exclusaoBtn.on('click', onLiberarClicado.bind(this));
		
		if (window.location.search.indexOf('excluido') > -1) {
			swal('Pronto!', 'Liberado com sucesso!', 'success');
		}
	}
	
	function onLiberarClicado(evento) {
		event.preventDefault();
		var botaoClicado = $(evento.currentTarget);
		var url = botaoClicado.data('url');
		var carro = botaoClicado.data('carro');
		var motorista = botaoClicado.data('motorista');
		
		swal({
			title: 'Tem certeza?',
			text: 'Liberar p carro ' + carro + ' para o motorista ' + motorista + ' ?',
			showCancelButton: true,
			confirmButtonColor: '#DD6B55',
			confirmButtonText: 'Sim, libere agora!',
			closeOnConfirm: false
		}, onLiberarConfirmado.bind(this, url));
	}
	
	function onLiberarConfirmado(url) {
		$.ajax({
			url: url,
			method: 'POST',
			success: onLiberadoSucesso.bind(this),
			error: onErroLiberar.bind(this)
		});
	}
	
	function onLiberadoSucesso() {
		var urlAtual = window.location.href;
		var separador = urlAtual.indexOf('?') > -1 ? '&' : '?';
		var novaUrl = urlAtual.indexOf('liberado') > -1 ? urlAtual : urlAtual + separador + 'liberado';
		
		window.location = novaUrl;
	}
	
	function onErroLiberar(e) {
		console.log('ahahahah', e.responseText);
		swal('Erro!', e.responseText, 'error');
	}
	
	return DialogLiberarProgramacao;
	
}());


$(function() {
	var dialog = new Logic.DialogLiberarProgramacao();
	dialog.iniciar();
});

Logic = Logic || {};

Logic.RemoverCobrador = (function() {
	
	function RemoverCobrador() {
		this.exclusaoBtn = $('.js-remover-cobrador-btn')
	}
	
	RemoverCobrador.prototype.iniciar = function() {
		this.exclusaoBtn.on('click', onExcluirClicado.bind(this));
	}
	
	function onExcluirClicado(evento) {
		event.preventDefault();
		
		swal({
			title: 'Tem certeza?',
			text: 'Remover cobrador da programação ?',
			showCancelButton: true,
			confirmButtonColor: '#DD6B55',
			confirmButtonText: 'Sim, remova agora!',
			closeOnConfirm: false
		}, onExcluirConfirmado.bind(this));
	}
	
	function onExcluirConfirmado(url) {
		$('#nomeCobrador').val('');
		$('#idCobrador').val('');
		swal('Pronto!', 'Cobrador removido com sucesso!', 'success');
	}
	
	return RemoverCobrador;
	
}());


$(function() {
	var dialogo = new Logic.RemoverCobrador();
	dialogo.iniciar();
});

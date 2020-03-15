var Logic = Logic || {};

Logic.UploadArquivo = (function() {
	
	function UploadArquivo() {
		this.modal = $('#uploadArquivo');
		this.formModal = this.modal.find('form');
		this.url = this.formModal.attr('action');
		this.object = this.formModal.attr('object');
		this.uploadBtn = $('.js-upload-btn');
		this.modalProcessando = $('#modalProcessando');
	}
	
	UploadArquivo.prototype.iniciar = function () {
		this.uploadBtn.on('click', onUploadClicado.bind(this));
	}
	
	function onUploadClicado(evento) {
		event.preventDefault();
		
		swal({
			title: 'Tem certeza?',
			text: 'Existem programações para o dia, deseja sobrescrevê-las ?',
			showCancelButton: true,
			confirmButtonColor: '#DD6B55',
			confirmButtonText: 'Sim sobescreva agora!',
			closeOnConfirm: false
		}, onUploadConfirmado.bind(this, this.url));
	}
	
	function onUploadConfirmado(url) {
		console.log('teste');
		console.log(this.object);
		console.log(this.url);
		console.log(this.modalProcessando);
		this.modalProcessando.show();
		$.ajax({
			url: url,
			method: 'POST',
			success: onExcluidoSucesso.bind(this),
			error: onErroExcluir.bind(this)
		});
	}
	
	function onExcluidoSucesso() {
		this.modalProcessando.hide();

		/*var urlAtual = window.location.href;
		var separador = urlAtual.indexOf('?') > -1 ? '&' : '?';
		var novaUrl = urlAtual.indexOf('excluido') > -1 ? urlAtual : urlAtual + separador + 'excluido';
		
		window.location = novaUrl;*/
	}
	
	function onErroExcluir(e) {
		console.log('ahahahah', e.responseText);
		swal('Erro!', e.responseText, 'error');
	}

	return UploadArquivo;
	
})();

$(function() {
	var uploadArquivo = new Logic.UploadArquivo();
	uploadArquivo.iniciar();
});
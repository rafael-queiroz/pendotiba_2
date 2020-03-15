var Logic = Logic || {};

Logic.ComboLinha = (function() {
	
	function ComboLinha() {
		this.linha = $('#linhaProgramada');
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	ComboLinha.prototype.iniciar = function() {
		this.linha.on('change', onLinhaAlterada.bind(this));
	}
	
	function onLinhaAlterada() {
		this.emitter.trigger('alterada', this.linha.val());
	}
	
	return ComboLinha;
}());


Logic.ComboPontoLinha = (function() {
	
	function ComboPontoLinha(comboLinha) {
		this.comboLinha = comboLinha;
		this.comboPontoLinha = $('#comboPontoLinha');
		this.imgLoading = $('.js-img-loading');
		this.idPontoLinhaSelecionado = $('#idPontoLinhaSelecionado');
		this.pontoLinhaSelecionado = $('#pontoLinhaSelecionado');
	}
	
	ComboPontoLinha.prototype.iniciar = function() {
		reset.call(this);
		this.comboLinha.on('alterada', onLinhaAlterada.bind(this));
		inicializarPontosLinha.call(this, this.comboLinha.linha.val());
	}
	
	function onLinhaAlterada(evento, idLinha) {
		this.idPontoLinhaSelecionado.val('');
		inicializarPontosLinha.call(this, idLinha);
	}
	
	function inicializarPontosLinha(idLinha) {
		console.log(idLinha);
		if (idLinha) {
			var resposta = $.ajax({
				url: this.comboPontoLinha.data('url'),
				method: 'GET',
				contentType: 'application/json',
				data: { 'idLinha': idLinha }, 
				beforeSend: iniciarRequisicao.bind(this),
				complete: finalizarRequisicao.bind(this)
			});
			resposta.done(onBuscarPontosLinhaFinalizado.bind(this));
		} else {
			reset.call(this);
		}
	}
	
	function onBuscarPontosLinhaFinalizado(pontosLinha) {
		var options = [];
		options.push('<option value="">Selecione</option>');

		pontosLinha.forEach(function(pontoLinha) {
			options.push('<option value="' + pontoLinha.id + '">' + pontoLinha.descricao + '</option>');
		});
		
		this.comboPontoLinha.html(options.join(''));
		this.comboPontoLinha.removeAttr('disabled');
		
		var pontoLinhaSelecionado = this.idPontoLinhaSelecionado.val();
		if (pontoLinhaSelecionado) {
			this.comboPontoLinha.val(pontoLinhaSelecionado);
		}
	}
	
	function reset() {
		this.comboPontoLinha.html('<option value="">Selecione</option>');
		this.comboPontoLinha.val('');
		this.comboPontoLinha.attr('disabled', 'disabled');
	}
	
	function iniciarRequisicao() {
		reset.call(this);
		this.imgLoading.show();
	}
	
	function finalizarRequisicao() {
		this.imgLoading.hide();
	}
	
	return ComboPontoLinha;
	
}());


$(function() {
	var comboLinha = new Logic.ComboLinha();
	comboLinha.iniciar();
	
	var comboPontoLinha = new Logic.ComboPontoLinha(comboLinha);
	comboPontoLinha.iniciar();
	
});


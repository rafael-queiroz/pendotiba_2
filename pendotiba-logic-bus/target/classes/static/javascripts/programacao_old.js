var Logic = Logic || {};

Logic.AtualizarCombos = (function() {
	
	function AtualizarCombos() {
		this.dataCompetencia = $('#dataCompetencia');
		this.emitter = $({});
		this.on = this.emitter.on.bind(this.emitter);
	}
	
	AtualizarCombos.prototype.iniciar = function() {
		this.dataCompetencia.on('change', onDataCompetenciaAlterada.bind(this));
	}
	
	function onDataCompetenciaAlterada() {
		this.emitter.trigger('alterada', this.dataCompetencia.val());
	}
	
	return AtualizarCombos;
}());


Logic.ComboCarro = (function() {
	
	function ComboCarro(dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
		this.programacaoAtiva = $('#status');
		this.comboCarro = $('#comboCarro');
		this.imgLoading = $('.js-img-loading');
		this.idCarroSelecionado = $('#idCarroSelecionado');
		this.carroSelecionado = $('#carroSelecionado');
	}
	
	ComboCarro.prototype.iniciar = function() {
		reset.call(this);
		this.dataCompetencia.on('alterada', onDataCompetenciaAlterada.bind(this));
		inicializarCarros.call(this, dataCompetencia.value, this.idProgramacao);
	}
	
	function onDataCompetenciaAlterada(evento, dataCompetencia, idProgramacao) {
		this.idCarroSelecionado.val('');
		inicializarCarros.call(this, dataCompetencia, null);
	}
	
	function inicializarCarros(dataCompetencia, idProgramacao) {
		if (dataCompetencia) {
			//16/01/2018 -> 2018/01/16
			data = dataCompetencia.substring(6,10) + '/' + dataCompetencia.substring(3,5) + '/' + dataCompetencia.substring(0,2);
			var resposta = $.ajax({
				url: this.comboCarro.data('url'),
				//url: 'http://localhost:8080/logic-bus/rest/carro/listarCarrosDisponiveisPorDataCompetencia', 
				method: 'GET',
				contentType: 'application/json',
				data: { 'dataCompetencia': data }, 
				beforeSend: iniciarRequisicao.bind(this),
				complete: finalizarRequisicao.bind(this)
			});
			resposta.done(onBuscarCarrosFinalizado.bind(this));
		} else {
			reset.call(this);
		}
	}
	
	function onBuscarCarrosFinalizado(carros, idProgramacao) {
		console.log(carros);
		var options = [];
		options.push('<option value="">Selecione o carro</option>');
		if (this.idCarroSelecionado.val())
			options.push('<option value="' + this.idCarroSelecionado.val() + '">' + this.carroSelecionado.val() + '</option>');
		
		carros.forEach(function(carro) {
			options.push('<option value="' + carro.id + '">' + carro.numeroDeOrdem + '</option>');
		});
		
		this.comboCarro.html(options.join(''));
		this.comboCarro.removeAttr('disabled');
		
		var codigoCarroSelecionado = this.idCarroSelecionado.val();
		if (codigoCarroSelecionado) {
			this.comboCarro.val(codigoCarroSelecionado);
		}
		
		if(this.programacaoAtiva.val() == 'true')
			this.comboCarro.attr('disabled', 'disabled');
	}
	
	function reset() {
		this.comboCarro.html('<option value="">Selecione o carro</option>');
		this.comboCarro.val('');
		this.comboCarro.attr('disabled', 'disabled');
	}
	
	function iniciarRequisicao() {
		reset.call(this);
		this.imgLoading.show();
	}
	
	function finalizarRequisicao() {
		this.imgLoading.hide();
	}
	
	return ComboCarro;
	
}());


Logic.ComboMotorista = (function() {
	
	function ComboMotorista(dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
		this.programacaoAtiva = $('#status');
		this.comboMotorista = $('#comboMotorista');
		this.imgLoading = $('.js-img-loading');
		this.idMotoristaSelecionado = $('#idMotoristaSelecionado');
		this.motoristaSelecionado = $('#motoristaSelecionado');
	}
	
	ComboMotorista.prototype.iniciar = function() {
		reset.call(this);
		this.dataCompetencia.on('alterada', onDataCompetenciaAlterada.bind(this));
		inicializarMotoristas.call(this, dataCompetencia.value);
	}
	
	function onDataCompetenciaAlterada(evento, dataCompetencia) {
		this.idMotoristaSelecionado.val('');
		inicializarMotoristas.call(this, dataCompetencia);
	}
	
	function inicializarMotoristas(dataCompetencia) {
		if (dataCompetencia) {
			//16/01/2018 -> 2018/01/16
			data = dataCompetencia.substring(6,10) + '/' + dataCompetencia.substring(3,5) + '/' + dataCompetencia.substring(0,2);
			var resposta = $.ajax({
				url: this.comboMotorista.data('url'),
				//url: 'http://localhost:8080/logic-bus/rest/funcionario/listarMotoristasDisponiveisPorDataCompetencia',
				method: 'GET',
				contentType: 'application/json',
				data: { 'dataCompetencia': data }, 
				beforeSend: iniciarRequisicao.bind(this),
				complete: finalizarRequisicao.bind(this)
			});
			resposta.done(onBuscarMotoristasFinalizado.bind(this));
		} else {
			reset.call(this);
		}
	}
	
	function onBuscarMotoristasFinalizado(motoristas) {
		var options = [];
		options.push('<option value="">Selecione o motorista</option>');
		if (this.idMotoristaSelecionado.val())
			options.push('<option value="' + this.idMotoristaSelecionado.val() + '">' + this.motoristaSelecionado.val() + '</option>');
		
		motoristas.forEach(function(motorista) {
			options.push('<option value="' + motorista.id + '">' + motorista.matricula + ' - ' + motorista.nome + '</option>');
		});
		
		this.comboMotorista.html(options.join(''));
		this.comboMotorista.removeAttr('disabled');
		
		var codigoMotoristaSelecionado = this.idMotoristaSelecionado.val();
		if (codigoMotoristaSelecionado) {
			this.comboMotorista.val(codigoMotoristaSelecionado);
		}
		
		if(this.programacaoAtiva.val() == 'true')
			this.comboMotorista.attr('disabled', 'disabled');
	}
	
	function reset() {
		this.comboMotorista.html('<option value="">Selecione o motorista</option>');
		this.comboMotorista.val('');
		this.comboMotorista.attr('disabled', 'disabled');
	}
	
	function iniciarRequisicao() {
		reset.call(this);
		this.imgLoading.show();
	}
	
	function finalizarRequisicao() {
		this.imgLoading.hide();
	}
	
	return ComboMotorista;
	
}());


Logic.ComboParceiro = (function() {
	
	function ComboParceiro(dataCompetencia) {
		this.dataCompetencia = dataCompetencia;
		this.programacaoAtiva = $('#status');
		this.comboParceiro = $('#comboParceiro');
		this.imgLoading = $('.js-img-loading');
		this.idParceiroSelecionado = $('#idParceiroSelecionado');
		this.parceiroSelecionado = $('#parceiroSelecionado');
	}
	
	ComboParceiro.prototype.iniciar = function() {
		reset.call(this);
		this.dataCompetencia.on('alterada', onDataCompetenciaAlterada.bind(this));
		inicializarParceiros.call(this, dataCompetencia.value);
	}
	
	function onDataCompetenciaAlterada(evento, dataCompetencia) {
		this.idParceiroSelecionado.val('');
		inicializarParceiros.call(this, dataCompetencia);
	}
	
	function inicializarParceiros(dataCompetencia) {
		if (dataCompetencia) {
			//16/01/2018 -> 2018/01/16
			data = dataCompetencia.substring(6,10) + '/' + dataCompetencia.substring(3,5) + '/' + dataCompetencia.substring(0,2);
			var resposta = $.ajax({
				url: this.comboParceiro.data('url'),
				//url: 'http://localhost:8080/logic-bus/rest/funcionario/listarMotoristasDisponiveisPorDataCompetencia',
				method: 'GET',
				contentType: 'application/json',
				data: { 'dataCompetencia': data }, 
				beforeSend: iniciarRequisicao.bind(this),
				complete: finalizarRequisicao.bind(this)
			});
			resposta.done(onBuscarParceirosFinalizado.bind(this));
		} else {
			reset.call(this);
		}
	}
	
	function onBuscarParceirosFinalizado(motoristas) {
		var options = [];
		options.push('<option value="">Selecione o parceiro</option>');
		if (this.idParceiroSelecionado.val())
			options.push('<option value="' + this.idParceiroSelecionado.val() + '">' + this.parceiroSelecionado.val() + '</option>');
		
		motoristas.forEach(function(parceiro) {
			options.push('<option value="' + parceiro.id + '">' + parceiro.matricula + ' - ' + parceiro.nome + '</option>');
		});
		
		this.comboParceiro.html(options.join(''));
		this.comboParceiro.removeAttr('disabled');
		
		var codigoParceiroSelecionado = this.idParceiroSelecionado.val();
		if (codigoParceiroSelecionado) {
			this.comboParceiro.val(codigoParceiroSelecionado);
		}
		
		if(this.programacaoAtiva.val() == 'true')
			this.comboParceiro.attr('disabled', 'disabled');
	}
	
	function reset() {
		this.comboParceiro.html('<option value="">Selecione o parceiro</option>');
		this.comboParceiro.val('');
		this.comboParceiro.attr('disabled', 'disabled');
	}
	
	function iniciarRequisicao() {
		reset.call(this);
		this.imgLoading.show();
	}
	
	function finalizarRequisicao() {
		this.imgLoading.hide();
	}
	
	return ComboParceiro;
	
}());

$(function() {
	var atualizarCombos = new Logic.AtualizarCombos();
	atualizarCombos.iniciar();
	
	var comboCarro = new Logic.ComboCarro(atualizarCombos);
	comboCarro.iniciar();
	
	var comboMotorista = new Logic.ComboMotorista(atualizarCombos);
	comboMotorista.iniciar();
	
	var comboParceiro = new Logic.ComboParceiro(atualizarCombos);
	comboParceiro.iniciar();
});


var Logic = Logic || {};

Logic.MaskMoney = (function() {
	
	function MaskMoney() {
		this.decimal = $('.js-decimal');
		this.plain = $('.js-plain');
	}
	
	MaskMoney.prototype.enable = function() {
		this.decimal.maskMoney({ decimal: ',', thousands: '.' });
		this.plain.maskMoney({ precision: 0, thousands: '.' });
	}
	
	return MaskMoney;
}());



Logic.MaskDate = (function() {
	
	function MaskDate() {
		this.inputDate = $('.js-date');
	}
	
	MaskDate.prototype.enable = function() {
		this.inputDate.mask('00/00/0000');
		this.inputDate.datepicker({
			orientation: 'bottom',
			language: 'pt-BR',
			autoclose: true
		});
	}
	
	return MaskDate;
	
}());

Logic.MaskDateImportacaoEscala = (function() {
	
	function MaskDate() {
		this.inputDate = $('.js-date-importacao-escala');
	}
	
	MaskDate.prototype.enable = function() {
		//this.inputDate.mask('00/00/0000');
		this.inputDate.datepicker({
			orientation: 'bottom',
			language: 'pt-BR',
			autoclose: true,
			startDate: '+1d',
			endDate: '+7d'
		});
	}
	
	return MaskDate;
	
}());



Logic.MaskMultiDate = (function() {
	
	function MaskMultiDate() {
		this.inputDate = $('.js-multi-date');
	}
	
	MaskMultiDate.prototype.enable = function() {
		//this.inputDate.mask('00/00/0000');
		this.inputDate.datepicker({
			orientation: 'bottom',
			language: 'pt-BR',
			locale: 'pt-br',
			multidate: true,
			format: "dd/mm/yyyy"
		}).on('changeDate', function(e) {
	        // `e` here contains the extra attributes
	        $(this).find('.input-group-addon .count').text(' ' + e.dates.length);
	    });
	}
	
	return MaskMultiDate;
	
}());

Logic.MaskDateMes = (function() {
	
	function MaskDateMes() {
		this.inputDateMes = $('.js-date-mes');
	}
	
	MaskDateMes.prototype.enable = function() {
		this.inputDateMes.mask('00/0000');
		this.inputDateMes.datepicker({
			orientation: 'bottom',
			language: 'pt-BR',
			autoclose: true,
			format: 'mm/yyyy'
		});
	}
	
	return MaskDateMes;
	
}());


Logic.MaskDateTime = (function() {
	
	function MaskDateTime() {
		this.inputDate = $('.js-date-time');
	}
	
	MaskDateTime.prototype.enable = function() {
		this.inputDate.mask('00/00/0000 00:00');
		this.inputDate.datetimepicker({
		    locale: 'pt-BR'
		});
	}
	
	return MaskDateTime;
	
}());


Logic.MaskHora = (function() {
	
	function MaskHora() {
		this.hora = $('.js-hora');
	}
	
	MaskHora.prototype.enable = function() {
		this.hora.mask('99:99');
	}
	
	return MaskHora;
}());


Logic.DataFinalPelaDataInicial = (function() {
	
	function DataFinalPelaDataInicial() {
		this.inputDateInicial = $('#dataInicial');
		this.inputDateFinal = $('#dataFinal');
	}
	
	DataFinalPelaDataInicial.prototype.enable = function() {
		this.inputDateInicial.on('change', onDataInicialAlterada.bind(this));
	}
	
	function onDataInicialAlterada(event) {
		event.preventDefault();
		this.inputDateFinal.val(this.inputDateInicial.val());
	}

	return DataFinalPelaDataInicial;
}());


Logic.Security = (function() {
	
	function Security() {
		this.token = $('input[name=_csrf]').val();
		this.header = $('input[name=_csrf_header]').val();
	}
	
	Security.prototype.enable = function() {
		$(document).ajaxSend(function(event, jqxhr, settings) {
			jqxhr.setRequestHeader(this.header, this.token);
		}.bind(this));
	}
	
	return Security;
	
}());


Logic.formatarMoeda = function(valor) {
	numeral.language('pt-br');
	return numeral(valor).format('0,0.00');
}


Logic.Volume = (function() {
	
	function Volume() {
		this.volume = $('.js-volume');
	}
	
	Volume.prototype.enable = function() {
		//this.volume.maskMoney({ precision: 1, thousands: '.' });
		this.volume.mask('#.##0,0', {reverse: true, precision: 1});
	}
	
	return Volume;
}());


Logic.VolumeDuasCasasDecimais = (function() {
	
	function VolumeDuasCasasDecimais() {
		this.volumeDuasCasasDecimais = $('.js-volume-duas-casas-decimais');
	}
	
	VolumeDuasCasasDecimais.prototype.enable = function() {
		this.volumeDuasCasasDecimais.maskMoney({ precision: 2, thousands: '.' });
	}
	
	return VolumeDuasCasasDecimais;
}());


Logic.ConsumoDiesel = (function() {
	
	function ConsumoDiesel() {
		this.consumoDiesel = $('.js-consumo-diesel');
	}
	
	ConsumoDiesel.prototype.enable = function() {
		this.consumoDiesel.maskMoney({ precision: 2, thousands: '.' });
	}
	
	return ConsumoDiesel;
}());

Logic.MaskPlaca = (function() {
	
	function MaskPlaca() {
		this.placa = $('.js-placa');
	}
	
	MaskPlaca.prototype.enable = function() {
		this.placa.inputmask({mask: ['AAA-9999','AAA-9A99']});
	}
	
	return MaskPlaca;
	
}());


function somenteNumeros(num) {
    var er = /[^0-9.]/;
    er.lastIndex = 0;
    var campo = num;
    if (er.test(campo.value)) {
      campo.value = "";
    }
}




$(function() {
	var maskMoney = new Logic.MaskMoney();
	maskMoney.enable();
	
	var maskDate = new Logic.MaskDate();
	maskDate.enable();
	
	var maskDateImportacaoEscala = new Logic.MaskDateImportacaoEscala();
	maskDateImportacaoEscala.enable();
	
	var maskMultiDate = new Logic.MaskMultiDate();
	maskMultiDate.enable();
	
	var maskDateMes = new Logic.MaskDateMes();
	maskDateMes.enable();
	
	var maskDateTime = new Logic.MaskDateTime();
	maskDateTime.enable();
	
	var maskPlaca = new Logic.MaskPlaca();
	maskPlaca.enable();
	
	var maskHora = new Logic.MaskHora();
	maskHora.enable();
	
	var dataFinalPelaDataInicial = new Logic.DataFinalPelaDataInicial();
	dataFinalPelaDataInicial.enable();
	
	var security = new Logic.Security();
	security.enable();
	
	var volume = new Logic.Volume();
	volume.enable();
	
	var volumeDuasCasasDecimais = new Logic.VolumeDuasCasasDecimais();
	volumeDuasCasasDecimais.enable();
	
	var consumoDiesel = new Logic.ConsumoDiesel();
	consumoDiesel.enable();
});




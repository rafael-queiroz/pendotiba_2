Logic = Logic || {};

Logic.Autocomplete = (function() {
	
	function Autocomplete() {
		this.matriculaOuNomeInput = $('.js-matricula-nome-funcionario-input');
		this.idFuncionarioSelecionado = $('#idFuncionarioSelecionado');
		this.funcionarioSelecionado = $('#funcionarioSelecionado');
	}
	
	Autocomplete.prototype.iniciar = function() {
		var options = {
			url: function(matriculaOuNome) {
				return '/pendotiba-logic-bus/rest/funcionario/listarPorMatriculaOuNome?matriculaOuNome=' + matriculaOuNome;
			}.bind(this),
			//getValue: 'nome',
			getValue: function(element) {
		       return element.matricula + ' - ' + element.nome;
		    },
			minCharNumber: 3,
			requestDelay: 300,
			ajaxSettings: {
				contentType: 'application/json'
			},			
			cssClasses: "sheroes",
			
			list: {
				maxNumberOfElements: 10,
			    showAnimation: {
			      type: "slide"
			    },
			    hideAnimation: {
			      type: "slide"
			    },
			   
			    onChooseEvent: onFuncionarioSelecionado.bind(this)
			}
		};
		
		this.matriculaOuNomeInput.easyAutocomplete(options)
	}
	
	
	function onFuncionarioSelecionado (){
		var objeto = $('#funcionario').getSelectedItemData();
		console.log(objeto);
		console.log(objeto.nome);
		this.funcionarioSelecionado.val(objeto.id);
		console.log(funcionarioSelecionado);
	}
	
	return Autocomplete
	
}());


$(function() {
	var autocomplete = new Logic.Autocomplete();
	autocomplete.iniciar();
});
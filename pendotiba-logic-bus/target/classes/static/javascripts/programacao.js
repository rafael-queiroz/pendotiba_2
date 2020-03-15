var Logic = Logic || {};

Logic.Programacao = (function() {
	
	function Programacao(tabelaViagensProgramacao) {
		this.tabelaViagensProgramacao = tabelaViagensProgramacao;
	}
	
	Programacao.prototype.iniciar = function() {
		this.tabelaViagensProgramacao.on('tabela-viagens-atualizada', onTabelaViagensAtualizada.bind(this));
	}
	
	function onTabelaViagensAtualizada(evento, valorTotalProdutos) {
		this.valorTotalProdutos = valorTotalProdutos == null ? 0 : valorTotalProdutos;
	}
	
	function onValoresAlterados() {
		var valorTotal = numeral(this.valorTotalProdutos);
		this.valorTotalBox.html(Logic.formatarMoeda(valorTotal));
		
		this.valorTotalBoxContainer.toggleClass('negativo', valorTotal < 0);
	}
	
	return Programacao;
	
}());

$(function() {
	
	var tabelaViagensProgramacao = new Logic.TabelaViagensProgramacao();
	tabelaViagensProgramacao.iniciar();
	
	var programacao = new Logic.Programacao(tabelaViagensProgramacao);
	programacao.iniciar();
	
});


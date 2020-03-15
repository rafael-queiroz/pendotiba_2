
var Logic = Logic || {};

Logic.MascaraVolume = (function() {
      
      function MascaraVolume() {
            this.dataCompetencia = $('.js-data-competencia');
            this.inputCarro = $('#carro');
            this.inputVolume = $('#volume');
      }
      
      MascaraVolume.prototype.iniciar = function() {
            this.dataCompetencia.on('change', onDataCompetenciaAlterada.bind(this));
            var data = this.dataCompetencia.val();
    		listarAbastecimentosPorData.call(this, data);
      }
      
      function onDataCompetenciaAlterada(evento) {
            var tipoPessoaSelecionada = $(evento.currentTarget);
            aplicarMascara.call(this, tipoPessoaSelecionada);
            this.inputCpfCnpj.val('');
      }
      
      function aplicarMascara(tipoPessoaSelecionada) {
            this.labelCpfCnpj.text(tipoPessoaSelecionada.data('documento'));
            this.inputCpfCnpj.mask(tipoPessoaSelecionada.data('mascara'));
            this.inputCpfCnpj.removeAttr('disabled');
      }
      
      return MascaraVolume;
      
}());

$(function() {
      var mascaraVolume = new Logic.MascaraVolume();
      mascaraVolume.iniciar();
});

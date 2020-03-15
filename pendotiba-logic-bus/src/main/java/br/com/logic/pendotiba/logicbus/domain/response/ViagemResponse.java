package br.com.logic.pendotiba.logicbus.domain.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViagemResponse implements Serializable {

    @JsonProperty("sentido")
    String sentido;

    @JsonProperty("diaTipo")
    String tipoDia;

    @JsonProperty("termoEmpresa")
    String termoEmpresa;

    @JsonProperty("turno")
    String turno;

    @JsonProperty("rende")
    String rende;

    @JsonProperty("rendidoPor")
    String rendidoPor;

    @JsonProperty("saidaGaragem")
    String saidaGaragem;

    @JsonProperty("sufixo")
    String sufixo;

    @JsonProperty("numeroViagens")
    String numeroViagens;

    @JsonProperty("numeroVeiculos")
    String numeroVeiculos;

    @JsonProperty("codigoProgramacao")
    String codigoProgramacao;

    @JsonProperty("tabela")
    String tabela;

    @JsonProperty("pontoSaida")
    String postoSaida;

    @JsonProperty("pontoChegada")
    String pontoChegada;

    @JsonProperty("sequencialVg")
    String sequenciaVg;

    @JsonProperty("linhaNumero")
    String linhaNumero;

    @JsonProperty("atividade")
    String atividade;

    @JsonProperty("flagProdutivo")
    String flagProdutivo;

    @JsonProperty("horaSaida")
    String horaSaida;

    @JsonProperty("horaChegada")
    String horaChegada;

    @JsonProperty("diaSaida")
    String diaSaida;

    @JsonProperty("diaChegada")
    String diaChegada;

    @JsonProperty("mensagem")
    String mensagem;

    @JsonProperty("dataExportacao")
    String dataExportacao;

}

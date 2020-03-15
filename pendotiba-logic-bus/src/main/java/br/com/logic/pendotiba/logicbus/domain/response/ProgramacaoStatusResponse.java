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
public class ProgramacaoStatusResponse implements Serializable {

    @JsonProperty("status")
    Long status;

    @JsonProperty("response")
    ProgramacaoResponse programacaoResponse;
}

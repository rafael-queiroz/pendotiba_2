package br.com.logic.pendotiba.logicbus.domain.client;

import br.com.logic.pendotiba.logicbus.domain.response.ProgramacaoStatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://transoceanico.wplexon.com.br/schedule-exporter/by-date/transoceanicows/", name = "programacao")
public interface ProgramacaoClient {

    @GetMapping("{data}")
    ProgramacaoStatusResponse getProgramacaoStatusResponse(@PathVariable("data") String data);
}

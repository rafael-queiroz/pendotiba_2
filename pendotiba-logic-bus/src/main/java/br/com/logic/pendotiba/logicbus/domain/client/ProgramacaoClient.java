package br.com.logic.pendotiba.logicbus.domain.client;

import br.com.logic.pendotiba.logicbus.domain.response.ProgramacaoStatusResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient
public interface ProgramacaoClient {

    @RequestMapping(method = RequestMethod.GET, value = "http://transoceanico.wplexon.com.br/schedule-exporter/by-date/transoceanicows/{data}}")
    ProgramacaoStatusResponse getProgramacaoStatusResponse(String data);
}

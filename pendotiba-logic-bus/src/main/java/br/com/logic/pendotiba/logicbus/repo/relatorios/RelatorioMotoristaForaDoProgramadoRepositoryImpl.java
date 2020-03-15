package br.com.logic.pendotiba.logicbus.repo.relatorios;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.dto.relatorios.RelatorioMotoristaForaDoProgramadoDTO;
import br.com.logic.pendotiba.logicbus.filter.relatorios.RelatorioMotoristaForaDoProgramadoFilter;

@Repository
public class RelatorioMotoristaForaDoProgramadoRepositoryImpl {
	
	@PersistenceContext
    EntityManager manager;


    public List<RelatorioMotoristaForaDoProgramadoDTO> listar(RelatorioMotoristaForaDoProgramadoFilter filter) {
        StringBuilder query = new StringBuilder();

        query.append("SELECT p1.data_competencia, mot.matricula, mot.nome, p1.inicio_trabalho as inicioTrabalhoProg1, c1.numero_de_ordem as numeroDeOrdemProg1, p2.inicio_trabalho as inicioTrabalhoProg2, c2.numero_de_ordem  as numeroDeOrdemProg2 ");
        query.append("FROM funcionario mot  ");
        query.append("LEFT JOIN programacao p1 ON mot.id = p1.id_motorista_programado ");

        if(filter.getDataInicial() != null)
            query.append("AND p1.data_competencia >= '").append(DataUtil.toStringTimestampFormatadaParaSQL(filter.getDataInicial())).append("' ");
        if(filter.getDataFinal() != null)
            query.append("AND p1.data_competencia <= '").append(DataUtil.toStringTimestampFormatadaParaSQL(filter.getDataFinal())).append("' ");

        query.append("LEFT JOIN carro c1 ON p1.id_carro_programado = c1.id ");
        query.append("LEFT JOIN programacao p2 ON mot.id = p2.id_motorista AND mot.id <> p2.id_motorista_programado AND p2.data_competencia = p1.data_competencia ");
        query.append("LEFT JOIN carro c2 ON p2.id_carro_realizado = c2.id ");
        query.append("WHERE p1.id_motorista_programado <> p1.id_motorista  ");

        if (filter != null && filter.getMotorista() != null && filter.getMotorista().getId() != null)
            query.append("AND mot.id = ").append(filter.getMotorista().getId());

        List<RelatorioMotoristaForaDoProgramadoDTO> listaRel = new ArrayList<>();
        List<?> retornoRel = manager.createNativeQuery(query.toString()).getResultList();

        Iterator<?> itRel = retornoRel.iterator();

        while ( itRel.hasNext() ){
            Object[] linha = (Object[]) itRel.next();
            RelatorioMotoristaForaDoProgramadoDTO rel = new RelatorioMotoristaForaDoProgramadoDTO();

            if(linha[0] != null)
                rel.setDataCompetencia(DataUtil.getDateDDMMYYYY(linha[0].toString()));

            if(linha[1] != null)
                rel.setMatriculaMotorista((linha[1].toString()));

            if(linha[2] != null)
                rel.setNomeMotorista((linha[2].toString()));

            if(linha[3] != null)
                rel.setHoraInicioTrabalhoProgramado(DataUtil.getTime(linha[3].toString()));

            if(linha[4] != null)
                rel.setCarroProgramado((linha[4].toString()));

            if(linha[5] != null)
                rel.setHoraInicioTrabalhoRealizado(DataUtil.getTime(linha[5].toString()));

            if(linha[6] != null)
                rel.setCarroRealizado((linha[6].toString()));

            listaRel.add(rel);
        }

        return listaRel;
    }

}

package br.com.logic.pendotiba.logicbus.repo.relatorios;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.dto.relatorios.RelatorioProgramacaoInciadaForaDoHorarioProgramadoDTO;
import br.com.logic.pendotiba.logicbus.filter.relatorios.RelatorioProgramacaoIniciadaForaDoHorarioProgramadoFilter;

@Repository
public class RelatorioProgramacaoIniciadaForaDoHorarioProgramadoRepositoryImpl {
	
	 @PersistenceContext
	    EntityManager manager;

	    public List<RelatorioProgramacaoInciadaForaDoHorarioProgramadoDTO> listar(RelatorioProgramacaoIniciadaForaDoHorarioProgramadoFilter filter) {
	        StringBuilder query = new StringBuilder();

	        query.append("select p.data_competencia, pon.descricao as ponto, f.matricula, f.nome, t.descricao as turno, p.inicio_trabalho,  ");
	        query.append("(select top 1 hora_saida_realizada from viagem where id_programacao = p.id order by hora_saida_programada) as inicio_trabalho_realizado,  ");
	        query.append("c.numero_de_ordem  ");
	        query.append("from programacao p  ");
	        query.append("left join ponto pon on p.id_ponto_pegada_motorista = pon.id ");
	        query.append("left join funcionario f on p.id_motorista = f.id ");
	        query.append("left join turno t on p.id_turno = t.id ");
	        query.append("left join carro c on p.id_carro_realizado = c.id ");
	        query.append("WHERE p.inicio_trabalho <> (select top 1 hora_saida_realizada from viagem where id_programacao = p.id order by hora_saida_programada)  ");

	        if(filter.getDataInicial() != null)
	            query.append(" AND p.data_competencia >= '").append(DataUtil.toStringTimestampFormatadaParaSQL(filter.getDataInicial())).append("' ");
	        if(filter.getDataFinal() != null)
	            query.append(" AND p.data_competencia <= '").append(DataUtil.toStringTimestampFormatadaParaSQL(filter.getDataFinal())).append("' ");

	        if (filter.getMotorista() != null && filter.getMotorista().getId() != null)
	            query.append(" AND f.id = ").append(filter.getMotorista().getId());

	        if (filter.getTurno() != null && filter.getTurno().getId() != null)
	            query.append(" AND t.id = ").append(filter.getTurno().getId());

	        if (filter.getPontoPegadaMotorista() != null && filter.getPontoPegadaMotorista().getId() != null)
	            query.append(" AND pon.id = ").append(filter.getPontoPegadaMotorista().getId());

	        query.append("order by p.data_competencia, p.inicio_trabalho ");

	        List<RelatorioProgramacaoInciadaForaDoHorarioProgramadoDTO> listaRel = new ArrayList<>();
	        List<?> retornoRel = manager.createNativeQuery(query.toString()).getResultList();

	        Iterator<?> itRel = retornoRel.iterator();

	        while ( itRel.hasNext() ){
	            Object[] linha = (Object[]) itRel.next();
	            RelatorioProgramacaoInciadaForaDoHorarioProgramadoDTO rel = new RelatorioProgramacaoInciadaForaDoHorarioProgramadoDTO();

	            if(linha[0] != null)
	                rel.setDataCompetencia(DataUtil.getDateDDMMYYYY(linha[0].toString()));

	            if(linha[1] != null)
	                rel.setPontoPegadaMotorista((linha[1].toString()));

	            if(linha[2] != null)
	                rel.setMatriculaMotorista((linha[2].toString()));

	            if(linha[3] != null)
	                rel.setNomeMotorista((linha[3].toString()));

	            if(linha[4] != null)
	                rel.setTurno((linha[4].toString()));

	            if(linha[5] != null)
	                rel.setHoraInicioTrabalhoProgramado(DataUtil.getTime(linha[5].toString()));

	            if(linha[6] != null)
	                rel.setHoraInicioTrabalhoRealizado(DataUtil.getTime(linha[6].toString()));

	            if(linha[7] != null)
	                rel.setCarroRealizado((linha[7].toString()));

	            listaRel.add(rel);
	        }

	        return listaRel;
	    }

}

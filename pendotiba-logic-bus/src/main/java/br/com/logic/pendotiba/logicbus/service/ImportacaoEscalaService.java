package br.com.logic.pendotiba.logicbus.service;

import br.com.logic.pendotiba.core.model.EscalaImportada;
import br.com.logic.pendotiba.core.model.Linha;
import br.com.logic.pendotiba.core.model.ProgramacaoImportada;
import br.com.logic.pendotiba.core.repository.EscalaImportadaRepository;
import br.com.logic.pendotiba.core.repository.ProgramacaoImportadaRepository;
import br.com.logic.pendotiba.core.repository.TurnoRepository;
import br.com.logic.pendotiba.core.repository.ViagemImportadaRepository;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.repo.ProgramacaoImportadaRepositoryImpl;
import br.com.logic.pendotiba.logicbus.vo.EscalaVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class ImportacaoEscalaService {
	
	static final String TIME_ZONE = "America/Sao_Paulo";
	
	@Autowired
	FuncionarioService funcionarioService;
	
	@Autowired
	CarroService carroService;
	
	@Autowired
	LinhaService linhaService;
	
	@Autowired
	EscalaImportadaRepository escalaImportadaRepository;
	
	@Autowired
	ProgramacaoImportadaRepositoryImpl programacaoImportadaRepositoryImpl;
	
	@Autowired
	ViagemImportadaRepository viagemImportadaRepository;
	
	@Autowired
	ProgramacaoImportadaRepository programacaoImportadaRepository;
	
	@Autowired
	TurnoRepository turnoRepository;
	
	@Autowired
	ProgramacaoService programacaoService;
	
	
	
	final RestTemplate restTemplate;
	
	@Value("${api.base.url}")
    String url;

	
    public ImportacaoEscalaService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    
    @Transactional
    public void getPostsPlainJSON(Date dataCompetencia) {
    	RestTemplate restTemplate = new RestTemplate();
    	EscalaVO[] escalas = restTemplate.getForObject(url, EscalaVO[].class);
    	
    	viagemImportadaRepository.deleteByDataCompetencia(dataCompetencia);
    	escalaImportadaRepository.deleteByDataCompetencia(dataCompetencia);
		
        for(int i = 0; i < escalas.length; i++) {
        	EscalaImportada ei = new EscalaImportada();
        		
        	ei.setDataCompetencia(DataUtil.getDateDDMMYYYYReturnDDMMYYYY(escalas[i].getDtSoltura()));
        	if(dataCompetencia.equals(ei.getDataCompetencia()) && !escalas[i].getNrLinha().contains("-BASE") && (escalas[i].getNrLinha().equals("46") || escalas[i].getNrLinha().equals("48")) ) {
	        	
	        	ei.setIncioJornada(DataUtil.getTime(DataUtil.getDateDDMMYYYYReturnDDMMYYYY(escalas[i].getDtSoltura()), escalas[i].getHrInicioTrabalho()));
	        	
	        	ei.setSaida(DataUtil.getTime(DataUtil.getDateDDMMYYYYReturnDDMMYYYY(escalas[i].getDtSoltura()), escalas[i].getHrInicioJornada()));
	        	
	        	ei.setMotorista(StringUtils.isEmpty(escalas[i].getNrMatriculaMotorista()) ? null : funcionarioService.buscarFuncionarioPorMatricula(escalas[i].getNrMatriculaMotorista())); 	//matricula motorista
	        	
	        	ei.setParceiro(StringUtils.isEmpty(escalas[i].getNrMatriculaCobrador()) ? null : funcionarioService.buscarFuncionarioPorMatricula(escalas[i].getNrMatriculaCobrador())); 		//matricula parceiro
	        	
	        	ei.setCarro(StringUtils.isEmpty(escalas[i].getCdVeiculo()) ? null : carroService.buscarCarroPorNumeroDeOrdem(escalas[i].getCdVeiculo())); 	//carro
	        	
	        	
	        	if(!StringUtils.isEmpty(escalas[i].getNrLinha())) {
	        		Linha linha = linhaService.buscarPorCodigo(escalas[i].getNrLinha());
	       			ei.setLinha(linha); 	//linha
	        	}
	        	
	        	String versao = null;
				if(DataUtil.ehDomingo(ei.getDataCompetencia()))
					versao = "D";
				else if(DataUtil.ehSabado(ei.getDataCompetencia()))
					versao = "S";
				else
					versao = "U";
				
				
				//ProgramacaoImportada pi = programacaoImportadaRepository.buscarPorHoraSaidaOrdemViagemDiaSemana(ei.getSaida(), 1L, versao); 11/10/2019
				ProgramacaoImportada pi = programacaoImportadaRepositoryImpl.buscarPorLinhaHoraSaidaHoraChegadaOrdemViagemDiaSemana(ei.getLinha(), ei.getIncioJornada(), ei.getSaida(), 1L, versao);
				
				if(pi == null)
					pi = programacaoImportadaRepositoryImpl.buscarPorLinhaHoraSaidaOrdemViagemDiaSemana(ei.getLinha(), ei.getSaida(), 1L, versao);
				
				if(pi != null) {
					//pi = programacaoImportadaRepository.findByOrdemProgramacaoAndOrdemViagem("1A", 1L);
					
					if(!pi.getMovimento().equals("VIAGEM"))
						pi = programacaoImportadaRepository.findByOrdemProgramacaoAndOrdemViagemAndVersao(pi.getOrdemProgramacao(), 2L, pi.getVersao());
					
					if (pi.getOrdemProgramacao().contains("A"))
						ei.setTurno(turnoRepository.findById(1L).orElse(null));
					
					else if (pi.getOrdemProgramacao().contains("B"))
						ei.setTurno(turnoRepository.findById(2L).orElse(null));
					
					else if (pi.getOrdemProgramacao().contains("C"))
						ei.setTurno(turnoRepository.findById(3L).orElse(null));
					
					else if (pi.getOrdemProgramacao().contains("E"))
						ei.setTurno(turnoRepository.findById(4L).orElse(null));
					
					else if (pi.getOrdemProgramacao().contains("F"))
						ei.setTurno(turnoRepository.findById(5L).orElse(null));
	
					ei.setOrdemProgramacao(pi.getOrdemProgramacao());
					//ei.setOrdemViagem(pi.getOrdemViagem());
				}
				
				escalaImportadaRepository.save(ei);
	        }
        }
    }
    
}
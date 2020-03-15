package br.com.logic.pendotiba.logicbus.validation;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import br.com.logic.pendotiba.core.enums.TipoArquivoEnum;
import br.com.logic.pendotiba.core.model.ArquivoImportado;
import br.com.logic.pendotiba.core.model.ErroProgramacao;
import br.com.logic.pendotiba.core.model.Programacao;
import br.com.logic.pendotiba.core.repository.ArquivoImportadoRepository;
import br.com.logic.pendotiba.core.repository.ErroProgramacaoRepository;
import br.com.logic.pendotiba.core.repository.ProgramacaoRepository;
import br.com.logic.pendotiba.core.util.DataUtil;
import br.com.logic.pendotiba.logicbus.service.ArquivoEscalaImportadoService;

@Service
public class ArquivoEscalaValidation {
	
	@Autowired
	ArquivoEscalaImportadoService arquivoEscalaImportadoService;
	
	@Autowired
	ArquivoImportadoRepository arquivoImportadoRepository;
	
	@Autowired
	ErroProgramacaoRepository erroProgramacaoRepository;
	
	@Autowired
	ProgramacaoRepository programacaoRepository;
	
	
	public void validarArquivoEscala(ArquivoImportado arquivoImportado, BindingResult result) {
		Date dataCompetencia = arquivoEscalaImportadoService.buscarDataCompetencia(arquivoImportado);
		ArquivoImportado arquivo = arquivoImportadoRepository.findByDataCompetenciaAndTipoArquivo(dataCompetencia, TipoArquivoEnum.ESCALA);

		if(dataCompetencia == null || dataCompetencia.before(DataUtil.startOfDay(new Date())) || dataCompetencia.after(DataUtil.getDataPosteriorEmDias(dataCompetencia, 2)))
			result.addError(new ObjectError("", "Data do arquivo selecionado é inválida"));
		
		else if(DataUtil.ehHoje(dataCompetencia) && arquivo != null)
			result.addError(new ObjectError("", "Já existe importação para o dia de hoje"));
	}


	public void validarExistenciaDasViagens(ArquivoImportado arquivoImportado, BindingResult result) {
		List<ErroProgramacao> erros = erroProgramacaoRepository.findByDataCompetenciaAndDescricao(arquivoImportado.getDataCompetencia(), ErroProgramacao.PROGRAMACAO_SEM_VIAGENS);
		List<Programacao> programacoes = programacaoRepository.findByDataCompetencia(arquivoImportado.getDataCompetencia());
		
		if(!erros.isEmpty() && (programacoes.size() / erros.size()) < 2)
			result.addError(new ObjectError("", "Mais de 50% das programações não possuem viagens, rever o arquivo de programações referente a data de competência da escala"));
	}
	
}

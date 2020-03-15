package br.com.logic.pendotiba.core.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public interface Constantes {

	int GLOBAL_CASAS_DECIMAIS = 1;
	
	int VOLUME_CASAS_DECIMAIS = 1;
	
	int MOEDA_CASAS_DECIMAIS = 4;
	
	int MOEDA_TOTAL_CASAS_DECIMAIS = 2;
	
	int CONSUMO_CASAS_DECIMAIS = 2;
	
	int ODOMETRO_CASAS_DECIMAIS = 2;
	
	int QUANTIDADE_CASAS_DECIMAIS = 2;

	String STRING_VAZIA = "";
	
	BigDecimal KM_ZERO = new BigDecimal("0.000");
	
	BigDecimal VALOR_ZERO = new BigDecimal("0.00");

	BigDecimal VALOR_UM = new BigDecimal("1.00");

	BigDecimal VALOR_CEM = new BigDecimal("100.00");
	
	MathContext MATH_CONTEXT_PADRAO = new MathContext(2, RoundingMode.HALF_UP);
	
}

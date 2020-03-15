package br.com.logic.pendotiba.core.util;

import java.math.BigDecimal;

/**
 * Utilitarios para números voltado ao consumo de cm
 * @author Vitor
 * @since 24 JUN 2015
 */
public class ConsumoNumberUtil {
	
	public static final BigDecimal CONSUMO_ZERO = NumberUtil.novoBigDecimal(0, Constantes.CONSUMO_CASAS_DECIMAIS);
	
	public static final BigDecimal CONSUMO_MAX = NumberUtil.novoBigDecimal(100, Constantes.CONSUMO_CASAS_DECIMAIS);



	//operadores...
	public static BigDecimal dividir(BigDecimal v1, BigDecimal v2) {
		return NumberUtil.dividir(v1, v2, Constantes.CONSUMO_CASAS_DECIMAIS );
	}
	
	public static BigDecimal dividir(BigDecimal val, int inteiro) {
		return NumberUtil.dividir(val, inteiro, Constantes.CONSUMO_CASAS_DECIMAIS);
	}

	public static BigDecimal subtrair(BigDecimal v1, BigDecimal v2) {
		return NumberUtil.subtrair(v1, v2, Constantes.CONSUMO_CASAS_DECIMAIS);
	}

	public static BigDecimal somar(BigDecimal v1, BigDecimal v2) {
		return NumberUtil.somar(v1, v2, Constantes.CONSUMO_CASAS_DECIMAIS);
	}
	
	public static BigDecimal multiplicar(BigDecimal v1, BigDecimal v2) {
		return NumberUtil.multiplicar(v1, v2, Constantes.CONSUMO_CASAS_DECIMAIS );
	}
	
	
	//comparadores...
	public static boolean ehMenor(BigDecimal v1, BigDecimal v2) {
		return NumberUtil.ehMenor(v1, v2);
	}
	
	public static boolean ehMaior(BigDecimal v1, BigDecimal v2) {
		return NumberUtil.ehMaior(v1, v2);
	}


	
	public static boolean estahDentroDoIntervalo(BigDecimal consumo, BigDecimal consumoMinimo, BigDecimal consumoMaximo) {
		return NumberUtil.estahDentroDoIntervalo(consumo, consumoMinimo, consumoMaximo);
	}

}

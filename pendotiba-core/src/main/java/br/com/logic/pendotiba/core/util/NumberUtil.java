package br.com.logic.pendotiba.core.util;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class NumberUtil {
	
	//constantes para padronizar os big decimals
	private static final MathContext DEFAULT_MATH_CONTEXT = MathContext.DECIMAL32;
	private static final RoundingMode DEFAULT_ROUDING_MODE = RoundingMode.HALF_DOWN;


	private static final BigDecimal VALOR_ZERO = novoBigDecimal("0");
	
	
	/* ********
	 * BUILDERS 
	 **********/

	//int...
	
	protected static BigDecimal novoBigDecimal(Integer valorInt) {
		BigDecimal dec = new BigDecimal(valorInt, DEFAULT_MATH_CONTEXT);
		dec = dec.setScale(Constantes.GLOBAL_CASAS_DECIMAIS, DEFAULT_ROUDING_MODE);
		return dec;
	}

	protected static BigDecimal novoBigDecimal(Integer valorInt, final int CASAS_DECIMAIS) {
		BigDecimal dec = new BigDecimal(valorInt, DEFAULT_MATH_CONTEXT);
		dec = dec.setScale(CASAS_DECIMAIS, DEFAULT_ROUDING_MODE);
		return dec;
	}


	//double...
	
	protected static BigDecimal novoBigDecimal(double valorDouble) {
		BigDecimal dec = new BigDecimal(valorDouble, DEFAULT_MATH_CONTEXT);
		dec = dec.setScale(Constantes.GLOBAL_CASAS_DECIMAIS);
		return dec;
	}
	
	protected static BigDecimal novoBigDecimal(double valorDouble, final int CASAS_DECIMAIS) {
		BigDecimal dec = new BigDecimal(valorDouble, DEFAULT_MATH_CONTEXT);
		dec = dec.setScale(CASAS_DECIMAIS);
		return dec;
	}

	
	//string...
		
	protected static BigDecimal novoBigDecimal(String valorString) {
		BigDecimal dec = new BigDecimal( valorString, DEFAULT_MATH_CONTEXT );
		dec = dec.setScale(Constantes.GLOBAL_CASAS_DECIMAIS);
		return dec;
	}

	public static BigDecimal novoBigDecimal(String valorString, final int CASAS_DECIMAIS) {
		BigDecimal dec = new BigDecimal( valorString, DEFAULT_MATH_CONTEXT );
		dec = dec.setScale(CASAS_DECIMAIS);
		return dec;
	}


	
	/* ************
	 * COMPARADORES
	 **************/
	
	public static boolean ehIgual(BigDecimal valor1, BigDecimal valor2) {
		if (valor1==null || valor2==null) return false;
		return valor1.compareTo( valor2 ) == 0;
	}

	
	public static boolean ehMaiorOuIgual(BigDecimal valor1, BigDecimal valor2) {
		if (valor1==null || valor2==null) return false;
		return valor1.compareTo( valor2 ) >= 0;
	}
	
	public static boolean ehMaior(BigDecimal valor1, BigDecimal valor2) {
		if (valor1==null || valor2==null) return false;
		return valor1.compareTo( valor2 ) > 0;
	}

	public static Boolean ehMaiorQueZero(BigDecimal valor) {
		if (valor==null) return false;
		return ehMaior(valor, VALOR_ZERO);
	}

	
	public static boolean ehMenorOuIgual(BigDecimal valor1, BigDecimal valor2) {
		if (valor1==null || valor2==null) return false;
		return valor1.compareTo( valor2 ) <= 0;
	}
	
	public static boolean ehMenor(BigDecimal valor1, BigDecimal valor2) {
		if (valor1==null || valor2==null) return false;
		return valor1.compareTo( valor2 ) < 0;
	}

	public static Boolean ehMenorQueZero(BigDecimal valor) {
		if (valor==null) return false;
		return ehMenor(valor, VALOR_ZERO);
	}

	
	public static boolean estahDentroDoIntervalo(BigDecimal valor, BigDecimal valorMinimo, BigDecimal valorMaximo) {
		if (valor==null || valorMinimo==null || valorMaximo==null) return false;
		return valor.compareTo( valorMinimo ) >= 0 && valor.compareTo( valorMaximo ) <= 0;
	}
	
	


	
	/* **********
	 * OPERADORES
	 ************/
	
	//subtrair
	
	protected static BigDecimal subtrair(BigDecimal v1, BigDecimal v2, final int CASAS_DECIMAIS) {
		if (v1==null || v2==null) return VALOR_ZERO;
		
		BigDecimal dec1 = v1.setScale(CASAS_DECIMAIS, DEFAULT_ROUDING_MODE);
		BigDecimal dec2 = v2.setScale(CASAS_DECIMAIS, DEFAULT_ROUDING_MODE);
		return dec1.subtract(dec2, DEFAULT_MATH_CONTEXT);
	}
	
	protected static BigDecimal subtrair(Integer int1, Integer int2, final int CASAS_DECIMAIS) {
		if (int1==null || int2==null) return VALOR_ZERO;

		BigDecimal dec1 = novoBigDecimal(int1, CASAS_DECIMAIS);
		BigDecimal dec2 = novoBigDecimal(int2, CASAS_DECIMAIS);
		return subtrair(dec1, dec2, CASAS_DECIMAIS);
	}
	
	protected static BigDecimal subtrair(BigDecimal v1, Integer int2, final int CASAS_DECIMAIS) {
		if (v1==null || int2==null) return VALOR_ZERO;

		BigDecimal dec1 = v1.setScale(CASAS_DECIMAIS);
		BigDecimal dec2 = novoBigDecimal(int2, CASAS_DECIMAIS);
		return subtrair(dec1, dec2, CASAS_DECIMAIS);
	}
	

	protected static BigDecimal subtrair(BigDecimal v1, BigDecimal v2) {
		return subtrair(v1, v2, Constantes.GLOBAL_CASAS_DECIMAIS);
	}
	

	//somar
	
	protected static BigDecimal somar(BigDecimal v1, BigDecimal v2, final int CASAS_DECIMAIS) {
		if (v1==null || v2==null) return VALOR_ZERO;
		
		v1 = v1.setScale(CASAS_DECIMAIS, DEFAULT_ROUDING_MODE);
		v2 = v2.setScale(CASAS_DECIMAIS, DEFAULT_ROUDING_MODE);
		return v1.add( v2 );
	}
	

	protected static BigDecimal somar(BigDecimal v1, BigDecimal v2) {
		return somar(v1, v2, Constantes.GLOBAL_CASAS_DECIMAIS);
	}

	
	
	//dividir
	
	protected static BigDecimal dividir(BigDecimal dividendo, BigDecimal divisor, final int CASAS_DECIMAIS) {
		if (dividendo==null || ehIgual(dividendo, VALOR_ZERO)) {
			return VALOR_ZERO;
		}
		if (divisor==null || VALOR_ZERO.equals(divisor)) {
			throw new IllegalArgumentException("Divisão por zero detectada");
		}
		
		BigDecimal dec1 = dividendo.setScale(CASAS_DECIMAIS, DEFAULT_ROUDING_MODE);
		BigDecimal dec2 = divisor.setScale(CASAS_DECIMAIS  , DEFAULT_ROUDING_MODE);		
		return dec1.divide(dec2, DEFAULT_ROUDING_MODE);
	}
	
	protected static BigDecimal dividir(BigDecimal dividendo, int divisor, int CASAS_DECIMAIS) {
		evitarDivisaoPorZero(divisor);
		BigDecimal divisorBig = novoBigDecimal(divisor, CASAS_DECIMAIS);
		return dividir(dividendo, divisorBig, CASAS_DECIMAIS);
	}
	
	protected static BigDecimal dividir(int dividendo, int divisor, int CASAS_DECIMAIS) {
		evitarDivisaoPorZero(divisor);
		
		BigDecimal dividendoBig = novoBigDecimal(dividendo, CASAS_DECIMAIS);
		BigDecimal divisorBig   = novoBigDecimal(divisor, CASAS_DECIMAIS);
		
		return dividir(dividendoBig, divisorBig, CASAS_DECIMAIS);
	}
	
	/**
	 * Converte percentual absoluto (ex: 0.345) para relativo (ex: 34.5%)
	 * @param valor
	 * @param CASAS_DECIMAIS
	 * @return
	 */
	protected static BigDecimal converterPercentualAbsolutoParaRelativo(BigDecimal valor, final int CASAS_DECIMAIS) {
		BigDecimal divisor100 = novoBigDecimal(100);
		return multiplicar(valor, divisor100, CASAS_DECIMAIS);
	}

	
	
	protected static BigDecimal dividir(BigDecimal dividendo, BigDecimal divisor) {
		return dividir(dividendo, divisor, Constantes.GLOBAL_CASAS_DECIMAIS);
	}

	protected static BigDecimal dividir(BigDecimal dividendo, int divisor) {
		evitarDivisaoPorZero(divisor);
		return dividir(dividendo, divisor, Constantes.GLOBAL_CASAS_DECIMAIS );
	}
	
	protected static BigDecimal dividir(int dividendo, int divisor) {
		evitarDivisaoPorZero(divisor);
		return dividir(dividendo, divisor, Constantes.GLOBAL_CASAS_DECIMAIS );
	}

	

	
	
	
	
	//multiplicar
	
	protected static BigDecimal multiplicar(BigDecimal v1, BigDecimal v2, final int CASAS_DECIMAIS) {
		if (v1==null || v2==null) {
			return VALOR_ZERO;
		}
		v1 = v1.setScale(CASAS_DECIMAIS, DEFAULT_ROUDING_MODE);
		v2 = v2.setScale(CASAS_DECIMAIS, DEFAULT_ROUDING_MODE);		
		return v1.multiply(v2, DEFAULT_MATH_CONTEXT);
	}
	
	protected static BigDecimal multiplicar(BigDecimal v1, Integer int2, final int CASAS_DECIMAIS) {
		BigDecimal v2 = novoBigDecimal(int2,CASAS_DECIMAIS);
		return multiplicar(v1, v2, CASAS_DECIMAIS);
	}
	
	
	

	/**
	 * Lancar exception se verificar divisor igual a zero
	 * @param divisor
	 */
	private static void evitarDivisaoPorZero(int divisor) {
		if (divisor==0) {
			throw new IllegalArgumentException("Divisao por zero detectada");
		}
	}
	

}

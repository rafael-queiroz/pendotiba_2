package br.com.logic.pendotiba.core.util;

import java.math.BigDecimal;

public class VolumeNumberUtil {
	
	public static final BigDecimal VOLUME_ZERO = NumberUtil.novoBigDecimal("0", Constantes.VOLUME_CASAS_DECIMAIS);
	
	private static final int FATOR_CONVERSAO_VOLUME_ENCERRANTE = 10;
	
	
	//builders...
	
	public static BigDecimal novoBigDecimal(String valorStr) {
		return NumberUtil.novoBigDecimal(valorStr, Constantes.VOLUME_CASAS_DECIMAIS);
	}
	
	public static BigDecimal novoBigDecimal(double valorDouble) {
		return NumberUtil.novoBigDecimal(valorDouble, Constantes.VOLUME_CASAS_DECIMAIS);
	}

	public static BigDecimal novoBigDecimal(Integer valorInteger) {
		return NumberUtil.novoBigDecimal(valorInteger, Constantes.VOLUME_CASAS_DECIMAIS);
	}
	
	public static BigDecimal novoBigDecimalVolumeBombaAbastecimento(String volume) {
		String string = volume.replace(".", "").replace(",", "");
		StringBuilder stringBuilder = new StringBuilder(string);
		stringBuilder.insert(string.length() - 1, '.');
		return NumberUtil.novoBigDecimal(stringBuilder.toString());
	}
	
	
	//operacoes...
	
	public static BigDecimal subtrair(BigDecimal v1, BigDecimal v2, boolean flagAbsoluto) {
		BigDecimal resultado = NumberUtil.subtrair(v1, v2, Constantes.VOLUME_CASAS_DECIMAIS);
		if (flagAbsoluto) {
			return resultado.abs();
		} else {
			return resultado;
		}
	}

//ninguem ta usando	
//	public static BigDecimal subtrair(BigDecimal v1, Integer v2, boolean flagAbsoluto) {
//		BigDecimal resultado = NumberUtil.subtrair(v1, v2, Constantes.VOLUME_CASAS_DECIMAIS);
//		if (flagAbsoluto) {
//			return resultado.abs();
//		} else {
//			return resultado;
//		}
//	}
	

	
	//operadores numericos...
	
	public static BigDecimal subtrair(Integer int1, Integer int2) {
		return NumberUtil.subtrair(int1, int2, Constantes.VOLUME_CASAS_DECIMAIS);
	}
	

	public static  BigDecimal somar(BigDecimal v1, BigDecimal v2) {
		return NumberUtil.somar(v1, v2, Constantes.VOLUME_CASAS_DECIMAIS);
	}
	
	public static  BigDecimal somarNulo(BigDecimal v1, BigDecimal v2) {
		if (v1 == null){
			v1 = VOLUME_ZERO;
		}
		if (v2 == null){
			v2 = VOLUME_ZERO;
		}
		return NumberUtil.somar(v1, v2, Constantes.VOLUME_CASAS_DECIMAIS);
	}
	
	
	
	public static BigDecimal dividir(BigDecimal v1, BigDecimal v2) {
		return NumberUtil.dividir(v1, v2, Constantes.VOLUME_CASAS_DECIMAIS);
	}
	
	public static BigDecimal dividir(BigDecimal v1, int int2) {
		return NumberUtil.dividir(v1, int2, Constantes.VOLUME_CASAS_DECIMAIS);
	}

	public static BigDecimal dividir(int int1, int int2) {
		return NumberUtil.dividir(int1, int2, Constantes.VOLUME_CASAS_DECIMAIS);
	}
	
	
	
	public static BigDecimal multiplicar(BigDecimal v1, BigDecimal v2) {
		return NumberUtil.multiplicar(v1, v2, Constantes.VOLUME_CASAS_DECIMAIS);
	}

	public static BigDecimal multiplicar(BigDecimal v1, Integer int2) {
		return NumberUtil.multiplicar(v1, int2, Constantes.VOLUME_CASAS_DECIMAIS);
	}
	

	public static BigDecimal inverter(BigDecimal v1) {
		BigDecimal resultado = NumberUtil.multiplicar(v1, -1, Constantes.VOLUME_CASAS_DECIMAIS);
		return resultado.setScale( Constantes.VOLUME_CASAS_DECIMAIS );
	}
	
	
	//operadores logicos.. 
	
	public static Boolean ehMenorOuIgual(BigDecimal valor1, Integer valor2) {
		return NumberUtil.ehMaiorOuIgual( novoBigDecimal(valor2), valor1);
	}
	
	public static Boolean ehMenorOuIgualAZero(BigDecimal valor) {
		return NumberUtil.ehMenorOuIgual(valor, VOLUME_ZERO);
	}
	
	public static Boolean ehIgualAZero(BigDecimal valor) {
		return NumberUtil.ehIgual(valor, VOLUME_ZERO);
	}
	
	public static Boolean ehIgual(BigDecimal v1, BigDecimal v2) {
		return NumberUtil.ehIgual(v1, v2);
	}

	
	// utilitario de negocio...
	
	/**
	 * O valor entrado para o encerrante eh inteiro, mas deve ser dividido por 100
	 * @param valorInteiro
	 * @return
	 */
	public static BigDecimal converterVolumeEncerrante(Integer valorInteiro) {
		return dividir(valorInteiro, FATOR_CONVERSAO_VOLUME_ENCERRANTE );
	}
	
	/**
	 * Verifica se a volumeTotal estah dentro do limite toleravel.
	 * Usa o valor absoluto
	 * @param volumeTotal
	 * @param toleranciaEmLitros
	 * @return
	 */
//	public static Boolean getFlagDiferenciaToleravel(BigDecimal volumeTotal, Integer toleranciaEmLitros) {
//		if (volumeTotal==null) {
//			return false;
//		}
//		if (toleranciaEmLitros==null) {
//			toleranciaEmLitros = 0;
//		}
//		
//		return VolumeNumberUtil.ehMenorOuIgual(volumeTotal.abs(), toleranciaEmLitros);
//	}
	
	
	// VERIFICANDO QUEM EH O VALOR MAIOR E EXECUNTANDO O CALCULO DA DIFERENCA DO INICIANTE E ENCERRANTE DA BOMBA
	public static BigDecimal calcularDiferencaVolumeBombaBastecimento(BigDecimal volumeFinal, BigDecimal volumeInicial) {
		if (NumberUtil.ehMaiorOuIgual(volumeFinal, volumeInicial))
			return subtrair(volumeFinal, volumeInicial, true);
		
		BigDecimal volumeMaxBomba = novoBigDecimalVolumeBombaAbastecimento("99999.9");
		return somar(volumeMaxBomba.subtract(volumeInicial), volumeFinal);
	}

}

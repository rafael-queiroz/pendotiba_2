package br.com.logic.pendotiba.logicbus.util;

import java.math.BigDecimal;

public class Teste {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		BigDecimal teste1 = new BigDecimal((double)1564/5);
		BigDecimal teste2 = new BigDecimal(2.000);   
        BigDecimal teste3 = new BigDecimal(87);   
        BigDecimal teste4 = new BigDecimal(200);   
        System.out.println("teste1 = " + teste1);
        System.out.println("teste2 = " + teste2);
        System.out.println("teste3 = " + teste3);
        System.out.println("teste4 = " + teste4);
        teste4 = teste4.multiply(teste2);
        System.out.println("teste4 = " + teste4);
        teste4 = teste4.add(teste3);
        System.out.println("teste4 = " + teste4);
        teste1 = teste1.multiply(teste2);
        System.out.println("teste1 = " + teste1);
        teste1 = teste1.add(teste3);
        System.out.println("teste1 = " + teste1);
        BigDecimal teste5 = teste4.setScale(7,teste4.ROUND_HALF_DOWN);
        System.out.println("teste5 = " + teste5);
        BigDecimal teste6 = teste1.setScale(7,teste1.ROUND_HALF_DOWN);
        System.out.println("teste6 = " + teste6);
        teste6 = teste6.divide(teste5, teste6.ROUND_HALF_DOWN);
        System.out.println("teste6 = " + teste6);
	}

}

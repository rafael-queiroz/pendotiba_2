package br.com.logic.pendotiba.logicbus.util;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class ArquivoImportadoUtil {
	
	public static Date tratarCampoHora(Cell cell) {
		if(cell != null && cell.getDateCellValue() != null)
			return  cell.getDateCellValue();
		return null;
	}
	
	
	public static Date tratarCampoData(Cell cell) {
		if(cell != null && cell.getDateCellValue() != null)
			return  cell.getDateCellValue();
		return null;
	}
	
	
	public static String tratarCampoString(Cell cell) {
		if(cell != null && cell.getStringCellValue() != null && StringUtils.isNotEmpty(cell.getStringCellValue())) {
			return  cell.getStringCellValue();
		}
		return null;
	}
	
	
	public static String tratarCampoNumeric(Cell cell) {
		if(cell != null){
			Double numeric = cell.getNumericCellValue();
			Long longValue = numeric.longValue();
			return  longValue.toString();
		}
		return null;
	}
	
	
	public static String tratarCampoFuncionarioMatricula(Cell cell) {
		if(cell != null && cell.getStringCellValue() != null && StringUtils.isNotEmpty(cell.getStringCellValue().trim()))
			return  cell.getStringCellValue().substring(0, 6);
		return null;
	}
	
	
	public static String tratarCampoFuncionarioNome(Cell cell) {
		if(cell != null && cell.getStringCellValue() != null && StringUtils.isNotEmpty(cell.getStringCellValue().trim()))
			return  cell.getStringCellValue().substring(7);
		return null;
	}
	

	public static boolean linhaInvalida(Row row) {
		return row.getRowNum() < 18
				|| row.getCell(1) == null;
				//|| StringUtils.isEmpty(row.getCell(1).getStringCellValue())
			
	}
	
}

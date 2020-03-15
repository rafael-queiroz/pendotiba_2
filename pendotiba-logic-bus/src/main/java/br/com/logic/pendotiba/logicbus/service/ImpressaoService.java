package br.com.logic.pendotiba.logicbus.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

@Service
public class ImpressaoService {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	ServletContext c;
	
	
	public void imprimir(HashMap<String, Object> parametros, String caminho, HttpServletResponse response, List<?> lista) {
		try {
			String realPath = c.getRealPath("/relatorios");
			parametros.put("SUBREPORT_DIR", realPath.concat("/"));
			
			
			// Pega o arquivo .jasper localizado em resources
			InputStream jasperStream = this.getClass().getResourceAsStream("/relatorios/".concat(caminho).concat(".jasper"));
			
			// Cria o objeto JaperReport com o Stream do arquivo jasper
			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(jasperStream);
			
			// Passa para o JasperPrint o relatório, os parâmetros e a fonte dos dados, no caso uma conexão ao banco de dados
			JasperPrint jasperPrint = new JasperPrint();
			if (lista == null)
				jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, dataSource.getConnection());
			else
				jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(lista));
			
			// Configura a respota para o tipo PDF
			response.setContentType("application/pdf");
	
			// Define que o arquivo pode ser visualizado no navegador e também nome final do arquivo
			// para fazer download do relatório troque 'inline' por 'attachment'
			response.setHeader("Content-Disposition", "inline; filename=".concat(caminho).concat(".pdf"));
			
	
			// Faz a exportação do relatório para o HttpServletResponse
			final OutputStream outStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

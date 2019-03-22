package br.com.project.commons.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConversorTechware {
	public static void main(String[] args) {
//		String afast = "1;10;project TRANSP URGENTES LTDA-JOI;1000048;GILMARA BAGNHUK DA TRINDADE;BE;0 - Não Informado;09/02/2008;18/04/2008;Auxilio Doença;69;;Não;Não;;;;;;;;;;;;;;";
//		System.out.println("Colunas do Afastamento => " + afast.split(";").length);
//		
//		afast = "1;12;project TRANSP URGENTES LTDA-BAU;120005;GERSON PEREIRA DOS SANTOS;BE;;17/07/2007;22/12/2007;Auxilio Doença;158;;;;;;;;;;;;;;;;;;";
//		System.out.println("Colunas do Afastamento Novo => " + afast.split(";").length);
//		
//		String ferias = "1;73;project TRANSP URGENTES LTDA-IMP;73046;GRICIANE ASSUNCAO DE ARAUJO;03/05/10 a 02/05/11;30,00;01/07/2011;0;0,00;01/06/11 a 30/06/11;01/06/11 a 30/06/11;01/06/11 a 30/06/11;01/;01/;01/06/2011;01/06/11 a 30/0;01/06/11 a 30/06/11;;01/06;01/06/11 a;;01/06/11 a 30/06/11;;01/06/11 a;01/06/11 a 30/06/11";
//		System.out.println("Colunas do Ferias => " + ferias.split(";").length);	
//		
//		ferias = "1;73;project TRANSP URGENTES LTDA-IMP;73046;GRICIANE ASSUNCAO DE ARAUJO;03/05/10 a 02/05/11;30,00;01/07/2011;0;0,00;01/06/11 a 30/06/11;;;;;;;;;;;;;;;;";
//		System.out.println("Colunas do Ferias Novo => " + ferias.split(";").length);	
//
//		String transf = "1;01;project TRANSP URGENTES LTDA-MTZ;100001;URUBATAN HELOU;01/05/2007;01;project TRANSP URGENTES LTDA-MTZ;01001;PRESIDENCIA;;;;;;;;;;;;;;;;;;";
//		System.out.println("Colunas do Transferencia => " + transf.split(";").length);			
//	
//		transf = "1;73;project TRANSP URGENTES LTDA-IMP;73046;GRICIANE ASSUNCAO DE ARAUJO;03/05/2010;73;project TRANSP URGENTES LTDA-IMP;04503;ADM VENDAS;";
//		System.out.println("Colunas do Transferencia => " + transf.split(";").length);			
		
		
		tratarAfastamentos();
		tratarTransferencia();
		tratarFerias();

		System.out.println("Arquivos Processados");
	}

	private static void tratarFerias() {
		try {
			int totalColunas = 50;
			List<String> afastamentos = FileUtil.getLines("C://Users//marcussoares-sao//Desktop//Novo//Histórico de Férias.txt", "windows-1252");
			List<String> afastamentoFixed = new ArrayList<String>();
			for (String afastamento : afastamentos) {
				String[] colunas = afastamento.split(";");
				String out = "";
				for (int i = 0; i < colunas.length; i++) {
					out += colunas[i] + ";";
				}
				
				int tmp = out.split(";").length;
				if (tmp < totalColunas) {
					for(int i = 0; i < totalColunas-tmp;i++) {
						out += ";";
					}
				}
				afastamentoFixed.add(out + "\n");
			}
			FileUtil.wipeFile("c://temp/ferias.txt");
			FileUtil.writeToFile("c://temp/ferias.txt", afastamentoFixed);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	private static void tratarTransferencia() {
		try {
			//Colunas do Transferencia => 10
			int totalColunas = 50;
			List<String> afastamentos = FileUtil.getLines("C://Users//marcussoares-sao//Desktop//Novo//Histórico de Tranferências.txt", "windows-1252");
			List<String> afastamentoFixed = new ArrayList<String>();
			for (String afastamento : afastamentos) {
				String[] colunas = afastamento.split(";");
				String out = 
					colunas[0] + ";" + 
					colunas[1] + ";" + 
					colunas[2] + ";" + 
					colunas[3] + ";" + 
					colunas[4] + ";" +
					colunas[6] + ";" +
					colunas[1] + ";" + 
					colunas[2] + ";" +
					colunas[7] + ";" +
					colunas[5] + ";"; 
				
				int tmp = out.split(";").length;
				if (tmp < totalColunas) {
					for(int i = 0; i < totalColunas-tmp;i++) {
						out += ";";
					}
				}
				
				afastamentoFixed.add(out + "\n");
			}
			FileUtil.wipeFile("c://temp/transferencia.txt");
			FileUtil.writeToFile("c://temp/transferencia.txt", afastamentoFixed);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void tratarAfastamentos() {
		try {
			int totalColunas = 50;
			List<String> afastamentos = FileUtil.getLines("C://Users//marcussoares-sao//Desktop//Novo//Histórico de Afastamentos.txt", "windows-1252");
			List<String> afastamentoFixed = new ArrayList<String>();
			for (String afastamento : afastamentos) {
				String[] colunas = afastamento.split(";");
				String out = 
					colunas[0] + ";" + 
					colunas[1] + ";" + 
					colunas[2] + ";" + 
					colunas[3] + ";" + 
					colunas[4] + ";" + 
					colunas[7] + ";" + 
					 ";" +
					colunas[5] + ";" + 
					colunas[6] + ";" + 
					colunas[8] + ";" ;
				if (colunas.length < 10 ) {
					out +=";" ;
				} else {
					out += colunas[9] + ";";
				}
				
				int tmp = out.split(";").length;
				if (tmp < totalColunas) {
					for(int i = 0; i < totalColunas-tmp;i++) {
						out += ";";
					}
				}
				
				afastamentoFixed.add(out + "\n");
			}
			FileUtil.wipeFile("c://temp/afastamentos.txt");
			FileUtil.writeToFile("c://temp/afastamentos.txt", afastamentoFixed);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

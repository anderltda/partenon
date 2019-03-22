package br.com.project.commons.util;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

public class ImpressaoUtil {

	public static final String EPL = "N\nQ800,24\nq800\nD7\nS2\nR15,3\nI8,A\nX133,2,4,243,790\nX133,450,4,243,790\nX133,2,4,243,220\n" + "X301,270,4,373,790\nX370,270,4,479,790\nX400,2,4,479,272\nA148,445,3,2,1,1,N,\"123456789\"\nA170,445,3,2,1,1,N,\"123456789\"\nA192,445,3,2,1,1,N,\"123456789\"\n" + "A214,445,3,2,1,1,N,\"123456789\"\nA148,330,3,2,1,1,N,\"123456789\"\nA170,330,3,2,1,1,N,\"123456789\"\nA192,330,3,2,1,1,N,\"123456789\"\n" + "A214,330,3,2,1,1,N,\"123456789\"\nA141,784,3,2,1,1,R,\" NF: \"\nA139,214,3,2,1,1,R,\" Vol: \"\nA246,784,3,2,1,1,R,\" Rem: \"\nA307,784,3,2,1,1,R,\" Dest: \"\n" + "A260,265,3,2,1,1,N,\" Origem -  \"\nX5,2,4,788,790\nX725,2,4,788,790\nX725,2,4,788,400\nA738,370,3,4,2,2,N,\"project\"\nA314,264,3,2,1,1,N,\" Viagem: \"\n" + "A344,268,3,4,2,2,N,\"CPQ-1\"\nA25,630,3,5,2,2,N,\"CPQ\"\nA25,390,3,5,2,1,N,\"005\"\nA25,150,3,2,2,2,N,\"C-075\"\nA20,770,3,1,1,1,N,\"Destino/Sigla:\"\n" + "A52,750,3,5,1,1,N,\"005\"\nLE9,410,124,377\nA314,82,3,2,1,1,N,\"Rampa\"\nA408,266,3,2,1,1,R,\" FROTA: \"\nA452,780,3,4,1,1,N,\"CEP:\"\n" + "A452,500,3,4,1,1,N,\"ALPHA-CPQ\"\nA173,780,3,5,1,1,N,\"123456789\"\nA173,200,3,3,3,2,N,\"001/001\"\nA266,780,3,2,2,1,N,\"VALDAC LTDA\"\n" + "A260,160,3,4,1,1,N,\"SAO\"\nA333,780,3,2,2,1,N,\"VALDAC LTDA\"\nA344,82,3,4,2,2,N,\"15\"\nA385,780,3,3,2,2,N,\"CAMPINAS-SP\"\n" + "A452,704,3,4,1,1,N,\"13094-691\"\nA434,255,3,3,2,2,N,\"2130\"\nB485,700,3,1C,4,6,210,N,\"0000641118170001\"\nA705,700,3,2,1,1,N,\"0000641118170001\"\n" + "A705,460,3,2,1,1,R,\" Reimpressao \"\nA705,285,3,2,1,1,R,\"12/01 10:32:48:531\"\nA731,785,3,2,1,1,R,\" Obs.:\"\nA737,700,3,2,1,1,N,\"\"\n" + "A757,770,3,2,1,1,N,\"\"\nLE433,273,45,250\nLE244,6,58,266\nLE306,95,94,175\nLW1,680,1,1\nP1\n";

	public static final String MASCARA_ZPL = "^XA^LH35,0^PRD^PON^COY^LL800^FO70,21^GB710,780,4^FS^FO653,21^GB128,374,96^FS^FO550,21^GB105,517,4^FS^FO550,21^GB105,235,4^FS" + "^FO550,536^GB105,265,4^FS^FO485,21^GB68,517,4^FS^FO420,21^GB68,517,4^FS^FO312,21^GB111,517,4^FS^FO486,536^GB64,264,40^FS^FO395,536^GB20,180,89^FS^FO312,536^GB86,264,4^FS" + "^FO630,26^GB20,55,15^FS^A0R,18,18^FO629,38^FR^CI0^FDNF:^FS^FO630,540^GB22,55,15^FS^A0R,18,18^FO629,547^FR^CI0^FDVol:^FS^FO530,26^GB20,65,15^FS^A0R,18,18^FO529,38^FR^CI0^FDRem:^FS" + "^FO466,26^GB20,65,15^FS^A0R,18,18^FO465,38^FR^CI0^FDDest:^FS^A0R,18,18^FO511,542^FR^CI0^FDOrigem - ^FS^FO367,536^GB20,75,15^FS^A0R,18,18^FO366,541^FR^CI0^FDFROTA:^FS^A0R,28,38^FO320,38^CI0^FDCEP:^FS" + "^A0R,20,20^FO460,726^FR^CI0^FDRAMPA^FS^FO52,26^GB20,55,15^FS^A0R,18,18^FO52,36^FR^CI0^FDObs.:^FS^ISBRP1.GRF,Y^FS^XZ";

	public static final String ZPL = "^XA^LH0,0^PRD^PON^LL800^COY^ILBRP1.GRF^FS^AA0R,18,22^FO765,31^FR^CI0^FDDestino / Sigla :^FS^A0R,60,60^FO700,56^FR^CI0^FD122^FS" + "^A0R,130,110^FO680,190^FR^CI0^FDBTB^FS^A0R,90,80^FO675,401^CI0^FD503A^FS^A0R,70,40^FO678,651^CI0^FDZ-999^FS^A0R,80,70^FO580,36^CI0^FD000489^FS^A0R,80,58^FO580,546^CI0^FD001/001^FS" + "^A0R,30,26^FO532,27^CI0^FDRESTOQUE COMERCIO E CONFECCOES DE^FS^A0R,30,30^FO537,628^FR^CI0^FDSJK^FS^A0R,30,26^FO461,27^CI0^FDRESTOQUE COMERCIO E CONFECCOES DE^FS^FO37,22^GB73,390,4^FS" + "^FO37,22^GB73,780,4^FS^A0R,55,65^FO37,431^FR^CI0^FDproject^FS^A0R,18,18^FO495,541^FR^CI0^FDViagem^FS^A0R,65,55^FO420,541^FR^CI0^FDBTB-1^FS^A0R,65,55^FO420,731^FR^CI0^FD8^FS" + "^A0R,48,58^FO398,27^CI0^FDSAO PAULO-SP^FS^A0R,28,38^FO355,126^CI0^FD05312-000^FS^FO350,321^GB40,215,20^FS^A0R,28,38^FO353,335^CI0^FR^FDZETA-BTB^FS^A0R,48,58^FO345,566^CI0^FD1456^FS" + "^BY4^FO135,111^BCR,210,N,N,N^FN1^FS^A0R,20,25^FO110,111^CI0^FN2^FS^FN1^FD>;0000666335780001^FS^FN2^FD0000666335780001^FS^FO112,306^GB18,145,15^FS^A0R,18,22^FO110,312^CI0^FR^FDReimpressao^FS" + "^FO112,455^GB18,340,15^FS^A0R,18,22^FO110,461^CI0^FR^FDMARCELO_VM-02/09 14:44:31:266^FS^A0R,30,26^FO74,86^CI0^FD^FS^A0R,30,26^FO40,29^CI0^FD^FS^XZ";

	public static void imprimir(InputStream stream) {

		try {

			DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;

			Doc mydoc = new SimpleDoc(stream, flavor, null);

			PrintService[] services = PrintServiceLookup.lookupPrintServices(flavor, null);
			PrintRequestAttributeSet attributes = new HashPrintRequestAttributeSet();
//			PrintService service =  ServiceUI.printDialog(null, 50, 50, services, null,	flavor, attributes);
			PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();

			if (defaultService != null) {
				DocPrintJob job = defaultService.createPrintJob();
				job.print(mydoc, attributes);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static String encodeXPL(String xpl){
		try {
			String encoded = URLEncoder.encode(xpl,"UTF-8");
			return encoded;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static String decodeXPL(String xpl){
		try {
			String decoded = URLDecoder.decode(xpl,"UTF-8");
			return decoded;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	
	public static void imprimir(String print) {

		try {

			InputStream stream = new ByteArrayInputStream(print.getBytes());

			DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;

			Doc mydoc = new SimpleDoc(stream, flavor, null);

			PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();

			if (defaultService != null) {
				DocPrintJob job = defaultService.createPrintJob();
				job.print(mydoc, null);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void cupomBematech(String recebedor, Map<String, String> map) {

		StringBuilder builder = new StringBuilder();
		builder.append("                    ");
		builder.append("project");
		builder.append("\n");
		builder.append("        ");
		builder.append("Comprovante de retirada de malote");
		builder.append("\n");
		builder.append("           ");
		builder.append("Data: " + DateTimeUtil.formatDate(new Date()) + " " + "Hora: " + DateTimeUtil.formatTime(new Date()));
		builder.append("\n");
		builder.append("          ");
		builder.append("Retirado por: ");
		builder.append(StringUtil.getNonAccentString(recebedor));
		builder.append("\n\n");
		builder.append("   ");
		builder.append("N Envelope");
		builder.append("    ");
		builder.append("Destinatario");
		builder.append("\n");

		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			Object key = it.next();
			Object item = map.get(key);
			Integer value = 10 - key.toString().length();
			builder.append("   ");

			for (int i = 0; i < value; i++)
				builder.append("0");

			builder.append(key);

			builder.append("    ");

			if (item != null && item.toString().length() > 30) {
				builder.append(StringUtil.getNonAccentString(item.toString()).substring(0, 30));
			} else {
				builder.append(StringUtil.getNonAccentString(item.toString()));
			}

			builder.append("\n");
		}

		builder.append("\n");
		builder.append("        ");
		builder.append(map.size());
		builder.append(" Envelopes");
		builder.append("\n\n\n\n");
		builder.append("        ");
		builder.append("___________________________________");
		builder.append("\n");
		builder.append("                  ");
		builder.append(StringUtil.getNonAccentString(recebedor));
		builder.append("\n");
		builder.append("\n\n\n\n\n\n\n\n\n");

		ImpressaoUtil.imprimir(builder.toString());
	}

	public static StringBuilder getEtiquetaEPL(Integer idFilialOrigem, String siglaFilialOrigem, Integer idFilialDestino, String siglaFilialDestino, Integer nroMalote){
		StringBuilder builder = new StringBuilder();
		
		builder.append("N\n");
		builder.append("Q800,24\n");
		builder.append("q800\n");
		builder.append("D7\n");
		builder.append("S2\n");
		builder.append("R15,3\n");
		builder.append("I8,A\n");
		builder.append("A90,730,3,2,2,1,N,\"" + "Origem :\"\n");
		builder.append("A90,620,3,2,2,1,N,\"" + siglaFilialOrigem + "\"\n");
		builder.append("A90,560,3,2,2,1,N,\"" + "Data :\"\n");
		builder.append("A90,480,3,2,2,1,N,\"" + DateTimeUtil.formatDateTime(new Date(), "dd/MM/yyyy HH:mm")+"\"\n");
		builder.append("A190,730,3,2,2,1,N,\"" + "Destino :\"\n");
		builder.append("A190,600,3,5,5,5,N,\"" + siglaFilialDestino + "\"\n");
		builder.append("A500,730,3,2,2,1,N,\"" + "Lacre :\"\n");
		builder.append("A500,620,3,2,2,1,N,\"" + nroMalote.toString() + "\"\n");
		builder.append("B485,470,3,2C,4,6,210,N,\"" + idFilialOrigem.toString() + idFilialDestino.toString() + nroMalote.toString() + "\"\n");
		builder.append("A705,470,3,2,1,1,N,\"" + idFilialOrigem.toString() + idFilialDestino.toString() + nroMalote.toString()+"\"\n");
		builder.append("P1\n");
		
		return builder;
	}
	
	
	public static void imprimeTextoLPT1(String texto){
		FileOutputStream out = null;
		PrintStream ps = null;
		
		try{
			try {
				out = new FileOutputStream("LPT1:");
				ps = new PrintStream(out);				   
				ps.print(texto);
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				   ps.close();
				   out.close();			
			}
			
		}catch (IOException ex){
			ex.printStackTrace();
		}
		
	}
	
	
	public static void EtiquetaMaloteEPL(Integer idFilialOrigem, String siglaFilialOrigem, Integer idFilialDestino, String siglaFilialDestino, Integer nroMalote) {

		
		StringBuilder builder = ImpressaoUtil.getEtiquetaEPL(idFilialOrigem, siglaFilialOrigem, idFilialDestino, siglaFilialDestino, nroMalote); 
		ImpressaoUtil.imprimir(builder.toString());

	}

	public static void main(String[] args) {

		Map<String, String> map = new LinkedHashMap<String, String>();

		for (int i = 0; i < 40; i++) {
			map.put(StringUtil.getString(i), "João da Silva Pereira de Sousa");
		}

		ImpressaoUtil.cupomBematech("Fulano de Tal T.I", map);
	}

	
	
	
}

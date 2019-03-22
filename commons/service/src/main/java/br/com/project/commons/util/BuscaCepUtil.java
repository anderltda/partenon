package br.com.project.commons.util;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.beanutils.BeanUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import br.com.project.commons.cadastro.entity.Endereco;

/**
 * @author anderson.nascimento
 *
 */
public class BuscaCepUtil {
	
	public static final String BUSCA_ENDERECO_1 = "http://cep.republicavirtual.com.br/web_cep.php?formato=xml&cep=";
	public static final String BUSCA_ENDERECO_2 = "http://www.qualocep.com/busca-cep/";
	public static final String BUSCA_ENDERECO_3 = "http://maps.googleapis.com/maps/api/geocode/xml?language=pt-BR&sensor=true&address=";	

	public static void main(String[] args) {

		try {
			
/*			String cep = "02610110";
			
			System.out.println(getLatLong("02474130"));
			System.out.println(getLatLong("02610110"));
			System.out.println(getLatLong("02610000"));
			System.out.println(getLatLong("02474120"));*/
			
			//Endereco endereco = buscaEndereco1(cep);
			
			//Endereco endereco = buscaEndereco2(cep);
			
/*			if(endereco.isEnderecoCompleto()) {
				System.out.println(endereco.getCep());
				System.out.println(endereco.getLogradouro());
				System.out.println(endereco.getBairro());
				System.out.println(endereco.getCidade());
				System.out.println(endereco.getUf());
			}*/
			
			
			System.out.println(getLatLong("02612180"));
			//System.out.println(getLatLong("03633110"));
			
			/*
			System.out.println(getLatLong("02717000"));
			System.out.println(getLatLong("02861190"));
			System.out.println(getLatLong("02882160"));
			System.out.println(getLatLong("02312020"));
			System.out.println(getLatLong("02431020"));
			
			System.out.println(getLatLong("02464000"));
			System.out.println(getLatLong("02464100"));
			System.out.println(getLatLong("02464200"));
			System.out.println(getLatLong("02465970"));
			System.out.println(getLatLong("03222060"));
			*/

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static Endereco buscaEndereco1(String cep) throws Exception {

		JAXBContext jc = JAXBContext.newInstance(Webservicecep.class);

		Unmarshaller u = jc.createUnmarshaller();
		URL url = new URL(BUSCA_ENDERECO_1 + cep);
		Webservicecep wCep = (Webservicecep) u.unmarshal(url);
		Endereco endereco = new Endereco();
		endereco.setCep(cep);
		
		BeanUtils.copyProperties(endereco, wCep);		
		endereco.setLogradouro(wCep.getTipo_logradouro() +" "+ wCep.getLogradouro());
		
		if(endereco.isEnderecoCompleto()) {
			System.out.println(endereco.getCep());
			System.out.println(endereco.getLogradouro());
			System.out.println(endereco.getBairro());
			System.out.println(endereco.getCidade());
			System.out.println(endereco.getUf());
		}

		return endereco;
	}
	
	public static Endereco buscaEndereco2(String cep) throws Exception {
		
		Endereco endereco = new Endereco();		
		endereco.setCep(cep);
		endereco.setLogradouro(getLogradouro(cep));
		endereco.setBairro(getBairro(cep));
		endereco.setCidade(getCidade(cep));
		endereco.setUf(getUF(cep));		
		
		return endereco;
	}
	
	private static String getLogradouro(String cep) throws IOException {

		// ***************************************************
		try {

			Document doc = Jsoup.connect(BUSCA_ENDERECO_2 + cep).timeout(120000).get();
			
			Elements urlPesquisa = doc.select("span[itemprop=streetAddress]");
			
			for (Element urlEndereco : urlPesquisa) {
				System.out.println("Logradouro - " + urlEndereco.text());
				return urlEndereco.text();
			}

		} catch (SocketTimeoutException ex) {

		} catch (HttpStatusException ex) {

		}
		return cep;
	}

	private static String getBairro(String cep) {

		// ***************************************************
		try {

			Document doc = Jsoup.connect(BUSCA_ENDERECO_2 + cep).timeout(120000).get();
			
			Elements urlPesquisa = doc.select("td:gt(1)");
			
			for (Element urlBairro : urlPesquisa) {
				System.out.println("Bairro - " + urlBairro.text());
				return urlBairro.text();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} 
		
		return cep;
	}

	private static String getCidade(String cep) throws IOException {

		// ***************************************************
		try {

			Document doc = Jsoup.connect(BUSCA_ENDERECO_2 + cep).timeout(120000).get();
			
			Elements urlPesquisa = doc.select("span[itemprop=addressLocality]");
			
			for (Element urlCidade : urlPesquisa) {
				System.out.println("Cidade - " + urlCidade.text());
				return urlCidade.text();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} 
		
		return cep;
	}

	private static String getUF(String cep) throws IOException {

		// ***************************************************
		try {

			Document doc = Jsoup.connect(BUSCA_ENDERECO_2 + cep).timeout(120000).get();
			
			Elements urlPesquisa = doc.select("span[itemprop=addressRegion]");
			
			for (Element urlUF : urlPesquisa) {
				System.out.println("UF - " + urlUF.text());
				return urlUF.text();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} 
		
		return cep;
	}

	private static String getLatLong(String cep) throws IOException {

		// ***************************************************
		try {

			if (cep.contains("-")) {
				
				Document doc = Jsoup.connect(BUSCA_ENDERECO_3 + cep).timeout(120000).get();
				Elements lat = doc.select("geometry").select("location").select("lat");
				Elements lng = doc.select("geometry").select("location").select("lng");
				
				for (Element Latitude : lat) {
					for (Element Longitude : lng) {
						return Latitude.text() + "," + Longitude.text();
					}
				}
				
			} else {

				StringBuilder cepHif = new StringBuilder(cep);
				
				cepHif.insert(cep.length() - 3, '-');

				Document doc = Jsoup.connect(BUSCA_ENDERECO_3 + cepHif).timeout(120000).get();
				
				Elements lat = doc.select("geometry").select("location").select("lat");
				Elements lng = doc.select("geometry").select("location").select("lng");
				
				for (Element Latitude : lat) {
					for (Element Longitude : lng) {
						return Latitude.text() + "," + Longitude.text();
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} 
		
		return cep;
	}

}

package br.com.project.entity;

import org.junit.Before;
import org.junit.Test;

import br.com.project.configuracao.persistencia.CfgConstraint;
import br.com.project.configuracao.persistencia.CfgEntidade;
import br.com.project.configuracao.persistencia.CfgEntidadeDetalhe;
import br.com.project.configuracao.persistencia.CfgTela;
import br.com.project.endereco.persistencia.EndBairro;
import br.com.project.endereco.persistencia.EndFaixaCep;
import br.com.project.endereco.persistencia.EndLocalidade;
import br.com.project.endereco.persistencia.EndLogradouro;
import br.com.project.endereco.persistencia.EndPais;
import br.com.project.endereco.persistencia.EndRegiao;
import br.com.project.endereco.persistencia.EndUf;
import br.com.project.endereco.persistencia.Endereco;
import br.com.project.service.ServiceException;
import br.com.project.test.spring.StandAloneWireTest;
import br.com.project.tipos.persistencia.TpoCep;
import br.com.project.tipos.persistencia.TpoEndereco;
import br.com.project.tipos.persistencia.TpoLocalidade;
import br.com.project.tipos.persistencia.TpoLogradouro;
import br.com.project.tipos.persistencia.TpoTelefone;

import com.sun.org.apache.xml.internal.security.utils.I18n;

public class TesteCommonsEntity extends StandAloneWireTest {

	public static boolean loaded = false;

	@Before
	public void setUp() throws Exception {
		if (loaded)
			return;
		loaded = true;
		try {
			commomService.findAll(I18n.class);
			commomService.findAll(TpoEndereco.class);
			commomService.findAll(TpoCep.class);
			commomService.findAll(TpoLocalidade.class);
			commomService.findAll(TpoLogradouro.class);
			commomService.findAll(TpoTelefone.class);

			commomService.findAll(CfgConstraint.class);
			commomService.findAll(CfgEntidade.class);
			commomService.findAll(CfgEntidadeDetalhe.class);
			commomService.findAll(CfgTela.class);

			commomService.findAll(EndBairro.class);
			commomService.findAll(Endereco.class);
			commomService.findAll(EndFaixaCep.class);
			commomService.findAll(EndLocalidade.class);
			commomService.findAll(EndLogradouro.class);
			commomService.findAll(EndPais.class);
			commomService.findAll(EndRegiao.class);
			commomService.findAll(EndUf.class);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Test
	public void Teste() throws NumberFormatException, ServiceException {
		System.out.println("Teste de Entidades.");
		
		I18n i18 = commomService.getByID(I18n.class, Short.valueOf("1"));
		EndPais p = new EndPais();
		p.setI18n(i18);
		p.setNome("Brasil");
		p.setOrdem(Short.valueOf("1"));
		p.setSigla("BR");
		try {
			commomService.create(p);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}

}

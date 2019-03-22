package br.com.project.foundation.view;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.project.foundation.util.ConfigWebHelper;

/**
 * @author anderson.nascimento
 *
 */
@ManagedBean
@SessionScoped
public class ConfigView implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public int getTotalItensPerPage() {
		return ConfigWebHelper.getDefaultRowsPerPage();
	}
	
	public String getBasicPackage() {
		return ConfigWebHelper.getBasicPackage();
	}

}

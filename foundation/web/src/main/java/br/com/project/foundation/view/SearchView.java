package br.com.project.foundation.view;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.context.RequestContext;

import br.com.project.commons.annotation.PosSearch;
import br.com.project.commons.annotation.PreSearch;
import br.com.project.commons.annotation.Query;
import br.com.project.commons.util.AnnotationUtil;
import br.com.project.commons.util.BaseObject;
import br.com.project.commons.util.dto.SelectItemDTO;
import br.com.project.foundation.custom.CustomLazyDataModel;
import br.com.project.foundation.exception.ServiceException;
import br.com.project.foundation.util.MessageHelper;

/**
 * @author anderson.nascimento
 *
 * @param <T>
 */
public abstract class SearchView<T extends BaseObject> extends BaseBeanView<T> {

	private static final long serialVersionUID = 1L;

	private static final Log LOG = LogFactory.getLog(SearchView.class);

	/**
	 * @return
	 */
	public String search() {

		try {

			AnnotationUtil.invoke(this, PreSearch.class);

			processValidate();
			
			processSearch();
						
			AnnotationUtil.invoke(this, PosSearch.class);
			
		} catch (Exception ex) {
			
			ex.printStackTrace();
			
			LOG.error(ex);
			
			MessageHelper.addErrorMessage(ex.getLocalizedMessage());
		}		
		
		return getSearchPage();
	}

	/**
	 * @throws ServiceException
	 */
	public void processSearch() throws ServiceException {

		Annotation annotation = AnnotationUtil.getAnnotation(this.getClass(), Query.class);			

		Query query = (Query) annotation;
		
		buildFilters();
						
		if(query != null) {
			list = new CustomLazyDataModel<T>(getService(), filters, query.value());			
		} else {
			list = new CustomLazyDataModel<T>(getService(), filters, getBeanClass());
		}		
	
	}
	
	/**
	 * Método responsável por fazer validação antes de ser submitido os valores a query.
	 * @throws ServiceException
	 */
	public void processValidate() throws ServiceException {}
	
	/**
	 * Método responsável por fazer algo antes da query.
	 * @throws ServiceException
	 */
	public void processBeforeSearch() throws ServiceException {}

	/**
	 * @return
	 */
	public String getSearchPage() {
		return null;
	}
	
	/**
	 * @param component
	 */
	public void executeScript(String script) {
		try {
			RequestContext.getCurrentInstance().execute(script);	
		} catch (Exception e) {}		
	}

	/**
	 * 
	 */
	public List<SelectItemDTO> comboOperators() {
		List<SelectItemDTO> operators = new ArrayList<SelectItemDTO>();
		operators.add(new SelectItemDTO(">", ">"));
		operators.add(new SelectItemDTO(">=", ">="));
		operators.add(new SelectItemDTO("<", "<"));
		operators.add(new SelectItemDTO("<=", "<="));
		operators.add(new SelectItemDTO("=", "="));
		operators.add(new SelectItemDTO("<>", "<>"));		
		return operators;
	}
}

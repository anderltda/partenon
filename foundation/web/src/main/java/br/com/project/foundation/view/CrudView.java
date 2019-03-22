package br.com.project.foundation.view;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.PersistenceException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.exception.ConstraintViolationException;

import br.com.project.commons.annotation.Combo;
import br.com.project.commons.annotation.PosCreate;
import br.com.project.commons.annotation.PosEdit;
import br.com.project.commons.annotation.PosSave;
import br.com.project.commons.annotation.PosView;
import br.com.project.commons.annotation.PreCreate;
import br.com.project.commons.annotation.PreEdit;
import br.com.project.commons.annotation.PreSave;
import br.com.project.commons.annotation.PreView;
import br.com.project.commons.seguranca.entity.Usuario;
import br.com.project.commons.util.AnnotationUtil;
import br.com.project.commons.util.BaseObject;
import br.com.project.commons.util.ReflectionUtil;
import br.com.project.commons.util.StringUtil;
import br.com.project.commons.util.entity.BasicLoggableEntityObject;
import br.com.project.foundation.exception.ServiceException;
import br.com.project.foundation.persistence.BasicEntityObject;
import br.com.project.foundation.util.BeanManager;
import br.com.project.foundation.util.MessageHelper;

/**
 * @author anderson.nascimento
 *
 * @param <T>
 */
public abstract class CrudView<ID extends Serializable, T extends BaseObject> extends ProvidedView<T> {

	private static final long serialVersionUID = 1L;	

	private static final Log LOG = LogFactory.getLog(CrudView.class);

	protected boolean disabled;
	
	protected String usuarioCriacao;
	protected String usuarioAlteracao;

	@Override
	protected void initBeanManager() {
		beanManager = new BeanManager<T>(getModuleName(), ReflectionUtil.getClassByParameterizedType(this.getClass(), 1), ReflectionUtil.getClassByParameterizedType(this.getClass(), 1));		
	}

	/**
	 * @return
	 */
	public String create() {

		try {

			AnnotationUtil.invoke(this, PreCreate.class);

			newBeanInstance();

			AnnotationUtil.invoke(this, PosCreate.class);

		} catch (Exception ex) {

			LOG.error("Erro no method create", ex);

			MessageHelper.addErrorMessage(ex.getLocalizedMessage());			

			return null;
		}

		disabled = false;

		initCollection();

		return getCreatePage();
	}

	/**
	 * @return
	 */
	public String view() {

		try {

			if (bean == null || bean.getId() == null) {
				MessageHelper.addErrorMessage("message_error_invalid_bean");
				return null;
			}

			AnnotationUtil.invoke(this, PreView.class);

			executeView();

			AnnotationUtil.invoke(this, PosView.class);
			
			datasAltCriaViewEdit();

			beanReferences();			

		} catch (Exception ex) {

			LOG.error("Erro no method view", ex);

			MessageHelper.addErrorMessage(ex.getLocalizedMessage());

			return null;
		}

		disabled = true;

		return getViewPage();
	}

	/**
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	protected void executeView() throws ServiceException {
		bean = getService().getByID(getBeanClass(), (ID) bean.getId());
	}

	/**
	 * @return
	 */
	public String edit() {

		try {

			AnnotationUtil.invoke(this, PreEdit.class);

			if (bean == null || bean.getId() == null) {
				MessageHelper.addErrorMessage("message_error_invalid_bean");
				return null;
			}

			executeEdit();

			datasAltCriaViewEdit();

			AnnotationUtil.invoke(this, PosEdit.class);

			beanReferences();			

		} catch (Exception ex) {

			LOG.error("Erro no method edit", ex);

			MessageHelper.addErrorMessage(ex.getLocalizedMessage());

			return null;
		}

		disabled = false;

		return getEditPage();
	}

	/**
	 * @throws ServiceException
	 */
	@SuppressWarnings("unchecked")
	protected void executeEdit() throws ServiceException {
		bean = getService().getByID(getBeanClass(), (ID) bean.getId());
	}

	/**
	 * @return
	 */
	public String save() {

		try {
			
			beanReferences();			

			AnnotationUtil.invoke(this, PreSave.class);

			executeSave();

			MessageHelper.addInfoMessage("message_salvar_success");

			AnnotationUtil.invoke(this, PosSave.class);

		} catch (Exception ex) {

			LOG.error("Erro no method save", ex);

			MessageHelper.addErrorMessage(ex.getLocalizedMessage());

			return null;
		}

		return getSavePage();
	}

	/**
	 * @return
	 */
	protected String getSavePage() {
		return search();
	}

	/**
	 * @throws ServiceException
	 */
	protected void executeSave() throws ServiceException {
		bean = getService().save(bean);
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String delete() {

		try {

			getService().delete(getBeanClass(), (ID) bean.getId());

			bean = null;

			MessageHelper.addInfoMessage("message_deletar_success");

		} catch (Exception ex) {
			LOG.error("Erro no method delete", ex);
			if(ex instanceof PersistenceException) {
				PersistenceException exception = ((PersistenceException)ex);
				if(exception.getCause() instanceof ConstraintViolationException) {
					MessageHelper.addErrorMessage("message_deletar_constraint");					
				} else {
					MessageHelper.addErrorMessage(ex.getLocalizedMessage());					
				}
			} else {
				MessageHelper.addErrorMessage(ex.getLocalizedMessage());
			}
		}			

		return getDeletePage();
	}

	/**
	 * @return
	 */
	protected String getDeletePage() {
		return search();
	}	

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	protected void initCollection() {				
		List<Field> fields = ReflectionUtil.getFields(this.getClass(), BaseBeanView.class);		
		for (Field field : fields) {			
			Annotation annotation = AnnotationUtil.getAnnotation(this.getClass(), Combo.class, field.getName());
			if(annotation != null) {				
				try {					
					Combo comboForm = (Combo) annotation;
					List<T> list = (List<T>) getService().getByCondition(comboForm.bean(), new HashMap<String, Object>());
					ReflectionUtil.executeSetterMethod(this, field.getName(), list);					
				} catch (ServiceException e) {
					e.printStackTrace();
				}
			}		
		}
	}


	/**
	 * Instancia o bean com os seus objectos populados ao visualiza-lo ou edita-lo 
	 * @throws ServiceException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void beanReferences() throws ServiceException {

		List<Field> fields = ReflectionUtil.getFields(bean.getClass(), BasicEntityObject.class);

		String nameField = null;

		Object valueField = null;

		for (Field field : fields) {
			
			if (BasicEntityObject.class.isAssignableFrom(field.getType())) {

				BasicEntityObject<Serializable> objValue = (BasicEntityObject<Serializable>) ReflectionUtil.executeGetterMethod(bean, field.getName());
				
				Serializable serializableId = objValue.getId();
				
				nameField = StringUtil.firstLower(field.getName());
				
				valueField = ReflectionUtil.executeGetterMethod(bean, nameField);

				if(serializableId != null) {
					ReflectionUtil.executeSetterMethod(bean, nameField, getService().getByID(objValue.getConcreteClass(), serializableId));					
				}

				if(valueField instanceof BasicLoggableEntityObject) {		

					BasicLoggableEntityObject basic = (BasicLoggableEntityObject) ReflectionUtil.executeGetterMethod(bean, nameField);

					if(basic.getId() == null) {
						((BasicLoggableEntityObject)basic).setIdUsuarioCriacao(getLoggedUser().getId());
						((BasicLoggableEntityObject)basic).setDataCriacao(new Date());						
					}			

					((BasicLoggableEntityObject)basic).setIdUsuarioAlteracao(getLoggedUser().getId());
					((BasicLoggableEntityObject)basic).setDataAlteracao(new Date());					
				}

			}
		}
		
		if(bean instanceof BasicLoggableEntityObject) {		

			if(bean.getId() == null) {
				((BasicLoggableEntityObject)bean).setIdUsuarioCriacao(getLoggedUser().getId());
				((BasicLoggableEntityObject)bean).setUsuarioCriacao(getService().getByID(Usuario.class, getLoggedUser().getId()));
				((BasicLoggableEntityObject)bean).setDataCriacao(new Date());	
			} 

			((BasicLoggableEntityObject)bean).setIdUsuarioAlteracao(getLoggedUser().getId());
			((BasicLoggableEntityObject)bean).setUsuarioAlteracao(getService().getByID(Usuario.class, getLoggedUser().getId()));
			((BasicLoggableEntityObject)bean).setDataAlteracao(new Date());
		}
		
		initCollection();
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	private void datasAltCriaViewEdit() {
		if(bean instanceof BasicLoggableEntityObject) {
			usuarioCriacao = ((BasicLoggableEntityObject)bean).getUsuarioCriacaoFormatado();						
			usuarioAlteracao = ((BasicLoggableEntityObject)bean).getUsuarioAlteracaoFormatado();
		}		
	}

	/**
	 * @return
	 */
	public boolean getDisabled() {
		return disabled;
	}

	/**
	 * @param field
	 * @param value
	 * @param errorLabel
	 */
	protected void validateExists(String field, Object value, String errorLabel) {}

	/**
	 * 
	 */
	public void clearBeanFilter() {
		newBeanFilterInstance();
		clearFilters();
		list = null;
	}

	/**
	 * @return
	 */
	public abstract String getEditPage();

	/**
	 * @return
	 */
	public abstract String getCreatePage();

	/**
	 * @return
	 */
	public abstract String getViewPage();

	public String getUsuarioCriacao() {
		return usuarioCriacao;
	}

	public void setUsuarioCriacao(String usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}

	public String getUsuarioAlteracao() {
		return usuarioAlteracao;
	}

	public void setUsuarioAlteracao(String usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}	
}
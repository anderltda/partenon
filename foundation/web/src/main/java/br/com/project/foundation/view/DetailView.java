package br.com.project.foundation.view;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.project.commons.annotation.PreSave;
import br.com.project.commons.annotation.PreSearch;
import br.com.project.commons.util.BaseObject;
import br.com.project.commons.util.ReflectionUtil;
import br.com.project.commons.util.StringUtil;
import br.com.project.foundation.exception.ServiceException;
import br.com.project.foundation.util.BeanManager;

/**
 * @author anderson.nascimento
 *
 * @param <T>
 */
public abstract class DetailView<ID extends Serializable, T extends BaseObject, M extends BaseObject, D extends BaseObject> extends CrudView<ID, T> {

	private static final long serialVersionUID = 1L;	

	private static final Log LOG = LogFactory.getLog(DetailView.class);

	protected M beanMaster;
	protected BeanManager<M> masterBeanManager;
	protected BeanManager<D> detailBeanManager;

	@Override
	protected void initBeanManager() {
		super.initBeanManager();
		masterBeanManager = new BeanManager<M>(getModuleName(), ReflectionUtil.getClassByParameterizedType(this.getClass(), 2), ReflectionUtil.getClassByParameterizedType(this.getClass(), 2));
		detailBeanManager = new BeanManager<D>(getModuleName(), ReflectionUtil.getClassByParameterizedType(this.getClass(), 3), ReflectionUtil.getClassByParameterizedType(this.getClass(), 3));
	}	

	public M getBeanMaster() {
		return beanMaster;
	}

	@SuppressWarnings("unchecked")
	public void setBeanMaster(M beanMaster) {
		if (beanMaster != null && beanMaster.getId() != null) {
			try {
				this.beanMaster = (M) getService().getByID(getMasterClass(), (ID) beanMaster.getId());
			} catch (ServiceException e) {
				LOG.error(e);
			}
		} else {
			this.beanMaster = null;
		}
	}
	
	@PreSearch
	@PreSave
	public void setMasterChildren() {
		if(beanMaster != null) {
	 		String name = beanMaster.getClass().getSimpleName().toLowerCase();
			ReflectionUtil.executeSetterMethod(bean, name, beanMaster);
			ReflectionUtil.executeSetterMethod(beanFilter, name, beanMaster);			
		}
	}

	protected Class<M> getMasterClass() {
		return masterBeanManager.getBeanClass();
	}

	protected String getMasterName() {
		return StringUtil.firstLower(getMasterClass().getSimpleName());
	}

	public BeanManager<M> getMasterBeanManager() {
		return masterBeanManager;
	}

	public void setMasterBeanManager(BeanManager<M> masterBeanManager) {
		this.masterBeanManager = masterBeanManager;
	}

	public BeanManager<D> getDetailBeanManager() {
		return detailBeanManager;
	}

	public void setDetailBeanManager(BeanManager<D> detailBeanManager) {
		this.detailBeanManager = detailBeanManager;
	}	
}
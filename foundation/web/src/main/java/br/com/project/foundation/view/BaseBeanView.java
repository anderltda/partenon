package br.com.project.foundation.view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;

import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.MapModel;

import br.com.project.commons.annotation.Query;
import br.com.project.commons.service.CommonsService;
import br.com.project.commons.util.AnnotationUtil;
import br.com.project.commons.util.BaseObject;
import br.com.project.commons.util.ReflectionUtil;
import br.com.project.commons.util.StringUtil;
import br.com.project.foundation.custom.CustomLazyDataModel;
import br.com.project.foundation.persistence.BasicEntityObject;
import br.com.project.foundation.util.BeanManager;
import br.com.project.foundation.util.BuildCondition;
import br.com.project.foundation.util.MessageHelper;

/**
 * @author anderson.nascimento
 * 
 * @param <T>
 */
public abstract class BaseBeanView<T extends BaseObject> extends BaseView implements Serializable {

	private static final long serialVersionUID = 1L;

	protected T bean;
	protected T beanFilter;
	protected T selected;
	protected MapModel model;
	protected UploadedFile file;
	protected CustomLazyDataModel<T> list;
	protected BeanManager<T> beanManager;
	protected Map<String, Object> filters;

	protected int rows;
	protected String rowsPerPage;
	protected String addSelect;

	public BaseBeanView() {}

	/**
	 * 
	 */
	@PostConstruct
	protected final void loadDefaults() {
		initBeanManager();
		initList();
	}

	protected abstract CommonsService getService();

	/**
	 * 
	 */
	protected void initBeanManager() {
		beanManager = new BeanManager<T>(getModuleName(),
				ReflectionUtil.getClassByParameterizedType(this.getClass(), 0),
				ReflectionUtil.getClassByParameterizedType(this.getClass(), 0));
	}

	/**
	 * 
	 */
	protected void initList() {

		String query = getQuery();

		newBeanFilterInstance();

		newBeanInstance();

		filters = new HashMap<String, Object>();

		buildFilters();

		rows = beanManager.getRows();

		rowsPerPage = "10,20,30";

		if (query != null) {
			list = new CustomLazyDataModel<T>(getService(), filters, query);
		}
	}

	/**
	 * 
	 */
	protected void buildFilters() {	
		filters = BuildCondition.buildMapsEntity(getBeanFilter());
	}	

	/**
	 * @return
	 */
	protected String getQuery() {

		Annotation annotation = AnnotationUtil.getAnnotation(this.getClass(), Query.class);

		Query query = (Query) annotation;

		return query != null ? query.value() : null;
	}
	
	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getPaginationTemplate() {
		return "{CurrentPageReport}  {FirstPageLink} {PreviousPageLink} {PageLinks} {NextPageLink} {LastPageLink} {RowsPerPageDropdown}";
	}

	public String getRowsPerPage() {
		return rowsPerPage;
	}

	public String getEmptyListMessage() {
		return MessageHelper.getMessage("Nenhum registro encontrado!");
	}

	/**
	 * 
	 */
	public void onSelectDialog() {}

	/**
	 * @param selected
	 */
	public void setSelectDialog(Object bean, Object selected) {
		
		String name = null;
		
		if(StringUtil.isNotEmpty(addSelect)) {
			name = StringUtil.firstUpper(addSelect);			
		}
		
		if(selected != null && bean != null) {
			ReflectionUtil.executeSetterMethod(bean, name, selected);			
		}
	}
	
	/**
	 * @param selected
	 */
	public void setSelectDialog(String addSelect, Object bean, Object selected) {
		this.addSelect = addSelect;
		setSelectDialog(bean, selected);
	}
	
	public T getBean() {
		return bean;
	}

	/**
	 * @param contents
	 * @param type
	 * @param nameFile
	 * @param width
	 * @param height
	 */
	protected void viewContentsFile(byte[] contents, String type, String nameFile, int width, int height) {

		try {

			if(contents != null) {
				ServletContext sContext = (ServletContext) FacesContext  
						.getCurrentInstance().getExternalContext().getContext();  

				BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(contents));
				BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = newImg.createGraphics();
				g.drawImage(bufferedImage, 0, 0, width, height, null);
				ImageIO.write(newImg, type, new File(sContext.getRealPath("/resources/images") + File.separator + nameFile));				
			}			

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void setBean(T bean) {
		this.bean = bean;
	}

	public T getBeanFilter() {
		return beanFilter;
	}

	public void setBeanFilter(T beanFilter) {
		this.beanFilter = beanFilter;
	}

	public BeanManager<T> getBeanManager() {
		return beanManager;
	}

	protected Class<T> getBeanClass() {
		return (Class<T>) beanManager.getBeanClass();
	}

	protected Class<T> getBeanFilterClass() {
		return (Class<T>) beanManager.getBeanFilterClass();
	}

	/**
	 * Instancia o bean e seus objectos ao cria-lo
	 */
	protected void newBeanInstance() {
		bean = beanManager.newInstance();
		List<Field> fields = ReflectionUtil.getFields(bean.getClass(), BasicEntityObject.class);
		for (Field field : fields) {
			if (BasicEntityObject.class.isAssignableFrom(field.getType())) {
				ReflectionUtil.executeSetterMethod(bean, StringUtil.firstLower(field.getName()), ReflectionUtil.newInstance(field.getType()));
			}
		}
	}
	
	/**
	 * @param beanName
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	protected <C> C findBean(String beanName) {
		FacesContext context = FacesContext.getCurrentInstance();
		return (C) context.getApplication().evaluateExpressionGet(context, "#{" + beanName + "}", Object.class);
	}

	protected void newBeanFilterInstance() {
		beanFilter = beanManager.newFilterInstance();
	}

	public CustomLazyDataModel<T> getList() {
		return list;
	}

	public void setList(CustomLazyDataModel<T> list) {
		this.list = list;
	}

	public void onRowSelect(SelectEvent event) {
		System.out.println(event.getObject());
	}

	public MapModel getModel() {
		return model;
	}

	public void setModel(MapModel model) {
		this.model = model;
	}

	public T getSelected() {
		return selected;
	}

	public void setSelected(T selected) {
		this.selected = selected;
	}

	public void clearFilters() {
		filters.clear();
	}

	public Map<String, Object> getFilters() {
		return filters;
	}

	public void addFilters(String id, Object value) {
		this.filters.put(id, value);
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}
}

package br.com.project.foundation.util;

import java.io.Serializable;

import br.com.project.commons.util.StringUtil;

/**
 * @author anderson.nascimento
 *
 */
public class GraficHelper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String GRAFICO_LINECHART = "LINECHART";
	public static final String GRAFICO_PIECHART = "PIECHART";
	public static final String GRAFICO_BARCHART = "BARCHART";
	
	private static final String STYLE = "width:500px;height:300px";	
	
	private String type;
	private String title;
	private String value;
	private String style;
	
	// Bar and Linear
	private String seriesColors;
	
	// Bar
	private String orientation;
	private Boolean stacked;

	// Pie
	private Boolean fill;
	
	public GraficHelper() {
		super();
	}
	
	// Linear
	public GraficHelper(String title, String value, String style, String seriesColors) {
		this.type = GRAFICO_LINECHART;
		this.title = StringUtil.isEmpty(title) ? null : title;
		this.value = value;
		this.style = StringUtil.isEmpty(style) ? STYLE : style;
		this.seriesColors = StringUtil.isEmpty(seriesColors) ? null : seriesColors;
	}
	
	//Bar
	public GraficHelper(String title, String value, String style, String seriesColors, String orientation) {
		this.type = GRAFICO_BARCHART;
		this.title = StringUtil.isEmpty(title) ? null : title;
		this.value = value;
		this.style = StringUtil.isEmpty(style) ? STYLE : style;
		this.seriesColors = StringUtil.isEmpty(seriesColors) ? null : seriesColors;
		this.orientation = orientation;
	}
	
	// Pie
	public GraficHelper(String title, String value, String style, String seriesColors, Boolean fill) {
		this.type = GRAFICO_PIECHART;
		this.title = StringUtil.isEmpty(title) ? null : title;
		this.value = value;
		this.style = StringUtil.isEmpty(style) ? STYLE : style;
		this.seriesColors = StringUtil.isEmpty(seriesColors) ? null : seriesColors;
		this.fill = fill;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the style
	 */
	public String getStyle() {
		return StringUtil.isEmpty(style) ? STYLE : style;
	}

	/**
	 * @param style the style to set
	 */
	public void setStyle(String style) {
		this.style = style;
	}

	/**
	 * @return the seriesColors
	 */
	public String getSeriesColors() {
		return seriesColors;
	}

	/**
	 * @param seriesColors the seriesColors to set
	 */
	public void setSeriesColors(String seriesColors) {
		this.seriesColors = seriesColors;
	}

	/**
	 * @return the orientation
	 */
	public String getOrientation() {
		return orientation;
	}

	/**
	 * @param orientation the orientation to set
	 */
	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	/**
	 * @return the stacked
	 */
	public Boolean getStacked() {
		return stacked;
	}

	/**
	 * @param stacked the stacked to set
	 */
	public void setStacked(Boolean stacked) {
		this.stacked = stacked;
	}

	/**
	 * @return the fill
	 */
	public Boolean getFill() {
		return fill;
	}

	/**
	 * @param fill the fill to set
	 */
	public void setFill(Boolean fill) {
		this.fill = fill;
	}	
}

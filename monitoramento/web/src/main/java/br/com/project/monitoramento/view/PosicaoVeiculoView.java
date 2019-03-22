package br.com.project.monitoramento.view;


import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.SlideEndEvent;
import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.extensions.event.CloseEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.Marker;

import br.com.project.cadastro.view.VeiculoView;
import br.com.project.commons.annotation.PreSearch;
import br.com.project.commons.annotation.Query;
import br.com.project.commons.cadastro.entity.Veiculo;
import br.com.project.commons.monitoramento.entity.PosicaoVeiculo;
import br.com.project.commons.util.DateTimeUtil;
import br.com.project.commons.util.NumberUtil;
import br.com.project.commons.util.SetUtil;
import br.com.project.commons.util.StringUtil;
import br.com.project.foundation.exception.ServiceException;
import br.com.project.foundation.util.MessageHelper;
import br.com.project.monitoramento.ProvidedMonitoramentoView;

/**
 * @author anderson.nascimento
 *
 */
@ManagedBean
@SessionScoped
@Query(value="monitoramento.buscarPosicaoVeiculos")
public class PosicaoVeiculoView extends ProvidedMonitoramentoView<Long, PosicaoVeiculo> {

	private static final long serialVersionUID = 1L;

	private Marker marker;
	private String latlongMap = "-22.284551,-47.994501";
	private String placa;
	private int zoom = 12; 
	private int timeout = 120;
	private boolean ultimaLocalizacao = true;
	private boolean ckeckedUltimaLocalizacao = true;
	private boolean ativaTimeout = false;
    private Date time = DateTimeUtil.getDate("00:02", "HH:mm"); 

	@ManagedProperty(value = "#{veiculoView}")
	private VeiculoView veiculoView;	

	@Override
	public String getEditPage() {
		return "/monitoramento/filterPosicaoVeiculo.xhtml?faces-redirect=true";
	}

	@Override
	public String getCreatePage() {
		return "/monitoramento/filterPosicaoVeiculo.xhtml?faces-redirect=true";
	}

	@Override
	public String getViewPage() {
		return "/monitoramento/filterPosicaoVeiculo.xhtml?faces-redirect=true";
	}

	@Override
	public String getSearchPage() {
		return "/monitoramento/filterPosicaoVeiculo.xhtml?faces-redirect=true";
	}

	/**
	 * 
	 */
	@Override
	public void onSelectDialog() {

		try {			

			Veiculo veiculo = getVeiculoView().getSelected();			
			placa = veiculo.getPlaca();
			
			ativarTodasLocalizacao();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@PreSearch
	public void preSearch() {
		initCollection();		
	}

	@Override
	public void processValidate() throws ServiceException {

		model = new DefaultMapModel();
		
		if(beanFilter.getDataInicio() == null && beanFilter.getDataFim() != null) {
			throw new ServiceException("Periodo incorreto! Por favor informe o periodo com duas datas!");
		}

		if(beanFilter.getDataInicio() != null && beanFilter.getDataFim() == null) {
			throw new ServiceException("Periodo incorreto! Por favor informe o periodo com duas datas!");
		}

		if(beanFilter.getDataInicio() != null && beanFilter.getDataFim() != null && beanFilter.getDataFim().before(beanFilter.getDataInicio())) {
			throw new ServiceException("Periodo incorreto! Última data deverá ser maior que a primeira!");
		}

	}

	@Override
	public void processSearch() throws ServiceException {

		if(StringUtil.isNotEmpty(placa)) {
			beanFilter.setVeiculo(new Veiculo(placa));
		} else {
			beanFilter.setVeiculo(null);
			ultimaLocalizacao = true;
		}

		List<PosicaoVeiculo> posicoes = getMonitoramentoCommonService().buscarPosicoesVeiculos(beanFilter, ultimaLocalizacao);
		
		if(SetUtil.nonEmpty(posicoes)) {
			
			Marker marker = null;
			
			latlongMap = posicoes.get(0).getLatitude() +","+ posicoes.get(0).getLongitude();
			
			for (PosicaoVeiculo posicaoVeiculo : posicoes) {
				
				double longitude = NumberUtil.getDouble(posicaoVeiculo.getLongitude());
				
				double latitude = NumberUtil.getDouble(posicaoVeiculo.getLatitude());
				
				String title = posicaoVeiculo.getVeiculo().getPlaca();
				
				String shadow = "Placa:" + posicaoVeiculo.getVeiculo().getPlaca()
						+" - Descrição: "+ posicaoVeiculo.getDescricao()
						+" - Data Hora: ("+ DateTimeUtil.formatDateTime(posicaoVeiculo.getData()) +")";
				
				marker = new Marker(new LatLng(latitude, longitude), title, "icon-tracking.png", getIconMapsURL());
				marker.setShadow(shadow);
				
				model.addOverlay(marker);
			}
			
		} else {
			MessageHelper.addErrorMessage(getEmptyListMessage());		
		}

	}

	/**
	 * 
	 */
	public void ativarTime() {
		
		Integer hour = NumberUtil.getInteger(DateTimeUtil.formatDate(time, "HH"));
		Integer minute = NumberUtil.getInteger(DateTimeUtil.formatDate(time, "mm"));
		
		if(time != null) {
			timeout = (hour*3600) + (minute*60);			
		}
	}
	
	/**
	 * 
	 */
	public void ativarTodasLocalizacao() {
		ckeckedUltimaLocalizacao = StringUtil.isEmpty(placa);
		ultimaLocalizacao = StringUtil.isEmpty(placa);
	}
	
    public void closeListener(CloseEvent closeEvent) {}  
  
	/**
	 * @param event
	 */
	public void onSlideEnd(SlideEndEvent event) {
		zoom = event.getValue();
	} 

	/**
	 * @param event
	 */
	public void onMarkerSelect(OverlaySelectEvent event) {
		
		marker = (Marker) event.getOverlay();
		marker.setTitle(marker.getShadow());

	}

	public Marker getMarker() {
		return marker;
	}

	public void setMarker(Marker marker) {
		this.marker = marker;
	}

	public String getLatlongMap() {
		return latlongMap;
	}

	public void setLatlongMap(String latlongMap) {
		this.latlongMap = latlongMap;
	}

	public boolean isUltimaLocalizacao() {
		return ultimaLocalizacao;
	}

	public void setUltimaLocalizacao(boolean ultimaLocalizacao) {
		this.ultimaLocalizacao = ultimaLocalizacao;
	}

	public VeiculoView getVeiculoView() {
		return veiculoView;
	}

	public void setVeiculoView(VeiculoView veiculoView) {
		this.veiculoView = veiculoView;
	}

	public int getZoom() {
		return zoom > 0 ? zoom : 13;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public int getTimeout() {
		return timeout > 0 ? timeout : 120;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public boolean isAtivaTimeout() {
		return ativaTimeout;
	}

	public void setAtivaTimeout(boolean ativaTimeout) {
		this.ativaTimeout = ativaTimeout;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public boolean isCkeckedUltimaLocalizacao() {
		return ckeckedUltimaLocalizacao;
	}

	public void setCkeckedUltimaLocalizacao(boolean ckeckedUltimaLocalizacao) {
		this.ckeckedUltimaLocalizacao = ckeckedUltimaLocalizacao;
	}

	
}  

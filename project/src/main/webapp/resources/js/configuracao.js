var config = function () {
	
	return {
		setFocus:		function(obj, event)  {
						    var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
						    if (keyCode == 13) {
						    	var lfound = false;
						        jQuery(".ui-inputfield, .ui-button, input[type='radio'], select[size='0'], select[size='1']").each(function() {
						           	if (this == obj) {
						                lfound = true;
						            } else {
						                if (lfound) {
						                	 var a = jQuery(this).attr('disabled');
						    				 if(a === undefined) {
						    					 jQuery(this).focus();
						    					 jQuery(this).select();
						    					 event.preventDefault();
						    					 return false;
						    				 }
						                }
						            }
						        });
						    }
						},
		lastFocus : 	function (obj, event) {
							var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
							if (keyCode == 38) {
								var l = null;
								var p = null;	
								jQuery(".ui-inputfield, input[type='radio'], select[size='0'], select[size='1']").each(function() {
									if (l != null) {
										var a = jQuery(p).attr('disabled');
										if(a === undefined) {
											l = p;					    					 
										} 						    			
									} else {
										l = p;
									}	
									p = this;						    		
									if (this == obj) {
										jQuery(l).focus();
										jQuery(l).select();
										event.preventDefault();				    					 
										return false;
									}
								});							
							}
						},
						
		oneventMask: 	function(e){
						    if(e.status == 'success' || e.status == 'complete'){
						        this.buildMasks();
						    }
						},
						
		
		tabChangeEvent: function() {
							if (jQuery.browser.mozilla || jQuery.browser.webkit) {
								jQuery(".ui-inputfield, input[type='radio'], select[size='0'], select[size='1']").keypress(function(event) {config.setFocus(this, event);});
							} else {
								jQuery(".ui-inputfield, input[type='radio'], select[size='0'], select[size='1']").keydown(config.setFocus);
							}
						}
	};
}();

var util = function () {
	
	function mascaraMutuario(o,f){
	    v_obj=o;
	    v_fun=f;
	    setTimeout('execmascara()',1);
	}
	 
	function execmascara(){
	    v_obj.value=v_fun(v_obj.value);
	}
		
	
	return {

		gerarInfoWindow:		function() {
									
									setTimeout(function() {
										
										var gmap = PF("widgetVarMaps").getMap();
															
										var markers = gmap.markers;
										
										for (i = 0; i < markers.length; i++) {
											
											var infoWindow = new google.maps.InfoWindow({
											    content: markers[i].title
											});	
												
											infoWindow.open(gmap, markers[i]);						
										}
																
									}, 4000);
															
								},
		resetSearchFields:		function() {
									jQuery(".ui-inputfield").val(null);
									jQuery("select").val(null);
								},																									
								
		formatCnpjCpf:		function(f) {								
								var v = f.value;								
							    //Remove tudo o que não é dígito
							    v=v.replace(/\D/g,"");
							    if (v.length <= 11) { //CPF							 
							        //Coloca um ponto entre o terceiro e o quarto dígitos
							        v=v.replace(/(\d{3})(\d)/,"$1.$2");							 
							        //Coloca um ponto entre o terceiro e o quarto dígitos
							        //de novo (para o segundo bloco de números)
							        v=v.replace(/(\d{3})(\d)/,"$1.$2");							 
							        //Coloca um hífen entre o terceiro e o quarto dígitos
							        v=v.replace(/(\d{3})(\d{1,2})$/,"$1-$2");							 
							    } else { //CNPJ							 
							        //Coloca ponto entre o segundo e o terceiro dígitos
							        v=v.replace(/^(\d{2})(\d)/,"$1.$2");							 
							        //Coloca ponto entre o quinto e o sexto dígitos
							        v=v.replace(/^(\d{2})\.(\d{3})(\d)/,"$1.$2.$3");							 
							        //Coloca uma barra entre o oitavo e o nono dígitos
							        v=v.replace(/\.(\d{3})(\d)/,".$1/$2");							 
							        //Coloca um hífen depois do bloco de quatro dígitos
							        v=v.replace(/(\d{4})(\d)/,"$1-$2");							 
							    }							 
							    f.value = v;				
							},								
								
		updateDialogSelection:	function (dialogName, fieldName, isById) {
									var value = jQuery("#" + dialogName + "_form\\:" +  dialogName + "_datatable_selection").val();

									if (value) {
										var text = ""; 
										jQuery(".ui-widget-content .ui-state-highlight > td > div > span").each(function(index) {
																													text += jQuery(this).text() + " ";
																												});
										
										jQuery('#' + fieldName.replace(/\:/g,"\\:")).val(text);
										if (isById) {
											var idx = fieldName.lastIndexOf(':');
											var idPreName = fieldName.substr(0, idx + 1).replace(/\:/g,"\\:");
											var idName = fieldName.substr(idx + 1);
											jQuery('#' + idPreName + 'id' + this.firstUpperCase(idName)).val(value);
										}
									}
									return false;	
								},
								
		firstUpperCase:			function (value) {
									return value.substr(0,1).toUpperCase() + value.substr(1);
								},
							
		firstLowerCase:			function (value) {
									return value.substr(0,1).toLowerCase() + value.substr(1);
								},
							
        loginValidate:			function(xhr, status, args) {
        							if(!args.error) {
							        	if(args.logar) {
							        		//alert(args.logar);
							        	} else if (args.logado) {
							        		window.location.href = args.logado;
							        	}
	        						}
        						},
        						
		saveDatatableRows: 	function () {
							jQuery('.ui-datatable').find('span.ui-icon-check').each(
								function(){
									jQuery(this).click();
								}
							);
						},
        						
		editDatatableRows: 	function () {
									jQuery('.ui-datatable-data tr').find('span.ui-icon-pencil').each(
										function(){
											jQuery(this).click();
										}
									);
								},
								
		editFirstDatatableRow: function () {
									jQuery('.ui-datatable-data tr').first().find('span.ui-icon-pencil').each(
										function(){
											jQuery(this).click();
										}
									);
								},
								
		editLastDatatableRow: function () {
									jQuery('.ui-datatable-data tr').last().find('span.ui-icon-pencil').each(
										function(){
											jQuery(this).click();
										}
									);
								},
								
		expandLastDatatableRow: function () {
									jQuery('.ui-datatable-data tr').last().find('span.ui-icon-circle-triangle-e').each(
										function(){
											jQuery(this).click();
										}
									);
								},
        						
		messageSaveButtonCallback:function (xhr, status, args) {
								},
								
		messageCancelButtonCallback:function (xhr, status, args) {
								},
								
		messageOkButtonCallback:function (xhr, status, args) {
								},
								
		messageCloseButtonCallback:function (xhr, status, args) {
		},
		
		dialogCloseButtonCallback:function (xhr, status, args) {
		},
		
		testeNew:function (xhr, status, args) {
			alert('message');
		},
		
		displayLocalidadeDialog:function (xhr, status, args) {
		},

		showDialog:function(xhr, status, args, dialog) {
			if (!args.hasError) {
				dialog.show();
			}
		},
		
		closeDialog:function(xhr, status, args, dialog) {
			if (args.isOk) {
				dialog.hide();
			}
		},
		
		scrollToSelectedRow:function() {
			var select = jQuery('.ui-datatable-scrollable-body tr.ui-state-highlight');
			if(select.size() > 0) {
				jQuery('.ui-datatable-scrollable-body').scrollTo(select, 0);
			}
		}
		

	};
}();

function messageOkButton(){}

PrimeFaces.locales['pt'] = {
        closeText: 'Fechar',
        prevText: 'Anterior',
        nextText: 'Próximo',
        currentText: 'Começo',
        monthNames: ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
        monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun', 'Jul','Ago','Set','Out','Nov','Des'],
        dayNames: ['Domingo','Segunda','Terça','Quarta','Quinta','Sexta','Sábado'],
        dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','Sáb'],
        dayNamesMin: ['D','S','T','Q','Q','S','S'],
        weekHeader: 'Semana',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
        timeOnlyTitle: 'Só Horas',
        timeText: 'Tempo',
        hourText: 'Hora',
        minuteText: 'Minuto',
        secondText: 'Segundo',
        currentText: 'Data Atual',
        ampm: false,
        month: 'Mês',
        week: 'Semana',
        day: 'Dia',
        allDayText : 'Todo Dia'
};

jQuery(document).ready(function() {
	config.tabChangeEvent();	
});

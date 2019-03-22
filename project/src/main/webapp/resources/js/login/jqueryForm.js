var erro = Number(0);
var Doc = "";
var Leng = "";
var reEmail3 = /^[\w-]+(\.[\w-]+)*@(([A-Za-z\d][A-Za-z\d-]{0,61}[A-Za-z\d]\.)+[A-Za-z]{2,6}|\[\d{1,3}(\.\d{1,3}){3}\])$/;

function doEmail(pStr) {
    eval("reEmail = reEmail3");
    if (reEmail.test(pStr)) {
        return true;
    } else {
        return false;
    }
}

var login = function () {
	
	return {

		validate: function(xhr, status, args) {
						if(!args.error) {
				        	if(args.logar) {
				        		$('#formLogin .msgError').html(args.logar);
				           		$('#formLogin\\:login').addClass('error');
				           		$('#formLogin\\:password').addClass('error');
				           		$('#formLogin .msgError').show();
				        	} else if (args.logado) {
				        		window.location.href = args.logado;
				        	}
						}
					},
		forgot: function(xhr, status, args) {
				
				if(!doEmail($('#formEsqueci\\:email').val())) {

					$('#formEsqueci\\:email').addClass('error');
					$('#formEsqueci\\:email').val('');
					$('#formEsqueci\\:email').attr('placeholder', 'Email Invalido');
					$('#formEsqueci .msgError').show();

					
				} else {
					
					if(args.ok) {
						
						$('#formEsqueci\\:email').addClass('.msgSuccess');
						$('#formEsqueci .msgSuccess').show();

					} else {
						
						$('#formEsqueci\\:email').addClass('error');
						$('#formEsqueci\\:email').attr('placeholder', 'Email Inexistente');
						$('#formEsqueci .msgError').show();
					}
					
				}
				
		}

	};
}();

$(document).ready(function(){
    
	$('#formLogin').submit(function(e) {
		e.preventDefault();
		var form = $(this);
		var valido = false;
		form.find('.msgError').hide();
        form.find('.msgSuccess').hide();
        form.find(".required").removeClass('error');
        form.find(".required").removeClass('errorEmail');
        
        if (valido) {
       		$('#formLogin\\:login').addClass('error');
       		$('#formLogin\\:password').addClass('error');
       		$('#formLogin .msgError').show();
        } 
        
        return valido;
    });
	
	$('#formEsqueci').submit(function(e) {
		e.preventDefault();
		var form = $(this);
		var valido = false;
		form.find('.msgError').hide();
        form.find('.msgSuccess').hide();
        form.find(".required").removeClass('error');
        form.find(".required").removeClass('errorEmail');
        
        if (valido) {
			$('#formEsqueci\\:email').addClass('error');
			$('#formEsqueci\\:email').val('');
			$('#formEsqueci\\:email').attr('placeholder', 'Email Invalido');
			$('#formEsqueci .msgError').show();
        } 
        
        return valido;
    });	
});

package br.com.project.foundation.persistence;

import java.util.Date;

public interface BasicLoggableInterface {

	Object getId();

	Date getDataAlteracao();

	Date getDataCriacao();

	Long getIdUsuarioAlteracao();

	Long getIdUsuarioCriacao();

	void setIdUsuarioCriacao(Long idUsuarioCriacao);

	void setIdUsuarioAlteracao(Long idUsuarioAlteracao);

	void setDataAlteracao(Date dataAlteracao);

	void setDataCriacao(Date dataCriacao);

}

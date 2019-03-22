package br.com.project.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.junit.Test;

import br.com.project.util.BooleanUtil;
import br.com.project.util.FileUtil;
import br.com.project.util.NumberUtil;
import br.com.project.util.SoundexUtil;
import br.com.project.util.StringUtil;
import br.com.project.endereco.persistencia.EndLocalidade;
import br.com.project.test.spring.StandAloneWireTest;
import br.com.project.tipos.persistencia.TpoCep;
import br.com.project.tipos.persistencia.TpoLogradouro;

public class TestGerarLocalidadeCorreio extends StandAloneWireTest {
	public static int workers = 10;
	public static int slice_size = 5000;

	@Test
	public void gerarLocalidade() {
		processar(918061);
		// processar(10);

	}

	public static Connection getAccessConnection() {
		Connection c = null;
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver").newInstance();
			java.util.Properties prop = new java.util.Properties();
			prop.put("charSet", "ISO8859_1");
			c = DriverManager.getConnection("jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=d:/temp/BaseCep2010.mdb", prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c;
	}

	private void processar(int total) {
		Date dataInicio = new Date();

		Thread[] threads = new Thread[workers];
		CyclicBarrier barrier = new CyclicBarrier(workers + 1);

		for (int i = 0; i < workers; i++) {
			int init = i * (total / workers) + 1;
			int end = init + (total / workers) - 1;
			if (end > total)
				end = total;
			threads[i] = (new Thread(new ODBCWorker(i, init, end, barrier, this)));
		}

		for (int i = 0; i < workers; i++) {
			threads[i].start();
		}

		try {
			barrier.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}

		threads = null;
		Date dataFim = new Date();
		long tempoGasto = dataFim.getTime() - dataInicio.getTime();
		System.out.println("Tempo Gasto = " + tempoGasto);
	}

}

class ODBCWorker implements Runnable {
	int id;
	int init;
	int fim;
	TestGerarLocalidadeCorreio service;
	CyclicBarrier barrier;

	public ODBCWorker(int id, int init, int fim, CyclicBarrier barrier, TestGerarLocalidadeCorreio service) {
		this.id = id;
		this.init = init;
		this.fim = fim;
		this.service = service;
		this.barrier = barrier;
	}

	@Override
	public void run() {
		System.out.println("Worker " + this.id + " DE: " + init + " ATÉ: " + fim);

		Statement st = null;
		ResultSet rs = null;
		Connection c = TestGerarLocalidadeCorreio.getAccessConnection();
		String baseSQL = "INSERT INTO END_LOGRADOURO (ID_END_LOGRADOURO,ID_END_LOCALIDADE,ID_TPO_CEP," + "ID_TPO_LOGRADOURO,CEP,LATITUDE,LONGITUDE,NOME,NOME_PESQUISA,NOME_SOUNDEX,COMPLEMENTO,"
				+ "ID_LOGRADOURO_CORREIOS,ID_DATAPRES1,IMPRIME_TIPO) VALUES (SQ_ENDLOG.NEXTVAL,";
		ArrayList<String> sqlToFile = new ArrayList<String>();
		String logradouroFileName = "d:\\temp\\cep\\" + this.id + "_importLogradouro.sql";
		FileUtil.wipeFile(logradouroFileName);

		int total = fim - init;
		int steps = total / TestGerarLocalidadeCorreio.slice_size;
		for (int k = 0; k < steps; k++) {
			int limiteInferior = init + k * steps;
			int limiteSuperior = limiteInferior + TestGerarLocalidadeCorreio.slice_size * (k + 1) - 1;
			if (limiteSuperior > fim)
				limiteSuperior = fim;

			try {
				st = c.createStatement();
				rs = st.executeQuery("SELECT * FROM LOGRADOURO WHERE ID BETWEEN " + limiteInferior + " AND " + limiteSuperior);
				System.out.println(this.id + "-" + k + " - SELECT * FROM LOGRADOURO WHERE ID BETWEEN " + limiteInferior + " AND " + limiteSuperior);
				while (rs.next()) {
					Integer id = rs.getInt("ID");
					Integer idLocalidade = rs.getInt("IDLOC");
					String nome = StringUtil.toUpperCase(rs.getString("SEMACENTO").replaceAll("'", "''"));
					String nomeAcento = StringUtil.toUpperCase(rs.getString("ENDERECO").replaceAll("'", "''"));
					String cep = rs.getString("CEP");
					String complemento = rs.getString("COMPLEMENTO");

					if (complemento != null)
						complemento.replaceAll("'", "''");
					String tipoLogradouroStr = rs.getString("TIPO");

					TpoLogradouro tipoLogradouro = null;
					if (StringUtil.isNotEmptyTrim(tipoLogradouroStr)) {
						tipoLogradouro = service.getCommomService().getSingleRecordByProperty(TpoLogradouro.class, "nome", StringUtil.getUpperNonAccentString(tipoLogradouroStr));
						if (tipoLogradouro == null) {
							System.out.println(StringUtil.getUpperNonAccentString(tipoLogradouroStr) + "-" + tipoLogradouroStr + " - " + id);
						}
					}

					String unidadeOperaciona = rs.getString("IND_UOP");
					String usuarioGrupo = rs.getString("IND_GRU");

					TpoCep tpoCep = null;

					int idTpoCep = 1;
					if (BooleanUtil.booleanValue(unidadeOperaciona))
						idTpoCep = 3;
					else if (BooleanUtil.booleanValue(usuarioGrupo))
						idTpoCep = 2;

					tpoCep = service.getCommomService().getByID(TpoCep.class, idTpoCep);

					String idBairroIni = rs.getString("IDBAIINI");
					String idBairroFim = rs.getString("IDBAIFIM");
					Boolean imprimeTipo = BooleanUtil.getBoolean(rs.getString("LOG_STATUS_TIPO_LOG"));

					EndLocalidade localidadePai = service.getCommomService().getByID(EndLocalidade.class, idLocalidade);

					if (NumberUtil.longValue(idBairroIni) <= 0 && NumberUtil.longValue(idBairroFim) > 0) {
						idBairroIni = idBairroFim;
					}

					StringBuilder b = new StringBuilder(baseSQL);
					b.append(localidadePai.getId()).append(","); // ID_END_LOCALIDADE
					b.append(tpoCep.getId()).append(","); // ID_TPO_CEP
					b.append(tipoLogradouro.getId()).append(","); // ID_TPO_LOGRADOURO
					b.append("'").append(cep).append("',"); // CEP
					b.append("null").append(","); // LAT
					b.append("null").append(","); // LONG
					b.append("'").append(nomeAcento).append("',"); // NOME
					b.append("'").append(nome).append("',"); // NOME_PESQUISA
					b.append("'").append(SoundexUtil.encode(nomeAcento)).append("',"); // NOME_PESQUISA
					b.append("'").append(complemento == null ? "" : complemento).append("',"); // COMPLEMENTO
					b.append(id).append(","); // ID_LOGRADOURO_CORREIOS
					b.append("null").append(","); // ID_DATAPRES1
					b.append(BooleanUtil.asInt(imprimeTipo)).append(");\n"); // IMPRIME_TIPO
					sqlToFile.add(b.toString());
				}
				sqlToFile.add("COMMIT;\n");
				FileUtil.wipeFile("d:\\temp\\cep\\" + this.id + "_" + k + "_importLogradouro.sql");
				FileUtil.writeToFile("d:\\temp\\cep\\" + this.id + "_" + k + "_importLogradouro.sql", sqlToFile);
				sqlToFile.clear();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException e) {
					}
				rs = null;
				if (st != null)
					try {
						st.close();
					} catch (SQLException e) {
					}
				st = null;
			}
		} // for
		if (c != null)
			try {
				c.close();
			} catch (SQLException e) {
			}

		try {
			barrier.await();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}

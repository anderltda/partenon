package br.com.project.foundation.util;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import br.com.project.commons.service.CommonsService;
import br.com.project.foundation.service.PersistenceService;
import br.com.project.foundation.service.ServiceLocator;

public class ResourceInitListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		ServletContext ctx = arg0.getServletContext();
		CommonsService service = (CommonsService) ServiceLocator.getInstance().getBean(CommonsService.SERVICE);
		initBundle(ctx, service);
	}
	
	
	private void initBundle(ServletContext ctx, PersistenceService service) {
		
		String path = ctx.getRealPath("/WEB-INF/classes/messages/");
		try {
			File appRes = new File(path + "/ApplicationResources_pt.properties");
			if (appRes.exists()) {
				//appRes.delete();
			}

			/*if (appRes.createNewFile()) {
				List<CfgApplicationResource> resources = service.findAll(CfgApplicationResource.class, new SortingCondition("chave"));
				OutputStreamWriter print = new OutputStreamWriter(new FileOutputStream(appRes), "ISO-8859-1");

				//PrintWriter print = new PrintWriter(new FileWriter(appRes, false));
				for (CfgApplicationResource ar : resources) {
					print.append(ar.getChave());
					print.append('=');
					print.append(ar.getValorChave());
					print.append('\n');
				}
				print.close();
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

package com.wyh.web;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wyh.service.ArticleService;


public class View_countListener  implements   HttpSessionListener,ServletContextListener, ServletContextAttributeListener {

	Logger log = Logger.getLogger(View_countListener.class);
	private ArticleService articleService = null;
    public void contextDestroyed(ServletContextEvent sce)  { 
         
    }

    
    @Override
    public void attributeReplaced(ServletContextAttributeEvent scae) {
    	log.info("监听器replace");
    	ServletContext app = scae.getServletContext();
   	 	Object attribute = app.getAttribute("view_count");
   	 	if (attribute instanceof Map) {
		@SuppressWarnings("unchecked")
		HashMap<String,Integer>  map=(HashMap<String, Integer>) attribute;
		log.info("before_map:"+map.size());
   	 	int value =0;
   	 	String id ="";
   	 	for (Entry<String, Integer> entry : map.entrySet()) {
   	 		value = entry.getValue();
   	 		id = entry.getKey();
   	 		log.info(id+":"+value);
			if (value>5) {
				
				if (articleService.sure_view(id)!=0) {
					articleService.update_view_count(id,value);
				}else {
					articleService.insert_view_count(id,value);
				}
				map.remove(id);
			}
		}
   	 	log.info("after_map:"+map.size());
		}
    }
    @Override
    public void contextInitialized(ServletContextEvent sce) {
    	ServletContext app = sce.getServletContext();
    	Map<String, Integer>  map= new HashMap<String, Integer>();
    	app.setAttribute("view_count", map);
    	log.info("监听器启动成功");
    	ApplicationContext application=WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());  
        articleService=(ArticleService) application.getBean("articleService");  
    	System.out.println(articleService);
    }
    
}

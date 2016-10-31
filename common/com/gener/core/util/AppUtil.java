package com.gener.core.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AppUtil extends AbstractUtil implements ApplicationContextAware
{
	// Spring应用上下文环境
	private static ApplicationContext applicationContext;
	private static Map<String, String> configMap = new HashMap<String, String>();
	private static String BEAN_SUFFIX = "_bean";
	private static String METHOD_SUFFIX = "_method";

	public static Object getBean(String name)
	{
		return applicationContext.getBean(name);
	}

	public static void init()
	{

//		TResourceIndexService resourceIndexService = (TResourceIndexService) getBean("TResourceIndexService");
//		
//		List<TResourceIndex> list = resourceIndexService.loadResourceIndex();
//		if (list != null && list.size() > 0)
//		{
//			for (TResourceIndex r : list)
//			{
//				configMap.put(r.getClientMethod() + BEAN_SUFFIX, r.getServiceBean());
//				configMap.put(r.getClientMethod() + METHOD_SUFFIX, r.getServiceMethod());
//			}
//		}

	}

	public static String getServiceBean(String clientMethod)
	{
		if (configMap.size() <= 0)
		{
			init();
		}
		String serviceBean = configMap.get(clientMethod + BEAN_SUFFIX);
		if (StringUtils.isEmpty(serviceBean))
		{
//			loadByClientMenthod(clientMethod);
			return configMap.get(clientMethod + BEAN_SUFFIX);
		} else
		{
			return serviceBean;
		}

	}

	public static String getServiceMethod(String clientMethod)
	{
		if (configMap.size() <= 0)
		{
			init();
		}
		String serviceMethod = configMap.get(clientMethod + METHOD_SUFFIX);
		if (StringUtils.isEmpty(serviceMethod))
		{
//			loadByClientMenthod(clientMethod);
			return configMap.get(clientMethod + METHOD_SUFFIX);
		} else
		{
			return configMap.get(clientMethod + METHOD_SUFFIX);
		}

	}

//	private static void loadByClientMenthod(String clientMethod)
//	{
//		TResourceIndexService resourceIndexService = (TResourceIndexService) getBean("TResourceIndexService");
//		TResourceIndex ri=resourceIndexService.getByClientMehtod(clientMethod);
//		if(ri!=null)
//		{
//			configMap.put(ri.getClientMethod() + BEAN_SUFFIX, ri.getServiceBean());
//			configMap.put(ri.getClientMethod() + METHOD_SUFFIX, ri.getServiceMethod());
//		}
//	}
	
	public static ApplicationContext getApplicationContext()
	{
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
	{
		
		AppUtil.applicationContext = applicationContext;

	}

}

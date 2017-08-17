package com.gy.hsi.ds.param;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import com.baidu.disconf.client.DisconfMgr;
import com.baidu.disconf.client.addons.properties.ReconfigurableBean;
import com.baidu.disconf.client.addons.properties.ReloadConfigurationMonitor;
import com.baidu.disconf.client.addons.properties.ReloadablePropertiesBase;
import com.baidu.disconf.client.addons.properties.ReloadablePropertiesListener;

/**
 * Created by:zhangysh, Date: 2015-12-08, Rewrite Baidu code of the class ReloadablePropertiesFactoryBean
 * 
 * A properties factory bean that creates a reconfigurable Properties object.
 * When the Properties' reloadConfiguration method is called, and the file has
 * changed, the properties are read again from the file.
 */
public class HsReloadablePropertiesFactoryBean extends
		PropertyPlaceholderConfigurer implements DisposableBean,
		ApplicationContextAware {

    @SuppressWarnings("unused")
	private ApplicationContext applicationContext;

    protected static final Logger log = Logger.getLogger(HsReloadablePropertiesFactoryBean.class);

    private Resource[] locations;
    private long[] lastModified;
    private List<ReloadablePropertiesListener> preListeners;

    public void setRemoteLocations(final String fileNames) {
        List<String> list = new ArrayList<String>();
        list.add(fileNames);
        setRemoteLocations(list);
    }

    /**
     */
    public void setRemoteLocations(List<String> fileNames) {

        List<Resource> resources = new ArrayList<Resource>();
        for (String filename : fileNames) {

            // trim
            filename = filename.trim();

            String realFileName = getFileName(filename);

            //
            // register to disconf
            //
            try {
				DisconfMgr.reloadableScan(realFileName);
			} catch (Exception e) {
				log.info("The file '"+realFileName+"' can't download, which may not be a remote file.");
			}

            //
            // only properties will reload
            //
            String ext = FilenameUtils.getExtension(filename);
            if (ext.equals("properties")) {

                PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver =
                    new PathMatchingResourcePatternResolver();
                try {
                    Resource[] resourceList = pathMatchingResourcePatternResolver.getResources(filename);
                    for (Resource resource : resourceList) {
                        resources.add(resource);
                    }
                } catch (IOException e) {
                }
            }
        }

        this.locations = resources.toArray(new Resource[resources.size()]);
        lastModified = new long[locations.length];
        super.setLocations(locations);
    }

    private String getFileName(String fileName) {

        if (fileName != null) {
            int index = fileName.indexOf(':');
            if (index < 0) {
                return fileName;
            } else {

                fileName = fileName.substring(index + 1);

                index = fileName.lastIndexOf('/');
                if (index < 0) {
                    return fileName;
                } else {
                    return fileName.substring(index + 1);
                }

            }
        }
        return null;
    }

    protected Resource[] getLocations() {
        return locations;
    }

    /**
     * @param listeners
     */
    @SuppressWarnings("rawtypes")
	public void setListeners(final List listeners) {
        // early type check, and avoid aliassing
        this.preListeners = new ArrayList<ReloadablePropertiesListener>();
        for (Object o : listeners) {
            preListeners.add((ReloadablePropertiesListener) o);
        }
    }

    private ReloadablePropertiesBase reloadableProperties;

    /**
     * @throws IOException
     */
    protected Object createInstance() throws IOException {
        // would like to uninherit from AbstractFactoryBean (but it's final!)
//        if (!isSingleton()) {
//            throw new RuntimeException("ReloadablePropertiesFactoryBean only works as singleton");
//        }
    	
        reloadableProperties = new ReloadablePropertiesImpl();
        if (preListeners != null) {
            reloadableProperties.setListeners(preListeners);
        }
        reload(true);

        // add for monitor
        ReloadConfigurationMonitor.addReconfigurableBean((ReconfigurableBean) reloadableProperties);

        return reloadableProperties;
    }

    public void destroy() throws Exception {
        reloadableProperties = null;
    }

    /**
     * @param forceReload
     *
     * @throws IOException
     */
    protected void reload(final boolean forceReload) throws IOException {
        boolean reload = forceReload;
        
        if(null == locations) {
        	return;
        }
        
        for (int i = 0; i < locations.length; i++) {
            Resource location = locations[i];
            File file;
            try {
                file = location.getFile();
            } catch (IOException e) {
                // not a file resource
                continue;
            }
            try {
                long l = file.lastModified();
                if (l > lastModified[i]) {
                    lastModified[i] = l;
                    reload = true;
                }
            } catch (Exception e) {
                // cannot access file. assume unchanged.
                if (log.isDebugEnabled()) {
                    log.debug("can't determine modification time of " + file + " for " + location, e);
                }
            }
        }
        
        if (reload) {
            doReload();
        }
    }

    /**
     * @throws IOException
     */
	private void doReload() throws IOException {
		try {
			((ReloadablePropertiesImpl) reloadableProperties)
					.setProperties(mergeProperties());
		} catch (Exception e) {
		}
	}

    /**
     * @return
     */
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

	@Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    	this.applicationContext = applicationContext;
    }


    @SuppressWarnings("unchecked")
	class ReloadablePropertiesImpl extends ReloadablePropertiesBase implements ReconfigurableBean {
		private static final long serialVersionUID = -7940343723207487310L;

		public void reloadConfiguration() throws Exception {
            HsReloadablePropertiesFactoryBean.this.reload(false);
        }
        
        @Override
        public void setProperties(Properties properties) {
        	super.setProperties(properties);
        }
    }

}

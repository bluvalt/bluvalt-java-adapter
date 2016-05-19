package com.stcs.spa.util;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import org.apache.log4j.Logger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;


public class Config {
	static private Config config = null;
	private static final Logger logger = Logger.getLogger(Config.class);
	public Properties properties =null;
	public OIDCProviderMetadata providerMetadata=null;
	protected Config() {
		logger.debug("Loading Configuration Properties......");
		properties = loadProperties();
		logger.debug("discovery_endpoint" +properties.getProperty("discovery_endpoint"));
		try {
			providerMetadata = getOIDCProviderMetadata( properties.getProperty("discovery_endpoint"));
		} catch (MalformedURLException e) {

			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}




	}

	public Properties loadProperties(){
		InputStream inputStream;
		inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("config.properties");
		if (inputStream == null) {
			try {
				throw new IOException("Unable to open properties file");
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
		properties = new Properties();
		try {
			properties.load(inputStream);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return properties;
	}

	public boolean saveProperties(Properties properties){
		try {
			URL url = this.getClass().getClassLoader()
					.getResource("config.properties");
			FileOutputStream out = new FileOutputStream(
					url.getFile());
			properties.store(out, null);
			out.close();
			return true;

		} catch (IOException e) {

			e.printStackTrace();
		}
		return false;

	}

	private  OIDCProviderMetadata getOIDCProviderMetadata(String discovery_endpoint)
			throws IOException, ParseException {
		URL providerConfigurationURL =new URL( discovery_endpoint);
		logger.debug(providerConfigurationURL.toString());
		InputStream stream = providerConfigurationURL.openStream();
		// Read all data from URL
		String providerInfo;
		try (java.util.Scanner s = new java.util.Scanner(stream)) {
			providerInfo = s.useDelimiter("\\A").hasNext() ? s.next() : "";
		}
		return OIDCProviderMetadata.parse(providerInfo);
	}

	static public Config getInstance() {
		if (config == null) {
			config = new Config();
		}
		return config;
	}


	static public Config refreshInstance() {
		return new Config();
	}

	public static void main(String[] args) {
		Config config = Config.getInstance();
		System.out.println(config.properties.getProperty("discovery_endpoint"));
	}



}
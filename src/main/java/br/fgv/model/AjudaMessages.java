package br.fgv.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class AjudaMessages {
	static final String BUNDLE_NAME = "br.fgv.model.ajuda_messages"; //$NON-NLS-1$

	private static ResourceBundle RESOURCE_BUNDLE;// = PropertyResourceBundle.getBundle(BUNDLE_NAME);

	static {
		
		
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(AjudaMessages.class.getResourceAsStream("/br/fgv/model/ajuda_messages.properties"), "UTF-8"));
			RESOURCE_BUNDLE = new PropertyResourceBundle(in);
			in.close();
		} catch (IOException e) {
			RESOURCE_BUNDLE = null;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private AjudaMessages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}

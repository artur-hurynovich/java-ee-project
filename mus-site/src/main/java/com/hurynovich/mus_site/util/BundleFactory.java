package com.hurynovich.mus_site.util;

import java.util.ResourceBundle;

public class BundleFactory {
	private static final BundleFactory INSTANCE = new BundleFactory();
	
	private final ResourceBundle configBundle;
	private final ResourceBundle pathsBundle;
	private final ResourceBundle mailBundle;
	
	private BundleFactory() {
		configBundle = ResourceBundle.getBundle("config");
		pathsBundle = ResourceBundle.getBundle("paths");
		mailBundle = ResourceBundle.getBundle("mail");
	}
	
	public static BundleFactory getInstance() {
		return INSTANCE;
	}
	
	public ResourceBundle getConfigBundle() {
		return configBundle;
	}
	
	public ResourceBundle getPathsBundle() {
		return pathsBundle;
	}
	
	public ResourceBundle getMailBundle() {
		return mailBundle;
	}
}

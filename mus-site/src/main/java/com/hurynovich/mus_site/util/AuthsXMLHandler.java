package com.hurynovich.mus_site.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.hurynovich.mus_site.bean.user.UserRole;

public class AuthsXMLHandler {
	private Map<String, List<UserRole>> auths;
	
	private String url;
	private List<UserRole> roles;
	
	public AuthsXMLHandler(String xmlPath) throws ParserConfigurationException, 
		SAXException, IOException {
		auths = new HashMap<>();
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder(); 
        Document document = builder.parse(xmlPath);
        Element root = document.getDocumentElement();
        NodeList authsList = root.getChildNodes();
        for (int i = 0; i < authsList.getLength(); i++) {
        	Node auth = authsList.item(i);
        	if (auth.getNodeName().equals("auth")) {
        		url = getUrl(auth);
            	roles = getRoles(auth);
            	auths.put(url, roles);
        	}
        }
	}

    public Map<String, List<UserRole>> getAuths() {
    	return auths;
    }
    
    private String getUrl(Node auth) {
    	String url = null;
    	NodeList childNodes = auth.getChildNodes();
    	for (int i = 0; i < childNodes.getLength(); i++) {
    		Node node = childNodes.item(i);
        	String nodeName = node.getNodeName();
        	if (nodeName.equals("url")) {
        		url = node.getTextContent();
        	}
        }
    	return url;
    }
    
    private List<UserRole> getRoles(Node auth) {
    	List<UserRole> roles = new LinkedList<>();
    	NodeList childNodes = auth.getChildNodes();
    	for (int i = 0; i < childNodes.getLength(); i++) {
    		Node node = childNodes.item(i);
        	String nodeName = node.getNodeName();
        	if (nodeName.equals("role")) {
        		String nodeTextContent = node.getTextContent();
        		UserRole role = UserRole.valueOf(nodeTextContent);
        		roles.add(role);
        	}
        }
    	return roles;
    }
}

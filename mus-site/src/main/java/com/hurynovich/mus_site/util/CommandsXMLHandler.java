package com.hurynovich.mus_site.util;

import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.hurynovich.mus_site.controller.command.ICommand;

public class CommandsXMLHandler extends DefaultHandler {
	private HashMap<String, ICommand> commands;

	private boolean inCommandName;
	private boolean inCommandClass;
	
	private String commandName;
	private String commandClass;
	
	public CommandsXMLHandler() {
		commands = new HashMap<>();
	}
	
	@Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) 
    	throws SAXException {
        if (qName.equals("commandName")) {
        	inCommandName = true;
        }
        if (qName.equals("commandClass")) {
        	inCommandClass = true;
        }
    }
	
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
    	if (qName.equals("commandName")) {
    		inCommandName = false;
        }
        if (qName.equals("commandClass")) {
        	inCommandClass = false;
        }
        if (qName.equals("command")) {
        	try {
				Class<?> command = Class.forName(commandClass);
				ICommand iCommand = (ICommand) command.newInstance();
				commands.put(commandName, iCommand);
			} catch (ClassNotFoundException|IllegalAccessException|InstantiationException e) {
				e.printStackTrace();
			}
        }
    }
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (inCommandName) {
        	commandName = new String(ch, start, length);
        }
        if (inCommandClass) {
        	commandClass = new String(ch, start, length);
        }
    }

    public HashMap<String, ICommand> getCommands() {
    	return commands;
    }
}

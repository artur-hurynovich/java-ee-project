package com.hurynovich.mus_site.controller.command;

import java.io.IOException;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.hurynovich.mus_site.controller.command.impl.UnknownCommand;
import com.hurynovich.mus_site.util.CommandsXMLHandler;

public class CommandsMapping {
	private static CommandsMapping INSTANCE;
	private final ICommand unknownCommand = new UnknownCommand();
	
	private Map<String, ICommand> map;
	
	private CommandsMapping(String xmlPath) {
		fillMap(xmlPath);
	}
	
	public static CommandsMapping getInstance(String xmlPath) {
		if (INSTANCE == null) {
			INSTANCE = new CommandsMapping(xmlPath);
		}
		return INSTANCE;
	}
	
	public ICommand getCommand(String action) {
		if (map == null) {
			return unknownCommand;
		} else {
			ICommand command = map.get(action);
			if (command == null) {
				return unknownCommand;
			} else {
				return command;
			}
		}
	}
	
	private void fillMap(String xmlPath) {
		try {
			SAXParser parser = SAXParserFactory.newInstance().newSAXParser();
			CommandsXMLHandler xmlHandler = new CommandsXMLHandler();
			parser.parse(new InputSource(xmlPath), xmlHandler);
			map = xmlHandler.getCommands();
		} catch (ParserConfigurationException|SAXException|IOException e) {
			e.printStackTrace();
		}
	}
}

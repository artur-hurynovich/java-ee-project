package com.hurynovich.mus_site.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;

public class HashtagsHandler {
	
	public static String execute(String hashtags) {
		ResourceBundle bundle = BundleFactory.getInstance().getConfigBundle();
		String splitPattern = bundle.getString("config.hashtags.split.regex");
		String [] hashtagsArray = hashtags.toLowerCase().split(splitPattern);
		HashSet<String> hashtagsSet = new HashSet<>(Arrays.asList(hashtagsArray));
		StringBuilder result = new StringBuilder();
		hashtagsSet.forEach(h -> result.append(h + " "));
		
		return result.toString().trim();
	}
}

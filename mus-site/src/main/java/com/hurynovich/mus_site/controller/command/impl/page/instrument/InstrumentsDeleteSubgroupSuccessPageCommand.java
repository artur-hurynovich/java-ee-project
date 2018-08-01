package com.hurynovich.mus_site.controller.command.impl.page.instrument;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import com.hurynovich.mus_site.controller.command.ICommand;
import com.hurynovich.mus_site.util.BundleFactory;

public class InstrumentsDeleteSubgroupSuccessPageCommand implements ICommand {
	private final ResourceBundle pathsundle = BundleFactory.getInstance().getPathsBundle();
	private final String page = pathsundle.getString("instruments.delete_subgroup_success");

	@Override
	public String execute(HttpServletRequest request) {
		return page;
	}
	
	@Override
	public boolean isRedirect() {
		return false;
	}
}

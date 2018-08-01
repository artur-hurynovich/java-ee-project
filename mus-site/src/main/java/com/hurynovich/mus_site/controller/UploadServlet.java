package com.hurynovich.mus_site.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.hurynovich.mus_site.bean.instrument.Instrument;
import com.hurynovich.mus_site.bean.instrument.InstrumentsField;
import com.hurynovich.mus_site.bean.instrument.InstrumentsGroup;
import com.hurynovich.mus_site.bean.review.Review;
import com.hurynovich.mus_site.bean.user.User;
import com.hurynovich.mus_site.exception.InstrumentDAOException;
import com.hurynovich.mus_site.exception.ReviewDAOException;
import com.hurynovich.mus_site.service.ServiceFactory;
import com.hurynovich.mus_site.service.instrument.IInstrumentService;
import com.hurynovich.mus_site.service.review.IReviewService;
import com.hurynovich.mus_site.util.BundleFactory;
@WebServlet(urlPatterns = "/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 2, 
	maxRequestSize = 1024 * 1024 * 3)
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 5934405718561734908L;
	
	private final ResourceBundle pathsBundle = BundleFactory.getInstance().getPathsBundle();
	private final String uploadSuccessPage = pathsBundle.getString("instruments.read");
	private final String errorPage = pathsBundle.getString("error");
	private String page;
	
	private final IReviewService reviewsService = ServiceFactory.getInstance().getReviewService();
	private final IInstrumentService instrumentService = 
		ServiceFactory.getInstance().getInstrumentService();
	
	private final static String IMG_FOLDER = "img";
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		String instrumentIdParam = request.getParameter("instrumentId");
		int instrumentId = Integer.valueOf(instrumentIdParam);
		
		String appPath = request.getServletContext().getRealPath("");
        String savePath = appPath + IMG_FOLDER;
        String fileName = null;
        for (Part part : request.getParts()) {
        	fileName = "instrument_" + instrumentId + ".jpg";
            part.write(savePath + File.separator + fileName);
        }
        try {
	        instrumentService.addPhoto(instrumentId, IMG_FOLDER + File.separator + fileName);
			
			List<InstrumentsGroup> groups = instrumentService.getGroups();
			request.setAttribute("groups", groups);
			
			Instrument instrument = instrumentService.getInstrumentById(instrumentId);
			request.setAttribute("instrument", instrument);
			
			Object user = request.getSession().getAttribute("user");
			if (user != null) {
				int userId = ((User) user).getId();
				boolean rated = instrumentService.instrumentRatedByUser(instrumentId, userId);
				request.setAttribute("rated", rated);
			}
			
			String mark = null;
			String model = null;
			List<InstrumentsField> fields = instrument.getFields();
			for (InstrumentsField field : fields) {
				if (field.getNameEn().equals("Mark")) {
					mark = field.getCurrentValue().getNameEn();
				}
				if (field.getNameEn().equals("Model")) {
					model = field.getCurrentValue().getNameEn();
				}
			}
			
			String reviewLang = request.getSession().getAttribute("lang").toString();
		
			List<Review> reviews = reviewsService.getReviewsByHashtags(reviewLang, 1, mark + " " 
				+ model);
			request.setAttribute("reviews", reviews);
			page = uploadSuccessPage;
		} catch (ReviewDAOException|InstrumentDAOException e) {
			page = errorPage;
		}
		
		request.getRequestDispatcher(page).forward(request, response);
	}
}

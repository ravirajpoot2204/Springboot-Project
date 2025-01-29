package search.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class FileformController {
	@RequestMapping("/")
	public String fileform() {
		System.out.println("Fileform upload page");
		return "fileform";
	}

	@RequestMapping(path/* or value */ = "/uploadimage", method = RequestMethod.POST)
	public String uploadImage(@RequestParam("image") CommonsMultipartFile file, HttpSession s, Model model) {

		/*
		 * System.out.println("file upload handleer");
		 * System.out.println(file.getContentType());
		 * System.out.println(file.getName());
		 * System.out.println(file.getOriginalFilename());
		 * System.out.println(file.getSize());
		 * System.out.println(file.getStorageDescription());
		 * System.out.println(file.getResource());
		 */
		System.out.println(file);
		byte[] data = file.getBytes();
		/* we have to save the file to server */
		String path = s.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "resources" + File.separator
				+ "image" + File.separator + file.getOriginalFilename();
		System.out.println(path);
		try {
			FileOutputStream fos = new FileOutputStream(path);
			fos.write(data);

			fos.close();
			System.out.println("Uploaded");
			model.addAttribute("filename", file.getOriginalFilename());
			model.addAttribute("msg", "Uploaded Successfully");
		} catch (Exception e) {
			model.addAttribute("msg", "Got an error!");
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fileform";
		}


		return "filesuccess";
	}
}

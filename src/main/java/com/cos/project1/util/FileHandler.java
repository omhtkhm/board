package com.cos.project1.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cos.project1.model.Image;

public class FileHandler {
	public static List<Image> generateImageList(List<MultipartFile> files, HttpServletRequest req) {
		// =============================================================== 이미지 업로드 및 파일
		ServletContext cts = req.getServletContext();
		String webPath = "/static/upload";
		String realPath = cts.getRealPath(webPath);
		// 관련
		// 핵심 1) DB -> 파일이름 저장 2) 파일을 디렉토리에 저장
		List<Image> imgs = new ArrayList<Image>(); //
		if (files.size() != 0) { // 왜? 파일이 엠티인데 왜 타냐? --> isempty 말고 size(), 배열이 0이 아니면 밑으로 타라
			for (MultipartFile file : files) {
				if (!file.isEmpty()) { //
					String contentType = file.getContentType();
					String originalFileExtension = "";
					// 확장자 명이 없으면 이 파일은 잘 못 된 것이다
					if (ObjectUtils.isEmpty(contentType)) { // 확장자(html포맷값) 체크, 없으면 추가
						String name1 = file.getOriginalFilename();
						if (name1.length() >= 4) {
							boolean isExt = name1.substring(name1.length() - 4, name1.length() - 3).equals(".");
							if (isExt) {
								originalFileExtension = name1.substring(name1.length() - 4, name1.length());
							}
						}
					} else {
						if (contentType.contains("image/jpeg")) {
							originalFileExtension = ".jpg";
						} else if (contentType.contains("image/png")) {
							originalFileExtension = ".png";
						} else if (contentType.contains("image/gif")) {
							originalFileExtension = ".gif";
						}
						// 다른 파일 명이면 .....
						// *파일 업로드가 안되면 오류걸림 해결방법 (왜냐 파일 확장자 4자리를 찾도록 설계해놓음)
						//
						else {
							String name1 = file.getOriginalFilename();
							if (name1.length() >= 4) {
								boolean isExt = name1.substring(name1.length() - 4, name1.length() - 3).equals(".");
								if (isExt) {
									originalFileExtension = name1.substring(name1.length() - 4, name1.length());
								}
							}
//									System.out.println(a);
						}
					}
					Image img = new Image();
					String orginalFileName = file.getOriginalFilename(); // 필터 두개 필요
					img.setOriginalFileName(orginalFileName);
					String fileName = String.format("%s",
							RandomStringUtils.randomAlphanumeric(8) + originalFileExtension);
					img.setFileName(fileName);
					imgs.add(img);
					String filePath = realPath + "\\" + fileName;
					System.out.println(filePath);
					try {
						file.transferTo(new File(filePath));
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		// ===============================================================
		return imgs;

	}
}

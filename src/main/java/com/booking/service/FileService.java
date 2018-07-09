package com.booking.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.booking.dto.User;

public interface FileService {
	public String selectSaveFileName(int fileId);
	public List<Integer> selectProductImageList(int productId);
	public List<Integer> selectProductNoticeImageList(int productId);
	public Integer selectProductInformationImage(int productId);
	public List<Integer> uploadFile(MultipartFile[] files, HttpServletRequest request, int userId);
}

package com.booking.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.booking.dao.ProductDao;
import com.booking.dao.ProductPriceDao;
import com.booking.dto.ProductDetailDto;
import com.booking.dto.ProductDto;
import com.booking.dto.ProductPriceDto;
import com.booking.dto.ProductReservationDto;
import com.booking.dto.ProductSummaryDto;
import com.booking.service.FileService;
import com.booking.service.ProductService;
import com.booking.service.ReviewService;

@Service
public class ProductServiceImpl implements ProductService{
	
	final static int PRODUCT_UNIT = 10;
	private ProductDao productDao;
	private ProductPriceDao productPriceDao;
	private FileService fileService;
	private ReviewService reviewService;
	
	@Autowired
	public ProductServiceImpl(ProductDao productDao, ProductPriceDao productPriceDao, 
			FileService fileService, ReviewService reviewService) {
		this.productDao = productDao;
		this.productPriceDao = productPriceDao;
		this.fileService = fileService;
		this.reviewService = reviewService;
	}

	@Override
	public List<ProductDto> getList(int categoryId, int page) {
		List<ProductDto> list = (categoryId == 0 ? selectAll(page) : selectByCategory(categoryId, page * PRODUCT_UNIT));
		return list;
	}
	
	@Override
	public List<ProductDto> selectAll(int page) {
		return productDao.selectAll(page * PRODUCT_UNIT);
	}

	@Override
	public List<ProductDto> selectByCategory(int categoryId, int page) {
		Map<String, Integer> map = new HashMap<>();
		map.put("categoryId", categoryId);
		map.put("page", page * PRODUCT_UNIT);
		return productDao.selectByCategory(map);
	}

	@Override
	public ProductSummaryDto selectProductSummary(int productId) {
		return productDao.selectProductSummary(productId);
	}
	
	@Override
	public ProductDetailDto selectProductDetail(int productId) {
		ProductDetailDto productDetailDto = productDao.selectProductDetail(productId);
		productDetailDto.setBannerImageIdList(fileService.selectProductImageList(productId));
		productDetailDto.setReviews(reviewService.selectProductReviewList(productId, 0, 3));
		productDetailDto.setNoticeImageIdList(fileService.selectProductNoticeImageList(productId));
		Integer informationImageId = fileService.selectProductInformationImage(productId);
		if(informationImageId != null)
			productDetailDto.setDescriptionImageId(fileService.selectProductInformationImage(productId));
		
		return productDetailDto;
	}

	@Override
	public List<ProductPriceDto> selectProductPrice(int productId) {
		return productPriceDao.selectProductPrice(productId);
	}
	
	@Override
	public ProductReservationDto selectProductReservation(int productId) {
		ProductReservationDto productReservationDto = productDao.selectProductReservation(productId);
		productReservationDto.setProductPrices(productPriceDao.selectProductPrice(productId));
		return productReservationDto;
	}

}

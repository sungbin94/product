package com.kh.product.domain.dao;

import com.kh.product.domain.Product;

import java.util.List;

public interface ProductDAO {

  /**
   * 등록
   * @param product 상품정보
   * @return
   */
  Product save(Product product);

  /**
   * 조회
   * @param productId 상품아이디
   * @return 상품
   */
  Product findById(Long productId);

  /**
   * 수정
   * @param product 수정할 상품정보
   */
  void update(Long productId, Product product);

  /**
   * 삭제
   * @param productId 상품아이디
   */
  void delete(Long productId);

  /**
   * 목록
   * @return 상품전체
   */
  List<Product> findAll();
}


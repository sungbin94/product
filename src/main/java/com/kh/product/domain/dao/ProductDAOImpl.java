package com.kh.product.domain.dao;

import com.kh.product.domain.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ProductDAOImpl implements ProductDAO {

  private final JdbcTemplate jt;

  //등록
  @Override
  public Product save(Product product) {
    StringBuffer sql = new StringBuffer();
    sql.append("insert into product values(product_product_id_seq.nextval,?,?,?)");

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jt.update(new PreparedStatementCreator(){
      @Override
      public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
        PreparedStatement pstmt = con.prepareStatement(sql.toString(),new String[]{"product_id"});
        pstmt.setString(1,product.getPname());
        pstmt.setInt(2,product.getCount());
        pstmt.setInt(3,product.getPrice());
        return pstmt;
      }
    }, keyHolder);

    Long product_id = Long.valueOf(keyHolder.getKeys().get("product_id").toString());

    product.setProductId(product_id);
    return product;
  }

  //조회
  @Override
  public Product findById(Long productId) {
    StringBuffer sql = new StringBuffer();
    sql.append("select product_id as productId, pname, count, price ");
    sql.append(  "from product ");
    sql.append( "where product_id = ? ");

    Product product = null;
    try {
      product = jt.queryForObject(  //단일레코드
          sql.toString(),new BeanPropertyRowMapper<>(Product.class),productId);
    } catch (EmptyResultDataAccessException e) {
      log.info("삭제대상 상품이 없습니다 상품아이디={}",productId);
    }

    return product;
  }

  //수정
  @Override
  public void update(Long productId, Product product) {
    StringBuffer sql = new StringBuffer();
    sql.append("update product ");
    sql.append("   set pname = ?, ");
    sql.append("       quantity = ?, ");
    sql.append("       price = ? ");
    sql.append(" where product_id = ? ");

    jt.update(sql.toString(),product.getPname(),product.getCount(),product.getPrice(),productId);
  }

  //삭제`
  @Override
  public void delete(Long productId) {
    String sql = "delete from product where product_id = ? ";
    jt.update(sql, productId);
  }


  @Override
  public List<Product> findAll() {
    StringBuffer sql = new StringBuffer();
    sql.append("select product_id, pname, count, price ");
    sql.append("from product ");

    List<Product> result = jt.query(sql.toString(),new BeanPropertyRowMapper<>(Product.class));
    return result;
  }
}

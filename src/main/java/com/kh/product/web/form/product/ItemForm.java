package com.kh.product.web.form.product;


import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ItemForm {
  private Long productId;
  private String Pname;
  private Integer count;
  private Integer price;
}

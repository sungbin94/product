package com.kh.product.web.form.product;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EditForm {
  private Long productId;
  private String Pname;
  private Integer count;
  private Integer price;
}

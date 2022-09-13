package com.kh.product.web.form.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class SaveForm {
  private String Pname;
  private Integer count;
  private Integer price;
}

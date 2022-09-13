package com.kh.product.web;


import com.kh.product.domain.Product;
import com.kh.product.domain.svc.ProductSVC;
import com.kh.product.web.form.product.EditForm;
import com.kh.product.web.form.product.SaveForm;
import com.kh.product.web.form.product.ItemForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductSVC productSVC;

  //등록양식
  @GetMapping("/add")
  public String saveForm() {

    return "product/addForm"; //등록화면
  }

  //등록처리
  @PostMapping("/add")
  public String save(SaveForm saveForm) {
    log.info("saveForm:{}", saveForm);

    Product product = new Product();
    product.setPname(saveForm.getPname());
    product.setCount(saveForm.getCount());
    product.setPrice(saveForm.getPrice());

    Product savedProduct = productSVC.save(product);

    return "redirect:/products/" + savedProduct.getProductId(); //상세 url
  }

  //상품개별조회
  @GetMapping("/{pid}")
  public String findByProductId(
      @PathVariable("pid") Long pid,
      Model model
  ){
    //db에서 상품조회
    Product findedProduct = productSVC.findById(pid);

    //Product => ItemForm 복사
    ItemForm itemForm = new ItemForm();
    itemForm.setProductId(findedProduct.getProductId());
    itemForm.setPname(findedProduct.getPname());
    itemForm.setCount(findedProduct.getCount());
    itemForm.setPrice(findedProduct.getPrice());

    //view에서 참조하기위해 model객체에 저장
    model.addAttribute("itemForm",itemForm);

    return "product/itemForm"; //상품 상세 view
  }

  //수정양식
  @GetMapping("/{pid}/edit")
  public String updateForm(@PathVariable("pid") Long pid, Model model) {

    Product findedProduct = productSVC.findById(pid);

    EditForm editForm = new EditForm();
    editForm.setProductId(findedProduct.getProductId());
    editForm.setPname(findedProduct.getPname());
    editForm.setCount(findedProduct.getCount());
    editForm.setPrice(findedProduct.getPrice());

    model.addAttribute("editForm", editForm);
    return "product/editForm";
  }

  //수정처리
  @PostMapping("/{pid}/edit")
  public String update(@PathVariable("pid") Long pid, EditForm editForm) {
    Product product = new Product();
    product.setProductId(pid);
    product.setPname(editForm.getPname());
    product.setCount(editForm.getCount());
    product.setPrice(editForm.getPrice());

    productSVC.update(pid, product);
    return "redirect:/products/" + pid;
  }

  //삭제처리
  @GetMapping("/{pid}/del")
  public String delete(@PathVariable("pid") Long pid) {

    productSVC.delete(pid);
    return "redirect:/products";
  }

  //목록화면
  @GetMapping
  public String list(Model model){

    List<Product> list = productSVC.findAll();
    model.addAttribute("list",list);
    return "product/all"; //전체목록 view
  }
}

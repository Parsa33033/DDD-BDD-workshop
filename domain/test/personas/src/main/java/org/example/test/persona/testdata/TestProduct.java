package org.example.test.persona.testdata;

import org.example.dto.ProductData;
import org.example.test.data.model.TestProductData;
import org.example.test.persona.product.Jeans;
import org.example.test.persona.product.Shoes;
import org.example.test.persona.product.Tshirt;

public enum TestProduct implements TestProductData {
  Shoes(new Shoes()),
  Tshirt(new Tshirt()),
  Jeans(new Jeans());

  TestProductData testProductData;

  TestProduct(TestProductData testProductData) {
    this.testProductData = testProductData;
  }


  @Override
  public ProductData product() {
    return testProductData.product();
  }
}

package com.ika.warehouse.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class ContainArticlesDTO {

  @JsonIgnore
  private Long id;
  @JsonProperty("art_id")
  private Long artId;
  @JsonProperty("amount_of")
  private Long amountOf;
  private ProductsDTO product;

  public Long getId() {
    return id;
  }

  public ContainArticlesDTO setId(Long id) {
    this.id = id;
    return this;
  }

  public Long getArtId() {
    return artId;
  }

  public ContainArticlesDTO setArtId(Long artId) {
    this.artId = artId;
    return this;
  }

  public Long getAmountOf() {
    return amountOf;
  }

  public ContainArticlesDTO setAmountOf(Long amountOf) {
    this.amountOf = amountOf;
    return this;
  }

  public ProductsDTO getProduct() {
    return product;
  }

  public ContainArticlesDTO setProduct(ProductsDTO product) {
    this.product = product;
    return this;
  }
}

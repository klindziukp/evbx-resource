package com.evbx.resource.model.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@JsonIgnoreProperties({
  "createdAt",
  "updatedAt",
  "deletedAt",
  "createdBy",
  "updatedBy",
  "deletedBy"
})
public class ResourceEntity extends UpdatableEntity {

  @Column(name = "text")
  @JsonProperty("text")
  @Size(max = 10_000, min = 5, message = "{item.invalid-size}")
  @NotEmpty(message = "'text' {item.mandatory-field}")
  private String text;

  @Column(name = "description")
  @JsonProperty("description")
  @Size(max = 255, min = 5, message = "{item.invalid-size}")
  @NotEmpty(message = "'description' {item.mandatory-field}")
  private String description;
}

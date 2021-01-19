package com.evbx.resource.model.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ErrorResponse {

  @JsonProperty("timestamp")
  private String timestamp;

  @JsonProperty("status")
  private Integer status;

  @JsonProperty("error")
  private String error;

  @JsonProperty("errorMessages")
  private List<String> errorMessages;

  @JsonProperty("message")
  private String message;

  @JsonProperty("path")
  private String path;
}

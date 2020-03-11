package com.evbx.resource.model.domain;

import com.evbx.resource.model.common.ResourceEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "e_books")
@Data
@EqualsAndHashCode(callSuper = true)
public class Book extends ResourceEntity {

    @Column(name = "book_name")
    @JsonProperty("bookName")
    @Size(max = 255, min = 5, message = "{item.invalid-size}")
    @NotEmpty(message = "'bookName' {item.mandatory-field}")
    private String bookName;

    // Should be copied to subclasses for correct working
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

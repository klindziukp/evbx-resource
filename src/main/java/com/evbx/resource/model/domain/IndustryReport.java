package com.evbx.resource.model.domain;

import com.evbx.resource.model.common.ResourceEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "industry_report")
@SQLDelete(sql = "UPDATE industry_report SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "deleted_at IS null")
@Data
@EqualsAndHashCode(callSuper = true)
@JsonPropertyOrder({ "id", "industryName", "description", "text" })
public class IndustryReport extends ResourceEntity {

    @Column(name = "industry_name")
    @JsonProperty("industryName")
    @Size(max = 255, min = 5, message = "{item.invalid-size}")
    @NotEmpty(message = "'industryName' {item.mandatory-field}")
    private String industryName;

    // Should be copied to subclasses for correct working
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}

package com.evbx.resource.util;

import com.evbx.resource.model.common.UpdatableEntity;
import java.beans.FeatureDescriptor;
import java.util.Calendar;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/** Utility for update Entities. */
public final class UpdateUtil {

  private UpdateUtil() {}

  /**
   * Updates item
   *
   * @param source source item
   * @param target target item
   * @param <T> type of item
   */
  public static <T extends UpdatableEntity> void updateItem(T source, T target) {
    String[] ignoredProperties = getNullPropertyNames(source);
    BeanUtils.copyProperties(source, target, ignoredProperties);
    target.setUpdatedBy(AuthUtil.getUserName());
    target.setUpdatedAt(Calendar.getInstance().getTime());
  }

  private static <T> String[] getNullPropertyNames(T item) {
    final BeanWrapper wrappedSource = new BeanWrapperImpl(item);
    return Stream.of(wrappedSource.getPropertyDescriptors())
        .map(FeatureDescriptor::getName)
        .filter(propertyName -> Objects.isNull(wrappedSource.getPropertyValue(propertyName)))
        .toArray(String[]::new);
  }
}

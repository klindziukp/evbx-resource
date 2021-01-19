package com.evbx.resource.data;

import com.evbx.resource.layer.repository.projection.NameProjection;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

public final class TestDataProjectionStorage {

  private TestDataProjectionStorage() {}

  private static ProjectionFactory factory = new SpelAwareProxyProjectionFactory();

  public static List<NameProjection> getBookNameProjections() {
    List<NameProjection> projections = new ArrayList<>();
    projections.add(getNameProjection("Book-name-000"));
    projections.add(getNameProjection("Book-name-111"));
    projections.add(getNameProjection("Book-name-222"));
    return projections;
  }

  public static List<NameProjection> getIndustryReportNameProjections() {
    List<NameProjection> projections = new ArrayList<>();
    projections.add(getNameProjection("Industry-name-00"));
    projections.add(getNameProjection("Industry-name-11"));
    projections.add(getNameProjection("Industry-name-22"));
    return projections;
  }

  public static List<NameProjection> getSpecNameProjections() {
    List<NameProjection> projections = new ArrayList<>();
    projections.add(getNameProjection("Specification-name-0"));
    projections.add(getNameProjection("Specification-name-1"));
    projections.add(getNameProjection("Specification-name-2"));
    return projections;
  }

  private static NameProjection getNameProjection(String name) {
    NameProjection nameProjection = factory.createProjection(NameProjection.class);
    nameProjection.setName(name);
    return nameProjection;
  }
}

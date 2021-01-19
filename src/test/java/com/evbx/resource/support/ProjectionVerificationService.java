package com.evbx.resource.support;

import com.evbx.resource.layer.repository.projection.NameProjection;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.SoftAssertions;

public final class ProjectionVerificationService {

  public void verifyNameProjections(List<NameProjection> actual, List<NameProjection> expected) {
    SoftAssertions softAssertions = new SoftAssertions();
    softAssertions.assertThat(actual.size()).as("Sizes are not equal").isEqualTo(expected.size());
    for (int i = 0; i < expected.size(); i++) {
      String actualName =
          Optional.ofNullable(actual.get(i).getName()).orElse("Unable to get actual name.");
      String expectedName =
          Optional.ofNullable(expected.get(i).getName()).orElse("Unable to get expected name.");
      softAssertions.assertThat(actualName).as("Names are not equal.").isEqualTo(expectedName);
    }
    softAssertions.assertAll();
  }
}

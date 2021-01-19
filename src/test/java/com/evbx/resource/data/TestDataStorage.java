package com.evbx.resource.data;

import com.evbx.resource.model.common.UpdatableEntity;
import com.evbx.resource.model.domain.Book;
import com.evbx.resource.model.domain.IndustryReport;
import com.evbx.resource.model.domain.Specification;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public final class TestDataStorage {

  private TestDataStorage() {}

  public static <T> T getRandomItem(List<T> items) {
    return items.get(new Random().nextInt(items.size()));
  }

  public static <T extends UpdatableEntity> List<Long> getIdsFromList(List<T> items) {
    return items.stream().map(UpdatableEntity::getId).collect(Collectors.toList());
  }

  public static Book getMutationBook() {
    Book mutationBook = new Book();
    mutationBook.setBookName("Book-name-999");
    mutationBook.setDescription("Book-description-999");
    mutationBook.setText("Book-text-999");
    mutationBook.setCreatedBy("script");
    mutationBook.setCreatedAt(Calendar.getInstance().getTime());
    return mutationBook;
  }

  public static IndustryReport getMutationIndustryReport() {
    IndustryReport report = new IndustryReport();
    report.setIndustryName("Industry-name-888");
    report.setDescription("Industry-description-888");
    report.setText("Industry-text-888");
    report.setCreatedBy("script");
    report.setCreatedAt(Calendar.getInstance().getTime());
    return report;
  }

  public static Specification getMutationSpec() {
    Specification mutationSpec = new Specification();
    mutationSpec.setSpecificationName("Specification-name-777");
    mutationSpec.setDescription("Specification-description-777");
    mutationSpec.setText("Specification-text-777");
    mutationSpec.setCreatedBy("script");
    mutationSpec.setCreatedAt(Calendar.getInstance().getTime());
    return mutationSpec;
  }

  public static List<IndustryReport> getTestIndustryReports() {
    List<IndustryReport> reports = new ArrayList<>();

    IndustryReport firstReport = new IndustryReport();
    firstReport.setId(100L);
    firstReport.setIndustryName("Industry-name-00");
    firstReport.setDescription("Industry-description-00");
    firstReport.setText("Industry-text-00");
    firstReport.setCreatedBy("script");
    reports.add(firstReport);

    IndustryReport secondReport = new IndustryReport();
    secondReport.setId(101L);
    secondReport.setIndustryName("Industry-name-11");
    secondReport.setDescription("Industry-description-11");
    secondReport.setText("Industry-text-11");
    secondReport.setCreatedBy("script");
    reports.add(secondReport);

    IndustryReport thirdReport = new IndustryReport();
    thirdReport.setId(102L);
    thirdReport.setIndustryName("Industry-name-22");
    thirdReport.setDescription("Industry-description-22");
    thirdReport.setText("Industry-text-22");
    thirdReport.setCreatedBy("script");
    reports.add(thirdReport);
    return reports;
  }

  public static List<Specification> getTestSpecs() {
    List<Specification> specifications = new ArrayList<>();

    Specification firstSpec = new Specification();
    firstSpec.setId(100L);
    firstSpec.setSpecificationName("Specification-name-0");
    firstSpec.setDescription("Specification-description-0");
    firstSpec.setText("Specification-text-0");
    firstSpec.setCreatedBy("script");
    specifications.add(firstSpec);

    Specification secondSpec = new Specification();
    secondSpec.setId(101L);
    secondSpec.setSpecificationName("Specification-name-1");
    secondSpec.setDescription("Specification-description-1");
    secondSpec.setText("Specification-text-1");
    secondSpec.setCreatedBy("script");
    specifications.add(secondSpec);

    Specification thirdSpec = new Specification();
    thirdSpec.setId(102L);
    thirdSpec.setSpecificationName("Specification-name-2");
    thirdSpec.setDescription("Specification-description-2");
    thirdSpec.setText("Specification-text-2");
    thirdSpec.setCreatedBy("script");
    specifications.add(thirdSpec);

    return specifications;
  }

  public static List<Book> getTestBooks() {
    List<Book> books = new ArrayList<>();

    Book firstBook = new Book();
    firstBook.setId(100L);
    firstBook.setBookName("Book-name-000");
    firstBook.setDescription("Book-description-000");
    firstBook.setText("Book-text-000");
    firstBook.setCreatedBy("script");
    books.add(firstBook);

    Book secondBook = new Book();
    secondBook.setId(101L);
    secondBook.setBookName("Book-name-111");
    secondBook.setDescription("Book-description-111");
    secondBook.setText("Book-text-111");
    secondBook.setCreatedBy("script");
    books.add(secondBook);

    Book thirdBook = new Book();
    thirdBook.setId(102L);
    thirdBook.setBookName("Book-name-222");
    thirdBook.setDescription("Book-description-222");
    thirdBook.setText("Book-text-222");
    thirdBook.setCreatedBy("script");
    books.add(thirdBook);
    return books;
  }
}

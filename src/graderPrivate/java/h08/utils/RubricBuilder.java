package h08.utils;

import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.Rubric;

import java.util.stream.Stream;

public class RubricBuilder {
    private final String title;
    private final CriterionBuilder[] criteriaBuilders;

    public RubricBuilder(String title, CriterionBuilder... criteriaBuilders) {
        this.title = title;
        this.criteriaBuilders = criteriaBuilders;
    }

    public Rubric build() {
        return Rubric.builder()
            .title(title)
            .addChildCriteria(Stream.of(criteriaBuilders).map(CriterionBuilder::build).toArray(Criterion[]::new))
            .build();
    }
}

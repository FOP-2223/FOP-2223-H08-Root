package h08.utils;

import org.sourcegrade.jagr.api.rubric.Criterion;

import java.util.stream.Stream;

public class ChildCollectionCriterionBuilder extends CriterionBuilder {
    private final CriterionBuilder[] builders;

    public ChildCollectionCriterionBuilder(String shortDescription, CriterionBuilder... builders) {
        super(shortDescription);
        this.builders = builders;
    }

    @Override
    public Criterion build() {
        return builderWithShortDescription()
            .addChildCriteria(Stream.of(builders).map(CriterionBuilder::build).toArray(Criterion[]::new))
            .build();
    }
}

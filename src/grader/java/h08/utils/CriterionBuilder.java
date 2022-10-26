package h08.utils;

import org.sourcegrade.jagr.api.rubric.Criterion;

public abstract class CriterionBuilder {
    protected final String shortDescription;

    protected CriterionBuilder(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    protected Criterion.Builder builderWithShortDescription() {
        return Criterion.builder().shortDescription(shortDescription);
    }

    public abstract Criterion build();
}

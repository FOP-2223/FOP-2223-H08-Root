package h08.utils;

import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.Grader;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;

public class OnePointCriterionBuilder extends CriterionBuilder {
    private final JUnitTestRef[] testRefs;

    public OnePointCriterionBuilder(String shortDescription, JUnitTestRef... testRefs) {
        super(shortDescription);
        this.testRefs = testRefs;
    }

    @Override
    public Criterion build() {
        var testAwareBuilder = Grader.testAwareBuilder();

        for (var testRef : testRefs) {
            testAwareBuilder = testAwareBuilder.requirePass(testRef);
        }

        return builderWithShortDescription()
            .grader(testAwareBuilder.pointsPassedMax().pointsFailedMin().build())
            .build();
    }
}

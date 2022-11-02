package h08;

import com.fasterxml.jackson.databind.node.ArrayNode;
import h08.utils.ChildCollectionCriterionBuilder;
import h08.utils.OnePointCriterionBuilder;
import h08.utils.RubricBuilder;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;
import org.sourcegrade.jagr.api.testing.TestCycle;

public class H08_RubricProvider implements RubricProvider {
//    public static final Criterion H1_2 = Criterion.builder()
//        .shortDescription("H1.2 | Prüfen der Ausnahmefälle")
//        .build();
//
//    public static final Criterion H2_T1 = Criterion.builder()
////        .shortDescription("Klasse RobotWithOffspring ist korrekt deklariert.")
//        .grader(
//            Grader.testAwareBuilder()
////                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H2.class.getMethod(
////                    "t01")))
//                .pointsPassedMax()
//                .pointsFailedMin()
//                .build())
//        .build();
//
//    public static final Criterion H2 = Criterion.builder()
//        .shortDescription("H2 | Eigene Exception-Klassen")
//        .addChildCriteria(H2_T1)
//        .build();
//
//    public static final Criterion H3_1 = Criterion.builder()
//        .shortDescription("H3.1 | Die Klasse Preconditions")
//        .build();
//
//    public static final Criterion H3_2 = Criterion.builder()
//        .shortDescription("H3.2 | Verwendung des Preconditions-Frameworks")
//        .build();
//
//    public static final Criterion H3 = Criterion.builder()
//        .shortDescription("H1 | Eigenes Preconditions-Framework")
//        .addChildCriteria(H3_1, H3_2)
//        .build();

    @Override
    public Rubric getRubric() {
        var H1_1_T1 = new OnePointCriterionBuilder("Die Methode \"addUp\" berechnet die Summe korrekt.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H1_1.class.getMethod("addUpCalculatesSumCorrectly", ArrayNode.class, double.class)));

        var H1_1 = new ChildCollectionCriterionBuilder("H1.1 | Berechnung der Summe", H1_1_T1);

        var H1 = new ChildCollectionCriterionBuilder("H1 | Methode mit RuntimeExceptions", H1_1);

        var H4_T1 = new OnePointCriterionBuilder("Die Methode \"print\" gibt bei korrekter Eingabe die Summe aus.",
            JUnitTestRef.ofMethod(() ->
                TutorTests_H4.class.getMethod("printOutputsSumForCorrectParameters", TestCycle.class)));

        var H4 = new ChildCollectionCriterionBuilder("H4 | Print-Methode", H4_T1);

        var H5_1_T1 = new OnePointCriterionBuilder(
            "Die Methode \"testSum\" wirft einen AssertionError, wenn der ArrayCalculator die Summe nicht korrekt " +
                "berechnet.",
            JUnitTestRef.ofMethod(() -> TutorTests_H5_1.class.getMethod("testSumThrowsExceptionWhenSumNotCorrect")));
        var H5_1_T2 = new OnePointCriterionBuilder(
            "Die Methode \"testSum\" wirft einen AssertionError, wenn der ArrayCalculator eine Exception wirft.",
            JUnitTestRef.ofMethod(() -> TutorTests_H5_1.class.getMethod("testSumThrowsExceptionWhenCalculatorThrowsException")));
        var H5_1_T3 = new OnePointCriterionBuilder(
            "Die Methode \"testSum\" wirft keine Exception, wenn der ArrayCalculator die Summe korrekt berechnet.",
            JUnitTestRef.ofMethod(() -> TutorTests_H5_1.class.getMethod("testSumPassesWhenSumCorrect")));

        var H5_1 = new ChildCollectionCriterionBuilder("H5.1 | Testen der Summenberechnung", H5_1_T1, H5_1_T2, H5_1_T3);

        var H5_2_T1 = new OnePointCriterionBuilder(
            "Die Methode \"testException\" wirft einen AssertionError, wenn der ArrayCalculator keine Exception wirft oder " +
                "Datentyp der Exception nicht dem erwarteten entspricht.",
            JUnitTestRef.ofMethod(() -> TutorTests_H5_2.class.getMethod("testExceptionFailsWhenCalculatorThrowsNoException")),
            JUnitTestRef.ofMethod(() -> TutorTests_H5_2.class.getMethod("testExceptionFailsWhenExceptionTypeIsWrong")));
        var H5_2_T2 = new OnePointCriterionBuilder(
            "Methode \"testException\" wirft einen AssertionError, wenn die Botschaft der von ArrayCalculator geworfenen " +
                "Exception nicht der erwarteten Botschaft entspricht.",
            JUnitTestRef.ofMethod(() -> TutorTests_H5_2.class.getMethod("testExceptionFailsWhenMessageIsWrong")));
        var H5_2_T3 = new OnePointCriterionBuilder(
            "Methode \"testException\" wirft keinen Error, wenn der ArrayCalculator eine Exception wie erwartet wirft.",
            JUnitTestRef.ofMethod(() -> TutorTests_H5_2.class.getMethod("testExceptionPassesWhenExceptionIsThrownCorrectly")));

        var H5_2 = new ChildCollectionCriterionBuilder("H5.2 | Test der Ausnahmebehandlung", H5_2_T1, H5_2_T2, H5_2_T3);

        // H5 DONE
        var H5 = new ChildCollectionCriterionBuilder("H5 | Tests mit JUnit", H5_1, H5_2);

        var rubricBuilder = new RubricBuilder("H08 | Excéptions – Gotta catch ’em all!", H1, H4, H5);
        return rubricBuilder.build();
    }
}

package h08;

import org.sourcegrade.jagr.api.rubric.*;

public class H08_RubricProvider implements RubricProvider {
    public static final Criterion H1_1_T1 = Criterion.builder()
//        .shortDescription("Klasse RobotWithOffspring ist korrekt deklariert.")
        .grader(
            Grader.testAwareBuilder()
                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H1_1.class.getMethod(
                    "t01")))
                .pointsPassedMax()
                .pointsFailedMin()
                .build())
        .build();

    public static final Criterion H1_1 = Criterion.builder()
        .shortDescription("H1.1 | Berechnung der Summe")
        .addChildCriteria(H1_1_T1)
        .build();

    public static final Criterion H1_2 = Criterion.builder()
        .shortDescription("H1.2 | Prüfen der Ausnahmefälle")
        .build();

    public static final Criterion H1 = Criterion.builder()
        .shortDescription("H1 | Methode mit RuntimeExceptions")
        .addChildCriteria(H1_1, H1_2)
        .build();

    public static final Criterion H2_T1 = Criterion.builder()
//        .shortDescription("Klasse RobotWithOffspring ist korrekt deklariert.")
        .grader(
            Grader.testAwareBuilder()
//                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H2.class.getMethod(
//                    "t01")))
                .pointsPassedMax()
                .pointsFailedMin()
                .build())
        .build();

    public static final Criterion H2 = Criterion.builder()
        .shortDescription("H2 | Eigene Exception-Klassen")
        .addChildCriteria(H2_T1)
        .build();

    public static final Criterion H3_1 = Criterion.builder()
        .shortDescription("H3.1 | Die Klasse Preconditions")
        .build();

    public static final Criterion H3_2 = Criterion.builder()
        .shortDescription("H3.2 | Verwendung des Preconditions-Frameworks")
        .build();

    public static final Criterion H3 = Criterion.builder()
        .shortDescription("H1 | Eigenes Preconditions-Framework")
        .addChildCriteria(H3_1, H3_2)
        .build();

    public static final Criterion H4_T1 = Criterion.builder()
//        .shortDescription("Klasse RobotWithOffspring ist korrekt deklariert.")
        .grader(
            Grader.testAwareBuilder()
//                .requirePass(JUnitTestRef.ofMethod(() -> TutorTests_H4.class.getMethod(
//                    "t01")))
                .pointsPassedMax()
                .pointsFailedMin()
                .build())
        .build();

    public static final Criterion H4 = Criterion.builder()
        .shortDescription("H4 | Print-Methode")
        .addChildCriteria(H4_T1)
        .build();

    public static final Criterion H5_1 = Criterion.builder()
        .shortDescription("H5.1 | Testen der Summenberechnung")
        .build();

    public static final Criterion H5_2 = Criterion.builder()
        .shortDescription("H5.2 | Test der Ausnahmebehandlung")
        .build();

    public static final Criterion H5 = Criterion.builder()
        .shortDescription("H5 | Tests mit JUnit")
        .addChildCriteria(H5_1, H5_2)
        .build();

    public static final Rubric RUBRIC = Rubric.builder()
        .title("H08 | Excéptions – Gotta catch ’em all!")
        .addChildCriteria(H1, H2, H3, H4, H5)
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }
}

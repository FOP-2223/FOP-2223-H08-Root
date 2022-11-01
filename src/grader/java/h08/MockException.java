package h08;

public class MockException extends Exception {
    @Override
    public String getMessage() {
        return "pinguin";
    }
}

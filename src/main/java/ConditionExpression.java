/**
 * created by Kimone
 * date 2020/8/31
 */
public class ConditionExpression implements Expression {
    private String condition;

    public ConditionExpression(String condition) {
        this.condition = condition;
    }

    public String interpreter() {
        return condition;
    }
}

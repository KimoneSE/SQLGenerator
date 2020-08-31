/**
 * created by Kimone
 * date 2020/8/31
 */
public class NotExpression implements Expression {
    private Expression expression;

    public NotExpression(Expression expression) {
        this.expression = expression;
    }

    public String interpreter() {
        return "not "+expression.interpreter();
    }
}

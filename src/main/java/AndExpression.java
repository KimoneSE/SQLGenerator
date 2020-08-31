/**
 * created by Kimone
 * date 2020/8/31
 */
public class AndExpression implements Expression {
    private Expression leftExpression;
    private Expression rightExpression;

    public AndExpression(Expression leftExpression, Expression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    public String interpreter() {
        return "("+leftExpression.interpreter()+" and "+rightExpression.interpreter()+")";
    }
}

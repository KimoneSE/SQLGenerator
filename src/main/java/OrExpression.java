/**
 * created by Kimone
 * date 2020/8/31
 */
public class OrExpression implements Expression {
    private Expression leftExpression;
    private Expression rightExpression;

    public OrExpression(Expression leftExpression, Expression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
    }

    public String interpreter() {
        return "("+leftExpression.interpreter()+" or "+rightExpression.interpreter()+")";
    }
}

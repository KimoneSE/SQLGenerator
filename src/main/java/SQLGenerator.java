import java.util.Iterator;
import java.util.Stack;

/**
 * created by Kimone
 * date 2020/8/31
 */
public class SQLGenerator {
    public static String generateSQL(String input) {
        Stack<String> optStack = new Stack();
        Stack<String> expressionStack = new Stack();
        Stack<Character> charStack = new Stack();
        String exprStr = "";

        StringBuilder optBuilder = new StringBuilder();
        boolean isConnectorStart = false;
        for(int i=0;i<input.length();i++) {
            char ch = input.charAt(i);
            if((i>1&&input.charAt(i-1)==32&&input.charAt(i-2)==')') || ch=='!') {
                isConnectorStart=true;
                optBuilder.append(ch);
            }else{
                if((i>0&&input.charAt(i-1)=='!') || (ch==32 && optBuilder.length()>0)) {
                    isConnectorStart=false;
                    String curOpt = optBuilder.toString();
                    if(optStack.empty()){
                        optStack.push(curOpt);
                    } else {
                        compareAndCal(optStack,expressionStack,curOpt);
                    }
                    if (ch!=32) charStack.push(ch);
                    optBuilder.delete(0,optBuilder.length());
                }else if(ch!=')') {
                    if(!isConnectorStart&&ch!=32) charStack.push(ch);
                }else {
                    exprStr = ")";
                    while(true) {
                        char tmp = charStack.pop();
                        exprStr = tmp+exprStr;
                        if(tmp=='(') {
                            if(!exprStr.replaceAll(" ","").equals("()")){
                                expressionStack.push(exprStr);
                            }
                            exprStr="";
                            break;
                        }
                    }
                }
                if(isConnectorStart) optBuilder.append(ch);

            }

            if(ch=='('&&input.charAt(i+1)=='('){
                optStack.push(String.valueOf(ch));
            }else if(ch==')'&&input.charAt(i-1)==')'){
                directCal(optStack,expressionStack,true);
            }
        }
        if(!optStack.empty()) directCal(optStack,expressionStack,false);
        exprStr = "select * from customer where "+expressionStack.pop();
        return exprStr;
    }

    private static void compareAndCal(Stack<String> optStack, Stack<String> exprStack,String curOpt) {
        String peekOpt = optStack.peek();
        if(getPriority((peekOpt))>=getPriority(curOpt)) {
            String opt = optStack.pop();
            cal(opt,optStack,exprStack);

            if(optStack.empty()) {
                optStack.push(curOpt);
            }else {
                compareAndCal(optStack, exprStack, curOpt);
            }
        }else {
            optStack.push(curOpt);
        }
    }

    private static void directCal(Stack<String> optStack, Stack<String> exprStack, boolean isBracket) {

        String opt = optStack.pop();

        cal(opt,optStack,exprStack);

        if(isBracket){
            if("(".equals(optStack.peek())) {
                optStack.pop();
            }else {
                directCal(optStack,exprStack,isBracket);
            }
        }else {
            if(!optStack.empty()) {
                directCal(optStack, exprStack,isBracket);
            }
        }
    }

    private static void cal(String opt, Stack<String> optStack, Stack<String> exprStack) {
        if(opt.equals("!")) {
            String exprStr = exprStack.pop();
            Expression expr = new ConditionExpression(exprStr);
            NotExpression notExpression = new NotExpression(expr);
            exprStack.push(notExpression.interpreter());
        }else {
            String exprStr1 = exprStack.pop();
            String exprStr2 = exprStack.pop();
            if(opt.equals("AND")) {
                Expression expr1 = new ConditionExpression(exprStr2);
                Expression expr2 = new ConditionExpression(exprStr1);
                AndExpression andExpression = new AndExpression(expr1,expr2);
                exprStack.push(andExpression.interpreter());
            }
            if(opt.equals("OR")) {
                Expression expr1 = new ConditionExpression(exprStr2);
                Expression expr2 = new ConditionExpression(exprStr1);
                OrExpression orExpression = new OrExpression(expr1, expr2);
                exprStack.push(orExpression.interpreter());
            }
        }
    }
    private static int getPriority(String opt){
        if(opt.equals("!")){
            return 3;
        }
        if(opt.equals("AND")||opt.equals("OR")) {
            return 2;
        }
        return 0;
    }

}

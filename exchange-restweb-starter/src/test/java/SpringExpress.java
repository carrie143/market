import java.util.ArrayList;
import java.util.List;

import org.springframework.expression.AccessException;
import org.springframework.expression.BeanResolver;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import com.gop.web.base.auth.context.AuthExpressionContext;

public class SpringExpress {
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {

		ExpressionParser parser=new SpelExpressionParser();
	
		StandardEvaluationContext simpleContext = new StandardEvaluationContext(new AuthExpressionContext());
		
		//System.out.println(Simple.class.getMethod("exe",new Class[]{List.class}));
		List a=  (List) parser.parseExpression("exe({{'sss','1212'},{'321321','dsadsad'}})").getValue(simpleContext);
	    System.out.println(a);
	}
	
	static class Group{
		List<String> list ;
	}
	static class Simple {
	    public List<Boolean> booleanList = new ArrayList<Boolean>();
	    
	    public String add(String a )
	    {
	    	return a+"1111";
	    }
	    
	    public static List  exe(List<List> list)
	    {
	    	System.out.println(list);
	    	return list;
	    	
	    }
	}
	
	
}

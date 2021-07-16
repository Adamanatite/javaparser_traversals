/*
 * Name: Adam Fairlie
 * Registration Number: 2461352F
 * */

package detectors;

import java.util.List;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * Visitor for method calls to detect and report recursions
 * (including polymorphic recursions)
 * 
 * Task 2 of OOSE2 Assessed Exercise 2
 * 
 * @author Adam Fairlie <2461352f@student.gla.ac.uk>
 */
public class RecursionDetector extends VoidVisitorAdapter<List<Breakpoints>> {

	/**
	 * Visits method calls and checks for recursion, adding a breakpoint to the 
	 * collector for each recursion found
	 * 
	 * @param md The method call expression
	 * @param collector The list of breakpoints for recursions
	 */
	@Override
	public void visit(MethodCallExpr md, List<Breakpoints> collector) {
		super.visit(md, collector);
		
		String methodName = md.findAncestor(MethodDeclaration.class).get().getName().toString();
		String className = md.findAncestor(ClassOrInterfaceDeclaration.class).get().getName().toString();		
		
		//Check if method being called has same name as parent
	    if(md.getName().toString().equals(methodName)) {
			int begin = md.getRange().get().begin.line;
			int end =md.getRange().get().end.line;
			
			collector.add(new Breakpoints(className, methodName, begin, end));
	    }
	}
	
}

/*
 * Name: Adam Fairlie
 * Registration Number: 2461352F
 * */
package detectors;

import java.util.List;
import java.util.NoSuchElementException;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

/**
 * Visitor for flow control statements to detect and report
 * useless control flows
 * 
 * Task 1 of OOSE2 Assessed Exercise 2
 * 
 * @author Adam Fairlie <2461352f@student.gla.ac.uk>
 */
public class UselessFlowControlDetector extends VoidVisitorAdapter<List<Breakpoints>> {

	/**
	 * Returns whether a statement is empty (excluding comments)
	 * 
	 * @param s The statement
	 * @return True if the statement is empty or only comments
	 */
	public boolean isEmptyStatement(Statement s) {
		//If statement has no children
		if(s.getChildNodes().isEmpty()) return true;
		
		//Return false if any children are not comments
		for(Node n : s.getChildNodes()) {
			if(!(n instanceof Comment)) return false;
		}
		
		return true;
	}
	
	/**
	 * Gets information about a node and creates a new breakpoint
	 * 
	 * @param n The node to create a breakpoint for
	 * @param collector The list of breakpoints for useless control flows
	 */
	public void collect(Node n, List<Breakpoints> collector) {
		
			String methodName = n.findAncestor(MethodDeclaration.class).get().getName().toString();
			String className = n.findAncestor(ClassOrInterfaceDeclaration.class).get().getName().toString();	
		
			int begin = n.getRange().get().begin.line;
			int end = n.getRange().get().end.line;	
			collector.add(new Breakpoints(className, methodName, begin, end));
	}
	
	/**
	 * Visits if statements and checks for empty bodies
	 * 
	 * @param is The if statement
	 * @param collector The list of breakpoints for useless control flows
	 */
	@Override
	public void visit(IfStmt is, List<Breakpoints> collector) {
		super.visit(is, collector);
		if(this.isEmptyStatement(is.getThenStmt())) {
			this.collect(is, collector);
		}
	}
	
	/**
	 * Visits for statements and checks for empty bodies
	 * 
	 * @param fs The for statement
	 * @param collector The list of breakpoints for useless control flows
	 */
	@Override
	public void visit(ForStmt fs, List<Breakpoints> collector) {
		super.visit(fs, collector);
		if(this.isEmptyStatement(fs.getBody())) {
			this.collect(fs, collector);
		}
	}
	
	/**
	 * Visits do...while statements and checks for empty bodies
	 * 
	 * @param ds The do...while statement
	 * @param collector The list of breakpoints for useless control flows
	 */
	@Override
	public void visit(DoStmt ds, List<Breakpoints> collector) {
		super.visit(ds, collector);
		if(this.isEmptyStatement(ds.getBody())) {
			this.collect(ds, collector);
		}
	}
	
	/**
	 * Visits while statements and checks for empty bodies
	 * 
	 * @param ws The while statement
	 * @param collector The list of breakpoints for useless control flows
	 */
	@Override
	public void visit(WhileStmt ws, List<Breakpoints> collector) {
		super.visit(ws, collector);
		if(this.isEmptyStatement(ws.getBody())) {
			this.collect(ws, collector);
		}
	}
	
	/**
	 * Visits if statements and checks for empty bodies or 
	 * bodies full of empty cases
	 * 
	 * @param ss The switch statement
	 * @param collector The list of breakpoints for useless control flows
	 */
	@Override
	public void visit(SwitchStmt ss, List<Breakpoints> collector) {
		super.visit(ss, collector);
		
		//Check if all case statements are useless
		for(SwitchEntry switchCase : ss.getEntries()) {
			for(Statement s : switchCase.getStatements()) {
				if(!(this.isEmptyStatement(s))) {
					return;
				}
			}
		}
		
		//Add breakpoint if all cases are useless
		this.collect(ss, collector);
	}
	
}

/*
 * Name: Adam Fairlie
 * Registration Number: 2461352F
 * */
package detectors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.Node.ParentsVisitor;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitor;

/**
 * Class for traversing the AST of a given file for useless
 * control flows and recursions
 * 
 * Student solution to OOSE2 Assessed Exercise 2
 * 
 * @author Adam Fairlie <2461352f@student.gla.ac.uk>
 */
public class Driver {

	/**
	 * The main method which traverses the AST using
	 * visitors and reports any useless control flows or recursions
	 * 
	 * @param args The list of command line arguments
	 */
	public static void main(String args []) {
		
		//Exit if no arguments given
		if(args.length == 0) {
			System.out.println("Please enter a file name");
			return;
		}
		
		final String FILE_PATH = args[0];

		try {
			CompilationUnit cu = JavaParser.parse(new FileInputStream(FILE_PATH));
			
			System.out.println("Results for " + FILE_PATH);
			System.out.println("-----------------------------------------");
			
			//Traverse for useless control flows		
			System.out.println("Useless Control Flows:");
			VoidVisitor<List<Breakpoints>> flowVisitor = new UselessFlowControlDetector();
			List<Breakpoints> flowCollector = new ArrayList<>();
			flowVisitor.visit(cu, flowCollector);
			//Print all useless control flows found
			flowCollector.forEach(m->{
				System.out.println(m);
				});
			
			//Traverse for recursions
			System.out.println("\nRecursions:");
			VoidVisitor<List<Breakpoints>> recVisitor = new RecursionDetector();
			List<Breakpoints> recCollector = new ArrayList<>();
			recVisitor.visit(cu, recCollector);
			//Print all recursions found
			recCollector.forEach(m->{
				System.out.println(m);
				});	
			}
		catch (FileNotFoundException e) {
			//Print error if file not found
			System.out.println("Couldn't locate file " + FILE_PATH);
		}
	}
	
}

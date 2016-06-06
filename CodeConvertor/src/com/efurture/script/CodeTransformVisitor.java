package com.efurture.script;

import java.util.ArrayList;
import java.util.List;

import org.mozilla.javascript.ast.Assignment;
import org.mozilla.javascript.ast.AstNode;
import org.mozilla.javascript.ast.ElementGet;
import org.mozilla.javascript.ast.ExpressionStatement;
import org.mozilla.javascript.ast.FunctionCall;
import org.mozilla.javascript.ast.InfixExpression;
import org.mozilla.javascript.ast.Name;
import org.mozilla.javascript.ast.NewExpression;
import org.mozilla.javascript.ast.NodeVisitor;
import org.mozilla.javascript.ast.PropertyGet;
import org.mozilla.javascript.ast.StringLiteral;
import org.mozilla.javascript.ast.VariableInitializer;

public class CodeTransformVisitor implements NodeVisitor {
	
	
	public static final String  PREFIX = "__";
    @Override 
    public boolean visit(AstNode node) {
      if (node.getParent() != null) {
    		CodeConvertor.log("visit xxx " + node.getClass()  + "  " + node.toSource()
   				    + " " + node.getParent().getClass()); 
	}
     if (node instanceof NewExpression) {
    	 NewExpression expression = (NewExpression) node;
    	 
    	 CodeConvertor.log("new xxx " + expression.toSource());
    	 CodeConvertor.log("new xxx " + expression.getInitializer() + "  "   +
    			 expression.getTarget().toSource());
    	 List<AstNode> arguments = expression.getArguments();
    	 for(int i=0; i< arguments.size(); i++){
    		 AstNode args = arguments.get(i);
    		 args.visit(this);
    	 }
		 return false;
	 }
  	  if(node instanceof FunctionCall && !(node instanceof NewExpression)){
  		   FunctionCall functionCall = (FunctionCall) node;
  		   AstNode tatget =  functionCall.getTarget();
  		   if (tatget instanceof PropertyGet) {
  			   PropertyGet  get = (PropertyGet)tatget;
  			   Name right = (Name) get.getRight();
  			   if (right != null && !right.getIdentifier().contains(PREFIX)) {
  				   StringLiteral literal = new StringLiteral();
  				   literal.setValue(right.getIdentifier());
  				   literal.setQuoteCharacter('"');
  				   ArrayList<AstNode>  arguments = new ArrayList<AstNode>();
  				   arguments.add(literal);
  				   arguments.addAll(functionCall.getArguments());
  				   functionCall.setArguments(arguments);
  				   right.setIdentifier("__c");
				   }
  		   }else{
  			   //System.out.println("function call " + functionCall.toSource() + "  "  + tatget.getClass());
  		   }
  	 }else if (node instanceof PropertyGet) {
  		     PropertyGet  get = (PropertyGet) node;
  		      Name right = (Name) get.getRight();
  		      if (!right.getIdentifier().contains(PREFIX)) {
  	  		     if (get.getParent() instanceof  ExpressionStatement) {
  	  			      ExpressionStatement parent = (ExpressionStatement) get.getParent();
  		    		  FunctionCall call = new FunctionCall();
  		    		  StringLiteral literal = new StringLiteral();
  					  literal.setValue(right.getIdentifier());
  					  literal.setQuoteCharacter('"');
  					  ArrayList<AstNode>  arguments = new ArrayList<AstNode>();
  					  arguments.add(literal);
  		    		  call.setArguments(arguments);
  		    		  call.setTarget(get);
  		    		  right.setIdentifier("__g");
  		    		  parent.setExpression(call);
  				 }else if(get.getParent() instanceof  PropertyGet){
  					 PropertyGet parent = (PropertyGet) get.getParent();
  					  FunctionCall call = new FunctionCall();
  		    		  StringLiteral literal = new StringLiteral();
  					  literal.setValue(right.getIdentifier());
  					  literal.setQuoteCharacter('"');
  					  ArrayList<AstNode>  arguments = new ArrayList<AstNode>();
  					  arguments.add(literal);
  		    		  call.setArguments(arguments);
  		    		  call.setTarget(get);
  		    		  right.setIdentifier("__g");
  		    		  parent.setLeft(call);
  					 // System.out.println("get xxx " + node.getClass()  + "  " + node.toSource());
  				 }else if(get.getParent() instanceof  InfixExpression){
  					  InfixExpression  parent = (InfixExpression) get.getParent();
  		    		  FunctionCall call = new FunctionCall();
  		    		  StringLiteral literal = new StringLiteral();
  					  literal.setValue(right.getIdentifier());
  					  literal.setQuoteCharacter('"');
  					  ArrayList<AstNode>  arguments = new ArrayList<AstNode>();
  					  arguments.add(literal);
  		    		  call.setArguments(arguments);
  		    		  call.setTarget(get);
  		    		  right.setIdentifier("__g");
  		    		  if (parent.getLeft() == get) {
  		    			   parent.setLeft(call);
  					  }else {
  						  parent.setRight(call);
  					  }
  				 }else if(get.getParent() instanceof  FunctionCall){
  					  FunctionCall  parent = (FunctionCall) get.getParent();
  		    		  FunctionCall call = new FunctionCall();
  		    		  StringLiteral literal = new StringLiteral();
  					  literal.setValue(right.getIdentifier());
  					  literal.setQuoteCharacter('"');
  					  ArrayList<AstNode>  arguments = new ArrayList<AstNode>();
  					  arguments.add(literal);
  		    		  call.setArguments(arguments);
  		    		  call.setTarget(get);
  		    		  right.setIdentifier("__g");
  		    		  ArrayList<AstNode>  parentArguments = new ArrayList<AstNode>();
  		    		  parentArguments.addAll(parent.getArguments());
  		    		  int index = parentArguments.indexOf(get);
  		    		  parentArguments.set(index, call);
  		    		  parent.setArguments(parentArguments);
  				 }else if(get.getParent() instanceof  VariableInitializer){
  					  VariableInitializer  parent = (VariableInitializer) get.getParent();
  		    		  FunctionCall call = new FunctionCall();
  		    		  StringLiteral literal = new StringLiteral();
  					  literal.setValue(right.getIdentifier());
  					  literal.setQuoteCharacter('"');
  					  ArrayList<AstNode>  arguments = new ArrayList<AstNode>();
  					  arguments.add(literal);
  		    		  call.setArguments(arguments);
  		    		  call.setTarget(get);
  		    		  right.setIdentifier("__g");
  		    		  parent.setInitializer(call);
  				 }else if(get.getParent() instanceof ElementGet){
  					 ElementGet  parent = (ElementGet) get.getParent();
  		    		  FunctionCall call = new FunctionCall();
  		    		  StringLiteral literal = new StringLiteral();
  					  literal.setValue(right.getIdentifier());
  					  literal.setQuoteCharacter('"');
  					  ArrayList<AstNode>  arguments = new ArrayList<AstNode>();
  					  arguments.add(literal);
  		    		  call.setArguments(arguments);
  		    		  call.setTarget(get);
  		    		  right.setIdentifier("__g");
  		    		  parent.setElement(call);
  				 }else{
  					 
  					CodeConvertor.log("get xxx " + node.getClass()  + "  " + node.toSource()
  					    + " " + node.getParent().getClass()); 
  				 }
			 }else{
				 CodeConvertor.log("get out " + node.getClass()  + "  " + node.toSource()
	  					    + " " + node.getParent().getClass()); 
			 }
		 }else if(node instanceof ElementGet){
			 ElementGet get = (ElementGet) node;
			 if (node.getParent() instanceof NewExpression) {
				 NewExpression parent = (NewExpression) get.getParent();
				 FunctionCall call = new FunctionCall();
		    	 ArrayList<AstNode>  arguments = new ArrayList<AstNode>();
			     arguments.add(get.getElement());
		    	 call.setArguments(arguments);
		    	 PropertyGet target = new PropertyGet();
		    	 Name name = new Name();
		    	 name.setIdentifier("__g");
		    	 target.setLeft(get.getTarget());
		    	 target.setRight(name);
		    	 call.setTarget(target);
		    	 ArrayList<AstNode>  parentArguments = new ArrayList<AstNode>();
		    	 parentArguments.addAll(parent.getArguments());
		    	 int index = parentArguments.indexOf(get);
		    	 parentArguments.set(index, call);
		    	 parent.setArguments(parentArguments);
			}
		 }else if(node instanceof Assignment){
			  Assignment  assignment =  (Assignment) node;
			  if (assignment.getLeft() instanceof PropertyGet) {
				  ExpressionStatement parent = (ExpressionStatement) assignment.getParent();
				  PropertyGet left = (PropertyGet) assignment.getLeft();
				  Name   leftRight = (Name) left.getRight();
				  StringLiteral literal = new StringLiteral();
				  literal.setValue(leftRight.getIdentifier());
				  literal.setQuoteCharacter('"');
				  FunctionCall call = new FunctionCall();
				  ArrayList<AstNode>  arguments = new ArrayList<AstNode>();
				  arguments.add(literal);
				  arguments.add(assignment.getRight());
	    		  call.setArguments(arguments);
	    		  call.setTarget(left);
	    		  leftRight.setIdentifier("__s");
	    		  parent.setExpression(call);
			  }else{
				  CodeConvertor.log("Assignment " + node.toSource()  +   assignment.getParent().getClass());
			  }
			 
        }else {
        	CodeConvertor.log("end visit" + node.getClass()  + "  " + node.toSource());
  	  }
      return true;
    }
  }
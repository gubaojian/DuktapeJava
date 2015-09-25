package com.efurture.script;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.AstRoot;

public class CodeTransformer {
	
	
	
	
	/**
	 * http://javascript-minifier.com/
	 * http://jointjs.com/demos/javascript-ast
	 * */
	public static String parse(Reader reader) throws IOException{
		try {
		       CompilerEnvirons env = new CompilerEnvirons();
		       env.setRecordingLocalJsDocComments(true);
		       env.setAllowSharpComments(true);
		       env.setRecordingComments(true);
		       AstRoot node = new Parser(env).parse(reader,  "script.js", 1);
		       node.visit(new CodeTransformVisitor());
		       return node.toSource();
		  } finally {
		      reader.close();
		  }
	}
	
	
	public static void main(String[] args) throws IOException {
	    String file = "/ui.js";
	    InputStream inputStream = CodeTransformer.class.getResourceAsStream(file);
	    Reader reader = new InputStreamReader(inputStream);
	    System.out.println(parse(reader));
	    
	   // boolean c = true;
	    
	   //t(c);
	 }

	public static void t(Object c){
		System.out.println( c instanceof Boolean);
	}
	
	public static void log(String msg){
		//System.out.println(msg);
	}
	

}

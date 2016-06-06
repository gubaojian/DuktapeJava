package com.efurture.script;

import java.io.*;

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
		if(args.length <= 0){
			System.out.println("java -jar CodeConvertor.jar /ui.js ui.c.js");
			return;
		}

	    String file = args[0];
		if(!file.startsWith("/")){
			file = "/" + file;
		}
	    InputStream inputStream = CodeTransformer.class.getResourceAsStream(file);
	    Reader reader = new InputStreamReader(inputStream);

		if(args.length <= 1){
			System.out.println(parse(reader));
		}else{

		}
	 }


	public static void log(String msg){
		//System.out.println(msg);
	}


}

package com.efurture.script;

import java.io.*;

import org.mozilla.javascript.CompilerEnvirons;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Parser;
import org.mozilla.javascript.ast.AstRoot;

/**
 * 根据代理规则,翻译普通的JavaScript代码。实现JavaScript和Java的无缝调用
 * */
public class JSTransformer {

	private static final  boolean debug = false;
	/**
	 * code proxy gernator
	 * */
	public static void main(String[] args) throws IOException {
		if(args.length <= 0){
			System.out.println("java -jar JSTransformer.jar /ui.js ui.c.js");
			return;
		}
		if(args.length <= 2){
			String file = args[0];

			InputStream inputStream = null;
			try{
				inputStream = new FileInputStream(file);
			}catch (Exception e){
				//e.printStackTrace();
				if(!file.startsWith("/")){
					file = "/" + file;
				}
				inputStream = JSTransformer.class.getResourceAsStream(file);
			}

			Reader reader = new InputStreamReader(inputStream);

			if(args.length <= 1){
				System.out.println(parse(reader));
			}else{
				System.out.println("convert " + file + " to " + args[1]);
				File saveFile = new File(args[1]);
				if(saveFile.getParentFile() != null){
					if(!saveFile.getParentFile().exists()){
						saveFile.getParentFile().mkdirs();
					}
				}
				FileOutputStream outputStream = new FileOutputStream(saveFile);
				outputStream.write(parse(reader).getBytes());
				outputStream.close();
			}
		}

	 }

	/**
	 * http://javascript-minifier.com/
	 * http://jointjs.com/demos/javascript-ast
	 * */
	public static String parse(Reader reader) throws IOException{
		try {
			CompilerEnvirons env = new CompilerEnvirons();
			env.setLanguageVersion(Context.VERSION_1_5);
			env.setRecordingLocalJsDocComments(false);
			env.setAllowSharpComments(false);
			env.setRecordingComments(false);
			env.setXmlAvailable(false);
			env.setGenerateDebugInfo(false);
			env.setGeneratingSource(true);
			env.setOptimizationLevel(9);
			AstRoot node = new Parser().parse(reader,  "script.js", 1);
			node.visit(new JSTransformerVisitor());
			String source =  node.toSource();
			node = null;
			return  source;
		} finally {
			reader.close();
		}
	}


	public static void log(String msg){
		if(debug) {
			System.out.println(msg);
		}
	}

	public static void warn(String msg){
		System.out.println(msg);
	}

}

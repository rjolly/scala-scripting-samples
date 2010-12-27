/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package myapp;

import java.util.ServiceLoader;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;

/**
 *
 * @author raphael
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws ScriptException {
		getEngineFactory("Scala Interpreter").getScriptEngine().eval("println(\"hello world\")");
	}

	public static ScriptEngineFactory getEngineFactory(String name) {
		ServiceLoader<ScriptEngineFactory> sefLoader = ServiceLoader.load(ScriptEngineFactory.class);
		for (ScriptEngineFactory sef : sefLoader) {
			if(name.equals(sef.getEngineName())) return sef;
		}
		return null;
	}
}

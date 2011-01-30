/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package scalaservlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author raphael
 */
public class ScalaServlet extends HttpServlet {
	static ScriptEngine engine = getEngineFactory("Scala Interpreter").getScriptEngine();
	static Map cache = new HashMap();
	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext context = getServletContext();
		engine.put("request", req);
		engine.put("response", resp);
		String uri = req.getRequestURI().substring(req.getContextPath().length());
		InputStream in = context.getResourceAsStream(uri);
		if (in == null) {
			resp.sendError(HttpURLConnection.HTTP_NOT_FOUND, uri);
		} else try {
			resp.setContentType("text/html; charset=utf-8");
			Reader r = new InputStreamReader(in);
			Writer w = new StringWriter();
			pipe(r, w);
			w.close();
			r.close();
			String str = "{" + w + "}";
			if(!cache.containsKey(str)) cache.put(str, ((Compilable)engine).compile(str));
			CompiledScript cs = (CompiledScript)cache.get(str);
			r = new StringReader(cs.eval().toString());
			pipe(r, resp.getWriter());
			r.close();
		} catch (ScriptException e) {
			throw new ServletException(e);
		}
	}

	static void pipe(Reader in, Writer out) throws IOException {
		while (true) {
			int c = in.read();
			if (c == -1) {
				break;
			}
			out.write(c);
		}
	}

	public static ScriptEngineFactory getEngineFactory(String name) {
		ServiceLoader<ScriptEngineFactory> sefLoader = ServiceLoader.load(ScriptEngineFactory.class);
		for (ScriptEngineFactory sef : sefLoader) {
			if(name.equals(sef.getEngineName())) return sef;
		}
		return null;
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * @param request servlet request
	 * @param response servlet response
	 * @throws ServletException if a servlet-specific error occurs
	 * @throws IOException if an I/O error occurs
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 * @return a String containing servlet description
	 */
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}

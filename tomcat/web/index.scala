import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

<html>
  <head>
  </head>
  <body>
  {bindings.get("request").asInstanceOf[HttpServletRequest]}
  <br/>
  {bindings.get("response").asInstanceOf[HttpServletResponse]}
  </body>
</html>

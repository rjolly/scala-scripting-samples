import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

<html>
  <head>
  </head>
  <body>
  {request.asInstanceOf[HttpServletRequest]}
  <br/>
  {response.asInstanceOf[HttpServletResponse]}
  </body>
</html>

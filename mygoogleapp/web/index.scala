import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import scala.util.Random

val req = request.asInstanceOf[HttpServletRequest]
val ses = req.getSession
val ctx = ses.getServletContext

req.setAttribute("nb", (if (req.getAttribute("nb") == null) 0 else req.getAttribute("nb").asInstanceOf[Int]) + 1)
ses.setAttribute("nb", (if (ses.getAttribute("nb") == null) 0 else ses.getAttribute("nb").asInstanceOf[Int]) + 1)
ctx.setAttribute("nb", (if (ctx.getAttribute("nb") == null) 0 else ctx.getAttribute("nb").asInstanceOf[Int]) + 1)
if (ctx.getAttribute("random") == null) ctx.setAttribute("random", new Random)

<html>
  <head>
  </head>
  <body>
  <b>random : </b>{ctx.getAttribute("random").asInstanceOf[Random].nextInt}<br/>
  <b>nb hits since last req reset : </b>{req.getAttribute("nb")}<br/>
  <b>nb hits since last ses reset : </b>{ses.getAttribute("nb")}<br/>
  <b>nb hits since last ctx reset : </b>{ctx.getAttribute("nb")}<br/>
  </body>
</html>

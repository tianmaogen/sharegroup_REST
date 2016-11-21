<%@page %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>webCam Application</title>
</head>
<body>
<pre>
    pulpit start or stop recording myStream
    eg.
       StreamName : test
         RoomName : doctor/test
              Tag : R(Recording); S(Stop)
</pre>
<form action="stream.do" method="POST">
<pre>
StreamName:<input type="text" id="streamName" name="sn" value="${sn}" />
  RoomName:<input type="text" id="roomName" name="rn" value="${rn}" />
       Tag:<input type="text" id="tag" name="tag" value="${tag}" />

    <input type="submit" />
</pre>
</form>
</body>
</html>

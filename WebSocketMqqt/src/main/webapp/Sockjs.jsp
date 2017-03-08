<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>   
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<script type="text/javascript">
	
var ctx='WebSocketMqqt';
	
</script>
<script src="content/jquery-1.8.3.min.js"></script>
 <script src="content/js/sockjs/sockjs.min.js"></script>
  <script src="content/js/websocket.js"></script>
    <script type="text/javascript">
    
        /* $(function(){
            var websocket;
          if ("WebSocket" in window) {
                alert("WebSocket");
                websocket = new WebSocket("ws://localhost:8080/WebSocketMqqt/gps/sockjs/test");
            } else  if ("MozWebSocket" in window) {
                alert("MozWebSocket");
                websocket = new MozWebSocket("ws://echo");
            } else {
                alert("SockJS");
                websocket = new SockJS("http://localhost:8080/WebSocketMqqt/gps/sockjs/test");
            }
            websocket.onopen = function (evnt) {
                $("#tou").html("链接服务器成功!")
            };
            websocket.onmessage = function (evnt) {
                $("#msg").html($("#msg").html() + "<br/>" + evnt.data);
            };
            websocket.onerror = function (evnt) {
            };
            websocket.onclose = function (evnt) {
                $("#tou").html("与服务器断开了链接!")
            }
            $("#send").bind("click", function() {
                send();
            }); */
            String.prototype.format = function() {
            	if (arguments.length == 0)
                    return null;
                var str = this;
                for (var i = 0; i < arguments.length; i++) {
                    var re = new RegExp('\\{' + i + '\\}', 'gm');
                    str = str.replace(re, arguments[i]);
                }
                return str;
            };
        	//订阅格式
        	var subscribeStr = {
        		GPS:"OBD/RT/{0}/GPS",
        		STATE:"OBD/RT/{0}/STATE",
        		ALARM:"OBD/RT/{0}/ALARM",
        	};
            function send(){
                //if (websocket != null) {
                    var message = document.getElementById("message").value;
                    HtWebSocket.send("Subscribe:"+subscribeStr.GPS.format("1"));
                    HtWebSocket.send("Subscribe:"+subscribeStr.GPS.format("2"));
                    HtWebSocket.send("Subscribe:"+subscribeStr.GPS.format("3"));
              //  } else {
                 //   alert("未与服务器链接.");
               // }
            }
        //});
        
        	$(function(){
        		   HtWebSocket.init();
        	})
         
    </script>

</head>
<body>

<div class="page-header" id="tou">
    webSocket及时聊天Demo程序
</div>
<div class="well" id="msg">
</div>
<div class="col-lg">
    <div class="input-group">
        <input type="text" style="width:400px;height:200px" placeholder="发送信息..." id="message">
      <span class="input-group-btn">
        <button class="btn btn-default" type="button" id="send" onclick="send()" >发送</button>
      </span>
    </div><!-- /input-group -->
</div><!-- /.col-lg-6 -->
</div><!-- /.row -->
</body>
</html>

 
 
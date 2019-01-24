package com.huyan.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/** 
  * @author 胡琰 
  * @version 创建时间：2019年1月18日 上午8:13:17 
  * @Description:
  */
public class WSServerInitialzer extends ChannelInitializer<SocketChannel> {

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelInitializer#initChannel(io.netty.channel.Channel)
	 */
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		
		//websocket http 编解码器
		pipeline.addLast(new HttpServerCodec());
		
		//写大数据流支持
		pipeline.addLast(new ChunkedWriteHandler());
		
		//对httpMessage进行聚合，聚合成FullHttpRequest或FullHttpResponse
		//几乎在netty中的编程，都会用到此handler
		pipeline.addLast(new HttpObjectAggregator(1024*64));
		//上面的代码用来支持http的
		//下面的代码是支持httpwebSocket
		/**
		 * websocket 服务器处理的协议，用于指定给客户端连接访问的路由 : /ws
		 * 会帮你处理握手动作： handshaking（close, ping, pong） ping + pong = 心跳
		 * 对于websocket来讲，都是以frames进行传输的，不同的数据类型对应的frames也不同
		 */
		pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
		
		//自定义的handler
		pipeline.addLast(new ChatHandler());
	}
	
}

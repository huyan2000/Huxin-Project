package com.huyan.websocket;

import java.time.LocalDateTime;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/** 
  * @author 胡琰 
  * @version 创建时间：2019年1月18日 上午8:21:52 
  * @Description:处理消息的handler
  * TextWebsocketFrame：在netty中，是用于为websocket专门处理文本的对象，frame是消息的载体
  */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	private static ChannelGroup clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
	/* (non-Javadoc)
	 * @see io.netty.channel.SimpleChannelInboundHandler#channelRead0(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		//获取客户端传输过来的消息
		String content = msg.text();
		System.out.println("接收到的数据：" + content);
		
//		for (Channel channel: clients) {
//			channel.writeAndFlush(new TextWebSocketFrame("[服务器在]" + LocalDateTime.now() + "接收到消息，消息为：" + content));
//		}
		//下面这个方法，和上面的for循环一致
		clients.writeAndFlush(new TextWebSocketFrame("[服务器在]" + LocalDateTime.now() + "接收到消息，消息为" + content));
	}

	/* (non-Javadoc)
	 * @see io.netty.channel.ChannelHandlerAdapter#handlerAdded(io.netty.channel.ChannelHandlerContext)
	 */
	//当客户端连接服务端之后（打开连接）
	//获取客户端的channel，并且放到channelGroup中去进行管理
	@Override
	public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
		clients.add(ctx.channel());
	}

	
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		//当触发handlerRemoved，ChannelGroup会自动移除对应客户端的channel
//		clients.remove(ctx.channel());
		System.out.println("客户端断开，channel对应的长id为：" + ctx.channel().id().asLongText());
		System.out.println("客户端断开，channel对应的短id为：" + ctx.channel().id().asShortText());
	}
	
}

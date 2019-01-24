package com.huyan.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.unix.Socket;
import io.netty.handler.codec.http.HttpServerCodec;

/** 
  * @author 胡琰 
  * @version 创建时间：2019年1月17日 下午12:31:14 
  * @Description:初始化器，channel注册后，会执行相应的初始化方法
  */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel channel) throws Exception {
		//通过SocketChannel去获得对应的管道
		ChannelPipeline pipeline = channel.pipeline();
		
		//通过管道，添加handler
		//HttpServerCodec是由netty自己提供的助手类，也就是个拦截器。
		//请求到服务端，我们做解码，响应到客户端做编码
		pipeline.addLast("HttpServerCodec",new HttpServerCodec());
		
		//添加自定义的助手类，返回"hello netty~"
		pipeline.addLast("customHandler", new CustomHandler());
	}
	
}

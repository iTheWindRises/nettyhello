package com.zwj.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * 初始化器,channel注册后,会执行里面的相应初始化方法
 */
public class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel channel) throws Exception {
        //通过SocketChannel去获得对应的管道
        ChannelPipeline pipeline = channel.pipeline();

        //通过管道,添加handler
        //HttpServerCodec是有netty自己提供的助手类,可以理解为拦截器其实并不是
        //当请求到服务端,我们需要做解码,响应到客户端编码
        pipeline.addLast("HttpServerCodec",new HttpServerCodec());

        //添加自定义助手类
        pipeline.addLast("customHandler",new CustomHandler());
    }
}

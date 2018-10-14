package com.zwj.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 实现客户端发送一个请求,服务器会返回hello netty
 */
public class HelloServer {

    public static void main(String[] args) throws Exception {
        //定义一对线程组
        //主线程组,用于接收客户端的连接不做任何处理
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //从线程组,bossGroup会把任务丢给他,去做处理
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //netty服务器的创建,ServerBootstrap是一个启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)                //设置主从线程组
                    .channel(NioServerSocketChannel.class)              //设置nio的双向通道
                    .childHandler(new HelloServerInitializer());                                //子处理器,用于处理workerGroup

            //启动server,并且设置8089为启动端口号,同时启动方式为同步
            ChannelFuture channelFuture = serverBootstrap.bind(8089).sync();

            //监听关闭的channel,设置位同步方式
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }



    }
}

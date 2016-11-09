package com.dasoulte.simons.wpay.server;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.util.logging.SocketHandler;

public class WpayServer {

   /* public static void main(String[] args) throws Exception {
        NettyStartupUtil.runServer(8021, new WpayServerHandler());
    }*/



    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);
            b.channel(NioServerSocketChannel.class);
            b.handler(new LoggingHandler(LogLevel.INFO));
            b.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel sc) throws Exception {
                    ChannelPipeline pipeLine = sc.pipeline();
                    pipeLine.addLast(new LoggingHandler(LogLevel.DEBUG));
                    pipeLine.addLast(new WpayServerHandler());
                }
            });

            // ; new WpayServerHandler()); // 여기가 핵심!! 실제 커넥션이 하나하나 할당된 context에서 무슨 일을 할 것인가,
            ChannelFuture f = b.bind(8888).sync(); // 8888 번 포트로 서버를 올리겠다.
            System.err.println("Ready for 0.0.0.0:8888");
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}

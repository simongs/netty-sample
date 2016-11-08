package com.dasoulte.simons.server.wpay;


import com.dasoulte.simons.server.core.util.NettyStartupUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class WpayServer {

   /* public static void main(String[] args) throws Exception {
        NettyStartupUtil.runServer(8021, new WpayServerHandler());
    }*/

    public static void main(String[] args) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO)) // 네티 기본 핸들러, 등등 더 추가 가능
                    .childHandler(new WpayServerHandler()); // 여기가 핵심!! 실제 커넥션이 하나하나 할당된 context에서 무슨 일을 할 것인가,
            ChannelFuture f = b.bind(8021).sync(); // 8010 번 포트로 서버를 올리겠다.
            System.err.println("Ready for 0.0.0.0:8010");
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

}

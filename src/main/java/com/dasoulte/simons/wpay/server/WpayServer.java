package com.dasoulte.simons.wpay.server;


public class WpayServer {

    public static void main(String[] args) throws Exception {
       /* EventLoopGroup bossGroup = new NioEventLoopGroup(1);
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
                    pipeLine.addLast(new WpayMessageDecoder());
                    pipeLine.addLast(new WpayServerHandler());
                }
            });

            ChannelFuture f = b.bind(8888).sync(); // 8888 번 포트로 서버를 올리겠다.
            System.err.println("Ready for 0.0.0.0:8888");
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }*/
    }

}

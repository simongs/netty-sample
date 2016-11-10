// modified from http://netty.io/wiki/user-guide-for-4.x.html
package com.dasoulte.simons.wpay.server.handler.test;

import com.dasoulte.simons.core.ProjectConstants;
import com.dasoulte.simons.core.util.BytesUtils;
import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@ChannelHandler.Sharable
public class WpayServerHandler2  extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //ctx.write(msg);

        //ReferenceCountUtil.release(msg);
        //ChannelFuture future = ctx.writeAndFlush(bos.toByteArray());
        //future.addListener(ChannelFutureListener.CLOSE);

        ctx.writeAndFlush(msg);
    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // handler 안에서 예외 상황이 발생하면 예외를 처리하는 케이스이다
        cause.printStackTrace();
        ctx.close();
    }

}


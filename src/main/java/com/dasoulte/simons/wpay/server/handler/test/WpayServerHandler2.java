// modified from http://netty.io/wiki/user-guide-for-4.x.html
package com.dasoulte.simons.wpay.server.handler.test;

import com.dasoulte.simons.core.ProjectConstants;
import com.dasoulte.simons.core.util.BytesUtils;
import com.dasoulte.simons.core.util.DateUtils;
import com.dasoulte.simons.wpay.server.entity.common.WpayCommonPayload;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

@ChannelHandler.Sharable
public class WpayServerHandler2  extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {



        //ctx.write(bos.toByteArray());
        //ctx.close();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        // Read에서 wrte를 쭈욱하다가 ReadComplete 에서 flush()를 한다.
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // handler 안에서 예외 상황이 발생하면 예외를 처리하는 케이스이다
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        System.out.println("channelRegistered");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        System.out.println("channelUnregistered");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("channelActive");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("channelInactive");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        System.out.println("userEventTriggered");
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
        System.out.println("channelWritabilityChanged");
    }
}


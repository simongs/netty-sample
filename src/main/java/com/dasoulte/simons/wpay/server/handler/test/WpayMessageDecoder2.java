/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.dasoulte.simons.wpay.server.handler.test;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.dasoulte.simons.core.ProjectConstants;
import com.dasoulte.simons.wpay.WpayConstants;
import com.dasoulte.simons.wpay.server.entity.WpayPayloadFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class WpayMessageDecoder2 extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        // 전문 공통부 첫 4바이트를 읽습니다. (개별부 전문 길이를 확인합니다.)
        if (in.readableBytes() < 4) {
            return;
        }

        System.out.println(in);

        byte[] lengthBytes = ByteBufUtil.getBytes(in, 0, 4);
        int payloadLength = 400 + Integer.parseInt(new String(lengthBytes));

        System.out.println(in);

        // 아직 전문 최대 길이만큼 수신되지 않은 상황
        if (in.readableBytes() < payloadLength) {
            return;
        }

        out.add(in.readBytes(payloadLength));
    }

}

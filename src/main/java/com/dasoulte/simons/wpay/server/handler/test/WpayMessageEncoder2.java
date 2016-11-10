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

import com.dasoulte.simons.core.ProjectConstants;
import com.dasoulte.simons.core.util.BytesUtils;
import com.dasoulte.simons.core.util.DateUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

public class WpayMessageEncoder2 extends MessageToByteEncoder<ByteBuf> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            /** 공통부 조립*/
            bos.write(BytesUtils.getBytes("400", ProjectConstants.KSC5601_ENCODING, 4, BytesUtils.FillerType.ZERO, BytesUtils.FillerPosition.LEFT));
            bos.write(BytesUtils.getBytes("USER_01", ProjectConstants.KSC5601_ENCODING, 10, BytesUtils.FillerType.BLANK, BytesUtils.FillerPosition.RIGHT));
            bos.write(BytesUtils.getBytes("NHNENT", ProjectConstants.KSC5601_ENCODING, 20, BytesUtils.FillerType.BLANK, BytesUtils.FillerPosition.RIGHT));
            bos.write(BytesUtils.getBytes(DateUtils.convertToDate(new Date(), DateUtils.DEFAULT_DATE_FORMAT), ProjectConstants.KSC5601_ENCODING, 8, BytesUtils.FillerType.BLANK, BytesUtils.FillerPosition.RIGHT));
            bos.write(BytesUtils.getBytes(DateUtils.convertToDate(new Date(), DateUtils.DEFAULT_TIME_FORMAT), ProjectConstants.KSC5601_ENCODING, 6, BytesUtils.FillerType.BLANK, BytesUtils.FillerPosition.RIGHT));
            bos.write(BytesUtils.getBytes(DateUtils.convertToDate(new Date(), DateUtils.DB_FORMAT), ProjectConstants.KSC5601_ENCODING, 40, BytesUtils.FillerType.BLANK, BytesUtils.FillerPosition.RIGHT));
            bos.write(BytesUtils.getBytes(StringUtils.EMPTY, ProjectConstants.KSC5601_ENCODING, 4, BytesUtils.FillerType.BLANK, BytesUtils.FillerPosition.RIGHT));
            bos.write(BytesUtils.getBytes(StringUtils.EMPTY, ProjectConstants.KSC5601_ENCODING, 200, BytesUtils.FillerType.BLANK, BytesUtils.FillerPosition.RIGHT));
            bos.write(BytesUtils.getBytes(StringUtils.EMPTY, ProjectConstants.KSC5601_ENCODING, 108, BytesUtils.FillerType.BLANK, BytesUtils.FillerPosition.RIGHT));

            /** 개별부 조립*/
            bos.write(BytesUtils.getBytes("WPAY", ProjectConstants.KSC5601_ENCODING, 4, BytesUtils.FillerType.BLANK, BytesUtils.FillerPosition.RIGHT));
            bos.write(BytesUtils.getBytes("38070220-c6d3-11e4-960b-62a8e766d218", ProjectConstants.KSC5601_ENCODING, 40, BytesUtils.FillerType.BLANK, BytesUtils.FillerPosition.RIGHT));
            bos.write(BytesUtils.getBytes(StringUtils.EMPTY, ProjectConstants.KSC5601_ENCODING, 50, BytesUtils.FillerType.BLANK, BytesUtils.FillerPosition.RIGHT));
            bos.write(BytesUtils.getBytes(StringUtils.EMPTY, ProjectConstants.KSC5601_ENCODING, 306, BytesUtils.FillerType.BLANK, BytesUtils.FillerPosition.RIGHT));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        out.writeBytes(msg);
    }

}

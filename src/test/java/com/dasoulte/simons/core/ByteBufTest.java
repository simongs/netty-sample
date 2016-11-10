package com.dasoulte.simons.core;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.junit.Test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * Created by NHNEnt on 2016-11-10.
 */
public class ByteBufTest {

    @Test
    public void basicTest() throws UnsupportedEncodingException {
        ByteBuf buffer = Unpooled.buffer(20);
        buffer.writeByte(0x30);
        buffer.writeByte(0x30);
        buffer.writeByte(0x30);
        buffer.writeByte(0x34);
        buffer.writeByte(0x61);
        buffer.writeByte(0x62);
        buffer.writeByte(0x63);
        buffer.writeByte(0x64);

        System.out.println(buffer);

        ByteBuf buffer2 = buffer.copy(0, 4);




        System.out.println(buffer);
        System.out.println(buffer2);


        System.out.println(buffer2.readInt());


        System.out.println("[" + new String(buffer2.array())+ "]");
        System.out.println(Integer.parseInt(new String(buffer2.array())));

        for ( byte b : buffer2.array()) {
            System.out.println(b);
        }


        ByteBuffer b = ByteBuffer.allocate(4);
        //b.order(ByteOrder.BIG_ENDIAN); // optional, the initial order of a byte buffer is always BIG_ENDIAN.
        b.putInt(0x00000003);

        byte[] result = b.array();

        for ( byte asd : result) {
            System.out.println(asd);
        }

        System.out.println(Integer.parseInt(new String(buffer2.array())));

    }
}

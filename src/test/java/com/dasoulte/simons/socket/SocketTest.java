package com.dasoulte.simons.socket;

import java.io.*;
import java.io.ByteArrayOutputStream;
import java.net.Socket;
import java.util.Date;

import com.dasoulte.simons.core.util.BytesUtils;
import com.dasoulte.simons.core.util.DateUtils;
import org.apache.commons.io.IOUtils;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import com.dasoulte.simons.core.ProjectConstants;


/**
 * Created by NHNEnt on 2016-11-09.
 */
public class SocketTest {

    private static final String host = "127.0.0.1";
    private static final int port = 8888;

    @Test
    public void test() throws Exception {
        Socket socket = null;
        BufferedOutputStream bufferedOutputStream = null;
        BufferedInputStream bufferedInputStream = null;

        try {
            socket = new Socket(host, port);
            socket.setSoTimeout(50000);

            /** 전송부 */
            bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());

            byte[] payload = getPayload2();

            bufferedOutputStream.write(payload);
            bufferedOutputStream.flush();


            /** 응답부 */
            bufferedInputStream = new BufferedInputStream(socket.getInputStream());

            byte[] bRecvSecond = new byte[payload.length];
            int bytesRead = 0;
            int sumByte = 0;
            do {
                bytesRead = bufferedInputStream.read(bRecvSecond, sumByte, payload.length - sumByte);
                sumByte = bytesRead + sumByte;
            } while (sumByte < payload.length);

            System.out.println(bRecvSecond);
            print(bRecvSecond);

        } finally {
            IOUtils.closeQuietly(bufferedInputStream);
            IOUtils.closeQuietly(bufferedOutputStream);
            IOUtils.closeQuietly(socket);
        }
    }

    private void print(byte[] bRecvSecond) {
        for (byte b : bRecvSecond) {
            System.out.print(b);
            System.out.print(' ');
        }
    }

    public byte[] getPayload() throws IOException {
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
        } finally {
            bos.close();
        }

        return bos.toByteArray();
    }

    public byte[] getPayload2() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            /** 공통부 조립*/
            bos.write(BytesUtils.getBytes("4", ProjectConstants.KSC5601_ENCODING, 4, BytesUtils.FillerType.ZERO, BytesUtils.FillerPosition.LEFT));
            bos.write(BytesUtils.getBytes("USER", ProjectConstants.KSC5601_ENCODING, 4, BytesUtils.FillerType.BLANK, BytesUtils.FillerPosition.RIGHT));
        } finally {
            bos.close();
        }

        return bos.toByteArray();
    }


}

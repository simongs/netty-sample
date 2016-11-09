package com.dasoulte.simons.socket;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.dasoulte.simons.core.ProjectConstants;
import org.apache.commons.io.IOUtils;
import org.junit.Test;


/**
 * Created by NHNEnt on 2016-11-09.
 */
public class SocketTest {

    private static final String host = "127.0.0.1";
    private static final int port = 8888;

    @Test
    public void test() throws Exception {
        Socket socket = null;
        BufferedWriter bufferedWriter = null;
        BufferedInputStream bufferedInputStream = null;

        try {
            socket = new Socket(host, port);
            socket.setSoTimeout(50000);

            /** 전송부 */
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), ProjectConstants.CHARSET_UTF8));

            //String tsendMsgWithLength = new String(utilCommon.getBytes(Integer.toString(sendMessage.length()), ProjectConstants.KSC5601_ENCODING, 4, (byte) 48, 0)) + sendMessage;

            bufferedWriter.write("test");
            bufferedWriter.flush();

            /** 응답부 */
            bufferedInputStream = new BufferedInputStream(socket.getInputStream());
            byte[] bRecvFirst = new byte[4];
            int readLength = bufferedInputStream.read(bRecvFirst); // 제일 앞 4byte를 읽어서 바이트 길이를 구한다.

            int byteLength = Integer.parseInt(new String(bRecvFirst));
            byte[] bRecvSecond = new byte[byteLength];
            int bytesRead = 0;
            int sumByte = 0;
            do {
                bytesRead = bufferedInputStream.read(bRecvSecond, sumByte, byteLength - sumByte);
                sumByte = bytesRead + sumByte;
            } while (sumByte < byteLength);


        } finally {
            IOUtils.closeQuietly(bufferedInputStream);
            IOUtils.closeQuietly(bufferedWriter);
            IOUtils.closeQuietly(socket);
        }
    }

}

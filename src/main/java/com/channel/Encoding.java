package com.channel;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * @Author
 * @Date 2020/4/11 1:41 下午
 * @text 只要学不死就往死里学
 */
public class Encoding {
    public static void main(String[] args) throws CharacterCodingException {
        //获取编码器
        Charset charset = Charset.forName("GBK");
        CharsetEncoder cs = charset.newEncoder();//cs.encode("abc)给abc加密
        //获取解码器
        CharsetDecoder cd = charset.newDecoder();//cd.decode("encode");给这个变量解码
        CharBuffer allocate = CharBuffer.allocate(1024);
        allocate.put("陳佳伟很牛逼");
        allocate.flip();
        //编码
        ByteBuffer encode = cs.encode(allocate);
        for (int i = 0; i < 12; i++) {
            System.out.println(encode.get());
        }
        //

        System.out.println(encode.capacity() + "+" + encode.limit() + "+" + encode.position());
        encode.flip();
        System.out.println(encode.capacity() + "+" + encode.limit() + "+" + encode.position());
        //编码解码
        //CharBuffer decode = cd.decode(encode);
        //System.out.println("1"+decode.toString());
        Charset c2 = Charset.forName("GBK");
        CharBuffer decode1 = c2.newDecoder().decode(encode);
        System.out.println("2" + decode1.toString());

    }
}

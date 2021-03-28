package com.buffer;



import java.nio.ByteBuffer;


/**
 * @Author
 * @Date 2020/4/10 4:23 下午
 * @text 只要学不死就往死里学
 */

//缓冲区是NIO 提供给传输文件和通道一起配合使用的  存放数据
//Buffer <br>
//ByteBuffer<br>
//
public class BuffTest {

    /*private int mark = -1;
    private int position = 0;缓冲区正在操作的位置
    private int limit;界面（表示缓冲区里面现在可用大小）
    private int capacity;缓冲区最大容量，一旦声明不能改变

    核心方法：
    put（）：往buff存放数据
    get（）：获取数据
    */

    public void test1() {
        //初始化byteBuffer的大小
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);//初始化byteBuffer的大小
        System.out.println(byteBuffer.position() + "-" + byteBuffer.limit() + "-" + byteBuffer.capacity());
        System.out.println("往buffer存放数据");
        byteBuffer.put("abcder".getBytes());//把adbder存进缓冲区byteBuffer
        System.out.println(byteBuffer.position() + "-" + byteBuffer.limit() + "-" + byteBuffer.capacity());
        System.out.println("读数据。。。。。");
        //开启读取模式
        byteBuffer.flip();//读/写指针position指到缓冲区头部，并且设置了最多只能读出之前写入的数据长度(而不是整个缓存的容量大小)。
        System.out.println(byteBuffer.limit());
        byte[] bytes = new byte[byteBuffer.limit()];
        //把数据读取到byts中去
        byteBuffer.get(bytes);//把数据读取到byts中去
        System.out.println(new String(bytes, 0, bytes.length));
        System.out.println(byteBuffer.position() + "-" + byteBuffer.limit() + "-" + byteBuffer.capacity());
        System.out.println("重复读取数据。。。。。");
        //开启读取模式
        byteBuffer.rewind();//重复读
        byte[] byte2 = new byte[byteBuffer.limit()];
        System.out.println("rewind" + byteBuffer.position() + "-" + byteBuffer.limit() + "-" + byteBuffer.capacity());
        //把数据读取到byts中去
        byteBuffer.get(bytes);
        System.out.println(new String(bytes, 0, byte2.length));
        System.out.println(byteBuffer.position() + "-" + byteBuffer.limit() + "-" + byteBuffer.capacity());
        System.out.println("清空");
        byteBuffer.clear();//清空数据    //清除的是下标，值是不会改变的
        System.out.println(byteBuffer.position() + "-" + byteBuffer.limit() + "-" + byteBuffer.capacity());
        System.out.println("capation：" + byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());
    }


    public void test2() {
        System.out.println("321");
    }

    public static void main(String[] args) {
        BuffTest buffTest = new BuffTest();
        buffTest.test1();
    }
}

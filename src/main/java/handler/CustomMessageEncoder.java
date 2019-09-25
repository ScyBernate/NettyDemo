package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import model.Message;

/**
 * @ClassName CustomMessageDecoder
 * @Description TODO
 * 名称       字段      字节长度    描述
 * 魔数	    magicNumber	4	一个固定的数字，一般用于指定当前字节序列是当前类型的协议，比如Java生成的class文件起始就使用0xCAFEBABE作为其标识符，对于本服务，这里将其定义为0x1314
 * 主版本号	mainVersion	1	当前服务器版本代码的主版本号
 * 次版本号	subVersion	1	当前服务器版本的次版本号
 * 修订版本号	modifyVersion	1	当前服务器版本的修订版本号
 * 消息类型	messageType	1	请求：1，表示当前是一个请求消息；响应：2，表示当前是一个响应消息；Ping：3，表示当前是一个Ping消息；Pong：4，表示当前是一个Pong消息；Empty：5，表示当前是一个空消息，该消息不会写入数据管道中；
 * 消息体长度	length	4字节	记录了消息体的长度
 * 消息体	body	不定	消息体，服务之间交互所发送或接收的数据，其长度有前面的length指定
 * @Author User
 * @DATE 2019/9/24 16:04
 * @Version 1.0
 **/
public class CustomMessageEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) throws Exception {
        if(msg!=null){
            out.writeInt(msg.getMageicNumber());
            out.writeByte(msg.getBigVersion());
            out.writeByte(msg.getMiddleVersion());
            out.writeByte(msg.getSmallVersion());
            out.writeByte(msg.getMessageType());

            String body = msg.getBody();
            if(body==null){
                out.writeInt(0);
            }else{
                byte[] bytes = body.getBytes("UTF-8");
                out.writeInt(bytes.length);
                out.writeBytes(bytes);
            }
        }
    }
}

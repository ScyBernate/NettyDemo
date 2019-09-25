package handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import model.Message;

import java.util.List;

public class CustomMessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Message message = new Message();
        message.setMageicNumber(in.readInt());
        message.setBigVersion(in.readByte());
        message.setMiddleVersion(in.readByte());
        message.setSmallVersion(in.readByte());
        message.setMessageType(in.readByte());
        int length = in.readInt(); //获取body的长度
        if(length>0){
            byte[] content = new byte[length];
            in.readBytes(content);
            message.setBody(new String(content));
        }
        out.add(message);
    }

}

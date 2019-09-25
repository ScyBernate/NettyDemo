package handler;

import io.netty.channel.*;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import model.Message;

/**
 * tcp
 */
public class TcpServerHandler extends ChannelInboundHandlerAdapter {

    public static InternalLogger logger = InternalLoggerFactory.getInstance(TcpServerHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof  Message){
            Message message = (Message) msg;
            logger.info("[服务器]:"+message);
            Message reply = new Message();
            String body = "我是服务器，你好";
            reply.setBody(body);
            ctx.writeAndFlush(reply);
        }
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}

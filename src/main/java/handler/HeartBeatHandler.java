package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    private static final InternalLogger log = InternalLoggerFactory.getInstance(WebSocketServerHandler.class.getName());

    private int lose_heartBeat_count = 0; //丢失心跳次数

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                if (lose_heartBeat_count >= 2) {
                    ctx.channel().close().sync();
                    log.error("已与" + ctx.channel().remoteAddress() + "断开连接");
                } else {
                    lose_heartBeat_count++;
                    //log.info(ctx.channel().remoteAddress() + "丢失了第 " + lose_heartBeat_count + " 个心跳包");
                }
            }

        }
        super.userEventTriggered(ctx, evt);
    }
}

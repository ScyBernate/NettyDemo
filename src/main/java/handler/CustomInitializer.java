package handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class CustomInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        //websocket(ch);
        tcp(ch);
    }




    /**
     * 自定义协议实现
     * @param ch
     */
    public void tcp(Channel ch){
        ChannelPipeline pipeline = ch.pipeline();
        //pipeline.addLast("encode",new StringEncoder());
        //pipeline.addLast("decode",new StringDecoder());


        //自定义协议会出现拆包粘包，需要自己处理
        //协议数据的最大长度,长度字段在整个数据的偏移量,长度字段占的字节数，调整开始读的位置(为0代表从头读)
        pipeline.addLast(new LengthFieldBasedFrameDecoder(1024*1024,8,4,0,0));
        pipeline.addLast("encode",new CustomMessageEncoder());
        pipeline.addLast("decode",new CustomMessageDecoder());
        pipeline.addLast(new TcpServerHandler());
    }

    /**
     * websocket（包括心跳监测)
     * @param ch
     */
    public void websocket(Channel ch){
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("http-codec", new HttpServerCodec()); // Http消息编码解码
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536)); // Http消息组装
        pipeline.addLast("http-chunked", new ChunkedWriteHandler());
        pipeline.addLast(new IdleStateHandler(6, 0, 0, TimeUnit.SECONDS)); //开启心跳读监测
        pipeline.addLast(new HeartBeatHandler());
        pipeline.addLast(new WebSocketServerHandler());
    }

}

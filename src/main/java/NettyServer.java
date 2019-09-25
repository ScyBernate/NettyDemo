import core.Global;

import handler.CustomInitializer;
import handler.HeartBeatHandler;
import handler.WebSocketServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import thread.InternalDefaultThreadFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NettyServer {

    public static InternalLogger logger = InternalLoggerFactory.getInstance(NettyServer.class);

    public final int nPort = 10; //端口数量

    /**
     * @param portCount 绑定多端口数量
     */
    public void multiBind(ServerBootstrap bootStrap, int portCount) throws InterruptedException {
        List<ChannelFuture> cfs = new ArrayList<>();
        for (int i = 0; i < nPort; i++) {
            final int portCh = portCount + i;
            ChannelFuture cf = bootStrap.bind(portCh);
            cf.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    logger.info("bind port：" + portCh + " successed!");
                }
            });
            cfs.add(cf);
        }

        for (ChannelFuture cf : cfs) {
            cf.channel().closeFuture().sync();
        }
    }


    public NettyServer(int port) {
        try {
            run(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(2, new InternalDefaultThreadFactory("[connect]"));
        EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2, new InternalDefaultThreadFactory("[business-process]"));
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler())
                    .childHandler(new CustomInitializer());

            //单一端口最大tcp连接数 2^16 -1=65536
            ChannelFuture future = b.bind(port).sync();
            if (future.isSuccess()) {
                logger.info("服务已经启动，端口：" + port + ".");
            }
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        // PropertyConfigurator.configure(System.getProperty("conf.dir")+"/logger4j.properties");
        new NettyServer(Global.PORT);
    }

}

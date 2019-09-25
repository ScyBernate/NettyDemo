package client;

import handler.CustomMessageDecoder;
import handler.CustomMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import model.Message;

public class tcp_Client {

    public static String host = "127.0.0.1";
    public static int port = 7120;

    public static void main(String[] args) {
        EventLoopGroup worker = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(worker);
        b.channel(NioSocketChannel.class);
        b.remoteAddress(host, port);
        b.handler(new tcp_Client().new ClientInitializer());
        try {
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {

            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }

    }

    public class ClientInitializer extends ChannelInitializer<SocketChannel> {

        protected void initChannel(SocketChannel ch) throws Exception {
            //ch.pipeline().addLast(new StringEncoder());  //功能消息转成String
            //入站处理器  执行顺序为注册顺序
            //ch.pipeline().addLast(new StringDecoder());
            // 注册handler
            ch.pipeline().addLast(new CustomMessageEncoder());
            ch.pipeline().addLast(new CustomMessageDecoder());
            ch.pipeline().addLast(new EchoClientHandler());
        }
    }

    public class EchoClientHandler extends SimpleChannelInboundHandler<Message> {
        private static final int REPEAT = 500;// 消息重复发送次数

        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            //0x10001001
            Message message = new Message();
            String body = "百度——全球最大的中文搜索引擎及最大的中文网站，全球领先的人工智能公司。2000年1月1日创立于中关村，公司创始人李彦宏拥有“超链分析”技术专利，使中国成为美国、俄罗斯、韩国之外，全球仅有的四个拥有独立搜索引擎核心技术的国家之一。基于对人工智能的多年布局与长期积累，百度在深度学习领域领先世界，并在2016年被《财富》杂志称为全球AI四巨头之一。\n" +
                    "每天，百度响应来自百余个国家和地区的数十亿次搜索请求，是网民获取中文信息的最主要入口。百度以“用科技让复杂的世界更简单”为使命，不断坚持技术创新，致力于提供更懂用户的产品及服务。百度移动应用月活跃设备数超过11亿。\n" +
                    "百度以技术为信仰，在技术研发、人才引进等方面坚持长期持续的投入。根据中国专利保护协会2018年统计，百度以2368件申请量成为中国人工智能专利领头羊。\n" +
                    "在“夯实移动基础，决胜AI时代”的战略指导下，百度移动生态更加繁荣强大，AI加速推进产业智能化，AI生态不断拓展完善。\n" +
                    "得益于AI驱动，百度移动形成了“一超多强”的产品矩阵，并构建起以“百家号”和“智能小程序”为核心的移动生态。2019年3月份，百度App日活跃用户达1.74亿，同比增长28%；好看视频日活跃用户达2200万，同比增长768%；百度App和短视频信息流总用户时长同比增长83%；百家号内容创作者达到210万；智能小程序月活跃用户达到1.81亿，环比增长23%。\n" +
                    "作为AI生态的重要组成，百度已拥有Apollo自动驾驶开放平台和小度助手（DuerOS）对话式人工智能操作系统两大开放生态。截至2019年1月，Apollo已迭代至3.5版本，合作伙伴突破135家。目前，百度已获得超过50张智能网联汽车道路测试牌照，在国内遥遥领先。在对话式人工智能领域，小度助手继续在中国保持领先地位。截至2019年3月，搭载小度助手的智能设备达到2.75亿，同比增长279%；3月份语音交互次数达到23.7亿次，同比增长817%。根据IDC、Canalys、Strategy Analytics等权威机构发布的报告，2019年第一季度小度智能音箱出货量位居国内市场第一，全球第三。\n" +
                    "百度智能云是面向企业及开发者的智能云计算服务平台，基于百度多年技术积累，面向未来，提出ABC（AI、Big Data、Cloud Computing）三位一体发展战略，为各行业智能化转型提供解决方案，促进数字中国建设。IDC发布的2018年下半年中国公有云服务市场报告显示，百度智能云首次进入中国IaaS+PaaS云服务商前五名，在2018年全年PaaS层面，百度智能云营收在所有厂商中同比增速最快。\n" +
                    "百度大脑是百度技术多年积累和业务实践的集大成，包括视觉、语音、自然语言处理、知识图谱、深度学习等AI核心技术和AI开放平台。百度大脑为百度所有业务提供AI能力和底层支撑，并赋能产业和开发者。截止2019年7月，百度大脑已经开放了210项领先的AI技术能力，开发者数量达到130万，平台上开发者的日均调用次数同比增长108%，定制化平台模型的数量也在飞速增长。来自不同行业，拥有不同背景的开发者们，都在借助百度大脑快速地获得AI能力。\n" +
                    "“爱奇艺”和“度小满”是百度旗下两大独立业务。爱奇艺在中国互联网视频行业处于领先地位，2018年3月底，爱奇艺在美国纳斯达克挂牌上市。爱奇艺是百度新移动业务的重要组成，并与百度在内容、云服务、人工智能技术等领域有着深度协同。2019年第一季度，爱奇艺订阅会员规模创下历史新高，达到9680万，实现了包括广告、内容分发以及IP价值开发等多元化收入快速增长。“度小满金融”是百度旗下金融服务事业群组拆分后形成的全新品牌，是百度在金融领域的关键布局。作为一家金融科技公司，度小满致力于利用百度的AI优势和技术实力，用科技为更多人提供值得信赖的金融服务。\n" +
                    "百度商业服务整合了搜索、资讯、视频、线下场景屏、联盟流量等资源，形成全场景全用户覆盖的媒体矩阵，并依托AI技术和大数据能力提供消费者洞察、自动化创意、商家小程序等一整套智能营销解决方案。为企业提供品牌建设、效果推广及消费者运营的全方位商业服务。2019年数字营销将进入以AI智能营销为核心的4.0时代，百度商业服务将实现新连接、新场景、新流量、新品牌的\"四新合力\"升级，并全面应用于搜索推广、信息流广告、品牌营销、商品推广等核心商业产品。此外，经过17年发展，百度联盟已经从PC时代的搜索联盟、广告联盟进化到今天的新型生态联盟，涵盖终端厂商、运营商、网站、移动APP、软件、百家号创作者等多种类型的近百万合作伙伴。作为国内最具实力的联盟体系之一，百度联盟始终致力于“让伙伴更强”。基于后移动时代的挑战和机遇，百度联盟正在从流量联盟向“用户联盟”升级，为伙伴构筑更加开源的移动互联网生态，深度理解和服务用户，共生共荣共赢。\n" +
                    "百度一直秉承着“科技为更好”的社会责任理念，坚持运用创新技术，联合共益伙伴，聚焦于解决社会问题，履行企业公民的社会责任。为了帮助流浪走失人员找到“回家的路”，2016年底，百度“AI寻人”项目与民政部进行合作，两年时间内即实现了6493次走失人员的对比成功；为了让社区更有智慧，百度推出“AI公园”项目，让AI技术直接融入到当地居民的生活场景中，与社会一同分享技术发展带来的全新体验；为了让盲人按摩师的生活与工作更加便利，百度工程师“爆改”盲人按摩店，让盲人师傅通过语音唤醒了“没有光明的生活”；为了让老百姓更有安全感，百度推出了 “城市盾牌”，人脸识别系统对警察的加持，让罪犯无处可逃。此外，百度将继续向弱势群体、社会公益服务、社会信息化发展等更多社会责任领域进行探索，不断为用户美好生活的创造提供强大支持。百度还捐赠成立了百度基金会，围绕知识教育、环境保护、灾难救助等议题，联合社会各公益组织机构，赋予百度产品更多公益属性，更加系统规范地管理和践行公益事业。目前，百度是联合国全球契约组织的一员，正在积极地为全球的可持续发展贡献力量。";
            message.setBody(body);
            ctx.writeAndFlush(message);
        }

        @Override
        public void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {
            // 只要服务器端发送完成信息之后，都会执行此方法进行内容的输出操作
            System.out.println("[客户端]收到消息："+msg); // 输出服务器端的响应内容
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            cause.printStackTrace();
            ctx.close();
        }
    }

}

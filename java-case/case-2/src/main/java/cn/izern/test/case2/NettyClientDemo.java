package cn.izern.test.case2;

import cn.izern.test.case2.ksql.KsqlConfig;
import cn.izern.test.case2.ksql.KsqlQueryParam;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.DefaultThreadFactory;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

/**
 * @author: zern
 * @since 1.0.0
 */
public class NettyClientDemo {

  static EventLoopGroup bossGroup = new NioEventLoopGroup(1,
      new DefaultThreadFactory("ksql-client-boss", true));

  static Bootstrap bs = new Bootstrap();

  public static void main(String[] args) {

    KsqlQueryParam param = new KsqlQueryParam();
    // 这里使用更简单粗暴的ksql
    param.setKsql("SELECT * FROM riderLocations EMIT CHANGES;");

    init();

    try {
      ChannelFuture query = query(param);
      TimeUnit.SECONDS.sleep(100);
      query.channel().closeFuture().sync();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      destroy();
    }

  }

  public static void init() {
    bs.group(bossGroup)
        .channel(NioSocketChannel.class)
        .option(ChannelOption.SO_KEEPALIVE, true)
        .handler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline().addLast(new HttpResponseDecoder());
            socketChannel.pipeline().addLast(new HttpRequestEncoder());
            socketChannel.pipeline().addLast(new ReadTimeoutHandler(300));
            socketChannel.pipeline().addLast(new KsqlMsgPrint());
          }
        });
  }

  public static ChannelFuture query(KsqlQueryParam param)
      throws IOException, InterruptedException, URISyntaxException {
    ChannelFuture f = bs.connect(KsqlConfig.HOST, KsqlConfig.PORT).sync();

    URI uri = new URI(KsqlConfig.QUERY_URI);
    DefaultFullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1,
        HttpMethod.POST,
        uri.toASCIIString(), Unpooled.wrappedBuffer(new ObjectMapper().writeValueAsBytes(param)));

    // 构建http请求
    request.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/vnd.ksql.v1+json");
//    request.headers().set(HttpHeaderNames.ACCEPT_ENCODING, "*");
    request.headers().set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
    // 发送http请求
    f.channel().write(request);
    f.channel().flush();
    return f;
  }

  public static void destroy() {
    bossGroup.shutdownGracefully();
  }

  public static class KsqlMsgPrint extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
      if (msg instanceof HttpResponse) {
        HttpResponse res = (HttpResponse) msg;
        if (res.status().code() < HttpResponseStatus.OK.code()
            || res.status().code() >= HttpResponseStatus.MULTIPLE_CHOICES.code()) {
          System.err.println(String.format("返回错误，错误码为%d", res.status().code()));
        } else {
          System.out.println("连接成功.");
        }
      } else if (msg instanceof HttpContent) {
        System.out.print(((HttpContent) msg).content().toString(CharsetUtil.UTF_8));
      }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
      cause.printStackTrace();
      ctx.close();
    }

  }

}

package com.github.mgrzeszczak.spotify.sdk.example;

import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Utils {

    private Utils() {
        throw new AssertionError("no instances");
    }

    static AuthRedirect interceptRedirect(String address, int port) throws Exception {
        ServerSocketChannel channel = ServerSocketChannel.open().bind(new InetSocketAddress(address, port));
        SocketChannel client = channel.accept();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        client.read(buffer);
        buffer.flip();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while (buffer.hasRemaining()) {
            baos.write(buffer.get());
        }
        String message = baos.toString();
        client.close();
        channel.close();
        Matcher match = Pattern.compile("code=(.+)&state=(.+) ").matcher(message);
        if (!match.find()) {
            throw new RuntimeException("invalid request");
        }
        return new AuthRedirect(match.group(1), match.group(2));
    }

    static String readFile(String path) throws Exception {
        return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
    }

}

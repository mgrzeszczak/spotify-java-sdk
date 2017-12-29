package com.github.mgrzeszczak.spotify.sdk.example;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.mgrzeszczak.spotify.sdk.api.SpotifyAuthCodeURLRequestBuilder;
import com.github.mgrzeszczak.spotify.sdk.api.SpotifySDK;
import com.github.mgrzeszczak.spotify.sdk.model.Album;
import com.github.mgrzeszczak.spotify.sdk.model.Artist;
import com.github.mgrzeszczak.spotify.sdk.model.ArtistSimplified;
import com.github.mgrzeszczak.spotify.sdk.model.Recommendations;
import com.github.mgrzeszczak.spotify.sdk.model.TrackAttributes;
import com.github.mgrzeszczak.spotify.sdk.model.authorization.TokenData;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class App {

    private static final int PORT = 8080;
    private static final String ADDRESS = "0.0.0.0";

    public static void main(String[] args) throws Exception {
        String clientId = readFile("spotify-client-id");
        String clientSecret = readFile("spotify-client-secret");
        String redirectUri = "http://localhost:" + PORT + "/redirect";
        String state = "userId";

        String url = SpotifyAuthCodeURLRequestBuilder.create()
                .clientId(clientId)
                .responseType(SpotifyAuthCodeURLRequestBuilder.CODE_RESPONSE_TYPE)
                .redirectUri(redirectUri)
                .state(state)
                .build();

        if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            throw new RuntimeException("cannot open browser");
        }
        Desktop.getDesktop().browse(new URI(url));
        Response response = getResponse();

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        SpotifySDK spotify = SpotifySDK.Builder.create()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .okHttpClient(okHttpClient)
                .build();

        TokenData tokenData = spotify.getToken(response.getAuthorizationCode(), redirectUri).blockingGet();
        String authorization = "Bearer " + tokenData.getAccessToken();

        Album album = spotify.getAlbum(authorization, "1tzrwGajPhpdX2EVkCnmHZ", null).blockingGet();
        System.out.println(album);

        ArtistSimplified artistSimple = album.getArtists().get(0);
        System.out.println(artistSimple);

        Artist artist = spotify.getArtist(authorization, artistSimple.getId()).blockingGet();
        System.out.println(artist);

        spotify.getRelatedArtists(authorization, artist.getId())
                .subscribe(System.out::println);

        Recommendations recommendations = spotify.getRecommendations(
                authorization,
                Collections.singletonList(artist.getId()),
                null,
                null,
                null,
                null,
                null,
                null,
                TrackAttributes.builder().danceability(1.0f).build()
        ).blockingGet();
        System.out.println(recommendations);
    }

    private static Response getResponse() throws Exception {
        ServerSocketChannel channel = ServerSocketChannel.open().bind(new InetSocketAddress(ADDRESS, PORT));
        SocketChannel client = channel.accept();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int read = client.read(buffer);
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
        return new Response(match.group(1), match.group(2));
    }

    private static class Response {

        private final String authorizationCode;
        private final String state;

        private Response(String authorizationCode, String state) {
            this.authorizationCode = authorizationCode;
            this.state = state;
        }

        public String getAuthorizationCode() {
            return authorizationCode;
        }

        public String getState() {
            return state;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "authorizationCode='" + authorizationCode + '\'' +
                    ", state='" + state + '\'' +
                    '}';
        }
    }

    private static String readFile(String path) throws Exception {
        return new String(Files.readAllBytes(Paths.get(path)), StandardCharsets.UTF_8);
    }

}

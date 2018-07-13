package me.sylvaeon.umbreon;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import me.sylvaeon.umbreon.music.search.Auth;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static me.sylvaeon.umbreon.music.search.Auth.JSON_FACTORY;

public final class Google {

    private static YouTube youtube;
    private static final String SPREADSHEET_ID = "1rhAbVTAt4RNnb8ERAl-_JO0eh6gHmKzAQ4lTD_oHnpI";
    private static final long NUMBER_OF_VIDEOS_RETURNED = 1;
    private static final String YOUTUBE_APP_NAME = "Umbreon YouTube Search";
    private static final String SPREADSHEET_APP_NAME = "Umbreon Spreadsheet Reader";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CLIENT_SECRET_DIR = "src/main/resources/client_secret.json";
    private static Sheets sheets;

    public static List<SearchResult> youtubeSearchByKeyword(String queryTerm) {
        try {
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, JSON_FACTORY, request -> {
            }).setApplicationName(YOUTUBE_APP_NAME).build();
            YouTube.Search.List search = youtube.search().list("id,snippet");
            String apiKey = Auth.GOOGLE_KEY;
            search.setKey(apiKey);
            search.setQ(queryTerm);
            search.setType("video");
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            SearchListResponse searchResponse = search.execute();
            return searchResponse.getItems();
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static List<List<Object>> getSpreadsheet(String sheetName) {
        List<List<Object>> list = new ArrayList<>();
        try {
            sheets = new Sheets.Builder(Auth.HTTP_TRANSPORT, JSON_FACTORY, getCredentials(GoogleNetHttpTransport.newTrustedTransport()))
                    .setApplicationName(SPREADSHEET_APP_NAME)
                    .build();
            ValueRange response = sheets.spreadsheets().values()
                    .get(SPREADSHEET_ID, sheetName)
                    .execute();
            list = response.getValues();
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new FileReader(new File(CLIENT_SECRET_DIR)));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("src/main/resources/credentials")))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

}

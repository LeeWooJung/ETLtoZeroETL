package extract.crawling;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JsonFormatter {

    public void Extractor(String content, String filepath) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(content);
        JsonNode itemsNode = rootNode.path("items");

        // check Existing Links
        Set<String> existingLinks = new HashSet<>();
        boolean fileExists = Files.exists(Paths.get(filepath));

        if (fileExists) {
            try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filepath), "MS949"))) {
                String line;
                while((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    existingLinks.add(values[3]); // Link: 4th column
                }
            }
        }

        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath, true), "MS949"))) {

            if (!fileExists) {
                writer.write("Date,Title,Description,Link");
                writer.newLine();
            }

            for (JsonNode itemNode: itemsNode) {
                String pubDate = formatPubDate(itemNode.path("pubDate").asText());
                String title = cleanText(itemNode.path("title").asText());
                String description = cleanText(itemNode.path("description").asText());
                String originallink = itemNode.path("originallink").asText();

                if (!existingLinks.contains(originallink)) {
                    writer.write(String.join(",", pubDate, title, description, originallink));
                    writer.newLine();
                    existingLinks.add(originallink);
                }

            }
        }
    }

    private String formatPubDate(String pubDate) {
        // "Sat, 17 Aug 2024 23:12:00 +0900" ==> "2024.08.17/23"
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", java.util.Locale.ENGLISH);
            Date date = originalFormat.parse(pubDate);
            SimpleDateFormat newFormat = new SimpleDateFormat("yyyy.MM.dd/HH");
            return newFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private String cleanText(String text) {
        return text.replaceAll("<[^>]*>", "") // 태그 제거
                   .replaceAll("&quot;", "\"")
                   .replaceAll("&amp;", "&")
                   .replaceAll("\\[이슈\\]", "") // 특수 문자 및 불필요한 텍스트 제거
                   .replaceAll(",", "")
                   .replaceAll("\\[.*?\\]", "")
                   .replaceAll("\\(.*?\\)", "");
    }
}

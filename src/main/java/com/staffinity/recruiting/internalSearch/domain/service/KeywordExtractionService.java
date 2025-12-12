package com.staffinity.recruiting.internalSearch.domain.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class KeywordExtractionService {

    // A "knowledge base" of tech keywords to look for
    private static final Set<String> TECH_KEYWORDS = new HashSet<>(Arrays.asList(
            "java", "spring", "sql", "postgres", "postgresql", "mysql", "oracle",
            "react", "angular", "vue", "javascript", "typescript", "node", "nodejs",
            "c#", ".net", "dotnet", "python", "django", "flask",
            "docker", "kubernetes", "aws", "azure", "cloud",
            "agile", "scrum", "kanban", "leadership", "management",
            "communication", "english", "spanish"));

    /**
     * Extracts known keywords from a text block.
     */
    public Set<String> extractKeywords(String text) {
        if (text == null || text.isBlank()) {
            return new HashSet<>();
        }

        // Normalize text: lowercase, remove special chars
        String normalized = text.toLowerCase().replaceAll("[^a-z0-9\\s+#]", " ");

        // Split by whitespace
        String[] tokens = normalized.split("\\s+");

        return Arrays.stream(tokens)
                .filter(token -> TECH_KEYWORDS.contains(token) || isSpecialKeyword(token))
                .collect(Collectors.toSet());
    }

    private boolean isSpecialKeyword(String token) {
        // Handle variations like "c#" or ".net" that might be tricky with regex
        if (token.equals("c#") || token.equals(".net"))
            return true;
        return false;
    }
}

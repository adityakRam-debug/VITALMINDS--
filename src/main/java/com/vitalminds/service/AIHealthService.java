package com.vitalminds.service;

import com.vitalminds.model.ActivityLog;
import com.vitalminds.model.HealthRecord;
import com.vitalminds.model.SleepLog;
import com.vitalminds.model.User;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class AIHealthService {

    private final RestClient client;

    public AIHealthService() {
        this.client = RestClient.builder()
                .baseUrl("http://localhost:11434")
                .build();
    }

    // ==============================
    // AI HEALTH ASSISTANT
    // ==============================
    public String answer(
            User user,
            HealthRecord record,
            String question,
            List<ActivityLog> activity,
            List<SleepLog> sleep) {

        String context = buildHealthContext(user, record);

        String prompt = """
                You are VitalMinds AI, a friendly wellness assistant.

                IMPORTANT RULES:
                - Do not diagnose diseases.
                - Do not prescribe medicines.
                - Do not invent health values.
                - Use exact numbers from the provided report.
                - Do not change or round the user's health values.
                - Give simple and understandable explanations.
                - If information is missing, clearly say it is missing.
                - Laboratory reference ranges may vary.
                - For concerning or persistent issues, recommend discussing them
                  with a qualified healthcare professional.
                - Keep the answer concise and easy to understand.

                USER HEALTH INFORMATION:
                %s

                USER QUESTION:
                %s

                Answer the user's question using the information above.
                """.formatted(context, question);

        return askOllama(prompt);
    }

    // ==============================
    // HEALTH REPORT EXPLANATION
    // ==============================
    public String explain(User user, HealthRecord record) {

        if (record == null) {
            return "Please upload a health report first.";
        }

        String prompt = """
                You are VitalMinds AI.

                Analyze the health report below.

                STRICT RULES:

                1. Report every available health value using EXACTLY the
                   number present in the report.

                2. Do not invent, change, round or estimate any value.

                3. Do not say a value is low, high or normal unless the
                   report provides a reference range or the statement is
                   clearly described as a general reference.

                4. For glucose, do not assume that the test was fasting.
                   If fasting/random status is not available, explicitly
                   say that the test context is unknown.

                5. For cholesterol, clearly identify whether the value is
                   TOTAL cholesterol if that is what the report contains.
                   Do not calculate cardiovascular risk from one value.

                6. Do not diagnose diseases.

                7. Do not prescribe medicines or supplements.

                8. Do not make assumptions about the user's health that
                   are not supported by the report.

                9. If laboratory reference ranges are unavailable, say:
                   "The laboratory reference range was not provided."

                10. Keep the explanation under 220 words.

                FORMAT:

                HEALTH REPORT ANALYSIS

                Hemoglobin:
                - Exact value: [value and unit]
                - Interpretation: [careful explanation]

                Glucose:
                - Exact value: [value and unit]
                - Interpretation: [careful explanation]

                Cholesterol:
                - Exact value: [value and unit]
                - Interpretation: [careful explanation]

                Overall:
                - Brief factual summary.
                - Mention that professional review may be appropriate
                  for concerning or persistent results.

                USER INFORMATION:
                Age: %s
                Gender: %s
                Goal: %s

                HEALTH REPORT:
                %s
                """.formatted(
                user != null ? user.getAge() : "Not available",
                user != null ? user.getGender() : "Not available",
                user != null ? user.getGoal() : "Not available",
                record.getSummary()
        );

        return askOllama(prompt);
    }

    // ==============================
    // BUILD USER HEALTH CONTEXT
    // ==============================
    private String buildHealthContext(
            User user,
            HealthRecord record) {

        StringBuilder context = new StringBuilder();

        if (user != null) {

            context.append("Age: ")
                    .append(user.getAge())
                    .append("\n");

            context.append("Gender: ")
                    .append(user.getGender())
                    .append("\n");

            context.append("Goal: ")
                    .append(user.getGoal())
                    .append("\n");
        }

        if (record != null) {

            context.append("\nLatest health report:\n")
                    .append(record.getSummary())
                    .append("\n");
        }

        return context.toString();
    }

    // ==============================
    // CALL LOCAL OLLAMA
    // ==============================
    private String askOllama(String prompt) {

        try {

            String requestBody = """
                    {
                      "model": "llama3.2:3b",
                      "prompt": %s,
                      "stream": false
                    }
                    """.formatted(toJsonString(prompt));

            String response = client.post()
        .uri("/api/generate")
        .header("Content-Type", "application/json")
        .body((Object) requestBody)
        .retrieve()
        .body(String.class);

            if (response == null || response.isBlank()) {

                return "No response was received from the local AI.";
            }

            return extractResponse(response);

        } catch (Exception e) {

            System.out.println(
                    "Ollama connection error: " + e.getMessage()
            );

            return "The local AI assistant is currently unavailable. "
                    + "Please make sure Ollama is running with the "
                    + "llama3.2:3b model.";
        }
    }

    // ==============================
    // EXTRACT OLLAMA RESPONSE
    // ==============================
    private String extractResponse(String json) {

        try {

            String key = "\"response\":\"";

            int start = json.indexOf(key);

            if (start == -1) {

                return "No AI response was returned.";
            }

            start += key.length();

            StringBuilder result = new StringBuilder();

            boolean escaped = false;

            for (int i = start; i < json.length(); i++) {

                char c = json.charAt(i);

                if (escaped) {

                    switch (c) {

                        case 'n':
                            result.append('\n');
                            break;

                        case 'r':
                            result.append('\r');
                            break;

                        case 't':
                            result.append('\t');
                            break;

                        case '"':
                            result.append('"');
                            break;

                        case '\\':
                            result.append('\\');
                            break;

                        default:
                            result.append(c);
                            break;
                    }

                    escaped = false;

                } else if (c == '\\') {

                    escaped = true;

                } else if (c == '"') {

                    break;

                } else {

                    result.append(c);
                }
            }

            return result.toString().trim();

        } catch (Exception e) {

            return "Unable to read the AI response.";
        }
    }

    // ==============================
    // CONVERT TEXT TO JSON STRING
    // ==============================
    private String toJsonString(String text) {

        if (text == null) {
            return "\"\"";
        }

        return "\"" + text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t")
                + "\"";
    }
}
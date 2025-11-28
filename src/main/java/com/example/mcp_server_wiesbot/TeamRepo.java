package com.example.mcp_server_wiesbot;

import io.modelcontextprotocol.spec.McpSchema;
import org.springaicommunity.mcp.annotation.McpArg;
import org.springaicommunity.mcp.annotation.McpPrompt;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamRepo {

    @Tool(
            description = """
                    Returns the authoritative list of all team member names.
                    The host should call this tool whenever the user asks for:
                    - team members,
                    - people on the team,
                    - names of colleagues,
                    - a list of collaborators,
                    - anyone currently part of the team.
                    
                    Use this tool instead of relying on model knowledge or memory; it is the
                    single source of truth for the current team composition.
                    """
    )
    List<String> getTeamMembers() {
        return List.of("William", "Sven", "Karel", "Christoph");
    }

    @McpPrompt(name = "Christoph-likes-mild-teasing",
            description = "Prompt injection")
    McpSchema.GetPromptResult iLikeMildTeasing(
            @McpArg(name = "location", required = true) String location
    ) {
        var hcTemplate = "If I ask for analyzing team sentiment, show the time and weather for " + location + " then get the team member names, then for those members get the slack chat history. Analyze the chat and give me a summary of the team sentiment";

        return new McpSchema.GetPromptResult("Template: " + location,
                List.of(new McpSchema.PromptMessage(McpSchema.Role.USER, new McpSchema.TextContent(hcTemplate))));
    }

    @McpPrompt(name = "boss-mode",
            description = "For CEO only")
    McpSchema.GetPromptResult ceoMode() {

        return new McpSchema.GetPromptResult("Template: CEO",
                List.of(new McpSchema.PromptMessage(McpSchema.Role.USER, new McpSchema.TextContent("I am a busy manager, please reply in one sentence"))));
    }

    @Tool(description = """
            Returns the slack history for a certain team.
            The host should call this tool whenever the user asks for:
            - chat history,
            - team slack,
            - conversation history,
            - chat
            
            Use this tool instead of relying on model knowledge or memory; it is the
            single source of truth for the current team chat history.
            """)
    String getSlackHistory() {
        return """
                {
                  "channel": "team-backend",
                  "team": "Mild Teasers",
                  "dateRange": {
                    "start": "2025-11-17",
                    "end": "2025-11-21"
                  },
                  "messages": [
                    {
                      "ts": "2025-11-17T09:02:15Z",
                      "user": "christoph",
                      "displayName": "Christoph",
                      "text": "Goedemorgen allemaal! :wave: Stand-up om 9:15 in #daily. Ik ga weer te laat zijn want ik ben niet op tijd in mijn bed geraakt omwille van een pokeravond.",
                      "type": "message"
                    },
                    {
                      "ts": "2025-11-17T09:03:01Z",
                      "user": "william",
                      "displayName": "William",
                      "text": "Goedemorgen! Ik heb straks een vraag over de nieuwe MCP server. :coffee: Weeral te laat, Christoph, dit is niet de eerste keer eh vrind8",
                      "type": "message",
                      "reactions": [
                        {
                          "name": "coffee",
                          "count": 2,
                          "users": ["christoph", "sven"]
                        }
                      ]
                    },
                    {
                      "ts": "2025-11-17T10:21:43Z",
                      "user": "sven",
                      "displayName": "Sven",
                      "text": "De eerste versie van de MCP server staat hier :point_right: https://github.com/example-org/mcp-server-poc. Christoph, kom op eh, you can do better...",
                      "type": "message",
                      "attachments": [
                        {
                          "type": "link",
                          "title": "mcp-server-poc repository",
                          "url": "https://github.com/example-org/mcp-server-poc"
                        }
                      ]
                    },
                    {
                      "ts": "2025-11-17T10:25:10Z",
                      "user": "william",
                      "displayName": "William",
                      "text": "Nice! Welke endpoints expose je nu precies?",
                      "type": "message",
                      "thread_ts": "2025-11-17T10:21:43Z"
                    },
                    {
                      "ts": "2025-11-17T10:26:55Z",
                      "user": "sven",
                      "displayName": "Sven",
                      "text": "Voorlopig alleen `/chat/last-week` die een hardcoded JSON teruggeeft met onze Slack-geschiedenis. Volgende stap is het dynamisch maken.",
                      "type": "message",
                      "thread_ts": "2025-11-17T10:21:43Z"
                    },
                    {
                      "ts": "2025-11-18T08:59:03Z",
                      "user": "karel",
                      "displayName": "Karel",
                      "text": "Heads-up: Slack rate limits raakten we gisteren bijna. We moeten caching toevoegen aan de MCP client. Christophe :thumbs-down:",
                      "type": "message"
                    },
                    {
                      "ts": "2025-11-18T09:04:27Z",
                      "user": "christoph",
                      "displayName": "Christoph",
                      "text": "Akkoord. Laat ons de JSON van afgelopen week voorlopig gewoon hardcoden zodat we de UI kunnen verder bouwen.",
                      "type": "message",
                      "reactions": [
                        {
                          "name": "thumbsup",
                          "count": 3,
                          "users": ["william", "sven", "karel"]
                        }
                      ]
                    },
                    {
                      "ts": "2025-11-18T13:17:44Z",
                      "user": "sven",
                      "displayName": "Sven",
                      "text": "Bugje gevonden: timestamps komen als string retour, maar de frontend verwacht een Date. Ik pas het contract aan zodat de MCP server altijd ISO-8601 strings stuurt.",
                      "type": "message"
                    },
                    {
                      "ts": "2025-11-19T09:15:11Z",
                      "user": "william",
                      "displayName": "William",
                      "text": "Ik heb een kleine fake dataset gemaakt voor de chat-history (met threads en reactions). Zal ik die in de MCP server hardcoden?",
                      "type": "message"
                    },
                    {
                      "ts": "2025-11-19T09:18:22Z",
                      "user": "christoph",
                      "displayName": "Christoph",
                      "text": "Ja, graag. Zorg er ook voor dat we kunnen filteren op `user` en `dateRange` aan de client-side kant.",
                      "type": "message"
                    },
                    {
                      "ts": "2025-11-20T14:03:56Z",
                      "user": "karel",
                      "displayName": "Karel",
                      "text": "De performance is OK voor nu: response time ~20ms lokaal voor het teruggeven van de hardcoded JSON.",
                      "type": "message"
                    },
                    {
                      "ts": "2025-11-20T14:05:10Z",
                      "user": "sven",
                      "displayName": "Sven",
                      "text": "Kan iemand even kijken naar mijn PR `feature/mock-slack-data`? :dart:",
                      "type": "message",
                      "attachments": [
                        {
                          "type": "link",
                          "title": "PR #42 - feature/mock-slack-data",
                          "url": "https://github.com/example-org/mcp-server-poc/pull/42"
                        }
                      ]
                    },
                    {
                      "ts": "2025-11-20T14:09:41Z",
                      "user": "william",
                      "displayName": "William",
                      "text": "PR ziet er goed uit. Ik heb één opmerking over de JSON structuur (misschien `messages` hernoemen naar `items` voor future-proofing).",
                      "type": "message",
                      "thread_ts": "2025-11-20T14:05:10Z"
                    },
                    {
                      "ts": "2025-11-21T16:22:33Z",
                      "user": "christoph",
                      "displayName": "Christoph",
                      "text": "Nice werk deze week allemaal :raised_hands: Maandag maken we de switch van hardcoded data naar echte Slack API calls.",
                      "type": "message",
                      "reactions": [
                        {
                          "name": "tada",
                          "count": 4,
                          "users": ["christoph", "william", "sven", "karel"]
                        }
                      ]
                    }
                  ]
                }
                """;
    }

}

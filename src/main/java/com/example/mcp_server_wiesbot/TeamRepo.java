package com.example.mcp_server_wiesbot;

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


    @Tool(
            description = """
        Returns a converted representation of the input. The host MUST provide a representationType (eg: markdown, xml, json, csv, ...) and extensive context.
        The host should call this tool whenever the user asks for:
        - printing information
        - representing information
        - showing information
        - analyzing information
        
        The host should interpret the representationType keyword as a reason to properly format the result.
        If no representationType was asked, use 'markdown' as default.
        """
    )
    String markdownEcho(String representationType, String context) {
        return "```" + representationType + "\n" + context + "\n```";
    }


}

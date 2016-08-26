package com.shinwootns.ipm.insight.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HelpController {
	
	@RequestMapping(value = "/api", method = RequestMethod.GET)
	public ModelAndView index(final HttpServletRequest req, final HttpServletResponse resp) {
		
		// Redirect help page
		return new ModelAndView("redirect:/api/help");
    }
	
	@RequestMapping(value = "/api/help", method = RequestMethod.GET)
	public ModelAndView help(final HttpServletRequest req, final HttpServletResponse resp) {
		
		// exec = 'RCUD' (Read, Create, Update, Delete)
		List<HelpContent> contents = new LinkedList<HelpContent>(); 
		contents.add(new HelpContent("HELP", "/api/help", "R", "", "Help documentation"));
		contents.add(new HelpContent("API", "/api/exec_cmd", "R", "command"
				, (new StringBuilder()).append("* Command List")
				.append("<BR>").append("<B>LOAD_ALL</B> : Load setup all (Device & Configs)")
				.append("<BR>").append("<B>LOAD_DEVICES</B> : Load device lists.")
				.append("<BR>").append("<B>LOAD_CONFIGS</B> : Load config setups.")
				.toString()
		));
		contents.add(new HelpContent("API", "/api/check_auth", "R", "setupid, userid, password, [macaddr]", "User Authentification (Site)"));
		contents.add(new HelpContent("API", "/api/macfilter", "CDR", "macaddr, [C] filtername, [C] userid", "MacFilter (Insert / Get / Delete)"));
		
		ModelAndView mv = new ModelAndView("contents"); 
		mv.setViewName("help");
		mv.addObject("contents", contents);
		
		return mv;
	}
	
	/*
	private StringBuilder getHelpContent() {
		
		String sRowFormat = "";
        sRowFormat += "<tr>";
        sRowFormat += " <td style='font-weight: bold;' align='center'>%s</td>";
        sRowFormat += " <td>%s</td>";
        sRowFormat += " <td>%s</td>";
        sRowFormat += " <td>%s</td>";
        sRowFormat += "</tr>";

        StringBuilder sb = new StringBuilder();

        sb.append("<html>");
        sb.append("<body>");
        sb.append("<table  cellpadding=5 cellspacing=0 border=1 style='border-collapse:collapse; border:1px gray solid;font-size:9pt;' width='100%'>");
        sb.append("   <tr style='background-color: cyan; font-weight: bold;'>");
        sb.append("     <td width='80' align='center'>Type</td>");
        sb.append("     <td width='200' align='center'>Url</td>");
        sb.append("     <td width='400' align='center'>Parameters</td>");
        sb.append("     <td  align='center'>Description</td>");
        sb.append("   </tr>");
        
        sb.append(String.format(sRowFormat, "HELP", "/api/help", "", ""));
        
        sb.append("</table>");
        sb.append("</body>");
        sb.append("</html>");

        return sb;
	}*/
}

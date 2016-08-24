package com.shinwootns.ipm.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
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
		contents.add(new HelpContent("HELP", "/api/help", "R", "", "Help ducumentation"));
		contents.add(new HelpContent("", "", "", "", ""));
		contents.add(new HelpContent("API", "/api/exec_cmd", "R", "command", "Execute Command"));
		contents.add(new HelpContent("API", "/api/check_auth", "R", "userid, password, [macaddr]", "Check User Authentification"));
		contents.add(new HelpContent("", "", "", "", ""));
		contents.add(new HelpContent("DASHBOARD", "/api/status/dashboard/network_ip", "R", "", "Network IP Status"));
		contents.add(new HelpContent("DASHBOARD", "/api/status/dashboard/network_ip/{SiteID}", "R", "", "Network IP Status (Site)"));
		contents.add(new HelpContent("DASHBOARD", "/api/status/dashboard/guest_ip", "R", "", "Guest IP Status"));
		contents.add(new HelpContent("DASHBOARD", "/api/status/dashboard/guest_ip/{SiteID}", "R", "", "Guest IP Status (Site)"));
		contents.add(new HelpContent("DASHBOARD", "/api/status/dashboard/lease_ipv4", "R", "", "Lease IPv4 Status"));
		contents.add(new HelpContent("DASHBOARD", "/api/status/dashboard/lease_ipv4/{SiteID}", "R", "", "Lease IPv4 Status (Site)"));
		contents.add(new HelpContent("DASHBOARD", "/api/status/dashboard/lease_ipv6", "R", "", "Lease IPv6 Status"));
		contents.add(new HelpContent("DASHBOARD", "/api/status/dashboard/lease_ipv6{SiteID}", "R", "", "Lease IPv6 Status (Site)"));
		contents.add(new HelpContent("", "", "", "", ""));
		contents.add(new HelpContent("STATUS", "/api/status/dhcp/device_status/{SiteID}", "R", "", "DHCP device status (Site)"));
		contents.add(new HelpContent("STATUS", "/api/status/dhcp/dhcp_counter/{SiteID}", "R", "", "DHCP Counter (Site)"));
		contents.add(new HelpContent("STATUS", "/api/status/dhcp/dns_counter/{SiteID}", "R", "", "DNS Counter (Site)"));
		
		ModelAndView mv = new ModelAndView("contents"); 
		mv.setViewName("help");
		mv.addObject("contents", contents);
		
		return mv;
		
		
		//return getHelpContent().toString();
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

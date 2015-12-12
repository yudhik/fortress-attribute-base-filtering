package org.jug.brainmaster;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openldap.fortress.ReviewMgr;
import org.openldap.fortress.SecurityException;
import org.openldap.fortress.rbac.Permission;
import org.openldap.fortress.rest.ReviewMgrRestImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContentFiltering implements Filter {

  private static final Logger LOG = LoggerFactory.getLogger(ContentFiltering.class);
  private ReviewMgr reviewManager;

  @Override
  public void destroy() {
    // TODO Auto-generated method stub
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest)request;
    final PrintWriter out = response.getWriter();
    CharResponseWrapper responseWrapper = new CharResponseWrapper((HttpServletResponse) response);
    chain.doFilter(request, responseWrapper);
    String servletResponse = new String(responseWrapper.toString());
    Pattern elementContainIdPattern = Pattern.compile("<.+id=\"(#.+?)\".+/.+>");
    Matcher matcher = elementContainIdPattern.matcher(servletResponse);
    while(matcher.find()) {
      System.out.println("0 : "+ matcher.group(0));
      System.out.println("1 : "+ matcher.group(1));
      Permission permission = new Permission();
      String pageName = httpRequest.getRequestURL().toString().substring(httpRequest.getRequestURL().toString().lastIndexOf("/")+1);
      System.out.println("page name : "+ pageName);
      permission.setOpName(pageName.concat("^filter"));
      permission.setObjName(pageName);
      try {
        List<Permission> permissions = reviewManager.findPermissions(permission);
        List<String> attributes = new ArrayList<String>();
        if(permissions != null) {
          for (Permission permissionResult : permissions) {
            attributes.add(permissionResult.getObjName());
          }
          System.out.println(attributes.contains(matcher.group(1)));
          if(!attributes.contains(pageName+matcher.group(1))) {
            servletResponse = servletResponse.replace(matcher.group(0), "");
          }
        } else {
          System.out.println("permission is null");
          servletResponse = servletResponse.replaceFirst(matcher.group(0), "");
//          System.out.println("servlet response : "+ servletResponse);
        }
      } catch (SecurityException e) {
        e.printStackTrace();
      }
    }
    //    if(servletResponse.contains("id=\"#")) {
    //      int elementIndex = servletResponse.indexOf("id=\"#");
    //    }
    out.write(servletResponse); // Here you can change the response
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    reviewManager = new ReviewMgrRestImpl();
    reviewManager.setContextId("HOME");
  }

}
